package org.example;

import org.example.FileNode;
import org.example.FileNodeComparator;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        FileNode root = new FileNode("~", true, 1000, null);

        FileNode etc = new FileNode("etc", true, 40, null);

        FileNode fstab = new FileNode("fstab", false, 20, null);
        FileNode hosts = new FileNode("hosts", false, 15, null);
        FileNode paths = new FileNode("paths", false, 14, null);

        FileNode usr = new FileNode("usr", true, 30, null);

        FileNode local = new FileNode("local", false, 10, null);
        FileNode lib = new FileNode("lib", false, 11, null);

        FileNode ssh = new FileNode("ssh", false, 25, null);
        FileNode cores = new FileNode("cores", false, 20, null);


        // no argument or --sorting_type=0
        if (args.length == 0 || args[0].substring("--sorting_type=".length()).equals("0")){
            System.out.println("No sorting chosen");
            etc.setChildren(new HashSet<>(Arrays.asList(fstab, hosts, paths)));
            root.setChildren(new HashSet<>(Arrays.asList(etc)));
            usr.setChildren(new HashSet<>(Arrays.asList(local, lib)));

            Set<FileNode> fileNodes = new HashSet<>(Arrays.asList(root, usr, ssh, cores));
            Map<FileNode, Integer> map = new HashMap<>();
            for  (FileNode fileNode : fileNodes) {
                FileNode.displayChildren(fileNode, 0);
                FileNode.fillMap(map, fileNode);
            }

            System.out.println("Map");
            for(Map.Entry<FileNode, Integer> entry : map.entrySet()){
                System.out.println(entry.getKey().getName() + " = " + entry.getValue());
            }
        }
        else if (args[0].substring("--sorting_type=".length()).equals("1")){
            System.out.println("Sorting with natural order");

            etc.setChildren(new TreeSet<>(Arrays.asList(fstab, hosts, paths)));
            root.setChildren(new TreeSet<>(Arrays.asList(etc)));
            usr.setChildren(new TreeSet<>(Arrays.asList(local, lib)));

            Set<FileNode> fileNodes = new TreeSet<>(Arrays.asList(root, usr, ssh, cores));
            Map<FileNode, Integer> map = new TreeMap<>(new FileNodeComparator());
            for  (FileNode fileNode : fileNodes) {
                FileNode.displayChildren(fileNode, 0);
                FileNode.fillMap(map, fileNode);
            }

            System.out.println("Map");
            for(Map.Entry<FileNode, Integer> entry : map.entrySet()){
                System.out.println(entry.getKey().getName() + " = " + entry.getValue());
            }
        }
        else if (args[0].substring("--sorting_type=".length()).equals("2")){
            System.out.println("Sorting with custom order");
            Set<FileNode> etcChildren = new TreeSet<>(new FileNodeComparator());
            etcChildren.add(fstab);
            etcChildren.add(hosts);
            etcChildren.add(paths);
            etc.setChildren(etcChildren);
            Set<FileNode> rootChildren = new TreeSet<>(new FileNodeComparator());
            rootChildren.add(etc);
            root.setChildren(rootChildren);
            Set<FileNode> usrChildren = new TreeSet<>(new FileNodeComparator());
            usrChildren.add(local);
            usrChildren.add(lib);
            usr.setChildren(usrChildren);

            Set<FileNode> fileNodes = new TreeSet<>(new FileNodeComparator());
            fileNodes.add(root);
            fileNodes.add(usr);
            fileNodes.add(ssh);
            fileNodes.add(cores);

            Map<FileNode, Integer> map = new TreeMap<>(new FileNodeComparator());
            for  (FileNode fileNode : fileNodes) {
                FileNode.displayChildren(fileNode, 0);
                FileNode.fillMap(map, fileNode);
            }

            System.out.println("Map");
            for(Map.Entry<FileNode, Integer> entry : map.entrySet()){
                System.out.println(entry.getKey().getName() + " = " + entry.getValue());
            }
        }
        else {
            System.out.println("There is no such sorting type");
        }
    }
}

//Sorting with custom order
//        -File[name='cores',size=20]
//        -File[name='ssh',size=25]
//        -Folder[name='usr',size=30]
//        --File[name='local',size=10]
//        --File[name='lib',size=11]
//        -Folder[name='~',size=1000]
//        --Folder[name='etc',size=40]
//        ---File[name='paths',size=14]
//        ---File[name='hosts',size=15]
//        ---File[name='fstab',size=20]