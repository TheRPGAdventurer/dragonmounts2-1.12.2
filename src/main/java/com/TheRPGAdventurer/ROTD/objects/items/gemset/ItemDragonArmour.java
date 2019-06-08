package com.TheRPGAdventurer.ROTD.objects.items.gemset;


import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelArmorStand;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.tools.nsc.doc.base.comment.Text;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemDragonArmour extends ItemArmor {
	
    public EnumItemBreedTypes type;
    /**Tick Delay per potion application*/
    private int fireCooldown;
    private int forestCooldown;
    private int moonCooldown;
    private int waterCooldown;
    

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
		tooltip.add(type.color + DMUtils.translateToLocal("dragon." + type.toString().toLowerCase()));
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (world.isRemote) return;
		
		if (fireCooldown > 0) --fireCooldown;
		if (forestCooldown > 0) --forestCooldown;
		if (moonCooldown > 0) --moonCooldown;
		
		Item head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
		Item chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
		Item legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
		Item feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

		if (fireCooldown != 0) tickFire(head, chest, legs, feet, player);
		if (forestCooldown != 0) tickForest(head, chest, legs, feet, player);
		if (moonCooldown != 0) tickMoon(head, chest, legs, feet, player);
		if (waterCooldown != 0) tickWater(head, chest, legs, feet, player);
	}
	
	private void tickFire(Item head, Item chest, Item legs, Item feet, EntityPlayer player) {
		if (!((head == ModArmour.fireDragonScaleCap && chest == ModArmour.fireDragonScaleTunic && legs == ModArmour.fireDragonScaleLeggings && feet == ModArmour.fireDragonScaleBoots) ||
		    (head == ModArmour.fireDragonScaleCap2 && chest == ModArmour.fireDragonScaleTunic2 && legs == ModArmour.fireDragonScaleLeggings2 && feet == ModArmour.fireDragonScaleBoots2)))
			return;
		
		if (player.isBurning()) player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 400,0, false, false));
		fireCooldown = 1650;
	}
	
	private void tickForest(Item head, Item chest, Item legs, Item feet, EntityPlayer player) {
		if (!(head == ModArmour.forestDragonScaleCap && chest == ModArmour.forestDragonScaleTunic && legs == ModArmour.forestDragonScaleLeggings && feet == ModArmour.forestDragonScaleBoots))
			return;
		
		player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 210, 0, false, false));
		
		if (player.getHealth() < 10f) player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 1, false, true));
		forestCooldown = 4250; //Relatively high because this is op af
	}
	
	private void tickMoon(Item head, Item chest, Item legs, Item feet, EntityPlayer player) {
		if (!(head == ModArmour.moonlightDragonScaleCap && chest == ModArmour.moonlightDragonScaleTunic && legs == ModArmour.moonlightDragonScaleLeggings && feet == ModArmour.moonlightDragonScaleBoots))
			return;
		
		player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0, false, false));
		moonCooldown = 70; // Do not go >70! creates the night vision "flicker"
	}
	
	private void tickWater(Item head, Item chest, Item legs, Item feet, EntityPlayer player) {
		if (!(head == ModArmour.waterDragonScaleCap && chest == ModArmour.waterDragonscaleChesplate && legs == ModArmour.waterDragonScaleLeggings && feet == ModArmour.waterDragonScaleBoots))
			return;
		
		if (player.isInWater()) player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 300,0, false, false));
		waterCooldown = 70;
	}
	
}