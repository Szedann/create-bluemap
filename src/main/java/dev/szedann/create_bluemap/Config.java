package dev.szedann.create_bluemap;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = Create_bluemap.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue INTERVAL_TRAINS = BUILDER
            .comment("Interval between train updates")
            .defineInRange("interval", 5, 1, 30);

    private static final ModConfigSpec.IntValue INTERVAL_TRACKS = BUILDER
            .comment("Interval between track updates")
            .defineInRange("interval_tracks", 30, 10, 240);

    private static final ModConfigSpec.BooleanValue RENDER_TRACKS = BUILDER
            .comment("Whether to render tracks")
            .define("renderTracks", false);

    private static final ModConfigSpec.BooleanValue RENDER_CARRIAGES = BUILDER
            .comment("Whether to render carriages")
            .define("renderCarriages", true);

    private static final ModConfigSpec.BooleanValue RENDER_TRAINS = BUILDER
            .comment("Whether to render trains")
            .define("renderTrains", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static int trainInterval;
    public static int trackInterval;
    public static boolean renderTracks;
    public static boolean renderCarriages;
    public static boolean renderTrains ;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        trainInterval = INTERVAL_TRAINS.get();
        trackInterval = INTERVAL_TRACKS.get();
        renderTracks = RENDER_TRACKS.get();
        renderCarriages = RENDER_CARRIAGES.get();
        renderTrains  = RENDER_TRAINS.get();
    }
}
