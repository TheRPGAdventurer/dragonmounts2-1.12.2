package com.TheRPGAdventurer.ROTD.server.entity.helper;

import net.ilexiconn.llibrary.server.entity.EntityProperties;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;

public class IceEntityProperties extends EntityProperties<EntityLiving> {

	public boolean isStone;
	public int breakLvl;

	@Override
	public int getTrackingTime() {
		return 20;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		compound.setBoolean("TurnedToStone", isStone);
		compound.setInteger("StoneBreakLvl", breakLvl);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		this.isStone = compound.getBoolean("TurnedToStone");
		this.breakLvl = compound.getInteger("StoneBreakLvl");
	}

	@Override
	public void init() {
		isStone = false;
		breakLvl = 0;
	}

	@Override
	public String getID() {
		return "Ice And Fire - Stone Property Tracker";
	}

	@Override
	public Class<EntityLiving> getEntityClass() {
		return EntityLiving.class;
	}
}
