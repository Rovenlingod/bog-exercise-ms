package com.example.bogexercisems.service;

import com.example.bogexercisems.domain.Exercise;
import com.example.bogexercisems.dto.ExerciseCreationRequest;
import com.example.bogexercisems.dto.ExerciseDTO;
import com.example.bogexercisems.dto.PageDTO;
import com.example.bogexercisems.exception.ExerciseException;
import com.example.bogexercisems.mapper.ExerciseMapper;
import com.example.bogexercisems.mapper.PageMapper;
import com.example.bogexercisems.repository.ExerciseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private MediaStorageService mediaStorageService;
    private ExerciseMapper exerciseMapper;
    private ExerciseRepository exerciseRepository;
    private PageMapper pageMapper;

    public ExerciseServiceImpl(MediaStorageService mediaStorageService,
                               ExerciseMapper exerciseMapper,
                               ExerciseRepository exerciseRepository,
                               PageMapper pageMapper) {
        this.mediaStorageService = mediaStorageService;
        this.exerciseMapper = exerciseMapper;
        this.exerciseRepository = exerciseRepository;
        this.pageMapper = pageMapper;
    }

    @Override
    public UUID createExercise(ExerciseCreationRequest exerciseCreationRequest) {
        String fileName = mediaStorageService.storeFile(exerciseCreationRequest.getMediaFile());
        Exercise newExercise = exerciseMapper.exerciseCreationRequestToExercise(exerciseCreationRequest);
        newExercise.setMediaFile(fileName);
        return exerciseRepository.save(newExercise).getId();
    }

    @Override
    public ExerciseDTO getExerciseById(String id) {
        Exercise exercise = exerciseRepository
                .findByIdAndIsPublicTrue(UUID.fromString(id))
                .orElseThrow(() -> new ExerciseException("Exercise with id = " + id + " does not exist or not for public access"));
        return exerciseMapper.exerciseToExerciseDTO(exercise);
    }

    @Override
    public List<ExerciseDTO> getExercisesByIds(List<String> ids) {
        List<Exercise> exercises = exerciseRepository
                .findAllByIdInAndIsPublicTrue(ids.stream().map(UUID::fromString).collect(Collectors.toList()));
        if (exercises.isEmpty()) throw new ExerciseException("Exercises from provided list are non-existent or are not for public use");
        return exerciseMapper.toDtos(exercises);
    }

    @Override
    public PageDTO<ExerciseDTO> findAll(int pageNo, int size) {
        Pageable paging = PageRequest.of(pageNo, size);
        Page<Exercise> pagedResult = exerciseRepository.findAll(paging);
        return pageMapper.toDto(pagedResult.map(e -> exerciseMapper.exerciseToExerciseDTO(e)));
    }
}
