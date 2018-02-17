package za.co.skoolboekie.dto.lookup;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ryan on 2/13/2018.
 */
@Getter
@Setter
public class LookupDTO {
    private Long id;
    private String clientID;
    private String lookupType;
    private String value;
    private String metaData;
    private String parentID;
}
