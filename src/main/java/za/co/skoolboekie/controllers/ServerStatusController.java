package za.co.skoolboekie.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.skoolboekie.dto.ServerStatusDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by ryan on 2/4/2018.
 */
@RestController
public class ServerStatusController {

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServerStatusDTO> test() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowAsString = now.format(formatter);

        ServerStatusDTO serverStatusDTO = new ServerStatusDTO();
        serverStatusDTO.setNow(nowAsString);
        serverStatusDTO.setMessage("The service is alive!!");
        serverStatusDTO.setDatabaseVersion("Yet to be implemented. Will track our database migrations");

        return new ResponseEntity<>(serverStatusDTO, new HttpHeaders(), HttpStatus.OK);
    }
}
