package com.TheRPGAdventurer.ROTD.entity.breath.effects;

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.breath.BreathNode;
import com.TheRPGAdventurer.ROTD.entity.helper.util.EntityMoveAndResizeHelper;
import com.TheRPGAdventurer.ROTD.entity.helper.util.RotatingQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by TGG on 21/06/2015.
 * EntityFX that makes up the air breath weapon; client side.
 *
 * Usage:
 * (1) create a new AetherBreathFX using createAetherBreathFX
 * (2) spawn it as per normal
 *
 */
public class AetherBreathFX extends Entity {

    private final float SPLASH_CHANCE = 0.1f;
    private final float LARGE_SPLASH_CHANCE = 0.3f;

    public float scale;
    private static final float MAX_ALPHA = 0.35F;
    private EntityTameableDragon dragon;

    private final float SMOKE_CHANCE = 0.1f;
    private final float LARGE_SMOKE_CHANCE = 0.3f;
    private EntityMoveAndResizeHelper entityMoveAndResizeHelper;
    private BreathNode breathNode;


    public AetherBreathFX(World world, double x, double y, double z, Vec3d motion,
                         BreathNode i_breathNode) {
        super(world);

        breathNode = i_breathNode;
        this.setPosition(x, y, z);
        this.scale = (this.rand.nextFloat() * 0.7F + 0.7F) * 4.0F;

        //undo random velocity variation of vanilla EntityFX constructor
        motionX = motion.x;
        motionY = motion.y;
        motionZ = motion.z;

        dragon = new EntityTameableDragon(world);
        entityMoveAndResizeHelper = new EntityMoveAndResizeHelper(this);
    }

    /**
     * creates a single EntityFX from the given parameters.  Applies some random spread to direction.
     * @param world
     * @param x world [x,y,z] to spawn at (coordinates are the centre point of the fireball)
     * @param y
     * @param z
     * @param directionX initial world direction [x,y,z] - will be normalised.
     * @param directionY
     * @param directionZ
     * @param power the power of the ball
     * @param partialTicksHeadStart if spawning multiple EntityFX per tick, use this parameter to spread the starting
     *                              location in the direction
     * @return the new AetherBreathFX
     */
    public static AetherBreathFX createAetherBreathFX(World world, double x, double y, double z,
                                                double directionX, double directionY, double directionZ,
                                                BreathNode.Power power,
                                                int tickCount, float partialTicksHeadStart, EntityTameableDragon dragon)
    {
        Vec3d direction = new Vec3d(directionX, directionY, directionZ).normalize();

        Random rand = new Random();
        BreathNode breathNode = new BreathNode(power);
        breathNode.randomiseProperties(rand);
        Vec3d actualMotion = breathNode.getRandomisedStartingMotion(direction, rand);

        x += actualMotion.x * partialTicksHeadStart;
        y += actualMotion.y * partialTicksHeadStart;
        z += actualMotion.z * partialTicksHeadStart;

        double spawnTickCount = tickCount - partialTicksHeadStart;
        double tickCountInFlight = partialTicksHeadStart / 20.0;

        AetherBreathFX breathFXAir = new AetherBreathFX(world, x, y, z, actualMotion, breathNode,
                spawnTickCount, tickCountInFlight);
        return breathFXAir;
    }

    private AetherBreathFX(World world, double x, double y, double z, Vec3d motion,
                           BreathNode i_breathNode, double i_spawnTimeTicks, double timeInFlightTicks) {
        super(world);

        breathNode = i_breathNode;
//        particleGravity = Blocks.ICE.blockParticleGravity;  /// arbitrary block!  maybe not even required.
//        particleMaxAge = (int)breathNode.getMaxLifeTime(); // not used, but good for debugging
//        this.particleAlpha = MAX_ALPHA;

        //undo random velocity variation of vanilla EntityFX constructor
        motionX = motion.x;
        motionY = motion.y;
        motionZ = motion.z;

        entityMoveAndResizeHelper = new EntityMoveAndResizeHelper(this);

//        textureUV = setRandomTexture(this.particleIcon);
//        clockwiseRotation = rand.nextBoolean();
//        final float MIN_ROTATION_SPEED = 2.0F; // revolutions per second
//        final float MAX_ROTATION_SPEED = 6.0F; // revolutions per second
//        rotationSpeedQuadrantsPerTick = MIN_ROTATION_SPEED + rand.nextFloat() * (MAX_ROTATION_SPEED - MIN_ROTATION_SPEED);
//        rotationSpeedQuadrantsPerTick *= 4.0 / 20.0F; // convert to quadrants per tick

        spawnTimeTicks = i_spawnTimeTicks;
        ticksSinceSpawn = timeInFlightTicks;
    }

