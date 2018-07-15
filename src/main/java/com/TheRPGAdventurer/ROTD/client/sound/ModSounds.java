package com.TheRPGAdventurer.ROTD.client.sound;

import com.TheRPGAdventurer.ROTD.DragonMounts;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(DragonMounts.MODID)
public class ModSounds {
	
	@ObjectHolder("mob.dragon.step")
	public static final SoundEvent ENTITY_DRAGON_STEP = createSoundEvent("mob.dragon.step");

	@ObjectHolder("mob.dragon.breathe")
	public static final SoundEvent ENTITY_DRAGON_BREATHE = createSoundEvent("mob.dragon.breathe");

	@ObjectHolder("mob.dragon.death")
	public static final SoundEvent ENTITY_DRAGON_DEATH = createSoundEvent("mob.dragon.death");
	
	@ObjectHolder("mob.dragon.growl")
	public static final SoundEvent ENTITY_DRAGON_GROWL = createSoundEvent("mob.dragon.growl");
	
	@ObjectHolder("mob.dragon.nethergrowl")
	public static final SoundEvent ENTITY_NETHER_DRAGON_GROWL = createSoundEvent("mob.dragon.nethergrowl");
	
	@ObjectHolder("mob.dragon.skeletongrowl")
	public static final SoundEvent ENTITY_SKELETON_DRAGON_GROWL = createSoundEvent("mob.dragon.skeletongrowl");
	
	private static SoundEvent createSoundEvent(final String soundName) {
		final ResourceLocation soundID = new ResourceLocation(DragonMounts.MODID, soundName);
		return new SoundEvent(soundID).setRegistryName(soundID);
	}

	@Mod.EventBusSubscriber(modid = DragonMounts.MODID)
	public static class RegistrationHandler {
		@SubscribeEvent
		public static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) {
			event.getRegistry().registerAll(
					ENTITY_DRAGON_STEP,
					ENTITY_DRAGON_BREATHE,
					ENTITY_DRAGON_DEATH,
					ENTITY_DRAGON_GROWL,
					ENTITY_NETHER_DRAGON_GROWL,
					ENTITY_SKELETON_DRAGON_GROWL
					
			);
		}
	}
	
	  public final String getJsonName() {return DragonMounts.MODID + ":" + jsonName;}

	  private ModSounds(String i_jsonName) {
	    jsonName = i_jsonName;
	  }
	  
	  private final String jsonName;
}
