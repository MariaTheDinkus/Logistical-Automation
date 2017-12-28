package com.zundrel.logisticalautomation.common.items;

import net.minecraft.item.Item;

import com.zundrel.logisticalautomation.LogisticalAutomation;

public class ItemBasic extends Item {
	public ItemBasic(String unlocalizedName) {
		setRegistryName(unlocalizedName);
		setUnlocalizedName(this.getRegistryName().toString());

		setCreativeTab(LogisticalAutomation.tabGeneral);
	}

	public void registerModel(Item i) {
		LogisticalAutomation.proxy.registerItemModel(i, 0, this.getUnlocalizedName().substring(5));
	}
}