package com.zundrel.logisticalautomation.client;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import com.zundrel.logisticalautomation.common.handler.ConfigHandler;
import com.zundrel.logisticalautomation.common.info.ModInfo;
import com.zundrel.logisticalautomation.common.registry.BlockRegistry;
import com.zundrel.logisticalautomation.common.registry.ItemRegistry;

public class LogisticCreativeTabs {

	public static class LogisticGeneralTab extends CreativeTabs {
		NonNullList<ItemStack> list;
		public static LogisticGeneralTab INSTANCE = new LogisticGeneralTab();

		public LogisticGeneralTab() {
			super(ModInfo.MOD_ID + "_general");
		}

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ItemRegistry.logistic_wrench);
		}

		@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> list) {
			this.list = list;

			this.addItem(ItemRegistry.logistic_wrench);
		}

		private void addItem(Item item) {
			item.getSubItems(this, list);
		}

		private void addBlock(Block block) {
			ItemStack stack = new ItemStack(block);
			block.getSubBlocks(this, list);
		}
	}

	public static class LogisticConveyorTab extends CreativeTabs {
		NonNullList<ItemStack> list;
		public static LogisticConveyorTab INSTANCE = new LogisticConveyorTab();

		public LogisticConveyorTab() {
			super(ModInfo.MOD_ID + "_conveyor");
		}

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(BlockRegistry.conveyor_normal);
		}

		@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> list) {
			this.list = list;

			this.addBlock(BlockRegistry.conveyor_normal);
			this.addBlock(BlockRegistry.conveyor_fast);
			this.addBlock(BlockRegistry.conveyor_express);

			if (ConfigHandler.enableInverseConveyors) {
				this.addBlock(BlockRegistry.conveyor_inverse_normal);
				this.addBlock(BlockRegistry.conveyor_inverse_normal);
				this.addBlock(BlockRegistry.conveyor_inverse_normal);
			}

			this.addBlock(BlockRegistry.conveyor_stair_normal);
			this.addBlock(BlockRegistry.conveyor_stair_fast);
			this.addBlock(BlockRegistry.conveyor_stair_express);

			this.addBlock(BlockRegistry.conveyor_stair_down_normal);
			this.addBlock(BlockRegistry.conveyor_stair_down_fast);
			this.addBlock(BlockRegistry.conveyor_stair_down_express);

			this.addBlock(BlockRegistry.conveyor_vertical_normal);
			this.addBlock(BlockRegistry.conveyor_vertical_fast);
			this.addBlock(BlockRegistry.conveyor_vertical_express);
		}

		private void addItem(Item item) {
			item.getSubItems(this, list);
		}

		private void addBlock(Block block) {
			ItemStack stack = new ItemStack(block);
			block.getSubBlocks(this, list);
		}
	}

}