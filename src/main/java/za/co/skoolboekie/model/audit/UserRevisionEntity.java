package za.co.skoolboekie.model.audit;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;

/**
 * Created by ryan on 2/12/2018.
 */
@Entity
@RevisionEntity(UserRevisionListener.class)
@Getter
@Setter
public class UserRevisionEntity extends DefaultRevisionEntity {

    private String userName;
}
