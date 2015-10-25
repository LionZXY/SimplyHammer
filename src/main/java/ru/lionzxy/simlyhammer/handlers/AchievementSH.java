package ru.lionzxy.simlyhammer.handlers;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.BlockEvent;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.config.Config;
import ru.lionzxy.simlyhammer.hammers.Aronil98Hammer;
import ru.lionzxy.simlyhammer.interfaces.IModifiHammer;
import ru.lionzxy.simlyhammer.interfaces.ITrash;
import ru.lionzxy.simlyhammer.interfaces.IVacuum;
import ru.lionzxy.simlyhammer.items.AutoSmeltItem;
import ru.lionzxy.simlyhammer.items.TrashItem;
import ru.lionzxy.simlyhammer.items.VacuumItem;
import ru.lionzxy.simlyhammer.libs.HammerSettings;

/**
 * Created by nikit on 03.09.2015.
 */
public class AchievementSH {
    public static Achievement firstDig, placeBlock, firstResearch, firstUpgrade;

    @SubscribeEvent
    public void onBreak(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
        if (event != null && event.entityPlayer != null && event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.getCurrentEquippedItem().getItem() instanceof Aronil98Hammer)
            if (!Aronil98Hammer.isPlayerAutor(event.entityPlayer))
                event.newSpeed = 0.0F;
    }

    @SubscribeEvent
    public void onCrafting(PlayerEvent.ItemCraftedEvent event) {
        if(event.crafting == null)
            return;
        if (event.crafting.getItem() instanceof IModifiHammer && event.crafting.hasTagCompound()) {
            if (event.crafting.getTagCompound().getBoolean("Modif"))
                event.player.addStat(firstUpgrade, 1);}
        if (event.crafting.getItem() instanceof Aronil98Hammer && !Aronil98Hammer.isPlayerAutor(event.player)){
            event.crafting.func_150996_a(Item.getItemFromBlock(Blocks.diamond_block));
            event.player.addChatComponentMessage(new ChatComponentText("This can craft only author mod"));}

        for (int i = 0; i < SimplyHammer.hammers.size(); i++)
            if (event.crafting.getItem() == SimplyHammer.hammers.get(i)) {
                event.player.addStat(SimplyHammer.achievements.get(i), 1);
                return;
            }
    }

    @SubscribeEvent
    public void onPickUp(EntityItemPickupEvent event) {
        if (Config.MCheckTrash)
            for (int i = 0; i < event.entityPlayer.inventory.getSizeInventory(); i++)
                if (event.entityPlayer.inventory.getStackInSlot(i) != null && (event.entityPlayer.inventory.getStackInSlot(i).getItem() instanceof TrashItem || event.entityPlayer.inventory.getStackInSlot(i).getItem() instanceof IModifiHammer)) {
                    if (event.item != null && TrashItem.isTrash(event.item.getEntityItem(), event.entityPlayer.inventory.getStackInSlot(i)))
                        event.item.getEntityItem().stackSize = 0;
                }

    }

    @SubscribeEvent
    public void onHarvestDrop(BlockEvent.HarvestDropsEvent event) {
        if (event.harvester == null || event.harvester.getCurrentEquippedItem() == null)
            return;
        if (event.harvester.getCurrentEquippedItem().getItem() instanceof ITrash) {
            TrashItem.removeTrash(event.drops, event.harvester.getCurrentEquippedItem());
        }

        if (event.harvester.getCurrentEquippedItem() != null) {
            if (event.harvester.getCurrentEquippedItem().getItem() instanceof IModifiHammer &&
                    HammerSettings.isSmelt(event.harvester.getCurrentEquippedItem()))
                for (int i = 0; i < event.drops.size(); i++) {
                    ItemStack drop = AutoSmeltItem.getSmelt(event.drops.get(i), event.harvester.getCurrentEquippedItem());
                    event.drops.remove(i);
                    event.drops.add(i, drop);
                }
            if (event.harvester.getCurrentEquippedItem().getItem() instanceof IVacuum &&
                    HammerSettings.isVacuum(event.harvester.getCurrentEquippedItem())) {
                for (int k = 0; k < event.drops.size(); k++)
                    if (event.harvester.inventory.addItemStackToInventory(event.drops.get(k)))
                        event.drops.remove(k);
            } else if (Config.MCheckVacuum)
                for (int i = 0; i < event.harvester.inventory.getSizeInventory(); i++)
                    if (event.harvester.inventory.getStackInSlot(i) != null && (
                            event.harvester.inventory.getStackInSlot(i).getItem() instanceof VacuumItem || (
                                    event.harvester.inventory.getStackInSlot(i).getItem() instanceof IModifiHammer &&
                                            HammerSettings.isVacuum(event.harvester.inventory.getStackInSlot(i)))))
                        for (int k = 0; k < event.drops.size(); k++)
                            if (!TrashItem.isTrash(event.drops.get(k), event.harvester.inventory.getStackInSlot(i))) {
                                if (event.harvester.inventory.addItemStackToInventory(event.drops.get(k)))
                                    event.drops.remove(k);
                            } else event.drops.remove(k);
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
        try {
            for (int i = 0; i < SimplyHammer.achievements.size(); i++)
                if (((IModifiHammer) SimplyHammer.hammers.get(i)).getHammerSettings().isAchive())
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
        } catch (Exception e) {
            FMLLog.bigWarning("ACHIVEMENT DON'T ADD!!!");
            e.printStackTrace();
        }
        Config.config.save();

    }

}
