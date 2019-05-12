/*
 ** 2012 August 27
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.event;

/**
 * Event handler that watches the currently ridden dragon entity. Used to change
 * the camera distance and to show button usage notices.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonEntityWatcher {
    /**
	private final Minecraft mc = Minecraft.getMinecraft();
    private final float defaultThirdPersonDistance;
    private int noticeTicks;
    private boolean ridingDragon;
    private boolean ridingDragonPrev;
    
    public DragonEntityWatcher() {
        defaultThirdPersonDistance = getThirdPersonDistance();
    }
    
    private float getThirdPersonDistance() {
        return ReflectionHelper.getPrivateValue(EntityRenderer.class, mc.entityRenderer, PrivateFields.ENTITYRENDERER_THIRDPERSONDISTANCE);
    }
    
    private void setThirdPersonDistance(float thirdPersonDistance) {
        ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, thirdPersonDistance, PrivateFields.ENTITYRENDERER_THIRDPERSONDISTANCE);
    }

    @SubscribeEvent
    public void onTick(ClientTickEvent evt) {
        if (evt.phase != TickEvent.Phase.START) {
            return;
        }
        
        if (mc.player == null) {
            return;
        }

        ridingDragon = mc.player.getRidingEntity() instanceof EntityTameableDragon;
        
        // display a key binding notice after the vanilla notice
        if (ridingDragon && !ridingDragonPrev) {
            setThirdPersonDistance(6);
            noticeTicks = 70;
        } else if (!ridingDragon && ridingDragonPrev) {
            setThirdPersonDistance(defaultThirdPersonDistance);
            noticeTicks = 0;
        } else {
            if (noticeTicks > 0) {
                noticeTicks--;
            }
            
            if (noticeTicks == 1) {
//                String keyUpName = GameSettings.getKeyDisplayString(DragonControl.KEY_FLY_UP.getKeyCode());
//                String keyDownName = GameSettings.getKeyDisplayString(DragonControl.KEY_FLY_DOWN.getKeyCode());
//                mc.ingameGUI.setRecordPlaying(I18n.format("dragon.mountNotice", new Object[] {keyUpName, keyDownName}), false);
            }
        }
        
        ridingDragonPrev = ridingDragon;
    }
*/
}
