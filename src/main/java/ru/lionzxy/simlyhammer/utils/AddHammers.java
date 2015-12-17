package ru.lionzxy.simlyhammer.utils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.commons.blocks.Disassembler;
import ru.lionzxy.simlyhammer.commons.config.Config;
import ru.lionzxy.simlyhammer.commons.hammers.*;
import ru.lionzxy.simlyhammer.commons.items.AddItems;

/**
 * Created by LionZXY on 08.09.2015.
 * SimplyHammer v0.9
 */
public class AddHammers {

    public static Item geologHammer = new ProspectorsPick(), BMHammer, CWPHammer, CWPTemporalHammer, IC2Hammer, ARHammer, IIEERRAAHammer, RFHammerv;
    public static Block disassembler;

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
                true, true, true, true, true, true, "Bronze Hammer", "simplyhammer:bronzeHammer", true,
                Ref.MODID + ":textures/models/item.bronzeHammer.png");
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
            IC2EnergyHammer.init();
        }

        if (Loader.isModLoaded("RedstoneArsenal") || Loader.isModLoaded("ThermalExpansion") || Loader.isModLoaded("EnderIO"))
            RFHammer.init();

        disassembler = new Disassembler();
        ARHammer = new Aronil98Hammer();
        IIEERRAAHammer = new IIEERRAAHammer();

        SimplyHammer.hammers.add(IIEERRAAHammer);
        SimplyHammer.hammers.add(ARHammer);


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
