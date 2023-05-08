package edu.umb.cs681.hw10;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class Directory extends FSElement {

    private LinkedList<FSElement> children;
    private LinkedList<Directory> subDirectory;
    private LinkedList<File> files;
    private LinkedList<Link> links;


    public Directory(Directory parent, String name, int size, LocalDateTime creationTime) {
        super(parent, name, 0, creationTime);
        this.children = new LinkedList<>();
        this.subDirectory = new LinkedList<>();
        this.files = new LinkedList<>();
        this.links = new LinkedList<>();
    }

    public LinkedList<FSElement> getChildren(){
        lock.lock();
        try {
            return this.children;
        } finally {
            lock.unlock();
        }
    }

    public void appendChild (FSElement child){
        lock.lock();
        try {
            this.children.add(child);
            child.setParent(this);
        } finally {
            lock.unlock();
        }
    }

    public int countChildren(){
        lock.lock();
        try {
            return this.children.size();
        } finally {
            lock.unlock();
        }
    }

    public LinkedList<Directory> getSubDirectories() {
        lock.lock();
        try {
            for (FSElement element : children) {
                if (element.isDirectory()) {
                    subDirectory.add((Directory) element);
                }
            }
            return subDirectory;
        } finally {
            lock.unlock();
        }
    }


    public LinkedList<File> getFiles() {
        lock.lock();
        try {
            for (FSElement element : children) {
                if (element.isFile()) {
                    files.add((File) element);
                }
            }
            return files;
        } finally {
            lock.unlock();
        }
    }

    public int getTotalSize() {
        lock.lock();
        try {
            int totalSize = 0;
            for (FSElement element : children) {
                if (element.isDirectory()) {
                    totalSize += ((Directory) element).getTotalSize();
                } else {
                    totalSize += element.getSize();
                }
            }
            return totalSize;
        } finally {
            lock.unlock();
        }
    }

    public boolean isDirectory() {
        return true;
    }

    public boolean isFile() {
        return false;
    }

    public boolean isLink() {
        return false;
    }

    public LinkedList<Link> getLinks(){
        lock.lock();
        try {
            for (FSElement fsElement : children) {
                if (fsElement.isLink()) {
                    Link link = (Link)fsElement;
                    links.add(link);
                }
            }
            return links;
        } finally {
            lock.unlock();
        }
    }
}
