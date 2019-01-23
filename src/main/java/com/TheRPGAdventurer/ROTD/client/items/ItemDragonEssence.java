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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
	
 /**
  * Determines if this Item has a special entity for when they are in the world.
  * Is called when a EntityItem is spawned in the world, if true and Item#createCustomEntity
  * returns non null, the EntityItem will be destroyed and the new Entity will be added to the world.
  *
  * @param stack The current item stack
  * @return True of the item has a custom entity, If true, Item#createCustomEntity will be called
  */
 public boolean hasCustomEntity(ItemStack stack)
 {
     return false;
 }

 /**
  * This function should return a new entity to replace the dropped item.
  * Returning null here will not kill the EntityItem and will leave it to function normally.
  * Called when the item it placed in a world.
  *
  * @param world The world object
  * @param location The EntityItem object, useful for getting the position of the entity
  * @param itemstack The current item stack
  * @return A new Entity object to spawn or null
  */
 @Nullable
 public Entity createEntity(World world, Entity location, ItemStack itemstack)
 {
     return null;
 }

 /**
  * Called by the default implemetation of EntityItem's onUpdate method, allowing for cleaner
  * control over the update of the item without having to write a subclass.
  *
  * @param entityItem The entity Item
  * @return Return true to skip any further update code.
  */
 public boolean onEntityItemUpdate(net.minecraft.entity.item.EntityItem entityItem)
 {
     return false;
 }

}
