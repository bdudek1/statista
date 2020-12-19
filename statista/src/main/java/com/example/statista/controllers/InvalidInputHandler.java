package com.example.statista.controllers;

import com.example.statista.exceptions.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class InvalidInputHandler {

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView invalidInput(HttpServletRequest req, InvalidInputException e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("problem", e.getMessage());
        mav.setViewName("error");
        return mav;
    }

}
