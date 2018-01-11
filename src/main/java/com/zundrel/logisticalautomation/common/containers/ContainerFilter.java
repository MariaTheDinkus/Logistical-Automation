package com.zundrel.logisticalautomation.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityFilter;
import com.zundrel.logisticalautomation.common.containers.slots.SlotFilter;

public class ContainerFilter extends Container {

	public TileEntityFilter tile;
	EntityPlayer player;

	public ContainerFilter(TileEntityFilter tile, EntityPlayer player) {
		super();
		this.tile = tile;
		this.player = player;
		for (int k = 0; k < 3; ++k) {
			for (int i1 = 0; i1 < 9; ++i1) {
				this.addSlotToContainer(new Slot(player.inventory, i1 + k * 9 + 9, 8 + i1 * 18, 114 + k * 18));
			}
		}
		for (int l = 0; l < 9; ++l) {
			this.addSlotToContainer(new Slot(player.inventory, l, 8 + l * 18, 172));
		}
		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new SlotFilter(tile.getWestFilterInventory(), i, 8 + i * 18, 18, player));
		}
		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new SlotFilter(tile.getNorthFilterInventory(), i, 8 + i * 18, 50, player));
		}
		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new SlotFilter(tile.getEastFilterInventory(), i, 8 + i * 18, 82, player));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tile != null && playerIn.getPositionVector().distanceTo(new Vec3d(tile.getPos())) < 32;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		return itemstack;
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		tile.markDirty();
	}

}