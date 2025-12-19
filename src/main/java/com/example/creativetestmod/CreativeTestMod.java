package com.example.creativetestmod;

import com.example.creativetestmod.proxy.CommonProxy;
import com.example.creativetestmod.tweaks.SkyLightHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class CreativeTestMod {

    @Instance(Reference.MOD_ID)
    public static CreativeTestMod instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static Item glowingApple;

    public static final CreativeTabs creativeTab = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Items.glowstone_dust;
        }
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        glowingApple = new ItemFood(6, 0.8F, false)
                .setPotionEffect(Potion.nightVision.id, 30, 0, 1.0F)
                .setAlwaysEdible()
                .setUnlocalizedName("glowingApple")
                .setTextureName(Reference.MOD_ID + ":glowing_apple")
                .setCreativeTab(creativeTab);

        GameRegistry.registerItem(glowingApple, "glowingApple");
        LogHelper.info("Glowing apple registered.");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerRenderers();

        GameRegistry.addShapelessRecipe(new ItemStack(glowingApple), new ItemStack(Items.apple), new ItemStack(Items.glowstone_dust));
        MinecraftForge.EVENT_BUS.register(new SkyLightHandler());
    }
}
