package com.example.lab_4;

import java.util.UUID;

public record ParentCollectionDTO(UUID id, String name) {
    public static ParentCollectionDTO fromParent(Parent parent) {
        return new ParentCollectionDTO(
                parent.getId(),
                parent.getName()
        );
    }
}

