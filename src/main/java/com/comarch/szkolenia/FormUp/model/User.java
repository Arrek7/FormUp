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
@Entity(name = "tuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String login;
    private String password;
    private String name;
    private String surname;
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10)")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Trening> trainings = new ArrayList<>();

    public enum Role {
        USER,
        ADMIN
    }
}
