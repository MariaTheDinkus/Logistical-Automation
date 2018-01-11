package com.zundrel.logisticalautomation.common.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import com.zundrel.logisticalautomation.api.ConveyorTier;
import com.zundrel.logisticalautomation.api.ConveyorType;
import com.zundrel.logisticalautomation.common.blocks.BlockConveyor;
import com.zundrel.logisticalautomation.common.blocks.BlockFacing;
import com.zundrel.logisticalautomation.common.blocks.BlockFilter;
import com.zundrel.logisticalautomation.common.blocks.BlockSplitter;
import com.zundrel.logisticalautomation.common.handler.ConfigHandler;
import com.zundrel.logisticalautomation.common.info.ModInfo;

@EventBusSubscriber(modid = ModInfo.MOD_ID)
public class BlockRegistry {
	public static IForgeRegistry<Block> registry;

	public static Block conveyor_normal, conveyor_fast, conveyor_express;
	public static Block conveyor_vertical_normal, conveyor_vertical_fast,
			conveyor_vertical_express;
	public static Block conveyor_inverse_normal, conveyor_inverse_fast,
			conveyor_inverse_express;
	public static Block conveyor_stair_normal, conveyor_stair_fast,
			conveyor_stair_express;
	public static Block conveyor_stair_down_normal, conveyor_stair_down_fast,
			conveyor_stair_down_express;

	public static Block conveyor_net;

	public static Block filter;

	public static Block splitter;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		registry = event.getRegistry();

		register(conveyor_normal = new BlockConveyor("conveyor_normal", Material.ROCK, ConveyorTier.NORMAL, ConveyorType.FLAT));
		register(conveyor_fast = new BlockConveyor("conveyor_fast", Material.ROCK, ConveyorTier.FAST, ConveyorType.FLAT));
		register(conveyor_express = new BlockConveyor("conveyor_express", Material.ROCK, ConveyorTier.EXPRESS, ConveyorType.FLAT));

		register(conveyor_vertical_normal = new BlockConveyor("conveyor_vertical_normal", Material.ROCK, ConveyorTier.NORMAL, ConveyorType.VERTICAL));
		register(conveyor_vertical_fast = new BlockConveyor("conveyor_vertical_fast", Material.ROCK, ConveyorTier.FAST, ConveyorType.VERTICAL));
		register(conveyor_vertical_express = new BlockConveyor("conveyor_vertical_express", Material.ROCK, ConveyorTier.EXPRESS, ConveyorType.VERTICAL));

		if (ConfigHandler.enableInverseConveyors) {
			register(conveyor_inverse_normal = new BlockConveyor("conveyor_inverse_normal", Material.ROCK, ConveyorTier.NORMAL, ConveyorType.INVERSE));
			register(conveyor_inverse_fast = new BlockConveyor("conveyor_inverse_fast", Material.ROCK, ConveyorTier.FAST, ConveyorType.INVERSE));
			register(conveyor_inverse_express = new BlockConveyor("conveyor_inverse_express", Material.ROCK, ConveyorTier.EXPRESS, ConveyorType.INVERSE));
		}

		register(conveyor_stair_normal = new BlockConveyor("conveyor_stair_normal", Material.ROCK, ConveyorTier.NORMAL, ConveyorType.STAIRUP));
		register(conveyor_stair_fast = new BlockConveyor("conveyor_stair_fast", Material.ROCK, ConveyorTier.FAST, ConveyorType.STAIRUP));
		register(conveyor_stair_express = new BlockConveyor("conveyor_stair_express", Material.ROCK, ConveyorTier.EXPRESS, ConveyorType.STAIRUP));

		register(conveyor_stair_down_normal = new BlockConveyor("conveyor_stair_down_normal", Material.ROCK, ConveyorTier.NORMAL, ConveyorType.STAIRDOWN));
		register(conveyor_stair_down_fast = new BlockConveyor("conveyor_stair_down_fast", Material.ROCK, ConveyorTier.FAST, ConveyorType.STAIRDOWN));
		register(conveyor_stair_down_express = new BlockConveyor("conveyor_stair_down_express", Material.ROCK, ConveyorTier.EXPRESS, ConveyorType.STAIRDOWN));

		register(conveyor_net = new BlockFacing("conveyor_net", Material.ROCK));

		register(filter = new BlockFilter("filter", Material.ROCK));

		register(splitter = new BlockSplitter("splitter", Material.ROCK));
	}

	public static <T extends Block> T register(T b, ItemBlock ib) {
		registry.register(b);
		ib.setRegistryName(b.getRegistryName());
		ItemRegistry.itemBlocks.add(ib);
		return b;
	}

	public static <T extends Block> T register(T b) {
		ItemBlock ib = new ItemBlock(b);
		return register(b, ib);
	}
}