    // the texture for water is made of four alternative textures, stacked 2x2
    // top left = white sphere ("spray")
    // top right = large droplet sphere
    // bottom left = cluster of small droplet spheres
    // bottom right = large droplet teardrop (points down)
    private RotatingQuad setRandomTexture(TextureAtlasSprite textureAtlasSprite)
    {
        Random random = new Random();
        double minU = textureAtlasSprite.getMinU();
        double maxU = textureAtlasSprite.getMaxU();
        //    double midU = (minU + maxU) / 2.0;
        double minV = textureAtlasSprite.getMinV();
        double maxV = textureAtlasSprite.getMaxV();
        //    double midV = (minV + maxV) / 2.0;
        renderScaleFactor = 1.0F;

        ////    if (spray) {
        //////      whichImage = WhichImage.SPRAY;
        ////      maxU = midU; maxV = midV;
        ////    } else {
        //      switch (random.nextInt(4)) {
        //        case 0: {
        ////          whichImage = WhichImage.SPHERE;
        //          minU = midU; maxV = midV;
        //          break;
        //        }
        //        case 1: {
        ////          whichImage = WhichImage.DROPLETS;
        //          maxU = midU; minV = midV;
        //          break;
        //        }
        //        case 2: {
        ////          whichImage = WhichImage.TEARDROP;
        //          minU = midU; minV = midV;
        //          break;
        //        }
        //        case 3: {
        //          maxU = midU; maxV = midV;
        //          break;
        //        }
        ////      }
        //    }

        RotatingQuad tex = new RotatingQuad(minU, minV, maxU, maxV);
        //    if (whichImage == WhichImage.SPRAY) {
        if (random.nextBoolean()) {
            tex.mirrorLR();
        }
        tex.rotate90(random.nextInt(4));
        //    } else {
        //      renderScaleFactor = random.nextFloat() * 0.5F + 0.5F;
        //    }
        return tex;
    }


//    /**
//     * Returns 1, which means "use a texture from the blocks + items texture sheet"
//     * @return
//     */
//    @Override
//    public int getFXLayer() {
//        return 1;
//    }
//
//    // this function is used by EffectRenderer.addEffect() to determine whether depthmask writing should be on or not.
//    // by default, vanilla turns off depthmask writing for entityFX with alphavalue less than 1.0
//    // BreathFXWater uses alphablending but we want depthmask writing on, otherwise translucent objects (such as water)
//    //   render over the top of our breath.
//    @Override
//    public float func_174838_j()
//    {
//        return 1.0F;
//    }
//
//    @Override
//    public int getBrightnessForRender(float partialTick)
//    {
//        return 0xf000f0;
//    }


    //  private static double calculateWiggleCycle(double initialSpawnTicks, double elapsedTicksInFlight)
    //  {
    //    double wiggleCycleCount = initialSpawnTicks / WIGGLE_CYCLE_IN_TICKS;
    //    wiggleCycleCount += elapsedTicksInFlight / WIGGLE_CYCLE_IN_TICKS  * (WIGGLE_RELATIVE_FORWARD_SPEED - 1.0);
    //    return wiggleCycleCount;
    //  }

