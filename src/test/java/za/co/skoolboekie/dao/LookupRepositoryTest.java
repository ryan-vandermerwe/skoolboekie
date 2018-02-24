package za.co.skoolboekie.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import za.co.skoolboekie.model.lookups.Lookup;
import za.co.skoolboekie.setup.RepoTestSetup;

import java.util.List;
import java.util.UUID;

/**
 * Created by ryan on 2/15/2018.
 */

public class LookupRepositoryTest extends RepoTestSetup {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private LookupRepository lookupRepository;

    @Test
    public void testLookupSave() {
        Lookup lookup = new Lookup(UUID.randomUUID(), "L-1", Lookup.LookupTypes.DEPARTMENT, "Physics", "", null);
        testEntityManager.persist(lookup);
        testEntityManager.flush();

        List<Lookup> persistedLookups = lookupRepository.findAll();
        Assert.assertTrue(!persistedLookups.isEmpty());
    }
}