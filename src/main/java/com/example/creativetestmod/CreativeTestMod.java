package com.example.creativetestmod;

import com.mojang.logging.LogUtils;
import net.minecraft.SharedConstants;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.slf4j.Logger;

/**
 * Minimal entrypoint for the Creative Test Mod targeting Minecraft 1.21.11.
 */
@Mod(CreativeTestMod.MOD_ID)
public final class CreativeTestMod {
    public static final String MOD_ID = "creative_test_mod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CreativeTestMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Creative Test Mod initialized for Minecraft {} on Forge {}", SharedConstants.getCurrentVersion().getName(), FMLLoader.versionInfo().forgeVersion());
    }
}
