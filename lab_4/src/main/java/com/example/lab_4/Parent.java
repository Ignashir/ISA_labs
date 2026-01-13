package com.example.lab_4;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "parents")
public class Parent {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Child> children = new ArrayList<>();

    public Parent(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
