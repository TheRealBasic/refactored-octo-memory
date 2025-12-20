package com.example.creativetestmod;

import com.example.creativetestmod.registry.ModEvents;
import com.example.creativetestmod.registry.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.SharedConstants;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.slf4j.Logger;

@Mod(CreativeTestMod.MOD_ID)
public final class CreativeTestMod {
    public static final String MOD_ID = "creative_test_mod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CreativeTestMod() {
        BusGroup modBus = FMLJavaModLoadingContext.get().getModBusGroup();
        ModItems.register(modBus);
        FMLCommonSetupEvent.getBus(modBus).addListener(this::commonSetup);
        BuildCreativeModeTabContentsEvent.getBus(modBus).addListener(this::buildCreativeTabContents);

        MinecraftForge.EVENT_BUS.register(ModEvents.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Creative Test Mod initialized for Minecraft {} on Forge {}.",
                SharedConstants.getCurrentVersion().name(), FMLLoader.versionInfo().forgeVersion());
    }

    private void buildCreativeTabContents(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.GLOWING_APPLE);
        }
    }
}
