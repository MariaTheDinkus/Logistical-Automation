package com.zundrel.logisticalautomation.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.zundrel.logisticalautomation.api.IWrenchable;

public class ItemWrench extends ItemBasic {
	public ItemWrench(String unlocalizedName) {
		super(unlocalizedName);

		setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.getBlockState(pos).getBlock() instanceof IWrenchable) {
			((IWrenchable) worldIn.getBlockState(pos).getBlock()).onWrenched(worldIn, pos, player, facing);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
}