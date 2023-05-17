package edu.umb.cs681.hw11;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MainApplication {
    public static void main(String[] args) throws InterruptedException {

        Path[] files = { Paths.get("htmlFiles/a.html"), Paths.get("htmlFiles/b.html"),
                Paths.get("htmlFiles/c.html"), Paths.get("htmlFiles/d.html")};

        RequestHandler[] handlers = new RequestHandler[10];
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            AccessCounter accessCounter = AccessCounter.getInstance();
            handlers[i] = new RequestHandler(accessCounter, files);
            threads[i] = new Thread(handlers[i]);
            threads[i].start();
            System.out.println("Thread #"+threads[i].getId() + " started !");

        }

        //2-step termination by using Explicit with Flag and Interruption
        for (RequestHandler handler : handlers) {
            handler.setDone();
        }
        for (Thread thread : threads) {
            thread.interrupt();
        }

    }
}

