package ru.lionzxy.simlyhammer.utils;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.config.Config;
import ru.lionzxy.simlyhammer.hammers.*;
import ru.lionzxy.simlyhammer.libs.HammerSettings;

/**
 * Created by nikit on 30.08.2015.
 */
public class AddHammers {

    public static Item geologHammer = new ProspectorsPick(), BMHammer, CWPHammer, CWPTemporalHammer;

    static public void addAllHammers() {
        FMLLog.bigWarning("reg7");
        addVanilaHammers();
        addOreDictModHammers();
        Config.config.save();
    }

    /*  WOOD(0, 59, 2.0F, 0.0F, 15),
        STONE(1, 131, 4.0F, 1.0F, 5),
        IRON(2, 250, 6.0F, 2.0F, 14),
        EMERALD(3, 1561, 8.0F, 3.0F, 10),
        GOLD(0, 32, 12.0F, 0.0F, 22);
        BasicHammer(String name,int breakRadius, int harvestLevel,float speed, int damage)*/
    static void addVanilaHammers() {
        addHammer("stoneHammer", 1, 1, 1F, 131, "stone", "cobblestone", false);
        addHammer("ironHammer", 1, 2, 6F, 2250, "blockIron", "ingotIron", false);
        new Aronil98Hammer();
    }

    static public void addOreDictModHammers() {
        addHammer("bronzeHammer", 1, 2, 6F, 2250, "blockBronze", "ingotBronze", false);
        addHammer("copperHammer", 1, 2, 6F, 512, "blockCopper", "ingotCopper", false);
        addHammer("steelHammer", 1, 2, 4F, 5120, "blockSteel", "ingotSteel", false);
        addHammer("tungstenHammer", 1, 3, 6F, 1100, "blockTungsten", "ingotTungsten", false);
        addHammer("HSLAHammer", 1, 3, 6F, 10240, "RotaryCraft", "rotarycraft_block_deco", "ingotHSLA", false);
        addHammer("unstableHammer", 1, 10, 10F, 10240, "blockUnstable", "ingotUnstable", true);
        addHammer("manaSteelHammer", 1, 3, 6F, 5120, "Botania", "storage", "ingotManasteel", false);
        addHammer("terraSteelHammer", 1, 3, 6F, 20480, "Botania", "storage:1", "ingotTerrasteel", false);
        addHammer("thaumiumHammer", 1, 3, 6F, 10240, "Thaumcraft", "blockCosmeticSolid:4", "ingotThaumium", false);
        FMLLog.bigWarning("reg4");
        if (Loader.isModLoaded("AWWayofTime"))
            BoundHammer.addBMHammer("boundHammer", 1, 3, 6F, 1100);
        if (Loader.isModLoaded("clockworkphase"))
            ClockWorkPhaseHammer.addCWPHammer("clockworkHammer", 1, 3, 6F, 1024);
        if (Config.pick) {
            GameRegistry.registerItem(geologHammer, "prospectorsPick");
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(geologHammer),
                    " x ", " yx", "y  ",
                    'x', "stone", // can use ordinary items, blocks, itemstacks in ShapedOreRecipe
                    'y', new ItemStack(Items.stick)// look in OreDictionary for vanilla definitions
            ));
        }

    }


    static void addHammer(String name, int breakRadius, int harvestLevel, float speed, int damage, String modName, String nameid, String repairMaterial, boolean infinity) {
        //if (!name.equalsIgnoreCase("HSLAHammer") || (name.equalsIgnoreCase("HSLAHammer") && Loader.isModLoaded("RotaryCraft"))) {
        if (Config.config.get("general", name, true).getBoolean()) {
            new BasicHammer(new HammerSettings(name, breakRadius, harvestLevel, speed, damage, repairMaterial, infinity).registerHammer(true));
            int thisPos = SimplyHammer.hammers.size() - 1;
            if (Loader.isModLoaded(modName))
                addCraft(SimplyHammer.hammers.get(thisPos), name, "ingotIron", modName + ":" + nameid);
        }
        /*} else {
            SimplyHammer.hammers.add(new BasicHammer(name, breakRadius, harvestLevel, speed, damage, (int) (speed * 1000) / damage));
            int thisPos = SimplyHammer.hammers.size() - 1;
            GameRegistry.registerItem(SimplyHammer.hammers.get(thisPos), name);
        }*/
    }


    static void addHammer(String name, int breakRadius, int harvestLevel, float speed, int damage, String materialOreDict, String repairMaterial, boolean infinity) {
        if (checkToNotNull(materialOreDict) && checkToNotNull(repairMaterial)) {
            if (Config.config.get("general", name, true).getBoolean()) {
                new BasicHammer(new HammerSettings(name, breakRadius, harvestLevel, speed, damage, repairMaterial, infinity).registerHammer(true));
                int thisPos = SimplyHammer.hammers.size() - 1;
                addCraft(SimplyHammer.hammers.get(thisPos), name, "ingotIron", materialOreDict);
            }
        } else {
            new BasicHammer(new HammerSettings(name, breakRadius, harvestLevel, speed, damage, repairMaterial, infinity).registerHammer(true));
        }
    }

    static public void addCraft(Item craftItem, String name, String craftRodt, String material) {
        String craftRod = Config.config.get(name, "CraftRod", craftRodt).getString();
        String craftMaterial = Config.config.get(name, "CraftMaterial", material).getString();
        if (!isOreDict(craftRod))
            if (!isOreDict(craftMaterial))
                GameRegistry.addRecipe(new ItemStack(craftItem),
                        "xxx", "xyx", " y ",
                        'x', HammerUtils.getItemFromString(craftMaterial),
                        'y', HammerUtils.getItemFromString(craftRod)// look in OreDictionary for vanilla definitions
                );
            else GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(craftItem),
                    "xxx", "xyx", " y ",
                    'x', craftMaterial,
                    'y', HammerUtils.getItemFromString(craftRod)));
        else if (!isOreDict(craftMaterial))
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(craftItem),
                    "xxx", "xyx", " y ",
                    'x', HammerUtils.getItemFromString(craftMaterial),
                    'y', craftRod));
        else GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(craftItem),
                    "xxx", "xyx", " y ",
                    'x', craftMaterial,
                    'y', craftRod));


    }

    static boolean checkToNotNull(String ore) {
        return isOreDict(ore) ? OreDictionary.doesOreNameExist(ore) : HammerUtils.getItemFromString(ore) != null;
    }

    static boolean isOreDict(String ore) {
        return ore.indexOf(':') == -1;
    }


}
