package com.zundrel.logisticalautomation.common.containers.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotFilter extends SlotItemHandler {

	EntityPlayer player;

	public SlotFilter(NonNullList<ItemStack> stacks, int index, int x, int y, EntityPlayer player) {
		super(new ItemStackHandler(stacks), index, x, y);

		this.player = player;
	}

	@Override
	public ItemStack decrStackSize(int amount) {
		putStack(ItemStack.EMPTY);
		return ItemStack.EMPTY;
	}

    @Override
	public void putStack(ItemStack stack) {
		super.putStack(stack.isEmpty() ? stack : ItemHandlerHelper.copyStackWithSize(stack, 1));
	}

    @Override
    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
	    stack.setCount(0);

        return ItemStack.EMPTY;
    }

    @Override
	public boolean isItemValid(ItemStack stack) {
		putStack(stack);
		return false;
	}

}