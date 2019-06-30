package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath;

import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModel;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper.SegmentSizePositionRotation;
import com.TheRPGAdventurer.ROTD.util.math.MathX;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * Created by TGG on 24/06/2015.
 * Helps to specify the position & orientation of the head and neck segments.
 * Can be used on both client and server sides.
 *
 * Usage:
 * 1) Create an instance
 * 2) call calculateHeadAndNeck() to set the neck and head positions & angles
 * 3) call getThroatPosition() to get the [x,y,z] position of the throat, uses body pitch from the dragon.
 * 4) getHeadPositionSizeLocation, getNeckPositionSizeLocation, getNeckSegmentPositionSizeLocations are used to
 *    provide detailed position information for the model
 */
public class DragonHeadPositionHelper {

  public SegmentSizePositionRotation[] neckSegments;
  public SegmentSizePositionRotation head;
  public SegmentSizePositionRotation neck;  //not required?  not sure.

  private EntityTameableDragon dragon;
  private final int NUMBER_OF_NECK_SEGMENTS;

  public DragonHeadPositionHelper(EntityTameableDragon parent, int i_numberOfNeckSegments) {
    dragon = parent;
    NUMBER_OF_NECK_SEGMENTS = i_numberOfNeckSegments;
  }

  /** calculate the position, rotation angles, and scale of the head and all segments in the neck
   * @param animBase
   * @param flutter
   * @param sit
   * @param walk
   * @param speed
   * @param ground
   * @param netLookYaw
   * @param lookPitch
   * @param breath
   */
  public void calculateHeadAndNeck(float animBase, float flutter, float sit, float walk, float speed, float ground, float netLookYaw, float lookPitch, float breath) {
    neckSegments = new SegmentSizePositionRotation[NUMBER_OF_NECK_SEGMENTS];
    head = new SegmentSizePositionRotation();
    SegmentSizePositionRotation currentSegment = new SegmentSizePositionRotation();

    currentSegment.rotationPointX = 0;
    currentSegment.rotationPointY = 14;
    currentSegment.rotationPointZ = -8;

    currentSegment.rotateAngleX = 0;
    currentSegment.rotateAngleY = 0;
    currentSegment.rotateAngleZ = 0;

    dragon.getAnimator().setLook(netLookYaw, lookPitch);
    double health = dragon.getHealthRelative();
    for (int i = 0; i < NUMBER_OF_NECK_SEGMENTS; i++) {
      float vertMulti = (i + 1) / (float)NUMBER_OF_NECK_SEGMENTS;

      float baseRotX = MathX.cos((float) i * 0.45f + animBase) * 0.15f;
      if(!dragon.isUsingBreathWeapon()) baseRotX *= MathX.lerp(0.2f, 1, flutter);
      baseRotX *= MathX.lerp(1, 0.2f, sit);
      float ofsRotX = MathX.sin(vertMulti * MathX.PI_F * 0.9f) * 0.63f; // 0.9

      // basic up/down movement
      currentSegment.rotateAngleX = baseRotX;
      // reduce rotation when on ground
      currentSegment.rotateAngleX *= MathX.slerp(1f, 0.5f, walk); // 1 != 0.8f
      // flex neck down when hovering
      currentSegment.rotateAngleX += (1 - speed) * vertMulti;
      // lower neck on low health
      currentSegment.rotateAngleX -= MathX.lerp(0, ofsRotX, ground * health);
      // use looking yaw
      currentSegment.rotateAngleY = MathX.toRadians(netLookYaw) * vertMulti * speed;

      // update size (scale)
      currentSegment.scaleX = currentSegment.scaleY = MathX.lerp(1.6f, 1, vertMulti);
      currentSegment.scaleZ = 0.6f;

      neckSegments[i] = currentSegment.getCopy();

      // move next segment behind the current one
      float neckSize = DragonModel.NECK_SIZE * currentSegment.scaleZ - 1.4f;
      currentSegment.rotationPointX -= MathX.sin(currentSegment.rotateAngleY) * MathX.cos(currentSegment.rotateAngleX) * neckSize;
      currentSegment.rotationPointY += MathX.sin(currentSegment.rotateAngleX) * neckSize;
      currentSegment.rotationPointZ -= MathX.cos(currentSegment.rotateAngleY) * MathX.cos(currentSegment.rotateAngleX) * neckSize;
    }
    neck = currentSegment.getCopy();  // might not be required, not sure, so do it anyway...

    final float HEAD_TILT_DURING_BREATH = -0.1F;
    head.rotateAngleX = MathX.toRadians(lookPitch) + (1 - speed); // + breath * HEAD_TILT_DURING_BREATH
    head.rotateAngleY = currentSegment.rotateAngleY;
    head.rotateAngleZ = currentSegment.rotateAngleZ * 0.2f;

    head.rotationPointX = currentSegment.rotationPointX;
    head.rotationPointY = currentSegment.rotationPointY;
    head.rotationPointZ = currentSegment.rotationPointZ;
  }

