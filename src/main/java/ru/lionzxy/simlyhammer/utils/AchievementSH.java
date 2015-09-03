package ru.lionzxy.simlyhammer.utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.config.Config;
import ru.lionzxy.simlyhammer.hammers.BasicHammer;

/**
 * Created by nikit on 03.09.2015.
 */
public class AchievementSH {
    public static Achievement firstDig, placeBlock, firstResearch;

    @SubscribeEvent
    public void onCrafting(PlayerEvent.ItemCraftedEvent event) {
        for (int i = 0; i < SimplyHammer.hammers.size(); i++)
            if (event.crafting.getItem() == SimplyHammer.hammers.get(i)) {
                event.player.addStat(SimplyHammer.achievements.get(i), 1);
                return;
            }
    }

    public static void addAchivement() {
        ItemStack thisItem;
        for (int i = 0; i < SimplyHammer.hammers.size(); i++) {
            thisItem = new ItemStack(SimplyHammer.hammers.get(i));
            SimplyHammer.achievements.add(new Achievement("achievement.craft." + thisItem.getItem().getUnlocalizedName(),
                    "craft." + thisItem.getItem().getUnlocalizedName(),
                    0, 0, thisItem.getItem(), null));
        }
        for (int i = 0; i < SimplyHammer.achievements.size(); i++)
            if (((BasicHammer) SimplyHammer.hammers.get(i)).isAchiv)
                SimplyHammer.achievements.get(i).registerStat();
        if (Config.config.get("general", "AchievementFirstBreak", true).getBoolean())
            firstDig = new Achievement("achievement.firstBreak",
                    "firstBreak",
                    0, 0, SimplyHammer.hammers.get(0), (Achievement) null).registerStat();

        if (Config.config.get("general", "AchievementPlaceBlock", true).getBoolean())
            placeBlock = new Achievement("achievement.placeBlock",
                    "placeBlock",
                    0, 0, Blocks.cobblestone, (Achievement) null).registerStat();

        if (Config.config.get("prospectorsPick", "Achievement", true).getBoolean())
            firstResearch = new Achievement("achievement.firstResearch",
                    "firstResearch",
                    0, 0, SimplyHammer.hammers.get(SimplyHammer.hammers.size() - 1), (Achievement) null).registerStat();


    }

}
