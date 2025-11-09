package dev.szedann.create_bluemap;

import de.bluecolored.bluemap.api.BlueMapAPI;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Watcher {
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static ScheduledFuture<?> future;

    public static void start(BlueMapAPI api) {
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

    public static void stop() {
        scheduler.shutdown();
    }
}