package com.mcommandes.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommandeNotFoundException extends RuntimeException {


    public CommandeNotFoundException(String message) {
        super(message);
    }
}
