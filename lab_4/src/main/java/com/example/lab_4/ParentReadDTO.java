package com.example.lab_4;

import java.util.List;
import java.util.UUID;


public record ParentReadDTO(UUID id, String name, List<ChildCollectionDTO> children) {
    public static ParentReadDTO fromParent(Parent parent) {
        return new ParentReadDTO(
                parent.getId(),
                parent.getName(),
                parent.getChildren().stream()
                        .map(ChildCollectionDTO::fromChild)
                        .toList()
        );
    }
}