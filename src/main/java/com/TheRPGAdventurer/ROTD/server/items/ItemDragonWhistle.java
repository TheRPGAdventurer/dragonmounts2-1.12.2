package com.TheRPGAdventurer.ROTD.server.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.gui.GuiDragonWhistle;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.util.IHasModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemDragonWhistle extends Item implements IHasModel
{

	public ItemDragonWhistle() {
		this.setUnlocalizedName("dragon_whistle");
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "dragon_whistle"));
		this.setMaxStackSize(1);
		this.setCreativeTab(DragonMounts.mainTab);
		
		ModItems.ITEMS.add(this);
	}

	@Override
	@SideOnly(Side.CLIENT) 
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		NBTTagCompound nbt = stack.getTagCompound();
		tooltip.add(TextFormatting.GREEN + StatCollector.translateToLocal("item.whistle.info"));
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
				   if (!nbt.hasKey(DragonMounts.MODID.toLowerCase() + "dragon") && !itemInteractionForEntity(stack, player, dragon, player.getActiveHand())) { 					        
				    	String dragonName = dragon.hasCustomName() ? dragon.getCustomNameTag() : dragon.getBreedType().toString().toLowerCase() + " dragon";
					    String ownerName = dragon.getOwner() != null ? dragon.getOwner().getName() : "NULL";			  
				    	nbt.setUniqueId(DragonMounts.MODID.toLowerCase() + "dragon", dragon.getUniqueID());
				    	player.sendStatusMessage(new TextComponentTranslation(
				    	        "item.whistle.hasDragon", "name:" + dragonName + "owner:" + ownerName),
                                true);
				    	if(dragon.hasCustomName()) {
				    		stack.setStackDisplayName(dragonName);
				    	}
				    	
				    }
			    }
				dragon.setControllingWhistle(stack);
			} else {
	            player.sendStatusMessage(new TextComponentTranslation("item.whistle.notOwned"), true);
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void RegisterModels()
	{
		DragonMounts.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
