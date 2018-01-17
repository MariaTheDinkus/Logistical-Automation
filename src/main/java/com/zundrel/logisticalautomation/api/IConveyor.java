package com.zundrel.logisticalautomation.api;

public interface IConveyor {
    ConveyorType getConveyorType();

    float getSpeed(ConveyorTier tier);
}