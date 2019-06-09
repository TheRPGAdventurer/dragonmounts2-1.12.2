package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.sound;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breath.sound.SoundEffectName;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

/**
 * Created by TGG on 24/06/2016.
 * Plays nothing
 */
public class ComponentSoundSilent extends ComponentSound
{
  public ComponentSoundSilent()
  {
    super(SILENCE, SoundCategory.HOSTILE);
    final float OFF_VOLUME = 0.0F;
    volume = OFF_VOLUME;
  }

  @Override
  public void update()
  {
    final float OFF_VOLUME = 0.0F;
    this.volume = OFF_VOLUME;
    setDonePlaying();
  }

  static private SoundEvent SILENCE = SoundEffectName.SILENCE.getSoundEvent();
}
