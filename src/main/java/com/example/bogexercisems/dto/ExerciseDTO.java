package com.example.bogexercisems.dto;

import com.example.bogexercisems.enums.MuscleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {
    private String id;
    private String title;
    private String mediaFileUrl;
    private Boolean isPublic;
    private Long countdownInSeconds;
    private MuscleType muscleType;
    private String creatorId;
    private List<EquipmentDTO> equipmentDTOS;
}
