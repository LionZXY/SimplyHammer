package ru.lionzxy.simlyhammer.utils;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by nikit on 08.09.2015.
 */
public class HammerUtils {
    public static Item openBlocksDevNull;

    public static void init() {
        openBlocksDevNull = GameRegistry.findItem("OpenBlocks", "devnull");
    }

    public static ItemStack getItemFromString(String item) {
        String fromSplit[] = item.split(":");
        return fromSplit.length == 2 ? new ItemStack(GameRegistry.findItem(fromSplit[0], fromSplit[1])) : new ItemStack(GameRegistry.findItem(fromSplit[0], fromSplit[1]), 1, Integer.parseInt(fromSplit[2]));
    }
}
