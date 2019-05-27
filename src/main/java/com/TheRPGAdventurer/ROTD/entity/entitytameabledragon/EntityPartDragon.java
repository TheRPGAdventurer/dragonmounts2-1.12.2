package com.TheRPGAdventurer.ROTD.entity.entitytameabledragon;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.network.MessageDragonHitboxInteract;
import net.ilexiconn.llibrary.server.entity.multipart.PartEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;

public class EntityPartDragon extends PartEntity {

	// Credits: AlexThe666
	public EntityPartDragon(EntityLiving parent, float radius, float angleYaw, float offsetY, float sizeX, float sizeY,
			float damageMultiplier) {
		super(parent, radius, angleYaw, offsetY, sizeX, sizeY, damageMultiplier);
	}
	
	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		if(world.isRemote){
            DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonHitboxInteract(this.parent.getEntityId(), 0));
        }
		return super.processInitialInteract(player, hand);
	}
	
    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if(world.isRemote && source.getTrueSource() instanceof EntityPlayer){
            DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonHitboxInteract(this.parent.getEntityId(), damage));
        }
        return this.parent.attackEntityFrom(source, damage * this.damageMultiplier);
    }

}
