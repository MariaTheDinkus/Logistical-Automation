package com.zundrel.logisticalautomation.common.items;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBasic extends Item {
	public ItemBasic(String unlocalizedName) {
		setRegistryName(unlocalizedName);
		setUnlocalizedName(this.getRegistryName().toString());

		setCreativeTab(LogisticalAutomation.CREATIVE_TAB);
	}

	public ItemBasic(String unlocalizedName, CreativeTabs tab) {
		setRegistryName(unlocalizedName);
		setUnlocalizedName(this.getRegistryName().toString());

		setCreativeTab(tab);
	}
}