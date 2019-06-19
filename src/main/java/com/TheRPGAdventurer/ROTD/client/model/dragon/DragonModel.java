/*
 ** 2011 December 10
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */

package com.TheRPGAdventurer.ROTD.client.model.dragon;

import com.TheRPGAdventurer.ROTD.client.model.dragon.anim.DragonAnimator;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.DragonHeadPositionHelper;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper.SegmentSizePositionRotation;
import com.TheRPGAdventurer.ROTD.util.math.MathX;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

/**
 * Generic model for all winged tetrapod dragons.
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 * @Modifier James Miller <TheRPGAdventurer.>
 */
public class DragonModel extends AdvancedModelBase {

    // model constants
    public static final int NECK_SIZE=10;
    public static final int TAIL_SIZE=10;
    public static final int VERTS_NECK=7;
    public static final int VERTS_TAIL=12;
    public static final int HEAD_OFS=-16;

    // model parts
    public ModelPart head;
    public ModelPart neck;
    public ModelPart neckScale;
    public ModelPart tail;
    public ModelPart tailHornLeft;
    public ModelPart tailHornRight;
    public ModelPart tailScaleLeft;
    public ModelPart tailScaleMiddle;
    public ModelPart tailScaleRight;
    public ModelPart jaw;
    public ModelPart body;
    public ModelPart heart;
    public ModelPart back;
    public ModelPart saddle;
    public ModelPart saddleFront;
    public ModelPart saddleBack;
    public ModelPart saddleTieL;
    public ModelPart saddleMetalL;
    public ModelPart saddleTieR;
    public ModelPart saddleMetalR;
    public ModelPart chestR;
    public ModelPart chestL;
    public ModelPart forethigh;
    public ModelPart forecrus;
    public ModelPart forefoot;
    public ModelPart foretoe;
    public ModelPart hindthigh;
    public ModelPart hindcrus;
    public ModelPart hindfoot;
    public ModelPart hindtoe;
    public ModelPart wingArm;
    public ModelPart wingForearm;
    public ModelPart[] wingFinger=new ModelPart[4];

    // model attributes
    public ModelPartProxy[] neckProxy=new ModelPartProxy[VERTS_NECK];
    public ModelPartProxy[] tailProxy=new ModelPartProxy[VERTS_TAIL];
    public ModelPartProxy[] thighProxy=new ModelPartProxy[4];

/*    // animation parameters
    private float[] wingArmf = new float[3];
    private float[] wingForearmf = new float[3];
    private float[] wingArmFlutterf = new float[3];
    private float[] wingForearmFlutterf = new float[3];
    private float[] wingArmGlidef = new float[3];
    private float[] wingForearmGlidef = new float[3];
    private float[] wingArmGroundf = new float[3];
    private float[] wingForearmGroundf = new float[3];
*/

    public float offsetX;
    public float offsetY;
    public float offsetZ;
    public float pitch;
    public float size;
    private EnumDragonBreed breed;
    private DragonModelMode mode;

    // final X rotation angles for ground
    private float[] xGround={0, 0, 0, 0};

    // X rotation angles for ground
    // 1st dim - front, hind
    // 2nd dim - thigh, crus, foot, toe
    private float[][] xGroundStand={{0.8f, -1.5f, 1.3f, 0}, {-0.3f, 1.5f, -0.2f, 0},};
    private float[][] xGroundSit={{0.3f, -1.8f, 1.8f, 0}, {-0.8f, 1.8f, -0.9f, 0},};

    // X rotation angles for walking
    // 1st dim - animation keyframe
    // 2nd dim - front, hind
    // 3rd dim - thigh, crus, foot, toe
    private float[][][] xGroundWalk={{{0.4f, -1.4f, 1.3f, 0}, // move down and forward
            {0.1f, 1.2f, -0.5f, 0} // move back
    }, {{1.2f, -1.6f, 1.3f, 0}, // move back
            {-0.3f, 2.1f, -0.9f, 0.6f} // move up and forward
    }, {{0.9f, -2.1f, 1.8f, 0.6f}, // move up and forward
            {-0.7f, 1.4f, -0.2f, 0} // move down and forward
    }};

    // final X rotation angles for walking
    private float[] xGroundWalk2={0, 0, 0, 0};

