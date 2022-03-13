package com.example.bogexercisems.mapper;

import com.example.bogexercisems.domain.Equipment;
import com.example.bogexercisems.dto.EquipmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel="spring")
public abstract class EquipmentMapper {
    @Mappings({
            @Mapping(target = "id", expression = "java(equipment.getId().toString())"),
            @Mapping(source = "title", target = "title")
    })
    public abstract EquipmentDTO toDto(Equipment equipment);
}
