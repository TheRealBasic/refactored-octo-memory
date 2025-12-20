package com.example.creativetestmod.registry;

import com.example.creativetestmod.CreativeTestMod;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    private static final FoodProperties GLOWING_APPLE_FOOD = new FoodProperties(6, 0.8f, true);
    private static final Consumable GLOWING_APPLE_CONSUMABLE = Consumable.builder()
            .animation(ItemUseAnimation.EAT)
            .onConsume(new ApplyStatusEffectsConsumeEffect(
                    List.of(
                            new MobEffectInstance(MobEffects.GLOWING, 20 * 30),
                            new MobEffectInstance(MobEffects.REGENERATION, 20 * 5, 0)
                    ),
                    1.0f))
            .build();

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, CreativeTestMod.MOD_ID);

    public static final RegistryObject<Item> GLOWING_APPLE = ITEMS.register("glowing_apple",
            () -> new Item(new Item.Properties()
                    .food(GLOWING_APPLE_FOOD, GLOWING_APPLE_CONSUMABLE)
                    .rarity(Rarity.UNCOMMON)));

    private ModItems() {
    }

    public static void register(final BusGroup modEventBus) {
        ITEMS.register(modEventBus);
    }
}
