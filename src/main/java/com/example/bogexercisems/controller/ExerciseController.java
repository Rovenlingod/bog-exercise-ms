package com.example.bogexercisems.controller;

import com.example.bogexercisems.dto.ExerciseCreationRequest;
import com.example.bogexercisems.service.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
public class ExerciseController {

    private ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping("/exercise")
    public ResponseEntity<Object> saveExercise(ExerciseCreationRequest exerciseCreationRequest) {
        UUID id = exerciseService.createExercise(exerciseCreationRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id.toString()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
