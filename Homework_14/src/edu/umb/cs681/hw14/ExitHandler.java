package edu.umb.cs681.hw14;

import java.time.Duration;

class ExitHandler implements Runnable {
    private AdmissionMonitor monitor;
    private volatile boolean done = false;

    public void setDone() {
        done = true;
    }

    public ExitHandler(AdmissionMonitor monitor) {
        this.monitor = monitor;
    }

    public void run() {
        while (!done) {
            monitor.exit();
        }
        if(done){
            System.out.println("Stopped Exit Thread #" + Thread.currentThread().getId());
        }
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException e) {
            System.out.println("Thread #"+Thread.currentThread().getId() + " Interrupted");
        }

    }


}
