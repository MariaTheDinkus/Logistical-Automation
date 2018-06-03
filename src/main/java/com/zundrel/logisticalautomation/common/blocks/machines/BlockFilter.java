package com.zundrel.logisticalautomation.common.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.api.IShowHopper;
import com.zundrel.logisticalautomation.api.IWrenchable;
import com.zundrel.logisticalautomation.common.blocks.BlockFacing;
import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityFilter;
import com.zundrel.logisticalautomation.common.registry.LogisticCreativeTabs.LogisticConveyorTab;
import com.zundrel.logisticalautomation.common.utilities.RotationUtilities;

public class BlockFilter extends BlockFacing implements IWrenchable, IShowHopper {
	public BlockFilter(String unlocalizedName, Material material) {
		super(unlocalizedName, material, LogisticConveyorTab.INSTANCE);

		setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityFilter();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return RotationUtilities.getRotatedAABB(new AxisAlignedBB(0, 0, 0.01F, 1, 1, 1), blockState.getValue(FACING).getOpposite());
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.openGui(LogisticalAutomation.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}

		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (!worldIn.isRemote) {
			if (entityIn instanceof EntityItem && !entityIn.isDead) {
				EntityItem entityItem = (EntityItem) entityIn;
				if (worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileEntityFilter) {
					TileEntityFilter tileSorter = (TileEntityFilter) worldIn.getTileEntity(pos);

					tileSorter.sortItemStack(entityItem.getItem());

					worldIn.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.1F, 1);

					entityIn.setDead();
				}
			}
		}
	}
}