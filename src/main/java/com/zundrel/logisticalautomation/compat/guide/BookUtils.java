package com.zundrel.logisticalautomation.compat.guide;

import amerifrance.guideapi.page.PageIRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WayOfTime
 * Thanks!
 */
public class BookUtils {
    public static IRecipe getRecipeForOutput(ItemStack stack) {
        for (IRecipe recipe : ForgeRegistries.RECIPES.getValues()) {
            if (recipe != null) {
                ItemStack resultStack = recipe.getRecipeOutput();
                if (!resultStack.isEmpty()) {
                    if (resultStack.getItem() == stack.getItem() && resultStack.getItemDamage() == stack.getItemDamage()) {
                        return recipe;
                    }
                }
            }
        }

        return null;
    }

    public static PageIRecipe getPageForRecipe(IRecipe recipe) {
        return new PageIRecipe(recipe);
    }

    public static PageIRecipe getPageForRecipe(ItemStack stack) { return new PageIRecipe(getRecipeForOutput(stack)); }
}