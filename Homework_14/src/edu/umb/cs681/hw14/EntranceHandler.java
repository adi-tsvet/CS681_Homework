package edu.umb.cs681.hw14;

class EntranceHandler implements Runnable {
    private AdmissionMonitor monitor;
    private volatile boolean flag;

    public EntranceHandler(AdmissionMonitor monitor) {
        this.monitor = monitor;
        this.flag = true;
    }

    public void run() {
        while (flag)  {
            try {
                monitor.enter();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        flag = false;
    }

}