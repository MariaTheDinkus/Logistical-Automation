package com.zundrel.logisticalautomation.common.utilities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.oredict.OreDictionary;

import java.util.stream.IntStream;

public class Filter implements INBTSerializable<NBTTagCompound> {
    public boolean meta = true, nbt = false, ore = false, white = true;

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("meta", meta);
        tag.setBoolean("nbt", nbt);
        tag.setBoolean("ore", ore);
        tag.setBoolean("white", white);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        meta = tag.getBoolean("meta");
        nbt = tag.getBoolean("nbt");
        ore = tag.getBoolean("ore");
        white = tag.getBoolean("white");
    }

    public boolean match(ItemStack a, ItemStack b) {
        if (a.isEmpty() || b.isEmpty())
            return false;
        boolean met = !meta || a.getItemDamage() == b.getItemDamage();
        boolean nb = !nbt || ItemStack.areItemStackTagsEqual(a, b);
        boolean or = ore && IntStream.of(OreDictionary.getOreIDs(a)).anyMatch(i -> IntStream.of(OreDictionary.getOreIDs(b)).anyMatch(j -> j == i));
        return (a.getItem() == b.getItem() || or) && met && nb;
    }

}