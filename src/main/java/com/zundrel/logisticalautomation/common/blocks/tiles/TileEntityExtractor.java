package com.zundrel.logisticalautomation.common.blocks.tiles;

import com.zundrel.logisticalautomation.common.blocks.machines.BlockExtractor;
import com.zundrel.logisticalautomation.common.utilities.Filter;
import com.zundrel.logisticalautomation.common.utilities.InventoryUtils;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class TileEntityExtractor extends TileEntity implements ITickable {
	private NonNullList<ItemStack> filterInventory;
	private boolean empty;

	private Filter filter = new Filter();

	public TileEntityExtractor() {
		filterInventory = NonNullList.withSize(9, ItemStack.EMPTY);
	}

	public NonNullList<ItemStack> getFilterInventory() {
		return filterInventory;
	}

	public Filter getFilter() {
		return filter;
	}

	private EnumFacing getFacing() {
		return world.getBlockState(pos).getValue(BlockHorizontal.FACING);
	}

	@Override
	public void update() {
		if(!getWorld().isRemote && getWorld().getTotalWorldTime() % 20 == 0) {
			for (ItemStack filterStack : getFilterInventory()) {
				if (!filterStack.isEmpty()) {
					empty = false;

					break;
				}
			}
		}

		if(!getWorld().isRemote && getWorld().getTotalWorldTime() % 5 == 0) {
			EnumFacing facing = getFacing();
			BlockPos outputPos = pos.offset(facing);

			ItemStack stack = InventoryUtils.extractStackFromInventory(this.getWorld(), this.getPos(), facing, empty);

			if (!stack.isEmpty()) {
				TileEntity tile = world.getTileEntity(outputPos);

				if (InventoryUtils.canInsertStackIntoInventory(tile, stack, facing.getOpposite())) {
					InventoryUtils.insertStackIntoInventory(tile, stack, facing.getOpposite());
				} else {
					EntityItem item = new EntityItem(world, outputPos.getX() + 0.5, outputPos.getY() + (1F / 16F), outputPos.getZ() + 0.5, stack);
					item.motionX = 0;
					item.motionY = 0;
					item.motionZ = 0;

					world.spawnEntity(item);
				}
			}
		}
	}

	public boolean filterContainsItem(ItemStack stack) {
		boolean match = filterInventory.stream().anyMatch(fs -> filter.match(fs, stack));
		return filter.white ^ !match;
	}

	public void handleMessage(NBTTagCompound nbt) {
		filter.deserializeNBT(nbt.getCompoundTag("f"));
		markDirty();
	}

	@Override
	@Nonnull
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound f = new NBTTagCompound();

		ItemStackHelper.saveAllItems(f, filterInventory);

		compound.setTag("filterInventory", f);

		compound.setTag("filter", filter.serializeNBT());

		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		ItemStackHelper.loadAllItems(compound.getCompoundTag("filterInventory"), filterInventory);

		filter.deserializeNBT(compound.getCompoundTag("filter"));

		super.readFromNBT(compound);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound updateTagDescribingTileEntityState = getUpdateTag();
		final int METADATA = 0;
		return new SPacketUpdateTileEntity(this.pos, METADATA, updateTagDescribingTileEntityState);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound updateTagDescribingTileEntityState = pkt.getNbtCompound();
		handleUpdateTag(updateTagDescribingTileEntityState);
	}

	@Override
	@Nonnull
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return nbtTagCompound;
	}

	@Override
	public void handleUpdateTag(@Nonnull NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, @Nonnull IBlockState oldState, @Nonnull IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
}
