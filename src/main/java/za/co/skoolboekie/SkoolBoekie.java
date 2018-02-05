package za.co.skoolboekie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ryan on 2/4/2018.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class SkoolBoekie {

    public static void main(String [] args){
        SpringApplication.run(SkoolBoekie.class, args);
    }
}
