package com.TheRPGAdventurer.ROTD.client.gui;

import com.TheRPGAdventurer.ROTD.client.inventory.ContainerDragon;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiHandler implements IGuiHandler {
	
	public static final int GUI_DRAGON = 0;
	public static final int GUI_DRAGON_WAND = 1;

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		Entity entity = world.getEntityByID(x);
		switch (id) { 
			case GUI_DRAGON:
				if (entity != null) {
					if (entity instanceof EntityTameableDragon) {
						return new ContainerDragon((EntityTameableDragon) entity, player);
					}
				}
				break;			
		}
		return null;

	}

	@SideOnly(Side.CLIENT)
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		Entity entity = world.getEntityByID(x);
		switch (id) {
			case GUI_DRAGON:
				if (entity != null) {
					if (entity instanceof EntityTameableDragon) {
						return new GuiDragon(player.inventory, (EntityTameableDragon) entity);
					}
				}
				break;
			case GUI_DRAGON_WAND:
				if(entity != null) {
					if(entity instanceof EntityTameableDragon) {
						return new GuiDragonWand((EntityTameableDragon) entity, player.inventory);
					}
			    }
				break;
		}
		return entity;
	}
}