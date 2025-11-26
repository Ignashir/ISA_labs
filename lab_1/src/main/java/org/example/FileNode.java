package org.example;

import lombok.*;

import java.io.File;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FileNode implements Comparable<FileNode>{
    private String name;
    private boolean isDirectory;
    private long size;

    private Set<FileNode> children;

    public static void displayChildren(FileNode fileNode, int indentation) {
        String indent = " ".repeat(indentation) + "-".repeat(indentation + 1);
        System.out.println(indent + fileNode.printInfo());
        if (fileNode.getChildren() == null){
            return;
        }
        for (FileNode child : fileNode.getChildren()) {
            displayChildren(child, indentation + 1);
        }
    }

    public static int fillMap(Map<FileNode, Integer> map, FileNode fileNode) {
        if (fileNode.getChildren() == null) {
            map.put(fileNode, 0);
            return 1;
        }
        int sumOfDescendants = 0;
        for  (FileNode child : fileNode.getChildren()) {
            sumOfDescendants += fillMap(map, child);
        }
        map.put(fileNode, sumOfDescendants);
        return sumOfDescendants + 1;
    }

    public String printInfo(){
        String output = "";
        if (this.isDirectory()){
            output += "Folder[";
        }
        else {
            output += "File[";
        }
        output += "name='" + this.getName() + "',";
        output += "size=" + this.getSize() + "]";
        return output;
    }

    @Override
    public  int compareTo(FileNode other) {
        if (this.name.equals(other.getName())){
            if (this.size > other.getSize()) return 1;
            else if (this.size < other.getSize()) return -1;
            else return 0;
        }
        return this.name.compareTo(other.name);
    }
}
