package ru.lionzxy.simlyhammer.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.config.Config;
import ru.lionzxy.simlyhammer.hammers.BasicHammer;
import ru.lionzxy.simlyhammer.interfaces.IModifiHammer;
import ru.lionzxy.simlyhammer.libs.HammerSettings;

import java.io.*;

/**
 * Created by nikit on 06.09.2015.
 */
public class CustomHammers {
    //public HammerSettings(String name, int breakRadius, int harvestLevel, float speed, int damage, String rm, boolean infinity,
    //boolean repair, boolean isAchive, boolean mDiamond, boolean mAxe, boolean mShovel, boolean mTorch,
    //boolean mTrash, boolean mVacuum, boolean mSmelt, int damageVsEntity, int enchantobility) {

    static File jsonFile = new File(Loader.instance().getConfigDir() + "/SimplyHammers", "Hammers.json");
    public static JsonArray mainJson = new JsonArray();

    static public void addCustomHammers() {
        try {
            try {
                try {
                    if (!jsonFile.canWrite()) {
                        jsonFile.getParentFile().mkdirs();
                        jsonFile.createNewFile();
                        FileOutputStream os = new FileOutputStream(jsonFile);
                        os.write(STARTJSON.getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mainJson = new JsonParser().parse(new JsonReader(new FileReader(jsonFile))).getAsJsonArray();
                for (JsonElement obj2 : mainJson) {
                    JsonObject obj = obj2.getAsJsonObject();
                    SimplyHammer.hammers.add(
                            new BasicHammer(new HammerSettings(obj.get("name").getAsString(),
                                    obj.get("BreakRadius") == null ? 1 : obj.get("BreakRadius").getAsInt(),
                                    obj.get("HarvestLevel") == null ? 2 : obj.get("HarvestLevel").getAsInt(),
                                    obj.get("Speed") == null ? 1F : obj.get("HarvestLevel").getAsFloat(),
                                    obj.get("Durability") == null ? 2000 : obj.get("Durability").getAsInt(),
                                    obj.get("RepairMaterial") == null ? "ingotIron" : obj.get("RepairMaterial").getAsString(),
                                    obj.get("Infinity") != null && obj.get("Infinity").getAsBoolean(),
                                    obj.get("Repairable") == null ? true : obj.get("Repairable").getAsBoolean(),
                                    obj.get("GetAchievement") == null ? true : obj.get("GetAchievement").getAsBoolean(),
                                    obj.get("DiamondModif") == null ? true : obj.get("DiamondModif").getAsBoolean(),
                                    obj.get("AxeModif") == null ? true : obj.get("AxeModif").getAsBoolean(),
                                    obj.get("ShovelModif") == null ? true : obj.get("ShovelModif").getAsBoolean(),
                                    obj.get("TorchModif") == null ? true : obj.get("TorchModif").getAsBoolean(),
                                    obj.get("TrashModif") == null ? true : obj.get("TrashModif").getAsBoolean(),
                                    obj.get("VacuumModif") == null ? true : obj.get("VacuumModif").getAsBoolean(),
                                    obj.get("SmeltModif") == null ? true : obj.get("SmeltModif").getAsBoolean(),
                                    obj.get("AttackDamage") == null ? 1 : obj.get("AttackDamage").getAsInt(),
                                    obj.get("Enchant") == null ? 1 : obj.get("Enchant").getAsInt()).registerHammer(false)));
                    int thisPos = SimplyHammer.hammers.size() - 1;
                    if (obj.get("LocalizeName") != null)
                        ((IModifiHammer) SimplyHammer.hammers.get(thisPos)).getHammerSettings().setLocalizeName(obj.get("LocalizeName").getAsString());
                    if (obj.get("TexturePath") != null)
                        SimplyHammer.hammers.get(thisPos).setTextureName(obj.get("TexturePath").getAsString());
                    AddHammers.addCraft(SimplyHammer.hammers.get(thisPos), obj.get("name").getAsString(), "ingotIron", obj.get("CraftMaterial") == null ? "blockIron" : obj.get("CraftMaterial").getAsString());
                    System.out.println("Add hammer!" + new ItemStack(SimplyHammer.hammers.get(thisPos)).getDisplayName());
                }
            } catch (FileNotFoundException e) {
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    static final String STARTJSON= "[\n" +
            "  {\n" +
            "    \"name\": \"bronzeHammer\",\n" +
            "    \"BreakRadius\": 1,\n" +
            "    \"HarvestLevel\": 2,\n" +
            "    \"Speed\": 6,\n" +
            "    \"Durability\": 2250,\n" +
            "    \"AttackDamage\": 5,\n" +
            "    \"Enchant\": 5,\n" +
            "    \"RepairMaterial\": \"ingotBronze\",\n" +
            "    \"CraftMaterial\": \"blockBronze\",\n" +
            "    \"Infinity\": false,\n" +
            "    \"Repairable\": true,\n" +
            "    \"GetAchievement\": true,\n" +
            "    \"DiamondModif\": true,\n" +
            "    \"AxeModif\": true,\n" +
            "    \"ShovelModif\": true,\n" +
            "    \"TorchModif\": true,\n" +
            "    \"TrashModif\": true,\n" +
            "    \"VacuumModif\": true,\n" +
            "    \"SmeltModif\": true,\n" +
            "    \"TexturePath\": \"simplyhammer:bronzeHammer\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"stoneHammer\",\n" +
            "    \"BreakRadius\": 1,\n" +
            "    \"HarvestLevel\": 1,\n" +
            "    \"Speed\": 2,\n" +
            "    \"Durability\": 131,\n" +
            "    \"AttackDamage\": 5,\n" +
            "    \"Enchant\": 5,\n" +
            "    \"RepairMaterial\": \"cobblestone\",\n" +
            "    \"CraftMaterial\": \"stone\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"ironHammer\",\n" +
            "    \"BreakRadius\": 1,\n" +
            "    \"HarvestLevel\": 2,\n" +
            "    \"Speed\": 6,\n" +
            "    \"Durability\": 2250,\n" +
            "    \"AttackDamage\": 5,\n" +
            "    \"Enchant\": 5,\n" +
            "    \"RepairMaterial\": \"ingotIron\",\n" +
            "    \"CraftMaterial\": \"blockIron\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"copperHammer\",\n" +
            "    \"BreakRadius\": 1,\n" +
            "    \"HarvestLevel\": 2,\n" +
            "    \"Speed\": 6,\n" +
            "    \"Durability\": 512,\n" +
            "    \"AttackDamage\": 5,\n" +
            "    \"Enchant\": 5,\n" +
            "    \"RepairMaterial\": \"ingotCopper\",\n" +
            "    \"CraftMaterial\": \"blockCopper\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"steelHammer\",\n" +
            "    \"BreakRadius\": 1,\n" +
            "    \"HarvestLevel\": 3,\n" +
            "    \"Speed\": 6,\n" +
            "    \"Durability\": 5120,\n" +
            "    \"AttackDamage\": 5,\n" +
            "    \"Enchant\": 5,\n" +
            "    \"RepairMaterial\": \"ingotSteel\",\n" +
            "    \"CraftMaterial\": \"blockSteel\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"tungstenHammer\",\n" +
            "    \"BreakRadius\": 1,\n" +
            "    \"HarvestLevel\": 3,\n" +
            "    \"Speed\": 6,\n" +
            "    \"Durability\": 1100,\n" +
            "    \"AttackDamage\": 5,\n" +
            "    \"Enchant\": 5,\n" +
            "    \"RepairMaterial\": \"ingotTungsten\",\n" +
            "    \"CraftMaterial\": \"blockTungsten\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"HSLAHammer\",\n" +
            "    \"BreakRadius\": 1,\n" +
            "    \"HarvestLevel\": 3,\n" +
            "    \"Speed\": 6,\n" +
            "    \"Durability\": 10240,\n" +
            "    \"AttackDamage\": 5,\n" +
            "    \"Enchant\": 5,\n" +
            "    \"RepairMaterial\": \"ingotHSLA\",\n" +
            "    \"CraftMaterial\": \"RotaryCraft:rotarycraft_block_deco\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"unstableHammer\",\n" +
            "    \"BreakRadius\": 1,\n" +
            "    \"HarvestLevel\": 10,\n" +
            "    \"Speed\": 10,\n" +
            "    \"Durability\": 10240,\n" +
            "    \"AttackDamage\": 5,\n" +
            "    \"Enchant\": 5,\n" +
            "    \"RepairMaterial\": \"ingotUnstable\",\n" +
            "    \"CraftMaterial\": \"blockUnstable\",\n" +
            "    \"Infinity\": true\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"manaSteelHammer\",\n" +
            "    \"BreakRadius\": 1,\n" +
            "    \"HarvestLevel\": 3,\n" +
            "    \"Speed\": 6,\n" +
            "    \"Durability\": 2048,\n" +
            "    \"AttackDamage\": 5,\n" +
            "    \"Enchant\": 5,\n" +
            "    \"RepairMaterial\": \"ingotManasteel\",\n" +
            "    \"CraftMaterial\": \"Botania:storage\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"terraSteelHammer\",\n" +
            "    \"BreakRadius\": 1,\n" +
            "    \"HarvestLevel\": 3,\n" +
            "    \"Speed\": 6,\n" +
            "    \"Durability\": 20480,\n" +
            "    \"AttackDamage\": 5,\n" +
            "    \"Enchant\": 5,\n" +
            "    \"RepairMaterial\": \"ingotTerrasteel\",\n" +
            "    \"CraftMaterial\": \"Botania:storage:1\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"thaumiumHammer\",\n" +
            "    \"BreakRadius\": 1,\n" +
            "    \"HarvestLevel\": 3,\n" +
            "    \"Speed\": 6,\n" +
            "    \"Durability\": 2250,\n" +
            "    \"AttackDamage\": 5,\n" +
            "    \"Enchant\": 5,\n" +
            "    \"RepairMaterial\": \"ingotThaumium\",\n" +
            "    \"CraftMaterial\": \"Thaumcraft:blockCosmeticSolid:4\"\n" +
            "  }\n" +
            "]";
}
