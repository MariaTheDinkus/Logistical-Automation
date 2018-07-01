package com.zundrel.logisticalautomation.api.conveyor;

public interface IShowHopper {
	default boolean showConveyorHopper() {
		return true;
	}

	default boolean compareFacing() {
		return true;
	}
}
