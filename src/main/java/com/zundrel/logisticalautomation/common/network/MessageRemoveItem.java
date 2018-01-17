package com.zundrel.logisticalautomation.common.network;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.common.capability.CapabilityTesting;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRemoveItem implements IMessage, IMessageHandler<MessageRemoveItem, IMessage> {
    BlockPos pos;

    public MessageRemoveItem() {
    }

    public MessageRemoveItem(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    @Override
    public IMessage onMessage(MessageRemoveItem message, MessageContext ctx) {
        EntityPlayer player = LogisticalAutomation.proxy.getPlayerEntity(ctx);

        CapabilityTesting test = player.world.getCapability(LogisticalAutomation.TEST_DATA, null);

        test.removeItemAtPos(message.pos, false);

        return null;
    }

}