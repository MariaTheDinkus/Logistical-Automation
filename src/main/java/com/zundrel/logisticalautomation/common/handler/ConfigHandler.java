package com.zundrel.logisticalautomation.common.handler;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

import com.zundrel.logisticalautomation.common.info.ModInfo;

@Config(modid = ModInfo.MOD_ID)
public class ConfigHandler {
	@Comment("Determines whether or not to enable inverse (upside-down) conveyors.")
	@RequiresMcRestart
	public static boolean enableInverseConveyors = false;
}
