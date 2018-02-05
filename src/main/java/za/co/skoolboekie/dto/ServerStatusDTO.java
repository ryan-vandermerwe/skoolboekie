package za.co.skoolboekie.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ryan on 2/4/2018.
 */
@Getter
@Setter
public class ServerStatusDTO {
    public String message;
    public String now;
    public String databaseVersion;
}
