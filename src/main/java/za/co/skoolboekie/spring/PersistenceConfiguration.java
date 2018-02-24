package za.co.skoolboekie.spring;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by ryan on 2/5/2018.
 */

@Slf4j
@Configuration
@EnableJpaRepositories("za.co.skoolboekie.dao")
public class PersistenceConfiguration {

    private static final long DEFAULT_MIN_CONS_PER_PARTITION = 1;
    private static final long DEFAULT_MAX_CONS_PER_PARTITION = 4;
    private static final long DEFAULT_PARTITION_COUNT = 5;
    private static final long DEFAULT_IDLE_MAX_AGE = 240;
    private static final long DEFAULT_STATEMENTS_CACHE_SIZE = 100;

    @Autowired
    private Environment env;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        // flyway.setLocations("classpath:" + classPath);
        flyway.setDataSource(dataSource());
        return flyway;
    }

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {

        final HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        hikariDataSource.setUsername(env.getProperty("jdbc.username"));
        hikariDataSource.setPassword(env.getProperty("jdbc.password"));
        hikariDataSource.setIdleTimeout(get(toMilliseconds(Long.valueOf(env.getProperty("jdbc.idleMaxAge"))),
                toMilliseconds(DEFAULT_IDLE_MAX_AGE)));

        final int minConnectionsPerPartition = get(Long.valueOf(env.getProperty("jdbc.MinConnectionsPerPartition")), DEFAULT_MIN_CONS_PER_PARTITION).intValue();
        final int maxConnectionsPerPartition = get(Long.valueOf(env.getProperty("jdbc.MaxConnectionsPerPartition")), DEFAULT_MAX_CONS_PER_PARTITION).intValue();
        final int partitionCount = get(Long.valueOf(env.getProperty("jdbc.PartitionCount")), DEFAULT_PARTITION_COUNT).intValue();

        hikariDataSource.setMinimumIdle(minConnectionsPerPartition * partitionCount);
        hikariDataSource.setMinimumIdle(minConnectionsPerPartition * partitionCount);
        hikariDataSource.setMaximumPoolSize(maxConnectionsPerPartition * partitionCount);

        hikariDataSource.addDataSourceProperty("cachePrepStmts", true);
        hikariDataSource.addDataSourceProperty("prepStmtCacheSize", get(Long.valueOf(env.getProperty("jdbc.statementsCacheSize")), DEFAULT_STATEMENTS_CACHE_SIZE).intValue());
        hikariDataSource.setAllowPoolSuspension(false);

        return hikariDataSource;

    }

    @Bean(name = "entityManagerFactory")
    @DependsOn("flyway")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        //jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabasePlatform(env.getProperty("hibernate.dialect"));

        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("za.co.skoolboekie.model");
        emf.setPersistenceUnitName("default");
        emf.afterPropertiesSet();
        return emf;
    }


    @Bean
    @Primary
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("za.co.skoolboekie");

        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.default_schema", env.getProperty("hibernate.default_schema"));
                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
                setProperty("hibernate.show_sql",env.getProperty("hibernate.show_sql"));
                setProperty("hibernate.globally_quoted_identifiers", env.getProperty("hibernate.globally_quoted_identifiers"));
                setProperty("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size"));
                setProperty("hibernate.jdbc.batch_versioned_data", env.getProperty("hibernate.jdbc.batch_versioned_data"));

                final String createSchema = "update"; // This should change!!

                setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.createSchema"));
                setProperty("hibernate.generate_statistics", "false");
                setProperty("hibernate.ejb.event.post-insert", "org.hibernate.ejb.event.EJB3PostInsertEventListener,org.hibernate.envers.event.AuditEventListener");
                setProperty("hibernate.ejb.event.post-update", "org.hibernate.ejb.event.EJB3PostUpdateEventListener,org.hibernate.envers.event.AuditEventListener");
                setProperty("hibernate.ejb.event.post-delete", "org.hibernate.ejb.event.EJB3PostDeleteEventListener,org.hibernate.envers.event.AuditEventListener");
                setProperty("hibernate.ejb.event.pre-collection-update", "org.hibernate.envers.event.AuditEventListener");
                setProperty("hibernate.ejb.event.pre-collection-remove", "org.hibernate.envers.event.AuditEventListener");
                setProperty("hibernate.ejb.event.post-collection-recreate", "org.hibernate.envers.event.AuditEventListener");
            }
        };
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return jpaTransactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Long get(Long property, Long defVal) {
        return (property == null) ? defVal : property;
    }

    private Long toMilliseconds(Long property) {
        return property * 1000;
    }


    static String getClassPath() {
        String classPath = null;
        try {
            File file = ResourceUtils.getFile("");
            try {
                classPath = file.getCanonicalPath();
                log.debug("Canonical root path: " + classPath);
                // return c;
            } catch (IOException e) {
                log.warn("Exception getting canonicalPath for Skoolboekie root (\"\")", e);
                //return "";
            }
        } catch (FileNotFoundException e) {
            log.warn("Exception getting Skoolboekie root (\"\")", e);
            //  return "";
        }

        //TODO:: In the future we are gonna have to do this for the tests and actual window service
        // Find the path -> For now we running from the IDE
        if (StringUtils.isEmpty(classPath)) {
            throw new RuntimeException("Cannot find the class path!");
        }

        return classPath + "\\src\\main\\resources\\db\\migration";

    }

}
