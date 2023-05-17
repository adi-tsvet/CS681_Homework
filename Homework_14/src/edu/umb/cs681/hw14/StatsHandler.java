package edu.umb.cs681.hw14;

import java.time.Duration;

class StatsHandler implements Runnable {
    private AdmissionMonitor monitor;
    private volatile boolean done = false;

    public StatsHandler(AdmissionMonitor monitor) {
        this.monitor = monitor;
    }

    public void setDone() {
        done = true;
    }

    public void run() {
        while (!done) {
            monitor.countCurrentVisitors();
            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException e) {
                System.out.println("Thread #"+Thread.currentThread().getId() + " Interrupted");
                break;
            }
        }
    }
}