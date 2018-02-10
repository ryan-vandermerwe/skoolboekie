package za.co.skoolboekie.spring;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by ryan on 2/5/2018.
 */

@Configuration
public class PersistenceConfiguration {

    private static final long DEFAULT_MIN_CONS_PER_PARTITION = 1;
    private static final long DEFAULT_MAX_CONS_PER_PARTITION = 4;
    private static final long DEFAULT_PARTITION_COUNT = 5;
    private static final long DEFAULT_IDLE_MAX_AGE = 240;
    private static final long DEFAULT_STATEMENTS_CACHE_SIZE = 100;

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {

        final HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/skoolboekie");
        hikariDataSource.setUsername("postgres");
        hikariDataSource.setPassword("ryanjason");
        hikariDataSource.setIdleTimeout(get(toMilliseconds(0L),
                toMilliseconds(DEFAULT_IDLE_MAX_AGE)));

        final int minConnectionsPerPartition = get(1L, DEFAULT_MIN_CONS_PER_PARTITION).intValue();
        final int maxConnectionsPerPartition = get(4L, DEFAULT_MAX_CONS_PER_PARTITION).intValue();
        final int partitionCount = get(1L, DEFAULT_PARTITION_COUNT).intValue();

        hikariDataSource.setMinimumIdle(minConnectionsPerPartition * partitionCount);
        hikariDataSource.setMinimumIdle(minConnectionsPerPartition * partitionCount);
        hikariDataSource.setMaximumPoolSize(maxConnectionsPerPartition * partitionCount);

        hikariDataSource.addDataSourceProperty("cachePrepStmts", true);
        hikariDataSource.addDataSourceProperty("prepStmtCacheSize", get((Long) 100L, DEFAULT_STATEMENTS_CACHE_SIZE).intValue());
        hikariDataSource.setAllowPoolSuspension(false);

        return hikariDataSource;

    }

    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");

        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("za.co.skoolboekie.model");
        emf.setPersistenceUnitName("default");
        emf.afterPropertiesSet();
        return emf.getObject();
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
                setProperty("hibernate.default_schema", "public");
                setProperty("hibernate.dialect", "org.hibernate.dialect.PostgresPlusDialect");
                setProperty("hibernate.show_sql", "false");
                setProperty("hibernate.globally_quoted_identifiers", "false");
                setProperty("hibernate.jdbc.batch_size", "50");
                setProperty("hibernate.jdbc.batch_versioned_data", "true");

                final String createSchema = "update"; // This should change!!

                setProperty("hibernate.hbm2ddl.auto", createSchema);
                setProperty("hibernate.generate_statistics", "false");
            }
        };
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory().getObject());
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

}