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

public class CategoryDecor {
    public static Map<ResourceLocation, EntryAbstract> buildCategory() {
               Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
        String keyBase = "guide." + ModInfo.MOD_ID + ".entry.decor.";

        List<IPage> introPages = new ArrayList<>();
        introPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "intro" + ".info"), 370));
        entries.put(new ResourceLocation(keyBase + "intro"), new EntryText(introPages, TextHelper.localize(keyBase + "intro"), true));

        List<IPage> catwalkPages = new ArrayList<>();

        catwalkPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "catwalks" + ".info"), 370));

        catwalkPages.add(BookUtils.getPageForRecipe(new ItemStack(LogisticalAutomation.Blocks.catwalk)));

        entries.put(new ResourceLocation(keyBase + "catwalks"), new EntryItemStack(catwalkPages, TextHelper.localize(keyBase + "catwalks"), new ItemStack(LogisticalAutomation.Blocks.catwalk), true));
        
        List<IPage> stairPages = new ArrayList<>();

        stairPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "catwalk_stairs" + ".info"), 370));

        stairPages.add(BookUtils.getPageForRecipe(new ItemStack(LogisticalAutomation.Blocks.catwalk_stairs)));

        entries.put(new ResourceLocation(keyBase + "catwalk_stairs"), new EntryItemStack(stairPages, TextHelper.localize(keyBase + "catwalk_stairs"), new ItemStack(LogisticalAutomation.Blocks.catwalk_stairs), true));

        List<IPage> pillarPages = new ArrayList<>();

        pillarPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "catwalk_pillars" + ".info"), 370));

        pillarPages.add(BookUtils.getPageForRecipe(new ItemStack(LogisticalAutomation.Blocks.catwalk_pillar)));

        entries.put(new ResourceLocation(keyBase + "catwalk_pillars"), new EntryItemStack(pillarPages, TextHelper.localize(keyBase + "catwalk_pillars"), new ItemStack(LogisticalAutomation.Blocks.catwalk_pillar), true));

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