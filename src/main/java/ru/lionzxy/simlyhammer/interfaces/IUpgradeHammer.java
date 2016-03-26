package ru.lionzxy.simlyhammer.interfaces;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

/**
 * Created by LionZXY on 26.03.16.
 * SimplyHammer
 */
public interface IUpgradeHammer {

    void upgradeHammer(InventoryCrafting ic, ItemStack hammer, ItemStack itemFound);
}
