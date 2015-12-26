package ru.lionzxy.simlyhammer.utils;

import gregapi.code.ItemStackContainer;
import gregapi.data.OP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import ru.lionzxy.simlyhammer.commons.config.GTJsonArray;

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
