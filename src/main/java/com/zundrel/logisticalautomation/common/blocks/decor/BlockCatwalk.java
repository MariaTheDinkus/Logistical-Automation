package com.zundrel.logisticalautomation.common.blocks.decor;

import java.util.List;

import javax.annotation.Nonnull;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.api.IWrenchable;
import com.zundrel.logisticalautomation.api.QuadBoolean;
import com.zundrel.logisticalautomation.api.QuadBooleanDirs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.zundrel.logisticalautomation.common.blocks.BlockBasic;

public class BlockCatwalk extends BlockBasic implements IWrenchable {
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");

	public BlockCatwalk(String unlocalizedName, Material material) {
		super(unlocalizedName, material, LogisticalAutomation.CREATIVE_TAB);

		setDefaultState(this.getDefaultState().withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { NORTH, EAST, SOUTH, WEST });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		boolean one = state.getValue(NORTH);
		boolean two = state.getValue(SOUTH);
		boolean three = state.getValue(WEST);
		boolean four = state.getValue(EAST);

		return QuadBooleanDirs.findQuadMeta(one, two, three, four);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		QuadBoolean quadBoolean = QuadBooleanDirs.get(meta);

		IBlockState state = this.getDefaultState().withProperty(NORTH, quadBoolean.getOne()).withProperty(SOUTH, quadBoolean.getTwo()).withProperty(WEST, quadBoolean.getThree()).withProperty(EAST, quadBoolean.getFour());
		return state;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState();
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

//	@Override
//	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos position) {
//		boolean north;
//		boolean south;
//		boolean west;
//		boolean east;
//
//		for (EnumFacing facing : EnumFacing.HORIZONTALS) {
//			IBlockState offset = world.getBlockState(position.offset(facing));
//			IBlockState offset_down = world.getBlockState(position.offset(facing).down());
//
//			boolean value;
//
//			if (offset.getBlock() instanceof BlockCatwalk) {
//				value = isAdjacentBlockOfMyType(world, position, facing);
//			} else if (offset.getBlock() instanceof BlockCatwalkStairs && offset.getValue(BlockHorizontal.FACING) == facing) {
//				value = true;
//			} else if (offset_down.getBlock() instanceof BlockCatwalkStairs && offset_down.getValue(BlockHorizontal.FACING) == facing) {
//				value = true;
//			}
//
//			switch(facing) {
//				case NORTH:
//					north = value;
//				case SOUTH:
//					south = value;
//				case WEST:
//					west = value;
//				case EAST:
//					east = value;
//			}
//		}
//
//		IBlockState finalState = state.withProperty(NORTH, north).withProperty(SOUTH, catwalk.getSouth()).withProperty(WEST, catwalk.getWest()).withProperty(EAST, catwalk.getEast());
//
//		return finalState;
//	}


	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {

	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		IBlockState neighborState = world.getBlockState(fromPos);

		BlockPos dirVec = pos.subtract(fromPos);

		PropertyBool bool = null;
		PropertyBool boolOpposite = null;
		EnumFacing facing = EnumFacing.getFacingFromVector(dirVec.getX(), dirVec.getY(), dirVec.getZ());

		if (facing == EnumFacing.NORTH) {
			bool = NORTH;
			boolOpposite = SOUTH;
		} else if (facing == EnumFacing.SOUTH) {
			bool = SOUTH;
			boolOpposite = NORTH;
		} else if (facing == EnumFacing.WEST) {
			bool = WEST;
			boolOpposite = EAST;
		} else if (facing == EnumFacing.EAST) {
			bool = EAST;
			boolOpposite = WEST;
		}

		if (neighborState.getBlock() == this) {
			if (boolOpposite != null) {
				world.setBlockState(pos, state.withProperty(boolOpposite, true));
			}

			if (bool != null) {
				world.setBlockState(fromPos, neighborState.withProperty(bool, true));
			}
		}
	}

	@Override
	public void onWrenched(World world, BlockPos pos, EntityPlayer player, EnumFacing facing) {
		IBlockState state = world.getBlockState(pos);
		PropertyBool bool = null;
		PropertyBool boolplayer = null;

		System.out.println(facing);

		if (facing == EnumFacing.NORTH) {
			bool = NORTH;
		} else if (facing == EnumFacing.SOUTH) {
			bool = SOUTH;
		} else if (facing == EnumFacing.WEST) {
			bool = WEST;
		} else if (facing == EnumFacing.EAST) {
			bool = EAST;
		}

		if (player.getHorizontalFacing() == EnumFacing.NORTH) {
			boolplayer = NORTH;
		} else if (player.getHorizontalFacing() == EnumFacing.SOUTH) {
			boolplayer = SOUTH;
		} else if (player.getHorizontalFacing() == EnumFacing.WEST) {
			boolplayer = WEST;
		} else if (player.getHorizontalFacing() == EnumFacing.EAST) {
			boolplayer = EAST;
		}

		if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
			world.setBlockState(pos, state.withProperty(bool, !state.getValue(bool)));
		} else {
			world.setBlockState(pos, state.withProperty(boolplayer, !state.getValue(boolplayer)));
		}
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

		if (!state.getValue(NORTH)) {
			addCollisionBox(side1, pos, collidingBoxes, entityBox);
		}

		if (!state.getValue(SOUTH)) {
			addCollisionBox(side3, pos, collidingBoxes, entityBox);
		}

		if (!state.getValue(WEST)) {
			addCollisionBox(side4, pos, collidingBoxes, entityBox);
		}

		if (!state.getValue(EAST)) {
			addCollisionBox(side2, pos, collidingBoxes, entityBox);
		}
	}
}