    // Y rotation angles for ground, thigh only
    private float[] yGroundStand={-0.25f, 0.25f};
    private float[] yGroundSit={0.1f, 0.35f};
    private float[] yGroundWalk={-0.1f, 0.1f};

    // final X rotation angles for air
    private float[] xAir;

    // X rotation angles for air
    // 1st dim - front, hind
    // 2nd dim - thigh, crus, foot, toe
    private float[][] xAirAll={{0, 0, 0, 0}, {0, 0, 0, 0}};

    // Y rotation angles for air, thigh only
    private float[] yAirAll={-0.1f, 0.1f};

    public DragonModel(EnumDragonBreed breed) {
        textureWidth=256;
        textureHeight=256;

        this.breed=breed;

        setTextureOffset("body.body", 0, 0);
        setTextureOffset("body.scale", 0, 32);
        setTextureOffset("body.heart", 130, 110);
        setTextureOffset("body.saddle", 184, 98);
        setTextureOffset("body.saddleFront", 214, 120);
        setTextureOffset("body.saddleBack", 214, 120);
        setTextureOffset("body.saddleTieL", 220, 100);
        setTextureOffset("body.saddleTieR", 220, 100);
        setTextureOffset("body.saddleMetalL", 224, 132);
        setTextureOffset("body.saddleMetalR", 224, 132);
        setTextureOffset("body.chestL", 192, 132);
        setTextureOffset("body.chestR", 224, 132);
        setTextureOffset("head.nostril", 48, 0);
        setTextureOffset("head.mainhead", 0, 0);
        setTextureOffset("head.upperjaw", 56, 88);
        setTextureOffset("head.lowerjaw", 0, 88);
        setTextureOffset("head.horn", 28, 32);
        setTextureOffset("forethigh.main", 112, 0);
        setTextureOffset("forecrus.main", 148, 0);
        setTextureOffset("forefoot.main", 210, 0);
        setTextureOffset("foretoe.main", 176, 0);
        setTextureOffset("hindthigh.main", 112, 29);
        setTextureOffset("hindcrus.main", 152, 29);
        setTextureOffset("hindfoot.main", 180, 29);
        setTextureOffset("hindtoe.main", 215, 29);
        setTextureOffset("neck.box", 112, 88);
        setTextureOffset("neck.scale", 0, 0);
        setTextureOffset("tail.box", 152, 88);
        setTextureOffset("tail.scale", 0, 0);
        setTextureOffset("tail.horn", 0, 117);
        setTextureOffset("wingarm.bone", 0, 152);
        setTextureOffset("wingarm.skin", 116, 232);
        setTextureOffset("wingfinger.bone", 0, 172);
        setTextureOffset("wingfinger.shortskin", -32, 224);
        setTextureOffset("wingfinger.skin", -49, 176);
        setTextureOffset("wingforearm.bone", 0, 164);

        buildBody();
        buildNeck();
        buildHead();
        buildTail();
        buildWing();
        buildLegs();
    }

    public void setMode(DragonModelMode mode) {
        this.mode=mode;
    }

    private void buildHead() {
        head = new ModelPart(this, "head");
        head.addBox("upperjaw",  -6, -1,   -8 + HEAD_OFS, 12,  5, 16);
        head.addBox("mainhead", -8, -8,    6 + HEAD_OFS, 16, 16, 16); // 6
        head.addBox("nostril",   -5, -3,   -6 + HEAD_OFS,  2,  2,  4);
        head.mirror = true;
        head.addBox("nostril",    3,  -3,  -6 + HEAD_OFS,  2,  2,  4);

        buildHorn(false);
        buildHorn(true);

        jaw = head.addChildBox("lowerjaw", -6, 0, -16, 12, 4, 16);
        jaw.setRotationPoint(0, 4, 8 + HEAD_OFS);
    }

    private void buildHorn(boolean mirror) {
        int hornThick=3;
        int hornLength=12;

        float hornOfs=-(hornThick / 2f);

        float hornPosX = -5;
        float hornPosY = -8;
        float hornPosZ = 0;

        float hornRotX=MathX.toRadians(30);
        float hornRotY=MathX.toRadians(-30);
        float hornRotZ=0;

        if (mirror) {
            hornPosX*=-1;
            hornRotY*=-1;
        }

        head.mirror=mirror;
        ModelPart horn=head.addChildBox("horn", hornOfs, hornOfs, hornOfs, hornThick, hornThick, hornLength);
        horn.setRotationPoint(hornPosX, hornPosY, hornPosZ);
        horn.setAngles(hornRotX, hornRotY, hornRotZ);

    }

