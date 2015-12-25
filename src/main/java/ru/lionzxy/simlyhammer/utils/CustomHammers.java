package ru.lionzxy.simlyhammer.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.commons.config.Config;
import ru.lionzxy.simlyhammer.commons.config.JsonConfig;
import ru.lionzxy.simlyhammer.commons.hammers.BasicHammer;
import ru.lionzxy.simlyhammer.interfaces.IModifiHammer;
import ru.lionzxy.simlyhammer.libs.HammerSettings;

import java.io.*;

/**
 * Created by LionZXY on 08.09.2015.
 * SimplyHammer v0.9
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
                        AddHammers.addVanilaHammers();
                        FileOutputStream os = new FileOutputStream(jsonFile);
                        os.write(JsonConfig.getFormatedText(mainJson.toString()).getBytes());
                        os.close();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mainJson = new JsonParser().parse(new JsonReader(new FileReader(jsonFile))).getAsJsonArray();
                for (JsonElement obj2 : mainJson) {
                    addHammerFromJsonObject(obj2.getAsJsonObject());
                }
            } catch (FileNotFoundException e) {
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    static public void addHammer(String name, int breakRadius, int harvestLevel, float speed, int damage, String materialOreDict, String repairMaterial, boolean infinity) {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", name);
        obj.addProperty("BreakRadius", breakRadius);
        obj.addProperty("HarvestLevel", harvestLevel);
        obj.addProperty("Speed", speed);
        obj.addProperty("Durability", damage);
        obj.addProperty("AttackDamage", (harvestLevel * speed) / 10);
        obj.addProperty("RepairMaterial", repairMaterial);
        obj.addProperty("CraftMaterial", materialOreDict);
        obj.addProperty("CraftMaterial2", repairMaterial);
        if (infinity)
            obj.addProperty("Infinity", true);
        mainJson.add(obj);
    }

    static public void addHammer(String name, int breakRadius, int harvestLevel, float speed, int damage, int damageVsEntity, int enchant, String materialOreDict, String rm, boolean infinity,
                                 boolean repair, boolean isAchive, boolean mDiamond, boolean mAxe, boolean mShovel, boolean mTorch,
                                 boolean mTrash, boolean mVacuum, boolean mSmelt, String localizeName, String texturepath
            , boolean model, String modelPath) {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", name);
        obj.addProperty("BreakRadius", breakRadius);
        obj.addProperty("HarvestLevel", harvestLevel);
        obj.addProperty("Speed", speed);
        obj.addProperty("Durability", damage);
        obj.addProperty("CraftMaterial", materialOreDict);
        obj.addProperty("CraftMaterial2", rm);
        obj.addProperty("RepairMaterial", rm);
        obj.addProperty("Infinity", infinity);
        obj.addProperty("Repairable", repair);
        obj.addProperty("GetAchievement", isAchive);
        obj.addProperty("DiamondModif", mDiamond);
        obj.addProperty("AxeModif", mAxe);
        obj.addProperty("ShovelModif", mShovel);
        obj.addProperty("TorchModif", mTorch);
        obj.addProperty("TrashModif", mTrash);
        obj.addProperty("VacuumModif", mVacuum);
        obj.addProperty("SmeltModif", mSmelt);
        obj.addProperty("AttackDamage", damageVsEntity);
        obj.addProperty("Enchant", enchant);
        obj.addProperty("LocalizeName", localizeName);
        obj.addProperty("TexturePath", texturepath);
        obj.addProperty("Model", model);
        obj.addProperty("ModelPath", modelPath);
        mainJson.add(obj);
    }

    public static void addHammerFromJsonObject(JsonObject obj) {
        if (obj.get("CraftMaterial") == null || AddHammers.checkToNotNull(obj.get("CraftMaterial").getAsString()) || Config.debugI) {
            SimplyHammer.hammers.add(
                    new BasicHammer(new HammerSettings(obj.get("name").getAsString(),
                            obj.get("BreakRadius") == null ? 1 : obj.get("BreakRadius").getAsInt(),
                            obj.get("HarvestLevel") == null ? 2 : obj.get("HarvestLevel").getAsInt(),
                            obj.get("Speed") == null ? 1F : obj.get("HarvestLevel").getAsFloat(),
                            obj.get("Durability") == null ? 2000 : obj.get("Durability").getAsInt(),
                            obj.get("RepairMaterial") == null ? "ingotIron" : obj.get("RepairMaterial").getAsString(),
                            obj.get("Infinity") != null && obj.get("Infinity").getAsBoolean(),
                            obj.get("Repairable") == null || obj.get("Repairable").getAsBoolean(),
                            obj.get("GetAchievement") == null || obj.get("GetAchievement").getAsBoolean(),
                            obj.get("DiamondModif") == null || obj.get("DiamondModif").getAsBoolean(),
                            obj.get("AxeModif") == null || obj.get("AxeModif").getAsBoolean(),
                            obj.get("ShovelModif") == null || obj.get("ShovelModif").getAsBoolean(),
                            obj.get("TorchModif") == null || obj.get("TorchModif").getAsBoolean(),
                            obj.get("TrashModif") == null || obj.get("TrashModif").getAsBoolean(),
                            obj.get("VacuumModif") == null || obj.get("VacuumModif").getAsBoolean(),
                            obj.get("SmeltModif") == null || obj.get("SmeltModif").getAsBoolean(),
                            obj.get("AttackDamage") == null ? 1 : obj.get("AttackDamage").getAsInt(),
                            obj.get("Enchant") == null ? (int) ((obj.get("Speed") == null ? 1.0 : obj.get("HarvestLevel").getAsFloat() * 10000) / (obj.get("Durability") == null ? 2000 : obj.get("Durability").getAsInt())) : obj.get("Enchant").getAsInt(),
                            obj.get("Model") == null || obj.get("Model").getAsBoolean()).setModelPath(
                            obj.get("ModelPath") == null ? null : obj.get("ModelPath").getAsString())));
            int thisPos = SimplyHammer.hammers.size() - 1;
            if (obj.get("LocalizeName") != null)
                ((IModifiHammer) SimplyHammer.hammers.get(thisPos)).getHammerSettings().setLocalizeName(obj.get("LocalizeName").getAsString());
            if (obj.get("TexturePath") != null)
                SimplyHammer.hammers.get(thisPos).setTextureName(obj.get("TexturePath").getAsString());
            AddHammers.addCraft(SimplyHammer.hammers.get(thisPos), "stickHammer", obj.get("CraftMaterial") == null ? "blockIron" : obj.get("CraftMaterial").getAsString(), obj.get("CraftMaterial2") == null ? obj.get("RepairMaterial") == null ? "ingotIron" : obj.get("RepairMaterial").getAsString() : obj.get("CraftMaterial2").getAsString());
            GameRegistry.registerItem(SimplyHammer.hammers.get(thisPos), obj.get("name").getAsString());
            FMLLog.fine("Add hammer!" + new ItemStack(SimplyHammer.hammers.get(thisPos)).getDisplayName());
        }
    }
}
