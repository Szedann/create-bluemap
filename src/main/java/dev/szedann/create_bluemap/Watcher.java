package dev.szedann.create_bluemap;

import de.bluecolored.bluemap.api.BlueMapAPI;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Watcher {
    private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public static synchronized void start(BlueMapAPI api) {
        if (scheduler.isShutdown()) {
            scheduler = Executors.newSingleThreadScheduledExecutor();
        }

        Create_bluemap.LOGGER.info("Starting Create Bluemap updater");
        Runnable trainUpdater = () -> {
            try {
                Trains.update(api);
            } catch (Exception e) {
                Create_bluemap.LOGGER.error(e.getMessage());
            }
        };
        Runnable trackUpdater = () -> {
            try {
                Tracks.update(api);
            } catch (Exception e) {
                Create_bluemap.LOGGER.error(e.getMessage());
            }
        };
        scheduler.scheduleAtFixedRate(trainUpdater, 0, Config.trainInterval, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(trackUpdater, 0, Config.trackInterval, TimeUnit.SECONDS);
    }

    public static synchronized void stop() {
        scheduler.shutdown();
    }
}
