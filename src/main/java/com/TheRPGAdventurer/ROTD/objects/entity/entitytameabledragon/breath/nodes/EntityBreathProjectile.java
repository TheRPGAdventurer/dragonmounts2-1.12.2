package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.nodes;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.sound.SoundEffectProjectile;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper.DragonLifeStage;
import com.TheRPGAdventurer.ROTD.util.math.MathX;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by TGG on 14/03/2016.
 * Copied from EntityFireball and modified a bit
 */
public abstract class EntityBreathProjectile extends Entity implements IEntityAdditionalSpawnData
{
  private int xTile = -1;
  private int yTile = -1;
  private int zTile = -1;
  private Block inTile;
  private boolean inGround;
  public EntityLivingBase shootingEntity;
  private int ticksAlive;
  private int ticksInAir;
  public double accelerationX;
  public double accelerationY;
  public double accelerationZ;
  protected Vec3d origin;
  protected Vec3d destination;

  public EntityBreathProjectile(World worldIn) {
    super(worldIn);
    this.setSize(1.0F, 1.0F);
    power = BreathNodeP.Power.SMALL;  // default
    ticksToLive = getLifeTimeTicks(power);
    parentDragon = null;
  }

  public EntityBreathProjectile(World worldIn, EntityTameableDragon shooter,
                                Vec3d i_origin, Vec3d i_destination, BreathNodeP.Power i_power) {
    super(worldIn);
    this.shootingEntity = shooter;
    parentDragon = shooter;
    origin = i_origin;
    destination = i_destination;
    power = i_power;
    this.setSizeFromPower(power);
    Vec3d offset = destination.subtract(origin);
    double yaw = MathX.calculateYaw(offset);
    double pitch = MathX.calculatePitch(offset);

    this.setLocationAndAngles(origin.x, origin.y, origin.z,
            (float)yaw, (float)pitch);
    this.motionX = this.motionY = this.motionZ = 0.0D;

    final double ACCELERATION_BLOCKS_PER_TICK_SQ = 0.2;

    Vec3d normalisedOffset = offset.normalize();
    this.accelerationX = ACCELERATION_BLOCKS_PER_TICK_SQ * normalisedOffset.x;
    this.accelerationY = ACCELERATION_BLOCKS_PER_TICK_SQ * normalisedOffset.y;
    this.accelerationZ = ACCELERATION_BLOCKS_PER_TICK_SQ * normalisedOffset.z;

    ticksToLive = getLifeTimeTicks(power);
  }

  protected void entityInit() {
  }

  /**
   * Return the lifetime of the projectile
   * @param power
   * @return lifetime in ticks
   */
  abstract protected int getLifeTimeTicks(BreathNodeP.Power power);

  /**
   * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
   * length * 64 * renderDistanceWeight Args: distance
   */
  @SideOnly(Side.CLIENT)
  public boolean isInRangeToRenderDist(double distance) {
    double d1 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
    d1 *= 64.0D;
    return distance < d1 * d1;
  }

  // used during initialisation to calculate the entity size based on the power.
  //  must not access member variables!
  protected abstract void setSizeFromPower(BreathNodeP.Power power);

  public Vec3d getCurrentPosition()
  {
    return new Vec3d(posX, posY, posZ);
  }

  @Override
  public void onUpdate() {
    BlockPos entityTilePos = new BlockPos(this);

    if (!this.world.isRemote && !this.world.isBlockLoaded(entityTilePos)) {
      this.setDead();
    } else {
      super.onUpdate();

      if (this.inGround) {
        if (this.world.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
          ++this.ticksAlive;

          if (this.ticksAlive == 600) {
            this.setDead();
          }

          return;
        }

        this.inGround = false;
        this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
        this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
        this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
        this.ticksAlive = 0;
        this.ticksInAir = 0;
      } else {
        ++this.ticksInAir;
      }

      Vec3d startPos = new Vec3d(this.posX, this.posY, this.posZ);
      Vec3d endPos = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

      final boolean STOP_ON_LIQUID_FALSE = false;
      final boolean IGNORE_BLOCK_WITHOUT_BOUNDING_BOX_TRUE = true;
      final boolean RETURN_LAST_UNCOLLIDABLE_BLOCK_FALSE = false;
      RayTraceResult movingobjectposition = this.world.rayTraceBlocks(startPos, endPos,
                                                    STOP_ON_LIQUID_FALSE, IGNORE_BLOCK_WITHOUT_BOUNDING_BOX_TRUE,
                                                    RETURN_LAST_UNCOLLIDABLE_BLOCK_FALSE);

      if (movingobjectposition != null) {
        endPos = new Vec3d(movingobjectposition.hitVec.x, movingobjectposition.hitVec.y,
                         movingobjectposition.hitVec.z);
      }

      Entity firstEntityStruck = null;
      double smallestCollisionDistance = Double.MAX_VALUE;
      List collidingEntities = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox()
                                                                               .expand(this.motionX, this.motionY,
                                                                                         this.motionZ)
                                                                               .grow(1.0D));
      for (Object entry : collidingEntities) {
        Entity collidingEntity = (Entity)entry;
        if (collidingEntity.canBeCollidedWith() &&
             (!collidingEntity.isEntityEqual(this.shootingEntity) || this.ticksInAir >= 25)) {
          final double f = 0.3F;
          AxisAlignedBB axisalignedbb = collidingEntity.getEntityBoundingBox().grow(f);
          RayTraceResult movingobjectposition1 = axisalignedbb.calculateIntercept(startPos, endPos);

          if (movingobjectposition1 != null) {
            double d1 = startPos.distanceTo(movingobjectposition1.hitVec);

            if (d1 < smallestCollisionDistance) {
              firstEntityStruck = collidingEntity;
              smallestCollisionDistance = d1;
            }
          }
        }
      }

      if (firstEntityStruck != null) {
        movingobjectposition = new RayTraceResult(firstEntityStruck);
      }

      if (movingobjectposition != null) {
        this.onImpact(movingobjectposition);
      }

      this.posX += this.motionX;
      this.posY += this.motionY;
      this.posZ += this.motionZ;
      float motionLength = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      this.rotationYaw = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) + 90.0F;

