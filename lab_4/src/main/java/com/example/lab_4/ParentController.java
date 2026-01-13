package com.example.lab_4;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/parents")
public class ParentController {

    private final ParentService service;

    public ParentController(ParentService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParentReadDTO create(@RequestBody ParentCreateUpdateDTO dto) {
        Parent parent = service.create(dto.name());
        return ParentReadDTO.fromParent(parent);
    }

    // GET ALL
    @GetMapping
    public List<ParentCollectionDTO> getAll() {
        return service.getAll().stream()
                .map(ParentCollectionDTO::fromParent)
                .toList();
    }

    // GET ONE
    @GetMapping("/{id}")
    public ParentReadDTO getOne(@PathVariable UUID id) {
        Parent parent = service.get(id);
        if (parent == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return ParentReadDTO.fromParent(parent);
    }

    @PutMapping("/{id}")
    public ParentReadDTO updateOne(@PathVariable UUID id, @RequestBody ParentCreateUpdateDTO dto) {
        Parent parent = service.get(id);
        if (parent == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        parent = service.update(parent.getId(), dto.name()); // Ask if I should pass the ParentCreateUpdateDTO to the method or to keep it that way
        return ParentReadDTO.fromParent(parent);
    }

    // DELETE (children auto-deleted)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        Parent parent = service.get(id);
        if (parent == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        service.delete(id);
    }
}