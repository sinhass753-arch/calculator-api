package com.shubham.calculatorapi.dtos;

import com.shubham.calculatorapi.enums.Operation;
import lombok.Data;

@Data
public class CalculationResponse {
    private String id;
    private Double result;
    private Operation operation;
}
