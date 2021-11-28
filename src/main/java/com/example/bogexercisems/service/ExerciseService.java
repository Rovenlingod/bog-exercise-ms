package com.example.bogexercisems.service;

import com.example.bogexercisems.dto.ExerciseCreationRequest;

import java.util.UUID;

public interface ExerciseService {

    UUID createExercise(ExerciseCreationRequest exerciseCreationRequest);

}
