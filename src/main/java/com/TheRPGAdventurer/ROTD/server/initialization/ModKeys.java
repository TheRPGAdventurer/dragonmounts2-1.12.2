package com.TheRPGAdventurer.ROTD.server.initialization;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ModKeys {
	
    public static final String KEY_CATEGORY = "key.categories.dragon";
   // public static KeyBinding KEY_FLY_UP;
  //  public static KeyBinding KEY_FLY_DOWN;
    public static KeyBinding FOLLOW_YAW;
    public static  KeyBinding KEY_BREATH;
    public static KeyBinding KEY_HOVERCANCEL;
    public static KeyBinding KEY_LOCKEDY;
    public static KeyBinding BOOST;
    public static KeyBinding DISMOUNT;
    public static KeyBinding dragon_change_view;
    
    public static void init() {
    	KEY_BREATH = new KeyBinding("key.dragon.breath", Keyboard.KEY_R, KEY_CATEGORY);
    	KEY_HOVERCANCEL = new KeyBinding("key.dragon.cancelhover", Keyboard.KEY_F12, KEY_CATEGORY);
    	dragon_change_view = new KeyBinding("key.dragon.f7", Keyboard.KEY_F7, KEY_CATEGORY);
    	FOLLOW_YAW = new KeyBinding("key.dragon.followYaw", Keyboard.KEY_F9, KEY_CATEGORY);
    	KEY_LOCKEDY = new KeyBinding("key.dragon.lockY", Keyboard.KEY_F10, KEY_CATEGORY);
    	BOOST=new KeyBinding("key.dragon.boost", Keyboard.KEY_LCONTROL, KEY_CATEGORY);
    	DISMOUNT = new KeyBinding("key.dragon.dismount",Keyboard.KEY_X, KEY_CATEGORY);
   // 	KEY_FLY_UP = new KeyBinding("key.dragon.flyUp", Keyboard.KEY_SPACE, KEY_CATEGORY);
        ClientRegistry.registerKeyBinding(KEY_BREATH);
      //  ClientRegistry.registerKeyBinding(KEY_FLY_UP);
       // ClientRegistry.registerKeyBinding(KEY_FLY_DOWN);
        ClientRegistry.registerKeyBinding(KEY_HOVERCANCEL);
        ClientRegistry.registerKeyBinding(dragon_change_view);
        ClientRegistry.registerKeyBinding(BOOST);
        ClientRegistry.registerKeyBinding(FOLLOW_YAW);
        ClientRegistry.registerKeyBinding(DISMOUNT);
        ClientRegistry.registerKeyBinding(KEY_LOCKEDY);

    	  
    }
    
/*    @SubscribeEvent
    public void onTick(ClientTickEvent evt) {
        BitSet flags = dcm.getFlags();
        flags.set(0, KEY_FLY_UP.isKeyDown());
        flags.set(1, KEY_FLY_DOWN.isKeyDown());
        flags.set(2, KEY_HOVERCANCEL.isPressed());
        
        // send message to server if it has changed
        if (dcm.hasChanged()) {
            DragonMounts.NETWORK_WRAPPER.sendToServer(dcm);
        }
    }
    */
}
