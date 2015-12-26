package ru.lionzxy.simlyhammer.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import cpw.mods.fml.common.registry.GameRegistry;
import gregapi.code.ItemStackContainer;
import gregapi.data.OP;
import gregapi.item.ItemBase;
import gregapi.item.MultiItemTool;
import gregapi.item.prefixitem.PrefixItem;
import gregapi.item.prefixitem.PrefixItemProjectile;
import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictMaterial;
import gregapi.util.OM;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import ru.lionzxy.simlyhammer.commons.config.GTJsonArray;
import scala.tools.cmd.gen.AnyValReps;

import java.awt.*;

/**
 * Created by LionZXY on 24.12.2015.
 */
public class GregTechHelper {
    public static CreativeTabs gregTechTab;

    public static boolean isOre(ItemStack is) {
        for (ItemStackContainer item : OP.ore.mRegisteredItems) {
            if (item.mItem == is.getItem())
                return true;
        }
        return false;
    }

    public static void addHammerServer(){
        GTJsonArray.init();
    }

}
