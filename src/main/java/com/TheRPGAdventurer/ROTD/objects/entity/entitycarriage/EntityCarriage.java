package com.TheRPGAdventurer.ROTD.objects.entity.entitycarriage;

import com.TheRPGAdventurer.ROTD.inits.ModItems;
import net.minecraft.block.BlockPlanks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityCarriage extends Entity {

    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(EntityCarriage.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.createKey(EntityCarriage.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(EntityCarriage.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityCarriage.class, DataSerializers.VARINT);
    public static float defaultMaxSpeedAirLateral = 0.4f;
    public static float defaultMaxSpeedAirVertical = -1f;
    public static double defaultDragAir = 0.94999998807907104D;
    private static com.TheRPGAdventurer.ROTD.util.ICollisionHandler collisionHandler = null;
    protected float maxSpeedAirLateral = defaultMaxSpeedAirLateral;
    protected float maxSpeedAirVertical = defaultMaxSpeedAirVertical;
    protected double dragAir = defaultDragAir;
    private boolean isInReverse;

    private int lerpSteps;
    private double boatPitch;
    private double lerpY;
    private double lerpZ;
    private double boatYaw;
    private double lerpXRot;

    public EntityCarriage(World worldIn) {
        super(worldIn);
        this.preventEntitySpawning = true;
        this.setSize(0.5F, 1.8F);
    }

    public EntityCarriage(World worldIn, double x, double y, double z) {
        this(worldIn);
        this.setPosition(x, y, z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    /**
     * Gets the current global Minecart Collision handler if none
     * is registered, returns null
     *
     * @return The collision handler or null
     */
    public static com.TheRPGAdventurer.ROTD.util.ICollisionHandler getCollisionHandler() {
        return collisionHandler;
    }

    /**
     * Sets the global Minecart Collision handler, overwrites any
     * that is currently set.
     *
     * @param handler The new handler
     */
    public static void setCollisionHandler(com.TheRPGAdventurer.ROTD.util.ICollisionHandler handler) {
        collisionHandler = handler;
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
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        if (getCollisionHandler() != null) return getCollisionHandler().getCollisionBox(this, entityIn);
        return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed() {
        return !this.isRiding() || !this.isBeingRidden();
    }

    /**
     * Returns the collision bounding box for this entity
     */
    public AxisAlignedBB getCollisionBoundingBox() {
        if (getCollisionHandler() != null) return getCollisionHandler().getBoundingBox(this);
        return null;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
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
        super.onUpdate();
        tickLerp();

        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }

        if (world.isRemote) {

        } else {
            if (!this.hasNoGravity()) {
                this.motionY -= 0.03999999910593033D;
            }
        }

        if (this.getRidingEntity() != null) {
            this.setEntityInvulnerable(true);
        }

        double d0 = onGround ? this.getMaximumSpeed() : getMaxSpeedAirLateral();
        this.motionX = MathHelper.clamp(this.motionX, -d0, d0);
        this.motionZ = MathHelper.clamp(this.motionZ, -d0, d0);

        double moveY = motionY;
        if (getMaxSpeedAirVertical() > 0 && motionY > getMaxSpeedAirVertical()) {
            moveY = getMaxSpeedAirVertical();
            if (Math.abs(motionX) < 0.3f && Math.abs(motionZ) < 0.3f) {
                moveY = 0.15f;
                motionY = moveY;
            }
        } else if (isInWater()) {
            moveY = getMaxSpeedAirVertical();
            if (Math.abs(motionX) < 0.3f && Math.abs(motionZ) < 0.3f) {
                moveY = 0.0000006f;
                motionY = moveY;
            }
        }

        if (this.onGround) {
            this.motionX *= 0.5D;
            this.motionY *= 1.0D;
            this.motionZ *= 0.5D;
        }

        this.move(MoverType.SELF, this.motionX, moveY, this.motionZ);

        ////  if (!this.onGround) {
        ///      this.motionX *= getDragAir();
        //      this.motionY *= getDragAir();
        //      this.motionZ *= getDragAir();
        //  }

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 1.0F);
        }

        if (this.posY < -64.0D) {
            this.outOfWorld();
        }

        if (!this.world.isRemote && this.world instanceof WorldServer) {
            this.world.profiler.startSection("portal");
            MinecraftServer minecraftserver = this.world.getMinecraftServer();
            int i = this.getMaxInPortalTime();

            if (this.inPortal) {
                if (minecraftserver.getAllowNether()) {
                    if (!this.isRiding() && this.portalCounter++ >= i) {
                        this.portalCounter = i;
                        this.timeUntilPortal = this.getPortalCooldown();
                        int j;

                        if (this.world.provider.getDimensionType().getId() == -1) {
                            j = 0;
                        } else {
                            j = -1;
                        }

                        this.changeDimension(j);
                    }

                    this.inPortal = false;
                }
            } else {
                if (this.portalCounter > 0) {
                    this.portalCounter -= 4;
                }

                if (this.portalCounter < 0) {
                    this.portalCounter = 0;
                }
            }

            if (this.timeUntilPortal > 0) {
                --this.timeUntilPortal;
            }

            this.world.profiler.endSection();
        }

        double d3 = (double) MathHelper.wrapDegrees(this.rotationYaw - this.prevRotationYaw);

        if (d3 < -170.0D || d3 >= 170.0D) {
            this.rotationYaw += 180.0F;
            this.isInReverse = !this.isInReverse;
        }

        this.setRotation(this.rotationYaw, this.rotationPitch);

        this.doBlockCollisions();
        List<Entity> list = world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().grow(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D), EntitySelectors.getTeamCollisionPredicate(this));

        if (!list.isEmpty()) {
            boolean flag = !this.world.isRemote && !(this.getControllingPassenger() instanceof EntityPlayer);

            for (int j = 0; j < list.size(); ++j) {
                Entity entity = list.get(j);

                if (!entity.isPassenger(this)) {
                    if (flag && this.getPassengers().size() < 2 && !entity.isRiding() && entity.width < this.width + 0.7 && entity instanceof EntityLivingBase && !(entity instanceof EntityWaterMob) && !(entity instanceof EntityPlayer)) {
                        entity.startRiding(this);
                        // } else {
                        //    this.applyEntityCollision(entity);
                    }
                }
            }
        }
    }

    private void tickLerp() {
        if (this.lerpSteps > 0 && !this.canPassengerSteer()) {
            double d0 = this.posX + (this.boatPitch - this.posX) / (double) this.lerpSteps;
            double d1 = this.posY + (this.lerpY - this.posY) / (double) this.lerpSteps;
            double d2 = this.posZ + (this.lerpZ - this.posZ) / (double) this.lerpSteps;
            double d3 = MathHelper.wrapDegrees(this.boatYaw - (double) this.rotationYaw);
            this.rotationYaw = (float) ((double) this.rotationYaw + d3 / (double) this.lerpSteps);
            this.rotationPitch = (float) ((double) this.rotationPitch + (this.lerpXRot - (double) this.rotationPitch) / (double) this.lerpSteps);
            --this.lerpSteps;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
    }


    // Forge: Fix MC-119811 by instantly completing lerp on board
    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (this.lerpSteps > 0) {
            this.lerpSteps = 0;
            this.posX = this.boatPitch;
            this.posY = this.lerpY;
            this.posZ = this.lerpZ;
            this.rotationYaw = (float) this.boatYaw;
            this.rotationPitch = (float) this.lerpXRot;
        }
    }

    /**
     * Set the position and rotation values directly without any clamping.
     */
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.boatPitch = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.boatYaw = (double) yaw;
        this.lerpXRot = (double) pitch;
        this.lerpSteps = 10;
    }

    @Override
    public boolean isEntityInvulnerable(DamageSource source) {
        return super.isEntityInvulnerable(source);
    }

    @Override
    public void updatePassenger(Entity passenger) {
        float f = 0.0F;
        float f1 = (float) ((this.isDead ? 0.009999999776482582D : this.getMountedYOffset()) + passenger.getYOffset());
        Vec3d vec3d = (new Vec3d((double) f, 0.0D, 0.0D)).rotateYaw(-this.rotationYaw * 0.017453292F - ((float) Math.PI / 2F));
        passenger.setPosition(this.posX + vec3d.x, this.posY + (double) f1, this.posZ + vec3d.z);

        if (!(passenger instanceof EntityPlayer)) {
            passenger.rotationYaw = this.rotationYaw;
            passenger.setRotationYawHead(passenger.getRotationYawHead() + this.rotationYaw);
            this.applyYawToEntity(passenger);
        }
    }

    /**
     * Applies this boat's yaw to the given entity. Used to update the orientation of its passenger.
     */
    protected void applyYawToEntity(Entity passenger) {
        //float rotation = dragon.isPassenger(this) && this == dragon.getPassengers().get(0) ? -50: dragon.isPassenger(this) && this == dragon.getPassengers().get(0) ? 50: 0;
        passenger.setRenderYawOffset(this.rotationYaw + 0);
        float f = MathHelper.wrapDegrees(passenger.rotationYaw - this.rotationYaw);
        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
        passenger.prevRotationYaw += f1 - f;
        passenger.rotationYaw += f1 - f;
        passenger.setRotationYawHead(passenger.rotationYaw);
    }

    /**
     * Applies a velocity to the entities, to push them away from eachother.
     */
    public void applyEntityCollision(Entity entityIn) {
        //   net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.minecart.MinecartCollisionEvent(this, entityIn));
        if (!this.world.isRemote) {
            if (!entityIn.noClip && !this.noClip) {
                if (!this.isPassenger(entityIn)) {
                    double d0 = entityIn.posX - this.posX;
                    double d1 = entityIn.posZ - this.posZ;
                    double d2 = d0 * d0 + d1 * d1;

                    if (d2 >= 9.999999747378752E-5D) {
                        d2 = (double) MathHelper.sqrt(d2);
                        d0 = d0 / d2;
                        d1 = d1 / d2;
                        double d3 = 1.0D / d2;

                        if (d3 > 1.0D) {
                            d3 = 1.0D;
                        }

                        d0 = d0 * d3;
                        d1 = d1 * d3;
                        d0 = d0 * 0.10000000149011612D;
                        d1 = d1 * 0.10000000149011612D;
                        d0 = d0 * (double) (1.0F - this.entityCollisionReduction);
                        d1 = d1 * (double) (1.0F - this.entityCollisionReduction);
                        d0 = d0 * 0.5D;
                        d1 = d1 * 0.5D;

                        if (entityIn instanceof EntityCarriage) {
                            double d4 = entityIn.posX - this.posX;
                            double d5 = entityIn.posZ - this.posZ;
                            Vec3d vec3d = (new Vec3d(d4, 0.0D, d5)).normalize();
                            Vec3d vec3d1 = (new Vec3d((double) MathHelper.cos(this.rotationYaw * 0.017453292F), 0.0D, (double) MathHelper.sin(this.rotationYaw * 0.017453292F))).normalize();
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

                        } else {
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
        return (double) this.height * 0D;
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
        if (!this.world.isRemote && !this.isDead) {
            if (source.getTrueSource() != null && !this.isPassenger(source.getTrueSource()) && !this.isEntityInvulnerable(source)) {

                this.setForwardDirection(-this.getForwardDirection());
                this.setTimeSinceHit(10);
                this.setDamage(this.getDamage() + amount * 10.0F);
                this.markVelocityChanged();
                boolean flag = source.getTrueSource() instanceof EntityPlayer && ((EntityPlayer) source.getTrueSource()).capabilities.isCreativeMode;

                if (flag || this.getDamage() > 40.0F) {
                    if (!flag && this.world.getGameRules().getBoolean("doEntityDrops")) {
                        this.dropItemWithOffset(this.getItemDrop(), 1, 0.0F);
                    }

                    this.setDead();
                }
            }
        }
        return false;
    }

    public Item getItemDrop() {
        switch (this.getType()) {
            case OAK:
            default:
                return ModItems.carriage_oak;
            case SPRUCE:
                return ModItems.carriage_spruce;
            case BIRCH:
                return ModItems.carriage_birch;
            case JUNGLE:
                return ModItems.carriage_jungle;
            case ACACIA:
                return ModItems.carriage_acacia;
            case DARK_OAK:
                return ModItems.carriage_darkoak;
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
        this.dataManager.register(TYPE, Integer.valueOf(EntityCarriage.Type.OAK.ordinal()));
    }

    /**
     * Gets the current amount of damage the minecart has taken. Decreases over time. The cart breaks when this is over
     * 40.
     */
    public float getDamage() {
        return this.dataManager.get(DAMAGE).floatValue();
    }

    /**
     * Sets the current amount of damage the minecart has taken. Decreases over time. The cart breaks when this is over
     * 40.
     */
    public void setDamage(float damage) {
        this.dataManager.set(DAMAGE, Float.valueOf(damage));
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit() {
        return this.dataManager.get(TIME_SINCE_HIT).intValue();
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setTimeSinceHit(int timeSinceHit) {
        this.dataManager.set(TIME_SINCE_HIT, Integer.valueOf(timeSinceHit));
    }

    /**
     * Gets the forward direction of the entity.
     */
    public int getForwardDirection() {
        return this.dataManager.get(FORWARD_DIRECTION).intValue();
    }

    /**
     * Sets the forward direction of the entity.
     */
    public void setForwardDirection(int forwardDirection) {
        this.dataManager.set(FORWARD_DIRECTION, Integer.valueOf(forwardDirection));
    }

    public EntityCarriage.Type getType() {
        return EntityCarriage.Type.byId(this.dataManager.get(TYPE).intValue());
    }

    public void setType(EntityCarriage.Type boatType) {
        this.dataManager.set(TYPE, Integer.valueOf(boatType.ordinal()));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setFloat("damage", this.getDamage());
        compound.setInteger("forward", this.getForwardDirection());
        compound.setInteger("timesincehit", this.getTimeSinceHit());
        compound.setString("Type", this.getType().getName());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        this.setDamage(compound.getFloat("damage"));
        this.setForwardDirection(compound.getInteger("forward"));
        this.setTimeSinceHit(compound.getInteger("timesincehit"));
        if (compound.hasKey("Type", 8)) {
            this.setType(EntityCarriage.Type.getTypeFromString(compound.getString("Type")));
        }
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    @Override
    public void fall(float distance, float damageMultiplier) {
        // ignore fall damage if the entity can fly
        if (!isBeingRidden()) {
            super.fall(distance, damageMultiplier);
        }
    }

    public static enum Type {
        OAK(BlockPlanks.EnumType.OAK.getMetadata(), "oak"), SPRUCE(BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce"), BIRCH(BlockPlanks.EnumType.BIRCH.getMetadata(), "birch"), JUNGLE(BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle"), ACACIA(BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia"), DARK_OAK(BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak");

        private final String name;
        private final int metadata;

        private Type(int metadataIn, String nameIn) {
            this.name = nameIn;
            this.metadata = metadataIn;
        }

        /**
         * Get a boat type by it's enum ordinal
         */
        public static EntityCarriage.Type byId(int id) {
            if (id < 0 || id >= values().length) {
                id = 0;
            }

            return values()[id];
        }

        public static EntityCarriage.Type getTypeFromString(String nameIn) {
            for (int i = 0; i < values().length; ++i) {
                if (values()[i].getName().equals(nameIn)) {
                    return values()[i];
                }
            }

            return values()[0];
        }

        public String getName() {
            return this.name;
        }

        public int getMetadata() {
            return this.metadata;
        }

        public String toString() {
            return this.name;
        }
    }

}
