package com.zundrel.logisticalautomation.common.blocks;

import java.util.List;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.chunk.Chunk;

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

	public void addCollisionBox(AxisAlignedBB box, BlockPos pos, List collidingBoxes, AxisAlignedBB entityBox) {
		if (box != null && entityBox.intersects(box.offset(pos))) {
			collidingBoxes.add(box.offset(pos));
		}
	}

	public TileEntity getTileEntitySafely(IBlockAccess blockAccess, BlockPos pos) {
		if (blockAccess instanceof ChunkCache) {
			return ((ChunkCache) blockAccess).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
		} else {
			return blockAccess.getTileEntity(pos);
		}
	}
}