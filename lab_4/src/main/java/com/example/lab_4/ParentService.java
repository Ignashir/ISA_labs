package com.example.lab_4;

import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class ParentService {
    private final ParentRepository repository;

    public ParentService(ParentRepository repository) {
        this.repository = repository;
    }

    public List<Parent> findAll() {
        return repository.findAll();
    }

    public Parent findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public void save(Parent parent) {
        repository.save(parent);
    }

    public void delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
        else{
            System.out.println("Parent with id " + id + " not found");
        }
    }
}