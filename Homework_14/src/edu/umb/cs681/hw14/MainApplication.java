package edu.umb.cs681.hw14;

public class MainApplication {

    public static void main(String[] args) throws InterruptedException {
        AdmissionMonitor monitor = new AdmissionMonitor();
        EntranceHandler[] entranceHandlers = new EntranceHandler[10];
        ExitHandler[] exitHandlers = new ExitHandler[10];
        StatsHandler statsHandler = new StatsHandler(monitor);

        // Start entrance threads
        for (int i = 0; i < entranceHandlers.length; i++) {
            entranceHandlers[i] = new EntranceHandler(monitor);
            new Thread(entranceHandlers[i]).start();
        }

        // Start exit threads
        for (int i = 0; i < exitHandlers.length; i++) {
            exitHandlers[i] = new ExitHandler(monitor);
            new Thread(exitHandlers[i]).start();
        }

        // Start stats thread
        new Thread(statsHandler).start();


        // Stop all threads
        for (EntranceHandler handler : entranceHandlers) {
            handler.stop();
        }
        for (ExitHandler handler : exitHandlers) {
            handler.stop();
        }
        statsHandler.stop();

        // Interrupt all handlers to ensure they stop immediately
        for (EntranceHandler handler : entranceHandlers) {
            new Thread(handler).interrupt();
        }
        for (ExitHandler handler : exitHandlers) {
            new Thread(handler).interrupt();
        }
        new Thread(statsHandler).interrupt();


    }
}
