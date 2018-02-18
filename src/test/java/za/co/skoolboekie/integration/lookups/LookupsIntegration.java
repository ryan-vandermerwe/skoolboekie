package za.co.skoolboekie.integration.lookups;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import za.co.skoolboekie.SkoolBoekie;
import za.co.skoolboekie.dao.LookupRepository;
import za.co.skoolboekie.model.lookups.Lookup;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ryan on 2/18/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SkoolBoekie.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test/resources/application-integrationtest.properties")
public class LookupsIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LookupRepository lookupRepository;

    @Test
    public void getAllLookups() throws Exception {
        Lookup lookup = new Lookup(UUID.randomUUID(), UUID.randomUUID().toString(), Lookup.LookupTypes.DEPARTMENT,
                "DEPT_1", UUID.randomUUID().toString(), null);

        lookupRepository.save(lookup);
        lookupRepository.flush();

        mockMvc.perform(get("/lookup")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("bob")));
    }
}
