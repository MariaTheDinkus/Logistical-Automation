package com.zundrel.logisticalautomation.common.blocks.machines.conveyors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

import com.zundrel.logisticalautomation.api.conveyor.EnumConveyorTier;
import com.zundrel.logisticalautomation.api.conveyor.IShowHopper;
import com.zundrel.logisticalautomation.common.utilities.MovementUtilities;

public class BlockFlatConveyor extends BlockConveyor {
	public BlockFlatConveyor(String unlocalizedName, Material material, EnumConveyorTier tier) {
		super(unlocalizedName, material, tier);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		EnumFacing facing = state.getValue(FACING);

		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);

		if (!entityIn.onGround || entityIn == null || entityIn.isSneaking() || entityIn instanceof EntityPlayer && ((EntityPlayer) entityIn).capabilities.isFlying) {
			return;
		}

		MovementUtilities.pushEntity(entityIn, pos, tier.getSpeed(), state.getValue(FACING));
		if (entityIn instanceof EntityItem || entityIn instanceof EntityXPOrb) {
			Block block = worldIn.getBlockState(pos.add(state.getValue(FACING).getDirectionVec())).getBlock();

			if (block instanceof BlockConveyor) {
				if (block instanceof BlockVerticalConveyor || block instanceof BlockStairConveyor) {
					entityIn.setPosition(entityIn.posX, entityIn.posY + 0.3F, entityIn.posZ);
				}
			}
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
		BlockPos leftPos = pos.offset(facing.rotateYCCW());
		BlockPos rightPos = pos.offset(facing.rotateY());

		if (!worldIn.isAirBlock(backPos)) {
			back = true;
		} else if (worldIn.getBlockState(backPos.down()).getBlock() instanceof BlockStairConveyor) {
			back = true;
		}

		if (worldIn.getBlockState(backPos.down()).getBlock() instanceof BlockVerticalConveyor) {
			back = true;
		}

		if (worldIn.getBlockState(leftPos).getBlock() instanceof BlockFlatConveyor) {
			left = true;
		}

		if (worldIn.getBlockState(rightPos).getBlock() instanceof BlockFlatConveyor) {
			right = true;
		}

		BlockPos invPos = pos.offset(facing);
		IBlockState invState = worldIn.getBlockState(invPos);
		Block invBlock = invState.getBlock();
		TileEntity inventoryTile = worldIn.getTileEntity(invPos);

		if (inventoryTile != null && inventoryTile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite())) {
			addon = true;
		} else if (invBlock instanceof IShowHopper && ((IShowHopper) invBlock).showConveyorHopper() && ((IShowHopper) invBlock).compareFacing() && invState.getValue(BlockHorizontal.FACING) == facing || invBlock instanceof IShowHopper && ((IShowHopper) invBlock).showConveyorHopper() && !((IShowHopper) invBlock).compareFacing()) {
			addon = true;
		}

		return state.withProperty(BACK, back).withProperty(LEFT, left).withProperty(RIGHT, right).withProperty(ADDON, addon);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0, 0, 0, 1, (1F / 16F), 1);
	}
}