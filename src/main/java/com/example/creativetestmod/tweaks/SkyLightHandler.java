package com.example.creativetestmod.tweaks;

import com.example.creativetestmod.CreativeTestMod;
import com.example.creativetestmod.LogHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

import java.util.Random;

public class SkyLightHandler {
    private final Random random = new Random();

    @SubscribeEvent
    public void onItemFinished(PlayerUseItemEvent.Finish event) {
        ItemStack stack = event.item;
        if (stack == null || stack.getItem() != CreativeTestMod.glowingApple) {
            return;
        }

        EntityPlayer player = event.entityPlayer;
        World world = player.worldObj;

        if (!world.isRemote) {
            player.addChatMessage(new ChatComponentText("You feel filled with light."));
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20 * 30, 1));
            player.addPotionEffect(new PotionEffect(Potion.jump.id, 20 * 30, 1));
            world.playSoundAtEntity(player, "random.levelup", 0.8F, 1.4F);
        }
    }

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        if (event.entityLiving.worldObj.isRemote) {
            return;
        }

        if (!(event.source.getEntity() instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.source.getEntity();
        if (!player.inventory.hasItem(CreativeTestMod.glowingApple)) {
            return;
        }

        if (random.nextFloat() < 0.35F) {
            ItemStack drop = new ItemStack(Items.glowstone_dust, 1 + random.nextInt(2));
            event.drops.add(new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, drop));
            LogHelper.info("Bonus glowstone dust dropped thanks to the glowing apple.");
        }
    }
}
