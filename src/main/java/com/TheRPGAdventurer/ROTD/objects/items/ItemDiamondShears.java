package com.TheRPGAdventurer.ROTD.objects.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.inits.ModTools;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemDiamondShears extends ItemShears implements IHasModel
{
	
	private ToolMaterial material;
	private EntityTameableDragon dragon;
		
	public ItemDiamondShears(ToolMaterial material, String unlocalizedName) {
	    this.setUnlocalizedName(unlocalizedName);
	    this.setRegistryName(new ResourceLocation(DragonMounts.MODID, unlocalizedName));
	    this.setMaxDamage(345);
	    this.setMaxStackSize(1);
	    this.setCreativeTab(DragonMounts.mainTab);
	    
	    ModTools.TOOLS.add(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT) 
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		NBTTagCompound nbt = stack.getTagCompound();
		tooltip.add(TextFormatting.GREEN + StatCollector.translateToLocal("item.diamondshears.info"));
	}
	
	@Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity, net.minecraft.util.EnumHand hand) {	 
		if (entity.world.isRemote) return false;

	        if (entity instanceof EntityTameableDragon) {
	            EntityTameableDragon target = (EntityTameableDragon)entity;
	            BlockPos pos = new BlockPos(target.posX, target.posY, target.posZ);
	            if (target.isShearable(itemstack, target.world, pos)) {
	                List<ItemStack> drops = target.onSheared(itemstack, target.world, pos,
	                        EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.FORTUNE, itemstack));

	                Random rand = new Random();
	                for(ItemStack stack : drops) {
	                    net.minecraft.entity.item.EntityItem ent = target.entityDropItem(stack, 1.0F);
	                    ent.motionY += rand.nextFloat() * 0.05F;
	                    ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
	                    ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
	                }
	                
	                Random random = new Random();
	  //              target.attackEntityFrom(DamageSource.causeMobDamage(player), 8);
	                
	                itemstack.damageItem(20, target);
	            }
	     
	      return true;

	  } else {
		  return super.itemInteractionForEntity(itemstack, player, entity, hand);
	  }
	  
   }

	@Override
	public void RegisterModels()
	{
		DragonMounts.proxy.registerItemRenderer(this, 0, "inventory");
	}

}