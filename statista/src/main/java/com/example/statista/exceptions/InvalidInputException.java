package com.example.statista.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Invalid data input. Try again.")
public class InvalidInputException extends RuntimeException{
    @Override
    public String getMessage(){
        return "Invalid data input. Check your data set and separator.";
    }
}
