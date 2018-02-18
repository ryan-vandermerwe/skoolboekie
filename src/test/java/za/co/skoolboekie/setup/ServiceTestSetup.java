package za.co.skoolboekie.setup;


import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.skoolboekie.spring.TestBeanConfiguration;

/**
 * Created by ryan on 2/18/2018.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {TestBeanConfiguration.class})
public abstract class ServiceTestSetup {
}
