package com.example.bogexercisems.controller;

import com.example.bogexercisems.dto.ExerciseCreationRequest;
import com.example.bogexercisems.dto.ExerciseDTO;
import com.example.bogexercisems.service.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.Pattern;
import java.net.URI;
import java.util.UUID;

@RestController
@Validated
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

    @GetMapping("/exercise/{id}")
    public ResponseEntity<ExerciseDTO> getExerciseById(@PathVariable("id") @Pattern(regexp = "^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$") String id) {
        return ResponseEntity.ok().body(exerciseService.getExerciseById(id));
    }
}
