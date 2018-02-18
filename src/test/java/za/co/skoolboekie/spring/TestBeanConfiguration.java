package za.co.skoolboekie.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import za.co.skoolboekie.model.lookups.ILookupService;
import za.co.skoolboekie.model.lookups.LookupServiceImpl;

/**
 * Created by ryan on 2/18/2018.
 */

@Configuration
@Import(ServiceConfiguration.class)
public class TestBeanConfiguration {

    @Bean
    public ILookupService lookupService(){
        return new LookupServiceImpl();
    }

}
