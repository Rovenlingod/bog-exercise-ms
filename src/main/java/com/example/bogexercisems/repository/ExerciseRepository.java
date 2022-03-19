package com.example.bogexercisems.repository;

import com.example.bogexercisems.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExerciseRepository extends PagingAndSortingRepository<Exercise, UUID> {
    Optional<Exercise> findByIdAndIsPublicTrue(UUID id);
    @Query ("SELECT e FROM Exercise e WHERE e.id = ?1 AND e.creatorId = ?2 OR e.id = ?1 AND e.isPublic = TRUE")
    Optional<Exercise> findExerciseByIdForCurrentUser(UUID exerciseId, UUID creatorId);
    List<Exercise> findAllByIdInAndIsPublicTrue(List<UUID> ids);
    @Query ("SELECT e FROM Exercise e WHERE (e.id IN ?1 AND e.creatorId = ?2) OR (e.id IN ?1 AND e.isPublic = TRUE)")
    List<Exercise> findAllForCurrent(List<UUID> ids, UUID creatorId);
}
