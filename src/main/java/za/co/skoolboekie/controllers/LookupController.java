package za.co.skoolboekie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import za.co.skoolboekie.dto.lookup.LookupDTO;
import za.co.skoolboekie.model.lookups.ILookupService;

import java.util.List;

/**
 * Created by ryan on 2/13/2018.
 */
@Controller
public class LookupController {

    @Autowired
    private ILookupService lookupService;

    @RequestMapping(value = "/lookup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity saveLookup(@RequestBody LookupDTO lookupDTO) {
        lookupService.saveLookup(lookupDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/lookup", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public ResponseEntity<List<LookupDTO>> getAllLookups(){
        List<LookupDTO> lookupDTOS = lookupService.getAllLookups();
        return new ResponseEntity<>(lookupDTOS, new HttpHeaders(), HttpStatus.OK);
    }
}
