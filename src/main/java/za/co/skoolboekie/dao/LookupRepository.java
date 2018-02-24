package za.co.skoolboekie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.skoolboekie.model.lookups.Lookup;

import java.util.UUID;

/**
 * Created by ryan on 2/13/2018.
 */
@Repository
public interface LookupRepository extends JpaRepository<Lookup, UUID> {
}