      for (this.rotationPitch = (float) (Math.atan2((double) motionLength, this.motionY) * 180.0D / Math.PI) - 90.0F;
           this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
        ;
      }

      while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
        this.prevRotationPitch += 360.0F;
      }

      while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
        this.prevRotationYaw -= 360.0F;
      }

      while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
        this.prevRotationYaw += 360.0F;
      }

      this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
      this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
      float motionFactor = this.getMotionFactor();

      if (this.isInWater()) {
        inWaterUpdate();
        for (int j = 0; j < 4; ++j) {
          float f3 = 0.25F;
          this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double) f3,
                                      this.posY - this.motionY * (double) f3, this.posZ - this.motionZ * (double) f3,
                                      this.motionX, this.motionY, this.motionZ, new int[0]);
        }

        motionFactor *= 0.8F;
      }

      this.motionX += this.accelerationX;
      this.motionY += this.accelerationY;
      this.motionZ += this.accelerationZ;
      this.motionX *= (double) motionFactor;
      this.motionY *= (double) motionFactor;
      this.motionZ *= (double) motionFactor;

      this.setPosition(this.posX, this.posY, this.posZ);
    }
  }

  /**
   * Return the motion factor for this projectile. The factor is multiplied by the original motion.
   * effectively a 'drag' on the projectile motion
   */
  protected float getMotionFactor() {
    return 0.95F;
  }

  // Called every tick that the projectile is in water
  protected void inWaterUpdate()
  {
  }

  /**
   * Called when this EntityFireball hits a block or entity.
   */
  protected abstract void onImpact(RayTraceResult movingObject);

  /**
   * (abstract) Protected helper method to write subclass entity data to NBT.
   */
  @Override
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    tagCompound.setShort("xTile", (short) this.xTile);
    tagCompound.setShort("yTile", (short) this.yTile);
    tagCompound.setShort("zTile", (short) this.zTile);
    ResourceLocation resourcelocation = (ResourceLocation) Block.REGISTRY.getNameForObject(this.inTile);
    tagCompound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
    tagCompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
    tagCompound.setTag("direction", this.newDoubleNBTList(new double[]{this.motionX, this.motionY, this.motionZ}));
    tagCompound.setInteger("ProjectilePower", this.power.ordinal());
    tagCompound.setDouble("accelerationX", accelerationX);
    tagCompound.setDouble("accelerationY", accelerationY);
    tagCompound.setDouble("accelerationZ", accelerationZ);
    tagCompound.setInteger("ticksToLive", ticksToLive);
    tagCompound.setDouble("originX", origin.x);
    tagCompound.setDouble("originY", origin.y);
    tagCompound.setDouble("originZ", origin.z);
    tagCompound.setDouble("destinationX", destination.x);
    tagCompound.setDouble("destinationY", destination.y);
    tagCompound.setDouble("destinationZ", destination.z);
  }

  /**
   * (abstract) Protected helper method to read subclass entity data from NBT.
   */
  @Override
  public void readEntityFromNBT(NBTTagCompound tagCompound) {
    this.xTile = tagCompound.getShort("xTile");
    this.yTile = tagCompound.getShort("yTile");
    this.zTile = tagCompound.getShort("zTile");

    if (tagCompound.hasKey("inTile", 8)) {
      this.inTile = Block.getBlockFromName(tagCompound.getString("inTile"));
    } else {
      this.inTile = Block.getBlockById(tagCompound.getByte("inTile") & 255);
    }

    this.inGround = tagCompound.getByte("inGround") == 1;

    if (tagCompound.hasKey("direction", 9)) {
      NBTTagList nbttaglist = tagCompound.getTagList("direction", 6);
      this.motionX = nbttaglist.getDoubleAt(0);
      this.motionY = nbttaglist.getDoubleAt(1);
      this.motionZ = nbttaglist.getDoubleAt(2);
    } else {
      this.setDead();
    }

    this.accelerationX = tagCompound.getDouble("accelerationX");
    this.accelerationY = tagCompound.getDouble("accelerationY");
    this.accelerationZ = tagCompound.getDouble("accelerationZ");

    power = BreathNodeP.Power.SMALL;  // default
    if (tagCompound.hasKey("ProjectilePower", 99)) {
      int powerIndex = tagCompound.getInteger("ProjectilePower");
      if (powerIndex >= 0 && powerIndex < BreathNodeP.Power.values().length) {
        this.power = BreathNodeP.Power.values()[powerIndex];
      }
    }
    ticksToLive = tagCompound.getInteger("ticksToLive");

    double originX = tagCompound.getDouble("originX");
    double originY = tagCompound.getDouble("originY");
    double originZ = tagCompound.getDouble("originZ");
    double destinationX = tagCompound.getDouble("destinationX");
    double destinationY = tagCompound.getDouble("destinationY");
    double destinationZ = tagCompound.getDouble("destinationZ");
    origin = new Vec3d(originX, originY, originZ);
    destination = new Vec3d(destinationX, destinationY, destinationZ);
  }

  /**
   * Called by the server when constructing the spawn packet.
   * Data should be added to the provided stream.
   *
   * @param buffer The packet data stream
   */
  @Override
  public void writeSpawnData(ByteBuf buffer)
  {
    buffer.writeInt(power.ordinal());
    buffer.writeFloat((float)accelerationX);
    buffer.writeFloat((float)accelerationY);
    buffer.writeFloat((float)accelerationZ);
    buffer.writeDouble(origin.x);
    buffer.writeDouble(origin.y);
    buffer.writeDouble(origin.z);
    buffer.writeDouble(destination.x);
    buffer.writeDouble(destination.y);
    buffer.writeDouble(destination.z);
    buffer.writeInt(parentDragon.getEntityId());
  }

  /**
   * Called by the client when it receives a Entity spawn packet.
   * Data should be read out of the stream in the same way as it was written.
   *
   * @param additionalData The packet data stream
   */
  @Override
  public void readSpawnData(ByteBuf additionalData)
  {
    int powerOrdinal = additionalData.readInt();
    accelerationX = additionalData.readFloat();
    accelerationY = additionalData.readFloat();
    accelerationZ = additionalData.readFloat();

    if (powerOrdinal >= 0 && powerOrdinal < BreathNodeP.Power.values().length) {
      this.power = BreathNodeP.Power.values()[powerOrdinal];
    }
    this.setSizeFromPower(power);

    double originX = additionalData.readDouble();
    double originY = additionalData.readDouble();
    double originZ = additionalData.readDouble();
    double destinationX = additionalData.readDouble();
    double destinationY = additionalData.readDouble();
    double destinationZ = additionalData.readDouble();
    origin = new Vec3d(originX, originY, originZ);
    destination = new Vec3d(destinationX, destinationY, destinationZ);

    parentDragonID = additionalData.readInt();
  }

  /**
   * Returns true if other Entities should be prevented from moving through this Entity.
   */
  public boolean canBeCollidedWith() {
    return true;
  }

  public float getCollisionBorderSize() {
    return 1.0F;
  }

  /**
   * Called when the entity is attacked.
   */
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;

  }

  public class SoundUpdateLink implements SoundEffectProjectile.ProjectileSoundUpdateLink
  {
    public boolean refreshSoundInfo(SoundEffectProjectile.ProjectileSoundInfo infoToUpdate)
    {

      infoToUpdate.projectileState = (ticksToLive > 0 && !isDead)
              ? SoundEffectProjectile.ProjectileSoundInfo.State.IN_FLIGHT
              : SoundEffectProjectile.ProjectileSoundInfo.State.FINISHED;
      infoToUpdate.projectileLocation = EntityBreathProjectile.this.getCurrentPosition();
      infoToUpdate.relativeVolume = 1.0F;
      EntityTameableDragon parentDragon = getParentDragon();
      if (parentDragon != null) {
        infoToUpdate.dragonMouthLocation = parentDragon.getPositionVector();
        infoToUpdate.lifeStage = parentDragon.getLifeStageHelper().getLifeStage();
      } else {
        infoToUpdate.dragonMouthLocation = new Vec3d(0,0,0);     //arbitrary fall-back values
        infoToUpdate.lifeStage = DragonLifeStage.HATCHLING;
      }
      return true;
    }
  }



  /**
   * Returns the dragon which spawned this projectile
   * @return the dragon, or null if invalid or doesn't exist.
   */
  public EntityTameableDragon getParentDragon()
  {
    if (parentDragon != null) return parentDragon;
    if (parentDragonID == null) throw new IllegalStateException("parentDragonID not initialised yet");
    Entity entityFromID = this.world.getEntityByID(parentDragonID);
    if (entityFromID instanceof EntityTameableDragon) {
      parentDragon = (EntityTameableDragon)entityFromID;
    }
    return parentDragon;
  }

  /**
   * Gets how bright this entity is.
   */
  public float getBrightness(float p_70013_1_) {
    return 1.0F;
  }

  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender(float p_70070_1_) {
    return 15728880;
  }

  protected BreathNodeP.Power power;
  protected int ticksToLive;
  private EntityTameableDragon parentDragon;
  private Integer parentDragonID = null;

}
