package com.TheRPGAdventurer.ROTD.client.items;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.gui.GuiDragonWhistle;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.network.MessageDragonWhistle;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
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
		tooltip.add("item.whistle.info");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		NBTTagCompound nbt = stack.getTagCompound();		

	    if (stack.hasTagCompound()) {
	         nbt = stack.getTagCompound(); 
	    } else {
	         nbt = new NBTTagCompound();
	    }
	       				
        stack.setTagCompound(nbt);
        if (!player.isSneaking() && stack.hasTagCompound()) {
      		  if (worldIn.isRemote) {
				  this.openDragonWhistleGui(nbt.getUniqueId(DragonMounts.MODID + "dragon"), new ItemStack(this), worldIn);
				  return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);

			  }
		//  } //else if(!nbt.hasKey(DragonMounts.MODID + "dragon")) {
           // player.sendStatusMessage(new TextComponentTranslation("item.whistle.noDragon", new Object[0]), true);
        }

	   return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);		
	}
	
    @SideOnly(Side.CLIENT)
    public void openDragonWhistleGui(UUID uuid, ItemStack whistle, World world) {
    	Minecraft.getMinecraft().displayGuiScreen(new GuiDragonWhistle(world, uuid, whistle));
    }
    
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity target) {
		NBTTagCompound nbt = stack.getTagCompound();		

	    if (stack.hasTagCompound()) {
	         nbt = stack.getTagCompound(); 
	    } else {
	         nbt = new NBTTagCompound();
	    }
	       				
        stack.setTagCompound(nbt);
        
		if (target instanceof EntityTameableDragon) {
			EntityTameableDragon dragon = (EntityTameableDragon) target;				
			
			if (dragon.isTamedFor(player)) {
				if(stack.getTagCompound() != null) {
				   if (!nbt.hasKey(DragonMounts.MODID.toLowerCase() + "dragon")) { 					        
				    	String dragonName = dragon.hasCustomName() ? dragon.getCustomNameTag() : dragon.getBreedType().toString().toLowerCase() + " dragon";
					    String ownerName = dragon.getOwner() != null ? dragon.getOwner().getName() : "NULL";			  
				    	nbt.setUniqueId(DragonMounts.MODID.toLowerCase() + "dragon", dragon.getUniqueID());
				    	player.sendStatusMessage(new TextComponentTranslation(
				    	        "item.whistle.hasDragon", new Object[0]),
                                true);
				    }
			    }
				dragon.setControllingWhistle(stack);
			} else {
	            player.sendStatusMessage(new TextComponentTranslation("item.whistle.notOwned", new Object[0]), true);
			}
			
			return true;
		} else {
			return false;
		}
	}
	
//	@Override
//    @SideOnly(Side.CLIENT)
//	public boolean hasEffect(ItemStack stack) {
//       return stack.getTagCompound().hasKey(DragonMounts.MODID + "dragon") && stack.hasTagCompound() && stack.getTagCompound() != null
//			   || super.hasEffect(stack);

//	}
}
