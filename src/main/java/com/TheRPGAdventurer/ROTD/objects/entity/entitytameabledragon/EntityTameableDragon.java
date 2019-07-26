/*
c ** 2012 August 13
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.client.gui.GuiHandler;
import com.TheRPGAdventurer.ROTD.client.model.dragon.anim.DragonAnimator;
import com.TheRPGAdventurer.ROTD.inits.*;
import com.TheRPGAdventurer.ROTD.inventory.ContainerDragon;
import com.TheRPGAdventurer.ROTD.network.MessageDragonBreath;
import com.TheRPGAdventurer.ROTD.network.MessageDragonExtras;
import com.TheRPGAdventurer.ROTD.network.MessageDragonInventory;
import com.TheRPGAdventurer.ROTD.objects.blocks.BlockDragonBreedEgg;
import com.TheRPGAdventurer.ROTD.objects.entity.entitycarriage.EntityCarriage;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai.ground.EntityAIDragonSit;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai.path.PathNavigateFlying;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.DragonBreathHelper;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.DragonBreathHelperP;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.DragonBreed;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper.*;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper.util.Pair;
import com.TheRPGAdventurer.ROTD.objects.items.ItemDragonAmulet;
import com.TheRPGAdventurer.ROTD.objects.items.ItemDragonEssence;
import com.TheRPGAdventurer.ROTD.objects.tileentities.TileEntityDragonShulker;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import com.TheRPGAdventurer.ROTD.util.math.MathX;
import com.TheRPGAdventurer.ROTD.util.reflection.PrivateAccessor;
import com.google.common.base.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
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
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.*;

import static net.minecraft.entity.SharedMonsterAttributes.ATTACK_DAMAGE;
import static net.minecraft.entity.SharedMonsterAttributes.FOLLOW_RANGE;

/**
 * Here be dragons
 */
public class EntityTameableDragon extends EntityTameable implements IShearable, PrivateAccessor {

    // base attributes
    public static final double BASE_GROUND_SPEED = 0.4;
    public static final double BASE_AIR_SPEED = 0.9;
    public static final IAttribute MOVEMENT_SPEED_AIR = new RangedAttribute(null, "generic.movementSpeedAir", 0.9, 0.0, Double.MAX_VALUE).setDescription("Movement Speed Air").setShouldWatch(true);
    public static final double BASE_DAMAGE = DragonMountsConfig.BASE_DAMAGE;
    public static final double BASE_ARMOR = DragonMountsConfig.ARMOR;
    public static final double BASE_TOUGHNESS = 30.0D;
    public static final float RESISTANCE = 10.0f;
    public static final double BASE_FOLLOW_RANGE = 70;
    public static final double BASE_FOLLOW_RANGE_FLYING = BASE_FOLLOW_RANGE * 2;
    public static final int HOME_RADIUS = 64;
    public static final double IN_AIR_THRESH = 10;
    private static final Logger L = LogManager.getLogger();

