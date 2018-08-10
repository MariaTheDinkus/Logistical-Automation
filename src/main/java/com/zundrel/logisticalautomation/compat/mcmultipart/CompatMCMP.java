package com.zundrel.logisticalautomation.compat.mcmultipart;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.common.blocks.BlockBasic;
import com.zundrel.logisticalautomation.common.blocks.BlockFacing;
import mcmultipart.api.addon.IMCMPAddon;
import mcmultipart.api.addon.IWrappedBlock;
import mcmultipart.api.addon.MCMPAddon;
import mcmultipart.api.container.IPartInfo;
import mcmultipart.api.item.ItemBlockMultipart;
import mcmultipart.api.multipart.IMultipart;
import mcmultipart.api.multipart.IMultipartRegistry;
import mcmultipart.api.multipart.MultipartHelper;
import mcmultipart.api.slot.IPartSlot;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

@MCMPAddon
public class CompatMCMP implements IMCMPAddon {

	List<BlockBasic> blocks = new ArrayList<BlockBasic>();

	@Override
	public void registerParts(IMultipartRegistry registry) {
		blocks.add(LogisticalAutomation.Blocks.conveyor_stone);

		blocks.add(LogisticalAutomation.Blocks.conveyor_normal);
		blocks.add(LogisticalAutomation.Blocks.conveyor_fast);
		blocks.add(LogisticalAutomation.Blocks.conveyor_express);

		blocks.add(LogisticalAutomation.Blocks.conveyor_vertical_normal);
		blocks.add(LogisticalAutomation.Blocks.conveyor_vertical_fast);
		blocks.add(LogisticalAutomation.Blocks.conveyor_vertical_express);

		blocks.add(LogisticalAutomation.Blocks.grate);

		for (BlockBasic block : blocks) {
			registry.registerPartWrapper(block, new PartBounds(block));

			IWrappedBlock conveyor_stone = registry.registerStackWrapper(Item.getItemFromBlock(block), stack -> true, block);
			conveyor_stone.setPlacementInfo(this::getState);
		}
	}

	private IBlockState getState(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand, IBlockState state) {
		if (state.getBlock() instanceof BlockFacing) {
			return state.withProperty(BlockFacing.FACING, placer.getHorizontalFacing());
		} else {
			return state;
		}
	}

}