package com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class DragonArmourBase extends ItemArmor implements IHasModel  {

	private EnumItemBreedTypes type;
	Item head;
	Item chest;
	Item legs;
	Item feet;
	
	public DragonArmourBase(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName, EnumItemBreedTypes type) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		setCreativeTab(DragonMounts.armoryTab);
		setUnlocalizedName("dragonscale_" + equipmentSlotIn.toString().toLowerCase());
		setRegistryName(new ResourceLocation(DragonMounts.MODID, unlocalizedName));
		this.type = type;
		
		ModArmour.ARMOR.add(this);
	}

	protected boolean isActive(Potion effects, EntityPlayer player) {
		return  !player.isPotionActive(effects);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		this.head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
		this.chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
		this.legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
		this.feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(type.color + DMUtils.translateToLocal("dragon." + type.toString().toLowerCase()));
		stack.setStackDisplayName(type.color + stack.getDisplayName());
	}
	
	@Override
	public void RegisterModels() {
		DragonMounts.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
