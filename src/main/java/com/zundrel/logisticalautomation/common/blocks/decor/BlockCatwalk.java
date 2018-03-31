package com.zundrel.logisticalautomation.common.blocks.decor;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.zundrel.logisticalautomation.common.blocks.BlockBasic;
import com.zundrel.logisticalautomation.common.registry.LogisticCreativeTabs;

public class BlockCatwalk extends BlockBasic {
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");

	public BlockCatwalk(String unlocalizedName, Material material) {
		super(unlocalizedName, material, LogisticCreativeTabs.LogisticConveyorTab.INSTANCE);

		setDefaultState(this.getDefaultState().withProperty(NORTH, true).withProperty(EAST, true).withProperty(SOUTH, true).withProperty(WEST, true));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { NORTH, EAST, SOUTH, WEST });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();
		return state;
	}

	public boolean isAdjacentBlockOfMyType(IBlockAccess world, BlockPos position, EnumFacing facing) {

		assert null != world : "world cannot be null";
		assert null != position : "position cannot be null";
		assert null != this : "type cannot be null";

		BlockPos newPosition = position.offset(facing);
		IBlockState blockState = world.getBlockState(newPosition);
		Block block = (null == blockState) ? null : blockState.getBlock();

		return this == block;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos position) {
		IBlockState newState = state;

		newState = state.withProperty(EAST, this.isAdjacentBlockOfMyType(world, position, EnumFacing.EAST)).withProperty(NORTH, this.isAdjacentBlockOfMyType(world, position, EnumFacing.NORTH)).withProperty(SOUTH, this.isAdjacentBlockOfMyType(world, position, EnumFacing.SOUTH)).withProperty(WEST, this.isAdjacentBlockOfMyType(world, position, EnumFacing.WEST));

		if (world.getBlockState(position.offset(EnumFacing.NORTH).down()).getBlock() instanceof BlockCatwalkStairs) {
			newState = newState.withProperty(NORTH, true);
		}

		if (world.getBlockState(position.offset(EnumFacing.EAST).down()).getBlock() instanceof BlockCatwalkStairs) {
			newState = newState.withProperty(EAST, true);
		}

		if (world.getBlockState(position.offset(EnumFacing.SOUTH).down()).getBlock() instanceof BlockCatwalkStairs) {
			newState = newState.withProperty(SOUTH, true);
		}

		if (world.getBlockState(position.offset(EnumFacing.WEST).down()).getBlock() instanceof BlockCatwalkStairs) {
			newState = newState.withProperty(WEST, true);
		}

		return newState;
	}

	@Override
	@Nonnull
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0, 0, 0, 1, 1, 1);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		AxisAlignedBB bottom = new AxisAlignedBB(0, 0, 0, 1, (1F / 16F), 1);
		AxisAlignedBB side1 = new AxisAlignedBB(0, 0, 0, 1, 1, (1F / 16F));
		AxisAlignedBB side2 = new AxisAlignedBB((15F / 16F), 0, 0, 1, 1, 1);
		AxisAlignedBB side3 = new AxisAlignedBB(0, 0, (15F / 16F), 1, 1, 1);
		AxisAlignedBB side4 = new AxisAlignedBB(0, 0, 0, (1F / 16F), 1, 1);

		addCollisionBox(bottom, pos, collidingBoxes, entityBox);

		if (!this.isAdjacentBlockOfMyType(worldIn, pos, EnumFacing.NORTH) && !(worldIn.getBlockState(pos.offset(EnumFacing.NORTH).down()).getBlock() instanceof BlockCatwalkStairs)) {
			addCollisionBox(side1, pos, collidingBoxes, entityBox);
		}

		if (!this.isAdjacentBlockOfMyType(worldIn, pos, EnumFacing.EAST) && !(worldIn.getBlockState(pos.offset(EnumFacing.EAST).down()).getBlock() instanceof BlockCatwalkStairs)) {
			addCollisionBox(side2, pos, collidingBoxes, entityBox);
		}

		if (!this.isAdjacentBlockOfMyType(worldIn, pos, EnumFacing.SOUTH) && !(worldIn.getBlockState(pos.offset(EnumFacing.SOUTH).down()).getBlock() instanceof BlockCatwalkStairs)) {
			addCollisionBox(side3, pos, collidingBoxes, entityBox);
		}

		if (!this.isAdjacentBlockOfMyType(worldIn, pos, EnumFacing.WEST) && !(worldIn.getBlockState(pos.offset(EnumFacing.WEST).down()).getBlock() instanceof BlockCatwalkStairs)) {
			addCollisionBox(side4, pos, collidingBoxes, entityBox);
		}
	}
}