    private void buildNeck() {
        neck=new ModelPart(this, "neck");
        neck.addBox("box", -5, -5, -5, NECK_SIZE, NECK_SIZE, NECK_SIZE);
        neckScale=neck.addChildBox("scale", -1, -7, -3, 2, 4, 6);

        // initialize model proxies
        for (int i=0; i < neckProxy.length; i++) {
            neckProxy[i]=new ModelPartProxy(neck);
        }
    }

    private void buildTail() {
        tail=new ModelPart(this, "tail");
        tail.addBox("box", -5, -5, -5, TAIL_SIZE, TAIL_SIZE, TAIL_SIZE);
        float scaleRotZ=MathX.toRadians(45);
        tailScaleLeft=tail.addChildBox("scale", -1, -8, -3, 2, 4, 6).setAngles(0, 0, scaleRotZ);
        tailScaleMiddle=tail.addChildBox("scale", -1, -8, -3, 2, 4, 6).setAngles(0, 0, 0);
        tailScaleRight=tail.addChildBox("scale", -1, -8, -3, 2, 4, 6).setAngles(0, 0, -scaleRotZ);

        boolean ice=breed==EnumDragonBreed.ICE;

        tailScaleMiddle.showModel=!ice;
        tailScaleLeft.showModel=ice;
        tailScaleRight.showModel=ice;

        buildTailHorn(false);
        buildTailHorn(true);

        // initialize model proxies
        for (int i=0; i < tailProxy.length; i++) {
            tailProxy[i]=new ModelPartProxy(tail);
        }
    }

    private void buildTailHorn(boolean mirror) {
        int hornThick=3;
        int hornLength=32;

        float hornOfs=-(hornThick / 2f);

        float hornPosX=0;
        float hornPosY=hornOfs;
        float hornPosZ=TAIL_SIZE / 2f;

        float hornRotX=MathX.toRadians(-15);
        float hornRotY=MathX.toRadians(-145);
        float hornRotZ=0;

        if (mirror) {
            hornPosX*=-1;
            hornRotY*=-1;
        }

        tail.mirror=mirror;
        ModelPart horn=tail.addChildBox("horn", hornOfs, hornOfs, hornOfs, hornThick, hornThick, hornLength);
        horn.setRotationPoint(hornPosX, hornPosY, hornPosZ);
        horn.setAngles(hornRotX, hornRotY, hornRotZ);
        horn.isHidden=true;
        boolean showSpike=breed==EnumDragonBreed.NETHER || breed==EnumDragonBreed.SYLPHID || breed==EnumDragonBreed.STORM;
        horn.showModel=showSpike;

        if (mirror) {
            tailHornLeft=horn;
        } else {
            tailHornRight=horn;
        }
    }

    private void buildBody() {
        body=new ModelPart(this, "body");
        body.setRotationPoint(0, 4, 8);
        body.addBox("body", -12, 0, -16, 24, 24, 64);
        body.addBox("scale", -1, -6, 10, 2, 6, 12);
        body.addBox("scale", -1, -6, 30, 2, 6, 12);
        heart=body.addChildBox("heart", -4, 12, -5, 8, 6, 15);
        back=body.addChildBox("scale", -1, -6, -10, 2, 6, 12);
        chestL=body.addChildBox("chestL", 12, 0, 21, 4, 12, 12);
        chestR=body.addChildBox("chestR", -16, 0, 21, 4, 12, 12);
        saddle=body.addChildBox("saddle", -7, -2, -15, 15, 3, 20);
        saddleFront=body.addChildBox("saddleFront", -3, -3, -14, 6, 1, 2);
        saddleBack=body.addChildBox("saddleBack", -6, -4, 2, 13, 2, 2);
        saddleTieL=body.addChildBox("saddleTieL", 12, 0, -14, 1, 14, 2);
        saddleMetalL=body.addChildBox("saddleMetalL", 12, 14, -15, 1, 5, 4);
        saddleTieR=body.addChildBox("saddleTieR", -13, 0, -14, 1, 10, 2);
        saddleMetalR=body.addChildBox("saddleMetalR", -13, 10, -15, 1, 5, 4);
    }

