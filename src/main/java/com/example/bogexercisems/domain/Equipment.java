package com.example.bogexercisems.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "equipment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {

    @Id
    @Column(name = "equipment_id")
    @GeneratedValue(generator = "UUID")
    @Type(type="uuid-char")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "title")
    private String title;

    @ManyToMany(mappedBy = "equipment")
    private List<Exercise> exercises;
}
