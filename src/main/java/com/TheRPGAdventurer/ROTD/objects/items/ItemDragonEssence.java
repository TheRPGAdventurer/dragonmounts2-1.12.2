package com.TheRPGAdventurer.ROTD.objects.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModItems;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.objects.items.entity.ImmuneEntityItem;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemDragonEssence extends Item implements IHasModel {

    public EnumItemBreedTypes type;
    public EnumDragonBreed breed;

    public ItemDragonEssence(EnumItemBreedTypes type, EnumDragonBreed breed) {
        this.breed = breed;
        this.setUnlocalizedName("dragon_essence");
        this.setRegistryName(type.toString().toLowerCase() + "_dragon_essence");
        this.maxStackSize = 1;
        this.type = type;

        ModItems.ITEMS.add(this);
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float x, float y, float z) {
    	if (world.isRemote || !player.isServerWorld()) return EnumActionResult.FAIL;
    	ItemStack stack = player.getHeldItem(hand);
    	if (!stack.hasTagCompound()) return EnumActionResult.FAIL;
    	
        EntityTameableDragon dragon = new EntityTameableDragon(world);
        dragon.readFromNBT(stack.getTagCompound());
        
        if (dragon.isTamedFor(player)) {
        	dragon.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
        	world.spawnEntity(dragon);
        		//debug
        		System.out.println(dragon.getUniqueID());
        	player.setHeldItem(hand, ItemStack.EMPTY);
            dragon.world.playSound(x, y, z, SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 1, 1, false);
            dragon.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, x + dragon.getRNG().nextInt(5), y + dragon.getRNG().nextInt(5), z + dragon.getRNG().nextInt(5), 1, 1, 1, 0);
        } else player.sendStatusMessage(new TextComponentTranslation("dragon.notOwned"), true);
        
        return super.onItemUse(player, world, pos, hand, facing, x, y, z);
    }
    
    /* INDESTRUCTIBLE */
    
    @Nonnull
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        EntityItem entity = new ImmuneEntityItem(world, location.posX, location.posY, location.posZ, itemstack);
        if (location instanceof EntityItem) {
            // workaround for private access on that field >_>
            NBTTagCompound tag = new NBTTagCompound();
            location.writeToNBT(tag);
            entity.setPickupDelay(tag.getShort("PickupDelay"));
        }
        entity.motionX = location.motionX;
        entity.motionY = location.motionY;
        entity.motionZ = location.motionZ;
        return entity;
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) { return true; }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    	tooltip.add(type.color + DMUtils.translateToLocal("dragon." + type.toString().toLowerCase()));
        if (stack.getTagCompound() == null) {
            //Broken NBT, possibly cheated in, Warn the player...
            tooltip.add(TextFormatting.RED + "ERROR: Broken or Missing NBT Data");
        }
    }

    @Override
    public void RegisterModels() { DragonMounts.proxy.registerItemRenderer(this, 0, "inventory"); }
}