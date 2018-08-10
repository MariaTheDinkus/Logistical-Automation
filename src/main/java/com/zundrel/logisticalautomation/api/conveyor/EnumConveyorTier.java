package com.zundrel.logisticalautomation.api.conveyor;

import net.minecraft.util.IStringSerializable;

public enum EnumConveyorTier implements IStringSerializable {
	STONE("Stone", 0.0625F), NORMAL("Normal", 0.125F), FAST("Fast", 0.25F), EXPRESS("Express", 0.375F);

	private String name;
	private float speed;

	EnumConveyorTier(String name, float speed) {
		this.name = name;
		this.speed = speed;
	}

	@Override
	public String getName() {
		return name;
	}

	public float getSpeed() {
		return speed;
	}
}