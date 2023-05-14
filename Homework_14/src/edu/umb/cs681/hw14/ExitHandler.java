package edu.umb.cs681.hw14;

import java.time.Duration;

class ExitHandler implements Runnable {
    private AdmissionMonitor monitor;
    private volatile boolean done = false;

    public ExitHandler(AdmissionMonitor monitor) {
        this.monitor = monitor;
    }

    public void run() {
        while (!done) {
            monitor.exit();
        }
        if(done){
            System.out.println("Terminated Exit Thread...");
        }
    }

    public void setDone() {
        done = true;
    }
}
