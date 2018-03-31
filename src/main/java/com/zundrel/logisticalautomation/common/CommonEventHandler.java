package com.zundrel.logisticalautomation.common;

import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import com.zundrel.logisticalautomation.common.info.ModInfo;

@EventBusSubscriber(modid = ModInfo.MOD_ID)
public class CommonEventHandler {
	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<World> event) {

	}

	@SubscribeEvent
	public static void onWorldTick(WorldTickEvent event) {

	}
}
