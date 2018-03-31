package com.zundrel.logisticalautomation.api;

import net.minecraft.util.IStringSerializable;

public enum EnumConveyorTier implements IStringSerializable {
	NORMAL("Normal", 0.125F), FAST("Fast", 0.25F), EXPRESS("Express", 0.375F);

	private String name;
	private float speed;

	private EnumConveyorTier(String name, float speed) {
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