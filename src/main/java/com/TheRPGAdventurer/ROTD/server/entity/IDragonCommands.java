package com.TheRPGAdventurer.ROTD.server.entity;

import com.TheRPGAdventurer.ROTD.client.items.ItemDragonWhistle;

public interface IDragonCommands {

	public void onHearFlute(EntityTameableDragon dragon, ItemDragonWhistle.Commands command);
}
