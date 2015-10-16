package ru.lionzxy.simlyhammer.utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.StatCollector;

/**
 * Created by LionZXY on 08.09.2015.
 * SimplyHammer v0.9
 */
public class CustomAchievement extends Achievement {
    String localizeName;

    public CustomAchievement(String localizeName, String p_i45301_1_, String p_i45301_2_, int p_i45301_3_, int p_i45301_4_, Block p_i45301_5_, Achievement p_i45301_6_) {
        super(p_i45301_1_, p_i45301_2_, p_i45301_3_, p_i45301_4_, new ItemStack(p_i45301_5_), p_i45301_6_);
        this.localizeName = localizeName;
    }

    @SideOnly(Side.CLIENT)
    public String getDescription() {
        return "Craft " + localizeName + " from Simply Hammer!";
    }
}
