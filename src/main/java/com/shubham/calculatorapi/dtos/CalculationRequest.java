package com.shubham.calculatorapi.dtos;

import com.shubham.calculatorapi.enums.Operation;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class CalculationRequest {

    @NotNull(message = "First number is required")
    private Double firstNumber;

    private Double secondNumber;

    @NotNull(message = "Operation is required")
    private Operation operation;
}
