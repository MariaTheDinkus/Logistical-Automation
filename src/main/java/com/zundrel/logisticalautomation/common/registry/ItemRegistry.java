package com.zundrel.logisticalautomation.common.registry;

import java.util.ArrayList;

import com.zundrel.logisticalautomation.common.items.ItemBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import com.zundrel.logisticalautomation.common.info.ModInfo;
import com.zundrel.logisticalautomation.common.items.ItemWrench;

@EventBusSubscriber(modid = ModInfo.MOD_ID)
public class ItemRegistry {
	public static IForgeRegistry<Item> registry;
	public static ArrayList<ItemBlock> itemBlocks = new ArrayList<ItemBlock>();

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		registry = event.getRegistry();

		register(new ItemWrench("logistic_wrench"));

		register(new ItemBasic("iron_stick"));
		register(new ItemBasic("roller_set"));
		register(new ItemBasic("iron_motor_set"));
		register(new ItemBasic("gold_motor_set"));
		register(new ItemBasic("diamond_motor_set"));
		register(new ItemBasic("leather_belt_set"));

		itemBlocks.forEach((ib) -> {
			registry.register(ib);
		});
	}

	public static <T extends Item> T register(T i) {
		registry.register(i);
		/*
		 * if (i instanceof ItemBasic) { ((ItemBasic) i).registerModel(i); }
		 */
		ModelRegistry.modelList.add(i);
		return i;
	}
}