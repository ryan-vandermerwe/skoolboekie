package za.co.skoolboekie.common;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ryan on 2/12/2018.
 */

@Audited
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datemodified", columnDefinition = "timestamp with time zone")
    @Version
    private Date dateModified;

    @Override
    public String toString() {
        return id.toString();
    }
}
