package com.zundrel.logisticalautomation.common.blocks.machines.conveyors;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.zundrel.logisticalautomation.api.conveyor.EnumConveyorTier;
import com.zundrel.logisticalautomation.api.conveyor.IConveyor;
import com.zundrel.logisticalautomation.api.IWrenchable;
import com.zundrel.logisticalautomation.common.blocks.BlockFacing;
import com.zundrel.logisticalautomation.common.utilities.InventoryUtils;

public class BlockConveyor extends BlockFacing implements IConveyor, IWrenchable {
	public static final PropertyBool BACK = PropertyBool.create("back");
	public static final PropertyBool LEFT = PropertyBool.create("left");
	public static final PropertyBool RIGHT = PropertyBool.create("right");
	public static final PropertyBool ADDON = PropertyBool.create("addon");
	protected EnumConveyorTier tier;

	public BlockConveyor(String unlocalizedName, Material material, EnumConveyorTier tier) {
		super(unlocalizedName, material, LogisticalAutomation.CREATIVE_TAB);

		this.tier = tier;

		this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(BACK, false).withProperty(LEFT, false).withProperty(RIGHT, false).withProperty(ADDON, false));
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
		return new BlockStateContainer(this, FACING, BACK, LEFT, RIGHT, ADDON);
	}

	@Override
	public EnumConveyorTier getConveyorTier() {
		return tier;
	}

	public static void insert(World worldIn, BlockPos pos, EnumFacing facing, Entity entityIn) {
		double distX = Math.abs(pos.offset(facing).getX() + .5 - entityIn.posX);
		double distZ = Math.abs(pos.offset(facing).getZ() + .5 - entityIn.posZ);

		if (!worldIn.isRemote && entityIn instanceof EntityItem) {
			EntityItem entity = (EntityItem) entityIn;
			BlockPos invPos = pos.offset(facing);
			TileEntity inventoryTile = worldIn.getTileEntity(invPos);
			boolean contact = facing.getAxis() == Axis.Z ? distZ < .7 : distX < .7;
			if (contact && inventoryTile != null) {
				ItemStack stack = entity.getItem();
				if (!stack.isEmpty()) {
					if (inventoryTile instanceof TileEntityFurnace) {
						if (TileEntityFurnace.isItemFuel(stack)) {
							ItemStack ret = InventoryUtils.insertStackIntoInventory(inventoryTile, stack, facing.getOpposite());
							if (ret.isEmpty()) {
								entity.setDead();
							} else if (ret.getCount() < stack.getCount()) {
								entity.setItem(ret);
							}
						} else {
							ItemStack ret = InventoryUtils.insertStackIntoInventory(inventoryTile, stack, EnumFacing.UP);
							if (ret.isEmpty()) {
								entity.setDead();
							} else if (ret.getCount() < stack.getCount()) {
								entity.setItem(ret);
							}
						}
					} else {
						ItemStack ret = InventoryUtils.insertStackIntoInventory(inventoryTile, stack, facing);
						if (ret.isEmpty()) {
							entity.setDead();
						} else if (ret.getCount() < stack.getCount()) {
							entity.setItem(ret);
						}
					}
				}
			}
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (entityIn instanceof EntityItem) {
			EntityItem item = (EntityItem) entityIn;
			entityIn.stepHeight = 0.5F;

			NBTTagCompound data = item.getEntityData();
			if (data != null) {
				if (!data.getBoolean("PreventRemoteMovement")) {
					data.setBoolean("PreventRemoteMovement", true);
				}

				if (!data.getBoolean("AllowMachineRemoteMovement")) {
					data.setBoolean("AllowMachineRemoteMovement", true);
				}
			}
		}
	}
}