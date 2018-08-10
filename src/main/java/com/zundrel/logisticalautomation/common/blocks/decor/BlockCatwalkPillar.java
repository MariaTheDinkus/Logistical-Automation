package com.zundrel.logisticalautomation.common.blocks.decor;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.common.blocks.BlockBasic;
import com.zundrel.logisticalautomation.common.registry.BlockRegistry;
import com.zundrel.logisticalautomation.common.registry.LogisticCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockCatwalkPillar extends BlockBasic {
	public static final PropertyBool NE = PropertyBool.create("ne");
	public static final PropertyBool SE = PropertyBool.create("se");
	public static final PropertyBool SW = PropertyBool.create("sw");
	public static final PropertyBool NW = PropertyBool.create("nw");

	public BlockCatwalkPillar(String unlocalizedName, Material material) {
		super(unlocalizedName, material, LogisticCreativeTabs.LogisticConveyorTab.INSTANCE);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, NE, SW, SE, NW);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return super.getStateFromMeta(meta);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		boolean[] connected = getConnections(worldIn, pos);
		for (Corner corner : Corner.values()) {
			state = state.withProperty(corner.prop, connected[corner.ordinal()]);
		}
		return state;
	}

	private boolean[] getConnections(IBlockAccess world, BlockPos pos) {
		boolean[] res = new boolean[4];

		boolean frameXPos = world.getBlockState(pos.east()).getBlock() == LogisticalAutomation.Blocks.catwalk_pillar;
		boolean frameXNeg = world.getBlockState(pos.west()).getBlock() == LogisticalAutomation.Blocks.catwalk_pillar;
		boolean frameZPos = world.getBlockState(pos.south()).getBlock() == LogisticalAutomation.Blocks.catwalk_pillar;
		boolean frameZNeg = world.getBlockState(pos.north()).getBlock() == LogisticalAutomation.Blocks.catwalk_pillar;

		res[Corner.SE.ordinal()]  = frameXPos || frameZPos;
		res[Corner.NE.ordinal()]  = frameXPos || frameZNeg;
		res[Corner.SW.ordinal()]  = frameXNeg || frameZPos;
		res[Corner.NW.ordinal()]  = frameXNeg || frameZNeg;

		return res;
	}

	@Override
	@Nonnull
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	/**
	 * @author TeamPnuematic
	 * Thank you to TeamPneumatic for their code from the elevator frame.
	 */
	private enum Corner {
		NE(1, -1, BlockCatwalkPillar.NE, new AxisAlignedBB(14f / 16f, 0, 0, 1, 1, 2f/16f)),
		SE(1, 1, BlockCatwalkPillar.SE, new AxisAlignedBB(14f / 16f, 0, 14f / 16f, 1, 1, 1)),
		SW(-1, 1, BlockCatwalkPillar.SW, new AxisAlignedBB(0, 0, 14f / 16f, 2f / 16f, 1, 1)),
		NW(-1,-1, BlockCatwalkPillar.NW, new AxisAlignedBB(0, 0, 0, 2f/16f, 1, 2f/16f));

		final int x;
		final int z;
		final PropertyBool prop;
		final AxisAlignedBB aabb;

		Corner(int x, int z, PropertyBool prop, AxisAlignedBB aabb) {
			this.x = x; this.z = z;
			this.prop = prop;
			this.aabb = aabb;
		}
	}
}
