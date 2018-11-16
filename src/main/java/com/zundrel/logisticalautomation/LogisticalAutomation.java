package com.zundrel.logisticalautomation;

import com.zundrel.logisticalautomation.common.blocks.decor.BlockCatwalk;
import com.zundrel.logisticalautomation.common.blocks.decor.BlockCatwalkPillar;
import com.zundrel.logisticalautomation.common.blocks.decor.BlockCatwalkStairs;
import com.zundrel.logisticalautomation.common.blocks.machines.*;
import com.zundrel.logisticalautomation.common.blocks.machines.conveyors.BlockFlatConveyor;
import com.zundrel.logisticalautomation.common.blocks.machines.conveyors.BlockStairConveyor;
import com.zundrel.logisticalautomation.common.blocks.machines.conveyors.BlockVerticalConveyor;
import com.zundrel.logisticalautomation.common.items.ItemBasic;
import com.zundrel.logisticalautomation.common.items.ItemWrench;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.zundrel.logisticalautomation.common.CommonProxy;
import com.zundrel.logisticalautomation.common.handler.GuiHandler;
import com.zundrel.logisticalautomation.common.info.ModInfo;
import com.zundrel.logisticalautomation.common.network.MessageButton;
import com.zundrel.logisticalautomation.common.registry.TileRegistry;

@Mod(name = ModInfo.MOD_NAME, modid = ModInfo.MOD_ID, version = ModInfo.MOD_VERSION, dependencies = "required-after:patchouli;after:mcmultipart")
public class LogisticalAutomation {
	@Mod.Instance(ModInfo.MOD_ID)
	public static LogisticalAutomation instance;

	@SidedProxy(clientSide = ModInfo.MOD_CLIENT_PROXY, serverSide = ModInfo.MOD_SERVER_PROXY)
	public static CommonProxy proxy;

	public static SimpleNetworkWrapper networkWrapper;

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(ModInfo.MOD_ID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.logistic_wrench);
        }
    };

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		TileRegistry.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

		networkWrapper = new SimpleNetworkWrapper(ModInfo.MOD_ID);
		networkWrapper.registerMessage(MessageButton.class, MessageButton.class, 0, Side.SERVER);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

	@GameRegistry.ObjectHolder(ModInfo.MOD_ID)
	public static class Blocks {
		public static final BlockFlatConveyor conveyor_stone = null;

		public static final BlockFlatConveyor conveyor_normal = null;
		public static final BlockFlatConveyor conveyor_fast = null;
		public static final BlockFlatConveyor conveyor_express = null;

		public static final BlockVerticalConveyor conveyor_vertical_normal = null;
		public static final BlockVerticalConveyor conveyor_vertical_fast = null;
		public static final BlockVerticalConveyor conveyor_vertical_express = null;

		public static final BlockStairConveyor conveyor_stair_normal = null;
		public static final BlockStairConveyor conveyor_stair_fast = null;
		public static final BlockStairConveyor conveyor_stair_express = null;

		public static final BlockFilter filter = null;
		public static final BlockAlternator alternator = null;
		public static final BlockJunction junction = null;
		public static final BlockSplitter splitter = null;
		public static final BlockExtractor extractor = null;

		public static final BlockBlowtorch blowtorch = null;
		public static final BlockPolyBlowtorch poly_blowtorch = null;

		public static final BlockGrate grate = null;

		public static final BlockCatwalk catwalk = null;
		public static final BlockCatwalkStairs catwalk_stairs = null;
		public static final BlockCatwalkPillar catwalk_pillar = null;
	}

	@GameRegistry.ObjectHolder(ModInfo.MOD_ID)
	public static class Items {
	    public static final ItemBasic iron_stick = null;

		public static final ItemBasic roller_set = null;
		public static final ItemBasic iron_motor_set = null;
		public static final ItemBasic gold_motor_set = null;
		public static final ItemBasic diamond_motor_set = null;
		public static final ItemBasic leather_belt_set = null;

		public static final ItemWrench logistic_wrench = null;
	}
}