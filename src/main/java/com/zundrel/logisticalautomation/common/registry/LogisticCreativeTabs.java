package com.zundrel.logisticalautomation.common.registry;

import javax.annotation.Nonnull;

import amerifrance.guideapi.api.GuideAPI;
import com.zundrel.logisticalautomation.LogisticalAutomation;
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
			return new ItemStack(LogisticalAutomation.Blocks.conveyor_normal);
		}

		@Override
		public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> list) {
			this.list = list;

			this.addItem(GuideAPI.getStackFromBook(GuideLogisticalAutomation.logisticsGuide).getItem());
			this.addItem(LogisticalAutomation.Items.logistic_wrench);

			this.addItem(LogisticalAutomation.Items.iron_stick);
			this.addItem(LogisticalAutomation.Items.roller_set);
            this.addItem(LogisticalAutomation.Items.iron_motor_set);
            this.addItem(LogisticalAutomation.Items.gold_motor_set);
            this.addItem(LogisticalAutomation.Items.diamond_motor_set);
            this.addItem(LogisticalAutomation.Items.leather_belt_set);

			this.addBlock(LogisticalAutomation.Blocks.filter);
			this.addBlock(LogisticalAutomation.Blocks.alternator);
			this.addBlock(LogisticalAutomation.Blocks.junction);
			this.addBlock(LogisticalAutomation.Blocks.splitter);
			this.addBlock(LogisticalAutomation.Blocks.extractor);

			this.addBlock(LogisticalAutomation.Blocks.blowtorch);
			this.addBlock(LogisticalAutomation.Blocks.poly_blowtorch);

			this.addBlock(LogisticalAutomation.Blocks.grate);

			this.addBlock(LogisticalAutomation.Blocks.catwalk);
			this.addBlock(LogisticalAutomation.Blocks.catwalk_stairs);
			this.addBlock(LogisticalAutomation.Blocks.catwalk_pillar);

			this.addBlock(LogisticalAutomation.Blocks.conveyor_stone);

            this.addBlock(LogisticalAutomation.Blocks.conveyor_normal);
            this.addBlock(LogisticalAutomation.Blocks.conveyor_fast);
            this.addBlock(LogisticalAutomation.Blocks.conveyor_express);

            this.addBlock(LogisticalAutomation.Blocks.conveyor_stair_normal);
            this.addBlock(LogisticalAutomation.Blocks.conveyor_stair_fast);
            this.addBlock(LogisticalAutomation.Blocks.conveyor_stair_express);

            this.addBlock(LogisticalAutomation.Blocks.conveyor_vertical_normal);
            this.addBlock(LogisticalAutomation.Blocks.conveyor_vertical_fast);
            this.addBlock(LogisticalAutomation.Blocks.conveyor_vertical_express);
		}

		private void addItem(Item item) {
			item.getSubItems(this, list);
		}

		private void addBlock(Block block) {
			block.getSubBlocks(this, list);
		}
	}

}