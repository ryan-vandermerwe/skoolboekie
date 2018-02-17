package za.co.skoolboekie.model.lookups;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import za.co.skoolboekie.dao.LookupRepository;
import za.co.skoolboekie.dto.lookup.LookupDTO;

import java.util.UUID;

/**
 * Created by ryan on 2/13/2018.
 */
@Service(value = "lookupService")
public class LookupServiceImpl implements ILookupService {

    @Autowired
    private LookupRepository lookupRepository;

    @Override
    public void saveLookup(LookupDTO lookupDTO) {
        //TODO:: Validate fields here

        UUID id = UUID.randomUUID();
        Lookup lookup = new Lookup();
        lookup.setId(id);
        lookup.setClientID(lookupDTO.getClientID());
        lookup.setLookupType(Lookup.LookupTypes.values()[new Integer(lookupDTO.getLookupType())]);
        lookup.setMetaData(lookupDTO.getMetaData());
        lookup.setValue(lookupDTO.getValue());

        if(!StringUtils.isEmpty(lookupDTO.getParentID())){
            // Test for malformed UUID here
            Lookup parent = lookupRepository.getOne(UUID.fromString(lookupDTO.getParentID()));

            if(parent != null)
                lookup.setParent(parent);

        }

        lookupRepository.save(lookup);
    }
}
