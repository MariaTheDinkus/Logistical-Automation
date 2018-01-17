package com.zundrel.logisticalautomation.common.utilities;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

/* Thanks to BluSunrize for this inventory insertion code */
public class InventoryUtils {
    public static boolean canInsertStackIntoInventory(TileEntity inventory, ItemStack stack, EnumFacing side) {
        if (!stack.isEmpty() && inventory != null && inventory.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
            IItemHandler handler = inventory.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            ItemStack temp = ItemHandlerHelper.insertItem(handler, stack.copy(), true);
            return temp.isEmpty() || temp.getCount() < stack.getCount();
        }
        return false;
    }

    public static ItemStack insertStackIntoInventory(TileEntity inventory, ItemStack stack, EnumFacing side) {
        if (!stack.isEmpty() && inventory != null && inventory.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
            IItemHandler handler = inventory.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            ItemStack temp = ItemHandlerHelper.insertItem(handler, stack.copy(), true);
            if (temp.isEmpty() || temp.getCount() < stack.getCount())
                return ItemHandlerHelper.insertItem(handler, stack, false);
        }
        return stack;
    }

    public static ItemStack insertStackIntoInventory(TileEntity inventory, ItemStack stack, EnumFacing side, boolean simulate) {
        if (inventory != null && !stack.isEmpty() && inventory.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
            IItemHandler handler = inventory.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            return ItemHandlerHelper.insertItem(handler, stack.copy(), simulate);
        }
        return stack;
    }
}
