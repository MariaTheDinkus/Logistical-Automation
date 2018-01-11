package com.zundrel.logisticalautomation.common.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;

import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityFilter;
import com.zundrel.logisticalautomation.common.info.ModInfo;

public class TileRegistry {
	public static void init() {
		GameRegistry.registerTileEntity(TileEntityFilter.class, ModInfo.MOD_ID + ":" + "filter");
	}
}