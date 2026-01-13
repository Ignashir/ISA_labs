package com.example.lab_4;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "children")
public class Child {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    private Parent parent;
}
