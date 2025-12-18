package com.example.lab_4;

import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class ChildService {
    private final ChildRepository repository;

    public ChildService(ChildRepository repository) {
        this.repository = repository;
    }

    public List<Child> findAll() {
        return repository.findAll();
    }

    public Child save(Child child) {
        return repository.save(child);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}