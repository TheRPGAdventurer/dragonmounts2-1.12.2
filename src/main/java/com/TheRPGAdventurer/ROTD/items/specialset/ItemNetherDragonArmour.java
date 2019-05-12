package com.TheRPGAdventurer.ROTD.items.specialset;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.inits.EnumItemBreedTypes;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemNetherDragonArmour extends ItemArmor {
	
	private final PotionEffect potionEffect;
    public EnumItemBreedTypes type;

	public ItemNetherDragonArmour(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName,  PotionEffect potionEffect, EnumItemBreedTypes type) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.potionEffect = potionEffect;
		this.setUnlocalizedName("dragonscale_" + equipmentSlotIn.toString().toLowerCase());
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, unlocalizedName));
		this.setCreativeTab(DragonMounts.armoryTab);
		this.type = type;

	}
	
	@Override
	public void onArmorTick(final World world, final EntityPlayer player, final ItemStack itemStack) {
//		if (!player.isPotionActive(potionEffect.getPotion())) { // If the Potion isn't currently active,
//			player.addPotionEffect(new PotionEffect(potionEffect)); // Apply a copy of the PotionEffect to the player
//		}
		
		if (!player.isInWater()) {
            spawnParticles(player);
            spawnParticles2(player);
		
		} else {
		
		if (player.isInWater()) {
            spawnParticles3(player);
            spawnParticles4(player);
		}
	  }
    }
	
	public void spawnParticles(EntityPlayer player) {
        // spawn generic particles
		Random rand = new Random();
        double px = player.posX + (rand.nextDouble() - 0.3);
        double py = player.posY + (rand.nextDouble() - 0.3);
        double pz = player.posZ + (rand.nextDouble() - 0.3);
        double ox = (rand.nextDouble() - 0.3) * 2;
        double oy = (rand.nextDouble() - 0.3) * 2;
        double oz = (rand.nextDouble() - 0.3) * 2;
        player.world.spawnParticle(EnumParticleTypes.DRIP_LAVA, px, py, pz, ox, oy, oz); 
        
        
	}
	
	public void spawnParticles2(EntityPlayer player) {
        // spawn generic particles
		Random rand = new Random();
        double d0 = player.posX + (rand.nextDouble() * 0.3F);
        double d1 = player.posY + (rand.nextDouble() * 0.3F);
        double d2 = player.posZ + (rand.nextDouble() * 0.3F);
        double ox = (rand.nextDouble() - 0.3) * 0.1;
        double oy = (rand.nextDouble() - 0.3) * 0.1;
        double oz = (rand.nextDouble() - 0.3) * 0.1;
        player.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, ox, oy, oz);
        
	}
	
	public void spawnParticles3(EntityPlayer player) {
        // spawn generic particles
		Random rand = new Random();
        double d0 = player.posX + (rand.nextDouble() * 0.3F);
        double d1 = player.posY + (rand.nextDouble() * 0.3F);
        double d2 = player.posZ + (rand.nextDouble() * 0.3F);
        double ox = (rand.nextDouble() - 0.3) * 0.1;
        double oy = (rand.nextDouble() - 0.3) * 0.1;
        double oz = (rand.nextDouble() - 0.3) * 0.1;
        player.world.spawnParticle(EnumParticleTypes.CLOUD, d0, d1, d2, ox, oy, oz);
	
	}
	
	public void spawnParticles4(EntityPlayer player) {
        // spawn generic particles
		Random rand = new Random();
        double px = player.posX + (rand.nextDouble() - 0.3);
        double py = player.posY + (rand.nextDouble() - 0.3);
        double pz = player.posZ + (rand.nextDouble() - 0.3);
        double ox = (rand.nextDouble() - 0.3) * 2;
        double oy = (rand.nextDouble() - 0.3) * 2;
        double oz = (rand.nextDouble() - 0.3) * 2;
        player.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, px, py, pz, ox, oy, oz);
        
        
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.GRAY + StatCollector.translateToLocal("item.armoryitems.info") + " " + type.color + TextFormatting.BOLD + StatCollector.translateToLocal("dragon." + type.toString().toLowerCase()));
	}

}
