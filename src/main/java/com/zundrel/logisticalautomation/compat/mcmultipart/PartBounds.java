package com.zundrel.logisticalautomation.compat.mcmultipart;

import com.zundrel.logisticalautomation.common.blocks.BlockBasic;
import mcmultipart.api.container.IPartInfo;
import mcmultipart.api.slot.EnumCenterSlot;
import mcmultipart.api.slot.EnumFaceSlot;
import mcmultipart.api.slot.IPartSlot;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

/**
 * @author shadowfacts
 */
public class PartBounds implements IMultipartBase {

	private BlockBasic block;

	public PartBounds(BlockBasic block) {
		this.block = block;
	}

	@Override
	public Block getBlock() {
		return block;
	}

	@Override
	public IPartSlot getSlotForPlacement(World world, BlockPos pos, IBlockState state, EnumFacing facing, float hitX, float hitY, float hitZ, EntityLivingBase placer) {
		return EnumFaceSlot.DOWN;
	}

	@Override
	public IPartSlot getSlotFromWorld(IBlockAccess world, BlockPos pos, IBlockState state) {
		return EnumFaceSlot.DOWN;
	}

}