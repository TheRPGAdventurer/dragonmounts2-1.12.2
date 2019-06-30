package com.TheRPGAdventurer.ROTD.client.gui;

import com.TheRPGAdventurer.ROTD.inventory.ContainerDragon;
import com.TheRPGAdventurer.ROTD.inventory.ContainerDragonShulker;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.tileentities.TileEntityDragonShulker;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_DRAGON = 0;
    public static final int GUI_DRAGON_WAND = 1;
    public static final int GUI_DRAGON_SHULKER = 3;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

        if (id == GUI_DRAGON_SHULKER)
            return new ContainerDragonShulker(player.inventory, (TileEntityDragonShulker) world.getTileEntity(new BlockPos(x, y, z)), player);
        if (id == GUI_DRAGON) {
            Entity entity = world.getEntityByID(x);
            if (entity != null) {
                if (entity instanceof EntityTameableDragon)
                    return new ContainerDragon((EntityTameableDragon) entity, player);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == GUI_DRAGON_SHULKER)
            return new GuiDragonShulker(player.inventory, (TileEntityDragonShulker) world.getTileEntity(new BlockPos(x, y, z)), player);
        if (id == GUI_DRAGON) {
            Entity entity = world.getEntityByID(x);
            if (entity != null)
                if (entity instanceof EntityTameableDragon)
                    return new GuiDragon(player.inventory, (EntityTameableDragon) entity);
        }
        return null;
    }
}