    private void buildWing() {
        wingArm=new ModelPart(this, "wingarm");
        wingArm.setRotationPoint(-10, 5, 4);
        wingArm.setRenderScale(1.1f); //1.1f
        wingArm.addBox("bone", -28, -3, -3, 28, 6, 6);
        wingArm.addBox("skin", -28, 0, 2, 28, 0, 24);

        wingForearm=new ModelPart(this, "wingforearm");
        wingForearm.setRotationPoint(-28, 0, 0);
        wingForearm.addBox("bone", -48, -2, -2, 48, 4, 4);
        wingArm.addChild(wingForearm);

        wingFinger[0]=buildWingFinger(false);
        wingFinger[1]=buildWingFinger(false);
        wingFinger[2]=buildWingFinger(false);
        wingFinger[3]=buildWingFinger(true);
    }

    private ModelPart buildWingFinger(boolean small) {
        ModelPart finger=new ModelPart(this, "wingfinger");
        finger.setRotationPoint(-47, 0, 0);
        finger.addBox("bone", -70, -1, -1, 70, 2, 2);
        if (small) {
            finger.addBox("shortskin", -70, 0, 1, 70, 0, 32);
        } else {
            finger.addBox("skin", -70, 0, 1, 70, 0, 48);
        }
        wingForearm.addChild(finger);

        return finger;
    }

    private void buildLegs() {
        buildLeg(false);
        buildLeg(true);

        // initialize model proxies
        for (int i=0; i < 4; i++) {
            if (i % 2==0) {
                thighProxy[i]=new ModelPartProxy(forethigh);
            } else {
                thighProxy[i]=new ModelPartProxy(hindthigh);
            }
        }
    }

    private void buildLeg(boolean hind) {
        // thinner legs for skeletons
        boolean skeleton=breed==EnumDragonBreed.SKELETON || breed==EnumDragonBreed.WITHER;

        float baseLength=26;
        String baseName=hind ? "hind" : "fore";

        // thigh variables
        float thighPosX=-11;
        float thighPosY=18;
        float thighPosZ=4;

        int thighThick=9 - (skeleton ? 2 : 0);
        int thighLength=(int) (baseLength * (hind ? 0.9f : 0.77f));

        if (hind) {
            thighThick++;
            thighPosY-=5;
        }

        float thighOfs=-(thighThick / 2f);

        ModelPart thigh=new ModelPart(this, baseName + "thigh");
        thigh.setRotationPoint(thighPosX, thighPosY, thighPosZ);
        thigh.addBox("main", thighOfs, thighOfs, thighOfs, thighThick, thighLength, thighThick);

        // crus variables
        float crusPosX=0;
        float crusPosY=thighLength + thighOfs;
        float crusPosZ=0;

        int crusThick=thighThick - 2;
        int crusLength=(int) (baseLength * (hind ? 0.7f : 0.8f));

        if (hind) {
            crusThick--;
            crusLength-=2;
        }

        float crusOfs=-(crusThick / 2f);

        ModelPart crus=new ModelPart(this, baseName + "crus");
        crus.setRotationPoint(crusPosX, crusPosY, crusPosZ);
        crus.addBox("main", crusOfs, crusOfs, crusOfs, crusThick, crusLength, crusThick);
        thigh.addChild(crus);

        // foot variables
        float footPosX=0;
        float footPosY=crusLength + (crusOfs / 2f);
        float footPosZ=0;

        int footWidth=crusThick + 2;
        int footHeight=4;
        int footLength=(int) (baseLength * (hind ? 0.67f : 0.34f));

        float footOfsX=-(footWidth / 2f);
        float footOfsY=-(footHeight / 2f);
        float footOfsZ=footLength * -0.75f;

        ModelPart foot=new ModelPart(this, baseName + "foot");
        foot.setRotationPoint(footPosX, footPosY, footPosZ);
        foot.addBox("main", footOfsX, footOfsY, footOfsZ, footWidth, footHeight, footLength);
        crus.addChild(foot);

        // toe variables
        int toeWidth=footWidth;
        int toeHeight=footHeight;
        int toeLength=(int) (baseLength * (hind ? 0.27f : 0.33f));

        float toePosX=0;
        float toePosY=0;
        float toePosZ=footOfsZ - (footOfsY / 2f);

        float toeOfsX=-(toeWidth / 2f);
        float toeOfsY=-(toeHeight / 2f);
        float toeOfsZ=-toeLength;

        ModelPart toe=new ModelPart(this, baseName + "toe");
        toe.setRotationPoint(toePosX, toePosY, toePosZ);
        toe.addBox("main", toeOfsX, toeOfsY, toeOfsZ, toeWidth, toeHeight, toeLength);
        foot.addChild(toe);

        if (hind) {
            hindthigh=thigh;
            hindcrus=crus;
            hindfoot=foot;
            hindtoe=toe;
        } else {
            forethigh=thigh;
            forecrus=crus;
            forefoot=foot;
            this.
                    foretoe=toe;
        }
    }

