package za.co.skoolboekie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    public DBVersion dbVersion;

    @Getter
    public class DBVersion{
        private String version;
        @JsonProperty(value = "last_migration_date")
        private String lastMigrationDate;
        @JsonProperty(value = "migration_message")
        private String migrationMessage;

        public DBVersion(String version, String lastMigrationDate, String migrationMessage){
            this.version = version;
            this.lastMigrationDate = lastMigrationDate;
            this.migrationMessage = migrationMessage;
        }
    }

}
