package com.example.lab_4;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/parents/{parentId}/children")
public class ChildController {

    private final ParentService parentService;
    private final ChildService childService;

    public ChildController(ParentService parentService, ChildService childService) {
        this.parentService = parentService;
        this.childService = childService;
    }

    // CREATE CHILD
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChildReadDTO create(
            @PathVariable UUID parentId,
            @RequestBody ChildCreateUpdateDTO dto
    ) {
        Parent parent = parentService.get(parentId);
        if (parent == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent not found");

        Child child = childService.create(parent.getId(), dto.name());

        return ChildReadDTO.fromChild(child);
    }

    // GET CHILDREN OF PARENT
    @GetMapping
    public List<ChildCollectionDTO> getChildren(@PathVariable UUID parentId) {
        Parent parent = parentService.get(parentId);
        if (parent == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        if (parent.getChildren().isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);

        return parent.getChildren().stream()
                .map(ChildCollectionDTO::fromChild)
                .toList();
    }

    @GetMapping("/{childId}")
    public ChildReadDTO getOne(@PathVariable String parentId, @PathVariable UUID childId) {
        return ChildReadDTO.fromChild(childService.getOne(childId));
    }

    @PutMapping("/{childId}")
    public ChildReadDTO update(@PathVariable UUID parentId, @RequestBody ChildCreateUpdateDTO dto, @PathVariable UUID childId) {
        Child child = childService.getOne(childId);
        if  (child == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        child = childService.update(child.getId(), dto.name());
        return ChildReadDTO.fromChild(child);
    }

    // DELETE CHILD
    @DeleteMapping("/{childId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String parentId, @PathVariable UUID childId) {
        childService.delete(childId);
    }
}

