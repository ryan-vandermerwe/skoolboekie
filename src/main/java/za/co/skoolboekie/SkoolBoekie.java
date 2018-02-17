package za.co.skoolboekie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;

/**
 * Created by ryan on 2/4/2018.
 * //
 */

@SpringBootApplication(scanBasePackages = "za.co.skoolboekie")
@EnableJpaRepositories("za.co.skoolboekie.dao")
@Slf4j
public class SkoolBoekie implements ApplicationRunner {

    public static void main(String[] args) {
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
