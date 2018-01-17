package com.zundrel.logisticalautomation.common.registry;

import com.zundrel.logisticalautomation.common.info.ModInfo;
import com.zundrel.logisticalautomation.common.items.ItemWrench;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

@EventBusSubscriber(modid = ModInfo.MOD_ID)
public class ItemRegistry {
    public static IForgeRegistry<Item> registry;
    public static ArrayList<ItemBlock> itemBlocks = new ArrayList<ItemBlock>();

    public static Item logistic_wrench;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        registry = event.getRegistry();

        register(logistic_wrench = new ItemWrench("logistic_wrench"));

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