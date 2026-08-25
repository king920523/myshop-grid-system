package com.example.myshop.controller.grid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object>handleValidation(MethodArgumentNotValidException ex){

        String errorMsg = ex.getBindingResult().getFieldError().getDefaultMessage();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", errorMsg);



        return  response; 
    }



    
}
