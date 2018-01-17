package com.zundrel.logisticalautomation.api;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map.Entry;

public class ItemConveyorPosHelper {
    public static NBTTagCompound saveAllItems(NBTTagCompound tag, HashMap<BlockPos, ItemConveyorPos> list) {
        return saveAllItems(tag, list, true);
    }

    public static NBTTagCompound saveAllItems(NBTTagCompound tag, HashMap<BlockPos, ItemConveyorPos> list, boolean saveEmpty) {
        NBTTagList nbttaglist = new NBTTagList();

        for (Entry<BlockPos, ItemConveyorPos> entry : list.entrySet()) {
            BlockPos pos = entry.getKey();
            ItemConveyorPos itemConveyorPos = entry.getValue();

            if (pos != null && itemConveyorPos != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setInteger("x", pos.getX());
                nbttagcompound.setInteger("y", pos.getY());
                nbttagcompound.setInteger("z", pos.getZ());
                nbttagcompound.setTag("Item", itemConveyorPos.serializeNBT());
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        if (!nbttaglist.hasNoTags() || saveEmpty) {
            tag.setTag("Items", nbttaglist);
        }

        return tag;
    }

    public static void loadAllItems(NBTTagCompound tag, HashMap<BlockPos, ItemConveyorPos> list) {
        NBTTagList nbttaglist = tag.getTagList("Items", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            BlockPos pos = new BlockPos(nbttagcompound.getInteger("x"), nbttagcompound.getInteger("y"), nbttagcompound.getInteger("z"));

            list.put(pos, new ItemConveyorPos(nbttagcompound.getCompoundTag("Item")));
        }
    }
}
