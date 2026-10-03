package com.shubham.calculatorapi.repository;

import com.shubham.calculatorapi.entity.Calculation;
import com.shubham.calculatorapi.enums.Operation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CalculationRepository extends MongoRepository<Calculation, String> {
    List<Calculation> findByOperation(Operation operation);
    List<Calculation> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}