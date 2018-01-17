package com.zundrel.logisticalautomation.common.network;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.api.ItemConveyorPos;
import com.zundrel.logisticalautomation.common.capability.CapabilityTesting;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageItem implements IMessage, IMessageHandler<MessageItem, IMessage> {
    NBTTagCompound nbt;
    BlockPos pos;

    public MessageItem() {
    }

    public MessageItem(BlockPos pos, NBTTagCompound nbt) {
        this.pos = pos;
        this.nbt = nbt;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();

        pos = new BlockPos(x, y, z);
        nbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());

        ByteBufUtils.writeTag(buf, nbt);
    }

    @Override
    public IMessage onMessage(MessageItem message, MessageContext ctx) {
        EntityPlayer player = LogisticalAutomation.proxy.getPlayerEntity(ctx);

        CapabilityTesting test = player.world.getCapability(LogisticalAutomation.TEST_DATA, null);

        test.setItemAtPos(message.pos, new ItemConveyorPos(message.nbt), false);

        return null;
    }

}