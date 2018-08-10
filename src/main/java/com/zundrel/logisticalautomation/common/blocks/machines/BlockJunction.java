package com.zundrel.logisticalautomation.common.blocks.machines;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.zundrel.logisticalautomation.common.utilities.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.zundrel.logisticalautomation.api.conveyor.IShowHopper;
import com.zundrel.logisticalautomation.api.IWrenchable;
import com.zundrel.logisticalautomation.common.blocks.BlockFacing;
import com.zundrel.logisticalautomation.common.registry.LogisticCreativeTabs.LogisticConveyorTab;
import com.zundrel.logisticalautomation.common.utilities.RotationUtilities;

public class BlockJunction extends BlockFacing implements IWrenchable, IShowHopper {
	private static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockJunction(String unlocalizedName, Material material) {
		super(unlocalizedName, material, LogisticConveyorTab.INSTANCE);

		setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
	}

	@Override
	@Nonnull
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, POWERED);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		int metaLength = String.valueOf(meta).length();

		boolean powered = metaLength == 2;

		int metaNew = metaLength == 1 ? Integer.parseInt(String.valueOf(("" + meta).charAt(0))) : Integer.parseInt(String.valueOf(("" + meta).charAt(1)));

		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(metaNew)).withProperty(POWERED, powered);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int powered = state.getValue(POWERED) ? 1 : 0;

		return Integer.parseInt(powered + "" + state.getValue(FACING).getIndex());
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return RotationUtilities.getRotatedAABB(new AxisAlignedBB(0, 0, 0.01F, 1, 1, 1), blockState.getValue(FACING).getOpposite());
	}

	public void sortItemStack(ItemStack stack, World world, BlockPos pos, EnumFacing facing, boolean right) {
		EnumFacing facingSorted = right ? facing.rotateY() : facing.rotateYCCW();
		TileEntity tile = world.getTileEntity(pos.offset(facingSorted));

		if (InventoryUtils.canInsertStackIntoInventory(tile, stack, facing.getOpposite())) {
			InventoryUtils.insertStackIntoInventory(tile, stack, facing.getOpposite());
		} else {
			Vec3d posSpawn = new Vec3d(pos.offset(facingSorted).getX() + 0.5D - facingSorted.getFrontOffsetX() * .35, pos.offset(facingSorted).getY() + 0.4D, pos.offset(facingSorted).getZ() + 0.5D - facingSorted.getFrontOffsetZ() * .35);
			Vec3d velocity = new Vec3d(0.03D * facingSorted.getFrontOffsetX(), 0.1D, 0.03D * facingSorted.getFrontOffsetZ());

			EntityItem entityItem = new EntityItem(world, posSpawn.x, (posSpawn.y - 0.5F) + (2.65F / 16F), posSpawn.z, stack);
			entityItem.isAirBorne = true;
			entityItem.motionX = velocity.x;
			entityItem.motionY = velocity.y;
			entityItem.motionZ = velocity.z;

			world.spawnEntity(entityItem);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (!worldIn.isRemote) {
			if (entityIn instanceof EntityItem && !entityIn.isDead) {
				sortItemStack(((EntityItem) entityIn).getItem(), worldIn, pos, state.getValue(FACING), state.getValue(POWERED));

				worldIn.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.1F, 1);

				entityIn.setDead();
			}
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.neighborChanged(state, worldIn, pos, worldIn.getBlockState(pos).getBlock(), null);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (state.getValue(POWERED) && !worldIn.isBlockPowered(pos)) {
			worldIn.scheduleUpdate(pos, this, 4);
		} else if (!state.getValue(POWERED) && worldIn.isBlockPowered(pos)) {
			worldIn.setBlockState(pos, state.withProperty(POWERED, true), 2);
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (state.getValue(POWERED) && !worldIn.isBlockPowered(pos)) {
			worldIn.setBlockState(pos, state.withProperty(POWERED, false), 2);
		}
	}

	@Override
	public boolean compareFacing() {
		return true;
	}
}