package edu.umb.cs681.hw10;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainApplication {

    //Tree Structure
    static private LocalDateTime now = LocalDateTime.now();
    static private LocalDateTime later = LocalDateTime.now();

    static Directory root = new Directory(null, "Root", 0, now);

    static Directory apps = new Directory(null, "Applications", 0, now);
    static File x = new File(null, "x", 1, now);

    static Directory bin = new Directory(null, "Bin", 0, now);
    static File y = new File(null, "y", 1, now);

    static Directory home = new Directory(null, "Home", 0, now);
    static Directory pictures = new Directory(null, "Pictures", 0, later);
    static File c = new File(null, "c", 1, now);
    static File a = new File(null, "a", 2, later);
    static File b = new File(null, "b", 2, later);

    static Link d = new Link(null, "d", 0, now, null);
    static Link e = new Link(null, "e", 0, later, null);



    public static void init() {

        root.appendChild(apps);
        root.appendChild(bin);
        root.appendChild(home);
        root.appendChild(d);
        root.appendChild(e);

        apps.appendChild(x);

        bin.appendChild(y);

        home.appendChild(pictures);
        home.appendChild(c);

        pictures.appendChild(a);
        pictures.appendChild(b);

        d.setTarget(pictures);
        e.setTarget(x);
    }

    public static void main(String[] args) {

        // Initialize the directory structure
        init();

        // Create a list of extra threads
        List<Thread> extraThreads = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                FileSystem fs = FileSystem.getFileSystem();
                fs.appendRootDirectory(root);
                LinkedList<Directory> rootDir = fs.getRootDirectory();
                List<FSElement> children = rootDir.get(0).getChildren();
                    for (FSElement child : children) {
                        if (child.isDirectory()) {
                            Directory dir = (Directory) child;
                            System.out.println("Accessing Directory: " + dir.getName());
                            System.out.println("Total Size: " + dir.getTotalSize());
                            List<FSElement> subDirsChild = dir.getChildren();
                            for (FSElement subchild : subDirsChild) {
                                if (subchild.isDirectory()) {
                                    Directory subdir = (Directory) subchild;
                                    System.out.println("Accessing Sub-Directory: " + subdir.getName());
                                    System.out.println("Total Size: " + subdir.getTotalSize());
                                }
                                if (subchild.isFile()) {
                                    File file = (File) subchild;
                                    System.out.println("Accessing File: " + file.getName());
                                    System.out.println("Size: " + file.getSize());
                                }
                            }
                        }

                        if (child.isLink()) {
                            Link link = (Link) child;
                            System.out.println("Accessing Link: " + link.getName());
                            System.out.println("Size: " + link.getSize());
                            System.out.println("Accessing its target: " + link.getTarget().getName());
                            System.out.println("Size: " + link.getTargetSize());

                        }
                    }
            });
            extraThreads.add(t);
            t.start();
        }

        // Wait for all extra threads to finish
        for (Thread t : extraThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Perform 2-step termination for the extra threads
        for (Thread t : extraThreads) {
            t.interrupt();
        }
        for (Thread t : extraThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
