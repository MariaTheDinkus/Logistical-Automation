package com.zundrel.logisticalautomation.common.blocks.machines;

import com.zundrel.logisticalautomation.api.conveyor.IShowHopper;
import com.zundrel.logisticalautomation.api.IWrenchable;
import com.zundrel.logisticalautomation.common.blocks.BlockFacing;
import com.zundrel.logisticalautomation.common.registry.LogisticCreativeTabs.LogisticConveyorTab;
import com.zundrel.logisticalautomation.common.utilities.RotationUtilities;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSplitter extends BlockFacing implements IWrenchable, IShowHopper {
	public BlockSplitter(String unlocalizedName, Material material) {
		super(unlocalizedName, material, LogisticConveyorTab.INSTANCE);

		setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return RotationUtilities.getRotatedAABB(new AxisAlignedBB(0, 0, 0.01F, 1, 1, 1), blockState.getValue(FACING).getOpposite());
	}

	public void sortItemStack(ItemStack stack, World world, BlockPos pos, EnumFacing facing, boolean right) {
		EnumFacing facingSorted = right ? facing.rotateY() : facing.rotateYCCW();

		Vec3d posSpawn = new Vec3d(pos.offset(facingSorted).getX() + 0.5D - facingSorted.getFrontOffsetX() * .35, pos.offset(facingSorted).getY() + 0.4D, pos.offset(facingSorted).getZ() + 0.5D - facingSorted.getFrontOffsetZ() * .35);
		Vec3d velocity = new Vec3d(0.03D * facingSorted.getFrontOffsetX(), 0.1D, 0.03D * facingSorted.getFrontOffsetZ());

		EntityItem entityItem = new EntityItem(world, posSpawn.x, (posSpawn.y - 0.5F) + (2.65F / 16F), posSpawn.z, stack);
		entityItem.isAirBorne = true;
		entityItem.motionX = velocity.x;
		entityItem.motionY = velocity.y;
		entityItem.motionZ = velocity.z;

		world.spawnEntity(entityItem);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (!worldIn.isRemote) {
			if (entityIn instanceof EntityItem && !entityIn.isDead) {
				ItemStack stack = ((EntityItem) entityIn).getItem();

				int size = ((EntityItem) entityIn).getItem().getCount();
				int smallHalf = size / 2;
				int largeHalf = size - smallHalf;

				ItemStack smallStack = stack.copy();
				ItemStack largeStack = stack.copy();

				smallStack.setCount(smallHalf);
				largeStack.setCount(largeHalf);

				if (smallStack.getCount() != 0) {
					sortItemStack(smallStack, worldIn, pos, state.getValue(FACING), false);
				}

				sortItemStack(largeStack, worldIn, pos, state.getValue(FACING), true);

				worldIn.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.1F, 1);

				entityIn.setDead();
			}
		}
	}

	@Override
	public boolean compareFacing() {
		return true;
	}
}