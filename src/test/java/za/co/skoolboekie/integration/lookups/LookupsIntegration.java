package za.co.skoolboekie.integration.lookups;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.co.skoolboekie.model.lookups.Lookup;
import za.co.skoolboekie.setup.IntegrationTestSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ryan on 2/18/2018.
 */

@Slf4j
public class LookupsIntegration extends IntegrationTestSetup {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postLookups() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        int numThreads = 100;
        List<Runnable> threads = new ArrayList<>(numThreads);

        for (int i = 1; i <= numThreads; i++) {
            Lookup lookup = new Lookup(null, "" + i, Lookup.LookupTypes.DEPARTMENT,
                    "" + i, UUID.randomUUID().toString(), null);
            String content = objectMapper.writeValueAsString(lookup);
            threads.add(() -> {
                try {
                    mockMvc.perform(post("/lookup").contentType(MediaType.APPLICATION_JSON).content(content)).andDo(print()).andExpect(status().isOk()).andReturn();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        List<Callable<Void>> callables = new ArrayList<>(numThreads);
        threads.forEach(runnable -> callables.add(() -> {
            runnable.run();
            return null;
        }));

        ExecutorService executorService = Executors.newFixedThreadPool(threads.size());
        executorService.invokeAll(callables);

        mockMvc.perform(get("/lookup")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.lookups", hasSize(numThreads)));

        executorService.shutdownNow();
    }

}
