package com.TheRPGAdventurer.ROTD.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.inits.ModItems;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Dragon Amulet Item for the use of carrying dragons in an item
 * 
 * @author WolfShotz
 * TODO Remove ItemDragonAmulet deprecated class and replace it with this one. Rename this to 'ItemDragonAmulet'
 */
public class ItemDragonAmuletNEW extends Item implements ItemMeshDefinition {

	private EnumItemBreedTypes type;

    public ItemDragonAmuletNEW() {
        String name = "dragon_amulet";
        this.setRegistryName(DragonMounts.MODID, name);
        this.setUnlocalizedName(name);
        this.setMaxStackSize(1);
        this.setCreativeTab(DragonMounts.mainTab);
        
        ModItems.ITEMS.add(this);
    }

    private boolean containsDragonEntity(ItemStack stack) {
        return !stack.isEmpty() && stack.hasTagCompound() && stack.getTagCompound().hasKey("breed");
    }
    
    /**
     * Called when the player has right clicked an entity with the itemstack
     */
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
    	if (!target.isServerWorld()) return false;
    	if (containsDragonEntity(stack) || !(target instanceof EntityTameableDragon) || !target.isEntityAlive()) return false;
    	EntityTameableDragon dragon = (EntityTameableDragon) target;
    	if (!dragon.isOwner(player)) {
    		player.sendStatusMessage(new TextComponentTranslation("dragon.notOwned"), true);
    		return false;
    	}
    	
    	NBTTagCompound tag = new NBTTagCompound();
    	tag.setString("breed", dragon.getBreedType().toString().toLowerCase());
    	this.type = EnumItemBreedTypes.valueOf(dragon.getBreedType().toString());
    	tag.setString("Name", type.color + (dragon.hasCustomName() ? dragon.getCustomNameTag() : StatCollector.translateToLocal("dragon." + type.toString().toLowerCase()) + " Dragon"));
    	tag.setString("Owner", dragon.getOwner().getName());

    	target.writeToNBT(tag);
    	stack.setTagCompound(tag);

    	doAmuletExtras(player, stack, false);
    	player.setHeldItem(hand, stack);
    	target.setDead();
		return true;
    }
    
    /**
     * Called when the player has right clicked the itemstack on the ground
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	if (world.isRemote || !player.isServerWorld()) return EnumActionResult.FAIL;
    	ItemStack stack = player.getHeldItem(hand);
    	if (!containsDragonEntity(stack) || !stack.hasTagCompound()) return EnumActionResult.FAIL;
    	
    	EntityTameableDragon entityDragon = new EntityTameableDragon(world);
    	entityDragon.readFromNBT(stack.getTagCompound());
    	
    	if (entityDragon.isTamedFor(player)) {
    		BlockPos blockPos = pos.offset(facing);
    		entityDragon.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    		stack.setTagCompound(null);
    		doAmuletExtras(player, stack, true);
    		player.setHeldItem(hand, stack);
    		world.spawnEntity(entityDragon);
    		return EnumActionResult.SUCCESS;
    	} else player.sendStatusMessage(new TextComponentTranslation("dragon.notOwned"), true);
    	return EnumActionResult.FAIL;
    }
    
    /* Item Extras */
   
    @SideOnly(Side.CLIENT)
    private void doAmuletExtras(EntityPlayer player, ItemStack stack, boolean release) {
        if (release) {
//            player.world.playSound((EntityPlayer) null, player.getPosition(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 1, 1);
            player.world.playSound((EntityPlayer) null, player.getPosition(), SoundEvents.ENTITY_ILLAGER_MIRROR_MOVE, SoundCategory.NEUTRAL, 2, 1);
            stack.setStackDisplayName(TextFormatting.RESET + stack.getDisplayName());
        } else {
            player.world.playSound((EntityPlayer) null, player.getPosition(), SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.NEUTRAL, 1, 1);
            stack.setStackDisplayName(type.color + stack.getDisplayName());
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn) {
		TextFormatting t = null;
    	if (containsDragonEntity(stack)) {
    		tooltip.add("Name: " + stack.getTagCompound().getString("Name"));
    		tooltip.add("Health: " + t.GREEN + stack.getTagCompound().getDouble("Health"));
    		tooltip.add("Owner: " + t.GOLD + stack.getTagCompound().getString("Owner"));
    	} else tooltip.add(t.GREEN + StatCollector.translateToLocal("item.dragonamulet.info"));
    }

    /**
     * Gets the Amulet Model According to breed type
     * @see com.TheRPGAdventurer.ROTD.event.RegistryEventHandler Amulet Model Registry Class
     */
	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("breed")) {
			return new ModelResourceLocation("dragonmounts:" + stack.getTagCompound().getString("breed") + "_dragon_amulet");
		} else return new ModelResourceLocation("dragonmounts:dragon_amulet");
	}
}