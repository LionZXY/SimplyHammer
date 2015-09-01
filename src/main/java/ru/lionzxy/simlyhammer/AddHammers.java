package ru.lionzxy.simlyhammer;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.lionzxy.simlyhammer.hammers.BasicHammer;
import ru.lionzxy.simlyhammer.hammers.ProspectorsPick;

/**
 * Created by nikit on 30.08.2015.
 */
public class AddHammers {
    static Item geologHammer = new ProspectorsPick();

    static public void addAllHammers() {
        addVanilaHammers();
    }

    /*  WOOD(0, 59, 2.0F, 0.0F, 15),
        STONE(1, 131, 4.0F, 1.0F, 5),
        IRON(2, 250, 6.0F, 2.0F, 14),
        EMERALD(3, 1561, 8.0F, 3.0F, 10),
        GOLD(0, 32, 12.0F, 0.0F, 22);
        BasicHammer(String name,int breakRadius, int harvestLevel,float speed, int damage)*/
    static void addVanilaHammers() {
        addHammer("stoneHammer", 1, 1, 1F, 131, "stone", "cobblestone");
        addHammer("ironHammer", 1, 2, 6F, 2250, "blockIron", "ingotIron");
    }

    static public void addOreDictModHammers() {
        addHammer("bronzeHammer", 1, 2, 6F, 2250, "blockBronze", "ingotBronze");
        addHammer("copperHammer", 1, 2, 6F, 512, "blockCopper", "ingotCopper");
        addHammer("steelHammer", 1, 2, 4F, 5000, "blockSteel", "ingotSteel");
        addHammer("tungstenHammer", 1, 3, 6F, 1100, "blockTungsten", "ingotTungsten");
        addHammer("HSLAHammer", 1, 3, 6F, 10240, GameRegistry.findItem("RotaryCraft", "rotarycraft_item_borecraft"), "ingotHSLA");
        GameRegistry.registerItem(geologHammer, "prospectorsPick");
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(geologHammer),
                    " x ", " yx", "y  ",
                    'x', "stone", // can use ordinary items, blocks, itemstacks in ShapedOreRecipe
                    'y', new ItemStack(Items.stick)// look in OreDictionary for vanilla definitions
            ));

    }

    static void addHammer(String name, int breakRadius, int harvestLevel, float speed, int damage, Item material, Item repairMaterial) {
        SimplyHammer.hammers.add(new BasicHammer(name, breakRadius, harvestLevel, speed, damage, (int) (speed * 1000) / damage, repairMaterial));
        int thisPos = SimplyHammer.hammers.size() - 1;
        GameRegistry.registerItem(SimplyHammer.hammers.get(thisPos), name);
        GameRegistry.addRecipe(new ItemStack(SimplyHammer.hammers.get(thisPos)), "xxx", "xyx", " y ",
                'x', new ItemStack(material),
                'y', new ItemStack(Items.iron_ingot));

    }

    static void addHammer(String name, int breakRadius, int harvestLevel, float speed, int damage, Item material, String repairMaterial) {
        if (!name.equalsIgnoreCase("HSLAHammer") || (name.equalsIgnoreCase("HSLAHammer") && Loader.isModLoaded("RotaryCraft"))) {
            SimplyHammer.hammers.add(new BasicHammer(name, breakRadius, harvestLevel, speed, damage, (int) (speed * 1000) / damage, OreDictionary.getOreID(repairMaterial)));
            int thisPos = SimplyHammer.hammers.size() - 1;
            GameRegistry.registerItem(SimplyHammer.hammers.get(thisPos), name);
            GameRegistry.addRecipe(new ItemStack(SimplyHammer.hammers.get(thisPos)), "xxx", "xyx", " y ",
                    'x', new ItemStack(material),
                    'y', new ItemStack(Items.iron_ingot));
        } else {
            SimplyHammer.hammers.add(new BasicHammer(name, breakRadius, harvestLevel, speed, damage, (int) (speed * 1000) / damage));
            int thisPos = SimplyHammer.hammers.size() - 1;
            GameRegistry.registerItem(SimplyHammer.hammers.get(thisPos), name);
        }
    }

    static void addHammer(String name, int breakRadius, int harvestLevel, float speed, int damage, String materialOreDict, String repairMaterial) {
        if (OreDictionary.doesOreNameExist(materialOreDict) && OreDictionary.doesOreNameExist(repairMaterial)) {
            SimplyHammer.hammers.add(new BasicHammer(name, breakRadius, harvestLevel, speed, damage, (int) (speed * 1000) / damage, OreDictionary.getOreID(repairMaterial)));
            int thisPos = SimplyHammer.hammers.size() - 1;
            GameRegistry.registerItem(SimplyHammer.hammers.get(thisPos), name);
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(SimplyHammer.hammers.get(thisPos)),
                    "xxx", "xyx", " y ",
                    'x', materialOreDict, // can use ordinary items, blocks, itemstacks in ShapedOreRecipe
                    'y', new ItemStack(Items.iron_ingot)// look in OreDictionary for vanilla definitions
            ));
        } else {
            SimplyHammer.hammers.add(new BasicHammer(name, breakRadius, harvestLevel, speed, damage, (int) (speed * 1000) / damage));
            int thisPos = SimplyHammer.hammers.size() - 1;
            GameRegistry.registerItem(SimplyHammer.hammers.get(thisPos), name);
        }
    }


}
