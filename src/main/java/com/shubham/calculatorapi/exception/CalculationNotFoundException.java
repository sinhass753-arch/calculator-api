package com.shubham.calculatorapi.exception;

import com.shubham.calculatorapi.dtos.CalculationRequest;

public class CalculationNotFoundException extends RuntimeException{
    public CalculationNotFoundException(String message){
        super(message);
    }
}
