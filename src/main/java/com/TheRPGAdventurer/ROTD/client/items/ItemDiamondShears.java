package com.TheRPGAdventurer.ROTD.client.items;

import java.util.Random;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.sound.ModSounds;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IShearable;

public class ItemDiamondShears extends ItemShears {
	
	private ToolMaterial material;
	private EntityTameableDragon dragon;
		
	public ItemDiamondShears(ToolMaterial material, String unlocalizedName) {
	    this.setUnlocalizedName(unlocalizedName);
	    this.setRegistryName(new ResourceLocation(DragonMounts.MODID, unlocalizedName));
	    this.setMaxDamage(345);
	    this.setMaxStackSize(1);
	    this.setCreativeTab(DragonMounts.TAB);	    
	}
	
	@Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity, net.minecraft.util.EnumHand hand) {	 
		if (entity.world.isRemote) {return false;}

	        if (entity instanceof EntityTameableDragon) {
	            EntityTameableDragon target = (EntityTameableDragon)entity;
	            BlockPos pos = new BlockPos(target.posX, target.posY, target.posZ);
	            if (target.isShearable(itemstack, target.world, pos)) {
	                java.util.List<ItemStack> drops = target.onSheared(itemstack, target.world, pos,
	                        net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.FORTUNE, itemstack));

	                java.util.Random rand = new java.util.Random();
	                for(ItemStack stack : drops) {
	                    net.minecraft.entity.item.EntityItem ent = target.entityDropItem(stack, 1.0F);
	                    ent.motionY += rand.nextFloat() * 0.05F;
	                    ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
	                    ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
	                }
	                
	                Random random = new Random();
	                target.attackEntityFrom(DamageSource.causeMobDamage(player), 8);
	                
	                itemstack.damageItem(35, target);
	            }
	     
	      return true;

	  } else {
		  return super.itemInteractionForEntity(itemstack, player, entity, hand);
	  }
	  
   }

}