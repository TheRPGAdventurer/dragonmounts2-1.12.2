package com.TheRPGAdventurer.ROTD.client.userinput;

/**
* The purpose of this class is to intercept key presses (especially left and right mouse button clicks) and allow
*    greater flexibility in responding to them.
*   The class replaces KeyBindings in GameSettings.  When interception is on:
*      .isPressed() is overridden to return false so that the vanilla code never receives the clicks.
*      .isKeyDown() is overridden to always return false.
*      The true .isPressed() and .isKeyDown() are available using .retrieveUnderlyingClick() and .isUnderlyingKeyDown()
*   Usage:
*    (1) replace KeyBinding with a newly generated interceptor
*        eg
*        KeyBindingInterceptor attackButtonInterceptor(GameSettings.keyBindAttack);
*        GameSettings.keyBindAttack = attackButtonInterceptor;
*        This creates an interceptor linked to the existing keyBindAttack.  The original keyBindAttack remains in the
*          KeyBinding hashmap and keyBindArray.
*    (2) Set the interception mode (eg true = on)
*        eg  setInterceptionActive(false);
*    (3) read the underlying clicks using .retrieveUnderlyingClick() or .isUnderlyingKeyDown();
*    (4) when Interceptor is no longer required, call .getOriginalKeyBinding();
*        eg GameSettings.keyBindAttack = attackButtonInterceptor.getOriginalKeyBinding();
*
*  NOTES -
*    (a) In the current vanilla code, if the bindings are changed it will affect the original keybinding.  The new binding will
*        be copied to the interceptor at the first call to .isKeyDown(), .isPressed(), isUnderlyingKeyDown() or retrieveUnderlyingClick().
*    (b) Will not work in GUI
 */

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.google.common.base.Throwables;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.Map;
import com.google.common.collect.Maps;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyBindingInterceptor extends KeyBinding
{
  private static final Field keybindArrayField = ReflectionHelper.findField(KeyBinding.class, "KEYBIND_ARRAY", "field_74516_a");
  private static final Field keyCodeField = ReflectionHelper.findField(KeyBinding.class, "keyCode", "field_74512_d");
  private static final Field pressedField = ReflectionHelper.findField(KeyBinding.class, "pressed", "field_74513");
  private static final Field pressTimeField = ReflectionHelper.findField(KeyBinding.class, "pressTime", "field_151474_i");

  /**
   *  Create an Interceptor based on an existing binding.
   *  The initial interception mode is OFF.
   *  If existingKeyBinding is already a KeyBindingInterceptor, a reinitialised copy will be created but no further effect.
   * @param existingKeyBinding - the binding that will be intercepted.
   */
  public KeyBindingInterceptor(KeyBinding existingKeyBinding)
  {
    super(existingKeyBinding.getKeyDescription(), existingKeyBinding.getKeyCode(), existingKeyBinding.getKeyCategory());
    try {
      // the base constructor automatically adds the class to the keybindArray and hash, which we don't want, so undo it
//      Map<String, KeyBinding> reflectkeybindArray = (Map<String, KeyBinding>)keybindArrayField.get(this);
//      reflectkeybindArray.remove(existingKeyBinding.getKeyDescription());

      pressedField.setBoolean(this, false);
      pressTimeField.setInt(this, 0);
//      this.pressed = false;
//      this.pressTime = 0;

    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
    this.interceptionActive = false;

    if (existingKeyBinding instanceof KeyBindingInterceptor) {
      interceptedKeyBinding = ((KeyBindingInterceptor)existingKeyBinding).getOriginalKeyBinding();
    } else {
      interceptedKeyBinding = existingKeyBinding;
    }

    KeyBinding.resetKeyBindingArrayAndHash();
  }

  public void setInterceptionActive(boolean newMode)
  {
    if (newMode && !interceptionActive) {
      try {
        pressTimeField.setInt(this, 0);
      } catch (Exception e) {
        throw Throwables.propagate(e);
      }
    }
    interceptionActive = newMode;
  }

/*
   * @return If interception is on, this will return false; Otherwise, it will pass on the state of the key
 */
  @Override
  public boolean isKeyDown()
  {
    copyKeyCodeToOriginal();
//    DragonMounts.logger.info("isKeyDown:"+ super.isKeyDown());
    if (interceptionActive) {
      return false;
    } else {
      return super.isKeyDown();
    }
  }

  /**
   *
   * @return returns false if interception isn't active.  Otherwise, retrieves the state of the key
   */
  public boolean isUnderlyingKeyDown()
  {
//    if (Keyboard.isKeyDown(Keyboard.KEY_SCROLL)) {
//      int j = 2;
//    }
    copyKeyCodeToOriginal();
//    return interceptedKeyBinding.pressed;

    if (interceptionActive) {
      return super.isKeyDown();
    } else {
      return false;
    }

//    try {
//      boolean pressed =  pressedField.getBoolean(interceptedKeyBinding);
//      DragonMounts.logger.info("isUnderlyingKeyDown:" + pressed);
//      return pressed;
////      return pressedField.getBoolean(interceptedKeyBinding);  todo restore
//    } catch (Exception e) {
//      Throwables.propagate(e);
//      return false;
//    }
  }

  /**
   *
   * @return returns false if interception isn't active.  Otherwise, retrieves one of the clicks (true) or false if no clicks left
   */
  public boolean retrieveUnderlyingClick()
  {
    copyKeyCodeToOriginal();
    if (interceptionActive) {
      return super.isPressed();
////      copyClickInfoFromOriginal();
//
//      if (this.interceptedPressTime == 0) {
//        return false;
//      } else {
//        --this.interceptedPressTime;
//        return true;
//      }
    } else {
      return false;
    }
  }

  /** A better name for this method would be retrieveClick.
   * If interception is on, returns false
   * Otherwise, passes on any clicks which have occurred
   * @return If interception is on, this will return false; Otherwise, it will pass on any clicks in the intercepted KeyBinding
   */
  @Override
  public boolean isPressed()
  {
//    if (Keyboard.isKeyDown(Keyboard.KEY_SCROLL)) {
//      int j = 2;
//    }
    copyKeyCodeToOriginal();
//    copyClickInfoFromOriginal();
    if (interceptionActive) {
      return false;
    } else {
      return super.isPressed();
    }

//    try {
//
//      if (interceptionActive) {
//        pressTimeField.setInt(this, 0);
//        pressedField.setBoolean(this, false);
////        this.pressTime = 0;
////        this.pressed = false;
//        return false;
//      } else {
////        if (this.pressTime == 0) {
//        if (pressTimeField.getInt(this) == 0) {
//          return false;
//        } else {
//          pressTimeField.setInt(this, pressTimeField.getInt(this) - 1);
////          --this.pressTime;
//          return true;
//        }
//      }
//    } catch (Exception e) {
//      Throwables.propagate(e);
//      return false;
//    }
  }

  public KeyBinding getOriginalKeyBinding() {
    return interceptedKeyBinding;
  }

  protected KeyBinding interceptedKeyBinding;
  private boolean interceptionActive;

//  private int interceptedPressTime;

//  protected void copyClickInfoFromOriginal()
//  {
//    try {
////      this.pressTime += interceptedKeyBinding.pressTime;
////      this.interceptedPressTime += interceptedKeyBinding.pressTime;
////      interceptedKeyBinding.pressTime = 0;
////      this.pressed = interceptedKeyBinding.pressed;
//      int value =  pressTimeField.getInt(this);
//      value += pressTimeField.getInt(interceptedKeyBinding);
//      pressTimeField.setInt(this, value);
//      this.interceptedPressTime += pressTimeField.getInt(interceptedKeyBinding);
//      pressTimeField.setInt(interceptedKeyBinding, 0);
//      pressedField.setBoolean(this, pressedField.getBoolean(interceptedKeyBinding));
//    } catch (Exception e) {
//      Throwables.propagate(e);
//    }
//  }

  protected void copyKeyCodeToOriginal()
  {
    try {
      // only copy if necessary
//      if (this.keyCode != interceptedKeyBinding.keyCode) {
//        this.keyCode = interceptedKeyBinding.keyCode;
      if (keyCodeField.getInt(this) != keyCodeField.getInt(interceptedKeyBinding)) {
        keyCodeField.setInt(this, keyCodeField.getInt(interceptedKeyBinding));
        resetKeyBindingArrayAndHash();
      }
    } catch (Exception e) {
      Throwables.propagate(e);
    }
  }

}
