package za.co.skoolboekie.model.audit;

import org.hibernate.envers.RevisionListener;

/**
 * Created by ryan on 2/12/2018.
 */
public class UserRevisionListener implements RevisionListener {

    private final static String SYSTEM = "Skool Boekie";

    @Override
    public void newRevision(Object revisionEntity) {
        UserRevisionEntity userRevisionEntity = (UserRevisionEntity)revisionEntity;
        userRevisionEntity.setUserName(SYSTEM);
    }
}
