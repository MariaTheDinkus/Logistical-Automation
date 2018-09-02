package com.zundrel.logisticalautomation.common.network;

import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityExtractor;
import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityFilter;
import com.zundrel.logisticalautomation.common.containers.ContainerExtractor;
import io.netty.buffer.ByteBuf;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.zundrel.logisticalautomation.common.containers.ContainerFilter;

public class MessageButton implements IMessage, IMessageHandler<MessageButton, IMessage> {
	NBTTagCompound nbt;
	BlockPos pos;

	public MessageButton() {}

	public MessageButton(NBTTagCompound nbt, BlockPos pos) {
		this.nbt = nbt;
		this.pos = pos;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		nbt = ByteBufUtils.readTag(buf);
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		pos = new BlockPos(x, y, z);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, nbt);
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

	@Override
	public IMessage onMessage(MessageButton message, MessageContext ctx) {
		ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
			EntityPlayerMP player = ctx.getServerHandler().player;
			WorldServer world = player.getServerWorld();

			if (world.getTileEntity(message.pos) != null) {
				if (world.getTileEntity(message.pos) instanceof TileEntityFilter) {
					((TileEntityFilter) world.getTileEntity(message.pos)).handleMessage(message.nbt);
				} else if (world.getTileEntity(message.pos) instanceof TileEntityExtractor) {
					((TileEntityExtractor) world.getTileEntity(message.pos)).handleMessage(message.nbt);
				}
			}
		});
		return null;
	}

}