package com.example.lab_4;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.*;


@Component
public class CommandLineInterface implements CommandLineRunner {
    private final ParentService parentService;
    private final ChildService childService;

    public CommandLineInterface(ParentService parentService, ChildService childService) {
        this.parentService = parentService;
        this.childService = childService;
    }

    private void listCommands() {
        System.out.println("==============================================");
        System.out.println("help            -> List all possible commands");
        System.out.println("list-children   -> List all children");
        System.out.println("list-parents    -> List all parents");
        System.out.println("add-child       -> Add child to parent");
        System.out.println("remove-parent   -> Remove parent from children");
        System.out.println("remove-child    -> Remove child from parent");
        System.out.println("exit            -> Exit");
        System.out.println("==============================================");
    }

    public void addChild(Scanner sc){
        System.out.println("Enter child name");
        String name = sc.next();
        System.out.println("Enter parent ID");
        UUID id = UUID.fromString(sc.next());
        Parent p = parentService.findById(id);
        childService.save(new Child(UUID.randomUUID(), name, p));
    }

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);
        UUID parentId;
        UUID childId;
        listCommands();
        loop: while (true) {
            System.out.print("Command: ");
            String cmd = sc.nextLine();

            switch (cmd) {
                case "help":
                    listCommands();
                    break;
                case "list-children":
                    childService.findAll().forEach(System.out::println);
                    break;
                case "list-parents":
                    parentService.findAll().forEach(System.out::println);
                    break;
                case "add-child":
                    addChild(sc);
                    break;
                case "remove-parent":
                    parentId = UUID.fromString(sc.nextLine());
                    parentService.delete(parentId);
                    break;
                case "remove-child":
                    childId = UUID.fromString(sc.nextLine());
                    childService.delete(childId);
                    break;
                case "exit":
                    sc.close();
                    break loop;
                default:
                    System.out.println("Invalid command");
            }
        }
    }
}
