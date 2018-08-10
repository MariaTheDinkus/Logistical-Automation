package com.zundrel.logisticalautomation.common.containers;

import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityExtractor;
import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityFilter;
import com.zundrel.logisticalautomation.common.containers.slots.SlotFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class ContainerExtractor extends Container {

    public TileEntityExtractor tile;
    EntityPlayer player;

    public ContainerExtractor(TileEntityExtractor tile, EntityPlayer player) {
        super();
        this.tile = tile;
        this.player = player;
        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlotToContainer(new Slot(player.inventory, i1 + k * 9 + 9, 8 + i1 * 18, 50 + k * 18));
            }
        }
        for (int l = 0; l < 9; ++l) {
            this.addSlotToContainer(new Slot(player.inventory, l, 8 + l * 18, 108));
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new SlotFilter(tile.getFilterInventory(), i, 8 + i * 18, 18, player));
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