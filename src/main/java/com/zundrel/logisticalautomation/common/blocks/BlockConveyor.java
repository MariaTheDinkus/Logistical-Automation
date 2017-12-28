package com.zundrel.logisticalautomation.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.zundrel.logisticalautomation.api.ConveyorTier;
import com.zundrel.logisticalautomation.api.ConveyorType;
import com.zundrel.logisticalautomation.api.IConveyor;
import com.zundrel.logisticalautomation.api.IWrenchable;
import com.zundrel.logisticalautomation.common.utilities.MovementUtilities;
import com.zundrel.logisticalautomation.common.utilities.RotationUtilities;

public class BlockConveyor extends BlockBasic implements IConveyor, IWrenchable {
	protected ConveyorTier tier;
	protected ConveyorType type;

	public static final PropertyBool BACK = PropertyBool.create("back");
	public static final PropertyBool LEFT = PropertyBool.create("left");
	public static final PropertyBool RIGHT = PropertyBool.create("right");

	public BlockConveyor(String unlocalizedName, Material material, ConveyorTier tier, ConveyorType type) {
		super(unlocalizedName, material);

		this.tier = tier;
		this.type = type;
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (type == ConveyorType.FLAT && !entityIn.onGround) {
			return;
		} else if (entityIn.isSneaking()) {
			return;
		}

		if (entityIn instanceof EntityItem) {
			entityIn.stepHeight = 0.5F;
		}

		if (type != ConveyorType.STAIRDOWN && type != ConveyorType.RAMPDOWN) {
			MovementUtilities.pushEntity(entityIn, pos, getSpeed(tier), state.getValue(FACING));
		} else if (type != ConveyorType.VERTICAL && type != ConveyorType.INVERSE) {
			if (!entityIn.onGround) {
				return;
			}
		}

		if (type == ConveyorType.VERTICAL) {
			if (tier == ConveyorTier.NORMAL && worldIn.getBlockState(pos.offset(state.getValue(FACING)).up()).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(state.getValue(FACING)).up()).getBlock()).getConveyorType() == ConveyorType.FLAT) {
				MovementUtilities.pushEntityUp(entityIn, pos, getSpeed(tier) + 0.13, state.getValue(FACING), true);
			} else {
				MovementUtilities.pushEntityUp(entityIn, pos, getSpeed(tier), state.getValue(FACING), true);
			}
		} else if (type == ConveyorType.INVERSE) {
			MovementUtilities.pushEntityUp(entityIn, pos, getSpeed(tier), state.getValue(FACING), true);
		} else if (type == ConveyorType.FLAT) {
			if (entityIn instanceof EntityItem || entityIn instanceof EntityXPOrb) {
				Block block = worldIn.getBlockState(pos.add(state.getValue(FACING).getDirectionVec())).getBlock();

				if (block instanceof BlockConveyor) {
					if (((IConveyor) block).getConveyorType() == ConveyorType.VERTICAL || ((IConveyor) block).getConveyorType() == ConveyorType.STAIRUP) {
						entityIn.setPosition(entityIn.posX, entityIn.posY + 0.3F, entityIn.posZ);
					}

				}
			}
		} else if (type == ConveyorType.STAIRUP) {
			entityIn.setPosition(entityIn.posX, entityIn.posY + 0.01F, entityIn.posZ);
		} else if (type == ConveyorType.STAIRDOWN) {
			MovementUtilities.pushEntity(entityIn, pos, getSpeed(tier), state.getValue(FACING).getOpposite());
		}
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
		return new BlockStateContainer(this, new IProperty[] { FACING, BACK, LEFT, RIGHT });
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		IBlockState newState = state;
		if (type == ConveyorType.FLAT) {
			if (worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getBlock() != Blocks.AIR) {
				newState = newState.withProperty(BACK, true);
			} else if (worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite()).down()).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite()).down()).getBlock()).getConveyorType() == ConveyorType.STAIRUP) {
				newState = newState.withProperty(BACK, true);
			} else {
				newState = newState.withProperty(BACK, false);
			}

			if (worldIn.getBlockState(pos.down().offset(state.getValue(FACING).getOpposite())).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite()).down()).getBlock()).getConveyorType() == ConveyorType.VERTICAL) {
				newState = newState.withProperty(BACK, true);
			}

			if (worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateYCCW())).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateYCCW())).getBlock()).getConveyorType() == ConveyorType.FLAT) {
				newState = newState.withProperty(LEFT, true);
			} else {
				newState = newState.withProperty(LEFT, false);
			}

			if (worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateY())).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateY())).getBlock()).getConveyorType() == ConveyorType.FLAT) {
				newState = newState.withProperty(RIGHT, true);
			} else {
				newState = newState.withProperty(RIGHT, false);
			}
		} else if (type == ConveyorType.INVERSE) {
			if (worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getBlock() != Blocks.AIR) {
				newState = newState.withProperty(BACK, true);
			} else {
				newState = newState.withProperty(BACK, false);
			}

			if (worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateYCCW())).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateYCCW())).getBlock()).getConveyorType() == ConveyorType.INVERSE) {
				newState = newState.withProperty(LEFT, true);
			} else {
				newState = newState.withProperty(LEFT, false);
			}

			if (worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateY())).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(state.getValue(FACING).rotateY())).getBlock()).getConveyorType() == ConveyorType.INVERSE) {
				newState = newState.withProperty(RIGHT, true);
			} else {
				newState = newState.withProperty(RIGHT, false);
			}
		} else if (type == ConveyorType.VERTICAL) {
			if (worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getBlock() instanceof IConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getBlock()).getConveyorType() == ConveyorType.FLAT && state.getValue(FACING) == worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getValue(FACING)) {
				newState = newState.withProperty(BACK, false);
			} else {
				newState = newState.withProperty(BACK, true);
			}

			if (worldIn.getBlockState(pos.offset(state.getValue(FACING)).up()).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(state.getValue(FACING)).up()).getBlock()).getConveyorType() == ConveyorType.FLAT) {
				newState = newState.withProperty(LEFT, true);
			} else {
				newState = newState.withProperty(LEFT, false);
			}

			newState = newState.withProperty(RIGHT, false);
		} else if (type == ConveyorType.STAIRUP || type == ConveyorType.STAIRDOWN) {
			if (worldIn.getBlockState(pos.offset(state.getValue(FACING)).up()).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(state.getValue(FACING)).up()).getBlock()).getConveyorType() == ConveyorType.FLAT) {
				newState = newState.withProperty(BACK, true);
			} else {
				newState = newState.withProperty(BACK, false);
			}

			newState = newState.withProperty(RIGHT, false);
		}

		return newState;
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
	public ConveyorTier getConveyorTier() {
		return tier;
	}

	@Override
	public ConveyorType getConveyorType() {
		return type;
	}

	@Override
	public float getSpeed(ConveyorTier tier) {
		switch (tier) {
			case NORMAL:
				return 0.125F;
			case FAST:
				return 0.250F;
			case EXPRESS:
				return 0.375F;
			default:
				return 0.125F;
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (type == ConveyorType.FLAT) {
			return new AxisAlignedBB(0, 0, 0, 1, (1F / 16F), 1);
		} else if (type == ConveyorType.INVERSE) {
			return new AxisAlignedBB(0, (15F / 16F), 0, 1, 1, 1);
		} else if (type == ConveyorType.VERTICAL) {
			if (source.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getBlock() instanceof IConveyor && ((IConveyor) source.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getBlock()).getConveyorType() == ConveyorType.FLAT && state.getValue(FACING) == source.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getValue(FACING)) {
				return FULL_BLOCK_AABB;
			} else {
				return RotationUtilities.getRotatedAABB(new AxisAlignedBB(0, 0, (15F / 16F), 1, 1, 1), state.getValue(FACING).getOpposite());
			}
		} else {
			return super.getBoundingBox(state, source, pos);
		}
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
		if (type == ConveyorType.FLAT) {
			addCollisionBox(new AxisAlignedBB(0, 0, 0, 1, (1F / 16F), 1), pos, collidingBoxes, entityBox);
		} else if (type == ConveyorType.INVERSE) {
			addCollisionBox(new AxisAlignedBB(0, (15F / 16F), 0, 1, 1, 1), pos, collidingBoxes, entityBox);
		} else if (type == ConveyorType.VERTICAL) {
			addCollisionBox(RotationUtilities.getRotatedAABB(new AxisAlignedBB(0, 0, (15F / 16F), 1, 1, 1), state.getValue(FACING).getOpposite()), pos, collidingBoxes, entityBox);
			if (worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getBlock() instanceof IConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getBlock()).getConveyorType() == ConveyorType.FLAT && state.getValue(FACING) == worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getValue(FACING)) {
				addCollisionBox(new AxisAlignedBB(0, 0, 0, 1, (1F / 16F), 1), pos, collidingBoxes, entityBox);
			}
		} else if (type == ConveyorType.STAIRUP || type == ConveyorType.STAIRDOWN) {
			addCollisionBox(RotationUtilities.getRotatedAABB(new AxisAlignedBB(0, 0, 0.01F, 1, (8F / 16F), 1), state.getValue(FACING)), pos, collidingBoxes, entityBox);

			addCollisionBox(RotationUtilities.getRotatedAABB(new AxisAlignedBB(0, (8F / 16F), (8F / 16F), 1, 0.99F, 1), state.getValue(FACING).getOpposite()), pos, collidingBoxes, entityBox);
		}
	}

	@Override
	public void onWrenched(World world, BlockPos pos, EntityPlayer player) {
		world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, world.getBlockState(pos).getValue(FACING).rotateY()));
	}
}