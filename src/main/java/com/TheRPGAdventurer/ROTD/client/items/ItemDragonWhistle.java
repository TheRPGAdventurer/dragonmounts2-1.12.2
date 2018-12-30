package com.TheRPGAdventurer.ROTD.client.items;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.DMUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.reflect.internal.Trees.Modifiers;

public class ItemDragonWhistle extends Item {

	// private final MessageDragonWhistle dcw = new MessageDragonWhistle();
	// private List<EntityTameableDragon> dragons = new ArrayList();
	// EntityTameableDragon dragon;
	private byte oldIndex;

	public ItemDragonWhistle() {
		this.setUnlocalizedName("dragon_whistle");
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "dragon_whistle"));
		this.setMaxStackSize(1);
		this.setCreativeTab(DragonMounts.TAB);
	}

	@Override 
	@SideOnly(Side.CLIENT) 
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		NBTTagCompound nbt = stack.getTagCompound();
		if(stack != null && stack.hasTagCompound() && nbt.hasKey(DragonMounts.MODID.toLowerCase() + "dragon"))  {
		   if(worldIn instanceof WorldServer) {
			 WorldServer worldServer = (WorldServer) worldIn; 
		     EntityTameableDragon dragon = (EntityTameableDragon) worldServer.getEntityFromUuid(nbt.getUniqueId(DragonMounts.MODID.toLowerCase() + "dragon"));
		     if(dragon != null) {
		        String dragonName = dragon.hasCustomName() ? dragon.getCustomNameTag() : dragon.getBreedType().toString().toLowerCase() + " dragon";
		        String ownerName = dragon.getOwner() != null ? dragon.getOwner().getName() : "null";
		        tooltip.add("Dragon:" + dragonName + " " + " Owner:" + " " + ownerName);
		      }
		   }
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		NBTTagCompound nbt = stack.getTagCompound();		

	    if (stack.hasTagCompound()) {
	         nbt = stack.getTagCompound(); 
	    }
	    else
	    {
	         nbt = new NBTTagCompound();
	    }
	       				
        stack.setTagCompound(nbt);
          if (!player.isSneaking() && stack.hasTagCompound()) {
      		  if (worldIn.isRemote) {
				  DragonMounts.proxy.openDragonWhistleGui(nbt.getUniqueId(DragonMounts.MODID.toLowerCase() + "dragon"),
						new ItemStack(this), worldIn); 
				  return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
			  }
		  }      
        
	   return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);		
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity target) {
		NBTTagCompound nbt = stack.getTagCompound();		

	    if (stack.hasTagCompound()) {
	         nbt = stack.getTagCompound(); 
	    }
	    else
	    {
	         nbt = new NBTTagCompound();
	    }
	       				
        stack.setTagCompound(nbt);
        
		if (target instanceof EntityTameableDragon) {
			EntityTameableDragon dragon = (EntityTameableDragon) target;				
			
			if (dragon.isTamedFor(player)) {	// I blame this, if my guess is right, this means that it only sets the dragon ID integer and removed if the player is untamed
				if(stack.getTagCompound() != null) { 
				   if (nbt.hasKey(DragonMounts.MODID.toLowerCase() + "dragon")) { 		
			          //  nbt.setInteger("dragon", null);
				    } else {
				    	String dragonName = dragon.hasCustomName() ? dragon.getCustomNameTag() : dragon.getBreedType().toString().toLowerCase() + " dragon";
					    String ownerName = dragon.getOwner() != null ? dragon.getOwner().getName() : "NULL";			  
				    	nbt.setUniqueId(DragonMounts.MODID.toLowerCase() + "dragon", dragon.getUniqueID());
				    	player.sendStatusMessage(new TextComponentTranslation("item.whistle.hasDragon" + ":" + dragonName + " for " + ownerName, new Object[0]), true);
				    }
			    }
			//	else { 
			//		return false;
			//	}
			
		//	    EntityTameableDragon storedDragon = (EntityTameableDragon) player.world.getEntityByID(nbt.getInteger("dragon"));
			  //  if(storedDragon.isDead && storedDragon != null && nbt.hasKey("dragon")) {
			 //      nbt.removeTag("dragon");
			//    }
			    
			} else {
	            player.sendStatusMessage(new TextComponentTranslation("item.whistle.notOwned", new Object[0]), true);
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();		

	    if (stack.hasTagCompound()) {
	         nbt = stack.getTagCompound(); 
	    }
	    else
	    {
	         nbt = new NBTTagCompound();
	    }
	       				
        stack.setTagCompound(nbt);
        if(nbt.hasKey("dragon")) {
        	return true;
        } else {
	    	return super.hasEffect(stack);
        }
	}
}
