package com.comarch.szkolenia.FormUp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "ttrening")
public class Trening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "trening", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Exercise> exercises = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
