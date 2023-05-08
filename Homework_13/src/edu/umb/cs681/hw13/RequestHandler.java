package edu.umb.cs681.hw13;

import java.nio.file.Path;
import java.util.Random;

public class RequestHandler implements Runnable {
    private AccessCounter accessCounter;
    private Path[] files;
    private volatile boolean flag;

    public RequestHandler(AccessCounter accessCounter, Path[] files) {
        this.accessCounter = accessCounter;
        this.files = files;
        this.flag = true;
    }

    @Override
    public void run() {
        Random rand = new Random();
        while (flag) {
            Path file = files[rand.nextInt(files.length)]; //Selects a random file
            accessCounter.increment(file); // Calls Increment method
            System.out.println("Access count for " + file + ": " + accessCounter.getCount(file)); //Calls getCount method
            try {
                Thread.sleep(5000); //Sleep for 5 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        flag = false;
    }
}
