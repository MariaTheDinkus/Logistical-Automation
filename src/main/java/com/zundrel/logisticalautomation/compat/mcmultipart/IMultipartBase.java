package com.zundrel.logisticalautomation.compat.mcmultipart;

import com.zundrel.logisticalautomation.common.blocks.BlockBasic;
import mcmultipart.api.container.IPartInfo;
import mcmultipart.api.multipart.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface IMultipartBase extends IMultipart {
    default void onPartPlacedBy(IPartInfo part, EntityLivingBase placer, ItemStack stack, EnumFacing face, float hitX, float hitY, float hitZ) {
        Block b = part.getState().getBlock();
        if (b instanceof BlockBasic) {
            ((BlockBasic) b).onBlockPlacedBy(part.getPartWorld(), part.getPartPos(), part.getState(), placer, stack);
        } else {
            b.onBlockPlacedBy(part.getPartWorld(), part.getPartPos(), part.getState(), placer, stack);
        }
    }
}