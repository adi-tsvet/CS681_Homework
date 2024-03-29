package edu.umb.cs681.hw11;

import java.nio.file.Path;
import java.util.Random;

public class RequestHandler implements Runnable {
    private AccessCounter accessCounter;
    private Path[] files;
    private volatile boolean done = false;

    public void setDone() {
        done = true;
    }
    public RequestHandler(AccessCounter accessCounter, Path[] files) {
        this.accessCounter = accessCounter;
        this.files = files;
    }

    @Override
    public void run() {
        while (!done) {
            Random rand = new Random();
            Path file = files[rand.nextInt(files.length)]; //Selects a random file
            accessCounter.increment(file); // Calls Increment method
            //Calls getCount method
            System.out.println("Access count for " + file + ": " + accessCounter.getCount(file));
            // Make the thread sleep and then interrupt will be called to terminate.
            try {
                Thread.sleep(1000);
            }catch(InterruptedException e) {
                System.out.println("Thread #"+Thread.currentThread().getId() + " Interrupted");
                break;
            }
        }
    }
}
