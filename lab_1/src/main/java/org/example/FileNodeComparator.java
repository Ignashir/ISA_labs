package org.example;

import java.util.Comparator;

public class FileNodeComparator implements Comparator<FileNode> {
    @Override
    public int compare(FileNode o1, FileNode o2) {
        if (o1.getSize() == o2.getSize()) {
            return o1.getName().compareTo(o2.getName());
        }
        if (o1.getSize() > o2.getSize()) {
            return 1;
        }
        return -1;
    }
}
