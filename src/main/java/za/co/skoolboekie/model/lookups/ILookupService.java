package za.co.skoolboekie.model.lookups;

import za.co.skoolboekie.dto.lookup.LookupDTO;

import java.util.List;

/**
 * Created by ryan on 2/13/2018.
 */
public interface ILookupService {

    public void saveLookup(LookupDTO lookupDTO);

    List<LookupDTO> getAllLookups();
}
