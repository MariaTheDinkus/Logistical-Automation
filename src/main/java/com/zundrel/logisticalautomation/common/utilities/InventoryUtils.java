package com.zundrel.logisticalautomation.common.utilities;

import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityExtractor;
import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityFilter;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

	public static ItemStack extractStackFromInventory(World world, BlockPos pos, EnumFacing facing) {
		BlockPos extractPos = pos.offset(facing.getOpposite());
		TileEntity invTile = world.getTileEntity(extractPos);

		if (invTile != null && invTile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
			IItemHandler inv = invTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);

			TileEntityExtractor tile = (TileEntityExtractor) world.getTileEntity((pos));
			ItemStack stack = ItemStack.EMPTY;

			for (int i = 0; i < inv.getSlots(); i++) {
				if (tile.filterContainsItem(inv.extractItem(i, 1, true))) {
					stack = inv.extractItem(i, 1, false);
				}

				if (!stack.isEmpty()) {
					break;
				}
			}

			return stack;
		}

		return ItemStack.EMPTY;
	}
}
