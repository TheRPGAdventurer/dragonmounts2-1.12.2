package com.TheRPGAdventurer.ROTD.client.model;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

/**
 * Gets the Amulet Model According to breed type
 * @see com.TheRPGAdventurer.ROTD.event.RegistryEventHandler Amulet Model Registry Class
 */
public class ModelAmuletMesh implements ItemMeshDefinition {

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("breed")) {
			return new ModelResourceLocation("dragonmounts:" + stack.getTagCompound().getString("breed") + "_dragon_amulet");
		} else return new ModelResourceLocation("dragonmounts:dragon_amulet");
	}
		
}
