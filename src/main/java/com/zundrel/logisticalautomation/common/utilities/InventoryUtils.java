package com.zundrel.logisticalautomation.common.utilities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
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

	public static void extractStackFromInventory(TileEntity tileInventory, EnumFacing facing) {
		if(!tileInventory.getWorld().isRemote && tileInventory.getWorld().getTotalWorldTime() % 5 == 0)
		{
			System.out.println("HAI");
			TileEntity tile2 = tileInventory.getWorld().getTileEntity(tileInventory.getPos().add(facing.getOpposite().getDirectionVec()));
			if(tile2 != null && tile2 instanceof IInventory)
			{
				IInventory inventory = (IInventory) tile2;
				boolean empty = true;
				for (int i = 0; i < inventory.getSizeInventory(); i++) {
					if (inventory.getStackInSlot(i) != ItemStack.EMPTY) {
						empty = false;
						break;
					}
				}
				if(!empty)
				{
					for(int i = 0; i < inventory.getSizeInventory(); i++)
					{
						if(inventory.getStackInSlot(i) != ItemStack.EMPTY)
						{
							EntityItem entityItem = new EntityItem(tileInventory.getWorld(), tileInventory.getPos().getX() + 0.5F, tileInventory.getPos().getY() + (1F / 16F), tileInventory.getPos().getZ() + 0.5F, new ItemStack(
									inventory.getStackInSlot(i).getItem(), 1, inventory.getStackInSlot(i).getMetadata()));
							if(entityItem.getItem() != ItemStack.EMPTY)
							{
								tileInventory.getWorld().spawnEntity(entityItem);
							}
							entityItem.motionX = 0;
							entityItem.motionY = 0;
							entityItem.motionZ = 0;
							entityItem.setPosition(tileInventory.getPos().getX() + 0.5F, tileInventory.getPos().getY() + (1F / 16F), tileInventory.getPos().getZ() + 0.5F);
							inventory.decrStackSize(i, 1);
							break;
						}
					}
				}
			}
		}
	}
}
