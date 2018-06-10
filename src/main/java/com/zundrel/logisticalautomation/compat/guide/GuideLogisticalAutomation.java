package com.zundrel.logisticalautomation.compat.guide;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.zundrel.logisticalautomation.common.registry.BlockRegistry;
import com.zundrel.logisticalautomation.common.registry.LogisticCreativeTabs;
import com.zundrel.logisticalautomation.compat.guide.book.CategoryMachines;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;

import com.zundrel.logisticalautomation.common.info.ModInfo;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@GuideBook
public class GuideLogisticalAutomation implements IGuideBook {

	public static Book logisticsGuide;

	@Nonnull
	@Override
	public Book buildBook() {
		logisticsGuide = new Book();
		logisticsGuide.setTitle("guide.logisticalautomation.title");
		logisticsGuide.setDisplayName("guide.logisticalautomation.display");
		logisticsGuide.setWelcomeMessage("guide.logisticalautomation.welcome");
		logisticsGuide.setAuthor("guide.logisticalautomation.author");
        logisticsGuide.setRegistryName(new ResourceLocation(ModInfo.MOD_ID, "guide"));
        logisticsGuide.setCreativeTab(LogisticCreativeTabs.LogisticConveyorTab.INSTANCE);
		logisticsGuide.setColor(Color.CYAN);

		return logisticsGuide;
	}

    @Override
    public void handlePost(ItemStack bookStack) {
        logisticsGuide.addCategory(new CategoryItemStack(CategoryMachines.buildCategory(), "guide.logisticalautomation.category.machines", new ItemStack(BlockRegistry.conveyor_normal)));
    }

	@SideOnly(Side.CLIENT)
	@Override
	public void handleModel(ItemStack bookStack) {
		GuideAPI.setModel(logisticsGuide);
	}

    @Nullable
    @Override
    public IRecipe getRecipe(@Nonnull ItemStack bookStack) {
        return new ShapelessOreRecipe(new ResourceLocation(ModInfo.MOD_ID, "guide"), GuideAPI.getStackFromBook(logisticsGuide), new ItemStack(Items.BOOK), "leather", "feather").setRegistryName("logistics_guide");
    }

}
