package com.zundrel.logisticalautomation;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.zundrel.logisticalautomation.common.CommonProxy;
import com.zundrel.logisticalautomation.common.info.ConfigHandler;
import com.zundrel.logisticalautomation.common.info.ModInfo;
import com.zundrel.logisticalautomation.common.registry.BlockRegistry;

@Mod(name = ModInfo.MOD_NAME, modid = ModInfo.MOD_ID, version = ModInfo.MOD_VERSION)
public class LogisticalAutomation {
	@Mod.Instance(ModInfo.MOD_ID)
	public static LogisticalAutomation instance;

	@SidedProxy(clientSide = ModInfo.MOD_CLIENT_PROXY, serverSide = ModInfo.MOD_SERVER_PROXY)
	public static CommonProxy proxy;

	public static File configFile;

	public static CreativeTabs tabGeneral = new CreativeTabs(ModInfo.MOD_ID) {
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(BlockRegistry.conveyor_normal);
		}
	};

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configFile = event.getSuggestedConfigurationFile();
		ConfigHandler.init(configFile);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}