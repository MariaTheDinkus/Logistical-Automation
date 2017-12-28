package com.zundrel.logisticalautomation.common.info;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	public static Configuration configuration;
	public static boolean enableInverseConveyors = false;

	public static void init(File file) {
		if (configuration == null) {
			configuration = new Configuration(file);
			loadConfiguration();
		}
	}

	public static void loadConfiguration() {
		enableInverseConveyors = configuration.getBoolean("Enable Inverse Conveyors", Configuration.CATEGORY_GENERAL, enableInverseConveyors, "Enables inverse conveyors");
		if (configuration.hasChanged()) {
			configuration.save();
		}
	}
}