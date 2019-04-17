package com.TheRPGAdventurer.ROTD.server.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.server.initialization.EnumItemBreedTypes;
import com.TheRPGAdventurer.ROTD.server.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.server.items.entity.ImmuneEntityItem;
import com.TheRPGAdventurer.ROTD.server.util.IHasModel;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
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

public class ItemDragonAmulet extends Item implements IHasModel
{

    private static final boolean cursedmessage = false;
	EnumItemBreedTypes type;
    EnumDragonBreed breed;
    ItemStack stack;
    public EntityAreaEffectCloud entityareaeffectcloud;

    public ItemDragonAmulet(@Nullable EnumItemBreedTypes type, @Nullable EnumDragonBreed breed)
    {
        String name = type.toString().toLowerCase() + "_dragon_amulet";
        this.type = type;
        this.breed = breed;
        this.setRegistryName(new ResourceLocation(DragonMounts.MODID, name));
        this.setUnlocalizedName(name);
        this.setMaxStackSize(1);
//        this.setCreativeTab(DragonMounts.TAB);
        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
                if (entityIn == null) {
                    return 0.0F;
                } else {
                    ItemStack itemstack = entityIn.getActiveItemStack();
                    return !itemstack.isEmpty() && itemstack.getItem() instanceof ItemDragonAmulet ? (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F : 0.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
        
        ModItems.ITEMS.add(this);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        this.stack = stack;
        stack.setTagCompound(new NBTTagCompound());
    }

    private boolean cursedAmulet = false;
    
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected)
    {
        if (stack.getTagCompound() == null)
        {
            stack.setTagCompound(new NBTTagCompound());
             cursedAmulet = true;
        }
        else if (stack.getTagCompound().getBoolean(DragonMounts.MODID + ":Released") && entity instanceof EntityPlayer)
        {
            stack.shrink(1);
        	EntityPlayer player = (EntityPlayer) entity;
            if(this.getEquipmentSlot(stack) == EntityEquipmentSlot.OFFHAND) {
                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.AmuletEmpty));
            } else {
                player.inventory.setInventorySlotContents(itemSlot, new ItemStack(ModItems.AmuletEmpty));
            }
        }
    }

    /* INDESTRUCTIBLE */

	@Override
    public boolean hasCustomEntity(ItemStack stack)
    {
        return true;
    }

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

    /**
     * Thanks again Lex!
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        EntityTameableDragon dragon = new EntityTameableDragon(worldIn);
        
        dragon.setPosition(pos.getX(), pos.getY() + 1, pos.getZ()); 

        if(hand != EnumHand.MAIN_HAND && !dragon.isTamedFor(player) || stack.getTagCompound() == null || worldIn.isRemote)
        	{
        		//checked twice for correct error messages
        		if (stack.getTagCompound() == null || cursedAmulet) {
        		player.sendStatusMessage(new TextComponentTranslation(TextFormatting.RED + "This Amulet has Broken or Missing NBT Data"), true); }
        		else if (hand != EnumHand.MAIN_HAND) {
        		player.sendStatusMessage(new TextComponentTranslation("Amulets Must be used in main hand"), true); }
        		return EnumActionResult.FAIL;
        	}
        
        //read cache nbt data
        if (stack.getTagCompound() != null) {
        	dragon.readEntityFromNBT(stack.getTagCompound()); }
        
        //Placing entity in world, applying NBT Data       
        dragon.setUniqueId(stack.getTagCompound().getUniqueId("amuletID"));
        dragon.setBreedType(breed);
    	if(dragon.isTamedFor(player))
    	{
    		worldIn.spawnEntity(dragon);
    		stack.getTagCompound().setBoolean(DragonMounts.MODID + ":Released", true);
    	    worldIn.playSound(player, pos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1, 1f);
            if (stack.hasDisplayName()) {
            	dragon.setCustomNameTag(stack.getDisplayName()); }
    	}
    	else {
    		player.sendStatusMessage(new TextComponentTranslation("item.whistle.notOwned"), true); }
        
        //Replace current amulet with empty one if above was successful
        stack = new ItemStack(ModItems.AmuletEmpty);
        
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		if (stack.getTagCompound() == null || cursedAmulet)
        {
        	//Broken NBT, possibly cheated in...
        	tooltip.add(TextFormatting.RED + "ERROR: Broken or Missing NBT Data");
        }
        else
        {
        	tooltip.add(TextFormatting.GREEN + StatCollector.translateToLocal("item.dragonamulet.info"));
        }
    }

	@Override
	public void RegisterModels()
	{
		DragonMounts.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
