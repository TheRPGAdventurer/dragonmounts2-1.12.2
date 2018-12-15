package com.TheRPGAdventurer.ROTD.client.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.gui.GuiHandler;
import com.TheRPGAdventurer.ROTD.client.sound.ModSounds;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.IDragonWhistle;
import com.TheRPGAdventurer.ROTD.server.network.MessageDragonWhistle;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget.Sorter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDragonWhistle extends Item {
	
	ItemDragonWhistle.Commands commands;
    private final MessageDragonWhistle dcw = new MessageDragonWhistle();
	
	public ItemDragonWhistle() {
		this.setUnlocalizedName("dragon_whistle");
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "dragon_whistle"));
		this.setMaxStackSize(1);
		this.setCreativeTab(DragonMounts.TAB);
	}
	
	public void setCommands(ItemDragonWhistle.Commands commands) {
		this.commands = commands;
	}
	
	public ItemDragonWhistle.Commands getCommands() {
		return commands;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//		tooltip.add(StatCollector.translateToLocal(commands.toString().toLowerCase()));
	}
	
	
	// thanks AlexThe666
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
		ItemStack itemStackIn = player.getHeldItem(hand);
		EntityTameableDragon dragon = new EntityTameableDragon(worldIn);
        EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemStackIn);
        ItemStack itemstack1 = player.getItemStackFromSlot(entityequipmentslot);

        if(worldIn.isRemote) {
        	DragonMounts.proxy.openDragonWhistleGui();
        }
        
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
            		
	}
	
	/*
	if(dragon.isTamed() && dragon.isOwner(player)) {
        if (dragon.isFlying() || dragon.isHovering()) {
            dragon.setFlying(false);
            dragon.setHovering(false);
        }
    }*/
  //  @SubscribeEvent
  ///  public void onTick(ClientTickEvent evt) {
  //      BitSet flags = dcw.getFlags();
  //      flags.set(0, commands == Commands.COME);
  //      flags.set(1, commands == Commands.CIRCLE);
  //      flags.set(2, KEY_HOVERCANCEL.isPressed());
        
        // send message to server if it has changed
   //     if (dcw.hasChanged()) {
    //        DragonMounts.NETWORK_WRAPPER.sendToServer(dcw);
   //     }
  //  }
    
	public enum Commands {
		NONE(),
	    COME(),
		CIRCLE();	
	}
}
