package com.zundrel.logisticalautomation.common.capability;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.api.ItemConveyorPos;
import com.zundrel.logisticalautomation.api.ItemConveyorPosHelper;
import com.zundrel.logisticalautomation.common.network.MessageItem;
import com.zundrel.logisticalautomation.common.network.MessageRemoveItem;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import java.util.HashMap;
import java.util.concurrent.Callable;

public class CapabilityTesting implements ICapabilitySerializable<NBTTagCompound> {
    private World world = null;
    private HashMap<BlockPos, ItemConveyorPos> items = new HashMap<BlockPos, ItemConveyorPos>();

    public CapabilityTesting(World world) {
        this.world = world;
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(CapabilityTesting.class, new CapabilityTesting.Storage(), new CapabilityTesting.Factory());
    }

    public World getWorld() {
        return world;
    }

    public HashMap<BlockPos, ItemConveyorPos> getItems() {
        return items;
    }

    public void clearEmptyItems() {
        for (BlockPos pos : getItems().keySet()) {
            if (getItemAtPos(pos) != null && getItemAtPos(pos).getStack().isEmpty()) {
                setItemAtPos(pos, null);
            }
        }
    }

    public ItemConveyorPos getItemAtPos(BlockPos pos) {
        return getItems().get(pos);
    }

    public void setItemAtPos(BlockPos pos, ItemConveyorPos conveyorPos) {
        setItemAtPos(pos, conveyorPos, true);
    }

    public void setItemAtPos(BlockPos pos, ItemConveyorPos conveyorPos, boolean sendUpdate) {
        getItems().put(pos, conveyorPos);

        if (sendUpdate) {
            LogisticalAutomation.networkWrapper.sendToAll(new MessageItem(pos, conveyorPos.serializeNBT()));
        }
    }

    public void removeItemAtPos(BlockPos pos) {
        removeItemAtPos(pos, true);
    }

    public void removeItemAtPos(BlockPos pos, boolean sendUpdate) {
        getItems().remove(pos);

        if (sendUpdate) {
            LogisticalAutomation.networkWrapper.sendToAll(new MessageRemoveItem(pos));
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return LogisticalAutomation.TEST_DATA != null && capability == LogisticalAutomation.TEST_DATA;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return LogisticalAutomation.TEST_DATA != null && capability == LogisticalAutomation.TEST_DATA ? (T) this : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();

        ItemConveyorPosHelper.saveAllItems(nbt, getItems());

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        ItemConveyorPosHelper.loadAllItems(nbt, getItems());
    }

    public static class Storage implements Capability.IStorage<CapabilityTesting> {

        @Override
        public NBTBase writeNBT(Capability<CapabilityTesting> capability, CapabilityTesting instance, EnumFacing side) {
            return null;
        }

        @Override
        public void readNBT(Capability<CapabilityTesting> capability, CapabilityTesting instance, EnumFacing side, NBTBase nbt) {

        }

    }

    public static class Factory implements Callable<CapabilityTesting> {
        @Override
        public CapabilityTesting call() throws Exception {
            return null;
        }
    }
}
