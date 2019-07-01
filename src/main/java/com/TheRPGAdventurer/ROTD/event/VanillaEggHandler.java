package com.TheRPGAdventurer.ROTD.event;

import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper.DragonLifeStage;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Handler for the vanilla dragon egg block
 * @deprecated - Should be handled in a different way. (Replacing the vanilla egg with our custom egg etc) This is a temporary solution
 * @author WolfShotz
 */
public class VanillaEggHandler {
		
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent.RightClickBlock evt) {
		World world = evt.getWorld();
		BlockPos pos = evt.getPos();
		if (world.getBlockState(pos).getBlock() != Blocks.DRAGON_EGG) return; //ignore all other blocks
		if (world.isRemote) return; //do nothing on client world
		if (DragonMountsConfig.isDisableBlockOverride()) return; //do nothing if config is set
		if (world.provider.getDimensionType() == DimensionType.THE_END) {
			evt.getEntityPlayer().sendStatusMessage(new TextComponentTranslation(DMUtils.translateToLocal("egg.cantHatchEnd.DragonMounts")), true);
			return;  //cant hatch in the end
		}
	    	
		world.setBlockToAir(evt.getPos());
	    	
		EntityTameableDragon entityDragon = new EntityTameableDragon(world);
		entityDragon.setPosition(pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5);
		entityDragon.setBreedType(EnumDragonBreed.END);
		entityDragon.getLifeStageHelper().setLifeStage(DragonLifeStage.EGG);
		entityDragon.getReproductionHelper().setBreeder(evt.getEntityPlayer());
	    	
		world.spawnEntity(entityDragon);
	}
}