    //  /** generates a sum-of-sines pattern - wiggles between +/- 1.0 in a smooth way that doesn't repeat (at least,
    //   *   not that the viewer can tell).
    //   *   The wiggle has a period of approximately 1.0, i.e. it reaches maximum approximately at 1.0, 2.0, 3.0, 4.0 etc
    //   * @param cycles animation parameter.  The wiggle reaches maximum approximately every 1.0
    //   * @return -1.0 -> 1.0
    //   */
    //  private double sinusGenerator(double cycles)
    //  {
    //    final double AMPLITUDES[] = {0.2F, 0.75F, 1.0F};   // amplitudes and frequencies just picked by trial and error
    //    final double FREQUENCIES[] = {37.0F, 13.0F, 11.0F};
    //    final double PERIOD_FACTOR = 2 * Math.PI / 11.0F;
    //    double amplitudesSum = 0;
    //    double sumOfSines = 0;
    //    for (int i = 0; i < AMPLITUDES.length; ++i) {
    //      amplitudesSum += AMPLITUDES[i];
    //      sumOfSines += AMPLITUDES[i] * Math.sin(cycles * PERIOD_FACTOR * FREQUENCIES[i]);
    //    }
    //    sumOfSines /= amplitudesSum;
    //    return sumOfSines;
    //  }

    @Override
    protected void entityInit() {

    }

    /** call once per tick to update the EntityFX size, position, collisions, etc
     */
    @Override
    public void onUpdate() {
        final float YOUNG_AGE = 0.25F;
        final float OLD_AGE = 0.75F;

        final float ENTITY_SCALE_RELATIVE_TO_SIZE = 5.0F;
        float currentEntitySize = breathNode.getCurrentRenderDiameter();
        scale = ENTITY_SCALE_RELATIVE_TO_SIZE * currentEntitySize;

        rotationResidual += rotationSpeedQuadrantsPerTick;
        int quadrantsRotated = MathHelper.floor(rotationResidual);
        textureUV.rotate90(clockwiseRotation ? -quadrantsRotated: quadrantsRotated);
        rotationResidual %= 1.0F;
        ++ticksSinceSpawn;

        final float PARTICLE_SCALE_RELATIVE_TO_SIZE = 5.0F; // factor to convert from particleSize to particleScale
        float currentParticleSize = breathNode.getCurrentRenderDiameter();
//        particleScale = PARTICLE_SCALE_RELATIVE_TO_SIZE * currentParticleSize;

        float newAABBDiameter = breathNode.getCurrentAABBcollisionSize();

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        entityMoveAndResizeHelper.moveAndResizeEntity(motionX, motionY, motionZ, newAABBDiameter, newAABBDiameter);

        if (isCollided && onGround) {
            motionY -= 0.01F;         // ensure that we hit the ground next time too
        }
        breathNode.updateAge(this);
//        particleAge = (int)breathNode.getAgeTicks();  // not used, but good for debugging
        if (breathNode.isDead()) {
            setDead();
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }

    //  protected EnumParticleTypes getSmokeParticleID() {
    //    if (LARGE_SPLASH_CHANCE != 0 && rand.nextFloat() <= LARGE_SPLASH_CHANCE) {
    //      return EnumParticleTypes.WATER_BUBBLE;
    //    } else {
    //      return EnumParticleTypes.WATER_SPLASH;
    //    }
    //  }

    /** Vanilla moveEntity does a pile of unneeded calculations, and also doesn't handle resize around the centre properly,
     * so replace with a custom one
     * @param dx the amount to move the entity in world coordinates [dx, dy, dz]
     * @param dy
     * @param dz
     */
    @Override
    public void move(MoverType mover, double dx, double dy, double dz) {
        entityMoveAndResizeHelper.moveAndResizeEntity(dx, dy, dz, this.width, this.height);
    }

    private RotatingQuad textureUV;
    private boolean clockwiseRotation;
    private float rotationSpeedQuadrantsPerTick;
    private float rotationResidual = 0;

    private float renderScaleFactor;
    //  private enum WhichImage {SPRAY, SPHERE, TEARDROP, DROPLETS}
    //  private WhichImage whichImage;

    //  private double ticksInFlight = 0;
    private double spawnTimeTicks = 0;
    private double ticksSinceSpawn = 0;


}
