package com.example.lab_4;

import java.util.UUID;

public record ChildReadDTO(UUID id, String name, UUID parentId) {
    public static ChildReadDTO fromChild(Child child) {
        return new ChildReadDTO(
                child.getId(),
                child.getName(),
                child.getParent().getId()
        );
    }
}