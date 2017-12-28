package com.zundrel.logisticalautomation.api;

public interface IConveyor {
	public ConveyorTier getConveyorTier();

	public ConveyorType getConveyorType();

	public float getSpeed(ConveyorTier tier);
}