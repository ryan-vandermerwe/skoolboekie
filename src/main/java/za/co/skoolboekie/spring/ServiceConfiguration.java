package za.co.skoolboekie.spring;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import za.co.skoolboekie.common.EntityMapperConfig;

/**
 * Created by ryan on 2/17/2018.
 */

@Configuration
public class ServiceConfiguration {

    @Bean
    public MapperFactory mapperFactory(){
        return new DefaultMapperFactory.Builder().build();
    }

    @Bean(initMethod = "init")
    public EntityMapperConfig mapperConfiguration(){
        EntityMapperConfig entityMapperConfig = new EntityMapperConfig();
        entityMapperConfig.setMapperFactory(mapperFactory());
        return entityMapperConfig;
    }

    @Bean
    public HealthCheckRegistry healthCheckRegistry(){
        return new HealthCheckRegistry();
    }

    @Bean
    public MetricRegistry metricRegistry(){
        return new MetricRegistry();
    }
}
