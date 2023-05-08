package edu.umb.cs681.hw10;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class FileSystem {

    private LinkedList<Directory> directory = new LinkedList<Directory>();
    private static FileSystem fileSystem = null;
    private static ReentrantLock lock = new ReentrantLock();

    private FileSystem() {}

    public static FileSystem getFileSystem() {
        lock.lock();
        try {
            if (fileSystem == null) {
                fileSystem = new FileSystem();
            }
        } finally {
            lock.unlock();
        }
        return fileSystem;
    }

    public LinkedList<Directory> getRootDirectory() {

            return this.directory;


    }
    public Directory getRoot() {

            if (directory.isEmpty()) {
                return null;
            } else {
                return directory.getFirst();
            }


    }
    public void appendRootDirectory(Directory root) {

            directory.add(root);

    }
}
