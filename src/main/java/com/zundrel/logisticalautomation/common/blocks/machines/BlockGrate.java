package com.zundrel.logisticalautomation.common.blocks.machines;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.zundrel.logisticalautomation.common.blocks.BlockBasic;

public class BlockGrate extends BlockBasic {
	private static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockGrate(String unlocalizedName, Material material) {
		super(unlocalizedName, material, LogisticalAutomation.CREATIVE_TAB);

		setDefaultState(this.getDefaultState().withProperty(POWERED, false));
	}

	@Override
	@Nonnull
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
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
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, POWERED);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(POWERED, meta == 1);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int powered = state.getValue(POWERED) ? 1 : 0;

		return powered;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0, (14F / 16F), 0, 1, 1, 1);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB entityBox, @Nonnull List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		if (!(entityIn instanceof EntityLivingBase)) {
			if (!state.getValue(POWERED)) {
				addCollisionBox(new AxisAlignedBB(0, (14F / 16F), 0, 1, 1, 1), pos, collidingBoxes, entityBox);
			}
		} else {
			addCollisionBox(new AxisAlignedBB(0, (14F / 16F), 0, 1, 1, 1), pos, collidingBoxes, entityBox);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!playerIn.isSneaking()) {
			worldIn.setBlockState(pos, state.withProperty(POWERED, !state.getValue(POWERED)));
			if (state.getValue(POWERED)) {
				worldIn.playSound(null, pos, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1, 1);
			} else {
				worldIn.playSound(null, pos, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1, 1);
			}
			return true;
		}
		return false;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote) {
			boolean flag = worldIn.isBlockPowered(pos);

			if (flag || blockIn.getDefaultState().canProvidePower()) {
				boolean flag1 = state.getValue(POWERED).booleanValue();

				if (flag1 != flag) {
					worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(flag)), 2);
					if (flag) {
						worldIn.playSound(null, pos, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1, 1);
					} else {
						worldIn.playSound(null, pos, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1, 1);
					}
				}
			}
		}
	}
}