    /**
     * Applies the animations on the model. Called every frame before the model
     * is rendered.
     */
    private void updateFromAnimator(EntityTameableDragon dragon) {
        DragonAnimator dragonAnimator=dragon.getAnimator();
        dragonAnimator.animate();

        // update flags
        back.isHidden=dragon.isSaddled();

        // update offsets
        offsetX=dragonAnimator.getModelOffsetX();
        offsetY=dragonAnimator.getModelOffsetY();
        offsetZ=dragonAnimator.getModelOffsetZ();

        // update pitch
        pitch=dragonAnimator.getBodyPitch();

        // updateFromAnimator body parts
        animHeadAndNeck(dragon);
        animTail(dragon);
        animWings(dragon);
        animLegs(dragon);
    }

    protected void animHeadAndNeck(EntityTameableDragon dragon) {
        DragonHeadPositionHelper headPositionHelper=dragon.getAnimator().getDragonHeadPositionHelper();

        SegmentSizePositionRotation[] segmentData=headPositionHelper.getNeckSegmentPositionSizeLocations();

        if (neckProxy.length!=segmentData.length) {
            throw new IllegalArgumentException("Mismatch of neck segment count");
        }

        for (int i=0; i < neckProxy.length; i++) {
            copyPositionRotationLocation(neck, segmentData[i]);
            // hide the first and every second scale
            neckScale.isHidden=i % 2!=0 || i==0;

            // update proxy
            neckProxy[i].update();
        }

        copyPositionRotationLocation(neck, headPositionHelper.getNeckPositionSizeLocation());
        copyPositionRotationLocation(head, headPositionHelper.getHeadPositionSizeLocation());

        jaw.rotateAngleX=dragon.getAnimator().getJawRotateAngleX();
    }

    protected void animWings(EntityTameableDragon dragon) {
        DragonAnimator dragonAnimatorCommon=dragon.getAnimator();
        // apply angles
        wingArm.rotateAngleX=dragonAnimatorCommon.getWingArmRotateAngleX();
        wingArm.rotateAngleY=dragonAnimatorCommon.getWingArmRotateAngleY();
        wingArm.rotateAngleZ=dragonAnimatorCommon.getWingArmRotateAngleZ();
        wingArm.preRotateAngleX=dragonAnimatorCommon.getWingArmPreRotateAngleX();
        wingForearm.rotateAngleX=dragonAnimatorCommon.getWingForearmRotateAngleX();
        wingForearm.rotateAngleY=dragonAnimatorCommon.getWingForearmRotateAngleY();
        wingForearm.rotateAngleZ=dragonAnimatorCommon.getWingForearmRotateAngleZ();

        // set wing finger angles
        for (int i=0; i < wingFinger.length; i++) {
            wingFinger[i].rotateAngleX=dragonAnimatorCommon.getWingFingerRotateX(i);
            wingFinger[i].rotateAngleY=dragonAnimatorCommon.getWingFingerRotateY(i);
        }

    }

