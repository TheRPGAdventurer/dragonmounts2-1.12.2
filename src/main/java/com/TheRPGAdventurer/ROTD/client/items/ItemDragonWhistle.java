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
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
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
		if(stack.getTagCompound() != null) {
		  EntityTameableDragon dragon = (EntityTameableDragon) worldIn.getEntityByID(stack.getTagCompound().getInteger("dragon"));
		  String dragonName = dragon.getDisplayName().getUnformattedComponentText();
		  if(dragon != null)
		  tooltip.add(StatCollector.translateToLocal(dragonName) + " owner:" + dragon.getOwner().getName());
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		
          if (!player.isSneaking() && stack.hasTagCompound() && stack.getTagCompound().hasKey("dragon")) {
			  if (worldIn.isRemote) {
				  DragonMounts.proxy.openDragonWhistleGui(stack.getTagCompound().getInteger("dragon"),
						new ItemStack(this), worldIn); 
				  return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
			  }
		  } //else if(!stack.hasTagCompound() && !stack.hasTagCompound() && !stack.getTagCompound().hasKey("dragon")) {
			//    player.sendStatusMessage(new TextComponentString("whistle.empty"), true);
		   //     return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		  //}
    //   }
        
	   return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);		
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity target) {
		if (target instanceof EntityTameableDragon) {
			EntityTameableDragon dragon = (EntityTameableDragon) target;
			
			if (dragon.isTamedFor(player)) {
				NBTTagCompound nbt = new NBTTagCompound();

				if (stack.hasTagCompound()) {
					nbt = stack.getTagCompound();
			    } else {
					stack.setTagCompound(nbt);
				}
				
				if (!nbt.hasKey("dragon")) { 
					  nbt.setInteger("dragon", dragon.getEntityId());
				   } else {
					  player.sendStatusMessage(new TextComponentString("whistle.limit"), true);
				}

				if(nbt.hasKey("dragon")) {
					player.sendStatusMessage(new TextComponentString("whistle.hasDragon" + " " + dragon.getBreed().getSkin()), true);
				} else {
					player.sendStatusMessage(new TextComponentString("whistle.noNBT"), true);
				}
			} else {
                player.sendStatusMessage(new TextComponentString("whistle.notOwned"), true);
			}
			return true;
		} else {
			return false;
		}
	}
}
