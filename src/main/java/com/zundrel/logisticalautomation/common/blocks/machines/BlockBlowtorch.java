package com.zundrel.logisticalautomation.common.blocks.machines;

import com.zundrel.logisticalautomation.api.IWrenchable;
import com.zundrel.logisticalautomation.common.blocks.BlockFacing;
import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityBlowtorch;
import com.zundrel.logisticalautomation.common.registry.LogisticCreativeTabs.LogisticConveyorTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockBlowtorch extends BlockFacing implements IWrenchable {
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockBlowtorch(String unlocalizedName, Material material) {
		super(unlocalizedName, material, LogisticConveyorTab.INSTANCE);

		setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, POWERED);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		int metaLength = String.valueOf(meta).length();

		boolean powered = metaLength == 2;

		int metaNew = metaLength == 1 ? Integer.parseInt(String.valueOf(("" + meta).charAt(0))) : Integer.parseInt(String.valueOf(("" + meta).charAt(1)));

		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(metaNew)).withProperty(POWERED, powered);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int powered = state.getValue(POWERED) ? 1 : 0;

		return Integer.parseInt(powered + "" + state.getValue(FACING).getIndex());
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.neighborChanged(state, worldIn, pos, worldIn.getBlockState(pos).getBlock(), null);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (state.getValue(POWERED) && !worldIn.isBlockPowered(pos)) {
			worldIn.scheduleUpdate(pos, this, 4);
		} else if (!state.getValue(POWERED) && worldIn.isBlockPowered(pos)) {
			worldIn.setBlockState(pos, state.withProperty(POWERED, true), 2);
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (state.getValue(POWERED) && !worldIn.isBlockPowered(pos)) {
			worldIn.setBlockState(pos, state.withProperty(POWERED, false), 2);
		}
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityBlowtorch();
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
}