package com.TheRPGAdventurer.ROTD.objects.items.gemset;


import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Random;

public class ItemDragonArmour extends ItemArmor {
	
	private Random rand = new Random();
	
    public EnumItemBreedTypes type;
    // Tick Delay per effect application
    private int fireCooldown;
    private int forestCooldown;
    private int moonCooldown;
    private int waterCooldown;
    private int netherCooldown;
    private int iceCooldown;
    

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
//		if (world.isRemote) return; // Disabled for particles... TODO workaround
		
		
		if (fireCooldown > 0) --fireCooldown;
		if (forestCooldown > 0) --forestCooldown;
		if (moonCooldown > 0) --moonCooldown;
		if (netherCooldown > 0) --netherCooldown;
		if (iceCooldown > 0) --iceCooldown;
		
		Item head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
		Item chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
		Item legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
		Item feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

		tickFire(head, chest, legs, feet, player);
		tickForest(head, chest, legs, feet, player);
		tickMoon(head, chest, legs, feet, player);
		tickWater(head, chest, legs, feet, player);
		tickEnder(head, chest, legs, feet, player);
		tickNether(head, chest, legs, feet, player);
		tickIce(head, chest, legs, feet, player);
	}
	

	private void tickFire(Item head, Item chest, Item legs, Item feet, EntityPlayer player) {
		if (!(fireCooldown <= 0)) return;
		if (!((head == ModArmour.fireDragonScaleCap && chest == ModArmour.fireDragonScaleTunic && legs == ModArmour.fireDragonScaleLeggings && feet == ModArmour.fireDragonScaleBoots) ||
		      (head == ModArmour.fireDragonScaleCap2 && chest == ModArmour.fireDragonScaleTunic2 && legs == ModArmour.fireDragonScaleLeggings2 && feet == ModArmour.fireDragonScaleBoots2)))
			return;
		if (!player.isBurning()) return;
		
		player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 400, 0, false, false));
		fireCooldown = 1650;
	}
	
	private void tickForest(Item head, Item chest, Item legs, Item feet, EntityPlayer player) {
		if (!(head == ModArmour.forestDragonScaleCap && chest == ModArmour.forestDragonScaleTunic && legs == ModArmour.forestDragonScaleLeggings && feet == ModArmour.forestDragonScaleBoots))
			return;
		player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 210, 0, false, false));
		if (!(forestCooldown <= 0) && !(player.getHealth() < 10f)) return; // check this after because luck is a perma effect
		
		player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 1, false, true));
		forestCooldown = 4250; //Relatively high because this is op af
	}
	
	private void tickMoon(Item head, Item chest, Item legs, Item feet, EntityPlayer player) {
		if (!(moonCooldown <= 0)) return;
		if (!(head == ModArmour.moonlightDragonScaleCap && chest == ModArmour.moonlightDragonScaleTunic && legs == ModArmour.moonlightDragonScaleLeggings && feet == ModArmour.moonlightDragonScaleBoots))
			return;
		
		player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0, false, false));
		moonCooldown = 70; // Do not go >70! creates the night vision "flicker"
	}
	
	private void tickWater(Item head, Item chest, Item legs, Item feet, EntityPlayer player) {
		if (!(waterCooldown <= 0)) return;
		if (!(head == ModArmour.waterDragonScaleCap && chest == ModArmour.waterDragonscaleChesplate && legs == ModArmour.waterDragonScaleLeggings && feet == ModArmour.waterDragonScaleBoots))
			return;
		if (!player.isInWater()) return;
		
		player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 300));
		waterCooldown = 70;
	}

	private void tickEnder(Item head, Item chest, Item legs, Item feet, EntityPlayer player) {
		if (head == ModArmour.enderDragonScaleCap || chest == ModArmour.enderDragonScaleTunic || legs == ModArmour.enderDragonScaleLeggings || feet == ModArmour.enderDragonScaleBoots) {
	        double px=player.posX + (rand.nextDouble() - 0.3);
	        double py=player.posY + (rand.nextDouble() - 0.3);
	        double pz=player.posZ + (rand.nextDouble() - 0.3);
	        double ox=(rand.nextDouble() - 0.3) * 2;
	        double oy=(rand.nextDouble() - 0.3) * 2;
	        double oz=(rand.nextDouble() - 0.3) * 2;
	        player.world.spawnParticle(EnumParticleTypes.PORTAL, px, py, pz, ox, oy, oz);
		}
		if (!(head == ModArmour.enderDragonScaleCap && chest == ModArmour.enderDragonScaleTunic && legs == ModArmour.enderDragonScaleLeggings && feet == ModArmour.enderDragonScaleBoots))
			return;
        if (!(player.getHealth() < 5f)) return; 
        
        player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2, 2, false, false));
        player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 2, 0, false, false));
	}
	
	private void tickNether(Item head, Item chest, Item legs, Item feet, EntityPlayer player) {
		if (!(netherCooldown <= 0)) return;
		if (!((head == ModArmour.netherDragonScaleCap && chest == ModArmour.netherDragonScaleTunic && legs == ModArmour.netherDragonScaleLeggings && feet == ModArmour.netherDragonScaleBoots) ||
			  (head == ModArmour.netherDragonScaleCap2 && chest == ModArmour.netherDragonScaleTunic2 && legs == ModArmour.netherDragonScaleLeggings2 && feet == ModArmour.netherDragonScaleBoots2)))
			return;
		
		if (player.isBurning()) {
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 400, 0, false, false));
			netherCooldown = 950; // less than Fire because nether is more rare
		}
		else if (player.getHealth() < 5f) {
			player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 800, 1));
			netherCooldown = 2125;
		}
	}
	
	private void tickIce(Item head, Item chest, Item legs, Item feet, EntityPlayer player) {
		if (!(iceCooldown <= 0)) return;
		if (!(head == ModArmour.iceDragonScaleCap && chest == ModArmour.iceDragonScaleTunic && legs == ModArmour.iceDragonScaleLeggings && feet == ModArmour.iceDragonScaleBoots))
			return;
		if (player.hurtTime <= 0) return;
		
		player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
		for (int x = 0; x < 20; ++x) {
	        double px=player.posX + (rand.nextDouble() - 0.3);
	        double py=player.posY + (rand.nextDouble() - 0.3);
	        double pz=player.posZ + (rand.nextDouble() - 0.3);
	        double ox=(rand.nextDouble() - 0.3) * 2;
	        double oy=(rand.nextDouble() - 0.3) * 2;
	        double oz=(rand.nextDouble() - 0.3) * 2;
        	player.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, px, py + 1, pz, ox, oy, oz, 79); //79
		}
		List<net.minecraft.entity.Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(3));
		for (net.minecraft.entity.Entity mobs : entities)
			if (mobs instanceof EntityLivingBase) { 
				((EntityLivingBase) mobs).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 500, 1));
				((EntityLivingBase) mobs).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200));
			}
		iceCooldown = 350;
		
	}
	
}