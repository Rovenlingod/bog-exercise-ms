package com.example.bogexercisems.repository;

import com.example.bogexercisems.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExerciseRepository extends PagingAndSortingRepository<Exercise, UUID> {
    Optional<Exercise> findByIdAndIsPublicTrue(UUID id);
    List<Exercise> findAllByIdInAndIsPublicTrue(List<UUID> ids);
}
