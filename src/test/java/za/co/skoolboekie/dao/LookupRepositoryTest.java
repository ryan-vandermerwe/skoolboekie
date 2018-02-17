package za.co.skoolboekie.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.skoolboekie.model.lookups.Lookup;

import java.util.List;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

/**
 * Created by ryan on 2/15/2018.
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)

public class LookupRepositoryTest{
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private LookupRepository lookupRepository;

    @Test
    public void testLookupSave() {
        Lookup lookup = new Lookup("L-1", Lookup.LookupTypes.DEPARTMENT, "Physics", "", null);
        testEntityManager.persist(lookup);
        testEntityManager.flush();

        List<Lookup> persistedLookups = lookupRepository.findAll();
        Assert.assertTrue(!persistedLookups.isEmpty());
    }
}