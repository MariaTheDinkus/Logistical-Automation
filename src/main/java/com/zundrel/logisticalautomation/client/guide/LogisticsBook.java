package com.zundrel.logisticalautomation.client.guide;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageFurnaceRecipe;
import amerifrance.guideapi.page.PageIRecipe;
import amerifrance.guideapi.page.PageText;

import com.zundrel.logisticalautomation.common.info.ModInfo;
import com.zundrel.logisticalautomation.common.registry.BlockRegistry;

@GuideBook
public class LogisticsBook implements IGuideBook {

	public static Book logisticsGuide;

	@Nonnull
	@Override
	public Book buildBook() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();

		List<IPage> pages1 = new ArrayList<IPage>();

		pages1.add(new PageText("Conveyors are one of the logistician's most valuable tools. They can move everything from items to players. Some examples include: items, players, mobs, and experience."));
		entries.put(new ResourceLocation(ModInfo.MOD_ID, "conveyors"), new EntryItemStack(pages1, "Conveyors Make the World Go Round", new ItemStack(BlockRegistry.conveyor_normal)));

		List<IPage> pages2 = new ArrayList<IPage>();
		pages2.add(new PageIRecipe(new ShapedOreRecipe(new ResourceLocation(ModInfo.MOD_ID), Items.DIAMOND_SWORD, "D", "D", "S", 'D', Items.DIAMOND, 'S', Items.STICK)));
		pages2.add(new PageFurnaceRecipe("oreGold"));
		entries.put(new ResourceLocation(ModInfo.MOD_ID, "entry_two"), new EntryItemStack(pages2, "Entry Two", new ItemStack(Items.DIAMOND_SWORD)));

		List<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();
		categories.add(new CategoryItemStack(entries, "My Category", new ItemStack(Blocks.COMMAND_BLOCK)));

		logisticsGuide = new Book();
		logisticsGuide.setTitle("Logistics 101");
		logisticsGuide.setDisplayName("Logistics 101");
		logisticsGuide.setAuthor("Zundrel");
		logisticsGuide.setColor(Color.CYAN);
		logisticsGuide.setCategoryList(categories);
		logisticsGuide.setRegistryName(new ResourceLocation(ModInfo.MOD_ID, "logistics_guide"));

		return logisticsGuide;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleModel(ItemStack bookStack) {
		GuideAPI.setModel(logisticsGuide);
	}

	@Override
	public void handlePost(ItemStack bookStack) {

	}

}
