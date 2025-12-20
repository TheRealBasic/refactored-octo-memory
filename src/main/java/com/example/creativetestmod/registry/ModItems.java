package com.example.creativetestmod.registry;

import com.example.creativetestmod.CreativeTestMod;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Central place for registering mod items.
 */
public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CreativeTestMod.MOD_ID);

    public static final RegistryObject<Item> GLOWING_APPLE = ITEMS.register("glowing_apple",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties(6, 0.8F, true))
                    .rarity(Rarity.RARE)));

    private ModItems() {
    }
}
