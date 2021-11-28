package com.example.bogexercisems.mapper;

import com.example.bogexercisems.domain.Equipment;
import com.example.bogexercisems.domain.Exercise;
import com.example.bogexercisems.dto.ExerciseCreationRequest;
import com.example.bogexercisems.dto.ExerciseDTO;
import com.example.bogexercisems.repository.EquipmentRepository;
import com.example.bogexercisems.service.MediaStorageService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Mapper(componentModel="spring", uses = {EquipmentMapper.class})
public abstract class ExerciseMapper {

    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    protected MediaStorageService mediaStorageService;

    @Mappings({
            @Mapping(source = "title", target = "title"),
            @Mapping(target = "creatorId", expression = "java(java.util.UUID.fromString(exerciseCreationRequest.getCreatorId()))"),
            @Mapping(target = "equipment", expression = "java(this.map(exerciseCreationRequest.getEquipmentIds()))")
    })
    public abstract Exercise exerciseCreationRequestToExercise(ExerciseCreationRequest exerciseCreationRequest);

    @Mappings({
            @Mapping(source = "exercise.equipment", target = "equipmentDTOS"),
            @Mapping(target = "creatorId", expression = "java(exercise.getCreatorId().toString())"),
            @Mapping(target = "id", expression = "java(exercise.getId().toString())"),
            @Mapping(target = "mediaFileUrl", expression = "java(mediaStorageService.getFileUrlByName(exercise.getMediaFile()))")
    })
    public abstract ExerciseDTO exerciseToExerciseDTO(Exercise exercise);

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

//    public String getFileUrl(String fileName) {
//        return mediaStorageService.getFileUrlByName(fileName);
//    }
}
