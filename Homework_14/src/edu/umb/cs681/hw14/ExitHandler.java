package edu.umb.cs681.hw14;

class ExitHandler implements Runnable {
    private AdmissionMonitor monitor;
    private volatile boolean flag;

    public ExitHandler(AdmissionMonitor monitor) {
        this.monitor = monitor;
        this.flag = true;
    }

    public void run() {
        while (flag)  {
            try {
                monitor.exit();
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
