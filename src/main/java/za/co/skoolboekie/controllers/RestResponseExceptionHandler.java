package za.co.skoolboekie.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by ryan on 3/24/2018.
 */

@ControllerAdvice
@Slf4j
public class RestResponseExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void catchAllExceptions(Exception r){
        log.debug(r.getMessage());
    }
}
