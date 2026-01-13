package com.example.lab_4;

import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class ParentService {

    private final ParentRepository repository;

    public ParentService(ParentRepository repository) {
        this.repository = repository;
    }

    public Parent get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Parent not found"));
    }

    public Parent create(String name) {
        Parent parent = new Parent(UUID.randomUUID(), name);
        return repository.save(parent);
    }

    public Parent update(UUID id, String name) {
        Parent parent = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Parent not found"));
        parent.setName(name);
        return repository.save(parent);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Parent not found");
        }
        repository.deleteById(id);
    }

    public List<Parent> getAll(){
        return repository.findAll();
    }
}