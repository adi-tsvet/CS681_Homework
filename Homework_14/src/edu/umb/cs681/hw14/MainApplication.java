package edu.umb.cs681.hw14;

public class MainApplication {

    public static void main(String[] args) throws InterruptedException {
        AdmissionMonitor monitor = new AdmissionMonitor();
        EntranceHandler[] entranceHandlers = new EntranceHandler[10];
        ExitHandler[] exitHandlers = new ExitHandler[10];
        StatsHandler[] statsHandler = new StatsHandler[10];

        // Starting 10 threads for Entrance and Exit with Stats
        for (int i = 0; i < 10; i++) {
            entranceHandlers[i] = new EntranceHandler(monitor);
            exitHandlers[i] = new ExitHandler(monitor);
            statsHandler[i] = new StatsHandler(monitor);
            new Thread(entranceHandlers[i]).start();
            new Thread(exitHandlers[i]).start();
            new Thread(statsHandler[i]).start();
        }


        // 2-Step Termination
        for (EntranceHandler handler : entranceHandlers) {
            handler.setDone();
        }
        for (ExitHandler handler : exitHandlers) {
            handler.setDone();
        }
        for (StatsHandler handler : statsHandler) {
            handler.setDone();
        }

    }
}
