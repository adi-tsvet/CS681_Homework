package edu.umb.cs681.hw10;

import edu.umb.cs681.hw11.RequestHandler;

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

    public static void main(String[] args) throws InterruptedException{

        // Initialize the directory structure
        init();

        // Create a list of extra threads
        FileSystemRunnable[] runnables = new FileSystemRunnable[10];

        for (int i = 0; i < runnables.length; i++) {
            runnables[i] = new FileSystemRunnable();
            new Thread(runnables[i]).start();
        }
        //2-step termination by using Explicit Thread Termination with a Flag
        for (FileSystemRunnable handler : runnables) {
            handler.setDone();
        }

    }

}
