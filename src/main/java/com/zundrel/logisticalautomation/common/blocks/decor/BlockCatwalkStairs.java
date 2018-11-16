package com.zundrel.logisticalautomation.common.blocks.decor;

import java.util.List;

import javax.annotation.Nonnull;

import com.zundrel.logisticalautomation.LogisticalAutomation;
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

import com.zundrel.logisticalautomation.common.blocks.BlockFacing;
import com.zundrel.logisticalautomation.common.utilities.RotationUtilities;

public class BlockCatwalkStairs extends BlockFacing {
	public static final PropertyBool LEFT = PropertyBool.create("left");
	public static final PropertyBool RIGHT = PropertyBool.create("right");

	public BlockCatwalkStairs(String unlocalizedName, Material material) {
		super(unlocalizedName, material, LogisticalAutomation.CREATIVE_TAB);

		setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEFT, false).withProperty(RIGHT, false));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, LEFT, RIGHT });
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

		newState = state.withProperty(RIGHT, this.isAdjacentBlockOfMyType(world, position, state.getValue(FACING).rotateY())).withProperty(LEFT, this.isAdjacentBlockOfMyType(world, position, state.getValue(FACING).rotateYCCW()));

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
		AxisAlignedBB step1 = new AxisAlignedBB(0, (2F / 16F), (12F / 16F), 1, (3F / 16F), 1);
		AxisAlignedBB step2 = new AxisAlignedBB(0, (6F / 16F), (8F / 16F), 1, (7F / 16F), (12F / 16F));
		AxisAlignedBB step3 = new AxisAlignedBB(0, (10F / 16F), (4F / 16F), 1, (11F / 16F), (8F / 16F));
		AxisAlignedBB step4 = new AxisAlignedBB(0, (14F / 16F), 0, 1, (15F / 16F), (4F / 16F));

		addCollisionBox(RotationUtilities.getRotatedAABB(step1, state.getValue(FACING)), pos, collidingBoxes, entityBox);
		addCollisionBox(RotationUtilities.getRotatedAABB(step2, state.getValue(FACING)), pos, collidingBoxes, entityBox);
		addCollisionBox(RotationUtilities.getRotatedAABB(step3, state.getValue(FACING)), pos, collidingBoxes, entityBox);
		addCollisionBox(RotationUtilities.getRotatedAABB(step4, state.getValue(FACING)), pos, collidingBoxes, entityBox);
	}
}
