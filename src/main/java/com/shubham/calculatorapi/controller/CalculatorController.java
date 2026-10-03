package com.shubham.calculatorapi.controller;

import com.shubham.calculatorapi.dtos.CalculationRequest;
import com.shubham.calculatorapi.dtos.CalculationResponse;
import com.shubham.calculatorapi.entity.Calculation;
import com.shubham.calculatorapi.enums.Operation;
import com.shubham.calculatorapi.repository.CalculationRepository;
import com.shubham.calculatorapi.service.CalculationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {
    @Autowired
    private CalculationService calculationService;

    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(@Valid @RequestBody CalculationRequest request){
        CalculationResponse response = calculationService.calculate(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<CalculationResponse>> history(
            @RequestParam(required = false) Operation operation) {

        List<Calculation> calculations;

        if (operation != null) {
            calculations = calculationService.getByOperation(operation);
        } else {
            calculations = calculationService.getAllHistory();
        }

        List<CalculationResponse> responses = new ArrayList<>();
        for (Calculation c : calculations) {
            CalculationResponse response = new CalculationResponse();
            response.setResult(c.getResult());
            response.setOperation(c.getOperation());
            response.setId(c.getId());
            responses.add(response);
        }

        return ResponseEntity.ok(responses);
    }

//    @GetMapping("/history")
//    public ResponseEntity<List<CalculationResponse>> history() {
////        List<Calculation> calculations = calculationService.getAllHistory();
////        List<CalculationResponse> responses = new ArrayList<>();
////
////        for (Calculation c : calculations) {
////            CalculationResponse response = new CalculationResponse();
////            response.setResult(c.getResult());
////            response.setOperation(c.getOperation());
////            response.setId(c.getId());
////            responses.add(response);
////        }
//
//
//
//        return ResponseEntity.ok(responses);
//    }

    @GetMapping("/history/{id}")
    public  ResponseEntity<CalculationResponse> getbyId(@PathVariable String id){
        Calculation c = calculationService.getById(id);
        CalculationResponse calculationResponse = new CalculationResponse();
        calculationResponse.setOperation(c.getOperation());
        calculationResponse.setResult(c.getResult());
        calculationResponse.setId(c.getId());

        return ResponseEntity.ok(calculationResponse);
    }

    @GetMapping("/history/range")
    public ResponseEntity<List<CalculationResponse>> historyByDateRange(
            @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate
            ){

        List<Calculation> calculations = calculationService.getByDateRange(startDate, endDate);
        List<CalculationResponse> responses = new ArrayList<>();

        for (Calculation c : calculations) {
            CalculationResponse response = new CalculationResponse();
            response.setResult(c.getResult());
            response.setOperation(c.getOperation());
            responses.add(response);
        }

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/history/{id}")
    public ResponseEntity<CalculationResponse> deleteById(@PathVariable String id ){
        calculationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/history")
    public ResponseEntity<CalculationResponse> deleteAll(){
        calculationService.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
