package com.zundrel.logisticalautomation.api;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.common.util.INBTSerializable;

public class BlockPosLine implements INBTSerializable<NBTTagCompound> {
    private BlockPos start;
    private BlockPos end;

    public BlockPosLine(int x, int y, int z, int x2, int y2, int z2) {
        start = new BlockPos(x, y, z);
        end = new BlockPos(x2, y2, z2);
    }

    public BlockPosLine(double x, double y, double z, double x2, double y2, double z2) {
        start = new BlockPos(x, y, z);
        end = new BlockPos(x2, y2, z2);
    }

    public BlockPosLine(Vec3d vec, Vec3d vec2) {
        this(vec.x, vec.y, vec.z, vec2.x, vec2.y, vec2.z);
    }

    public BlockPosLine(Vec3i source, Vec3i source2) {
        this(source.getX(), source.getY(), source.getZ(), source2.getX(), source2.getY(), source2.getZ());
    }

    public BlockPos getStart() {
        return start;
    }

    public void setStart(BlockPos start) {
        this.start = start;
    }

    public BlockPos getEnd() {
        return end;
    }

    public void setEnd(BlockPos end) {
        this.end = end;
    }

    public EnumFacing getLineFacing() {
        if (Math.abs(end.getX() - start.getX()) > 0) {
            return EnumFacing.EAST;
        } else if (Math.abs(end.getY() - start.getY()) > 0) {
            return EnumFacing.UP;
        } else if (Math.abs(end.getZ() - start.getZ()) > 0) {
            return EnumFacing.NORTH;
        } else {
            return null;
        }
    }

    public boolean isValid(BlockPos pos) {
        switch (getLineFacing()) {
            case EAST:
                return (pos.getX() >= start.getX() && pos.getX() <= end.getX());
            case UP:
                return (pos.getY() >= start.getY() && pos.getY() <= end.getY());
            case NORTH:
                return (pos.getZ() >= start.getZ() && pos.getZ() <= end.getZ());
            default:
                return false;
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("startx", start.getX());
        tag.setInteger("starty", start.getY());
        tag.setInteger("startz", start.getZ());
        tag.setInteger("endx", end.getX());
        tag.setInteger("endy", end.getY());
        tag.setInteger("endz", end.getZ());
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        int startx;
        int starty;
        int startz;
        int endx;
        int endy;
        int endz;

        startx = tag.getInteger("startx");
        starty = tag.getInteger("starty");
        startz = tag.getInteger("startz");
        endx = tag.getInteger("endx");
        endy = tag.getInteger("endy");
        endz = tag.getInteger("endz");

        setStart(new BlockPos(startx, starty, startz));
        setEnd(new BlockPos(endx, endy, endz));
    }
}
