package com.zundrel.logisticalautomation.compat.guide.book;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageText;
import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.common.info.ModInfo;
import com.zundrel.logisticalautomation.common.registry.BlockRegistry;
import com.zundrel.logisticalautomation.common.registry.ItemRegistry;
import com.zundrel.logisticalautomation.compat.guide.BookUtils;
import com.zundrel.logisticalautomation.compat.guide.entry.EntryText;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CategoryComponents {
    public static Map<ResourceLocation, EntryAbstract> buildCategory() {
               Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
        String keyBase = "guide." + ModInfo.MOD_ID + ".entry.components.";

        List<IPage> introPages = new ArrayList<>();
        introPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "intro" + ".info"), 370));
        entries.put(new ResourceLocation(keyBase + "intro"), new EntryText(introPages, TextHelper.localize(keyBase + "intro"), true));

        List<IPage> wrenchPages = new ArrayList<>();

        wrenchPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "logistic_wrench" + ".info"), 370));

        wrenchPages.add(BookUtils.getPageForRecipe(new ItemStack(LogisticalAutomation.Items.logistic_wrench)));

        entries.put(new ResourceLocation(keyBase + "logistic_wrench"), new EntryItemStack(wrenchPages, TextHelper.localize(keyBase + "logistic_wrench"), new ItemStack(LogisticalAutomation.Items.logistic_wrench), true));

        List<IPage> stickPages = new ArrayList<>();

        stickPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "iron_sticks" + ".info"), 370));

        stickPages.add(BookUtils.getPageForRecipe(new ItemStack(LogisticalAutomation.Items.iron_stick)));

        entries.put(new ResourceLocation(keyBase + "iron_sticks"), new EntryItemStack(stickPages, TextHelper.localize(keyBase + "iron_sticks"), new ItemStack(LogisticalAutomation.Items.iron_stick), true));

        List<IPage> motorPages = new ArrayList<>();

        motorPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "motors" + ".info"), 370));

        motorPages.add(BookUtils.getPageForRecipe(new ItemStack(LogisticalAutomation.Items.iron_motor_set)));
        motorPages.add(BookUtils.getPageForRecipe(new ItemStack(LogisticalAutomation.Items.gold_motor_set)));
        motorPages.add(BookUtils.getPageForRecipe(new ItemStack(LogisticalAutomation.Items.diamond_motor_set)));

        entries.put(new ResourceLocation(keyBase + "motors"), new EntryItemStack(motorPages, TextHelper.localize(keyBase + "motors"), new ItemStack(LogisticalAutomation.Items.iron_motor_set), true));

        List<IPage> leatherPages = new ArrayList<>();

        leatherPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "leather_belts" + ".info"), 370));

        leatherPages.add(BookUtils.getPageForRecipe(new ItemStack(LogisticalAutomation.Items.leather_belt_set)));

        entries.put(new ResourceLocation(keyBase + "leather_belts"), new EntryItemStack(leatherPages, TextHelper.localize(keyBase + "leather_belts"), new ItemStack(LogisticalAutomation.Items.leather_belt_set), true));

        for (Entry<ResourceLocation, EntryAbstract> entry : entries.entrySet()) {
            for (IPage page : entry.getValue().pageList) {
                if (page instanceof PageText) {
                    ((PageText) page).setUnicodeFlag(true);
                }
            }
        }

        return entries;
    }
}