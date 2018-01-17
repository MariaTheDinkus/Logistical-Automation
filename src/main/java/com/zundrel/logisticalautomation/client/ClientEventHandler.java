package com.zundrel.logisticalautomation.client;

import com.zundrel.logisticalautomation.common.info.ModInfo;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid = ModInfo.MOD_ID, value = Side.CLIENT)
public class ClientEventHandler {
    //private static Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public static void renderWorld(RenderWorldLastEvent event) {
//		CapabilityTesting test = mc.world.getCapability(LogisticalAutomation.TEST_DATA, null);
//
//		EntityPlayer player = mc.player;
//
//		double doubleX = -(player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks());
//		double doubleY = -(player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks());
//		double doubleZ = -(player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks());
    }
}