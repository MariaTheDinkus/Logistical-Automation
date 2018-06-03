package com.zundrel.logisticalautomation.common.blocks.machines;

import com.zundrel.logisticalautomation.api.IWrenchable;
import com.zundrel.logisticalautomation.common.blocks.BlockFacing;
import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityBlowtorch;
import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityPolyBlowtorch;
import com.zundrel.logisticalautomation.common.registry.LogisticCreativeTabs.LogisticConveyorTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockPolyBlowtorch extends BlockFacing implements IWrenchable {
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockPolyBlowtorch(String unlocalizedName, Material material) {
		super(unlocalizedName, material, LogisticConveyorTab.INSTANCE);

		setDefaultState(this.getDefaultState().withProperty(POWERED, false));
	}

	@Override
	@Nonnull
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, POWERED);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		boolean powered = meta == 1 ? true : false;

		return this.getDefaultState().withProperty(POWERED, powered);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int powered = state.getValue(POWERED) ? 1 : 0;

		return powered;
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
		return new TileEntityPolyBlowtorch();
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
}