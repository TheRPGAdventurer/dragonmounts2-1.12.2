package com.TheRPGAdventurer.ROTD.objects.items.gemset.armorset;

import com.TheRPGAdventurer.ROTD.inits.ModArmour;
import com.TheRPGAdventurer.ROTD.objects.items.EnumItemBreedTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class DragonArmourIce extends DragonArmourBase {

    private Random rand = new Random();

    public DragonArmourIce(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName) {
        super(materialIn, renderIndexIn, equipmentSlotIn, unlocalizedName, EnumItemBreedTypes.ICE);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (player.getCooldownTracker().getCooldown(this, 0) > 0) return;
        super.onArmorTick(world, player, itemStack);
        if (!(head == ModArmour.iceDragonScaleCap && chest == ModArmour.iceDragonScaleTunic && legs == ModArmour.iceDragonScaleLeggings && feet == ModArmour.iceDragonScaleBoots))
            return;
        if (player.hurtTime == 0) return;

        if (player.getAttackingEntity() != null) {
            List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(5));
            if (entities.isEmpty()) return; // Dont set a cooldown for not hitting anything
            doEffects(player);
            for (net.minecraft.entity.Entity entity : entities)
                if (entity instanceof EntityMob) {
                    ((EntityMob) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
                    ((EntityMob) entity).attackEntityFrom(DamageSource.GENERIC, 1);
                    ((EntityMob) entity).knockBack(entity, 0.4f, 1, 1);
                }
        }
        if (player.hurtTime < 7) player.getCooldownTracker().setCooldown(this, 1200);
    }

    @SideOnly(Side.CLIENT)
    private void doEffects(EntityPlayer player) {
        double px = player.posX + (rand.nextDouble() - 0.3);
        double py = player.posY + (rand.nextDouble() - 0.3);
        double pz = player.posZ + (rand.nextDouble() - 0.3);
        double ox = (rand.nextDouble() - 0.3) * 2;
        double oy = (rand.nextDouble() - 0.3);
        double oz = (rand.nextDouble() - 0.3) * 2;
        for (int x = -30; x < 31; x++) {
            player.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, px, py + 1, pz, ox, oy, oz, 79); //79
            player.world.spawnParticle(EnumParticleTypes.CLOUD, false, player.posX, player.posY + 0.1, player.posZ, Math.sin(x), 0, Math.cos(x));
        }
        player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 0.46f, 1.0f);
        player.world.spawnParticle(EnumParticleTypes.CLOUD, false, player.posX, player.posY + 0.1, player.posZ, 10, 0, 10);
    }

}
