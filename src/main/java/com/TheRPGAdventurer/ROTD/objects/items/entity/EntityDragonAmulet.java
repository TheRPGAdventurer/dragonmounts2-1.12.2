package com.TheRPGAdventurer.ROTD.objects.items.entity;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.items.ItemDragonAmuletNEW;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class EntityDragonAmulet extends EntityItem {

    ItemStack stack;

    public EntityDragonAmulet(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.world = worldIn;
    }

    public EntityDragonAmulet(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
        this.stack = stack;
    }

    public EntityDragonAmulet(World worldIn) {
        super(worldIn);
    }

    private boolean containsDragonEntity(ItemStack stack) {
        return !stack.isEmpty() && stack.hasTagCompound() && stack.getTagCompound().hasKey("breed");
    }

    /**
     * Spawn the dragon when the item gets destroyed
     */
    @Override
    public void setDead() {
        ItemStack stack = this.stack;
        if (stack != null && stack.getItem() instanceof ItemDragonAmuletNEW) {
            if (containsDragonEntity(stack)) {
                EntityTameableDragon dragon = new EntityTameableDragon(this.world);
                dragon.readEntityFromNBT(stack.getTagCompound());
                dragon.setPosition(this.posX, this.posY, this.posZ);
                Random random = new Random();
                dragon.rotationYaw=random.nextInt(180);
                dragon.rotationPitch=random.nextInt(180);
                dragon.attackEntityFrom(DamageSource.GENERIC,17);
                world.playSound(posX, posY, posZ, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 1F, 1F, false);
                world.spawnEntity(dragon);
            }
        }
        super.setDead();
    }

    public static class EventHandler {

        public static final EntityDragonAmulet.EventHandler instance = new EntityDragonAmulet.EventHandler();

        private EventHandler() {
        }

        @SubscribeEvent
        public void onExpire(ItemExpireEvent event) {
            if (event.getEntityItem() instanceof EntityDragonAmulet) {
                event.setCanceled(true);
            }
        }
    }
}
