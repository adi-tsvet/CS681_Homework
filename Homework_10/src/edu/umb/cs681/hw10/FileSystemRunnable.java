package edu.umb.cs681.hw10;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class FileSystemRunnable implements Runnable{
    private volatile boolean done = false;
    public void setDone() {
        done = true;

    }

    @Override
    public void run() {
        while(!done) {
            FileSystem fs = FileSystem.getFileSystem();
            fs.appendRootDirectory(MainApplication.root);
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
        }
    }
}
