package ru.lionzxy.simlyhammer.utils;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by LionZXY on 08.09.2015.
 * SimplyHammer v0.9
 */
public class HammerTab extends CreativeTabs {
    public HammerTab(String lable) {
        super(lable);
    }

    @Override
    public Item getTabIconItem() {
        return GameRegistry.findItem("simplyhammer", "stoneHammer");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        return new ItemStack(GameRegistry.findItem("simplyhammer", "stoneHammer"));
    }

}
