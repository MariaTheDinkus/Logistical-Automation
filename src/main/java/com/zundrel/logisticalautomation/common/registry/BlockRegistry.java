package com.zundrel.logisticalautomation.common.registry;

import com.zundrel.logisticalautomation.common.blocks.BlockClear;
import com.zundrel.logisticalautomation.common.blocks.decor.BlockCatwalkPillar;
import com.zundrel.logisticalautomation.common.blocks.machines.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import com.zundrel.logisticalautomation.api.EnumConveyorTier;
import com.zundrel.logisticalautomation.common.blocks.BlockFacing;
import com.zundrel.logisticalautomation.common.blocks.decor.BlockCatwalk;
import com.zundrel.logisticalautomation.common.blocks.decor.BlockCatwalkStairs;
import com.zundrel.logisticalautomation.common.blocks.machines.conveyors.BlockFlatConveyor;
import com.zundrel.logisticalautomation.common.blocks.machines.conveyors.BlockStairConveyor;
import com.zundrel.logisticalautomation.common.blocks.machines.conveyors.BlockVerticalConveyor;
import com.zundrel.logisticalautomation.common.info.ModInfo;

@EventBusSubscriber(modid = ModInfo.MOD_ID)
public class BlockRegistry {
	public static IForgeRegistry<Block> registry;

	public static Block conveyor_normal, conveyor_fast, conveyor_express;
	public static Block conveyor_vertical_normal, conveyor_vertical_fast, conveyor_vertical_express;
	public static Block conveyor_stair_normal, conveyor_stair_fast, conveyor_stair_express;

	public static Block conveyor_net;

	public static Block filter;
	public static Block splitter;
	public static Block junction;
	public static Block halver;

	public static Block blowtorch;
	public static Block poly_blowtorch;

	public static Block grate;

	public static Block catwalk;
	public static Block catwalk_stairs;
	public static Block catwalk_pillar;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		registry = event.getRegistry();

		register(conveyor_normal = new BlockFlatConveyor("conveyor_normal", Material.ROCK, EnumConveyorTier.NORMAL));
		register(conveyor_fast = new BlockFlatConveyor("conveyor_fast", Material.ROCK, EnumConveyorTier.FAST));
		register(conveyor_express = new BlockFlatConveyor("conveyor_express", Material.ROCK, EnumConveyorTier.EXPRESS));

		register(conveyor_vertical_normal = new BlockVerticalConveyor("conveyor_vertical_normal", Material.ROCK, EnumConveyorTier.NORMAL));
		register(conveyor_vertical_fast = new BlockVerticalConveyor("conveyor_vertical_fast", Material.ROCK, EnumConveyorTier.FAST));
		register(conveyor_vertical_express = new BlockVerticalConveyor("conveyor_vertical_express", Material.ROCK, EnumConveyorTier.EXPRESS));

		register(conveyor_stair_normal = new BlockStairConveyor("conveyor_stair_normal", Material.ROCK, EnumConveyorTier.NORMAL));
		register(conveyor_stair_fast = new BlockStairConveyor("conveyor_stair_fast", Material.ROCK, EnumConveyorTier.FAST));
		register(conveyor_stair_express = new BlockStairConveyor("conveyor_stair_express", Material.ROCK, EnumConveyorTier.EXPRESS));

		register(conveyor_net = new BlockFacing("conveyor_net", Material.ROCK));

		register(filter = new BlockFilter("filter", Material.ROCK));
		register(splitter = new BlockSplitter("splitter", Material.ROCK));
		register(junction = new BlockJunction("junction", Material.ROCK));
		register(halver = new BlockHalver("halver", Material.ROCK));

		register(blowtorch = new BlockBlowtorch("blowtorch", Material.ROCK));
		register(poly_blowtorch = new BlockPolyBlowtorch("poly_blowtorch", Material.ROCK));

		register(grate = new BlockGrate("grate", Material.ROCK));

		register(catwalk = new BlockCatwalk("catwalk", Material.ROCK));
		register(catwalk_stairs = new BlockCatwalkStairs("catwalk_stairs", Material.ROCK));
		register(catwalk_pillar = new BlockCatwalkPillar("catwalk_pillar", Material.ROCK));
	}

	public static <T extends Block> T register(T b, ItemBlock ib) {
		registry.register(b);
		ib.setRegistryName(b.getRegistryName());
		ItemRegistry.itemBlocks.add(ib);
		ModelRegistry.modelList.add(ib);
		return b;
	}

	public static <T extends Block> T register(T b) {
		ItemBlock ib = new ItemBlock(b);
		return register(b, ib);
	}
}