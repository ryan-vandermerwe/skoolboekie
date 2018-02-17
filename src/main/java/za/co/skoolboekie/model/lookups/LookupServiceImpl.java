package za.co.skoolboekie.model.lookups;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import za.co.skoolboekie.dao.LookupRepository;
import za.co.skoolboekie.dto.lookup.LookupDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by ryan on 2/13/2018.
 */
@Service(value = "lookupService")
public class LookupServiceImpl implements ILookupService {

    @Autowired
    private LookupRepository lookupRepository;

    @Autowired
    private MapperFactory mapperFactory;

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

        if (!StringUtils.isEmpty(lookupDTO.getParentID())) {
            // Test for malformed UUID here
            Lookup parent = lookupRepository.getOne(UUID.fromString(lookupDTO.getParentID()));

            if (parent != null)
                lookup.setParent(parent);

        }

        lookupRepository.save(lookup);
    }

    @Override
    public List<LookupDTO> getAllLookups() {
        List<Lookup> lookups = lookupRepository.findAll();
        BoundMapperFacade<Lookup, LookupDTO> lookupsFacade = mapperFactory.getMapperFacade(Lookup.class, LookupDTO.class);

        return lookups.stream().map(lookupsFacade::map).collect(Collectors.toList());
    }
}
