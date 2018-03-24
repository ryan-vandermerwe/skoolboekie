package za.co.skoolboekie.controllers;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.co.skoolboekie.dto.lookup.LookupDTO;
import za.co.skoolboekie.model.lookups.ILookupService;
import za.co.skoolboekie.setup.ControllerTestSetup;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ryan on 2/17/2018.
 */

@WebMvcTest(LookupController.class)
public class LookupControllerTest extends ControllerTestSetup {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ILookupService lookupService;

    @Test
    public void getAllLookups() throws Exception {
        String id = UUID.randomUUID().toString();
        String clientID = UUID.randomUUID().toString();
        String metaData = UUID.randomUUID().toString();
        String parentID = UUID.randomUUID().toString();
        String value = UUID.randomUUID().toString();
        String lookupType = "DEPARTMENT";

        LookupDTO lookupDTO = new LookupDTO(id, clientID, lookupType, value, metaData, parentID);
        List<LookupDTO> lookupDTOS = Collections.singletonList(lookupDTO);

        given(lookupService.getAllLookups()).willReturn(lookupDTOS);


        mockMvc.perform(get("/lookup").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lookups[0].id", is(id)));
    }
}