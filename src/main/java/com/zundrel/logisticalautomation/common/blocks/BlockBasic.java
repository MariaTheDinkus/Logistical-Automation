package com.zundrel.logisticalautomation.common.blocks;

import java.util.List;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.client.LogisticCreativeTabs.LogisticGeneralTab;

public class BlockBasic extends BlockHorizontal {
	public BlockBasic(String unlocalizedName, Material material) {
		super(material);

		setRegistryName(unlocalizedName);
		setUnlocalizedName(this.getRegistryName().toString());

		setCreativeTab(LogisticGeneralTab.INSTANCE);
	}

	public BlockBasic(String unlocalizedName, Material material, CreativeTabs tab) {
		super(material);

		setRegistryName(unlocalizedName);
		setUnlocalizedName(this.getRegistryName().toString());

		setCreativeTab(tab);
	}

	public void registerModel(ItemBlock ib) {
		LogisticalAutomation.proxy.registerItemModel(ib, 0, this.getUnlocalizedName().substring(5));
	}

	public void addCollisionBox(AxisAlignedBB box, BlockPos pos, List collidingBoxes, AxisAlignedBB entityBox) {
		if (box != null && entityBox.intersects(box.offset(pos))) {
			boolean add = true;
			collidingBoxes.add(box.offset(pos));
		}
	}
}