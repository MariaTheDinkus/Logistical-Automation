package com.zundrel.logisticalautomation.common.blocks.tiles;

import com.zundrel.logisticalautomation.common.blocks.machines.BlockBlowtorch;
import com.zundrel.logisticalautomation.common.utilities.Filter;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class TileEntityBlowtorch extends TileEntity implements ITickable {
	@Override
	public void update() {
	    IBlockState blowtorch = getWorld().getBlockState(getPos());
        AxisAlignedBB range = new AxisAlignedBB(0, 0, 0, 1, 1,1).offset(getPos().offset(blowtorch.getValue(BlockBlowtorch.FACING)));
		List entities = getWorld().getEntitiesWithinAABB(EntityLivingBase.class, range);

        if (getWorld().getTotalWorldTime() % 40 == 0) {
            if (blowtorch.getValue(BlockBlowtorch.POWERED)) {
                BlockPos flameDir = new BlockPos(0, 0, 0).offset(blowtorch.getValue(BlockBlowtorch.FACING));

                int particleAmount = 14;

                for (int i = 0; i < particleAmount; i++) {
                    double change = i * 0.01;
                    double changeX = i * flameDir.getX() * (1.5 / particleAmount);
                    double changeZ = i * flameDir.getZ() * (1.5 / particleAmount);

                    EnumParticleTypes type = EnumParticleTypes.SMOKE_NORMAL;

                    if (i > 2) {
                        type = EnumParticleTypes.FLAME;
                    }

                    getWorld().spawnParticle(type, getPos().getX() + 0.5 + changeX, getPos().getY() + 0.5 + ((new Random().nextFloat() * 0.1) - 0.05), getPos().getZ() + 0.5 + changeZ, flameDir.getX() * 0.03,0.005, flameDir.getZ() * 0.03);
                }

                for (Object obj : entities) {
                    EntityLivingBase entity = (EntityLivingBase) obj;

                    entity.attackEntityFrom(DamageSource.IN_FIRE, 6);
                    entity.setFire(2);
                }
            }

        }
	}
}