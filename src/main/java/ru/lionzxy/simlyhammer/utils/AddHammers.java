package ru.lionzxy.simlyhammer.utils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.config.Config;
import ru.lionzxy.simlyhammer.hammers.BasicHammer;
import ru.lionzxy.simlyhammer.hammers.BoundHammer;
import ru.lionzxy.simlyhammer.hammers.ProspectorsPick;

/**
 * Created by nikit on 30.08.2015.
 */
public class AddHammers {
    public static Item geologHammer = new ProspectorsPick(),BMHammer;

    static public void addAllHammers() {
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
        addHammer("stoneHammer", 1, 1, 1F, 131, "stone", "cobblestone",false);
        addHammer("ironHammer", 1, 2, 6F, 2250, "blockIron", "ingotIron",false);
    }

    static public void addOreDictModHammers() {
        addHammer("bronzeHammer", 1, 2, 6F, 2250, "blockBronze", "ingotBronze",false);
        addHammer("copperHammer", 1, 2, 6F, 512, "blockCopper", "ingotCopper",false);
        addHammer("steelHammer", 1, 2, 4F, 5120, "blockSteel", "ingotSteel",false);
        addHammer("tungstenHammer", 1, 3, 6F, 1100, "blockTungsten", "ingotTungsten",false);
        addHammer("HSLAHammer", 1, 3, 6F, 10240, "RotaryCraft", "rotarycraft_block_deco", "ingotHSLA",false);
        addHammer("unstableHammer", 1, 10, 10F, 10240, "blockUnstable", "ingotUnstable",true);
        addHammer("manaSteelHammer", 1, 3, 6F, 5120, "Botania", "storage", "ingotManasteel",false);
        addHammer("terraSteelHammer", 1, 3, 6F, 20480, "Botania", "storage:1", "ingotTerrasteel",false);
        addHammer("thaumiumHammer", 1, 3, 6F, 10240, "Thaumcraft", "blockCosmeticSolid:4", "ingotThaumium",false);
        if(Loader.isModLoaded("AWWayofTime"))
        addBMHammer("boundHammer", 1, 3, 6F, 1100);
        if (Config.pick) {
            GameRegistry.registerItem(geologHammer, "prospectorsPick");
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(geologHammer),
                    " x ", " yx", "y  ",
                    'x', "stone", // can use ordinary items, blocks, itemstacks in ShapedOreRecipe
                    'y', new ItemStack(Items.stick)// look in OreDictionary for vanilla definitions
            ));
        }

    }


    static void addHammer(String name, int breakRadius, int harvestLevel, float speed, int damage, String modName, String nameid, String repairMaterial,boolean infinity) {
        //if (!name.equalsIgnoreCase("HSLAHammer") || (name.equalsIgnoreCase("HSLAHammer") && Loader.isModLoaded("RotaryCraft"))) {
        if (Config.config.get("general", name, true).getBoolean()) {
            SimplyHammer.hammers.add(new BasicHammer(name,
                    Config.config.get(name, "BreakRadius", breakRadius).getInt(),
                    Config.config.get(name, "HarvestLevel", harvestLevel).getInt(),
                    (float) Config.config.get(name, "Speed", (double) speed).getDouble(),
                    Config.config.get(name, "Durability", damage).getInt(),
                    Config.config.get(name, "AttackDamage", (int) (speed * 1000) / damage).getInt(),
                    Config.config.get(name, "RepairMaterial", repairMaterial).getString(),
                    Config.config.get(name, "Repairable", true).getBoolean(),
                    Config.config.get(name, "GetAchievement", true).getBoolean(),
                    Config.config.get(name, "DiamondModif", true).getBoolean(),
                    Config.config.get(name, "AxeModif", true).getBoolean(),
                    Config.config.get(name, "ShovelModif", true).getBoolean(),
                    Config.config.get(name, "TorchModif", true).getBoolean(),
                        Config.config.get(name, "Infinity", infinity).getBoolean()));
                int thisPos = SimplyHammer.hammers.size() - 1;
            GameRegistry.registerItem(SimplyHammer.hammers.get(thisPos), name);

                addCraft(SimplyHammer.hammers.get(thisPos), name, "ingotIron", modName + ":" + nameid);
            }
        /*} else {
            SimplyHammer.hammers.add(new BasicHammer(name, breakRadius, harvestLevel, speed, damage, (int) (speed * 1000) / damage));
            int thisPos = SimplyHammer.hammers.size() - 1;
            GameRegistry.registerItem(SimplyHammer.hammers.get(thisPos), name);
        }*/
    }

    static void addBMHammer(String name, int breakRadius, int harvestLevel, float speed, int damage) {
            if (Config.config.get("general", name, true).getBoolean()) {
                BMHammer = new BoundHammer(name,
                        Config.config.get(name, "BreakRadius", breakRadius).getInt(),
                        Config.config.get(name, "HarvestLevel", harvestLevel).getInt(),
                        (float) Config.config.get(name, "Speed", (double) speed).getDouble(),
                        Config.config.get(name, "Durability", damage).getInt(),
                        Config.config.get(name, "AttackDamage", (int) (speed * 1000) / damage).getInt(), "ingotIron",
                        Config.config.get(name, "Repairable", false).getBoolean(),
                        Config.config.get(name, "GetAchievement", true).getBoolean(),
                        Config.config.get(name, "DiamondModif", true).getBoolean(),
                        Config.config.get(name, "AxeModif", true).getBoolean(),
                        Config.config.get(name, "ShovelModif", true).getBoolean(),
                        Config.config.get(name, "TorchModif", true).getBoolean());
                GameRegistry.registerItem(BMHammer, name);
            }
    }

    static void addHammer(String name, int breakRadius, int harvestLevel, float speed, int damage, String materialOreDict, String repairMaterial,boolean infinity) {
        if (checkToNotNull(materialOreDict) && checkToNotNull(repairMaterial)) {
            if (Config.config.get("general", name, true).getBoolean()) {
                SimplyHammer.hammers.add(new BasicHammer(name,
                        Config.config.get(name, "BreakRadius", breakRadius).getInt(),
                        Config.config.get(name, "HarvestLevel", harvestLevel).getInt(),
                        (float) Config.config.get(name, "Speed", (double) speed).getDouble(),
                        Config.config.get(name, "Durability", damage).getInt(),
                        Config.config.get(name, "AttackDamage", (int) (speed * 1000) / damage).getInt(),
                        Config.config.get(name, "RepairMaterial", repairMaterial).getString(),
                        Config.config.get(name, "Repairable", true).getBoolean(),
                        Config.config.get(name, "GetAchievement", true).getBoolean(),
                        Config.config.get(name, "DiamondModif", true).getBoolean(),
                        Config.config.get(name, "AxeModif", true).getBoolean(),
                        Config.config.get(name, "ShovelModif", true).getBoolean(),
                        Config.config.get(name, "TorchModif", true).getBoolean(),
                        Config.config.get(name, "Infinity", infinity).getBoolean()));
                int thisPos = SimplyHammer.hammers.size() - 1;
                GameRegistry.registerItem(SimplyHammer.hammers.get(thisPos), name);
                addCraft(SimplyHammer.hammers.get(thisPos), name, "ingotIron", materialOreDict);
            }
        } else {
            SimplyHammer.hammers.add(new BasicHammer(name, breakRadius, harvestLevel, speed, damage, (int) (speed * 1000) / damage));
            int thisPos = SimplyHammer.hammers.size() - 1;
            GameRegistry.registerItem(SimplyHammer.hammers.get(thisPos), name);
        }
    }

    static public void addCraft(Item craftItem, String name, String craftRodt, String material) {
        String craftRod = Config.config.get(name, "CraftRod", craftRodt).getString();
        String craftMaterial = Config.config.get(name, "CraftMaterial", material).getString();
        if (!isOreDict(craftRod))
            if (!isOreDict(craftMaterial))
                GameRegistry.addRecipe(new ItemStack(craftItem),
                        "xxx", "xyx", " y ",
                        'x', new ItemStack(getItemCraft(craftMaterial)),
                        'y', new ItemStack(getItemCraft(craftRod))// look in OreDictionary for vanilla definitions
                );
            else GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(craftItem),
                    "xxx", "xyx", " y ",
                    'x', craftMaterial,
                    'y', new ItemStack(getItemCraft(craftRod))));
        else if (!isOreDict(craftMaterial))
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(craftItem),
                    "xxx", "xyx", " y ",
                    'x', new ItemStack(getItemCraft(craftMaterial)),
                    'y', craftRod));
        else GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(craftItem),
                    "xxx", "xyx", " y ",
                    'x', craftMaterial,
                    'y', craftRod));


    }

    static boolean checkToNotNull(String ore) {
        if (isOreDict(ore))
            return OreDictionary.doesOreNameExist(ore);
        else if (getItemCraft(ore) != null)
            return true;
        return false;
    }

    static boolean isOreDict(String ore) {
        if (ore.indexOf(':') == -1)
            return true;
        return false;
    }

    static Item getItemCraft(String item) {
        return GameRegistry.findItem(item.substring(0, item.indexOf(':')), item.substring(item.indexOf(':') + 1));
    }

}
