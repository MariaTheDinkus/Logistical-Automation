package com.zundrel.logisticalautomation.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.zundrel.logisticalautomation.common.CommonProxy;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerItemModel(Item i, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(i, meta, new ModelResourceLocation(i.getRegistryName(), "inventory"));
	}

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.side.isClient() ? Minecraft.getMinecraft().player : super.getPlayerEntity(ctx);
	}
}