package ru.lionzxy.simlyhammer.utils;

import gregapi.code.ItemStackContainer;
import gregapi.data.OP;
import gregapi.item.ItemBase;
import gregapi.item.MultiItemTool;
import gregapi.item.prefixitem.PrefixItem;
import gregapi.item.prefixitem.PrefixItemProjectile;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import scala.tools.cmd.gen.AnyValReps;

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

    public static boolean isPickaxe(ItemStack is) {
        return is != null && is.getItem() instanceof PrefixItem /* && ((MultiItemTool) is.getItem()).canHarvestBlock(Blocks.stone,is)*/;
    }

    public static void addHammers() {
        for (ItemStackContainer item : OP.toolPickaxe.mRegisteredItems) {
            if (item.mItem instanceof PrefixItem) {
                int color = ((PrefixItem) item.mItem).getColorFromItemStack(item.toStack(), 0);
                //((PrefixItem) item.mItem).getMaterial(item.mMetaData)
                //System.out.print(color);
            }
        }
    }

}
