/*
c ** 2012 August 13
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.server.entity;

import static net.minecraft.entity.SharedMonsterAttributes.ATTACK_DAMAGE;
import static net.minecraft.entity.SharedMonsterAttributes.FOLLOW_RANGE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.gui.GuiHandler;
import com.TheRPGAdventurer.ROTD.client.initialization.ModArmour;
import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.client.initialization.ModKeys;
import com.TheRPGAdventurer.ROTD.client.initialization.ModTools;
import com.TheRPGAdventurer.ROTD.client.message.MessageDragonBreath;
import com.TheRPGAdventurer.ROTD.client.model.dragon.anim.DragonAnimator;
import com.TheRPGAdventurer.ROTD.client.sound.ModSounds;
import com.TheRPGAdventurer.ROTD.server.entity.ai.ground.EntityAIDragonSit;
import com.TheRPGAdventurer.ROTD.server.entity.ai.path.PathNavigateFlying;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.DragonBreed;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.server.entity.helper.DragonBodyHelper;
import com.TheRPGAdventurer.ROTD.server.entity.helper.DragonBrain;
import com.TheRPGAdventurer.ROTD.server.entity.helper.DragonBreedHelper;
import com.TheRPGAdventurer.ROTD.server.entity.helper.DragonHeadPositionHelper;
import com.TheRPGAdventurer.ROTD.server.entity.helper.DragonHelper;
import com.TheRPGAdventurer.ROTD.server.entity.helper.DragonInteractHelper;
import com.TheRPGAdventurer.ROTD.server.entity.helper.DragonLifeStageHelper;
import com.TheRPGAdventurer.ROTD.server.entity.helper.DragonMoveHelper;
import com.TheRPGAdventurer.ROTD.server.entity.helper.DragonParticleHelper;
import com.TheRPGAdventurer.ROTD.server.entity.helper.DragonReproductionHelper;
import com.TheRPGAdventurer.ROTD.server.entity.helper.DragonSoundManager;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.DragonBreathHelper;
import com.TheRPGAdventurer.ROTD.server.network.MessageDragonArmor;
import com.TheRPGAdventurer.ROTD.server.util.ItemUtils;
import com.TheRPGAdventurer.ROTD.util.PrivateFields;
import com.google.common.base.Optional;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ContainerHorseChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Here be dragons.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 * @Modifier James Miller <TheRPGAdventurer.>
 */
public class EntityTameableDragon extends EntityTameable implements IShearable, IEntityMultiPart {

	private static final Logger L = LogManager.getLogger();

	public static final IAttribute MOVEMENT_SPEED_AIR = new RangedAttribute(null, "generic.movementSpeedAir", 1.5, 0.0,
			Double.MAX_VALUE).setDescription("Movement Speed Air").setShouldWatch(true);

	// base attributes
	public static final double BASE_GROUND_SPEED = 0.3;
	public static final double BASE_AIR_SPEED = 0.8;
	public static final double BASE_DAMAGE = 4.5D; 
	public static final double BASE_ARMOR = 10.0D;
	public static final double BASE_TOUGHNESS = 30.0D;
	public static final float BASE_WIDTH = 2.75f;
	public static final float BASE_HEIGHT = 2.10f;
	public static final float RESISTANCE = 20.0f;
	public static final double BASE_FOLLOW_RANGE = 70;
	public static final double BASE_FOLLOW_RANGE_FLYING = BASE_FOLLOW_RANGE * 2;
	public static final int HOME_RADIUS = 64;
	public static final double IN_AIR_THRESH = 10;

	protected int ticksSinceLastAttack;
	public static int ticksShear;

