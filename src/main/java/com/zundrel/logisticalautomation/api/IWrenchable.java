package com.zundrel.logisticalautomation.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWrenchable {
    void onWrenched(World world, BlockPos pos, EntityPlayer player);
}