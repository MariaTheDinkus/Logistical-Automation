package com.zundrel.logisticalautomation.common.blocks.machines.conveyors;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.zundrel.logisticalautomation.api.EnumConveyorTier;
import com.zundrel.logisticalautomation.common.utilities.MovementUtilities;
import com.zundrel.logisticalautomation.common.utilities.RotationUtilities;

public class BlockStairConveyor extends BlockConveyor {
	public static final PropertyBool UP = PropertyBool.create("up");

	public BlockStairConveyor(String unlocalizedName, Material material, EnumConveyorTier tier) {
		super(unlocalizedName, material, tier);

		this.setDefaultState(getDefaultState().withProperty(UP, true));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, BACK, LEFT, RIGHT, ADDON, UP);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		boolean up = false;
		if (String.valueOf(meta).length() == 2) {
			up = true;
		} else {
			up = false;
		}

		int metaNew = 0;
		if (String.valueOf(meta).length() == 1) {
			metaNew = Integer.parseInt(String.valueOf(("" + meta).charAt(0)));
		} else {
			metaNew = Integer.parseInt(String.valueOf(("" + meta).charAt(1)));
		}

		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(metaNew)).withProperty(UP, up);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int up = 0;
		if (state.getValue(UP) == false) {
			up = 0;
		} else {
			up = 1;
		}

		return Integer.parseInt(up + "" + state.getValue(FACING).getIndex());
	}

	@Override
	public void onWrenched(World world, BlockPos pos, EntityPlayer player) {
		if (!player.isSneaking()) {
			world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, world.getBlockState(pos).getValue(FACING).rotateY()));
		} else {
			world.setBlockState(pos, world.getBlockState(pos).withProperty(UP, !world.getBlockState(pos).getValue(UP)));
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		EnumFacing facing = state.getValue(FACING);

		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);

		if (entityIn == null || entityIn.isSneaking() || entityIn instanceof EntityPlayer && ((EntityPlayer) entityIn).capabilities.isFlying) {
			return;
		}

		if (state.getValue(UP)) {
			MovementUtilities.pushEntity(entityIn, pos, tier.getSpeed(), state.getValue(FACING));
		} else if (!state.getValue(UP)) {
			MovementUtilities.pushEntity(entityIn, pos, tier.getSpeed(), state.getValue(FACING).getOpposite());
		}

		if (entityIn instanceof EntityItem && state.getValue(UP)) {
			entityIn.setPosition(entityIn.posX, entityIn.posY + 0.01F, entityIn.posZ);
		}
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		EnumFacing facing = state.getValue(FACING);

		boolean back = false;
		boolean left = false;
		boolean right = false;
		boolean addon = false;

		BlockPos backPos = pos.offset(facing.getOpposite());

		if (worldIn.getBlockState(backPos.up()).getBlock() instanceof BlockFlatConveyor) {
			back = true;
		}

		return state.withProperty(BACK, back).withProperty(LEFT, left).withProperty(RIGHT, right).withProperty(ADDON, addon);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FULL_BLOCK_AABB;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
		addCollisionBox(RotationUtilities.getRotatedAABB(new AxisAlignedBB(0, 0, 0.01F, 1, (8F / 16F), 1), state.getValue(FACING)), pos, collidingBoxes, entityBox);

		addCollisionBox(RotationUtilities.getRotatedAABB(new AxisAlignedBB(0, (8F / 16F), (8F / 16F), 1, 0.99F, 1), state.getValue(FACING).getOpposite()), pos, collidingBoxes, entityBox);
	}
}