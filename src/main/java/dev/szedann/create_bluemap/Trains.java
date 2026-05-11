package dev.szedann.create_bluemap;

import com.flowpowered.math.vector.Vector3d;
import com.simibubi.create.Create;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.schedule.ScheduleRuntime;
import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.BlueMapMap;
import de.bluecolored.bluemap.api.markers.LineMarker;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import de.bluecolored.bluemap.api.markers.POIMarker;
import de.bluecolored.bluemap.api.math.Color;
import de.bluecolored.bluemap.api.math.Line;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class Trains {
    private static final Color manualColor = new Color(Config.lineColorScheduledTrains);
    private static final Color scheduledColor = new Color(Config.lineColorManualTrains);

    public static void update(BlueMapAPI api) {
        if (Config.renderTrains)
            updatePOIs(api);
        if (Config.renderCarriages)
            updateCarriages(api);

    }

    private static void updatePOIs(BlueMapAPI api) {
        Map<ResourceKey<Level>, MarkerSet> POIMarkerSets = new HashMap<>();

        Create.RAILWAYS.trains.forEach((uuid, train) -> {
            TrackNode node = train.carriages.get(0).getLeadingPoint().node1;
            if (node == null)
                return;
            ResourceKey<Level> level = node.getLocation().dimension;
            if (!POIMarkerSets.containsKey(level)) {
                POIMarkerSets.put(level, MarkerSet.builder()
                        .defaultHidden(true)
                        .label(String.format("Trains in %s", level.location().toShortLanguageKey())).build());
            }

            Vec3 pos = train.carriages.get(0).getLeadingPoint().getPosition(train.graph);
            var marker = POIMarker.builder()
                    .label(train.name.getString())
                    .position(pos.x, pos.y, pos.z)
                    .maxDistance(150)
                    .build();

            POIMarkerSets.get(level).put(uuid.toString(), marker);
        });

        POIMarkerSets.forEach((level, markerSet) -> {
            api.getWorld(level).ifPresent(world -> {
                for (BlueMapMap map : world.getMaps()) {
                    map.getMarkerSets().put(String.format("trains-%s", level.location().toShortLanguageKey()),
                            markerSet);
                }
            });
        });
    }

    private static void updateCarriages(BlueMapAPI api) {
        Map<ResourceKey<Level>, MarkerSet> lineMarkerMap = new HashMap<>();

        Create.RAILWAYS.trains.forEach((uuid, train) -> {

            int i = 0;
            for (Carriage carriage : train.carriages) {
                TrackNode node = carriage.getLeadingPoint().node1;
                if (node == null)
                    return;
                ResourceKey<Level> level = node.getLocation().dimension;

                if (carriage.getTrailingPoint().node2.getLocation().dimension != level)
                    return;
                if (!lineMarkerMap.containsKey(level)) {
                    lineMarkerMap.put(level, MarkerSet.builder()
                            .label(String.format("Carriages in %s", level.location().toShortLanguageKey())).build());
                }
                i++;
                Vec3 p1 = carriage.getLeadingPoint().getPosition(train.graph);
                Vec3 p2 = carriage.getTrailingPoint().getPosition(train.graph);
                boolean front = train.currentlyBackwards ? i == train.carriages.size() : i == 1;
                boolean scheduled = train.runtime.state == ScheduleRuntime.State.IN_TRANSIT;
                lineMarkerMap.get(level).put(train.id.toString() + "-" + carriage.id, LineMarker.builder()
                        .label(String.format("%s carriage %s", train.name.getString(), i))
                        .line(Line.builder()
                                .addPoint(new Vector3d(p1.x, p1.y + 1, p1.z))
                                .addPoint(new Vector3d(p2.x, p2.y + 1, p2.z))
                                .build())
                        .lineColor(scheduled ? manualColor : scheduledColor)
                        .lineWidth(front ? Config.lineWidthTrains +2 : Config.lineWidthTrains)
                        .depthTestEnabled(false)
                        // .maxDistance(400)
                        .build());
            }

        });

        lineMarkerMap.forEach((level, markerSet) -> {
            api.getWorld(level).ifPresent(world -> {
                for (BlueMapMap map : world.getMaps()) {
                    map.getMarkerSets().put(String.format("carriages-%s", level.location().toShortLanguageKey()),
                            markerSet);
                }
            });
        });
    }
}