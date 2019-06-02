package com.TheRPGAdventurer.ROTD.objects.tileentities;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler
{
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityDragonShulker.class, new ResourceLocation(DragonMounts.MODID + ":dragon_shulker"));
	}
}
