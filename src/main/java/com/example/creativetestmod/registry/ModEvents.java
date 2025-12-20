package com.example.creativetestmod.registry;

import com.example.creativetestmod.CreativeTestMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreativeTestMod.MOD_ID)
public final class ModEvents {
    private static final float GLOWSTONE_DROP_CHANCE = 0.35F;
    private static final int EFFECT_DURATION_TICKS = 20 * 30;

    private static final RandomSource RANDOM = RandomSource.create();

    private ModEvents() {
    }

    @SubscribeEvent
    public static void onItemFinished(final LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        ItemStack stack = event.getItem();
        if (!stack.is(ModItems.GLOWING_APPLE.get())) {
            return;
        }

        Level level = player.level();
        if (level.isClientSide()) {
            return;
        }

        applyBuffs(player);
        level.playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.8F, 1.2F);
        player.displayClientMessage(Component.literal("You feel filled with light."), true);
    }

    @SubscribeEvent
    public static void onLivingDrops(final LivingDropsEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        Level level = event.getEntity().level();
        if (level.isClientSide()) {
            return;
        }

        if (!playerHasGlowingApple(player)) {
            return;
        }

        if (RANDOM.nextFloat() >= GLOWSTONE_DROP_CHANCE) {
            return;
        }

        BlockPos pos = event.getEntity().blockPosition();
        ItemStack drop = new ItemStack(Items.GLOWSTONE_DUST, RANDOM.nextInt(2) + 1);
        event.getDrops().add(new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, drop));
    }

    private static void applyBuffs(final Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.SPEED, EFFECT_DURATION_TICKS, 1));
        player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, EFFECT_DURATION_TICKS, 1));
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, EFFECT_DURATION_TICKS, 0));

        // Give a small hunger top-up to mirror a satisfying snack.
        FoodData foodData = player.getFoodData();
        foodData.eat(2, 0.3F);
    }

    private static boolean playerHasGlowingApple(final Player player) {
        return player.getInventory().findSlotMatchingItem(new ItemStack(ModItems.GLOWING_APPLE.get())) >= 0;
    }
}
