package com.zundrel.logisticalautomation.common.blocks;

import java.util.ArrayList;
import java.util.List;

import com.zundrel.logisticalautomation.api.conveyor.EnumConveyorTier;
import javafx.scene.chart.Axis;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.chunk.Chunk;

import com.zundrel.logisticalautomation.common.registry.LogisticCreativeTabs.LogisticConveyorTab;

public class BlockBasic extends BlockHorizontal {
	public List<AxisAlignedBB> collisions = new ArrayList<AxisAlignedBB>();

	public BlockBasic(String unlocalizedName, Material material) {
		super(material);

		setRegistryName(unlocalizedName);
		setUnlocalizedName(this.getRegistryName().toString());

		setCreativeTab(LogisticConveyorTab.INSTANCE);

		if (material == Material.ROCK) {
			setSoundType(SoundType.STONE);
			setHardness(1.5F);
		} else if (material == Material.WOOD) {
			setSoundType(SoundType.WOOD);
			setHardness(2.0F);
		}
	}

	public BlockBasic(String unlocalizedName, Material material, CreativeTabs tab) {
		super(material);

		setRegistryName(unlocalizedName);
		setUnlocalizedName(this.getRegistryName().toString());

		setCreativeTab(tab);

		if (material == Material.ROCK) {
			setSoundType(SoundType.STONE);
			setHardness(1.5F);
		} else if (material == Material.WOOD) {
			setSoundType(SoundType.WOOD);
			setHardness(2.0F);
		}
	}

	public void addCollisionBox(AxisAlignedBB box, BlockPos pos, List collidingBoxes, AxisAlignedBB entityBox) {
		if (box != null && entityBox.intersects(box.offset(pos))) {
			collidingBoxes.add(box.offset(pos));
			collisions.add(box.offset(pos));
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