package dev.szedann.create_bluemap;

import com.mojang.logging.LogUtils;
import de.bluecolored.bluemap.api.BlueMapAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Create_bluemap.MODID)
public class Create_bluemap
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "create_bluemap";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public Create_bluemap(IEventBus modEventBus, ModContainer modContainer)
    {
        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC);
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        BlueMapAPI.onEnable(Watcher::start);
        BlueMapAPI.onDisable(api -> {
            Watcher.stop();
        });
    }
    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event)
    {
        Watcher.stop();
    }

}
