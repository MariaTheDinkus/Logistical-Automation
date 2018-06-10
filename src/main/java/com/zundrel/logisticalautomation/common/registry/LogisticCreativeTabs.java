package com.zundrel.logisticalautomation.common.registry;

import javax.annotation.Nonnull;

import amerifrance.guideapi.api.GuideAPI;
import com.zundrel.logisticalautomation.compat.guide.GuideLogisticalAutomation;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import com.zundrel.logisticalautomation.common.info.ModInfo;

public class LogisticCreativeTabs {

	// public static class LogisticGeneralTab extends CreativeTabs {
	// public static LogisticGeneralTab INSTANCE = new LogisticGeneralTab();
	// NonNullList<ItemStack> list;
	//
	// private LogisticGeneralTab() {
	// super(ModInfo.MOD_ID + "_general");
	// }
	//
	// @Override
	// @Nonnull
	// public ItemStack getTabIconItem() {
	// return new ItemStack(ItemRegistry.logistic_wrench);
	// }
	//
	// @Override
	// public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> list)
	// {
	// this.list = list;
	//
	// }
	//
	// private void addItem(Item item) {
	// item.getSubItems(this, list);
	// }
	//
	// private void addBlock(Block block) {
	// block.getSubBlocks(this, list);
	// }
	// }

	public static class LogisticConveyorTab extends CreativeTabs {
		public static LogisticConveyorTab INSTANCE = new LogisticConveyorTab();
		NonNullList<ItemStack> list;

		private LogisticConveyorTab() {
			super(ModInfo.MOD_ID + "_conveyor");
		}

		@Override
		@Nonnull
		public ItemStack getTabIconItem() {
			return new ItemStack(BlockRegistry.conveyor_normal);
		}

		@Override
		public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> list) {
			this.list = list;

			this.addItem(GuideAPI.getStackFromBook(GuideLogisticalAutomation.logisticsGuide).getItem());
			this.addItem(ItemRegistry.logistic_wrench);

			this.addItem(ItemRegistry.iron_stick);
			this.addItem(ItemRegistry.roller_set);
            this.addItem(ItemRegistry.iron_motor_set);
            this.addItem(ItemRegistry.gold_motor_set);
            this.addItem(ItemRegistry.diamond_motor_set);
            this.addItem(ItemRegistry.leather_belt_set);

			this.addBlock(BlockRegistry.filter);
			this.addBlock(BlockRegistry.splitter);
			this.addBlock(BlockRegistry.junction);
			this.addBlock(BlockRegistry.halver);

			this.addBlock(BlockRegistry.blowtorch);
			this.addBlock(BlockRegistry.poly_blowtorch);

			this.addBlock(BlockRegistry.grate);

			this.addBlock(BlockRegistry.catwalk);
			this.addBlock(BlockRegistry.catwalk_stairs);
			this.addBlock(BlockRegistry.catwalk_pillar);

            this.addBlock(BlockRegistry.conveyor_normal);
            this.addBlock(BlockRegistry.conveyor_fast);
            this.addBlock(BlockRegistry.conveyor_express);

            this.addBlock(BlockRegistry.conveyor_stair_normal);
            this.addBlock(BlockRegistry.conveyor_stair_fast);
            this.addBlock(BlockRegistry.conveyor_stair_express);

            this.addBlock(BlockRegistry.conveyor_vertical_normal);
            this.addBlock(BlockRegistry.conveyor_vertical_fast);
            this.addBlock(BlockRegistry.conveyor_vertical_express);
		}

		private void addItem(Item item) {
			item.getSubItems(this, list);
		}

		private void addBlock(Block block) {
			block.getSubBlocks(this, list);
		}
	}

}