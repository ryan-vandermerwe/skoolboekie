package za.co.skoolboekie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import za.co.skoolboekie.spring.FileConfigClient;

import java.util.Arrays;

/**
 * Created by ryan on 2/4/2018.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@Slf4j
public class SkoolBoekie implements ApplicationRunner{

    public static void main(String [] args){
        SpringApplication.run(SkoolBoekie.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
        log.info("NonOptionArgs: {}", args.getNonOptionArgs());
        log.info("OptionNames: {}", args.getOptionNames());
        // FileConfigClient fileConfigClient = new FileConfigClient();
    }
}
