package com.TheRPGAdventurer.ROTD.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.inits.ModItems;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * <<<<<<< HEAD
 * Dragon Amulet Item for the use of carrying dragons in an item
 *
 * @author WolfShotz
 * Temporary Class made in order to "datafix" old amulets.
 * This Class checks for the old amulet item use, place the dragon and then replace it with the new amulet.
 * TODO Remove this entire class at some point...
 * @deprecated
 */
public class ItemDragonAmulet extends Item implements IHasModel {

    EnumItemBreedTypes type;

    @Deprecated
    public ItemDragonAmulet(@Nullable EnumItemBreedTypes type, @Nullable EnumDragonBreed breed) {
        this.type=type;
        this.setUnlocalizedName("dragon_amulet");
        this.setRegistryName(DragonMounts.MODID, type.toString().toLowerCase() + "_dragon_amulet");
        this.setMaxStackSize(1);

        ModItems.ITEMS.add(this);
    }

    /**
     * Called when the player has right clicked the itemstack on the ground
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote || !player.isServerWorld()) return EnumActionResult.FAIL;
        ItemStack stack=player.getHeldItem(hand);
        if (!stack.hasTagCompound()) return EnumActionResult.FAIL;

        EntityTameableDragon dragon=new EntityTameableDragon(world);
        dragon.readFromNBT(stack.getTagCompound());

        stack.getTagCompound().setString("Age", StatCollector.translateToLocal("dragon." + dragon.getLifeStageHelper().getLifeStage().toString().toLowerCase()));
        //    	if (dragon.isTamedFor(player)) {
        BlockPos blockPos=pos.offset(facing);
        dragon.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        stack.setTagCompound(null);
        player.setHeldItem(hand, new ItemStack(ModItems.Amulet));
        world.spawnEntity(dragon);
        return EnumActionResult.SUCCESS;
        //    	} else player.sendStatusMessage(new TextComponentTranslation("item.whistle.notOwned"), true);
        //    	return EnumActionResult.FAIL;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn) {
        if (containsDragonEntity(stack)) {
            tooltip.add("Name: " + stack.getTagCompound().getString("Name"));
            tooltip.add("Health: " + TextFormatting.GREEN + stack.getTagCompound().getDouble("Health"));
            tooltip.add(TextFormatting.GRAY + "Age: " + TextFormatting.AQUA + stack.getTagCompound().getString("Age"));
            tooltip.add("Owner: " + TextFormatting.GOLD + stack.getTagCompound().getString("Owner"));
          } else tooltip.add(StatCollector.translateToLocal("dragon.amulet"));
    }

    private boolean containsDragonEntity(ItemStack stack) {
        return !stack.isEmpty() && stack.hasTagCompound() && stack.getTagCompound().hasKey("breed");
    }

    /**
     * Gets the Amulet Model According to breed type
     *
     * @see com.TheRPGAdventurer.ROTD.event.RegistryEventHandler Amulet Model Registry Class
     */
    @SideOnly(Side.CLIENT)
    public ModelResourceLocation getModelLocation(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("breed")) {
            return new ModelResourceLocation("dragonmounts:" + stack.getTagCompound().getString("breed") + "_dragon_amulet");
        } else return new ModelResourceLocation("dragonmounts:dragon_amulet");
    }

    @Override
    public void RegisterModels() {
        DragonMounts.proxy.registerItemRenderer(this, 0, "inventory");
    }
}