  public SegmentSizePositionRotation getHeadPositionSizeLocation() {
    if (head == null) {
      throw new IllegalStateException("DragonHeadPositionHelper.calculateHeadAndNeck() must be called first");
    }
    return head.getCopy();
  }

  public SegmentSizePositionRotation getNeckPositionSizeLocation() {
    if (neck == null) {
      throw new IllegalStateException("DragonHeadPositionHelper.calculateHeadAndNeck() must be called first");
    }
    return neck.getCopy();
  }

  public SegmentSizePositionRotation[] getNeckSegmentPositionSizeLocations() {
    if (neckSegments == null) {
      throw new IllegalStateException("DragonHeadPositionHelper.calculateHeadAndNeck() must be called first");
    }
    SegmentSizePositionRotation[] retval = new SegmentSizePositionRotation[neckSegments.length];
    for (int i = 0; i < neckSegments.length; ++i) {
      retval[i] = neckSegments[i].getCopy();
    }
    return retval;
  }

  /** Calculate the position of the dragon's throat
   * Must have previously called calculateHeadAndNeck()
   * @return the world [x,y,z] of the throat
   */
  public Vec3d getThroatPosition() {
    if (head == null) {
      throw new IllegalStateException("DragonHeadPositionHelper.calculateHeadAndNeck() must be called first");
    }

    float renderYawOffset = dragon.renderYawOffset;

    Vec3d bodyOrigin = dragon.getPositionVector();
    bodyOrigin = bodyOrigin.addVector(0, dragon.getEyeHeight(), 0);
    float scale = dragon.getScale();
    final float ADULT_SCALE_FACTOR = dragon.getBreed().getAdultModelScaleFactor();
    final float BODY_X_SCALE = -ADULT_SCALE_FACTOR * scale;
    final float BODY_Y_SCALE = -ADULT_SCALE_FACTOR * scale;
    final float BODY_Z_SCALE = ADULT_SCALE_FACTOR * scale;

    final float headScale = scale * getRelativeHeadSize();
    final float HEAD_X_SCALE = ADULT_SCALE_FACTOR * headScale;
    final float HEAD_Y_SCALE = ADULT_SCALE_FACTOR * headScale;
    final float HEAD_Z_SCALE = ADULT_SCALE_FACTOR * headScale;

    // the head offset plus the headLocation.rotationPoint is the origin of the head, i.e. the point about which the
    //   head rotates, relative to the origin of the body (getPositionEyes)
    final float HEAD_X_OFFSET = 0;
    final float HEAD_Y_OFFSET = 2; // 2  //-4
    final float HEAD_Z_OFFSET = -23; //-23

    final float THROAT_X_OFFSET = 0;
    final float THROAT_Y_OFFSET = 4; //  0
    final float THROAT_Z_OFFSET = -17; //  -20

    Vec3d headOffset =  new Vec3d((head.rotationPointX + HEAD_X_OFFSET) * BODY_X_SCALE,
                                  (head.rotationPointY + HEAD_Y_OFFSET) * BODY_Y_SCALE,
                                  (head.rotationPointZ + HEAD_Z_OFFSET) * BODY_Z_SCALE);

    // offset of the throat position relative to the head origin- rotate and pitch to match head

    Vec3d throatOffset = new Vec3d(THROAT_X_OFFSET * HEAD_X_SCALE,
                                   THROAT_Y_OFFSET * HEAD_Y_SCALE,
                                   THROAT_Z_OFFSET * HEAD_Z_SCALE);

    throatOffset = throatOffset.rotatePitch(head.rotateAngleX);
    throatOffset = throatOffset.rotateYaw(-head.rotateAngleY);

    Vec3d headPlusThroatOffset = headOffset.add(throatOffset);

    float bodyPitch = dragon.getBodyPitch();
    Vec3d CENTRE_OFFSET = new Vec3d(0, -6 * BODY_Y_SCALE,  19 * BODY_Z_SCALE);

    //rotate body

    bodyPitch = (float)Math.toRadians(bodyPitch);

    headPlusThroatOffset = headPlusThroatOffset.add(CENTRE_OFFSET);
    headPlusThroatOffset = headPlusThroatOffset.rotatePitch(-bodyPitch);
    headPlusThroatOffset = headPlusThroatOffset.subtract(CENTRE_OFFSET);

    headPlusThroatOffset = headPlusThroatOffset.rotateYaw((float) (Math.toRadians(-renderYawOffset) + Math.PI));

    Vec3d throatPos = bodyOrigin.add(headPlusThroatOffset);

    return throatPos;
  }

