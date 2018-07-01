package com.zundrel.logisticalautomation.common.blocks.machines.conveyors;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.zundrel.logisticalautomation.api.conveyor.EnumConveyorTier;
import com.zundrel.logisticalautomation.common.utilities.MovementUtilities;
import com.zundrel.logisticalautomation.common.utilities.RotationUtilities;

public class BlockVerticalConveyor extends BlockConveyor {
	public BlockVerticalConveyor(String unlocalizedName, Material material, EnumConveyorTier tier) {
		super(unlocalizedName, material, tier);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		EnumFacing facing = state.getValue(FACING);

		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);

		if (entityIn == null || entityIn.isSneaking() || entityIn instanceof EntityPlayer && ((EntityPlayer) entityIn).capabilities.isFlying) {
			return;
		}

		double distX = Math.abs(pos.offset(facing).getX() + .5 - entityIn.posX);
		double distZ = Math.abs(pos.offset(facing).getZ() + .5 - entityIn.posZ);

		boolean contact = facing.getAxis() == Axis.Z ? distZ < 0.9 : distX < 0.9;

		if (entityIn instanceof EntityItem) {
			contact = true;
		}

		MovementUtilities.pushEntity(entityIn, pos, tier.getSpeed(), state.getValue(FACING));

		if (contact && (tier == EnumConveyorTier.NORMAL || tier == EnumConveyorTier.FAST) && worldIn.getBlockState(pos.offset(state.getValue(FACING)).up()).getBlock() instanceof BlockFlatConveyor) {
			MovementUtilities.pushEntityUp(entityIn, pos, tier.getSpeed() + 0.13, state.getValue(FACING), true);
		} else if (contact) {
			MovementUtilities.pushEntityUp(entityIn, pos, tier.getSpeed(), state.getValue(FACING), true);
		}

		insert(worldIn, pos, facing, entityIn);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		EnumFacing facing = state.getValue(FACING);

		boolean back = false;
		boolean left = false;
		boolean right = false;
		boolean addon = false;

		BlockPos backPos = pos.offset(facing.getOpposite());

		if (worldIn.getBlockState(backPos).getBlock() instanceof BlockFlatConveyor && facing == worldIn.getBlockState(backPos).getValue(FACING)) {
			back = true;
		}

		if (worldIn.getBlockState(pos.offset(facing).up()).getBlock() instanceof BlockFlatConveyor) {
			addon = true;
		}

		return state.withProperty(BACK, back).withProperty(LEFT, left).withProperty(RIGHT, right).withProperty(ADDON, addon);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (source.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getBlock() instanceof BlockFlatConveyor && state.getValue(FACING) == source.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getValue(FACING)) {
			return FULL_BLOCK_AABB;
		} else {
			return RotationUtilities.getRotatedAABB(new AxisAlignedBB(0, 0, (15F / 16F), 1, 1, 1), state.getValue(FACING).getOpposite());
		}
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
		addCollisionBox(RotationUtilities.getRotatedAABB(new AxisAlignedBB(0, 0, (15F / 16F), 1, 1, 1), state.getValue(FACING).getOpposite()), pos, collidingBoxes, entityBox);
		if (worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getBlock() instanceof BlockFlatConveyor && state.getValue(FACING) == worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getValue(FACING)) {
			addCollisionBox(new AxisAlignedBB(0, 0, 0, 1, (1F / 16F), 1), pos, collidingBoxes, entityBox);
		}
	}
}