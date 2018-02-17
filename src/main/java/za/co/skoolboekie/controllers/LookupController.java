package za.co.skoolboekie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import za.co.skoolboekie.dto.lookup.LookupDTO;
import za.co.skoolboekie.model.lookups.ILookupService;

/**
 * Created by ryan on 2/13/2018.
 */
@Controller
public class LookupController {

    @Autowired
    private ILookupService lookupService;

    @RequestMapping(value = "/lookup", method = RequestMethod.POST)

    public ResponseEntity saveLookup(@RequestBody LookupDTO lookupDTO) {
        lookupService.saveLookup(lookupDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}
