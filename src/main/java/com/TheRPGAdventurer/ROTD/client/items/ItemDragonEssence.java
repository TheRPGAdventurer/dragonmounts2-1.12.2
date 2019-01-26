package com.TheRPGAdventurer.ROTD.client.items;

import java.util.List;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.initialization.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDragonEssence extends Item {
	
	public EnumItemBreedTypes type;
	public EnumDragonBreed breed;

	public ItemDragonEssence(EnumItemBreedTypes type, EnumDragonBreed breed) {
		   this.breed = breed;
	    this.setUnlocalizedName(type.toString().toLowerCase() + "_dragon_essence");
	    this.setRegistryName(type.toString().toLowerCase() + "_dragon_essence");
	    this.setCreativeTab(DragonMounts.TAB);
	    this.maxStackSize = 1;
	    this.type = type;
	}
	
	@Override
	public boolean updateItemStackNBT(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		return super.updateItemStackNBT(nbt);
	}
	
	public void setDragonNBT(EntityTameableDragon dragon) {
		 ItemStack stack = new ItemStack(this);
	 	NBTTagCompound nbt = stack.getTagCompound();		
   if (stack.hasTagCompound()) {
     nbt = stack.getTagCompound(); 
   } else {
     nbt = new NBTTagCompound();
   }
 				
   stack.setTagCompound(nbt);
   
   if(stack.getTagCompound() != null) {
   	nbt.setUniqueId("essenceDragonId", dragon.getUniqueID());
   	nbt.setUniqueId("essenceOwnerId" , dragon.getOwnerId ());
   }
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(type.color + StatCollector.translateToLocal("dragon." + type.toString().toLowerCase()));
	}
 
 @Override
 public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
 		float hitX, float hitY, float hitZ) {
	 ItemStack stack = new ItemStack(this);
 	NBTTagCompound nbt = stack.getTagCompound();		
  if (stack.hasTagCompound()) {
    nbt = stack.getTagCompound(); 
  } else {
    nbt = new NBTTagCompound();
  }
				
  stack.setTagCompound(nbt);
 	if(stack.getTagCompound().hasKey("essenceDragonId")) {
  	EntityTameableDragon dragon = new EntityTameableDragon(world);
  	dragon.setUniqueId(stack.getTagCompound().getUniqueId("essenceDragonId"));
  	dragon.setOwnerId(stack.getTagCompound().getUniqueId("essenceOwnerId"));
 	 world.spawnEntity(dragon);
 	}
 	return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
 }

}
