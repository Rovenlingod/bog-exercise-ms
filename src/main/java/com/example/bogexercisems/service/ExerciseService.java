package com.example.bogexercisems.service;

import com.example.bogexercisems.dto.ExerciseCreationRequest;
import com.example.bogexercisems.dto.ExerciseDTO;
import com.example.bogexercisems.dto.PageDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ExerciseService {

    UUID createExercise(ExerciseCreationRequest exerciseCreationRequest);
    ExerciseDTO getExerciseById(String id);
    List<ExerciseDTO> getExercisesByIds(List<String> ids);
    PageDTO<ExerciseDTO> findAll(int pageNo, int size);
}
