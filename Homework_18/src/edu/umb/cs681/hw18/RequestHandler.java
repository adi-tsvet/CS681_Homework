package edu.umb.cs681.hw18;

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
            Path file = files[rand.nextInt(files.length)];
            accessCounter.increment(file);
            System.out.println("Access count for " + file + ": " + accessCounter.getCount(file));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        flag = false;
    }
}
