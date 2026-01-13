package com.example.lab_4;

import java.util.UUID;

public record ChildCollectionDTO(UUID id, String name) {
    public static ChildCollectionDTO fromChild(Child child) {
        return new ChildCollectionDTO(
                child.getId(),
                child.getName()
        );
    }
}

