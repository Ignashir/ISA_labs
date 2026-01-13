package com.example.lab_4;

import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class ChildService {

    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;

    public ChildService(ChildRepository childRepository,
                        ParentRepository parentRepository) {
        this.childRepository = childRepository;
        this.parentRepository = parentRepository;
    }

    public Child create(UUID parentId, String name) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new NoSuchElementException("Parent not found"));

        Child child = new Child(UUID.randomUUID(), name, parent);
        return childRepository.save(child);
    }

    public List<Child> findByParent(UUID parentId) {
        if (!parentRepository.existsById(parentId)) {
            throw new NoSuchElementException("Parent not found");
        }
        return childRepository.findByParentId(parentId);
    }

    public Child getOne(UUID childId) {
        if (!childRepository.existsById(childId)) {
            throw new NoSuchElementException("Child not found");
        }
        return childRepository.findById(childId).get();
    }

    public Child update(UUID childId, String name) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new NoSuchElementException("Parent not found"));
        child.setName(name);
        return childRepository.save(child);
    }

    public void delete(UUID childId) {
        if (!childRepository.existsById(childId)) {
            throw new NoSuchElementException("Child not found");
        }
        childRepository.deleteById(childId);
    }
}