    protected void animTail(EntityTameableDragon dragon) {
        DragonAnimator DragonAnimator=dragon.getAnimator();
        SegmentSizePositionRotation[] tailSegmentData=DragonAnimator.getTailSegments();
        final int TAIL_SEGMENTS=tailSegmentData.length;

        for (int i=0; i < tailSegmentData.length; i++) {
            copyPositionRotationLocation(tail, tailSegmentData[i]);

            // display horns near the tip
            boolean horn=i > TAIL_SEGMENTS - 7 && i < TAIL_SEGMENTS - 3;
            tailHornLeft.isHidden=tailHornRight.isHidden=!horn;

            // update proxy
            tailProxy[i].update();
        }

        copyPositionRotationLocation(tail, DragonAnimator.getTail());
    }

    // left this in DragonModel because it isn't really needed by the server and is difficult to move.
    protected void animLegs(EntityTameableDragon dragon) {
        DragonAnimator DragonAnimator=dragon.getAnimator();

        // dangling legs for flying
        float ground=DragonAnimator.getGroundTime();
        float speed=DragonAnimator.getSpeed();
        float walk=DragonAnimator.getWalkTime();
        float sit=DragonAnimator.getSitTime();
        float cycleOfs=DragonAnimator.getCycleOfs();
        if (ground < 1) {
            float footAirOfs=cycleOfs * 0.1f;
            float footAirX=0.75f + cycleOfs * 0.1f;

            xAirAll[0][0]=1.3f + footAirOfs;
            xAirAll[0][1]=-(0.7f * speed + 0.1f + footAirOfs);
            xAirAll[0][2]=footAirX;
            xAirAll[0][3]=footAirX * 0.5f;

            xAirAll[1][0]=footAirOfs + 0.6f;
            xAirAll[1][1]=footAirOfs + 0.8f;
            xAirAll[1][2]=footAirX;
            xAirAll[1][3]=footAirX * 0.5f;
        }

        // 0 - front leg, right side
        // 1 - hind leg, right side
        // 2 - front leg, left side
        // 3 - hind leg, left side
        for (int i=0; i < thighProxy.length; i++) {
            ModelPart thigh, crus, foot, toe;

            if (i % 2==0) {
                thigh=forethigh;
                crus=forecrus;
                foot=forefoot;
                toe=foretoe;

                thigh.rotationPointZ=4;
            } else {
                thigh=hindthigh;
                crus=hindcrus;
                foot=hindfoot;
                toe=hindtoe;

                thigh.rotationPointZ=46;
            }

            xAir=xAirAll[i % 2];

            // interpolate between sitting and standing
            DragonAnimator.slerpArrays(xGroundStand[i % 2], xGroundSit[i % 2], xGround, sit);

            // align the toes so they're always horizontal on the ground
            xGround[3]=-(xGround[0] + xGround[1] + xGround[2]);

            // apply walking cycle
            if (walk > 0) {
                // interpolate between the keyframes, based on the cycle
                DragonAnimator.splineArrays(DragonAnimator.getMoveTime() * 0.2f, i > 1, xGroundWalk2, xGroundWalk[0][i % 2], xGroundWalk[1][i % 2], xGroundWalk[2][i % 2]);
                // align the toes so they're always horizontal on the ground
                xGroundWalk2[3]-=xGroundWalk2[0] + xGroundWalk2[1] + xGroundWalk2[2];

                DragonAnimator.slerpArrays(xGround, xGroundWalk2, xGround, walk);
            }

            float yAir=yAirAll[i % 2];
            float yGround;

            // interpolate between sitting and standing
            yGround=MathX.slerp(yGroundStand[i % 2], yGroundSit[i % 2], sit);

            // interpolate between standing and walking
            yGround=MathX.slerp(yGround, yGroundWalk[i % 2], walk);

            // interpolate between flying and grounded
            thigh.rotateAngleY=MathX.slerp(yAir, yGround, ground);
            thigh.rotateAngleX=MathX.slerp(xAir[0], xGround[0], ground);
            crus.rotateAngleX=MathX.slerp(xAir[1], xGround[1], ground);
            foot.rotateAngleX=MathX.slerp(xAir[2], xGround[2], ground);
            toe.rotateAngleX=MathX.slerp(xAir[3], xGround[3], ground);

            // update proxy
            thighProxy[i].update();
        }
    }

