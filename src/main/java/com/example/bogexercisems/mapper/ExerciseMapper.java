package com.example.bogexercisems.mapper;

import com.example.bogexercisems.domain.Equipment;
import com.example.bogexercisems.domain.Exercise;
import com.example.bogexercisems.dto.ExerciseCreationRequest;
import com.example.bogexercisems.repository.EquipmentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Mapper(componentModel="spring")
public abstract class ExerciseMapper {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Mappings({
            @Mapping(source = "title", target = "title"),
            @Mapping(target = "creatorId", expression = "java(java.util.UUID.fromString(exerciseCreationRequest.getCreatorId()))"),
            @Mapping(target = "equipment", expression = "java(this.map(exerciseCreationRequest.getEquipmentIds()))")
    })
    public abstract Exercise exerciseCreationRequestToExercise(ExerciseCreationRequest exerciseCreationRequest);

    public String map(MultipartFile value) {
        return null;
    }

    public List<Equipment> map(List<String> value) {
        List<Equipment> result = new ArrayList<>();
        value.forEach(e ->
            equipmentRepository.findById(UUID.fromString(e)).ifPresent(result::add)
        );
        return result;
    }
}
