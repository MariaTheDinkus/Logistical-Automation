package com.zundrel.logisticalautomation.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.zundrel.logisticalautomation.common.CommonProxy;

public class ClientProxy extends CommonProxy {
	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.side.isClient() ? Minecraft.getMinecraft().player : null;
	}
}