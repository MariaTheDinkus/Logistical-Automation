package com.zundrel.logisticalautomation.common.network;

import com.zundrel.logisticalautomation.common.containers.ContainerExtractor;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.zundrel.logisticalautomation.common.containers.ContainerFilter;

public class MessageButton implements IMessage, IMessageHandler<MessageButton, IMessage> {
	NBTTagCompound nbt;

	public MessageButton() {}

	public MessageButton(NBTTagCompound nbt) {
		this.nbt = nbt;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		nbt = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, nbt);
	}

	@Override
	public IMessage onMessage(MessageButton message, MessageContext ctx) {
		ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
			EntityPlayer player = ctx.getServerHandler().player;
			if (player.openContainer instanceof ContainerFilter) {
				((ContainerFilter) player.openContainer).tile.handleMessage(message.nbt);
			} else if (player.openContainer instanceof ContainerExtractor) {
				((ContainerExtractor) player.openContainer).tile.handleMessage(message.nbt);
			}
		});
		return null;
	}

}