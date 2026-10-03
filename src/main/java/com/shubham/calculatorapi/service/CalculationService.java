package com.shubham.calculatorapi.service;

import com.shubham.calculatorapi.dtos.CalculationRequest;
import com.shubham.calculatorapi.dtos.CalculationResponse;
import com.shubham.calculatorapi.entity.Calculation;
import com.shubham.calculatorapi.enums.Operation;
import com.shubham.calculatorapi.exception.CalculationNotFoundException;
import com.shubham.calculatorapi.repository.CalculationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDateTime;
@Slf4j
@Service
public class CalculationService {
    @Autowired
    private CalculationRepository calculationRepository;


//    @Autowired
//    private CalculationResponse calculationResponse;
//
//    @Autowired
//    private CalculationRequest calculationRequest;

    public CalculationResponse calculate(CalculationRequest request) {
        log.info("Received calculation request: {} {} {}",
                request.getFirstNumber(), request.getOperation(), request.getSecondNumber());

        double result;

        switch (request.getOperation()) {
            case ADD -> result = request.getFirstNumber() + request.getSecondNumber();
            case SUBTRACT -> result = request.getFirstNumber() - request.getSecondNumber();
            case MULTIPLY -> result = request.getFirstNumber() * request.getSecondNumber();
            case DIVIDE -> {
                if (request.getSecondNumber() == 0) {
                    log.warn("Divide by zero attempted with firstNumber = {}", request.getFirstNumber());
                    throw new ArithmeticException("Cannot divide by zero");
                }
                result = request.getFirstNumber() / request.getSecondNumber();
            }
            case MODULUS -> result = request.getFirstNumber() % request.getSecondNumber();
            case POWER -> result = Math.pow(request.getFirstNumber(), request.getSecondNumber());
            case SQRT -> result = Math.sqrt(request.getFirstNumber());
            case PERCENTAGE -> result = request.getFirstNumber() * request.getSecondNumber() / 100;
            default -> throw new IllegalArgumentException("Invalid operation");
        }


        Calculation calculation = new Calculation();
        calculation.setFirstNumber(request.getFirstNumber());
        calculation.setSecondNumber(request.getSecondNumber());
        calculation.setOperation(request.getOperation());
        calculation.setResult(result);

        calculation.setCreatedAt(LocalDateTime.now());

        calculationRepository.save(calculation);

        log.info("Calculation successful, result = {}", result);
        CalculationResponse response = new CalculationResponse();


        response.setResult(result);
        response.setOperation(request.getOperation());
        response.setId(calculation.getId());
        return response;
    }

    public List<Calculation> getAllHistory() {
        return calculationRepository.findAll();
    }

    public Calculation getById(String id) {
        return calculationRepository.findById(id)
                .orElseThrow(() -> new CalculationNotFoundException("Calculation not found with id: " + id));
    }

    public void deleteById(String id){
        calculationRepository.deleteById(id);
    }

    public  void deleteAll(){
        calculationRepository.deleteAll();
    }

    public List<Calculation> getByOperation(Operation operation){
        return calculationRepository.findByOperation(operation);
    }

    public List<Calculation> getByDateRange(LocalDateTime start, LocalDateTime end){
        return calculationRepository.findByCreatedAtBetween(start,end);
    }

}
