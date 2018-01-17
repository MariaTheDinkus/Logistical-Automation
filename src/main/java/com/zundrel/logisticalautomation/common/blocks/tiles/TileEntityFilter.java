package com.zundrel.logisticalautomation.common.blocks.tiles;

import com.zundrel.logisticalautomation.common.blocks.BlockFacing;
import com.zundrel.logisticalautomation.common.utilities.Filter;
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

import javax.annotation.Nonnull;

public class TileEntityFilter extends TileEntity implements ITickable {
    private NonNullList<ItemStack> northFilterInventory;
    private NonNullList<ItemStack> westFilterInventory;
    private NonNullList<ItemStack> eastFilterInventory;

    private Filter northFilter = new Filter();
    private Filter westFilter = new Filter();
    private Filter eastFilter = new Filter();

    public TileEntityFilter() {
        northFilterInventory = NonNullList.withSize(9, ItemStack.EMPTY);
        westFilterInventory = NonNullList.withSize(9, ItemStack.EMPTY);
        eastFilterInventory = NonNullList.withSize(9, ItemStack.EMPTY);
    }

    public NonNullList<ItemStack> getNorthFilterInventory() {
        return northFilterInventory;
    }

    public NonNullList<ItemStack> getWestFilterInventory() {
        return westFilterInventory;
    }

    public NonNullList<ItemStack> getEastFilterInventory() {
        return eastFilterInventory;
    }

    public Filter getNorthFilter() {
        return northFilter;
    }

    public Filter getWestFilter() {
        return westFilter;
    }

    public Filter getEastFilter() {
        return eastFilter;
    }

    @Override
    public void update() {
        // System.out.println(eastFilter.meta);
    }

    private EnumFacing getFacing() {
        return world.getBlockState(pos).getValue(BlockFacing.FACING);
    }

    public void sortItemStack(ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        EnumFacing facingSorted = getSideForItem(stack);

        Vec3d posSpawn = new Vec3d(pos.offset(facingSorted).getX() + 0.5D - facingSorted.getFrontOffsetX() * .35, pos.offset(facingSorted).getY() + 0.4D, pos.offset(facingSorted).getZ() + 0.5D - facingSorted.getFrontOffsetZ() * .35);
        Vec3d velocity = new Vec3d(0.03D * facingSorted.getFrontOffsetX(), 0.1D, 0.03D * facingSorted.getFrontOffsetZ());

        EntityItem entityItem = new EntityItem(world, posSpawn.x, (posSpawn.y - 0.5F) + (2.65F / 16F), posSpawn.z, stack);
        entityItem.isAirBorne = true;
        entityItem.motionX = velocity.x;
        entityItem.motionY = velocity.y;
        entityItem.motionZ = velocity.z;

        world.spawnEntity(entityItem);
    }

    private EnumFacing getSideForItem(ItemStack stack) {
        EnumFacing facingSorter = getFacing();

        if (filterContainsItem(stack, westFilterInventory, westFilter))
            return facingSorter.rotateYCCW();

        else if (filterContainsItem(stack, eastFilterInventory, eastFilter))
            return facingSorter.rotateY();

        else if (filterContainsItem(stack, northFilterInventory, northFilter))
            return facingSorter;

        else
            return EnumFacing.UP;
    }

    private boolean filterContainsItem(ItemStack stack, NonNullList<ItemStack> filter, Filter f) {
        boolean match = filter.stream().anyMatch(fs -> f.match(fs, stack));
        return f.white ^ !match;
    }

    public void handleMessage(NBTTagCompound nbt) {
        northFilter.deserializeNBT(nbt.getCompoundTag("n"));
        westFilter.deserializeNBT(nbt.getCompoundTag("w"));
        eastFilter.deserializeNBT(nbt.getCompoundTag("e"));
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound north = new NBTTagCompound();
        NBTTagCompound west = new NBTTagCompound();
        NBTTagCompound east = new NBTTagCompound();

        ItemStackHelper.saveAllItems(north, northFilterInventory);
        ItemStackHelper.saveAllItems(west, westFilterInventory);
        ItemStackHelper.saveAllItems(east, eastFilterInventory);

        compound.setTag("northFilterInventory", north);
        compound.setTag("westFilterInventory", west);
        compound.setTag("eastFilterInventory", east);

        compound.setTag("northFilter", northFilter.serializeNBT());
        compound.setTag("westFilter", westFilter.serializeNBT());
        compound.setTag("eastFilter", eastFilter.serializeNBT());

        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        ItemStackHelper.loadAllItems(compound.getCompoundTag("northFilterInventory"), northFilterInventory);
        ItemStackHelper.loadAllItems(compound.getCompoundTag("westFilterInventory"), westFilterInventory);
        ItemStackHelper.loadAllItems(compound.getCompoundTag("eastFilterInventory"), eastFilterInventory);

        northFilter.deserializeNBT(compound.getCompoundTag("northFilter"));
        westFilter.deserializeNBT(compound.getCompoundTag("westFilter"));
        eastFilter.deserializeNBT(compound.getCompoundTag("eastFilter"));

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
