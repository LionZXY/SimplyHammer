package ru.lionzxy.simlyhammer.utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.config.Config;
import ru.lionzxy.simlyhammer.interfaces.IModifiHammer;

/**
 * Created by nikit on 03.09.2015.
 */
public class AchievementSH {
    public static Achievement firstDig, placeBlock, firstResearch, firstUpgrade;

    @SubscribeEvent
    public void onCrafting(PlayerEvent.ItemCraftedEvent event) {
        if(event.crafting.getItem() instanceof IModifiHammer)if(event.crafting.hasTagCompound())
                if(event.crafting.getTagCompound().getBoolean("Modif"))
                    event.player.addStat(firstUpgrade,1);
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
            if (((IModifiHammer) SimplyHammer.hammers.get(i)).isIsAchiv())
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

        if (Config.config.get("general", "AchievementModification", true).getBoolean())
            placeBlock = new Achievement("achievement.Modification",
                    "Modification",
                    0, 0, Items.diamond, (Achievement) null).registerStat();

        Config.config.save();

    }

}
