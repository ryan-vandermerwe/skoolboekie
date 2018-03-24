package za.co.skoolboekie.model.lookups;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import za.co.skoolboekie.common.BaseEntity;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by ryan on 2/12/2018.
 */

@Audited
@Entity
@Table(name = "lookups")
@Getter
@Setter
public class Lookup extends BaseEntity {
    public enum LookupTypes {
        DEPARTMENT;

        public static LookupTypes getLookupValue(String value){
            for(LookupTypes lookupType: LookupTypes.values()){
                if(lookupType.name().equalsIgnoreCase(value)){
                    return lookupType;
                }
            }

            throw new RuntimeException("Cannot find lookup type " + value);
        }

    }

    @Column(nullable = true, unique = true, name = "clientid")
    private String clientID;

    @Column(nullable = false, name = "lookuptype")
    @Enumerated(EnumType.STRING)
    private LookupTypes lookupType;

    @Column(nullable = false)
    private String value;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metaData;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, optional = true)
    private Lookup parent;

    public Lookup(){}

    public Lookup(UUID id, String clientID, LookupTypes lookupType, String value, String metaData, Lookup parent) {
        super.setId(id);
        this.clientID = clientID;
        this.lookupType = lookupType;
        this.value = value;
        this.metaData = metaData;
        this.parent = parent;
    }
}
