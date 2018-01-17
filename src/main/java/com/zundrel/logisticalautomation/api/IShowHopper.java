package com.zundrel.logisticalautomation.api;

public interface IShowHopper {
    default boolean showConveyorHopper() {
        return true;
    }

    default boolean compareFacing() {
        return true;
    }
}
