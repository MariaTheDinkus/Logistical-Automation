package com.zundrel.logisticalautomation.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.zundrel.logisticalautomation.api.IWrenchable;

public class BlockFacing extends BlockBasic implements IWrenchable {
	public BlockFacing(String unlocalizedName, Material material) {
		super(unlocalizedName, material);

		setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH));
	}

	public BlockFacing(String unlocalizedName, Material material, CreativeTabs tab) {
		super(unlocalizedName, material, tab);

		setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		if (!placer.isSneaking()) {
			return getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
		} else {
			return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
		}
	}

	@Override
	public void onWrenched(World world, BlockPos pos, EntityPlayer player) {
		world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, world.getBlockState(pos).getValue(FACING).rotateY()));
	}
}