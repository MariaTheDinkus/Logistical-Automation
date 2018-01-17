package com.zundrel.logisticalautomation.common.blocks;

import com.zundrel.logisticalautomation.api.*;
import com.zundrel.logisticalautomation.client.LogisticCreativeTabs.LogisticConveyorTab;
import com.zundrel.logisticalautomation.common.blocks.tiles.TileEntityFilter;
import com.zundrel.logisticalautomation.common.utilities.InventoryUtils;
import com.zundrel.logisticalautomation.common.utilities.MovementUtilities;
import com.zundrel.logisticalautomation.common.utilities.RotationUtilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.List;

public class BlockConveyor extends BlockFacing implements IConveyor, IWrenchable {
    public static final PropertyBool BACK = PropertyBool.create("back");
    public static final PropertyBool LEFT = PropertyBool.create("left");
    public static final PropertyBool RIGHT = PropertyBool.create("right");
    public static final PropertyBool ADDON = PropertyBool.create("addon");
    protected ConveyorTier tier;
    protected ConveyorType type;

    public BlockConveyor(String unlocalizedName, Material material, ConveyorTier tier, ConveyorType type) {
        super(unlocalizedName, material, LogisticConveyorTab.INSTANCE);

        this.tier = tier;
        this.type = type;

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
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        EnumFacing facing = state.getValue(FACING);

        if (type == ConveyorType.FLAT && !entityIn.onGround) {
            return;
        } else if (entityIn.isSneaking()) {
            return;
        }

        if (entityIn instanceof EntityItem) {
            entityIn.stepHeight = 0.5F;
        }

        if (type != ConveyorType.STAIRDOWN) {
            MovementUtilities.pushEntity(entityIn, pos, getSpeed(tier), state.getValue(FACING));
        } else if (type != ConveyorType.VERTICAL && type != ConveyorType.INVERSE) {
            if (!entityIn.onGround) {
                return;
            }
        }

        if (type == ConveyorType.VERTICAL) {
            double distX = Math.abs(pos.offset(facing).getX() + .5 - entityIn.posX);
            double distZ = Math.abs(pos.offset(facing).getZ() + .5 - entityIn.posZ);

            boolean contact = facing.getAxis() == Axis.Z ? distZ < 0.9 : distX < 0.9;

            if (entityIn instanceof EntityItem) {
                contact = true;
            }

            if (contact && (tier == ConveyorTier.NORMAL || tier == ConveyorTier.FAST) && worldIn.getBlockState(pos.offset(state.getValue(FACING)).up()).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(state.getValue(FACING)).up()).getBlock()).getConveyorType() == ConveyorType.FLAT) {
                MovementUtilities.pushEntityUp(entityIn, pos, getSpeed(tier) + 0.13, state.getValue(FACING), true);
            } else if (contact) {
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

        double distX = Math.abs(pos.offset(facing).getX() + .5 - entityIn.posX);
        double distZ = Math.abs(pos.offset(facing).getZ() + .5 - entityIn.posZ);

        if (!worldIn.isRemote && (type == ConveyorType.FLAT || type == ConveyorType.INVERSE) && entityIn instanceof EntityItem) {
            EntityItem entity = (EntityItem) entityIn;
            BlockPos invPos = pos.offset(facing);
            TileEntity inventoryTile = worldIn.getTileEntity(invPos);
            boolean contact = facing.getAxis() == Axis.Z ? distZ < .7 : distX < .7;
            if (contact && inventoryTile != null) {
                ItemStack stack = entity.getItem();
                if (!stack.isEmpty()) {
                    ItemStack ret = InventoryUtils.insertStackIntoInventory(inventoryTile, stack, facing.getOpposite());
                    if (ret.isEmpty()) {
                        entity.setDead();
                    } else if (ret.getCount() < stack.getCount()) {
                        entity.setItem(ret);
                    }
                }
            }
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, BACK, LEFT, RIGHT, ADDON);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);

        boolean back = false;
        boolean left = false;
        boolean right = false;
        boolean addon = false;

        if (type == ConveyorType.FLAT) {
            if (worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock() != Blocks.AIR) {
                back = true;
            } else if (worldIn.getBlockState(pos.offset(facing.getOpposite()).down()).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(facing.getOpposite()).down()).getBlock()).getConveyorType() == ConveyorType.STAIRUP) {
                back = true;
            }

            if (worldIn.getBlockState(pos.down().offset(facing.getOpposite())).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(facing.getOpposite()).down()).getBlock()).getConveyorType() == ConveyorType.VERTICAL) {
                back = true;
            }

            if (worldIn.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(facing.rotateYCCW())).getBlock()).getConveyorType() == ConveyorType.FLAT) {
                left = true;
            }

            if (worldIn.getBlockState(pos.offset(facing.rotateY())).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(facing.rotateY())).getBlock()).getConveyorType() == ConveyorType.FLAT) {
                right = true;
            }

            BlockPos invPos = pos.offset(facing);
            IBlockState invState = worldIn.getBlockState(invPos);
            Block invBlock = invState.getBlock();
            TileEntity inventoryTile = worldIn.getTileEntity(invPos);

            if (inventoryTile != null && inventoryTile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite())) {
                addon = true;
            } else if (invBlock instanceof IShowHopper && ((IShowHopper) invBlock).showConveyorHopper() && ((IShowHopper) invBlock).compareFacing() && invState.getValue(BlockHorizontal.FACING) == facing || invBlock instanceof IShowHopper && ((IShowHopper) invBlock).showConveyorHopper() && !((IShowHopper) invBlock).compareFacing()) {
                addon = true;
            }
        } else if (type == ConveyorType.INVERSE) {
            if (worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock() != Blocks.AIR) {
                back = true;
            }

            if (worldIn.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(facing.rotateYCCW())).getBlock()).getConveyorType() == ConveyorType.INVERSE) {
                left = true;
            }

            if (worldIn.getBlockState(pos.offset(facing.rotateY())).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(facing.rotateY())).getBlock()).getConveyorType() == ConveyorType.INVERSE) {
                right = true;
            }

            BlockPos invPos = pos.offset(facing);
            TileEntity inventoryTile = worldIn.getTileEntity(invPos);

            if (inventoryTile != null && (inventoryTile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()) || inventoryTile instanceof TileEntityFilter)) {
                left = false;
                right = false;
                addon = true;
            }
        } else if (type == ConveyorType.VERTICAL) {
            if (worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock() instanceof IConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock()).getConveyorType() == ConveyorType.FLAT && facing == worldIn.getBlockState(pos.offset(facing.getOpposite())).getValue(FACING)) {
                back = true;
            }

            if (worldIn.getBlockState(pos.offset(facing).up()).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(facing).up()).getBlock()).getConveyorType() == ConveyorType.FLAT) {
                addon = true;
            }
        } else if (type == ConveyorType.STAIRUP || type == ConveyorType.STAIRDOWN) {
            if (worldIn.getBlockState(pos.offset(facing).up()).getBlock() instanceof BlockConveyor && ((IConveyor) worldIn.getBlockState(pos.offset(facing).up()).getBlock()).getConveyorType() == ConveyorType.FLAT) {
                back = true;
            }
        }

        return super.getActualState(state, worldIn, pos).withProperty(BACK, back).withProperty(LEFT, left).withProperty(RIGHT, right).withProperty(ADDON, addon);
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
}