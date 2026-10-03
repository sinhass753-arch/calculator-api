package com.shubham.calculatorapi.entity;

import com.shubham.calculatorapi.enums.Operation;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "calculations")
@Data
public class Calculation {

    @Id
    private String id;

    private Double firstNumber;
    private Double secondNumber;
    private Operation operation;
    private Double result;
    private LocalDateTime createdAt;
}