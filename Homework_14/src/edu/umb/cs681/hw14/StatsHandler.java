package edu.umb.cs681.hw14;

class StatsHandler implements Runnable {
    private AdmissionMonitor monitor;
    private volatile boolean flag;

    public StatsHandler(AdmissionMonitor monitor) {
        this.monitor = monitor;
        this.flag = true;
    }

    public void run() {
        while (flag)  {
            try {
                monitor.countCurrentVisitors();

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