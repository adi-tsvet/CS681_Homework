package edu.umb.cs681.hw14;

public class MainApplication {

    public static void main(String[] args) throws InterruptedException {
        AdmissionMonitor monitor = new AdmissionMonitor();
        EntranceHandler[] entranceHandlers = new EntranceHandler[10];
        ExitHandler[] exitHandlers = new ExitHandler[10];
        StatsHandler[] statsHandlers = new StatsHandler[10];
        Thread[] threads = new Thread[30];

        // Starting 10 threads for Entrance and Exit with Stats
        for (int i = 0; i < 10; i++) {
            entranceHandlers[i] = new EntranceHandler(monitor);
            exitHandlers[i] = new ExitHandler(monitor);
            statsHandlers[i] = new StatsHandler(monitor);
            threads[i] = new Thread(entranceHandlers[i]);
            threads[i + 10] = new Thread(exitHandlers[i]);
            threads[i + 20] = new Thread(statsHandlers[i]);
        }

        // Start all the threads
        for (Thread thread : threads) {
            System.out.println("Thread #"+thread.getId() + " Started !");
            thread.start();
        }

        // 2-Step Explicit Thread Termination via flag and interrupt
        for (EntranceHandler handler : entranceHandlers) {
            handler.setDone();
        }
        for (ExitHandler handler : exitHandlers) {
            handler.setDone();
        }
        for (StatsHandler handler : statsHandlers) {
            handler.setDone();
        }

        // Interrupt all the threads
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}
