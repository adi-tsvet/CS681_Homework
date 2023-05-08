package edu.umb.cs681.hw07;

import java.time.LocalDateTime;
import java.util.LinkedList;

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
        return this.children;
    }

    public void appendChild (FSElement child){
        this.children.add(child);
        child.setParent(this);
    }

    public int countChildren(){
        return this.children.size();
    }

    public LinkedList<Directory> getSubDirectories() {
        for (FSElement element : children) {
            if (element.isDirectory()) {
                subDirectory.add((Directory) element);
            }
        }
        return subDirectory;
    }

    public LinkedList<File> getFiles() {
        for (FSElement element : children) {
            if (element.isFile()) {
                files.add((File) element);
            }
        }
        return files;
    }

    public int getTotalSize() {
        int totalSize = 0;
        for (FSElement element : children) {
            if (element.isDirectory()) {
                totalSize += ((Directory) element).getTotalSize();
            } else {
                totalSize += element.getSize();
            }
        }
        return totalSize;
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

        for (FSElement fsElement : children) {
            if (fsElement.isLink()) {
                Link link = (Link)fsElement;
                links.add(link);
            }
        }
        return links;
    }
}
