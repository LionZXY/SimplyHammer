package ru.lionzxy.simlyhammer.utils;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IC2Items;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.lionzxy.simlyhammer.config.Config;
import ru.lionzxy.simlyhammer.hammers.*;
import ru.lionzxy.simlyhammer.items.AddItems;
import ru.lionzxy.simlyhammer.items.Ductape;
import ru.lionzxy.simlyhammer.items.Stick;
import ru.lionzxy.simlyhammer.recipe.IC2DrillCrafting;

/**
 * Created by LionZXY on 08.09.2015.
 * SimplyHammer v0.9
 */
public class AddHammers {

    public static Item geologHammer = new ProspectorsPick(), BMHammer, CWPHammer, CWPTemporalHammer, IC2Hammer, ARHammer;

    static public void addAllHammers() {
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

        CustomHammers.addHammer("bronzeHammer", 1, 2, 6, 2250, 5, 5, "blockBronze", "ingotBronze", false, true, true, true,
                true, true, true, true, true, true, "Bronze Hammer", "simplyhammer:bronzeHammer");
        CustomHammers.addHammer("stoneHammer", 1, 1, 2, 131, "stone", "cobblestone", false);
        CustomHammers.addHammer("ironHammer", 1, 2, 6, 2250, "blockIron", "ingotIron", false);
        CustomHammers.addHammer("copperHammer", 1, 2, 6, 512, "blockCopper", "ingotCopper", false);
        CustomHammers.addHammer("steelHammer", 1, 3, 6, 5120, "blockSteel", "ingotSteel", false);
        CustomHammers.addHammer("tungstenHammer", 1, 3, 6, 1100, "blockTungsten", "ingotTungsten", false);
        CustomHammers.addHammer("HSLAHammer", 1, 3, 6, 2250, "RotaryCraft:rotarycraft_block_deco", "ingotHSLA", false);
        CustomHammers.addHammer("unstableHammer", 1, 10, 10, 10240, "blockUnstable", "ingotUnstable", true);
        CustomHammers.addHammer("manaSteelHammer", 1, 3, 6, 2048, "Botania:storage", "ingotManasteel", false);
        CustomHammers.addHammer("terraSteelHammer", 1, 3, 6, 20480, "Botania:storage:1", "ingotTerrasteel", false);
        CustomHammers.addHammer("thaumiumHammer", 1, 3, 6, 2250, "Thaumcraft:blockCosmeticSolid:4", "ingotThaumium", false);
    }

    static public void addOreDictModHammers() {
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
        if (Loader.isModLoaded("IC2")) {
            FMLLog.info("Register oredict drill");
            try {
                OreDictionary.registerOre("drillEu", IC2Items.getItem("miningDrill").getItem());
                OreDictionary.registerOre("drillEu", IC2Items.getItem("diamondDrill").getItem());
                OreDictionary.registerOre("drillEu", IC2Items.getItem("iridiumDrill").getItem());
                OreDictionary.registerOre("ingotSteel", IC2Items.getItem("advIronIngot").getItem());
                OreDictionary.registerOre("blockSteel", IC2Items.getItem("advironblock").getItem());
            } catch (NullPointerException e) {
                FMLLog.bigWarning("[SimplyHammers] NOT FOUND SOME IC2 ITEMS. EU Hammer not crafting!!!");
                e.printStackTrace();
            }
            IC2Hammer = new IC2EnergyHammer();
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(IC2Hammer),
                    "xyx", "zpz", " k ",
                    'x', "blockSteel",
                    'y', "drillEu",
                    'z', "ingotSteel",
                    'p', IC2Items.getItem("mfeUnit"),
                    'k', new ItemStack(AddItems.stick, 1, 0)
            ));
            GameRegistry.addRecipe(new IC2DrillCrafting());
        }
        new RFHammer();
        ARHammer = new Aronil98Hammer();

    }

    static public void addCraft(Item craftItem, String craftRodt, String material, String materialSimply) {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(craftItem),
                "zzz", "xpx", " y ",
                'x', isOreDict(material) ? material : HammerUtils.getItemFromString(material),
                'p', new ItemStack(AddItems.stick, 1, 1),
                'y', new ItemStack(AddItems.stick, 1, 0),
                'z', isOreDict(materialSimply) ? materialSimply : HammerUtils.getItemFromString(materialSimply)));


    }

    static boolean checkToNotNull(String ore) {
        return isOreDict(ore) ? OreDictionary.doesOreNameExist(ore) : HammerUtils.getItemFromString(ore) != null;
    }

    static boolean isOreDict(String ore) {
        return ore.indexOf(':') == -1;
    }


}
