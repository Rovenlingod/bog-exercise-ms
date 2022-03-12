package com.example.bogexercisems.domain;

import com.example.bogexercisems.enums.MuscleType;
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
@Table(name = "exercise")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {

    @Id
    @Column(name = "exercise_id")
    @Type(type="uuid-char")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "media_file")
    private String mediaFile;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "countdown")
    private Long countdownInSeconds;

    @Enumerated(EnumType.STRING)
    @Column(name = "muscle_type")
    private MuscleType muscleType;

    @Column(name = "creator_id")
    private UUID creatorId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Exercise_Equipment",
            joinColumns = { @JoinColumn(name = "exercise_id") },
            inverseJoinColumns = { @JoinColumn(name = "equipment_id") }
    )
    private List<Equipment> equipment;
}
