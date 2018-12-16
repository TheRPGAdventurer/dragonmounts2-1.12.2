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

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDragonWhistle extends Item {

    private final MessageDragonWhistle dcw = new MessageDragonWhistle();
    private List<EntityTameableDragon> controllableDragons = new ArrayList(); 
    EntityTameableDragon dragon;
	
	public ItemDragonWhistle() {
		this.setUnlocalizedName("dragon_whistle");
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "dragon_whistle"));
		this.setMaxStackSize(1);
		this.setCreativeTab(DragonMounts.TAB);
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
		
        // setting the value of N as 4 
        int limit = 4; 
        int count = 0; 
        Iterator<EntityTameableDragon> it = controllableDragons.iterator(); 
  
        // Iterating through the list of integers 
        while (it.hasNext()) { 
            it.next(); 
            count++; 
  
            // Check if first four i.e, (equal to N) 
            // integers are iterated. 
            if (count > limit) { 
  
                // If yes then remove all the remaining integers. 
                it.remove(); 
            } 
        } 
  
        System.out.print("New stream of length N"
                         + " after truncation is : "); 
  
        // Displaying new stream of length 
        // N after truncation 
        for (EntityTameableDragon number : controllableDragons) { 
            System.out.print(number + " "); 
        } 
        
        if(player.isSneaking()) {
        	controllableDragons.iterator().next();
        	player.sendStatusMessage(dragon.getDisplayName(), true);
        }

        if(worldIn.isRemote) {
        	DragonMounts.proxy.openDragonWhistleGui(dragon);
        }
        
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
            		
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if(target.world.isRemote) return false;

		if(target instanceof EntityTameableDragon) {
			dragon = (EntityTameableDragon)target;
			controllableDragons.add(dragon);
			return true;
		} else {
			return super.itemInteractionForEntity(stack, playerIn, target, hand);
		}
	}
}