  /**
   * Baby dragon has a relatively larger head compared to its body size (makes it look cuter)
   */
  public float getRelativeHeadSize() {
    return getRelativeHeadSize(dragon.getScale());
  }

  public float getRelativeHeadSize(float scale) {
    final float SIZE_OF_ADULT_HEAD = 1.0F;
    final float SIZE_OF_BABY_HEAD = 2.0F;
    final float SCALE_OF_BABY = 0.2F;
    final float SCALE_OF_ADULT = 1.0F;

    // used to be 1.4F / (scale + 0.4F) i.e. a rational function of the form head_size = A / (scale + B)
    // We want the headsize of the adult to be SIZE_OF_ADULT_HEAD at SCALE_OF_ADULT, and
    //    headsize of the baby to be SIZE_OF_BABY_HEAD at SCALE_OF_BABY
    //  we can rearrange to solve for A and B
    final float B = (SIZE_OF_ADULT_HEAD * SCALE_OF_ADULT - SIZE_OF_BABY_HEAD * SCALE_OF_BABY) /
                    (SIZE_OF_BABY_HEAD - SIZE_OF_ADULT_HEAD);
    final float A = SIZE_OF_ADULT_HEAD * (SCALE_OF_ADULT + B);

    scale = MathX.clamp(scale, SCALE_OF_BABY, SCALE_OF_ADULT);
    return A * (scale + B);
  }

  /**
   * rotate a vector around the X axis
   * @param angle in radians
   * @return
   */
  public Vec3d rotateX(Vec3d source, float angle) {
    float cosAngle = MathHelper.cos(angle);
    float sinAngle = MathHelper.sin(angle);
    double d0 = source.x;
    double d1 = source.y * (double)cosAngle + source.z * (double)sinAngle;
    double d2 = source.z * (double)cosAngle - source.y * (double)sinAngle;
    return new Vec3d(d0, d1, d2);
  }

  public Vec3d rotateY(Vec3d source, float angle) {
    float cosAngle = MathHelper.cos(angle);
    float sinAngle = MathHelper.sin(angle);
    double d0 = source.x * (double)cosAngle + source.z * (double)sinAngle;
    double d1 = source.y;
    double d2 = source.z * (double)cosAngle - source.x * (double)sinAngle;
    return new Vec3d(d0, d1, d2);
  }

  public Vec3d rotateZ(Vec3d source, float angle) {
    float cosAngle = MathHelper.cos(angle);
    float sinAngle = MathHelper.sin(angle);
    double d0 = source.x * (double)cosAngle + source.y * (double)sinAngle;
    double d1 = source.y * (double)cosAngle - source.x * (double)sinAngle;
    double d2 = source.z;
    return new Vec3d(d0, d1, d2);
  }

}