    // data value IDs
    private static final DataParameter<Boolean> DATA_FLYING = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GROWTH_PAUSED = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_SADDLED = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_BREATHING = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_ALT_BREATHING = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GOING_DOWN = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ALLOW_OTHERPLAYERS = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> BOOSTING = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_MALE = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_ALBINO = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> ARMOR = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> HOVER_CANCELLED = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> Y_LOCKED = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ALT_TEXTURE = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FOLLOW_YAW = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Optional<UUID>> DATA_BREEDER = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private static final DataParameter<String> DATA_BREED = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_REPRO_COUNT = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> HUNGER = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DATA_TICKS_SINCE_CREATION = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.VARINT);
    private static final DataParameter<Byte> DRAGON_SCALES = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BYTE);
    private static final DataParameter<ItemStack> BANNER1 = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<ItemStack> BANNER2 = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<ItemStack> BANNER3 = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<ItemStack> BANNER4 = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<Boolean> HAS_ADJUCATOR_STONE = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ELDER_STONE = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Byte> WHISTLE_STATE = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BYTE);
    private static final DataParameter<ItemStack> WHISTLE = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<Boolean> SLEEP = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FIRE_SUPPORT = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<String> DATA_BREATH_WEAPON_TARGET = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_BREATH_WEAPON_MODE = EntityDataManager.createKey(EntityTameableDragon.class, DataSerializers.VARINT);
    public static int ticksShear;
    // server/client delegates
    private final Map<Class, DragonHelper> helpers = new HashMap<>();
    // client-only delegates
    private final DragonBodyHelper dragonBodyHelper = new DragonBodyHelper(this);
    public EntityEnderCrystal healingEnderCrystal;
    public DragonInventory dragonInv;
    public int inAirTicks;
    public int roarTicks;
    protected int ticksSinceLastAttack;
    float damageReduction = (float) getArmorResistance() + 3.0F;
    private boolean hasChestVarChanged = false;
    private boolean isUsingBreathWeapon;
    private boolean altBreathing;
    private boolean isGoingDown;
    private boolean isUnhovered;
    private boolean yLocked;
    private boolean followYaw;
    private DragonAnimator animator;
    private double airSpeedVertical = 0;

    public EntityTameableDragon(World world) {
        super(world);

        // enables walking over blocks
        stepHeight = 1;

        // create entity delegates
        addHelper(new DragonBreedHelper(this, DATA_BREED));
        addHelper(new DragonLifeStageHelper(this, DATA_TICKS_SINCE_CREATION));
        addHelper(new DragonReproductionHelper(this, DATA_BREEDER, DATA_REPRO_COUNT));
        addHelper(new DragonBreathHelper(this, DATA_BREATH_WEAPON_TARGET, DATA_BREATH_WEAPON_MODE));
        addHelper(new DragonHungerHelper(this));
        if (isServer()) addHelper(new DragonBrain(this));

        // set base size
        Pair<Float, Float> adultSize = getBreed().getAdultEntitySize();
        setSize(adultSize.getFirst(), adultSize.getSecond());           //todo: later - update it when breed changes

        // init helpers
        moveHelper = new DragonMoveHelper(this);
        aiSit = new EntityAIDragonSit(this);
        helpers.values().forEach(DragonHelper::applyEntityAttributes);
        animator = new DragonAnimator(this);

        InitializeDragonInventory();
    }

    public static boolean hasInteractItemsEquipped(EntityPlayer player) {
        return DMUtils.hasEquippedUsable(player)
                || DMUtils.hasEquipped(player, ModTools.diamond_shears)
                || DMUtils.hasEquipped(player, ModItems.dragon_wand)
                || DMUtils.hasEquipped(player, ModItems.dragon_whistle)
                || DMUtils.hasEquipped(player, ModItems.Amulet)
                || DMUtils.hasEquipped(player, Items.BONE)
                || DMUtils.hasEquipped(player, Items.STICK)
                || DMUtils.hasEquippedFood(player);
    }

    @Override
    protected float updateDistance(float f1, float f2) {
        dragonBodyHelper.updateRenderAngles();
        return f2;
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        dataManager.register(DATA_FLYING, false);
        dataManager.register(GROWTH_PAUSED, false);
        dataManager.register(DATA_BREATHING, false);
        dataManager.register(DATA_ALT_BREATHING, false);
        dataManager.register(GOING_DOWN, false);
        dataManager.register(DATA_SADDLED, false);
        dataManager.register(CHESTED, false);
        dataManager.register(Y_LOCKED, false);
        dataManager.register(HOVER_CANCELLED, false);
        dataManager.register(ALT_TEXTURE, false);
        dataManager.register(HAS_ELDER_STONE, false);
        dataManager.register(HAS_ADJUCATOR_STONE, false);
        dataManager.register(FIRE_SUPPORT, false);
        dataManager.register(ALLOW_OTHERPLAYERS, false);
        dataManager.register(BOOSTING, false);
        dataManager.register(DRAGON_SCALES, (byte) 0);
        dataManager.register(IS_MALE, getRNG().nextBoolean());
        dataManager.register(IS_ALBINO, getRNG().nextInt(25) == 0);
        dataManager.register(ARMOR, 0);
        dataManager.register(BANNER1, ItemStack.EMPTY);
        dataManager.register(BANNER2, ItemStack.EMPTY);
        dataManager.register(BANNER3, ItemStack.EMPTY);
        dataManager.register(BANNER4, ItemStack.EMPTY);
        dataManager.register(WHISTLE, ItemStack.EMPTY);
        dataManager.register(WHISTLE_STATE, Byte.valueOf((byte) 0));
        //        dataManager.register(SLEEP, false); //unused as of now
        dataManager.register(FOLLOW_YAW, true);
        dataManager.register(DATA_BREATH_WEAPON_TARGET, "");
        dataManager.register(DATA_BREATH_WEAPON_MODE, 0);
        dataManager.register(HUNGER, 100);
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
        nbt.setBoolean("Saddle", isSaddled());
        nbt.setInteger("Armor", this.getArmor());
        nbt.setBoolean("Chested", this.isChested());
        nbt.setBoolean("Sheared", this.isSheared());
        nbt.setBoolean("Breathing", this.isUsingBreathWeapon());
        nbt.setBoolean("projectile", this.isUsingAltBreathWeapon());
        nbt.setBoolean("IsMale", this.isMale());
        nbt.setBoolean("IsAlbino", this.isAlbino());
        nbt.setBoolean("unhovered", this.isUnHovered());
        nbt.setBoolean("followyaw", this.followYaw());
        nbt.setBoolean("firesupport", this.firesupport());
        nbt.setInteger("AgeTicks", this.getLifeStageHelper().getTicksSinceCreation());
        nbt.setInteger("hunger", this.getHunger());
        nbt.setBoolean("boosting", this.boosting());
        nbt.setBoolean("ylocked", this.isYLocked());
        nbt.setBoolean("Elder", this.canBeElder());
        nbt.setBoolean("Adjucator", this.canBeAdjucator());
        nbt.setBoolean("growthpause", this.isGrowthPaused());
        nbt.setBoolean("AllowOtherPlayers", this.allowedOtherPlayers());
        //        nbt.setBoolean("sleeping", this.isSleeping()); //unused as of now
        writeDragonInventory(nbt);
        helpers.values().forEach(helper -> helper.writeToNBT(nbt));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setSaddled(nbt.getBoolean("Saddle"));
        this.setChested(nbt.getBoolean("Chested"));
        this.setSheared(nbt.getBoolean("Sheared"));
        this.setHunger(nbt.getInteger("hunger"));
        this.setfiresupport(nbt.getBoolean("firesupport"));
        this.setGrowthPaused(nbt.getBoolean("growthpause"));
        this.setUsingBreathWeapon(nbt.getBoolean("Breathing"));
        this.setUsingProjectile(nbt.getBoolean("projectile"));
        this.getLifeStageHelper().setTicksSinceCreation(nbt.getInteger("AgeTicks"));
        this.setArmor(nbt.getInteger("Armor"));
        this.setMale(nbt.getBoolean("IsMale"));
        this.setAlbino(nbt.getBoolean("IsAlbino"));
        this.setUnHovered(nbt.getBoolean("unhovered"));
        this.setYLocked(nbt.getBoolean("ylocked"));
        this.setFollowYaw(nbt.getBoolean("followyaw"));
        this.setBoosting(nbt.getBoolean("boosting"));
        //        this.setSleeping(nbt.getBoolean("sleeping")); //unused as of now
        this.setCanBeElder(nbt.getBoolean("Elder"));
        this.setCanBeAdjucator(nbt.getBoolean("Adjucator"));
        this.setToAllowedOtherPlayers(nbt.getBoolean("AllowOtherPlayers"));
        readDragonInventory(nbt);
        helpers.values().forEach(helper -> helper.readFromNBT(nbt));
    }

    /**
     * Returns relative speed multiplier for the vertical flying speed.
     *
     * @return relative vertical speed multiplier
     */
    public double getMoveSpeedAirVert() {
        return this.airSpeedVertical;
    }

    public ItemStack getControllingWhistle() {
        return dataManager.get(WHISTLE);
    }

    public void setControllingWhistle(ItemStack whistle) {
        dataManager.set(WHISTLE, whistle);
    }

    /**
     * Returns true if the dragon is saddled.
     */
    public boolean isSaddled() {
        return dataManager.get(DATA_SADDLED);
    }

    /**
     * Set or remove the saddle of the
     */
    public void setSaddled(boolean saddled) {
        L.trace("setSaddled({})", saddled);
        dataManager.set(DATA_SADDLED, saddled);
    }

    public boolean boosting() {
        return dataManager.get(BOOSTING);
    }

    public void setBoosting(boolean allow) {
        dataManager.set(BOOSTING, allow);
    }

    // used to be called isChestedLeft
    public boolean isChested() {
        return dataManager.get(CHESTED);
    }

    public void setChested(boolean chested) {
        dataManager.set(CHESTED, chested);
        hasChestVarChanged = true;
    }

    public boolean canBeAdjucator() {
        return dataManager.get(HAS_ADJUCATOR_STONE);
    }

    public void setCanBeAdjucator(boolean male) {
        dataManager.set(HAS_ADJUCATOR_STONE, male);
    }

    public boolean canBeElder() {
        return dataManager.get(HAS_ELDER_STONE);
    }

    public void setCanBeElder(boolean male) {
        dataManager.set(HAS_ELDER_STONE, male);
    }

    public ItemStack getBanner1() {
        return dataManager.get(BANNER1);
    }

    public void setBanner1(ItemStack bannered) {
        dataManager.set(BANNER1, bannered);
    }

    public ItemStack getBanner2() {
        return dataManager.get(BANNER2);
    }

    public void setBanner2(ItemStack male) {
        dataManager.set(BANNER2, male);
    }

    public ItemStack getBanner3() {
        return dataManager.get(BANNER3);
    }

    public void setBanner3(ItemStack male) {
        dataManager.set(BANNER3, male);
    }

    public ItemStack getBanner4() {
        return dataManager.get(BANNER4);
    }

    public void setBanner4(ItemStack male) {
        dataManager.set(BANNER4, male);
    }

    public boolean nowhistlecommands() {
        return (dataManager.get(WHISTLE_STATE)) == 0;
    }

    public boolean follow() {
        return (dataManager.get(WHISTLE_STATE)) == 1;
    }

    public boolean circle() {
        return (dataManager.get(WHISTLE_STATE)) == 2;
    }

    public boolean firesupport() {
        return dataManager.get(FIRE_SUPPORT);
    }

    // public boolean isSleeping() {
    //  return dataManager.get(SLEEP);
    // }

    // public void setSleeping(boolean sleeping) {
    //   dataManager.set(SLEEP, sleeping);
    // }

    public void setfiresupport(boolean firesupport) {
        dataManager.set(FIRE_SUPPORT, firesupport);
    }

    public void setnowhistlecommands(boolean nothing) {
        setStateField(0, nothing);
    }

    /**
     * @TheRPGAdventurer thanks AlexThe666
     */
    public void setStateField(int i, boolean newState) {
        byte prevState = dataManager.get(WHISTLE_STATE).byteValue();
        if (newState) {
            setWhistleState((byte) i);
        } else {
            setWhistleState(prevState);
        }
    }

    public byte getWhistleState() {
        return dataManager.get(WHISTLE_STATE).byteValue();
    }

    public void setWhistleState(byte state) {
        dataManager.set(WHISTLE_STATE, state);
    }

    /**
     * Gets the gender since booleans return only 2 values (true or false) true == MALE, false == FEMALE
     * 2 genders only dont call me sexist and dont talk to me about political correctness
     */
    public boolean isMale() {
        return dataManager.get(IS_MALE);
    }

    public void setMale(boolean male) {
        dataManager.set(IS_MALE, male);
    }

    /**
     * set in commands
     */
    public void setOppositeGender() {
        this.setMale(!this.isMale());
    }

    public boolean isAlbino() {
        return dataManager.get(IS_ALBINO);
    }

    public void setAlbino(boolean albino) {
        dataManager.set(IS_ALBINO, albino);
    }

    /**
     * 1 equals iron 2 equals gold 3 equals diamond 4 equals emerald
     *
     * @return 0 no armor
     */
    public int getArmor() {
        return this.dataManager.get(ARMOR);
    }

    public void setArmor(int armorType) {
        world.playSound(posX, posY, posZ, SoundEvents.ENTITY_HORSE_ARMOR, SoundCategory.PLAYERS, 0.5F, 1F, false);
        this.dataManager.set(ARMOR, armorType);
    }

    /**
     * 1 equals iron 2 equals gold 3 equals diamond 4 equals emerald
     *
     * @return 0 no armor
     */
    public double getArmorResistance() {
        switch (getArmor()) {
            case 1:
                return 1.5;
            case 2:
                return 1.4;
            case 3:
                return 1.7;
            case 4:
                return 1.5;
            default:
                return 0;
        }
    }

    public boolean isGrowthPaused() {
        return dataManager.get(GROWTH_PAUSED);
    }

    public void setGrowthPaused(boolean paused) {
        dataManager.set(GROWTH_PAUSED, paused);
    }

    public boolean canFly() {
        // eggs can't fly
        return !isEgg() && !isBaby();
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
    public boolean isUsingBreathWeapon() {
        if (world.isRemote) {
            boolean usingBreathWeapon = this.dataManager.get(DATA_BREATHING);
            this.isUsingBreathWeapon = usingBreathWeapon;
            return usingBreathWeapon;
        }
        return isUsingBreathWeapon;
    }

    /**
     * Set the breathing flag of the entity.
     */
    public void setUsingBreathWeapon(boolean usingBreathWeapon) {
        this.dataManager.set(DATA_BREATHING, usingBreathWeapon);
        if (!world.isRemote) {
            this.isUsingBreathWeapon = usingBreathWeapon;
        }
    }

    /**
     * Returns true if the entity is breathing.
     */
    public boolean isUsingAltBreathWeapon() {
        if (world.isRemote) {
            boolean usingBreathWeapon = this.dataManager.get(DATA_ALT_BREATHING);
            this.altBreathing = usingBreathWeapon;
            return usingBreathWeapon;
        }
        return altBreathing;
    }

    /**
     * Set the breathing flag of the entity.
     */
    public void setUsingAltBreathWeapon(boolean altBreathing) {
        this.dataManager.set(DATA_ALT_BREATHING, altBreathing);
        if (!world.isRemote) {
            this.altBreathing = altBreathing;
        }
    }

    /**
     * Set the breathing flag of the entity.
     */
    public void setUsingProjectile(boolean altBreathing) {
        this.dataManager.set(DATA_ALT_BREATHING, altBreathing);
        if (!world.isRemote) {
            this.altBreathing = altBreathing;
        }
    }

    /**
     * Returns true if the entity is breathing.
     */
    public boolean isGoingDown() {
        if (world.isRemote) {
            boolean goingdown = this.dataManager.get(GOING_DOWN);
            this.isGoingDown = goingdown;
            return isGoingDown;
        }
        return isGoingDown;
    }

    /**
     * Set the breathing flag of the entity.
     */
    public void setGoingDown(boolean goingdown) {
        this.dataManager.set(GOING_DOWN, goingdown);
        if (!world.isRemote) {
            this.isGoingDown = goingdown;
        }
    }

    public boolean altTextures() {
        return this.dataManager.get(ALT_TEXTURE);
    }

    public void setaltTextures(boolean alt) {
        dataManager.set(ALT_TEXTURE, alt);
    }

    public boolean allowedOtherPlayers() {
        return this.dataManager.get(ALLOW_OTHERPLAYERS);
    }

    public void setToAllowedOtherPlayers(boolean allow) {
        dataManager.set(ALLOW_OTHERPLAYERS, allow);
    }

    public boolean isYLocked() {
        if (world.isRemote) {
            boolean yLocked = dataManager.get(Y_LOCKED);
            this.yLocked = yLocked;
            return yLocked;
        }
        return yLocked;
    }

    public void setYLocked(boolean yLocked) {
        dataManager.set(Y_LOCKED, yLocked);
        if (!world.isRemote) {
            this.yLocked = yLocked;
        }
    }

    public boolean isUnHovered() {
        if (world.isRemote) {
            boolean isUnhovered = dataManager.get(HOVER_CANCELLED);
            this.isUnhovered = isUnhovered;
            return isUnhovered;
        }
        return isUnhovered;
    }

    public void setUnHovered(boolean isUnhovered) {
        dataManager.set(HOVER_CANCELLED, isUnhovered);
        if (!world.isRemote) {
            this.isUnhovered = isUnhovered;
        }
    }

    public boolean followYaw() {
        if (world.isRemote) {
            boolean folowYaw = dataManager.get(FOLLOW_YAW);
            this.followYaw = folowYaw;
            return folowYaw;
        }
        return followYaw;
    }

    public void setFollowYaw(boolean folowYaw) {
        dataManager.set(FOLLOW_YAW, folowYaw);
        if (!world.isRemote) {
            this.followYaw = folowYaw;
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
     */
    public float getBodyPitch() {
        return getAnimator().getBodyPitch();
    }

    /**
     * Returns the distance to the ground while the entity is flying.
     */
    public double getAltitude() {
        BlockPos groundPos = world.getHeight(getPosition());
        double altitude = posY - groundPos.getY();
        return altitude;
    }

    /**
     * Causes this entity to lift off if it can fly.
     */
    public void liftOff() {
        L.trace("liftOff");
        if (canFly()) {
            boolean ridden = isBeingRidden();
            // stronger jump for an easier lift-off
            motionY += ridden || (isInWater() && isInLava()) ? 0.7 : 6;
            inAirTicks += ridden || (isInWater() && isInLava()) ? 3.0 : 4;
            jump();
        }
    }

    @Override
    protected float getJumpUpwardsMotion() {
        // stronger jumps for easier lift-offs
        return canFly() ? 1 : super.getJumpUpwardsMotion();
    }

    /**
     * Checks if the blocks below the dragons hitbox is present and solid
     */
    public boolean onSolidGround() {
        for (double y = -3.0; y <= -1.0; ++y) {
            for (double xz = -2.0; xz < 3.0; ++xz) {
                if (isBlockSolid(posX + xz, posY + y, posZ + xz)) return true;
            }
        }
        return false;
    }

    /*
     * Called in onSolidGround()
     */
    private boolean isBlockSolid(double xcoord, double ycoord, double zcoord) {
        BlockPos pos = new BlockPos(xcoord, ycoord, zcoord);
        IBlockState state = world.getBlockState(pos);
        return state.getMaterial().isSolid() || (this.getControllingPlayer() == null && (this.isInWater() || this.isInLava()));
    }

    @Override
    public void onEntityUpdate() {
        if (getRNG().nextInt(1400) == 1 && !isEgg()) roar();
        super.onEntityUpdate();
    }

    @SideOnly(Side.CLIENT)
    public void updateKeys() {
        Minecraft mc = Minecraft.getMinecraft();
        if ((hasControllingPlayer(mc.player) && getControllingPlayer() != null) ||
                (this.getRidingEntity() instanceof EntityPlayer && this.getRidingEntity() != null && this.getRidingEntity().equals(mc.player)) ||
                (getOwner() != null && firesupport()) && isOldEnoughToBreathe()) {
            boolean isBreathing = ModKeys.KEY_BREATH.isKeyDown();
            boolean isBoosting = ModKeys.BOOST.isKeyDown();
            boolean isDown = ModKeys.DOWN.isKeyDown();
            boolean projectile = ModKeys.KEY_PROJECTILE.isPressed();
            boolean unhover = ModKeys.KEY_HOVERCANCEL.isPressed();
            boolean followyaw = ModKeys.FOLLOW_YAW.isPressed();
            boolean locky = ModKeys.KEY_LOCKEDY.isPressed();

            DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonBreath(getEntityId(), isBreathing));
            DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonExtras(getEntityId(), unhover, followyaw, locky, isBoosting, isDown));
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (world.isRemote) {
            this.updateKeys();
            helpers.values().forEach(DragonHelper::onUpdate);
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

            // set home position near owner when tamed
            if (isTamed()) {
                Entity owner = getOwner();
                if (owner != null) {
                    setHomePosAndDistance(owner.getPosition(), HOME_RADIUS);
                }
            }

            // delay flying state for 10 ticks (0.5s)
            if (onSolidGround()) {
                inAirTicks = 0;
            } else {
                inAirTicks++;
            }

            boolean flying = canFly() && inAirTicks > IN_AIR_THRESH && (!isInWater() || !isInLava() && getControllingPlayer() != null);
            if (flying != isFlying()) {

                // notify client
                setFlying(flying);

                // clear tasks (needs to be done before switching the navigator!)
                //			getBrain().clearTasks();

                // update AI follow range (needs to be updated before creating
                // new PathNavigate!)
                getEntityAttribute(FOLLOW_RANGE).setBaseValue(getDragonSpeed());

                // update pathfinding method
                if (isFlying()) {
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

        if (roarTicks >= 0) {
            ++roarTicks;
            if (roarTicks > 1000) {
                roarTicks = -1;
            }
        }


        if (this.getRidingEntity() instanceof EntityLivingBase) {
            EntityLivingBase ridingEntity = (EntityLivingBase) this.getRidingEntity();
            if (ridingEntity.isElytraFlying() && ridingEntity != null) {
                this.setUnHovered(true);
            }
        }
        if (this.ticksExisted % (DragonMountsConfig.hungerDecrement) == 1) {
            if (this.getHunger() > 0) {
                this.setHunger(this.getHunger() - 1);
            }
        }

        if (hasChestVarChanged && dragonInv != null && !this.isChested()) {
            for (int i = ContainerDragon.chestStartIndex; i < 30; i++) {
                if (!dragonInv.getStackInSlot(i).isEmpty()) {
                    if (!world.isRemote) {
                        this.entityDropItem(dragonInv.getStackInSlot(i), 1);
                    }
                    dragonInv.removeStackFromSlot(i);
                }
            }
            hasChestVarChanged = false;
        }

        if (ticksShear <= 0) {
            setSheared(false);
        }

        if (ticksShear >= 0) {
            ticksShear--;
        }

        if (this.isPotionActive(MobEffects.WEAKNESS)) {
            this.removePotionEffect(MobEffects.WEAKNESS);
        }

        if (!isDead) {
            if (this.healingEnderCrystal != null) {
                if (this.healingEnderCrystal.isDead) {
                    this.healingEnderCrystal = null;
                } else if (this.ticksExisted % 10 == 0) {
                    if (this.getHealth() < this.getMaxHealth()) {
                        this.setHealth(this.getHealth() + 1.0F);
                    }

                    addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 15 * 20));
                }
            }

            if (this.rand.nextInt(10) == 0) {
                List<EntityEnderCrystal> list = this.world.getEntitiesWithinAABB(EntityEnderCrystal.class, this.getEntityBoundingBox().grow(32.0D));
                EntityEnderCrystal entityendercrystal = null;
                double d0 = Double.MAX_VALUE;

                for (EntityEnderCrystal entityendercrystal1 : list) {
                    double d1 = entityendercrystal1.getDistanceSq(this);
                    if (d1 < d0) {
                        d0 = d1;
                        entityendercrystal = entityendercrystal1;
                    }
                }

                this.healingEnderCrystal = entityendercrystal;
            }
        }

        doBlockCollisions();
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().grow(0.2, -0.01, 0.2), EntitySelectors.getTeamCollisionPredicate(this));

        if (!list.isEmpty() && isSaddled()) {
            boolean onServer = !this.world.isRemote;

            for (int j = 0; j < list.size(); ++j) {
                Entity entity = list.get(j);
                if (!entity.isPassenger(this) && !entity.isRiding() && entity instanceof EntityCarriage) {
                    if (onServer && this.getPassengers().size() < 5 && !entity.isRiding() && !isBaby() && (isJuvenile() || isAdult())) {
                        entity.startRiding(this);
                    } else {
                        this.applyEntityCollision(entity);
                    }
                }
            }
        }

        if (getControllingPlayer() == null && !isFlying() && isSitting()) {
            removePassengers();
        }

        Random rand = new Random();
        if (this.getBreed().getSneezeParticle() != null && rand.nextInt(700) == 1 && !this.isUsingBreathWeapon() && !isBaby() && !isEgg()) {
            for (int i = -1; i < 2; i++) {
                if (world.isRemote) {
                    double throatPosX = (this.getAnimator().getThroatPosition().x);
                    double throatPosY = (this.getAnimator().getThroatPosition().y + i);
                    double throatPosZ = (this.getAnimator().getThroatPosition().z);
                    world.spawnParticle(this.getBreed().getSneezeParticle(), throatPosX, throatPosY, throatPosZ, 0, 0.3, 0);
                    world.playSound(null, new BlockPos(throatPosX, throatPosY, throatPosZ), ModSounds.DRAGON_SNEEZE, SoundCategory.NEUTRAL, 0.8F, 1);
                }
            }
        }
        super.onLivingUpdate();
    }

    public void spawnBodyParticle(EnumParticleTypes type) {
        double ox, oy, oz;
        float s = this.getScale() * 1.2f;

        switch (type) {
            case EXPLOSION_NORMAL:
                ox = rand.nextGaussian() * s;
                oy = rand.nextGaussian() * s;
                oz = rand.nextGaussian() * s;
                break;

            case CLOUD:
                ox = (rand.nextDouble() - 0.5) * 0.1;
                oy = rand.nextDouble() * 0.2;
                oz = (rand.nextDouble() - 0.5) * 0.1;
                break;

            case REDSTONE:
                ox = 0.8;
                oy = 0;
                oz = 0.8;
                break;

            default:
                ox = 0;
                oy = 0;
                oz = 0;
        }

        // use generic random box spawning
        double x = this.posX + (rand.nextDouble() - 0.5) * this.width * s;
        double y = this.posY + (rand.nextDouble() - 0.5) * this.height * s;
        double z = this.posZ + (rand.nextDouble() - 0.5) * this.width * s;

        this.world.spawnParticle(type, x, y, z, ox, oy, oz);
    }

    public void spawnBodyParticles(EnumParticleTypes type, int baseAmount) {
        int amount = (int) (baseAmount * this.getScale());
        for (int i = 0; i < amount; i++) {
            spawnBodyParticle(type);
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource src) {
        super.onDeath(src);
        if (dragonInv != null && !this.world.isRemote && !isEgg() && !isTamed()) {
            for (int i = 0; i < dragonInv.getSizeInventory(); ++i) {
                ItemStack itemstack = dragonInv.getStackInSlot(i);
                if (!itemstack.isEmpty()) {
                    this.entityDropItem(itemstack, 0.0F);
                }
            }
        }

        if (isTamed()) {
            ItemDragonEssence essence = dragonEssence();
            ItemStack essenceStack = new ItemStack(essence);
            NBTTagCompound nbt = new NBTTagCompound();
            this.writeToNBT(nbt);
            essenceStack.setTagCompound(nbt);

            generateContainer(world, this.getPosition(), essenceStack);
        }

    }

    public void generateContainer(World world, BlockPos pos, ItemStack essenceStack) {
        world.setBlockState(pos, ModBlocks.DRAGONSHULKER.getDefaultState(), 1);
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityDragonShulker) {
            ((TileEntityDragonShulker) tile).setInventorySlotContents(0, essenceStack);
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

        if (isEgg()) setDead();
        else if (deathTime >= getMaxDeathTime()) setDead(); // actually delete entity after the time is up

        if (isClient() && !isEgg() && deathTime < getMaxDeathTime() - 20)
            spawnBodyParticles(EnumParticleTypes.CLOUD, 4);

        deathTime++;
    }

    @Override
    public void setDead() {
        helpers.values().forEach(DragonHelper::onDeath);
        super.setDead();
    }

    @Override
    public ITextComponent getDisplayName() {
        // return custom name if set
        String s = this.getCustomNameTag();
        if (hasCustomName()) {
            return new TextComponentString(s);
        }

        // return default breed name otherwise
        String entName = EntityList.getEntityString(this);
        String breedName = getBreed().getSkin().toLowerCase();
        ITextComponent nameAlbino = new TextComponentTranslation("entity." + entName + "." + "albino." + breedName + ".name");
        ITextComponent name = new TextComponentTranslation("entity." + entName + "." + breedName + ".name");
        return isAlbino() ? nameAlbino : name;
    }

    public void roar() {
        if (!isDead && getBreed().getRoarSoundEvent(this) != null && !isUsingBreathWeapon()) {
            this.roarTicks = 0;
            world.playSound(posX, posY, posZ, getBreed().getRoarSoundEvent(this), SoundCategory.NEUTRAL, MathX.clamp(getScale(), 0.3F, 0.6F), getSoundPitch(), true);
            // sound volume should be between 0 - 1, and scale is also 0 - 1
        }
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected SoundEvent getDeathSound() {
        if (this.isEgg()) return ModSounds.DRAGON_HATCHED;
        else return this.getBreed().getDeathSound();
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    public SoundEvent getLivingSound() {
        if (isEgg() || isUsingBreathWeapon()) return null;
        else return getBreed().getLivingSound(this);
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    public SoundEvent getHurtSound(DamageSource src) {
        if (isEgg()) return ModSounds.DRAGON_HATCHING;
        else return getBreed().getHurtSound();
    }

    public SoundEvent getWingsSound() {
        return getBreed().getWingsSound();
    }

    public SoundEvent getStepSound() {
        return getBreed().getStepSound();
    }

    public SoundEvent getEatSound() {
        return getBreed().getEatSound();
    }

    public SoundEvent getAttackSound() {
        return getBreed().getAttackSound();
    }

    /**
     * Plays living's sound at its position
     */
    public void playLivingSound() {
        SoundEvent sound = getLivingSound();
        if (sound == null && !isEgg() && isUsingBreathWeapon()) {
            return;
        }

        playSound(sound, 0.8f, 1);
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getTalkInterval() {
        return 240;
    }

    /**
     * Client side method for wing animations. Plays wing flapping sounds.
     *
     * @param speed wing animation playback speed
     */
    public void onWingsDown(float speed) {
        if (!isInWater() && isFlying()) {
            // play wing sounds
            float pitch = (1);
            float volume = 0.8f + (getScale() - speed);
            playSound(getWingsSound(), volume, pitch, false);
        }
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    public void playStepSound(BlockPos entityPos, Block block) {
        // no sounds for eggs or underwater action
        if (isEgg() || isInWater() || isOverWater()) return;

        if (isFlying() || isSitting()) return;

        SoundEvent stepSound;
        // baby has quiet steps, larger have stomping sound
        if (isBaby()) {
            SoundType soundType;
            // override sound type if the top block is snowy
            if (world.getBlockState(entityPos.up()).getBlock() == Blocks.SNOW_LAYER)
                soundType = Blocks.SNOW_LAYER.getSoundType();
            else
                soundType = block.getSoundType();
            stepSound = soundType.getStepSound();
        } else {
            stepSound = getStepSound();
        }
        playSound(stepSound, 0.2f, 1f, false);
    }

    public void playSound(SoundEvent sound, float volume, float pitch, boolean local) {
        if (sound == null || isSilent()) {
            return;
        }

        volume *= getVolume(sound);
        pitch *= getSoundPitch();

        if (local) world.playSound(posX, posY, posZ, sound, getSoundCategory(), volume, pitch, false);
        else world.playSound(null, posX, posY, posZ, sound, getSoundCategory(), volume, pitch);
    }

    public void playSound(SoundEvent sound, float volume, float pitch) {
        playSound(sound, volume, pitch, false);
    }

    /**
     * Returns the volume for a sound to play.
     */
    public float getVolume(SoundEvent sound) {
        return MathX.clamp(getScale(), 0.8F, 1.4F);
    }

    /**
     * Returns the sound this mob makes on swimming.
     *
     * @TheRPGAdenturer: disabled due to its annoyance while swimming underwater it
     * played too many times
     */
    @Override
    protected SoundEvent getSwimSound() {
        return null;
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
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return getBreed().getCreatureAttribute();
    }

    @Override
    protected float getWaterSlowDown() {
        return 1.1F;
    }

    public void tamedFor(EntityPlayer player, boolean successful) {
        if (successful) {
            setTamed(true);
            navigator.clearPath(); // replacement for setPathToEntity(null);
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
     * Returns the height level of the eyes. Used for looking at other entities.
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
     * Returns the Y offset from the entity's position for any entity riding this
     * one.
     * May not be necessary since we also override updatePassenger()
     */
    @Override
    public double getMountedYOffset() {
        final int DEFAULT_PASSENGER_NUMBER = 0;
        return getBreed().getAdultMountedPositionOffset(isSitting(), DEFAULT_PASSENGER_NUMBER).y * getScale();
    }

    /**
     * Returns render size modifier for the shadow
     */
    @Override
    public float getRenderSizeModifier() {
        return getScale() / (isChild() ? 0.5F : 1.0F);
//  0.5 isChild() correction is required due to the code in Render::renderShadow which shrinks the shadow for a child
//    if (entityIn instanceof EntityLiving)
//    {
//      EntityLiving entityliving = (EntityLiving)entityIn;
//      f *= entityliving.getRenderSizeModifier();
//
//      if (entityliving.isChild())
//      {
//        f *= 0.5F;
//      }
//    }

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

    public ItemDragonEssence dragonEssence() {
        switch (getBreedType()) {
            case AETHER:
                return ModItems.EssenceAether;
            case ENCHANT:
                return ModItems.EssenceEnchant;
            case END:
                return ModItems.EssenceEnd;
            case FIRE:
                return ModItems.EssenceFire;
            case FOREST:
                return ModItems.EssenceForest;
            case ICE:
                return ModItems.EssenceIce;
            case NETHER:
                return ModItems.EssenceNether;
            case SKELETON:
                return ModItems.EssenceSkeleton;
            case STORM:
                return ModItems.EssenceStorm;
            case SUNLIGHT:
                return ModItems.EssenceSunlight;
            case SYLPHID:
                return ModItems.EssenceWater;
            case TERRA:
                return ModItems.EssenceTerra;
            case WITHER:
                return ModItems.EssenceWither;
            case ZOMBIE:
                return ModItems.EssenceZombie;
            case MOONLIGHT:
                return ModItems.EssenceMoonlight;
            default:
                return ModItems.EssenceEnd;

        }
    }

    /**
     * @deprecated TODO Method used for temporary amulet datafix. REMOVE THIS BEFORE NEXT PATCH!
     */
    public ItemDragonAmulet dragonAmulet() {
        switch (getBreedType()) {
            case AETHER:
                return ModItems.AmuletAether;
            case ENCHANT:
                return ModItems.AmuletEnchant;
            case END:
                return ModItems.AmuletEnd;
            case FIRE:
                return ModItems.AmuletFire;
            case FOREST:
                return ModItems.AmuletForest;
            case ICE:
                return ModItems.AmuletIce;
            case NETHER:
                return ModItems.AmuletNether;
            case SKELETON:
                return ModItems.AmuletSkeleton;
            case STORM:
                return ModItems.AmuletStorm;
            case SUNLIGHT:
                return ModItems.AmuletSunlight;
            case SYLPHID:
                return ModItems.AmuletWater;
            case TERRA:
                return ModItems.AmuletTerra;
            case WITHER:
                return ModItems.AmuletWither;
            case ZOMBIE:
                return ModItems.AmuletZombie;
            case MOONLIGHT:
                return ModItems.AmuletMoonlight;
            default:
                return ModItems.AmuletEnd;

        }
    }

    public Item shearItem() {
        switch (getBreedType()) {
            case AETHER:
                return ModItems.AetherDragonScales;
            case ENCHANT:
                return ModItems.EnchantDragonScales;
            case END:
                return ModItems.EnderDragonScales;
            case FIRE:
                return isMale() ? ModItems.FireDragonScales : ModItems.FireDragonScales2;
            case FOREST:
                return altTextures() ? ModItems.ForestDragonScales2 : ModItems.ForestDragonScales;
            case ICE:
                return ModItems.IceDragonScales;
            case NETHER:
                return isMale() ? ModItems.NetherDragonScales : ModItems.NetherDragonScales2;
            case SKELETON:
                return null;
            case STORM:
                return isMale() ? ModItems.StormDragonScales : ModItems.SunlightDragonScales2;
            case SUNLIGHT:
                return isMale() ? ModItems.SunlightDragonScales : ModItems.SunlightDragonScales2;
            case SYLPHID:
                return ModItems.WaterDragonScales;
            case TERRA:
                return isMale() ? ModItems.TerraDragonScales : ModItems.TerraDragonScales2;
            case WITHER:
                return null;
            case ZOMBIE:
                return ModItems.ZombieDragonScales;
            case MOONLIGHT:
                return ModItems.MoonlightDragonScales;
            default:
                return ModItems.EnderDragonScales;

        }
    }

    /**
     * gets loottable from dragon
     */
    public ResourceLocation lootTable() {
        switch (getBreedType()) {
            case AETHER:
                return DragonMountsLootTables.ENTITIES_DRAGON_AETHER;
            case ENCHANT:
                return DragonMountsLootTables.ENTITIES_DRAGON_ENCHANT;
            case END:
                return DragonMountsLootTables.ENTITIES_DRAGON_END;
            case FIRE:
                return isMale() ? DragonMountsLootTables.ENTITIES_DRAGON_FIRE : DragonMountsLootTables.ENTITIES_DRAGON_FIRE2;
            case FOREST:
                return altTextures() ? DragonMountsLootTables.ENTITIES_DRAGON_FOREST2 : DragonMountsLootTables.ENTITIES_DRAGON_FOREST;
            case ICE:
                return DragonMountsLootTables.ENTITIES_DRAGON_ICE;
            case NETHER:
                return isMale() ? DragonMountsLootTables.ENTITIES_DRAGON_NETHER : DragonMountsLootTables.ENTITIES_DRAGON_NETHER2;
            case SKELETON:
                return DragonMountsLootTables.ENTITIES_DRAGON_SKELETON;
            case STORM:
                return isMale() ? DragonMountsLootTables.ENTITIES_DRAGON_STORM : DragonMountsLootTables.ENTITIES_DRAGON_STORM2;
            case SUNLIGHT:
                return isMale() ? DragonMountsLootTables.ENTITIES_DRAGON_SUNLIGHT : DragonMountsLootTables.ENTITIES_DRAGON_SUNLIGHT2;
            case SYLPHID:
                return DragonMountsLootTables.ENTITIES_DRAGON_WATER;
            case TERRA:
                return isMale() ? DragonMountsLootTables.ENTITIES_DRAGON_TERRA : DragonMountsLootTables.ENTITIES_DRAGON_TERRA2;
            case WITHER:
                return LootTableList.ENTITIES_WITHER_SKELETON;
            case ZOMBIE:
                return DragonMountsLootTables.ENTITIES_DRAGON_ZOMBIE;
            case MOONLIGHT:
                return DragonMountsLootTables.ENTITIES_DRAGON_MOONLIGHT;
            default:
                return DragonMountsLootTables.ENTITIES_DRAGON_END;

        }
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

    public void setImmuneToFire(boolean isImmuneToFire) {
        L.trace("setImmuneToFire({})", isImmuneToFire);
        this.isImmuneToFire = isImmuneToFire;
    }

    public void setAttackDamage(double damage) {
        L.trace("setAttackDamage({})", damage);
        getEntityAttribute(ATTACK_DAMAGE).setBaseValue(damage);
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

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        Entity sourceEntity = source.getTrueSource();

        if (source != DamageSource.IN_WALL) {
            // don't just sit there!
            this.getAISit().setSitting(false);
        }

        if (this.isBeingRidden() && source.getTrueSource() != null && source.getTrueSource().isPassenger(source.getTrueSource()) && damage < 1) {
            return false;
        }

        if (getRidingCarriage() != null && getRidingCarriage().isPassenger(sourceEntity)) {
            return false;
        }

        if (!world.isRemote && source.getTrueSource() != null && this.getRNG().nextInt(4) == 0 && !isEgg()) {
            this.roar();
        }

        if (isBaby() && isJumping) {
            return false;
        }

        if (sourceEntity != null && sourceEntity.isPassenger(this)) {
            return false;
        }

        //when killed with damage greater than 17 cause the game to crash
        if (damage >= 17 && (source != DamageSource.GENERIC || source != DamageSource.OUT_OF_WORLD)) {
            return damage == 8.0f;
        }

        if (getArmorResistance() != 0) {
            damage -= damageReduction;
        }

        return super.attackEntityFrom(source, damage);
    }

    /**
     * Called when an entity attacks
     */
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean attacked = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getEntityAttribute(ATTACK_DAMAGE).getAttributeValue());

        if (attacked) {
            applyEnchantments(this, entityIn);
        }

        if (!this.nowhistlecommands()) {
            return false;
        }

        if (getBreedType() == EnumDragonBreed.WITHER) {
            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, 200));
        }

        return attacked;
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
                if (this.isTamedFor(playertarget)) {
                    return false;
                }
            }
        }

        return super.shouldAttackEntity(target, owner);
    }

    /**
     * Used to get the hand in which to swing and play the hand swinging animation
     * when attacking In this case the dragon's jaw
     */
    @Override
    public void swingArm(EnumHand hand) {
        // play eating sound
        playSound(getAttackSound(), 1, 0.7f);

        // play attack animation
        if (world instanceof WorldServer) {
            ((WorldServer) world).getEntityTracker().sendToTracking(this, new SPacketAnimation(this, 0));
        }

        ticksSinceLastAttack = 0;
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
        EntityTameableDragon parent1 = this;
        EntityTameableDragon parent2 = (EntityTameableDragon) mate;

        if (parent1.isMale() && !parent2.isMale() || !parent1.isMale() && parent2.isMale()) {
            return getReproductionHelper().createChild(parent1.isMale() ? mate : parent1);
        } else {
            return null;
        }
    }

    private void addHelper(DragonHelper helper) {
        L.trace("addHelper({})", helper.getClass().getName());
        helpers.put(helper.getClass(), helper);
    }

    @SuppressWarnings("unchecked")
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

    public DragonBreathHelper getBreathHelper() {
        return getHelper(DragonBreathHelper.class);
    }

    public DragonBreathHelperP getBreathHelperP() {  // enable compilation only
        throw new UnsupportedOperationException();
        //return getHelper(DragonBreathHelperP.class);
    }

    public DragonAnimator getAnimator() {
        return animator;
    }

    public DragonBrain getBrain() {
        return getHelper(DragonBrain.class);
    }

    /**
     * Returns the breed for this
     *
     * @return breed
     */
    public EnumDragonBreed getBreedType() {
        return getBreedHelper().getBreedType();
    }

    /**
     * Sets the new breed for this
     *
     * @param type new breed
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

    public void updateIntendedRideRotation(EntityPlayer rider) {
        if (isUsingBreathWeapon()) this.rotationYawHead = this.renderYawOffset;
        ;
        boolean hasRider = this.hasControllingPlayer(rider);
        if (hasRider && rider.moveStrafing == 0) {
            this.rotationYaw = rider.rotationYaw;
            this.prevRotationYaw = this.rotationYaw;
            this.rotationPitch = rider.rotationPitch;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.renderYawOffset = this.rotationYaw;
        }
    }

    @Override
    public boolean canBeSteered() {
    //   must always return false or the vanilla movement code interferes
    //   with DragonMoveHelper
        return false;
    }
//
//    public boolean canBeSteered() {
//        return true;
//    }

    @Override
    public void travel(float strafe, float forward, float vertical) {
        // disable method while flying, the movement is done entirely by
        // moveEntity() and this one just makes the dragon to fall slowly when
        // moveEntity() and this one just makes the dragon to fall slowly when
        // hovering
        if (!isFlying()) {
            super.travel(strafe, forward, vertical);
        }

//        EntityPlayer rider = getControllingPlayer();
//        if (rider != null) {
//            if (isFlying()) {
//                if (rider.moveForward > 0) {
//                    rotationYaw = rider.rotationYaw;
//                    prevRotationYaw = rider.prevRotationYaw;
//                    motionX *= 1.2;
//                    motionY *= isGoingDown() ? -1.2 : 1.2;
//                    motionZ *= 1.2;
//                    forward = rider.moveForward;
//                    strafe = rider.moveStrafing;
//                    super.travel(strafe, vertical = 0, forward);
//                    return;
//                }
//            } else {
//                if (entityIsJumping(rider)) liftOff();
//                super.travel(strafe, vertical, forward);
//            }
//        }
//
//        super.travel(strafe, forward, vertical);
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : getPassengers().get(0);
    }

    /**
     * Biased method of getControllingPassenger so that only players can control the dragon
     *
     * @return player on the front
     */
    @Nullable
    public EntityPlayer getControllingPlayer() {
        Entity entity = this.getPassengers().isEmpty() ? null : getPassengers().get(0);
        if (entity instanceof EntityPlayer) {
            return (EntityPlayer) entity;
        } else {
            return null;
        }
    }

    /**
     * gets the passengers and check if there is any carriages
     *
     * @return a riding carriage
     */
    @Nullable
    public Entity getRidingCarriage() {
        List<Entity> entity = this.getPassengers().isEmpty() ? null : this.getPassengers();
        if (entity instanceof EntityCarriage) {
            return (EntityCarriage) entity;
        } else {
            return null;
        }
    }

    /**
     * Gets a controlling player along with clientside
     *
     * @param player
     * @return
     */
    public boolean hasControllingPlayer(EntityPlayer player) {
        return this.getControllingPassenger() != null && this.getControllingPassenger() instanceof EntityPlayer && this.getControllingPassenger().getUniqueID().equals(player.getUniqueID());
    }

    /**
     * sets the riding player alongide with its rotationYaw and rotationPitch
     *
     * @param player
     */
    public void setRidingPlayer(EntityPlayer player) {
        L.trace("setRidingPlayer({})", player.getName());
        player.rotationYaw = rotationYaw;
        player.rotationPitch = rotationPitch;
        player.startRiding(this);
    }

    public boolean isRidingAboveGround(Entity entityBeingRidden) {
        int groundPos = world.getHeight(getPosition()).getY();
        double altitude = entityBeingRidden.posY - groundPos;
        return altitude > 2.0;
    }

    @Override
    public void updateRidden() {
        Entity riding = this.getRidingEntity();
        if (this.isRiding() && riding.isSneaking() || this.isDead || !isBaby()) {
            this.dismountRidingEntity();
        } else {
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
            this.onUpdate();
            if (this.isRiding()) this.updateRiding((EntityLivingBase) riding);
        }
    }

    /**
     * This code is called when the dragon is riding on the shoulder of the player. Credits: Ice and Fire
     *
     * @param riding
     */
    public void updateRiding(EntityLivingBase riding) {
        if (riding != null && riding.isPassenger(this) && riding instanceof EntityPlayer) {
            int index = riding.getPassengers().indexOf(this);
            float radius = (index == 2 ? 0F : 0.4F) + (((EntityPlayer) riding).isElytraFlying() ? 2 : 0);
            float angle = (0.01745329251F * ((EntityPlayer) riding).renderYawOffset) + (index == 1 ? -90 : index == 0 ? 90 : 0);
            double extraX = (double) (radius * MathHelper.sin((float) (Math.PI + angle)));
            double extraZ = (double) (radius * MathHelper.cos(angle));
            double extraY = (!riding.isSneaking() ? 1.4D : 1.1D) + (index == 2 ? 0.4D : 0D);
            this.rotationYaw = riding.rotationYaw;
            this.prevRotationYaw = riding.prevRotationYaw;
            this.renderYawOffset = riding.renderYawOffset;
            this.rotationYawHead = riding.rotationYawHead;
            this.prevRotationYawHead = riding.prevRotationYawHead;
            this.rotationPitch = riding.rotationPitch;
            this.prevRotationPitch = riding.prevRotationPitch;
            this.setPosition(riding.posX + extraX, riding.posY + extraY, riding.posZ + extraZ);
            this.setFlying(isRidingAboveGround(riding) && !((EntityPlayer) riding).capabilities.isFlying && !riding.onGround || riding.isElytraFlying());
        }
    }

    /**
     * Arrays Starts at 0, hope a new coder wont be confused with < and <=
     *
     * @param passenger
     * @return
     */
    protected boolean canFitPassenger(Entity passenger) {
        return this.getPassengers().size() < 5;
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
     * This code is called when the passenger is riding on the dragon
     *
     * @param passenger
     */
    @Override
    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            List<Entity> passengers = getPassengers();
            int passengerNumber = passengers.indexOf(passenger);
            if (passengerNumber < 0) {  // should never happen!
                DragonMounts.loggerLimit.error_once("Logic error- passenger not found");
                return;
            }

            Vec3d mountedPositionOffset = getBreed().getAdultMountedPositionOffset(isSitting(), passengerNumber);

//      // todo remove (debugging only)
//      mountedPositionOffset = new Vec3d(DebugSettings.getDebugParameter("x"),
//                                        DebugSettings.getDebugParameter("y"),
//                                        DebugSettings.getDebugParameter("z"));
//      System.out.println("MountedOffset:" + mountedPositionOffset);

            double dragonScaling = getScale(); //getBreed().getAdultModelRenderScaleFactor() * getScale();

            mountedPositionOffset = mountedPositionOffset.scale(dragonScaling);
            mountedPositionOffset = mountedPositionOffset.rotateYaw((float) Math.toRadians(-renderYawOffset)); // oops
            mountedPositionOffset = mountedPositionOffset.addVector(0, passenger.getYOffset(), 0);

            if (!(passenger instanceof EntityPlayer)) {
                passenger.rotationYaw = this.rotationYaw;
                passenger.setRotationYawHead(passenger.getRotationYawHead() + this.rotationYaw);
                this.applyYawToEntity(passenger);
            }
            Vec3d passengerPosition = mountedPositionOffset.addVector(this.posX, this.posY, this.posZ);
            passenger.setPosition(passengerPosition.x, passengerPosition.y, passengerPosition.z);

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

    public boolean isBaby() {
        return getLifeStageHelper().isBaby();
    }

    public boolean isOldEnoughToBreathe() {
        return getLifeStageHelper().isOldEnoughToBreathe();
    }

    public boolean isJuvenile() {
        return getLifeStageHelper().isJuvenile();
    }

    public boolean isAdult() {
        return getLifeStageHelper().isFullyGrown();
    }

    @Override
    public boolean isChild() {
        return getLifeStageHelper().isBaby();
    }

    /**
     * Checks if this entity is running on a client.
     * <p>
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
    public boolean shouldDismountInWater(Entity rider) {
        return false;
    }

    public boolean canBeLeashedTo(EntityPlayer player) {
        return true;
    }

    public int getHunger() {
        return dataManager.get(HUNGER);
    }

    public void setHunger(int hunger) {
        this.dataManager.set(HUNGER, Math.min(100, hunger));
    }

    @Override
    protected ResourceLocation getLootTable() {
        // dont drop loot if tamed to make them use shears instead
        if (!isTamed() && !isEgg() || !isBaby()) {
            return lootTable();
        } else {
            return null;
        }
    }

    public boolean isSheared() {
        return (this.dataManager.get(DRAGON_SCALES).byteValue() & 16) != 0;
    }

    /**
     * make a dragon sheared if set to true
     */
    public void setSheared(boolean sheared) {
        byte b0 = this.dataManager.get(DRAGON_SCALES).byteValue();

        if (sheared) {
            dataManager.set(DRAGON_SCALES, Byte.valueOf((byte) (b0 | 16)));
        } else {
            dataManager.set(DRAGON_SCALES, Byte.valueOf((byte) (b0 & -17)));
        }
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
        return item != null && item.getItem() == ModTools.diamond_shears && (isJuvenile() || isAdult()) && ticksShear <= 0;
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
        this.setSheared(true);
        int i = 2 + this.rand.nextInt(3);

        List<ItemStack> ret = new ArrayList<ItemStack>();
        for (int j = 0; j < i; ++j)
            ret.add(new ItemStack(shearItem()));

        ticksShear = 3000;

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
        if (currentType == EnumDragonBreed.SKELETON) {
            this.setBreedType(EnumDragonBreed.WITHER);

            this.playSound(SoundEvents.BLOCK_PORTAL_TRIGGER, 2, 1);
            this.playSound(SoundEvents.BLOCK_END_PORTAL_SPAWN, 2, 1);
        }

        if (currentType == EnumDragonBreed.SYLPHID) {
            this.setBreedType(EnumDragonBreed.STORM);

            this.playSound(SoundEvents.BLOCK_PORTAL_TRIGGER, 2, 1);
            this.playSound(SoundEvents.BLOCK_END_PORTAL_SPAWN, 2, 1);
        }

        addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 35 * 20));
    }


    /**
     * is the dragon allowed to be controlled by other players? Uses dragon is not owned status messages
     *
     * @param player
     * @return
     */
    public boolean isAllowed(EntityPlayer player) {
        // can Interact when holding food, player can feed dragon whether they are allowed or not
        boolean hasFood = player.getHeldItemMainhand().getItem() instanceof ItemFood;

        if (!this.isTamed() && !hasFood && !isEgg()) {
            player.sendStatusMessage(new TextComponentTranslation("dragon.notTamed"), true);
            return this.isTamedFor(player);
        } else if (!this.allowedOtherPlayers() && !this.isTamedFor(player) && this.isTamed() && !(this.getHunger() < 100 && hasFood)) {
            player.sendStatusMessage(new TextComponentTranslation("dragon.locked"), true);
            return this.isTamedFor(player);
        } else return true;
    }

    private void eatEvent(Item item) {
        playSound(this.getEatSound(), 1f, 0.75f);
        double motionX = this.getRNG().nextGaussian() * 0.07D;
        double motionY = this.getRNG().nextGaussian() * 0.07D;
        double motionZ = this.getRNG().nextGaussian() * 0.07D;
        Vec3d pos = this.getAnimator().getThroatPosition();

        if (world.isRemote) {
            world.spawnParticle(EnumParticleTypes.ITEM_CRACK, pos.x, pos.y, pos.z, motionX, motionY, motionZ, Item.getIdFromItem(item));
        }
    }

    /**
     * Called when a player right clicks a mob. e.g. gets milk from a cow, gets into the saddle on a pig, ride a dragon.
     */
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        /*
         * Turning it to block
         */
        if (isEgg() && player.isSneaking()) {
            world.playSound(player, getPosition(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.PLAYERS, 0.5F, 1);
            world.setBlockState(getPosition(), BlockDragonBreedEgg.DRAGON_BREED_EGG.getStateFromMeta(getBreedType().getMeta()));
            setDead();
            return true;
        }

        /*
         * Dragon Riding The Player
         */
        if (isAllowed(player) && !isSitting() && this.isBaby() && !hasInteractItemsEquipped(player) && !player.isSneaking() && player.getPassengers().size() < 3) {
            this.setAttackTarget(null);
            this.getNavigator().clearPath();
            this.getAISit().setSitting(false);
            this.startRiding(player, true);
            return true;
        }

        // prevent doing any interactions when a hatchling rides you, the hitbox could block the player's raytraceresult for rightclick
        if (player.isPassenger(this)) return false;

        if (this.isServer() && !this.isEgg()) {
            if (isAllowed(player)) {
                /*
                 * Player Riding the Dragon
                 */
                if (this.isTamed() && this.isSaddled() && (this.isAdult() || this.isJuvenile()) && !player.isSneaking() && !hasInteractItemsEquipped(player) && this.getPassengers().size() < 3) {
                    this.setRidingPlayer(player);
                }

                /*
                 * GUI
                 */
                if (player.isSneaking() && !hasInteractItemsEquipped(player)) {
                    // Dragon Inventory
                    this.openGUI(player, GuiHandler.GUI_DRAGON);
                }
            }

            /*
             * Sit
             */
            if (this.isTamed() && (DMUtils.hasEquipped(player, Items.STICK) || DMUtils.hasEquipped(player, Items.BONE)) && this.onGround) {
                this.getAISit().setSitting(!this.isSitting());
                this.getNavigator().clearPath();
            }
        }

        /*
         * Consume
         */
        if (stack != null && !stack.isEmpty() && stack.getItem() instanceof ItemFood) {
            ItemFood food = (ItemFood) stack.getItem();
            if (food.isWolfsFavoriteMeat() ||
                    DMUtils.hasEquipped(player, Items.PORKCHOP) ||
                    DMUtils.hasEquipped(player, Items.COOKED_PORKCHOP) ||
                    DMUtils.hasEquipped(player, Items.BEEF) ||
                    DMUtils.hasEquipped(player, Items.CHICKEN) ||
                    DMUtils.hasEquipped(player, Items.COOKED_CHICKEN) ||
                    DMUtils.hasEquipped(player, Items.COOKED_BEEF) ||
                    DMUtils.hasEquipped(player, Items.RABBIT) ||
                    DMUtils.hasEquipped(player, Items.COOKED_RABBIT) ||
                    DMUtils.hasEquipped(player, Items.MUTTON) ||
                    DMUtils.hasEquipped(player, Items.COOKED_MUTTON) ||
                    DMUtils.hasEquipped(player, Items.FISH) ||
                    DMUtils.hasEquipped(player, Items.COOKED_FISH) ||
                    DMUtils.hasEquipped(player, Items.ROTTEN_FLESH) ||
                    DMUtils.hasEquippedOreDicFish(player)) {

                // Taming
                if (!this.isTamed()) {
                    consumeItemFromStack(player, stack);
                    eatEvent(stack.getItem());
                    tamedFor(player, this.getRNG().nextInt(4) == 0);
                    return true;
                }

                //  hunger
                if (this.getHunger() >= 0) {
                    consumeItemFromStack(player, stack);
                    eatEvent(stack.getItem());
                    setHunger(this.getHunger() + (DMUtils.getFoodPoints(player)));
                    return true;
                }
            }

            // Mate
            if ((DMUtils.hasEquipped(player, getBreed().getBreedingItem()) || DMUtils.hasEquippedOreDicFish(player)) && this.isAdult() && !this.isInLove()) {
                setInLove(player);
                eatEvent(stack.getItem());
                consumeItemFromStack(player, stack);
                return true;
            }

            // Stop Growth
            if (DMUtils.hasEquipped(player, getBreed().getShrinkingFood()) && !isGrowthPaused() && isTamedFor(player)) {
                setGrowthPaused(true);
                eatEvent(stack.getItem());
                playSound(SoundEvents.ENTITY_PLAYER_BURP, 1f, 0.8F);
                player.sendStatusMessage(new TextComponentTranslation("dragon.growth.paused"), true);
                consumeItemFromStack(player, stack);
                return true;
            }

            // Continue growth
            if (DMUtils.hasEquipped(player, getBreed().getGrowingFood()) && isGrowthPaused() && isTamedFor(player)) {
                setGrowthPaused(false);
                eatEvent(stack.getItem());
                playSound(SoundEvents.ENTITY_PLAYER_BURP, 1, 0.8F);
                consumeItemFromStack(player, stack);
                return true;
            }
        }

        return false;
    }

    /**
     * Credits: AlexThe 666 Ice and Fire
     */
    public void openGUI(EntityPlayer playerEntity, int guiId) {
        if (!this.world.isRemote && (!this.isPassenger(playerEntity))) {
            playerEntity.openGui(DragonMounts.instance, guiId, this.world, this.getEntityId(), 0, 0);
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

        if (!stack.isEmpty() && stack.getItem() != null && stack.getItem() == ModArmour.dragonarmor_emerald) {
            return 4;
        }

        return 0;
    }

    /**
     * Credits: AlexThe 666 Ice and Fire
     */
    private void InitializeDragonInventory() {
        int numberOfInventoryforChest = 27;
        int numberOfPlayerArmor = 5;
        DragonInventory dragonInv = this.dragonInv;
        this.dragonInv = new DragonInventory("dragonInv", 6 + numberOfInventoryforChest + 6 + numberOfPlayerArmor, this);
        this.dragonInv.setCustomName(this.getName());
        if (dragonInv != null) {
            int i = Math.min(dragonInv.getSizeInventory(), this.dragonInv.getSizeInventory());
            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = dragonInv.getStackInSlot(j);
                if (!itemstack.isEmpty()) {
                    this.dragonInv.setInventorySlotContents(j, itemstack.copy());
                }
            }

            if (world.isRemote) {
                ItemStack saddle = dragonInv.getStackInSlot(0);
                ItemStack chest_left = dragonInv.getStackInSlot(1);
                ItemStack banner1 = this.dragonInv.getStackInSlot(31);
                ItemStack banner2 = this.dragonInv.getStackInSlot(32);
                ItemStack banner3 = this.dragonInv.getStackInSlot(33);
                ItemStack banner4 = this.dragonInv.getStackInSlot(34);

                DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 0, saddle != null && saddle.getItem() == Items.SADDLE && !saddle.isEmpty() ? 1 : 0));

                DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 1, chest_left != null && chest_left.getItem() == Item.getItemFromBlock(Blocks.CHEST) && !chest_left.isEmpty() ? 1 : 0));

                DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 2, this.getIntFromArmor(dragonInv.getStackInSlot(2))));

                DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 31, banner1 != null && banner1.getItem() == Items.BANNER && !banner1.isEmpty() ? 1 : 0));

                DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 32, banner2 != null && banner2.getItem() == Items.BANNER && !banner2.isEmpty() ? 1 : 0));

                DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 33, banner3 != null && banner3.getItem() == Items.BANNER && !banner3.isEmpty() ? 1 : 0));

                DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 34, banner4 != null && banner4.getItem() == Items.BANNER && !banner4.isEmpty() ? 1 : 0));

            }
        }
    }

    /**
     * Credits: AlexThe 666 Ice and Fire
     */
    public void readDragonInventory(NBTTagCompound nbt) {
        if (dragonInv != null) {
            NBTTagList nbttaglist = nbt.getTagList("Items", 10);
            InitializeDragonInventory();
            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound.getByte("Slot") & 255;
                this.dragonInv.setInventorySlotContents(j, new ItemStack(nbttagcompound));
            }
        } else {
            NBTTagList nbttaglist = nbt.getTagList("Items", 10);
            InitializeDragonInventory();
            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound.getByte("Slot") & 255;
                this.InitializeDragonInventory();
                this.dragonInv.setInventorySlotContents(j, new ItemStack(nbttagcompound));

                ItemStack saddle = dragonInv.getStackInSlot(0);
                ItemStack chest = dragonInv.getStackInSlot(1);
                ItemStack banner1 = dragonInv.getStackInSlot(31);
                ItemStack banner2 = dragonInv.getStackInSlot(32);
                ItemStack banner3 = dragonInv.getStackInSlot(33);
                ItemStack banner4 = dragonInv.getStackInSlot(34);

                if (world.isRemote) {
                    DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 0, saddle != null && saddle.getItem() == Items.SADDLE && !saddle.isEmpty() ? 1 : 0));

                    DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 1, chest != null && chest.getItem() == Item.getItemFromBlock(Blocks.CHEST) && !chest.isEmpty() ? 1 : 0));

                    DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 2, this.getIntFromArmor(dragonInv.getStackInSlot(2))));

                    DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 31, banner1 != null && banner1.getItem() == Items.BANNER && !banner1.isEmpty() ? 1 : 0));

                    DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 32, banner2 != null && banner2.getItem() == Items.BANNER && !banner2.isEmpty() ? 1 : 0));

                    DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 33, banner3 != null && banner3.getItem() == Items.BANNER && !banner3.isEmpty() ? 1 : 0));

                    DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 34, banner4 != null && banner4.getItem() == Items.BANNER && !banner4.isEmpty() ? 1 : 0));
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

    public void refreshInventory() {
        ItemStack saddle = dragonInv.getStackInSlot(0);
        ItemStack leftChestforInv = dragonInv.getStackInSlot(1);
        ItemStack banner1 = dragonInv.getStackInSlot(31);
        ItemStack banner2 = dragonInv.getStackInSlot(32);
        ItemStack banner3 = dragonInv.getStackInSlot(33);
        ItemStack banner4 = dragonInv.getStackInSlot(34);
        ItemStack armorStack = dragonInv.getStackInSlot(2);
        int armor = getIntFromArmor(dragonInv.getStackInSlot(2));
        int armor1 = getIntFromArmor(dragonInv.getStackInSlot(2));

        if (saddle != null && saddle.getItem() == Items.SADDLE && !saddle.isEmpty() && !isSaddled() && isOldEnoughToBreathe()) {
            this.setSaddled(true);
            world.playSound(posX, posY, posZ, SoundEvents.ENTITY_HORSE_SADDLE, SoundCategory.PLAYERS, 1F, 1.0F, false);
        }
        if (leftChestforInv != null && leftChestforInv.getItem() == Item.getItemFromBlock(Blocks.CHEST) &&
                !leftChestforInv.isEmpty() && !isChested() && isOldEnoughToBreathe()) {
            this.setChested(true);
            world.playSound(posX, posY, posZ, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.PLAYERS, 1F, 1F, false);
        }

        // figure out to break the boolean loop and make it play armor equip sounds
        if (ticksExisted > 20 && armor != armor1 && armor1 != 0 && armorStack.getItem() == ModArmour.dragonarmor_diamond && isOldEnoughToBreathe()) {
            this.setArmor(armor);
        }

        this.setBanner1(banner1);
        this.setBanner2(banner2);
        this.setBanner3(banner3);
        this.setBanner4(banner4);

        if (isServer()) {
            DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 0, saddle != null && saddle.getItem() == Items.SADDLE && !saddle.isEmpty() ? 1 : 0));
            DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 1, leftChestforInv != null && leftChestforInv.getItem() == Item.getItemFromBlock(Blocks.CHEST) && !leftChestforInv.isEmpty() ? 1 : 0));
            DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 2, this.getIntFromArmor(this.dragonInv.getStackInSlot(2))));
            DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 31, banner1 != null && banner1.getItem() == Items.BANNER && !banner1.isEmpty() ? 1 : 0));
            DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 32, banner2 != null && banner2.getItem() == Items.BANNER && !banner2.isEmpty() ? 1 : 0));
            DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 31, banner3 != null && banner3.getItem() == Items.BANNER && !banner3.isEmpty() ? 1 : 0));
            DragonMounts.NETWORK_WRAPPER.sendToServer(new MessageDragonInventory(this.getEntityId(), 32, banner4 != null && banner4.getItem() == Items.BANNER && !banner4.isEmpty() ? 1 : 0));
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

    /**
     * Credits: AlexThe 666 Ice and Fire
     */
    public class DragonInventoryListener implements IInventoryChangedListener {
        EntityTameableDragon dragon;

        public DragonInventoryListener(EntityTameableDragon dragon) {
            this.dragon = dragon;
        }

        @Override
        public void onInventoryChanged(IInventory invBasic) {
            refreshInventory();
        }
    }
}

