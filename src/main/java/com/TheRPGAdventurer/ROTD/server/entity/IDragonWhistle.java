package com.TheRPGAdventurer.ROTD.server.entity;

import net.minecraft.entity.player.EntityPlayer;

public interface IDragonWhistle { 

	void Whistle(EntityPlayer player);
	
	 void nothing(boolean nothing);

	 void follow(boolean follow);

	 void circle(boolean circle);

	 void come(boolean landToPlayer);
}
