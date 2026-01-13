//package com.example.lab_4;
//
//import org.springframework.stereotype.Component;
//import jakarta.annotation.PostConstruct;
//import java.util.*;
//
//
//@Component
//public class DataInitializer {
//    private final ParentService parentService;
//    private final ChildService childService;
//
//    public DataInitializer(ParentService parentService, ChildService childService) {
//        this.parentService = parentService;
//        this.childService = childService;
//    }
//
//    @PostConstruct
//    public void init() {
//        Parent p = new Parent(UUID.randomUUID(), "Parent-A");
//        parentService.save(p);
//
//        childService.save(new Child(UUID.randomUUID(), "Child-1", p));
//        childService.save(new Child(UUID.randomUUID(), "Child-2", p));
//
//        Parent p2 = new Parent(UUID.randomUUID(), "Parent-B");
//        parentService.save(p2);
//        childService.save(new Child(UUID.randomUUID(), "Child-3", p2));
//        childService.save(new Child(UUID.randomUUID(), "Child-4", p2));
//        childService.save(new Child(UUID.randomUUID(), "Child-5", p2));
//
//        Parent p3 = new Parent(UUID.randomUUID(), "Parent-C");
//        parentService.save(p3);
//        childService.save(new Child(UUID.randomUUID(), "Child-6", p3));
//        childService.save(new Child(UUID.randomUUID(), "Child-7", p3));
//        childService.save(new Child(UUID.randomUUID(), "Child-8", p3));
//    }
//}