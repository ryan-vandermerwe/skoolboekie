package za.co.skoolboekie.common;

import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.skoolboekie.dto.lookup.LookupDTO;
import za.co.skoolboekie.model.lookups.Lookup;

/**
 * Created by ryan on 2/17/2018.
 */
@Component
public class EntityMapperConfig {

   @Autowired
   private MapperFactory mapperFactory;
   
   public void init(){
      lookupToLookupDTO();
   }

   private void lookupToLookupDTO() {
      mapperFactory.classMap(Lookup.class, LookupDTO.class)
              .field("id", "id")
              .field("lookupType", "lookupType")
              .field("clientID", "clientID")
              .field("value", "value")
              .field("metaData", "metaData")
              .field("parent.id", "parentID")
              .register();
   }


   public void setMapperFactory(MapperFactory mapperFactory) {
      this.mapperFactory = mapperFactory;
   }
}
