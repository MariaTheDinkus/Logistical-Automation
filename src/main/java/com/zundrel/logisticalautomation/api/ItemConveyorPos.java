package com.zundrel.logisticalautomation.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;

public class ItemConveyorPos implements INBTSerializable<NBTTagCompound> {
    private double conveyorPosition;
    private EnumFacing facing;
    private ItemStack stack;

    public ItemConveyorPos(double conveyorPosition, EnumFacing facing, ItemStack stack) {
        this.conveyorPosition = conveyorPosition;
        this.facing = facing;
        this.stack = stack;
    }

    public ItemConveyorPos(ItemStack stack, EnumFacing facing) {
        this(0, facing, stack);
    }

    public ItemConveyorPos(NBTTagCompound compound) {
        this.deserializeNBT(compound);
    }

    public double getConveyorPosition() {
        return conveyorPosition;
    }

    public void setConveyorPosition(double conveyorPosition) {
        this.conveyorPosition = conveyorPosition;
    }

    public EnumFacing getFacing() {
        return facing;
    }

    public void setFacing(EnumFacing facing) {
        this.facing = facing;
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setDouble("conveyorPosition", conveyorPosition);

        tag.setInteger("facing", facing.getHorizontalIndex());

        if (stack != null) {
            tag.setTag("item", stack.serializeNBT());
        } else {
            tag.setTag("item", ItemStack.EMPTY.serializeNBT());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        setConveyorPosition(tag.getDouble("conveyorPosition"));

        setFacing(EnumFacing.getHorizontal(tag.getInteger("facing")));

        setStack(new ItemStack((NBTTagCompound) tag.getTag("item")));
    }

}
