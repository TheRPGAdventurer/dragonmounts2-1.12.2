package com.TheRPGAdventurer.ROTD.objects.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.gui.GuiDragonWhistle;
import com.TheRPGAdventurer.ROTD.inits.ModItems;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.UUID;

/**
 * Dragon Whistle Item for controlling certain dragon behaviour remotely.
 *
 * @author TheRPGAdventurer
 * @modifier WolfShotz
 */
public class ItemDragonWhistle extends Item implements IHasModel {


    public ItemDragonWhistle() {
        this.setUnlocalizedName("dragon_whistle");
        this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "dragon_whistle"));
        this.setMaxStackSize(1);
        this.setCreativeTab(DragonMounts.mainTab);

        ModItems.ITEMS.add(this);
    }

    /**
     * Open Dragon Whistle gui for dragon with given uuid
     *
     * @param uuid
     * @param whistle
     * @param world
     */
    @SideOnly(Side.CLIENT)
    private void openDragonWhistleGui(UUID uuid, ItemStack whistle, World world) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiDragonWhistle(world, uuid, whistle));
    }

    /**
     * Called when the player left clicks an entity
     * <p> Registers dragon id as well as cosmetic keys to the whistle
     */
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity target) {
        if (target.world.isRemote) return false;
        if (!(target instanceof EntityTameableDragon)) return false;
        EntityTameableDragon dragon = (EntityTameableDragon) target;
        if (!dragon.isTamedFor(player)) {
            player.sendStatusMessage(new TextComponentTranslation("dragon.notOwned"), true);
            return true; // Return true so the method ends and the dragon isnt damaged
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setUniqueId(DragonMounts.MODID.toLowerCase() + "dragon", dragon.getUniqueID());

        EnumItemBreedTypes type = EnumItemBreedTypes.valueOf(dragon.getBreedType().toString());
        nbt.setString("Name", type.color + (dragon.hasCustomName() ? dragon.getCustomNameTag() : DMUtils.translateToLocal("dragon." + type.toString().toLowerCase()) + " Dragon"));
        nbt.setString("Age", DMUtils.translateToLocal("dragon." + dragon.getLifeStageHelper().getLifeStage().toString().toLowerCase()));
        nbt.setString("OwnerName", dragon.getOwner().getName());
        nbt.setInteger("Color", dragon.getBreed().getColor());

        stack.setTagCompound(nbt);
        dragon.setControllingWhistle(stack);
        player.sendStatusMessage(new TextComponentTranslation("whistle.msg.hasDragon"), true);
        return true;
    }

    /**
     * Called when the ItemStack is right clicked by the player
     * <p> If player is sneaking, clear the tag compound. else, open the whistle gui with given id
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.hasTagCompound() || !stack.getTagCompound().hasUniqueId(DragonMounts.MODID.toLowerCase() + "dragon")) {
            player.sendStatusMessage(new TextComponentTranslation("whistle.msg.unBound"), true);
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
        }
        if (stack.getTagCompound().getString("OwnerName") != player.getName()) {
            player.sendStatusMessage(new TextComponentTranslation("dragon.notOwned"), true);
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
        }
        if (player.isSneaking()) {
            stack.setTagCompound(null);
            player.swingArm(hand);
            player.sendStatusMessage(new TextComponentTranslation("whistle.msg.cleared"), true);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }

        this.openDragonWhistleGui(stack.getTagCompound().getUniqueId(DragonMounts.MODID + "dragon"), new ItemStack(this), world);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    /*=== Item Extras ===*/

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasUniqueId(DragonMounts.MODID.toLowerCase() + "dragon")) {
            return new TextComponentTranslation(super.getUnlocalizedName(stack) + ".name").getUnformattedComponentText() + " (" + stack.getTagCompound().getString("Name") + TextFormatting.RESET + ")";
        } else
            return new TextComponentTranslation(super.getUnlocalizedName(stack) + ".name").getUnformattedComponentText();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasUniqueId(DragonMounts.MODID.toLowerCase() + "dragon")) {
            tooltip.add(TextFormatting.GRAY + "Name: " + stack.getTagCompound().getString("Name"));
            tooltip.add(TextFormatting.GRAY + "Age: " + TextFormatting.AQUA + stack.getTagCompound().getString("Age"));
            tooltip.add(TextFormatting.GRAY + "Owner: " + TextFormatting.GOLD + stack.getTagCompound().getString("OwnerName"));
            tooltip.add(TextFormatting.GRAY + "" + TextFormatting.ITALIC + DMUtils.translateToLocal("item.removeNBT"));
        } else tooltip.add(DMUtils.translateToLocal("item.whistle.info"));
    }

    @Override
    public void RegisterModels() {
        DragonMounts.proxy.registerItemRenderer(this, 0, "inventory");
    }
}