package com.zundrel.logisticalautomation.compat.guide;

import java.awt.Color;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.common.registry.LogisticCreativeTabs;
import com.zundrel.logisticalautomation.compat.guide.book.CategoryComponents;
import com.zundrel.logisticalautomation.compat.guide.book.CategoryConveyors;
import com.zundrel.logisticalautomation.compat.guide.book.CategoryDecor;
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
import amerifrance.guideapi.api.impl.Book;
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
		logisticsGuide.addCategory(new CategoryItemStack(CategoryComponents.buildCategory(), "guide.logisticalautomation.category.components", new ItemStack(LogisticalAutomation.Items.logistic_wrench)));
        logisticsGuide.addCategory(new CategoryItemStack(CategoryConveyors.buildCategory(), "guide.logisticalautomation.category.conveyors", new ItemStack(LogisticalAutomation.Blocks.conveyor_normal)));
		logisticsGuide.addCategory(new CategoryItemStack(CategoryMachines.buildCategory(), "guide.logisticalautomation.category.machines", new ItemStack(Blocks.BARRIER)));
		logisticsGuide.addCategory(new CategoryItemStack(CategoryDecor.buildCategory(), "guide.logisticalautomation.category.decor", new ItemStack(LogisticalAutomation.Blocks.catwalk)));
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
