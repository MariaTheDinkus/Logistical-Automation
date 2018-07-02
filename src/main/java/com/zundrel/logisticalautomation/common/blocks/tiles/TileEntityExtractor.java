package com.zundrel.logisticalautomation.common.blocks.tiles;

import com.zundrel.logisticalautomation.common.blocks.machines.BlockExtractor;
import com.zundrel.logisticalautomation.common.utilities.InventoryUtils;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityExtractor extends TileEntity implements ITickable {
	@Override
	public void update() {
		EnumFacing facing = getWorld().getBlockState(this.getPos()).getValue(BlockExtractor.FACING);

		if(!getWorld().isRemote && getWorld().getTotalWorldTime() % 20 == 0) {
			TileEntity tile2 = getWorld().getTileEntity(getPos().add(facing.getOpposite().getDirectionVec()));

			if (tile2 != null && tile2.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
				IItemHandler itemHandler = tile2.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);

				for (int i = 0; i < itemHandler.getSlots(); i++) {
					if (!itemHandler.getStackInSlot(i).isEmpty()) {
						ItemStack output = itemHandler.extractItem(i, 4, false);

						BlockPos pos = getPos().offset(facing);

						EntityItem item = new EntityItem(getWorld(), pos.getX() + 0.5, getPos().getY() + (1F / 16F), pos.getZ() + 0.5, output);
						item.motionX = 0;
						item.motionY = 0;
						item.motionZ = 0;

						getWorld().spawnEntity(item);

						break;
					}
				}
			}
		}
	}
}
