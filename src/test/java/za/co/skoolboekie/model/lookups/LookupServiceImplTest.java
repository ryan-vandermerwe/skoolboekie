package za.co.skoolboekie.model.lookups;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import za.co.skoolboekie.dao.LookupRepository;
import za.co.skoolboekie.dto.lookup.LookupDTO;
import za.co.skoolboekie.setup.ServiceTestSetup;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ryan on 2/18/2018.
 */

public class LookupServiceImplTest extends ServiceTestSetup {

    @Autowired
    private ILookupService lookupService;

    @MockBean
    private LookupRepository lookupRepository;

    @Test
    public void testGetAllLookups() {

        String id = UUID.randomUUID().toString();
        String clientID = UUID.randomUUID().toString();
        String metaData = UUID.randomUUID().toString();
        String value = UUID.randomUUID().toString();
        Lookup lookup = new Lookup(UUID.fromString(id), clientID, Lookup.LookupTypes.DEPARTMENT, value, metaData, null);

        Mockito.when(lookupRepository.findAll()).thenReturn(Collections.singletonList(lookup));
        List<LookupDTO> persistedLookups = lookupService.getAllLookups();
        assertThat(persistedLookups.get(0).getClientID()).isEqualTo(lookup.getClientID());
    }

}