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

	public static Item iron_stick;

	public static Item roller_set;
	public static Item iron_motor_set;
	public static Item gold_motor_set;
	public static Item diamond_motor_set;
	public static Item leather_belt_set;

	public static Item logistic_wrench;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		registry = event.getRegistry();

		register(logistic_wrench = new ItemWrench("logistic_wrench"));

		register(iron_stick = new ItemBasic("iron_stick"));
		register(roller_set = new ItemBasic("roller_set"));
		register(iron_motor_set = new ItemBasic("iron_motor_set"));
		register(gold_motor_set = new ItemBasic("gold_motor_set"));
		register(diamond_motor_set = new ItemBasic("diamond_motor_set"));
		register(leather_belt_set = new ItemBasic("leather_belt_set"));

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