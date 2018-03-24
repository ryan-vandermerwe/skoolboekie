package za.co.skoolboekie.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by ryan on 3/5/2018.
 */
@Component
public class DatabaseHealthCheck extends HealthCheck{
    @Autowired
    private HealthCheckRegistry healthCheckRegistry;

    @PostConstruct
    public void init(){
        healthCheckRegistry.register("DB", this);
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy("Healthy");
    }
}
