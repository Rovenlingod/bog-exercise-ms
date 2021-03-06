package com.example.bogexercisems.controller;

import com.example.bogexercisems.dto.ExerciseCreationRequest;
import com.example.bogexercisems.dto.ExerciseDTO;
import com.example.bogexercisems.dto.PageDTO;
import com.example.bogexercisems.service.ExerciseService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.Pattern;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/exercise")
public class ExerciseController {

    private ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<Object> saveExercise(ExerciseCreationRequest exerciseCreationRequest) {
        UUID id = exerciseService.createExercise(exerciseCreationRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id.toString()).toUri();
        return ResponseEntity.created(uri).build();
    }

/*    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDTO> getExerciseById(@PathVariable("id") @Pattern(regexp = "^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$", message = "invalid uuid format") String id) {
        return ResponseEntity.ok().body(exerciseService.getExerciseById(id));
    }*/

    @GetMapping("/{ids}")
    public ResponseEntity<List<ExerciseDTO>> getExercisesByIds(@PathVariable("ids") List<@Pattern(regexp = "^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$", message = "some of the provided uuids have invalid format") String> ids) {
        return ResponseEntity.ok().body(exerciseService.getExercisesByIds(ids));
    }

    @GetMapping
    public ResponseEntity<PageDTO<ExerciseDTO>> getAllPageable(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo, @RequestParam(value = "pageSize", defaultValue = "3") int pageSize) {
        return ResponseEntity.ok().body(exerciseService.findAll(pageNo, pageSize));
    }
}
