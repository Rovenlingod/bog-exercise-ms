package com.example.bogexercisems.mapper;

import com.example.bogexercisems.domain.Equipment;
import com.example.bogexercisems.dto.EquipmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class EquipmentMapper {
    public abstract EquipmentDTO toDto(Equipment equipment);
}
