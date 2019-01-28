package com.TheRPGAdventurer.ROTD.client.items;

import java.util.List;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.initialization.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.server.entity.helper.EnumDragonLifeStage;

import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.util.math.MathHelper;
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
   	if(dragon.hasCustomName())	nbt.setString("name", dragon.getCustomNameTag());
   }
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(type.color + StatCollector.translateToLocal("dragon." + type.toString().toLowerCase()));
	}
 
 @Override
 public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side,
 		float hitX, float hitY, float hitZ) {
 	
 	ItemStack stack = player.getHeldItem(hand);
  if (world.isRemote) {
      return EnumActionResult.SUCCESS;
  } else if (!player.canPlayerEdit(pos.offset(side), side, stack)) {
      return EnumActionResult.PASS;
  } else {
      IBlockState state = world.getBlockState(pos);

      pos = pos.offset(side);
      double yOffset = 0.0D;

      if (side == EnumFacing.UP && state.getBlock() instanceof BlockFence) {
          yOffset = 0.5D;
      }

      EntityTameableDragon EntityTameableDragon = this.spawnEntityTameableDragon(world, player, stack, pos.getX() + 0.5D, pos.getY() + yOffset, pos.getZ() + 0.5D);

      if (EntityTameableDragon != null) {
          if (stack.hasDisplayName()) {
              EntityTameableDragon.setCustomNameTag(stack.getDisplayName());
          }
           //    no just no!
          if (!player.capabilities.isCreativeMode) {
              stack.shrink(1);
          }

          world.spawnEntity(EntityTameableDragon);
          EntityTameableDragon.playLivingSound();
      }
    }        return EnumActionResult.SUCCESS;     
 }
 
 /**
  * True if the item has enchantment data
  */
 public boolean hasDragonInside(ItemStack stack) {
     if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("essenceDragonId")) {
         return !stack.getTagCompound().getTagList("essenceDragonId", 10).hasNoTags();
     }
     else
     {
         return false;
     }
 }
 
 public EntityTameableDragon spawnEntityTameableDragon(World world, EntityPlayer player, ItemStack stack, double x, double y, double z) {
 	EntityTameableDragon dragon = new EntityTameableDragon(world);
         try {     
         	NBTTagCompound nbt = stack.getTagCompound();		
          if (stack.hasTagCompound()) {
            nbt = stack.getTagCompound(); 
          } else {
            nbt = new NBTTagCompound();
          }
        				
          stack.setTagCompound(nbt);
         	if(stack.getTagCompound().hasKey("essenceDragonId")) {
            	dragon.setUniqueId(stack.getTagCompound().getUniqueId("essenceDragonId"));
          	  dragon.setOwnerId(stack.getTagCompound().getUniqueId("essenceOwnerId"));	
             dragon.setPosition(x, y, z);
             dragon.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
             dragon.rotationYawHead = dragon.rotationYaw;
             dragon.renderYawOffset = dragon.rotationYaw;
             dragon.setBreedType(breed);
             dragon.setHealth(dragon.getMaxHealth());
             return dragon;
          }
         } catch (Exception e) {
             e.printStackTrace();
         }       

     return null;
 }
 
 @Override
 public boolean hasEffect(ItemStack stack) {
 	// TODO Auto-generated method stub
 	return super.hasEffect(stack) || hasDragonInside(stack);
 }

}