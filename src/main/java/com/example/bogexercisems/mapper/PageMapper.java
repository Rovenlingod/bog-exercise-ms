package com.example.bogexercisems.mapper;

import com.example.bogexercisems.domain.Equipment;
import com.example.bogexercisems.dto.EquipmentDTO;
import com.example.bogexercisems.dto.PageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageMapper {
    public <T> PageDTO<T> toDto(Page<T> page) {
        PageDTO<T> result = new PageDTO<>();
        result.setContent(page.toList());
        result.setTotalPages(page.getTotalPages());
        result.setTotalElements(page.getTotalElements());
        result.setCurrentPage(page.getNumber());
        return result;
    }
}
