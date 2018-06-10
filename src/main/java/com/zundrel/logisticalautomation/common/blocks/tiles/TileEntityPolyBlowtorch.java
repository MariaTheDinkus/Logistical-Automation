package com.zundrel.logisticalautomation.common.blocks.tiles;

import com.zundrel.logisticalautomation.common.blocks.machines.BlockBlowtorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;

public class TileEntityPolyBlowtorch extends TileEntity implements ITickable {
	@Override
	public void update() {
	    IBlockState blowtorch = getWorld().getBlockState(getPos());
        AxisAlignedBB range = new AxisAlignedBB(-1, 0, -1, 2, 1,2).offset(getPos());
		List entities = getWorld().getEntitiesWithinAABB(EntityLivingBase.class, range);

        if (getWorld().getTotalWorldTime() % 40 == 0) {
            if (blowtorch.getValue(BlockBlowtorch.POWERED)) {
                int particleAmount = 14;

                for (int i = 0; i < particleAmount; i++) {
                    double changeDir = i * (1.05 / particleAmount) + 0.45;

                    EnumParticleTypes type = EnumParticleTypes.SMOKE_NORMAL;

                    if (i > 2) {
                        type = EnumParticleTypes.FLAME;
                    }

                    getWorld().spawnParticle(type, getPos().getX() + 0.5, getPos().getY() + 0.75 + ((new Random().nextFloat() * 0.1) - 0.05), getPos().getZ() + 0.5 + changeDir, 0,0.005, 0.03);
                    getWorld().spawnParticle(type, getPos().getX() + 0.5 + changeDir, getPos().getY() + 0.75 + ((new Random().nextFloat() * 0.1) - 0.05), getPos().getZ() + 0.5 + changeDir, 0.03,0.005, 0.03);
                    getWorld().spawnParticle(type, getPos().getX() + 0.5 + changeDir, getPos().getY() + 0.75 + ((new Random().nextFloat() * 0.1) - 0.05), getPos().getZ() + 0.5, 0.03,0.005, 0);
                    getWorld().spawnParticle(type, getPos().getX() + 0.5 + changeDir, getPos().getY() + 0.75 + ((new Random().nextFloat() * 0.1) - 0.05), getPos().getZ() + 0.5 - changeDir, 0.03,0.005, -0.03);
                    getWorld().spawnParticle(type, getPos().getX() + 0.5, getPos().getY() + 0.75 + ((new Random().nextFloat() * 0.1) - 0.05), getPos().getZ() + 0.5 - changeDir, 0,0.005, -0.03);
                    getWorld().spawnParticle(type, getPos().getX() + 0.5 - changeDir, getPos().getY() + 0.75 + ((new Random().nextFloat() * 0.1) - 0.05), getPos().getZ() + 0.5 - changeDir, -0.03,0.005, -0.03);
                    getWorld().spawnParticle(type, getPos().getX() + 0.5 - changeDir, getPos().getY() + 0.75 + ((new Random().nextFloat() * 0.1) - 0.05), getPos().getZ() + 0.5, -0.03,0.005, 0);
                    getWorld().spawnParticle(type, getPos().getX() + 0.5 - changeDir, getPos().getY() + 0.75 + ((new Random().nextFloat() * 0.1) - 0.05), getPos().getZ() + 0.5 + changeDir, -0.03,0.005, 0.03);

                    for (Object obj : entities) {
                        EntityLivingBase entity = (EntityLivingBase) obj;

                        entity.attackEntityFrom(DamageSource.IN_FIRE, 6);
                        entity.setFire(2);
                    }
                }
            }

        }
	}
}
