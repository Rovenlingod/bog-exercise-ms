package com.example.bogexercisems;

import com.example.bogexercisems.domain.Exercise;
//import com.example.bogexercisems.repository.DataAccessHelper;
import com.example.bogexercisems.repository.ExerciseRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTests {
/*
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private DataAccessHelper helper;

    private Exercise firstExercise;
    private Exercise secondExercise;
    private Exercise thirdExercise;


    @Before
    public void setUp() {
        firstExercise = exerciseRepository.save(new Exercise());
        secondExercise = exerciseRepository.save(new Exercise());
        thirdExercise = exerciseRepository.save(new Exercise());
    }

//    @After
//    public void tearDown() {
//        exerciseRepository.delete(firstExercise);
//        exerciseRepository.delete(secondExercise);
//        exerciseRepository.delete(thirdExercise);
//    }

    @Test
    public void testFindAllByIdIn() {
//        List<Exercise> expectedResult = Arrays.asList(firstExercise, secondExercise);
//        List<Exercise> actualResult;
//        helper.doInTransaction(e -> {
//            List<UUID> ids = Arrays.asList(firstExercise.getId(), secondExercise.getId());
//            actualResult = exerciseRepository.findAllByIdIn(ids).get();
//            System.out.println(expectedResult.size());
//            assertTrue(expectedResult.size() == actualResult.size() && expectedResult.containsAll(actualResult) && actualResult.containsAll(expectedResult));
//            //Assert.assertEquals(expectedResult, actualResult);
//            //assertThat(actualResult, containsInAnyOrder(expectedResult.toArray()));
//        });
//        helper.doInTransaction(e -> {
//            List<Exercise> expectedResult = Arrays.asList(firstExercise, secondExercise);
//            List<UUID> ids = Arrays.asList(firstExercise.getId(), secondExercise.getId(), UUID.randomUUID());
//            assertThat("List equality without order",
//                    exerciseRepository.findAllByIdIn(ids).get(), containsInAnyOrder(expectedResult));
//        });
    }*/
}
