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
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CategoryMachines {
    public static Map<ResourceLocation, EntryAbstract> buildCategory() {
               Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
        String keyBase = "guide." + ModInfo.MOD_ID + ".entry.machines.";

        List<IPage> introPages = new ArrayList<>();
        introPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "intro" + ".info"), 370));
        entries.put(new ResourceLocation(keyBase + "intro"), new EntryText(introPages, TextHelper.localize(keyBase + "intro"), true));

        List<IPage> componentPages = new ArrayList<>();

        componentPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "components" + ".info"), 370));

        componentPages.add(BookUtils.getPageForRecipe(new ItemStack(ItemRegistry.iron_stick)));
        componentPages.add(BookUtils.getPageForRecipe(new ItemStack(ItemRegistry.roller_set)));
        componentPages.add(BookUtils.getPageForRecipe(new ItemStack(ItemRegistry.leather_belt_set)));
        componentPages.add(BookUtils.getPageForRecipe(new ItemStack(ItemRegistry.iron_motor_set)));
        componentPages.add(BookUtils.getPageForRecipe(new ItemStack(ItemRegistry.gold_motor_set)));
        componentPages.add(BookUtils.getPageForRecipe(new ItemStack(ItemRegistry.diamond_motor_set)));

        entries.put(new ResourceLocation(keyBase + "components"), new EntryItemStack(componentPages, TextHelper.localize(keyBase + "components"), new ItemStack(ItemRegistry.iron_motor_set), true));

        List<IPage> conveyorPages = new ArrayList<>();

        conveyorPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "conveyors" + ".info"), 370));

        conveyorPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_normal)));
        conveyorPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_fast)));
        conveyorPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_express)));
        conveyorPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_stair_normal)));
        conveyorPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_stair_fast)));
        conveyorPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_stair_express)));
        conveyorPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_vertical_normal)));
        conveyorPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_vertical_fast)));
        conveyorPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.conveyor_vertical_express)));

        entries.put(new ResourceLocation(keyBase + "conveyors"), new EntryItemStack(conveyorPages, TextHelper.localize(keyBase + "conveyors"), new ItemStack(BlockRegistry.conveyor_normal), true));

        List<IPage> filterPages = new ArrayList<>();

        filterPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "filters" + ".info"), 370));

        filterPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.filter)));

        entries.put(new ResourceLocation(keyBase + "filters"), new EntryItemStack(filterPages, TextHelper.localize(keyBase + "filters"), new ItemStack(BlockRegistry.filter), true));

        List<IPage> splitterPages = new ArrayList<>();

        splitterPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "splitters" + ".info"), 370));

        splitterPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.splitter)));

        entries.put(new ResourceLocation(keyBase + "splitters"), new EntryItemStack(splitterPages, TextHelper.localize(keyBase + "splitters"), new ItemStack(BlockRegistry.splitter), true));

        List<IPage> junctionPages = new ArrayList<>();

        junctionPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "junctions" + ".info"), 370));

        junctionPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.junction)));

        entries.put(new ResourceLocation(keyBase + "junctions"), new EntryItemStack(junctionPages, TextHelper.localize(keyBase + "junctions"), new ItemStack(BlockRegistry.junction), true));

        List<IPage> halverPages = new ArrayList<>();

        halverPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "halvers" + ".info"), 370));

        halverPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.halver)));

        entries.put(new ResourceLocation(keyBase + "halvers"), new EntryItemStack(halverPages, TextHelper.localize(keyBase + "halvers"), new ItemStack(BlockRegistry.halver), true));

        List<IPage> blowtorchPages = new ArrayList<>();

        blowtorchPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "blowtorches" + ".info"), 370));

        blowtorchPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.blowtorch)));
        blowtorchPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.poly_blowtorch)));

        entries.put(new ResourceLocation(keyBase + "blowtorches"), new EntryItemStack(blowtorchPages, TextHelper.localize(keyBase + "blowtorches"), new ItemStack(BlockRegistry.blowtorch), true));

        List<IPage> gratePages = new ArrayList<>();

        gratePages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "grates" + ".info"), 370));

        gratePages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.grate)));

        entries.put(new ResourceLocation(keyBase + "grates"), new EntryItemStack(gratePages, TextHelper.localize(keyBase + "grates"), new ItemStack(BlockRegistry.grate), true));

        List<IPage> catwalkPages = new ArrayList<>();

        catwalkPages.addAll(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "catwalks" + ".info"), 370));

        catwalkPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.catwalk)));
        catwalkPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.catwalk_stairs)));
        catwalkPages.add(BookUtils.getPageForRecipe(new ItemStack(BlockRegistry.catwalk_pillar)));

        entries.put(new ResourceLocation(keyBase + "catwalks"), new EntryItemStack(catwalkPages, TextHelper.localize(keyBase + "catwalks"), new ItemStack(BlockRegistry.catwalk), true));

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