    protected void copyPositionRotationLocation(ModelPart modelPart, SegmentSizePositionRotation segmentData) {
        modelPart.rotateAngleX=segmentData.copyIfValid(segmentData.rotateAngleX, modelPart.rotateAngleX);
        modelPart.rotateAngleY=segmentData.copyIfValid(segmentData.rotateAngleY, modelPart.rotateAngleY);
        modelPart.rotateAngleZ=segmentData.copyIfValid(segmentData.rotateAngleZ, modelPart.rotateAngleZ);
        modelPart.renderScaleX=segmentData.copyIfValid(segmentData.scaleX, modelPart.renderScaleX);
        modelPart.renderScaleY=segmentData.copyIfValid(segmentData.scaleY, modelPart.renderScaleY);
        modelPart.renderScaleZ=segmentData.copyIfValid(segmentData.scaleZ, modelPart.renderScaleZ);
        modelPart.rotationPointX=segmentData.copyIfValid(segmentData.rotationPointX, modelPart.rotationPointX);
        modelPart.rotationPointY=segmentData.copyIfValid(segmentData.rotationPointY, modelPart.rotationPointY);
        modelPart.rotationPointZ=segmentData.copyIfValid(segmentData.rotationPointZ, modelPart.rotationPointZ);
    }


    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    @Override
    public void setLivingAnimations(EntityLivingBase entity, float moveTime, float moveSpeed, float partialTicks) {
        setLivingAnimations((EntityTameableDragon) entity, moveTime, moveSpeed, partialTicks);
    }

    public void setLivingAnimations(EntityTameableDragon dragon, float moveTime, float moveSpeed, float partialTicks) {
        DragonAnimator animator=dragon.getAnimator();
        animator.setPartialTicks(partialTicks);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity entity, float moveTime, float moveSpeed, float ticksExisted, float lookYaw, float lookPitch, float scale) {
        render((EntityTameableDragon) entity, moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale);
    }

    public void render(EntityTameableDragon dragon, float moveTime, float moveSpeed, float ticksExisted, float lookYaw, float lookPitch, float scale) {
        DragonAnimator animator=dragon.getAnimator();
        animator.setMovement(moveTime/3, moveSpeed/3);
        animator.setLook(lookYaw, lookPitch);
        animator.animate();
        updateFromAnimator(dragon);

        size=dragon.getScale();

        renderModel(dragon, scale);
    }

    /**
     * Renders the model after all animations are applied.
     */
    public void renderModel(EntityTameableDragon dragon, float scale) {
        if (mode==null) {
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(offsetX, offsetY, offsetZ);
        GlStateManager.rotate(-pitch, 1, 0, 0);

        switch (mode) {
            case BODY_ONLY:
                renderBody(scale);
                break;
            case WINGS_ONLY:
                renderWings(scale);
                break;
            default:
                renderHead(scale);
                renderNeck(scale);
                renderBody(scale);
                renderLegs(scale);
                renderTail(scale);
                if (mode!=DragonModelMode.NO_WINGS) {
                    renderWings(scale);
                }
        }

        GlStateManager.popMatrix();
    }

    protected void renderBody(float scale) {
        body.render(scale);
    }

    protected void renderHead(float scale) {
        float headScale=scale * 0.92f;
        head.render((headScale));
    }

    protected void renderNeck(float scale) {
        for (ModelPartProxy proxy : neckProxy) {
            proxy.render(scale);
        }
    }

    protected void renderTail(float scale) {
        for (ModelPartProxy proxy : tailProxy) {
            proxy.render(scale);
        }
    }

    protected void renderWings(float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.enableCull();
        GlStateManager.cullFace(GlStateManager.CullFace.FRONT);

        for (int i=0; i < 2; i++) {
            wingArm.render(scale);

            if (i==0) {
                // mirror next wing
                GlStateManager.scale(-1, 1, 1);
                // switch to back face culling
                GlStateManager.cullFace(GlStateManager.CullFace.BACK);
            }
        }

        GlStateManager.disableCull();
        GlStateManager.popMatrix();
    }

    protected void renderLegs(float scale) {
        GlStateManager.enableCull();
        GlStateManager.cullFace(GlStateManager.CullFace.BACK);

        for (int i=0; i < thighProxy.length; i++) {
            thighProxy[i].render(scale);

            if (i==1) {
                // mirror next legs
                GlStateManager.scale(-1, 1, 1);
                // switch to front face culling
                GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
            }
        }

        GlStateManager.cullFace(GlStateManager.CullFace.BACK);
        GlStateManager.disableCull();
    }
}