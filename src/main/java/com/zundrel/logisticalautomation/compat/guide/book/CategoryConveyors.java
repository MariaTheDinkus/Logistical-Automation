package com.zundrel.logisticalautomation.compat.guide.book;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageText;
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

public class CategoryConveyors {
    public static Map<ResourceLocation, EntryAbstract> buildCategory() {
               Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
        String keyBase = "guide." + ModInfo.MOD_ID + ".entry.conveyors.";

        List<IPage> introPages = new ArrayList<>();
        introPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "intro" + ".info"), 370));
        entries.put(new ResourceLocation(keyBase + "intro"), new EntryText(introPages, TextHelper.localize(keyBase + "intro"), true));

        List<IPage> normalPages = new ArrayList<>();

        normalPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "normal" + ".info"), 370));

        normalPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_normal)));
        normalPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_fast)));
        normalPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_express)));

        entries.put(new ResourceLocation(keyBase + "normal"), new EntryItemStack(normalPages, TextHelper.localize(keyBase + "normal"), new ItemStack(BlockRegistry.conveyor_normal), true));

        List<IPage> verticalPages = new ArrayList<>();

        verticalPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "vertical" + ".info"), 370));

        verticalPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_vertical_normal)));
        verticalPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_vertical_fast)));
        verticalPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_vertical_express)));

        entries.put(new ResourceLocation(keyBase + "vertical"), new EntryItemStack(verticalPages, TextHelper.localize(keyBase + "vertical"), new ItemStack(BlockRegistry.conveyor_vertical_normal), true));

        List<IPage> stairPages = new ArrayList<>();

        stairPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "stair" + ".info"), 370));

        stairPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_stair_normal)));
        stairPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_stair_fast)));
        stairPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_stair_express)));

        entries.put(new ResourceLocation(keyBase + "stair"), new EntryItemStack(stairPages, TextHelper.localize(keyBase + "stair"), new ItemStack(BlockRegistry.conveyor_stair_normal), true));

        List<IPage> filterPages = new ArrayList<>();

        filterPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "filters" + ".info"), 370));

        filterPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.filter)));

        entries.put(new ResourceLocation(keyBase + "filters"), new EntryItemStack(filterPages, TextHelper.localize(keyBase + "filters"), new ItemStack(BlockRegistry.filter), true));

        List<IPage> alternatorPages = new ArrayList<>();

        alternatorPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "alternators" + ".info"), 370));

        alternatorPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.alternator)));

        entries.put(new ResourceLocation(keyBase + "alternators"), new EntryItemStack(alternatorPages, TextHelper.localize(keyBase + "alternators"), new ItemStack(BlockRegistry.alternator), true));

        List<IPage> junctionPages = new ArrayList<>();

        junctionPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "junctions" + ".info"), 370));

        junctionPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.junction)));

        entries.put(new ResourceLocation(keyBase + "junctions"), new EntryItemStack(junctionPages, TextHelper.localize(keyBase + "junctions"), new ItemStack(BlockRegistry.junction), true));

        List<IPage> splitterPages = new ArrayList<>();

        splitterPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "splitters" + ".info"), 370));

        splitterPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.splitter)));

        entries.put(new ResourceLocation(keyBase + "splitters"), new EntryItemStack(splitterPages, TextHelper.localize(keyBase + "splitters"), new ItemStack(BlockRegistry.splitter), true));

        List<IPage> blowtorchPages = new ArrayList<>();

        blowtorchPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "blowtorches" + ".info"), 370));

        blowtorchPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.blowtorch)));

        entries.put(new ResourceLocation(keyBase + "blowtorches"), new EntryItemStack(blowtorchPages, TextHelper.localize(keyBase + "blowtorches"), new ItemStack(BlockRegistry.blowtorch), true));

        List<IPage> poly_blowtorchPages = new ArrayList<>();

        poly_blowtorchPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "poly_blowtorches" + ".info"), 370));

        poly_blowtorchPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.poly_blowtorch)));

        entries.put(new ResourceLocation(keyBase + "poly_blowtorches"), new EntryItemStack(poly_blowtorchPages, TextHelper.localize(keyBase + "poly_blowtorches"), new ItemStack(BlockRegistry.poly_blowtorch), true));

        List<IPage> gratePages = new ArrayList<>();

        gratePages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "grates" + ".info"), 370));

        gratePages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.grate)));

        entries.put(new ResourceLocation(keyBase + "grates"), new EntryItemStack(gratePages, TextHelper.localize(keyBase + "grates"), new ItemStack(BlockRegistry.grate), true));

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