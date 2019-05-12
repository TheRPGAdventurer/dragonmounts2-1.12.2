package com.TheRPGAdventurer.ROTD.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.gui.GuiDragonWhistle;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.initialization.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import net.minecraft.client.Minecraft;
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

import java.util.UUID;

public class ItemDragonWhistle extends Item implements IHasModel {
	
	public ItemDragonWhistle() {
		this.setUnlocalizedName("dragon_whistle");
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "dragon_whistle"));
		this.setMaxStackSize(1);
		this.setCreativeTab(DragonMounts.mainTab);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity target) {
		if (target.world.isRemote) return false;
		if (!(target instanceof EntityTameableDragon)) return false;
		EntityTameableDragon dragon = (EntityTameableDragon) target;
		if (!dragon.isTamedFor(player)) {
			player.sendStatusMessage(new TextComponentTranslation("item.whistle.notOwned"), true);
			return true; // Return true so the dragon isnt damaged
		}
		
		NBTTagCompound nbt = new NBTTagCompound();
        nbt.setUniqueId(DragonMounts.MODID.toLowerCase() + "dragon", dragon.getUniqueID());
        EnumItemBreedTypes type = EnumItemBreedTypes.valueOf(dragon.getBreedType().toString());
    	nbt.setString("Name", type.color + (dragon.hasCustomName() ? dragon.getCustomNameTag() : StatCollector.translateToLocal("dragon." + type.toString().toLowerCase()) + " Dragon"));
        stack.setTagCompound(nbt);
		dragon.setControllingWhistle(stack);
		player.sendStatusMessage(new TextComponentTranslation("item.whistle.hasDragon"), true);
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote) return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		if (!stack.hasTagCompound()) {
			player.sendStatusMessage(new TextComponentTranslation("item.whistle.unBound"), true);
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		}
		this.openDragonWhistleGui(stack.getTagCompound().getUniqueId(DragonMounts.MODID + "dragon"), new ItemStack(this), world);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
	
    @SideOnly(Side.CLIENT)
    public void openDragonWhistleGui(UUID uuid, ItemStack whistle, World world)
    	{ Minecraft.getMinecraft().displayGuiScreen(new GuiDragonWhistle(world, uuid, whistle)); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasUniqueId(DragonMounts.MODID.toLowerCase() + "dragon")) {
			return new TextComponentTranslation(super.getUnlocalizedName(stack) + ".name").getUnformattedComponentText() + " (" + stack.getTagCompound().getString("Name") + TextFormatting.RESET + ")";
		} else return new TextComponentTranslation(super.getUnlocalizedName(stack) + ".name").getUnformattedComponentText();
	}
	
//	@Override
//	public String getItemStackDisplayName(ItemStack stack) {
//		if (!stack.hasTagCompound())
//			return new TextComponentTranslation(super.getUnlocalizedName(stack) + ".name").getUnformattedComponentText();
//		return new TextComponentTranslation(super.getUnlocalizedName(stack) + ".name").getUnformattedComponentText() + " (" + type.color + dragon.getBreedType().toString().toLowerCase() + " dragon" + TextFormatting.RESET + ")";
//	}
	
	@Override
	public void RegisterModels()
		{ DragonMounts.proxy.registerItemRenderer(this, 0, "inventory"); }
}