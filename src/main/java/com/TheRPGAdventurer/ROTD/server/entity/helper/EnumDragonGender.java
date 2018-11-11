package com.TheRPGAdventurer.ROTD.server.entity.helper;

import net.minecraft.util.IStringSerializable;

public enum EnumDragonGender implements IStringSerializable {
	
	MALE(true),
	FEMALE(false);

	public boolean isMale;
	
	EnumDragonGender(boolean isMale) {
		this.isMale = isMale;
	}

    @Override
    public String getName() {
        return name().toLowerCase();
    }

}
