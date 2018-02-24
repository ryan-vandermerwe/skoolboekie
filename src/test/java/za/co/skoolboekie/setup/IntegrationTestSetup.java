package za.co.skoolboekie.setup;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.skoolboekie.SkoolBoekie;

/**
 * Created by ryan on 2/24/2018.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SkoolBoekie.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yaml")
public abstract class IntegrationTestSetup {
}
