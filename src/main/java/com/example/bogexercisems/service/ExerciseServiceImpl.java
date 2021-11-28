package com.example.bogexercisems.service;

import com.example.bogexercisems.domain.Exercise;
import com.example.bogexercisems.dto.ExerciseCreationRequest;
import com.example.bogexercisems.mapper.ExerciseMapper;
import com.example.bogexercisems.repository.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private MediaStorageService mediaStorageService;
    private ExerciseMapper exerciseMapper;
    private ExerciseRepository exerciseRepository;

    public ExerciseServiceImpl(MediaStorageService mediaStorageService,
                               ExerciseMapper exerciseMapper,
                               ExerciseRepository exerciseRepository) {
        this.mediaStorageService = mediaStorageService;
        this.exerciseMapper = exerciseMapper;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public UUID createExercise(ExerciseCreationRequest exerciseCreationRequest) {
        String fileName = mediaStorageService.storeFile(exerciseCreationRequest.getMediaFile());
        Exercise newExercise = exerciseMapper.exerciseCreationRequestToExercise(exerciseCreationRequest);
        newExercise.setMediaFile(fileName);
        return exerciseRepository.save(newExercise).getId();
    }
}