	// data value IDs
	private static final DataParameter<Boolean> DATA_FLYING = EntityDataManager
			.<Boolean>createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> DATA_SADDLED = EntityDataManager
			.<Boolean>createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> DATA_BREATHING = EntityDataManager
			.<Boolean>createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> CHESTED = EntityDataManager
			.<Boolean>createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> ALLOW_OTHERPLAYERS = EntityDataManager
			.<Boolean>createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_MALE = EntityDataManager
			.<Boolean>createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> ARMOR = 
			EntityDataManager.<Integer>createKey(EntityTameableDragon.class, DataSerializers.VARINT);
	private static final DataParameter<Optional<UUID>> DATA_BREEDER = EntityDataManager
			.<Optional<UUID>>createKey(EntityTameableDragon.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	private static final DataParameter<String> DATA_BREED = EntityDataManager
			.<String>createKey(EntityTameableDragon.class, DataSerializers.STRING);
	private static final DataParameter<Integer> DATA_REPRO_COUNT = EntityDataManager
			.<Integer>createKey(EntityTameableDragon.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> DATA_TICKS_SINCE_CREATION = EntityDataManager
			.<Integer>createKey(EntityTameableDragon.class, DataSerializers.VARINT);
	private static final DataParameter<Byte> DRAGON_SCALES = EntityDataManager
			.<Byte>createKey(EntityTameableDragon.class, DataSerializers.BYTE);
	private static final DataParameter<String> DATA_BREATH_WEAPON = EntityDataManager
			.<String>createKey(EntityTameableDragon.class, DataSerializers.STRING);
	private static final DataParameter<Boolean> BANNERED = EntityDataManager
			.<Boolean>createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> WANDED = EntityDataManager
			.<Boolean>createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> HAS_ADJUCATOR_STONE = EntityDataManager
			.<Boolean>createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> HAS_ELDER_STONE = EntityDataManager
			.<Boolean>createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);

	// data NBT IDs
	private static final String NBT_ARMOR = "Armor";
	private static final String NBT_SADDLED = "Saddle";
	private static final String NBT_SHEARED = "Sheared";
	private static final String NBT_CHESTED = "Chested";
	private static final String NBT_BREATHING = "Breathing";
	private static final String NBT_ISMALE = "IsMale";
	private static final String NBT_WANDED = "Wanded";
	private static final String NBT_BANNERED = "Bannered";
	private static final String NBT_ELDER = "Elder";
	private static final String NBT_ADJUCATOR = "Adjucator";

	// server/client delegates
	private final Map<Class, DragonHelper> helpers = new HashMap<>();

	// client-only delegates
	private final DragonBodyHelper bodyHelper = new DragonBodyHelper(this);

	public EntityEnderCrystal healingEnderCrystal;
	public DragonInventory dragonInv;
	public DragonInventory dragonStats;
	private ItemStackHandler itemHandler = null;
	private boolean hasChestVarChanged = false;
	public boolean onGround2;
	private boolean isBreathingFire;
	public boolean isSlowed;
	private int inAirTicks;
	public final EntityAITasks attackTasks;
	public DragonAnimator animator;

	/** An array containing all body parts of this dragon */
	public MultiPartEntityPart[] dragonPartArray;
	public MultiPartEntityPart dragonPartHead = new MultiPartEntityPart(this, "head", 4.0F, 4.0F);
	public MultiPartEntityPart dragonPartBody = new MultiPartEntityPart(this, "body", 2.75f, 2.4f);
	public MultiPartEntityPart dragonPartNeck = new MultiPartEntityPart(this, "throat", 2.75f, 2.4f);
	public MultiPartEntityPart dragonPartTail = new MultiPartEntityPart(this, "tail", 5.0f, 5.0f);

	public EntityTameableDragon(World world) {
		super(world);

		this.dragonPartArray = new MultiPartEntityPart[] { this.dragonPartHead, this.dragonPartBody };

		// override EntityBodyHelper field, which is private and has no setter
		// required to fixate body while sitting. also slows down rotation while
		// standing.
		try {
			ReflectionHelper.setPrivateValue(EntityLiving.class, this, new DragonBodyHelper(this),
					PrivateFields.ENTITYLIVING_BODYHELPER);
		} catch (Exception ex) {
			L.warn("Can't override EntityBodyHelper", ex);
		}

		attackTasks = new EntityAITasks(world != null ? world.profiler : null);

		// set base size
		setSize(BASE_WIDTH, BASE_HEIGHT);

		// enables walking over blocks
		stepHeight = 1;

		// create entity delegates
		addHelper(new DragonBreedHelper(this, DATA_BREED));
		addHelper(new DragonLifeStageHelper(this, DATA_TICKS_SINCE_CREATION));
		addHelper(new DragonReproductionHelper(this, DATA_BREEDER, DATA_REPRO_COUNT));
		addHelper(new DragonBreathHelper(this, DATA_BREATH_WEAPON));
		addHelper(new DragonSoundManager(this));
		addHelper(new DragonInteractHelper(this));

		initDragonInv();

		if (isClient()) {
			addHelper(new DragonParticleHelper(this));
		} else {
			addHelper(new DragonBrain(this));
		}

		moveHelper = new DragonMoveHelper(this);
		aiSit = new EntityAIDragonSit(this);

		// init helpers
		helpers.values().forEach(DragonHelper::applyEntityAttributes);
		animator = new DragonAnimator(this);

	}

	@Override
	protected float updateDistance(float p_110146_1_, float p_110146_2_) {
		// required to fixate body while sitting. also slows down rotation while
		// standing.
		bodyHelper.updateRenderAngles();
		return p_110146_2_;
	}

	@Override
	protected void entityInit() {
		super.entityInit();

		dataManager.register(DATA_FLYING, false);
		dataManager.register(DATA_BREATHING, false);
		dataManager.register(DATA_SADDLED, false);
		dataManager.register(CHESTED, false);
		dataManager.register(IS_MALE, getRNG().nextBoolean());
		dataManager.register(DRAGON_SCALES, Byte.valueOf((byte) 0));
		dataManager.register(ARMOR, 0);
		dataManager.register(BANNERED, false);
		dataManager.register(WANDED, false);
		dataManager.register(HAS_ELDER_STONE, false);
		dataManager.register(HAS_ADJUCATOR_STONE, false);
	}

	@Override
	protected void updateAITasks() {
		attackTasks.onUpdateTasks();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getAttributeMap().registerAttribute(MOVEMENT_SPEED_AIR);
		getAttributeMap().registerAttribute(ATTACK_DAMAGE);
		getEntityAttribute(MOVEMENT_SPEED_AIR).setBaseValue(BASE_AIR_SPEED);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(BASE_GROUND_SPEED);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(BASE_DAMAGE);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(BASE_FOLLOW_RANGE);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(RESISTANCE);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(BASE_ARMOR);
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(BASE_TOUGHNESS);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean(NBT_SADDLED, isSaddled());
		nbt.setInteger(NBT_ARMOR, this.getArmor());
		nbt.setBoolean(NBT_CHESTED, this.isChested());
		nbt.setBoolean(NBT_SHEARED, this.isSheared());
		nbt.setBoolean(NBT_BREATHING, this.isBreathing());
		nbt.setBoolean(NBT_ISMALE, this.isMale());
		nbt.setBoolean(NBT_BANNERED, this.isBannered());
		nbt.setBoolean(NBT_WANDED, this.isWanded());
		nbt.setBoolean(NBT_ELDER, this.canBeElder());
		nbt.setBoolean(NBT_ADJUCATOR, this.canBeAducator());
		nbt.setBoolean("onGround2", this.onGround2);
		writeDragonInventory(nbt);
		helpers.values().forEach(helper -> helper.writeToNBT(nbt));
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.setSaddled(nbt.getBoolean(NBT_SADDLED));
		this.setChested(nbt.getBoolean(NBT_CHESTED));
		this.setSheared(nbt.getBoolean(NBT_SHEARED));
		this.setBreathing(nbt.getBoolean(NBT_BREATHING));
		this.setArmor(nbt.getInteger(NBT_ARMOR));
		this.setMale(nbt.getBoolean(NBT_ISMALE));
		this.setBannered(nbt.getBoolean(NBT_BANNERED));
		this.setWanded(nbt.getBoolean(NBT_WANDED));
		this.setCanBeElder(nbt.getBoolean(NBT_ELDER));
		this.setCanBeAdjucator(nbt.getBoolean(NBT_ADJUCATOR));
		this.onGround2 = nbt.getBoolean("onGround2");
		readDragonInventory(nbt);
		helpers.values().forEach(helper -> helper.readFromNBT(nbt));

	}

	/**
	 * Returns true if the dragon is saddled.
	 */
	public boolean isSaddled() {
		return dataManager.get(DATA_SADDLED);
	}

	/**
	 * Set or remove the saddle of the dragon.
	 */
	public void setSaddled(boolean saddled) {
		L.trace("setSaddled({})", saddled);
		dataManager.set(DATA_SADDLED, saddled);
	}

	// used to be called isChestedLeft
	public boolean isChested() {
		return dataManager.get(CHESTED);
	}

	public void setChested(boolean chested) {
		dataManager.set(CHESTED, chested);
		hasChestVarChanged = true;
	}
	
	public boolean isBannered() {
		return dataManager.get(IS_MALE);
	}

	public void setBannered(boolean male) {
		dataManager.set(IS_MALE, male);
	}
	
	public boolean canBeAducator() {
		return dataManager.get(IS_MALE);
	}

	public void setCanBeAdjucator(boolean male) {
		dataManager.set(IS_MALE, male);
	}
	
	public boolean canBeElder() {
		return dataManager.get(IS_MALE);
	}

	public void setCanBeElder(boolean male) {
		dataManager.set(IS_MALE, male);
	}
	
	public boolean isWanded() {
		return dataManager.get(IS_MALE);
	}

	public void setWanded(boolean male) {
		dataManager.set(IS_MALE, male);
	}
	
	public boolean isMale() {
		return dataManager.get(IS_MALE);
	}

	public void setMale(boolean male) {
		dataManager.set(IS_MALE, male);
	}

	/**
	 * 1 equals iron 2 equals gold 3 equals diamond
	 * 
	 * @return 0 no armor
	 */
	public int getArmor() {
		return this.dataManager.get(ARMOR);
	}

	public void setArmor(int armorType) {
		this.dataManager.set(ARMOR, armorType);
	}

	public boolean canFly() {
		// eggs and hatchlings can't fly
		return !isEgg() && !isHatchling();
	}

	/**
	 * Returns true if the entity is flying.
	 */
	public boolean isFlying() {
		return dataManager.get(DATA_FLYING);
	}

	/**
	 * f Set the flying flag of the entity.
	 */
	public void setFlying(boolean flying) {
		L.trace("setFlying({})", flying);
		dataManager.set(DATA_FLYING, flying);
	}

	/**
	 * Returns true if the entity is breathing.
	 */
	public boolean isBreathing() {
		if (world.isRemote) {
			boolean breathing = this.dataManager.get(DATA_BREATHING);
			this.isBreathingFire = breathing;
			return breathing;
		}
		return isBreathingFire;
	}

	/**
	 * Set the breathing flag of the entity.
	 */
	public void setBreathing(boolean breathing) {
		this.dataManager.set(DATA_BREATHING, breathing);
		if (!world.isRemote) {
			this.isBreathingFire = breathing;
		}
	}

	/**
	 * Called when the mob is falling. Calculates and applies fall damage.
	 */
	@Override
	public void fall(float distance, float damageMultiplier) {
		// ignore fall damage if the entity can fly
		if (!canFly()) {
			super.fall(distance, damageMultiplier);
		}
	}

	public int getTicksSinceLastAttack() {
		return ticksSinceLastAttack;
	}

	/**
	 * returns the pitch of the dragon's body
	 *
	 * @return
	 */
	public float getBodyPitch() {
		return getAnimator().getBodyPitch();
	}

	/**
	 * Returns the distance to the ground while the entity is flying.
	 */
	public double getAltitude() {
		BlockPos groundPos = world.getHeight(new BlockPos(this));
		double altitude = posY - groundPos.getY();
		return altitude;
	}

	/**
	 * Causes this entity to lift off if it can fly.
	 */
	public void liftOff() {
		L.trace("liftOff");
		if (canFly()) {
			// stronger jump for an easier lift-off
			motionY += 0.5;
			inAirTicks += 20;
			jump();
		}
	}

	@Override
	protected float getJumpUpwardsMotion() {
		// stronger jumps for easier lift-offs
		return canFly() ? 1 : super.getJumpUpwardsMotion();
	}

	@SideOnly(Side.CLIENT)
	public void updateBreathing() {
		Minecraft mc = Minecraft.getMinecraft();
		if (hasControllingPlayer(mc.player)) {
			boolean isBreathing = ModKeys.KEY_BREATH.isKeyDown();
			DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonBreath(getEntityId(), isBreathing));
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (world.isRemote) {
			this.updateBreathing();
		}
	}

	@Override
	public void onLivingUpdate() {
		helpers.values().forEach(DragonHelper::onLivingUpdate);
		getBreed().onLivingUpdate(this);

		if (isServer()) {
			final float DUMMY_MOVETIME = 0;
			final float DUMMY_MOVESPEED = 0;
			animator.setMovement(DUMMY_MOVETIME, DUMMY_MOVESPEED);
			float netYawHead = getRotationYawHead() - renderYawOffset;
			animator.setLook(netYawHead, rotationPitch);
			animator.tickingUpdate();
			animator.animate();
			
	//		if(!isMale()) {
	//			((WorldServer)world).spawnParticle(EnumParticleTypes.HEART, posX, posY, posZ, 1, 0.5D, 0.25D, 0.5D, 0.0D);
	//		}
			
			// set home position near owner when tamed
			if (isTamed()) {
				Entity owner = getOwner();
				if (owner != null) {
					setHomePosAndDistance(owner.getPosition(), HOME_RADIUS);
				}
			}

			// delay flying state for 10 ticks (0.5s)
			if (!onGround2) {
				inAirTicks++;
			} else {
				inAirTicks = 0;
			}

			boolean flying = canFly() && inAirTicks > IN_AIR_THRESH && (!isInWater() || !isInLava() && getControllingPlayer() != null);
			if (flying != isFlying()) {

				// notify client
				setFlying(flying);

				// clear tasks (needs to be done before switching the navigator!)
				getBrain().clearTasks();

				// update AI follow range (needs to be updated before creating
				// new PathNavigate!)
				getEntityAttribute(FOLLOW_RANGE).setBaseValue(getDragonSpeed());

				// update pathfinding method
				if (flying) {
					navigator = new PathNavigateFlying(this, world);
				} else {
					navigator = new PathNavigateGround(this, world);
				}

				// tasks need to be updated after switching modes
				getBrain().updateAITasks();
			}
		} else {
			animator.tickingUpdate();
		}

		if (ticksSinceLastAttack >= 0) { // used for jaw animation
			++ticksSinceLastAttack;
			if (ticksSinceLastAttack > 1000) {
				ticksSinceLastAttack = -1; // reset at arbitrary large value
			}
		}

		if (hasChestVarChanged && dragonInv != null && !this.isChested()) {
			for (int i = 3; i < 30; i++) {
				if (!dragonInv.getStackInSlot(i).isEmpty()) {
					if (!world.isRemote) {
						this.entityDropItem(dragonInv.getStackInSlot(i), 1);
					}
					dragonInv.removeStackFromSlot(i);
				}
			}
			hasChestVarChanged = false;
		}

		updateMultipleBoundingBox();
		updateShearing();
		updateDragonEnderCrystal();
		regenerateHealth();
		updateForRiding();

		super.onLivingUpdate();
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	public void onDeath(DamageSource src) {
		super.onDeath(src);
		if (dragonInv != null && !this.world.isRemote && !isEgg()) {
			for (int i = 0; i < dragonInv.getSizeInventory(); ++i) {
				ItemStack itemstack = dragonInv.getStackInSlot(i);
				if (!itemstack.isEmpty()) {
					this.entityDropItem(itemstack, 0.0F);
				}
			}
		}
	}

	/**
	 * Handles entity death timer, experience orb and particle creation
	 */
	@Override
	protected void onDeathUpdate() {
		helpers.values().forEach(DragonHelper::onDeathUpdate);

		// unmount any riding entities
		removePassengers();

		// freeze at place
		motionX = motionY = motionZ = 0;
		rotationYaw = prevRotationYaw;
		rotationYawHead = prevRotationYawHead;

		if (isEgg()) {
			setDead();
		} else {
			// actually delete entity after the time is up
			if (deathTime >= getMaxDeathTime()) {
				setDead();
			}
		}
		deathTime++;
	}

	@Override
	public void setDead() {
		helpers.values().forEach(DragonHelper::onDeath);
		super.setDead();
	}

	@Override
	public String getName() {
		// return custom name if set
		if (hasCustomName()) {
			return getCustomNameTag();
		}

		// return default breed name otherwise
		String entName = EntityList.getEntityString(this);
		String breedName = getBreedType().getName().toLowerCase();
		return I18n.translateToLocal("entity." + entName + "." + breedName + ".name");
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected SoundEvent getAmbientSound() {
		return getSoundManager().getLivingSound();
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected SoundEvent getHurtSound(DamageSource src) {
		return getSoundManager().getHurtSound();
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected SoundEvent getDeathSound() {
		return getSoundManager().getDeathSound();
	}

	/**
	 * Returns the sound this mob makes on swimming.
	 * @TheRPGAdenturer: disabled due to its annoyance while swimming underwater it
	 *                   play it too many times
	 */
	@Override
	protected SoundEvent getSwimSound() {
		return null;
	}

	/**
	 * Plays living's sound at its position
	 */
	@Override
	public void playLivingSound() {
		getSoundManager().playLivingSound();
	}

	@Override
	public void playSound(SoundEvent soundIn, float volume, float pitch) {
		getSoundManager().playSound(soundIn, volume, pitch);
	}

	/**
	 * Plays step sound at given x, y, z for the entity
	 */
	@Override
	protected void playStepSound(BlockPos entityPos, Block block) {
		getSoundManager().playStepSound(entityPos, block);
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		// note: unused, managed in playSound()
		return 1;
	}

	/**
	 * Gets the pitch of living sounds in living entities.
	 */
	@Override
	protected float getSoundPitch() {
		// note: unused, managed in playSound()
		return 1;
	}

	/**
	 * Get number of ticks, at least during which the living entity will be silent.
	 */
	@Override
	public int getTalkInterval() {
		return getSoundManager().getTalkInterval();
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return getBreed().getCreatureAttribute();
	}

	@Nullable
	public EntityLivingBase getOwner2() {
		for (int i = 0; i < world.playerEntities.size();) {
			EntityPlayer entityplayer = world.playerEntities.get(i);
			return entityplayer;
		}
		return null;
	}
	
	@Override
	protected float getWaterSlowDown() {
		return 0.8F;
	}

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
    	ItemStack item = player.getHeldItem(hand);    
        // don't interact with eggs!
        if (isEgg()) { 
            return !this.canBeLeashedTo(player);
        }
        
        // baby dragons are tameable now! :D
        if (this.isChild() && !isTamed() && this.isBreedingItem(item)) {   
            ItemUtils.consumeEquipped(player, this.getBreed().getBreedingItem());
            tamedFor(player, getRNG().nextInt(5) == 0);
            return true;
        }
        
        // inherited interaction
        if (super.processInteract(player, hand)) {
            return true;
        }
           
        return getInteractHelper().interact(player, item);
    }

	public void tamedFor(EntityPlayer player, boolean successful) {
		if (successful) {
			setTamed(true);
			navigator.clearPathEntity(); // replacement for setPathToEntity(null);
			setAttackTarget(null);
			setOwnerId(player.getUniqueID());
			playTameEffect(true);
			world.setEntityState(this, (byte) 7);
		} else {
			playTameEffect(false);
			world.setEntityState(this, (byte) 6);
		}
	}

	public boolean isTamedFor(EntityPlayer player) {
		return isTamed() && isOwner(player);
	}

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed it
	 * (wheat, carrots or seeds depending on the animal type)
	 */
	@Override
	public boolean isBreedingItem(ItemStack item) {
		return getBreed().getBreedingItem() == item.getItem();
	}

	/**
	 * Returns the height of the eyes. Used for looking at other entities.
	 */
	@Override
	public float getEyeHeight() {
		float eyeHeight = height * 0.85F;

		if (isSitting()) {
			eyeHeight *= 0.8f;
		}

		if (isEgg()) {
			eyeHeight = 1.3f;
		}

		return eyeHeight;
	}
	
	/**
	 * Returns the height of the eyes. Used for looking at other entities.
	 * @TheRPGAdventurer duplicated one for firebreathing
	 */
	public float getEyeHeight2() {
		float eyeHeight = 2.75f * 0.85F;

		if (isSitting()) {
			eyeHeight *= 0.8f;
		}

		if (isEgg()) {
			eyeHeight = 1.3f;
		}

		return eyeHeight;
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding this
	 * one.
	 */
	@Override
	public double getMountedYOffset() {
		return (isSitting() ? 1.7f : 2.0f) * getScale();
	}

	/**
	 * Returns render size modifier
	 */
	@Override
	public float getRenderSizeModifier() {
		return getScale();
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities when
	 * colliding.
	 */
	@Override
	public boolean canBePushed() {
		return super.canBePushed() && isEgg();
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn() {
		return false;
	}

	/**
	 * returns true if this entity is by a ladder, false otherwise
	 */
	@Override
	public boolean isOnLadder() {
		// this better doesn't happen...
		return false;
	}

	/**
	 * Drop 0-2 items of this living's type.
	 * 
	 * @param par1
	 *            - Whether this entity has recently been hit by a player.
	 * @param par2
	 *            - Level of Looting used to kill this mob.
	 */
	@Override
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
		super.dropFewItems(wasRecentlyHit, lootingModifier);

		// drop saddle if equipped
		if (isSaddled()) {
			dropItem(Items.SADDLE, 1);
		}
	}

	/**
	 * Called when an entity attacks
	 */
	public boolean attackEntityAsMob(Entity entityIn) {
		boolean attacked = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this),
				(float) getEntityAttribute(ATTACK_DAMAGE).getAttributeValue());

		   if (attacked) {
			   applyEnchantments(this, entityIn);
		   }

		   if (getBreedType() == EnumDragonBreed.WITHER) {
			     ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, 200));
           }		

	  return attacked;
		
	}

	/**
	 * Used to get the hand in which to swing and play the hand swinging animation
	 * when attacking In this case the dragon's jaw
	 * 
	 */
	@Override
	public void swingArm(EnumHand hand) {
		// play eating sound
		playSound(getSoundManager().getAttackSound(), 1, 0.7f);

		// play attack animation
		if (world instanceof WorldServer) {
			((WorldServer) world).getEntityTracker().sendToTracking(this, new SPacketAnimation(this, 0));
		}

		ticksSinceLastAttack = 0;
	}

	/**
	 * 1 equals iron 2 equals gold 3 equals diamond
	 * 
	 * @return 0 no armor
	 */
	public int getArmorResistance() {
		if (getArmor() == 1) {
			return 3;
		}
		if (getArmor() == 2) {
			return 2;
		}
		if (getArmor() == 3) {
			return 5;
		}
		return 0;
	}

	/**
	 * Return whether this entity should be rendered as on fire.
	 */
	@Override
	public boolean canRenderOnFire() {
		return super.canRenderOnFire() && !getBreed().isImmuneToDamage(DamageSource.IN_FIRE);
	}

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	@Override
	public boolean canMateWith(EntityAnimal mate) {
		return getReproductionHelper().canMateWith(mate);
	}

	/**
	 * This function is used when two same-species animals in 'love mode' breed to
	 * generate the new baby animal.
	 */
	@Override
	public EntityAgeable createChild(EntityAgeable mate) {
		return getReproductionHelper().createChild(mate);
	}

	private void addHelper(DragonHelper helper) {
		L.trace("addHelper({})", helper.getClass().getName());
		helpers.put(helper.getClass(), helper);
	}

	private <T extends DragonHelper> T getHelper(Class<T> clazz) {
		return (T) helpers.get(clazz);
	}

	public DragonBreedHelper getBreedHelper() {
		return getHelper(DragonBreedHelper.class);
	}

	public DragonLifeStageHelper getLifeStageHelper() {
		return getHelper(DragonLifeStageHelper.class);
	}

	public DragonReproductionHelper getReproductionHelper() {
		return getHelper(DragonReproductionHelper.class);
	}

	public DragonParticleHelper getParticleHelper() {
		return getHelper(DragonParticleHelper.class);
	}

	public DragonBreathHelper getBreathHelper() {
		return getHelper(DragonBreathHelper.class);
	}

	public DragonAnimator getAnimator() {
		return animator;
	}

	public DragonSoundManager getSoundManager() {
		return getHelper(DragonSoundManager.class);
	}

	public DragonBrain getBrain() {
		return getHelper(DragonBrain.class);
	}

	public DragonInteractHelper getInteractHelper() {
		return getHelper(DragonInteractHelper.class);
	}

	/**
	 * Returns the breed for this dragon.
	 * 
	 * @return breed
	 */
	public EnumDragonBreed getBreedType() {
		return getBreedHelper().getBreedType();
	}

	/**
	 * Sets the new breed for this dragon.
	 * 
	 * @param type
	 *            new breed
	 */
	public void setBreedType(EnumDragonBreed type) {
		getBreedHelper().setBreedType(type);
	}

	public DragonBreed getBreed() {
		return getBreedType().getBreed();
	}

	public double getDragonSpeed() {
		return isFlying() ? BASE_FOLLOW_RANGE_FLYING : BASE_FOLLOW_RANGE;
	}

	@Override
	public boolean canBeSteered() {
		// must always return false or the vanilla movement code interferes
		// with DragonMoveHelper a custom move coe that supports tameable flying entities like a dragonarmor_diamond
		return false;
	}

	@Override
	public void travel(float strafe, float up, float forward) {
		// disable method while flying, the movement is done entirely by
		// moveEntity() and this one just makes the dragon to fall slowly when
		// hovering
		if (!isFlying()) {
			super.travel(strafe, up, forward); 
		}
	}
	
	@Override
	public void move(MoverType type, double x, double y, double z) {
		this.onGround2 = this.isCollidedVertically && y > -20.0D;
		super.move(type, x, y, z);
	}
	
    @Nullable
    public Entity getControllingPassenger() {
    	return this.getPassengers().isEmpty() ? null : (Entity) getPassengers().get(0);		
    }
	
    @Nullable
	public EntityPlayer getControllingPlayer() { 
		Entity entity = this.getPassengers().isEmpty() ? null : (Entity) getPassengers().get(0);
		if (entity instanceof EntityPlayer) { 
			return (EntityPlayer) entity;
		} else {
			return null;
		}
	}
    
    public boolean hasControllingPlayer(EntityPlayer player) {
        return this.getControllingPassenger() != null && this.getControllingPassenger() instanceof EntityPlayer && this.getControllingPassenger().getUniqueID().equals(player.getUniqueID());
    }

	public void setRidingPlayer(EntityPlayer player) {
		L.trace("setRidingPlayer({})", player.getName());
		player.rotationYaw = rotationYaw;
		player.rotationPitch = rotationPitch;
		player.startRiding(this);
	}

	@Override
	public void updatePassenger(Entity passenger) {
		if (this.isPassenger(passenger)) {
			double px = posX;
			double py = posY + getMountedYOffset() + passenger.getYOffset();
			double pz = posZ;

			Vec3d pos = new Vec3d(0, 0, 0);

			// dragon position is the middle of the model and the saddle is on
			// the shoulders, so move player forwards on Z axis relative to the
			// dragon's rotation to fix that
			if (passenger == getPassengers().get(0)) {
				pos = new Vec3d(0  * getScale(), 0.1  * getScale(), 1.0 * getScale());
			} else if (passenger == getPassengers().get(1)) {
				pos = new Vec3d(0.3 * getScale(), 0.2 * getScale(), -0.20 * getScale());
			} else if (passenger == getPassengers().get(2)) {
				pos = new Vec3d(-0.3  * getScale(), 0.2 * getScale(), -0.20  * getScale()); 
			} 			
	        
	    	if(!(passenger instanceof EntityPlayer)) {
	           passenger.rotationYaw = this.rotationYaw;
	           passenger.setRotationYawHead(passenger.getRotationYawHead() + this.rotationYaw);
	           this.applyYawToEntity(passenger); 
	    	}

			pos = pos.rotateYaw((float) Math.toRadians(-renderYawOffset)); // oops
			px += pos.x;
			py += pos.y;
			pz += pos.z;

			passenger.setPosition(px, py, pz);

			// fix rider rotation
			if (passenger == getControllingPlayer()) {
				EntityPlayer rider = getControllingPlayer();
				rider.prevRotationPitch = rider.rotationPitch;
				rider.prevRotationYaw = rider.rotationYaw;
				rider.renderYawOffset = renderYawOffset;
			}
		}
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

	@Override
	public boolean isEntityInvulnerable(DamageSource src) {
		Entity srcEnt = src.getImmediateSource();
		if (srcEnt != null) {
			// ignore own damage
			if (srcEnt == this) {
				return true;
			}

			// ignore damage from riders
			if (isPassenger(srcEnt)) {
				return true;
			}
		}

		// don't drown as egg
		if (src == DamageSource.DROWN && isEgg()) {
			return true;
		}

		return getBreed().isImmuneToDamage(src);
	}

	/**
	 * Returns the entity's health relative to the maximum health.
	 * 
	 * @return health normalized between 0 and 1
	 */
	public double getHealthRelative() {
		return getHealth() / (double) getMaxHealth();
	}

	public int getDeathTime() {
		return deathTime;
	}

	public int getMaxDeathTime() {
		return 120;
	}

	public boolean canBeLeashedTo(EntityPlayer player) {
		return true;
	}

	public void setImmuneToFire(boolean isImmuneToFire) {
		L.trace("setImmuneToFire({})", isImmuneToFire);
		this.isImmuneToFire = isImmuneToFire;
	}

	public void setAttackDamage(double damage) {
		L.trace("setAttackDamage({})", damage);
		getEntityAttribute(ATTACK_DAMAGE).setBaseValue(damage);
	}

	/**
	 * Public wrapper for protected final setScale(), used by DragonLifeStageHelper.
	 * 
	 * @param scale
	 */
	public void setScalePublic(float scale) {
		double posXTmp = posX;
		double posYTmp = posY;
		double posZTmp = posZ;
		boolean onGroundTmp = onGround;

		setScale(scale);

		// workaround for a vanilla bug; the position is apparently not set correcty
		// after changing the entity size, causing asynchronous server/client
		// positioning
		setPosition(posXTmp, posYTmp, posZTmp);

		// otherwise, setScale stops the dragon from landing while it is growing
		onGround = onGroundTmp;
	}

	/**
	 * The age value may be negative or positive or zero. If it's negative, it get's
	 * incremented on each tick, if it's positive, it get's decremented each tick.
	 * Don't confuse this with EntityLiving.getAge. With a negative value the Entity
	 * is considered a child.
	 */
	@Override
	public int getGrowingAge() {
		// adapter for vanilla code to enable breeding interaction
		return isAdult() ? 0 : -1;
	}

	/**
	 * The age value may be negative or positive or zero. If it's negative, it get's
	 * incremented on each tick, if it's positive, it get's decremented each tick.
	 * With a negative value the Entity is considered a child.
	 */
	@Override
	public void setGrowingAge(int age) {
		// managed by DragonLifeStageHelper, so this is a no-op
	}

	/**
	 * Sets the scale for an ageable entity according to the boolean parameter,
	 * which says if it's a child.
	 */
	@Override
	public void setScaleForAge(boolean child) {
		// managed by DragonLifeStageHelper, so this is a no-op
	}

	@Override
	public boolean shouldDismountInWater(Entity rider) {
		return false;
	}

	/**
	 * Get's the appopriate age via ticksSinceCreation for interactions like
	 * appropriate age for riding the dragon appropriate age for opening gui etc.
	 */
	public static int getAppropriateAgeForInteraction() {
		return 35000;
	}

	/**
	 * Returns the size multiplier for the current age.
	 * 
	 * @return scale
	 */
	public float getScale() {
		return getLifeStageHelper().getScale();
	}

	public boolean isEgg() {
		return getLifeStageHelper().isEgg();
	}

	public boolean isHatchling() {
		return getLifeStageHelper().isHatchling();
	}

	public boolean isJuvenile() {
		return getLifeStageHelper().isJuvenile();
	}

	public boolean isAdult() {
		return getLifeStageHelper().isAdult();
	}

	@Override
	public boolean isChild() {
		return !isAdult();
	}

	/**
	 * Checks if this entity is running on a client.
	 * 
	 * Required since MCP's isClientWorld returns the exact opposite...
	 * 
	 * @return true if the entity runs on a client or false if it runs on a server
	 */
	public final boolean isClient() {
		return world.isRemote;
	}

	/**
	 * Checks if this entity is running on a server.
	 * 
	 * @return true if the entity runs on a server or false if it runs on a client
	 */
	public final boolean isServer() {
		return !world.isRemote;
	}

	@Override
	protected ResourceLocation getLootTable() {
		return getBreed().getLootTable();

	}

	protected Item getShearDropItem() {
		switch (this.getBreedType()) {
		case END:
			return ModItems.EnderDragonScales;
		case NETHER:
			return ModItems.NetherDragonScales;
		case FOREST:
			return ModItems.ForestDragonScales;
		case ICE:
			return ModItems.IceDragonScales;
		case FIRE:
			return ModItems.FireDragonScales;
		case SYLPHID:
			return ModItems.WaterDragonScales;
		case AETHER:
			return ModItems.AetherDragonScales;
		case STORM:
			return ModItems.StormDragonScales;
		case SUNLIGHT:
			return ModItems.SunlightDragonScales;
		case ENCHANT:
			return ModItems.EnchantDragonScales;
		default:
			return null;

		}
	}

	public boolean isSheared() {
		return (((Byte) this.dataManager.get(DRAGON_SCALES)).byteValue() & 16) != 0;
	}

	/**
	 * make a dragon sheared if set to true
	 */
	public void setSheared(boolean sheared) {
		byte b0 = ((Byte) this.dataManager.get(DRAGON_SCALES)).byteValue();

		if (sheared) {
			dataManager.set(DRAGON_SCALES, Byte.valueOf((byte) (b0 | 16)));
		} else {
			dataManager.set(DRAGON_SCALES, Byte.valueOf((byte) (b0 & -17)));
		}
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return item != null && item.getItem() == ModTools.diamond_shears && !this.isChild() && !this.isSheared()
				&& ticksShear <= 0;

	}

	/**
	 * when the dragon rotates its head left-right (yaw), how fast does it move?
	 *
	 * @return max yaw speed in degrees per tick
	 */
	public float getHeadYawSpeed() {
		return 10.0F;
	}

	/**
	 * when the dragon rotates its head up-down (pitch), how fast does it move?
	 *
	 * @return max pitch speed in degrees per tick
	 */
	public float getHeadPitchSpeed() {
		return 40;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos,
			int fortune) {
		this.setSheared(true);
		int i = 1 + this.rand.nextInt(2);

		List<ItemStack> ret = new ArrayList<ItemStack>();
		for (int j = 0; j < i; ++j)
			ret.add(new ItemStack(this.getShearDropItem()));
		

		ticksShear = 4500;
		playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
		playSound(ModSounds.ENTITY_DRAGON_GROWL, 1.0F, 1.0F);

		return ret;
	}

	/**
	 * Called when a lightning bolt hits the entity.
	 */
	@Override
	public void onStruckByLightning(EntityLightningBolt lightningBolt) {
		EnumDragonBreed currentType = getBreedType();
		super.onStruckByLightning(lightningBolt);
		Random random = new Random();
		if (currentType == EnumDragonBreed.SKELETON) {
			this.setBreedType(EnumDragonBreed.WITHER);

			if (world.getWorldInfo().isThundering() && currentType == EnumDragonBreed.SKELETON && isSitting()
					|| isEgg()) {
				world.addWeatherEffect(new EntityLightningBolt(this.world, this.posX, this.posY, this.posZ, true));
			}
			this.playSound(SoundEvents.BLOCK_PORTAL_TRIGGER, 2, 1);
			this.playSound(SoundEvents.BLOCK_END_PORTAL_SPAWN, 2, 1);
		}

		addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 35 * 20));
	}

	private void regenerateHealth() {
		if (!isEgg() && this.getHealth() < this.getMaxHealth() && this.ticksExisted % 55 == 0 && !isDead) {
			Random rand = new Random();
			this.heal(rand.nextInt(7));
		}
	}

	private void updateShearing() {
		if (ticksShear <= 0) {
			setSheared(false);
		}
		if (ticksShear >= 0) {
			ticksShear--;
		}
	}

	@Override
	public boolean shouldAttackEntity(EntityLivingBase target, EntityLivingBase owner) {
		if (!target.isChild()) {
			if (target instanceof EntityTameable) {
				EntityTameable tamedEntity = (EntityTameable) target;
				if (tamedEntity.isTamed()) {
					return false;
				} 
			}

			if (target instanceof EntityPlayer) {
				EntityPlayer playertarget = (EntityPlayer) target;
				if (this.isTamed()) {
					return false;
				}
			}

			if (target.hasCustomName()) {
				return false;
			}

		}
		
		return super.shouldAttackEntity(target, owner);
	}

	protected boolean canFitPassenger(Entity passenger) {
		return this.getPassengers().size() < 3;
	}

	private void updateForRiding() { 
     doBlockCollisions();
		List<Entity> list = this.world.getEntitiesInAABBexcluding(this,
				this.getEntityBoundingBox().grow(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D),
				EntitySelectors.getTeamCollisionPredicate(this));

		if (!list.isEmpty() && isSaddled()) {
			boolean flag = !this.world.isRemote;

			for (int j = 0; j < list.size(); ++j) {
				Entity entity = list.get(j);
				if (!entity.isPassenger(this) && !entity.isRiding() && entity instanceof EntityCarriage) {
					if (flag && this.getPassengers().size() < 3 && !entity.isRiding() && (isJuvenile() || isAdult())) {
						entity.startRiding(this);
					} else {
						this.applyEntityCollision(entity); 
					}
				}
			}
		}
		
		if (getControllingPlayer() == null && !isFlying() && isSitting()) {
			removePassengers();
		} else if (!isSaddled()) {
			removePassengers();
		}
	}

	/**
	 * Updates the state of the enderdragon's current endercrystal.
	 */
	private void updateDragonEnderCrystal() {
		if (!isDead) {
			if (this.healingEnderCrystal != null) {
				if (this.healingEnderCrystal.isDead) {
					this.healingEnderCrystal = null;
				} else if (this.ticksExisted % 10 == 0) {
					if (getBreedType() == EnumDragonBreed.END && this.getHealth() < this.getMaxHealth()) {
						this.setHealth(this.getHealth() + 1.0F);
					}
					
					addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 15 * 20));
				}
			}

			if (this.rand.nextInt(10) == 0) {
				List<EntityEnderCrystal> list = this.world.<EntityEnderCrystal>getEntitiesWithinAABB(
						EntityEnderCrystal.class, this.getEntityBoundingBox().grow(32.0D));
				EntityEnderCrystal entityendercrystal = null;
				double d0 = Double.MAX_VALUE;

				for (EntityEnderCrystal entityendercrystal1 : list) {
					double d1 = entityendercrystal1.getDistanceSqToEntity(this);

					if (d1 < d0) {
						d0 = d1;
						entityendercrystal = entityendercrystal1;
					}
				}

				this.healingEnderCrystal = entityendercrystal;
			}
		}
	}

	/**
	 * Credits: AlexThe 666 Ice and Fire
	 */
	public int getIntFromArmor(ItemStack stack) {
		if (!stack.isEmpty() && stack.getItem() != null && stack.getItem() == ModArmour.dragonarmor_iron) {
			return 1;
		}
		if (!stack.isEmpty() && stack.getItem() != null && stack.getItem() == ModArmour.dragonarmor_gold) {
			return 2;
		}
		if (!stack.isEmpty() && stack.getItem() != null && stack.getItem() == ModArmour.dragonarmor_diamond) {
			return 3;
		}

		return 0;
	}

	/**
	 * Credits: AlexThe 666 Ice and Fire
	 */
	public void openGUI(EntityPlayer playerEntity, int guiId) {
		if (!this.world.isRemote && (!this.isBeingRidden() || !this.isPassenger(playerEntity))) {
			playerEntity.openGui(DragonMounts.instance, guiId, this.world, this.getEntityId(), 0, 0);
		}
	}

	/**
	 * Credits: AlexThe 666 Ice and Fire
	 */
	public boolean replaceItemInInventory(int inventorySlot, @Nullable ItemStack itemStackIn) {
		int j = inventorySlot - 500 + 2;
		if (j >= 0 && j < this.dragonInv.getSizeInventory()) {
			this.dragonInv.setInventorySlotContents(j, itemStackIn);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability,
			net.minecraft.util.EnumFacing facing) {
		if (capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) itemHandler;
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability,
			net.minecraft.util.EnumFacing facing) {
		return capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				|| super.hasCapability(capability, facing);
	}
	
	/**
	 * Credits: AlexThe 666 Ice and Fire
	 */
	private void initDragonInv() {
		int numberOfInventoryforChest = 27;
		DragonInventory animalchest = this.dragonInv;
		this.dragonInv = new DragonInventory("dragonInv", 3 + numberOfInventoryforChest, this);
		this.dragonInv.setCustomName(this.getName());
		if (animalchest != null) {
			int i = Math.min(animalchest.getSizeInventory(), this.dragonInv.getSizeInventory());
			for (int j = 0; j < i; ++j) {
				ItemStack itemstack = animalchest.getStackInSlot(j);
				if (!itemstack.isEmpty()) {
					this.dragonInv.setInventorySlotContents(j, itemstack.copy());
				}
			}

			if (world.isRemote) {
				ItemStack saddle = animalchest.getStackInSlot(0);
				ItemStack chest_left = animalchest.getStackInSlot(1);
				ItemStack banner = this.dragonInv.getStackInSlot(2);
				ItemStack dragon_wand = this.dragonInv.getStackInSlot(3);
				DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonArmor(this.getEntityId(), 0,
						saddle != null && saddle.getItem() == Items.SADDLE && !saddle.isEmpty() ? 1 : 0));
				
				DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonArmor(this.getEntityId(), 1,
						chest_left != null && chest_left.getItem() == Item.getItemFromBlock(Blocks.CHEST)
								&& !chest_left.isEmpty() ? 1 : 0));
				
				DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonArmor(this.getEntityId(), 2,
						this.getIntFromArmor(animalchest.getStackInSlot(2))));
				
				DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonArmor(this.getEntityId(), 3,
						banner != null && banner.getItem() == Items.BANNER && !banner.isEmpty() ? 1 : 0));
				
				DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonArmor(this.getEntityId(), 3,
						dragon_wand != null && dragon_wand.getItem() == ModItems.dragon_wand && !dragon_wand.isEmpty() ? 1 : 0));
			
			}
		}
	}

	/**
	 * Credits: AlexThe 666 Ice and Fire
	 */
	public void readDragonInventory(NBTTagCompound nbt) {
		if (dragonInv != null) {
			NBTTagList nbttaglist = nbt.getTagList("Items", 10);
			initDragonInv();
			for (int i = 0; i < nbttaglist.tagCount(); ++i) {
				NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
				int j = nbttagcompound.getByte("Slot") & 255;
				this.dragonInv.setInventorySlotContents(j, new ItemStack(nbttagcompound));
			}
		} else {
			NBTTagList nbttaglist = nbt.getTagList("Items", 10);
			initDragonInv();
			for (int i = 0; i < nbttaglist.tagCount(); ++i) {
				NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
				int j = nbttagcompound.getByte("Slot") & 255;
				this.initDragonInv();
				this.dragonInv.setInventorySlotContents(j, new ItemStack(nbttagcompound));
				ItemStack saddle = dragonInv.getStackInSlot(0);
				ItemStack chest = dragonInv.getStackInSlot(1);
				ItemStack banner = dragonInv.getStackInSlot(2);
				ItemStack dragon_wand = dragonInv.getStackInSlot(3);
				if (world.isRemote) {
					DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonArmor(this.getEntityId(), 0,
							saddle != null && saddle.getItem() == Items.SADDLE && !saddle.isEmpty() ? 1 : 0));
					
					DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonArmor(this.getEntityId(), 1,
							chest != null && chest.getItem() == Item.getItemFromBlock(Blocks.CHEST) && !chest.isEmpty()
									? 1 : 0));
					
					DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonArmor(this.getEntityId(), 2,
							this.getIntFromArmor(dragonInv.getStackInSlot(2))));
					
					DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonArmor(this.getEntityId(), 3,
							banner != null && banner.getItem() == Items.BANNER && !banner.isEmpty() ? 1 : 0));
					
					DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonArmor(this.getEntityId(), 4,
							dragon_wand != null && dragon_wand.getItem() == ModItems.dragon_wand && !dragon_wand.isEmpty() ? 1 : 0));
					
				}
			}
		}
	}

	/**
	 * Credits: AlexThe 666 Ice and Fire
	 */
	public void writeDragonInventory(NBTTagCompound nbt) {
		if (dragonInv != null) {
			NBTTagList nbttaglist = new NBTTagList();
			for (int i = 0; i < this.dragonInv.getSizeInventory(); ++i) {
				ItemStack itemstack = this.dragonInv.getStackInSlot(i);
				if (!itemstack.isEmpty()) {
					NBTTagCompound nbttagcompound = new NBTTagCompound();
					nbttagcompound.setByte("Slot", (byte) i);
					itemstack.writeToNBT(nbttagcompound);
					nbttaglist.appendTag(nbttagcompound);
				}
			}
			nbt.setTag("Items", nbttaglist);
		}
		if (this.getCustomNameTag() != null && !this.getCustomNameTag().isEmpty()) {
			nbt.setString("CustomName", this.getCustomNameTag());
		}
	}

	/**
	 * Credits: AlexThe 666 Ice and Fire
	 */
	public void refreshInventory() {
		ItemStack saddle = this.dragonInv.getStackInSlot(0);
		ItemStack leftChestforInv = this.dragonInv.getStackInSlot(1);
		ItemStack banner = this.dragonInv.getStackInSlot(2);
		ItemStack dragon_wand = this.dragonInv.getStackInSlot(3);
		
		this.setSaddled(saddle != null && saddle.getItem() == Items.SADDLE && !saddle.isEmpty());
		this.setChested(leftChestforInv != null && leftChestforInv.getItem() == Item.getItemFromBlock(Blocks.CHEST) && !leftChestforInv.isEmpty());
		this.setBannered(banner != null && banner.getItem() == Items.BANNER && !banner.isEmpty());
		this.setWanded(dragon_wand != null && dragon_wand.getItem() == ModItems.dragon_wand && !dragon_wand.isEmpty());
		this.setArmor(getIntFromArmor(this.dragonInv.getStackInSlot(2)));
		
		if (this.world.isRemote) {
			DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonArmor(this.getEntityId(), 0,
					saddle != null && saddle.getItem() == Items.SADDLE && !saddle.isEmpty() ? 1 : 0));
			DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonArmor(this.getEntityId(), 1,
					leftChestforInv != null && leftChestforInv.getItem() == Item.getItemFromBlock(Blocks.CHEST) && !leftChestforInv.isEmpty() ? 1 : 0));
			DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonArmor(this.getEntityId(), 2,
					this.getIntFromArmor(this.dragonInv.getStackInSlot(2))));
			DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonArmor(this.getEntityId(), 3,
					banner != null && banner.getItem() == Items.BANNER && !banner.isEmpty() ? 1 : 0));
			DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonArmor(this.getEntityId(), 4,
					dragon_wand != null && dragon_wand.getItem() == ModItems.dragon_wand && !dragon_wand.isEmpty() ? 1 : 0));
		}
	}

	/**
	 * Credits: AlexThe 666 Ice and Fire
	 */
	public class DragonInventory extends ContainerHorseChest {

		public DragonInventory(String inventoryTitle, int slotCount, EntityTameableDragon dragon) {
			super(inventoryTitle, slotCount);
			this.addInventoryChangeListener(new DragonInventoryListener(dragon));
		}
	}

	public class DragonInventoryListener implements IInventoryChangedListener {

		EntityTameableDragon dragon;

		public DragonInventoryListener(EntityTameableDragon dragon) {
			this.dragon = dragon;
		}

		@Override
		public void onInventoryChanged(IInventory invBasic) {
			dragon.refreshInventory();
		}
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {
		Entity sourceEntity = source.getTrueSource();
		if (this.isBeingRidden() && source.getTrueSource() != null && source.getTrueSource().isPassenger(source.getTrueSource())) {
			return false;
		}
		
		if(this.isPassenger(sourceEntity)) {
			return false;
		}

		if (damage >= 20 ) {
			return damage == 4.0f;
		}

		// don't just sit there!
		aiSit.setSitting(false);

		float damageReduction = getArmorResistance() + 3.0F;
		if (getArmorResistance() != 0) {
			damage -= damageReduction;
		}

		return super.attackEntityFrom(source, damage);
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart dragonPart, DamageSource source, float damage) {
		if (this.isBeingRidden() && source.getTrueSource() != null && source.getTrueSource().isPassenger(source.getTrueSource())) {
			return false;
		}

		return this.attackEntityFrom(source, damage);
	}

	public void updateMultipleBoundingBox() {
		DragonLifeStageHelper stage = getLifeStageHelper();
		double hx, hy, hz;
		float angle;
		DragonHeadPositionHelper pos = getAnimator().getDragonHeadPositionHelper();

		angle = (((renderYawOffset + 0) * 3.14159265F) / 180F);
		hx = posX - MathHelper.sin(angle) * 3.0 - pos.head.rotateAngleX * getScale();
		hy = posY + 2 * getScale();
		hz = posZ + MathHelper.cos(angle) * 3.0 + pos.head.rotateAngleZ * getScale();
		dragonPartHead.setPosition(hx * getScale(), hy * getScale(), hz * getScale());
		dragonPartHead.width = dragonPartHead.height = 1.0F * getScale();
		dragonPartHead.onUpdate();

		dragonPartBody.width = (float) (this.width - 0.3 * getScale());
		dragonPartBody.height = (float) (this.height - 0.3 * getScale());
		dragonPartBody.setPosition(posX, posY, posZ);
		dragonPartBody.onUpdate();

		
	}

	/**
	 * Return the Entity parts making up this Entity (currently only for dragons)
	 */
	@Override
	public Entity[] getParts() {
		return dragonPartArray;
	}

}

// /**
// * Gets the gender since booleans return only 2 values (true or false) true ==
// MALE, false == FEMALE
// * 2 genders only dont call me sexist and dont talk to me about political
// correctness
// */
// public boolean isMale() {
// return dataManager.get(IS_MALE);
// }
