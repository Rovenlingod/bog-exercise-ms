package com.example.bogexercisems;

import com.example.bogexercisems.domain.Equipment;
import com.example.bogexercisems.domain.Exercise;
import com.example.bogexercisems.dto.ExerciseCreationRequest;
import com.example.bogexercisems.enums.MuscleType;
import com.example.bogexercisems.mapper.ExerciseMapper;
import com.example.bogexercisems.repository.EquipmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTests {

    @Autowired
    private ExerciseMapper entityMapper;
    @MockBean
    private EquipmentRepository equipmentRepository;

    @Test
    public void mapExerciseCreationRequestToExercise() {
        //given
        ExerciseCreationRequest request = new ExerciseCreationRequest();
        request.setTitle("testTitle");
        request.setIsPublic(true);
        request.setMediaFile(new MockMultipartFile("/", new byte[1]));
        request.setCountdownInSeconds(1L);
        request.setMuscleType(MuscleType.ABS);
        //request.setCreatorId(UUID.randomUUID().toString());
        List<String> equipmentIds = new ArrayList<>();
        equipmentIds.add("8adc58fa-5057-11ec-bf63-0242ac130002");
        request.setEquipmentIds(equipmentIds);

        Equipment equipment = new Equipment();
        equipment.setId(UUID.fromString("8adc58fa-5057-11ec-bf63-0242ac130002"));
        equipment.setTitle("testExerciseTitle");
        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(equipment);
        Mockito.when(equipmentRepository.findById(UUID.fromString("8adc58fa-5057-11ec-bf63-0242ac130002"))).thenReturn(Optional.of(equipment));

        //when
        Exercise exercise = entityMapper.exerciseCreationRequestToExercise(request);

        //then
        assertNotNull(exercise);
        assertEquals(exercise.getTitle(), "testTitle");
        assertEquals(exercise.getIsPublic(), true);
        assertEquals(exercise.getMediaFile(), null);
        assertEquals(exercise.getCountdownInSeconds(), Long.valueOf(1L));
        assertEquals(exercise.getMuscleType(), MuscleType.ABS);
        assertEquals(exercise.getEquipment(), equipmentList);
    }
}
