package za.co.skoolboekie.dto.lookup;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ryan on 2/13/2018.
 */
@Getter
@Setter
public class LookupDTO {
    private String id;
    private String clientID;
    private String lookupType;
    private String value;
    private String metaData;
    private String parentID;

    public LookupDTO(){}

    public LookupDTO(String id, String clientID, String lookupType, String value, String metaData, String parentID) {
        this.id = id;
        this.clientID = clientID;
        this.lookupType = lookupType;
        this.value = value;
        this.metaData = metaData;
        this.parentID = parentID;
    }
}
