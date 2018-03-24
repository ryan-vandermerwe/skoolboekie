package za.co.skoolboekie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ryan on 3/24/2018.
 */
public class MigrationDTO {
    @JsonProperty(value = "version")
    private String version;
    @JsonProperty(value = "message")
    private String migrationMessage;
    @JsonProperty(value = "date")
    private String migrationDate;

    public MigrationDTO(String version, String migrationDate, String migrationMessage){
        this.version = version;
        this.migrationDate = migrationDate;
        this.migrationMessage = migrationMessage;
    }
}
