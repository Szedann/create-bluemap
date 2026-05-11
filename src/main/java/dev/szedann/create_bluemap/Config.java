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

    private static final ModConfigSpec.IntValue LINE_WIDTH_TRAINS = BUILDER
            .comment("")
            .comment("Graphical options for displayed lines in BlueMap")
            .comment("")
            .comment("line width:")
            .comment("Width of the train lines")
            .defineInRange("lineWidthTrains", 6, 1, 20);

    private static final ModConfigSpec.IntValue LINE_WIDTH_TRACKS = BUILDER
            .comment("Width of the train track lines")
            .defineInRange("lineWidthTracks", 6, 1, 20);

    private static final ModConfigSpec.ConfigValue<String> LINE_COLOR_TRACKS = BUILDER
            .comment("")
            .comment("line colors:")
            .comment("Color of the train track lines; HEX value")
            .define("lineColorTracks", "#fff");

    private static final ModConfigSpec.ConfigValue<String> LINE_COLOR_MANUAL_TRAINS = BUILDER
            .comment("Color of trains without schedules; HEX value")
            .define("lineColorManualTrains", "#f99");

    private static final ModConfigSpec.ConfigValue<String> LINE_COLOR_SCHEDULED_TRAINS = BUILDER
            .comment("Color of trains with schedules; HEX value")
            .define("lineColorScheduledTrains", "#99f");

    static final ModConfigSpec SPEC = BUILDER.build();

    public static int trainInterval;
    public static int trackInterval;
    public static boolean renderTracks;
    public static boolean renderCarriages;
    public static boolean renderTrains ;
    public static int lineWidthTrains;
    public static int lineWidthTracks;
    public static String lineColorTracks;
    public static String lineColorManualTrains;
    public static String lineColorScheduledTrains;

    private static void applyValues(){
        trainInterval = INTERVAL_TRAINS.get();
        trackInterval = INTERVAL_TRACKS.get();
        renderTracks = RENDER_TRACKS.get();
        renderCarriages = RENDER_CARRIAGES.get();
        renderTrains  = RENDER_TRAINS.get();
        lineWidthTrains = LINE_WIDTH_TRAINS.get();
        lineWidthTracks = LINE_WIDTH_TRACKS.get();
        lineColorTracks = LINE_COLOR_TRACKS.get();
        lineColorManualTrains = LINE_COLOR_MANUAL_TRAINS.get();
        lineColorScheduledTrains = LINE_COLOR_SCHEDULED_TRAINS.get();
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent.Loading event)
    {
        applyValues();
    }
    @SubscribeEvent
    static void onReload(final ModConfigEvent.Reloading event){
        applyValues();
    }
}
