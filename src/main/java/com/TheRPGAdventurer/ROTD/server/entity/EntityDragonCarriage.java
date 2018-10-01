package com.TheRPGAdventurer.ROTD.server.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.util.ICollisionHandler;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDragonCarriage extends Entity {
	
    private static final DataParameter<Float> DAMAGE = EntityDataManager.<Float>createKey(EntityDragonCarriage.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.<Integer>createKey(EntityDragonCarriage.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.<Integer>createKey(EntityDragonCarriage.class, DataSerializers.VARINT);
    private static ICollisionHandler collisionHandler = null;
    public static float defaultMaxSpeedAirLateral = 0.4f;
    public static float defaultMaxSpeedAirVertical = -1f;
    public static double defaultDragAir = 0.94999998807907104D;
    protected float maxSpeedAirLateral = defaultMaxSpeedAirLateral;
    protected float maxSpeedAirVertical = defaultMaxSpeedAirVertical;
    protected double dragAir = defaultDragAir;
    private boolean isInReverse;
    
    public EntityDragonCarriage(World worldIn) { 
        super(worldIn);
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.7F); 
    }
    
    public EntityDragonCarriage(World worldIn, double x, double y, double z)  {
        this(worldIn);
        this.setPosition(x, y, z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z; 
    }
    
    @Override
    public boolean canPassengerSteer() {
    	return false;
    }
    
    @Override
    protected boolean canFitPassenger(Entity passenger) {
    	return this.getPassengers().size() < 1;
    }
    
    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking() {
        return false;
    }
    
    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
     * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : (Entity)list.get(0);
    }
    
    /**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    @Nullable
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
    }
    
    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    /**
     * Returns the collision bounding box for this entity
     */
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.getEntityBoundingBox(); 
    } 
    
    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    	super.updateFallState(y, onGroundIn, state, pos);  
    } 
    
    public float getMaxSpeedAirVertical() {
        return maxSpeedAirVertical;
    }
    
    /**
     * Get's the maximum speed for a minecart
     */
    protected double getMaximumSpeed() {
        return 0.4D;
    }
    
    public float getMaxSpeedAirLateral() {
        return maxSpeedAirLateral;
    }
    
    public double getDragAir() {
        return dragAir;
    }

    public void setDragAir(double value) {
        dragAir = value;
    }
    
    @Override
    public void onUpdate() { 
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }  
        
        if(world.isRemote) {
        	
        } else {
        	if (!this.hasNoGravity()) {
                this.motionY -= 0.03999999910593033D;
            }
        }
        
        double d0 = onGround ? this.getMaximumSpeed() : getMaxSpeedAirLateral();
        this.motionX = MathHelper.clamp(this.motionX, -d0, d0);
        this.motionZ = MathHelper.clamp(this.motionZ, -d0, d0);

        double moveY = motionY;
        if(getMaxSpeedAirVertical() > 0 && motionY > getMaxSpeedAirVertical()) {
            moveY = getMaxSpeedAirVertical();
            if(Math.abs(motionX) < 0.3f && Math.abs(motionZ) < 0.3f) {
                moveY = 0.15f;
                motionY = moveY;
            }
        } else if(this.isInWater()) {
        	moveY = getMaxSpeedAirVertical();
            if(Math.abs(motionX) < 0.3f && Math.abs(motionZ) < 0.3f) {
                moveY = 0.000002213f;
                motionY = moveY;
            }
        }

        if (this.onGround || this.isInWater()) {
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
        }

        this.move(MoverType.SELF, this.motionX, moveY, this.motionZ);

        if (!this.onGround || this.isInWater()) {
            this.motionX *= getDragAir();
            this.motionY *= getDragAir();
            this.motionZ *= getDragAir();
        }

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 1.0F);
        }
        
        if (this.posY < -64.0D) {
            this.outOfWorld();
        }
        
        double d3 = (double)MathHelper.wrapDegrees(this.rotationYaw - this.prevRotationYaw);

        if (d3 < -170.0D || d3 >= 170.0D) {
            this.rotationYaw += 180.0F;
            this.isInReverse = !this.isInReverse;
        }
        
        this.setRotation(this.rotationYaw, this.rotationPitch);
    	
    	this.doBlockCollisions();
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().grow(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D), EntitySelectors.getTeamCollisionPredicate(this));

        if (!list.isEmpty()) {
            boolean flag = !this.world.isRemote && !(this.getControllingPassenger() instanceof EntityPlayer);

            for (int j = 0; j < list.size(); ++j) {
                Entity entity = list.get(j);

                if (!entity.isPassenger(this)) { 
                    if (flag && this.getPassengers().size() < 1 && !entity.isRiding() && entity.width < this.width && entity instanceof EntityLivingBase && !(entity instanceof EntityWaterMob) && !(entity instanceof EntityPlayer)) {
                        entity.startRiding(this);
                    } else {
                        this.applyEntityCollision(entity); 
                    }
                }
            }
        }    super.onUpdate();
    }
    
    @Override
    public void updatePassenger(Entity passenger) {	
        float f = 0.0F;
        float f1 = (float)((this.isDead ? 0.009999999776482582D : this.getMountedYOffset()) + passenger.getYOffset());
        Vec3d vec3d = (new Vec3d((double)f, 0.0D, 0.0D)).rotateYaw(-this.rotationYaw * 0.017453292F - ((float)Math.PI / 2F));
        passenger.setPosition(this.posX + vec3d.x, this.posY + (double)f1, this.posZ + vec3d.z);
        
    	if(!(passenger instanceof EntityPlayer)) {
           passenger.rotationYaw = this.rotationYaw;
           passenger.setRotationYawHead(passenger.getRotationYawHead() + this.rotationYaw);
           this.applyYawToEntity(passenger); 
    	}
    	
    	EntityTameableDragon dragon = new EntityTameableDragon(world);
    	
        passenger.setEntityInvulnerable(true);
    }
    
    /**
     * Applies this boat's yaw to the given entity. Used to update the orientation of its passenger.
     */
    protected void applyYawToEntity(Entity entityToUpdate) {
        entityToUpdate.setRenderYawOffset(this.rotationYaw);
        float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
        entityToUpdate.prevRotationYaw += f1 - f;
        entityToUpdate.rotationYaw += f1 - f;
        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw); 
    }
    
    /**
     * Applies a velocity to the entities, to push them away from eachother.
     */
    public void applyEntityCollision(Entity entityIn) {
     //   net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.minecart.MinecartCollisionEvent(this, entityIn));
        if (getCollisionHandler() != null) {
            getCollisionHandler().onEntityCollision(this, entityIn);
            return;
        }
        if (!this.world.isRemote) {
            if (!entityIn.noClip && !this.noClip)  {
                if (!this.isPassenger(entityIn)) {
                    double d0 = entityIn.posX - this.posX;
                    double d1 = entityIn.posZ - this.posZ;
                    double d2 = d0 * d0 + d1 * d1;

                    if (d2 >= 9.999999747378752E-5D)
                    {
                        d2 = (double)MathHelper.sqrt(d2);
                        d0 = d0 / d2;
                        d1 = d1 / d2;
                        double d3 = 1.0D / d2;

                        if (d3 > 1.0D)
                        {
                            d3 = 1.0D;
                        }

                        d0 = d0 * d3;
                        d1 = d1 * d3;
                        d0 = d0 * 0.10000000149011612D;
                        d1 = d1 * 0.10000000149011612D;
                        d0 = d0 * (double)(1.0F - this.entityCollisionReduction);
                        d1 = d1 * (double)(1.0F - this.entityCollisionReduction);
                        d0 = d0 * 0.5D;
                        d1 = d1 * 0.5D;

                        if (entityIn instanceof EntityDragonCarriage) {
                            double d4 = entityIn.posX - this.posX;
                            double d5 = entityIn.posZ - this.posZ;
                            Vec3d vec3d = (new Vec3d(d4, 0.0D, d5)).normalize();
                            Vec3d vec3d1 = (new Vec3d((double)MathHelper.cos(this.rotationYaw * 0.017453292F), 0.0D, (double)MathHelper.sin(this.rotationYaw * 0.017453292F))).normalize();
                            double d6 = Math.abs(vec3d.dotProduct(vec3d1));

                            if (d6 < 0.800000011920929D) {
                                return;
                            }

                            double d7 = entityIn.motionX + this.motionX;
                            double d8 = entityIn.motionZ + this.motionZ;

                            
                                d7 = d7 / 2.0D;
                                d8 = d8 / 2.0D;
                                this.motionX *= 0.20000000298023224D;
                                this.motionZ *= 0.20000000298023224D;
                                this.addVelocity(d7 - d0, 0.0D, d8 - d1);
                                entityIn.motionX *= 0.20000000298023224D;
                                entityIn.motionZ *= 0.20000000298023224D;
                                entityIn.addVelocity(d7 + d0, 0.0D, d8 + d1);
                            
                        }  else {
                            this.addVelocity(-d0, 0.0D, -d1);
                            entityIn.addVelocity(d0 / 4.0D, 0.0D, d1 / 4.0D);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Gets the horizontal facing direction of this Entity, adjusted to take specially-treated entity types into
     * account.
     */
    @Override
    public EnumFacing getAdjustedHorizontalFacing() {
        return this.isInReverse ? this.getHorizontalFacing().getOpposite().rotateY() : this.getHorizontalFacing().rotateY();
    }
    
    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    @Override
    public double getMountedYOffset() {
        return (double)this.height * 0D;
    }
    
    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    @Override
    public boolean canBePushed() {
        return true;
    }
    
    /**
     * Setups the entity to do the hurt animation. Only used by packets in multiplayer.
     */
    @SideOnly(Side.CLIENT)
    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamage(this.getDamage() * 11.0F);
    }
    
    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        } else if (!this.world.isRemote && !this.isDead) {
            if (source instanceof EntityDamageSourceIndirect && source.getTrueSource() != null && this.isPassenger(source.getTrueSource())) {
                return false;
            } else {
                this.setForwardDirection(-this.getForwardDirection());
                this.setTimeSinceHit(10);
                this.setDamage(this.getDamage() + amount * 10.0F);
                this.setBeenAttacked();
                boolean flag = source.getTrueSource() instanceof EntityPlayer && ((EntityPlayer)source.getTrueSource()).capabilities.isCreativeMode;

                if (flag || this.getDamage() > 40.0F)  {
                    if (!flag && this.world.getGameRules().getBoolean("doEntityDrops")) {
                        this.dropItemWithOffset(ModItems.dragon_carriage, 1, 0.0F); 
                    } 

                    this.setDead();
                }

                return true;
            }
        } else {
            return true;
        }
    }
    
    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (player.isSneaking()) {
            return false;
        } else {
            if (!this.world.isRemote) {
                player.startRiding(this);
            }

            return true;
        }
    }

	@Override
	protected void entityInit() {
		this.dataManager.register(DAMAGE, Float.valueOf(0.0F));
        this.dataManager.register(FORWARD_DIRECTION, Integer.valueOf(1));
        this.dataManager.register(TIME_SINCE_HIT, Integer.valueOf(2));
	}
	
    /**
     * Gets the current global Minecart Collision handler if none
     * is registered, returns null
     * @return The collision handler or null
     */
    @Nullable
    public static ICollisionHandler getCollisionHandler()
    {
        return collisionHandler;
    }

    /**
     * Sets the global Minecart Collision handler, overwrites any
     * that is currently set.
     * @param handler The new handler
     */
    public static void setCollisionHandler(ICollisionHandler handler)
    {
        collisionHandler = handler;
    }
	
    /**
     * Sets the current amount of damage the minecart has taken. Decreases over time. The cart breaks when this is over
     * 40.
     */
    public void setDamage(float damage) {
        this.dataManager.set(DAMAGE, Float.valueOf(damage));
    }

    /**
     * Gets the current amount of damage the minecart has taken. Decreases over time. The cart breaks when this is over
     * 40.
     */
    public float getDamage() {
        return ((Float)this.dataManager.get(DAMAGE)).floatValue();
    }
    
    /**
     * Sets the forward direction of the entity.
     */
    public void setForwardDirection(int forwardDirection) {
        this.dataManager.set(FORWARD_DIRECTION, Integer.valueOf(forwardDirection));
    }
    
    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setTimeSinceHit(int timeSinceHit) {
        this.dataManager.set(TIME_SINCE_HIT, Integer.valueOf(timeSinceHit));
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit() {
        return ((Integer)this.dataManager.get(TIME_SINCE_HIT)).intValue();
    }

    /**
     * Gets the forward direction of the entity.
     */
    public int getForwardDirection() {
        return ((Integer)this.dataManager.get(FORWARD_DIRECTION)).intValue();
    }
    
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setFloat("damage", this.getDamage());
		compound.setInteger("forward", this.getForwardDirection());
		compound.setInteger("timesincehit", this.getTimeSinceHit());
	}	

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		this.setDamage(compound.getFloat("damage"));
		this.setForwardDirection(compound.getInteger("forward"));
		this.setTimeSinceHit(compound.getInteger("timesincehit"));
	}

}
