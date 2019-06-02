package com.TheRPGAdventurer.ROTD.objects.items.gemset;


import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.util.StatCollector;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemDragonArmour extends ItemArmor {
	
    public EnumItemBreedTypes type;

	public ItemDragonArmour(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName,  EnumItemBreedTypes type) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName("dragonscale_" + equipmentSlotIn.toString().toLowerCase());
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, unlocalizedName));
		this.setCreativeTab(DragonMounts.armoryTab);
		this.type = type;
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.GRAY + StatCollector.translateToLocal("item.armoryitems.info") + " " + type.color + TextFormatting.BOLD + StatCollector.translateToLocal("dragon." + type.toString().toLowerCase()));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		ItemStack head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
		ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		ItemStack legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
		ItemStack feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);

		if(head.getItem() == ModArmour.forestDragonScaleCap && player.fishEntity != null && new Random().nextInt(25) == 1) player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 300,0, false, false));
		if(head.getItem() == ModArmour.moonlightDragonScaleCap && !player.world.isDaytime() && new Random().nextInt(25) == 1) player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300,0, false, false));
		if(chest.getItem() == ModArmour.forestDragonScaleTunic && player.getAttackingEntity() != null && new Random().nextInt(25) == 1) player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200,0, false, false));
		if(chest.getItem() == ModArmour.iceDragonScaleTunic && player.getAttackingEntity() != null && new Random().nextInt(25) == 1) player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 200,0, false, false));

	}
}