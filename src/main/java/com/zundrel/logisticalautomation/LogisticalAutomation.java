package com.zundrel.logisticalautomation;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import com.zundrel.logisticalautomation.common.CommonProxy;
import com.zundrel.logisticalautomation.common.handler.GuiHandler;
import com.zundrel.logisticalautomation.common.info.ModInfo;
import com.zundrel.logisticalautomation.common.network.MessageButton;
import com.zundrel.logisticalautomation.common.registry.TileRegistry;

@Mod(name = ModInfo.MOD_NAME, modid = ModInfo.MOD_ID, version = ModInfo.MOD_VERSION)
public class LogisticalAutomation {
	@Mod.Instance(ModInfo.MOD_ID)
	public static LogisticalAutomation instance;
	@SidedProxy(clientSide = ModInfo.MOD_CLIENT_PROXY, serverSide = ModInfo.MOD_SERVER_PROXY)
	public static CommonProxy proxy;

	public static SimpleNetworkWrapper networkWrapper;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		TileRegistry.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

		networkWrapper = new SimpleNetworkWrapper(ModInfo.MOD_ID);
		networkWrapper.registerMessage(MessageButton.class, MessageButton.class, 0, Side.SERVER);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}