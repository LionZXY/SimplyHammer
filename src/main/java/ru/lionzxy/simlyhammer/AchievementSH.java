package ru.lionzxy.simlyhammer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

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
                    0, 0, thisItem.getItem(), null).registerStat());
        }
        firstDig = new Achievement("achievement.firstBreak",
                "firstBreak",
                0, 0, SimplyHammer.hammers.get(0), (Achievement) null).registerStat();

        placeBlock = new Achievement("achievement.placeBlock",
                "placeBlock",
                0, 0, Blocks.cobblestone, (Achievement) null).registerStat();

        firstResearch = new Achievement("achievement.firstResearch",
                "firstResearch",
                0, 0, SimplyHammer.hammers.get(SimplyHammer.hammers.size() - 1), (Achievement) null).registerStat();


    }

}
