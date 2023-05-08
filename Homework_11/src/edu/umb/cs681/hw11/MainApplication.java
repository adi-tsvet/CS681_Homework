package edu.umb.cs681.hw11;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MainApplication {
    public static void main(String[] args) throws InterruptedException {
        Path[] files = { Paths.get("htmlFiles/a.html"), Paths.get("htmlFiles/b.html"),
                Paths.get("htmlFiles/c.html"), Paths.get("htmlFiles/d.html")};
        AccessCounter accessCounter = AccessCounter.getInstance();
        RequestHandler[] handlers = new RequestHandler[10];
        for (int i = 0; i < handlers.length; i++) {
            handlers[i] = new RequestHandler(accessCounter, files);
            new Thread(handlers[i]).start();
        }
        Thread.sleep(10000);
        for (RequestHandler handler : handlers) {
            handler.stop();
        }
        for (RequestHandler handler : handlers) {
            new Thread(handler).interrupt();
        }

    }
}

