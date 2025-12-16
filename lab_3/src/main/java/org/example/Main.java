package org.example;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) throws Exception {
        List<Parent> parents = new ArrayList<>();
        // Create collections
        for (int p = 3; p >= 1; p--) {
            Parent parent = new Parent.Builder()
                    .id(p)
                    .name("Parent-" + p)
                    .build();
            for (int c = 1; c <= 3; c++) {
                Child child = new Child.Builder()
                        .id(p * 10 + c)
                        .name("Child-" + p + "-" + c)
                        .build();
                parent.addChild(child);
                // Present the Set working
//                parent.addChild(child);
            }
            parents.add(parent);
        }
        // Print collections
        parents.forEach(parent -> {
            System.out.println(parent);
            parent.getChildren().forEach(child -> System.out.println(" " + child));
        });

        // Transform each element to map -> iterate through children (sleep) -> into Set
        ForkJoinPool pool = new ForkJoinPool(4);
        Set<Child> allChildren = pool.submit(() ->
                parents.stream()
                        .flatMap(p -> p.getChildren().stream())
                        .map(child -> {
                            try { Thread.sleep(20); } catch (InterruptedException ignored) {}
                            return child;
                        })
                        .collect(Collectors.toSet())
        ).get();

        System.out.println("All children collected:");
        allChildren.stream().forEach(System.out::println);

        pool.shutdown();

        System.out.println("Filtered + sorted children:");
        allChildren.stream()
                .filter(c -> c.getId() % 2 == 0)
                .sorted(Comparator.comparing(Child::getName))
                .forEach(System.out::println);

        System.out.println("Child DTOs:");
        List<ChildDTO> dtos = allChildren.stream()
                .map(c -> new ChildDTO.Builder()
                        .id(c.getId())
                        .name(c.getName())
                        .parentId(c.getParent().getId())
                        .build())
                .sorted()
                .toList();

        dtos.forEach(System.out::println);

        // Writing DTO's to file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("parents.bin"))) {
            out.writeObject(parents);
        } catch (IOException e) {}

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("parents.bin"))){
            List<Parent> loaded = (List<Parent>) in.readObject();

            System.out.println("Loaded from file:");
            loaded.forEach(parent -> {
                System.out.println(parent);
                parent.getChildren().forEach(child -> System.out.println(" " + child));
            });

//            ForkJoinPool parallelPool = new ForkJoinPool(3);
            ForkJoinPool parallelPool = new ForkJoinPool(2);

            System.out.println("Parallel processing:");
            parallelPool.submit(() ->
                    loaded.parallelStream().forEach(parent -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Processing parent " + parent.getId() +
                                " on " + Thread.currentThread().getName());
                        parent.getChildren().forEach(System.out::println);
                    })
            ).get();

            parallelPool.shutdown();
        } catch (IOException | ClassNotFoundException e) {}
    }
}