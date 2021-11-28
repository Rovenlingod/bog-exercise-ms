package com.example.bogexercisems.service;

import com.example.bogexercisems.dto.ExerciseCreationRequest;
import com.example.bogexercisems.dto.ExerciseDTO;

import java.util.UUID;

public interface ExerciseService {

    UUID createExercise(ExerciseCreationRequest exerciseCreationRequest);
    ExerciseDTO getExerciseById(String id);

}
