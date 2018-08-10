package com.zundrel.logisticalautomation.common.registry;

import com.zundrel.logisticalautomation.common.blocks.decor.BlockCatwalkPillar;
import com.zundrel.logisticalautomation.common.blocks.machines.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import com.zundrel.logisticalautomation.api.conveyor.EnumConveyorTier;
import com.zundrel.logisticalautomation.common.blocks.decor.BlockCatwalk;
import com.zundrel.logisticalautomation.common.blocks.decor.BlockCatwalkStairs;
import com.zundrel.logisticalautomation.common.blocks.machines.conveyors.BlockFlatConveyor;
import com.zundrel.logisticalautomation.common.blocks.machines.conveyors.BlockStairConveyor;
import com.zundrel.logisticalautomation.common.blocks.machines.conveyors.BlockVerticalConveyor;
import com.zundrel.logisticalautomation.common.info.ModInfo;

@EventBusSubscriber(modid = ModInfo.MOD_ID)
public class BlockRegistry {
	public static IForgeRegistry<Block> registry;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		registry = event.getRegistry();

		register(new BlockFlatConveyor("conveyor_stone", Material.ROCK, EnumConveyorTier.STONE));

		register(new BlockFlatConveyor("conveyor_normal", Material.ROCK, EnumConveyorTier.NORMAL));
		register(new BlockFlatConveyor("conveyor_fast", Material.ROCK, EnumConveyorTier.FAST));
		register(new BlockFlatConveyor("conveyor_express", Material.ROCK, EnumConveyorTier.EXPRESS));

		register(new BlockVerticalConveyor("conveyor_vertical_normal", Material.ROCK, EnumConveyorTier.NORMAL));
		register(new BlockVerticalConveyor("conveyor_vertical_fast", Material.ROCK, EnumConveyorTier.FAST));
		register(new BlockVerticalConveyor("conveyor_vertical_express", Material.ROCK, EnumConveyorTier.EXPRESS));

		register(new BlockStairConveyor("conveyor_stair_normal", Material.ROCK, EnumConveyorTier.NORMAL));
		register(new BlockStairConveyor("conveyor_stair_fast", Material.ROCK, EnumConveyorTier.FAST));
		register(new BlockStairConveyor("conveyor_stair_express", Material.ROCK, EnumConveyorTier.EXPRESS));

		register(new BlockFilter("filter", Material.ROCK));
		register(new BlockAlternator("alternator", Material.ROCK));
		register(new BlockJunction("junction", Material.ROCK));
		register(new BlockSplitter("splitter", Material.ROCK));
		register(new BlockExtractor("extractor", Material.ROCK));

		register(new BlockBlowtorch("blowtorch", Material.ROCK));
		register(new BlockPolyBlowtorch("poly_blowtorch", Material.ROCK));

		register(new BlockGrate("grate", Material.ROCK));

		register(new BlockCatwalk("catwalk", Material.ROCK));
		register(new BlockCatwalkStairs("catwalk_stairs", Material.ROCK));
		register(new BlockCatwalkPillar("catwalk_pillar", Material.ROCK));
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