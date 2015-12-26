package ru.lionzxy.simlyhammer.commons.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.code.ItemStackContainer;
import gregapi.data.OP;
import gregapi.oredict.OreDictItemData;
import gregapi.util.OM;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.oredict.OreDictionary;
import ru.lionzxy.simlyhammer.client.renders.HammerSimplyRender;
import ru.lionzxy.simlyhammer.commons.hammers.BasicHammer;
import ru.lionzxy.simlyhammer.utils.CustomHammers;
import ru.lionzxy.simlyhammer.utils.GregTechHelper;
import ru.lionzxy.simlyhammer.utils.Ref;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 * Created by LionZXY on 24.12.2015.
 */
public class GTJsonArray {

    public static File jsonFile = new File(Loader.instance().getConfigDir() + "/SimplyHammers", "GTHammers.json");
    public static JsonArray gregtechJson = new JsonArray();

    public static void addHammerInJson(ItemStack itemStack) {
        OreDictItemData tData = OM.data(itemStack);
        if (tData != null) {
            //if (tData.mPrefix == OP.toolHeadPickaxe) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", itemStack.getUnlocalizedName() + itemStack.getItemDamage());
            obj.addProperty("UniqId", itemStack.getItemDamage());
            obj.addProperty("HarvestLevel", tData.mMaterial.mMaterial.mToolQuality);
            obj.addProperty("Speed", tData.mMaterial.mMaterial.mToolSpeed);
            obj.addProperty("Durability", tData.mMaterial.mMaterial.mToolDurability);
            obj.addProperty("AttackDamage", (tData.mMaterial.mMaterial.mToolQuality * tData.mMaterial.mMaterial.mToolSpeed) / Config.attMinus);
            obj.addProperty("RepairMaterial", getNormalOreDict(tData.mMaterial.mMaterial.mNameInternal));
            obj.addProperty("CraftMaterial", getNormalBlockOreDict(tData.mMaterial.mMaterial.mNameInternal));
            obj.addProperty("CraftMaterial2", getNormalOreDict(tData.mMaterial.mMaterial.mNameInternal));

            obj.addProperty("Color", new Color(tData.mMaterial.mMaterial.mRGBaSolid[0],
                    tData.mMaterial.mMaterial.mRGBaSolid[1],
                    tData.mMaterial.mMaterial.mRGBaSolid[2],
                    tData.mMaterial.mMaterial.mRGBaSolid[3]).getRGB());
            obj.addProperty("LocalizeName", tData.mMaterial.mMaterial.getLocal() + " Hammer");
            gregtechJson.add(obj);
        }
    }

    public static void init() {
        try {
            if (!jsonFile.canWrite()) {
                jsonFile.getParentFile().mkdirs();
                jsonFile.createNewFile();
                createNewFile();
                FileOutputStream os = new FileOutputStream(jsonFile);
                os.write(JsonConfig.getFormatedText(gregtechJson.toString()).getBytes());
                os.close();
            } else {
                gregtechJson = new JsonParser().parse(new JsonReader(new FileReader(jsonFile))).getAsJsonArray();
            }
            parseJsonArr();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createNewFile() {
        for (ItemStackContainer item : OP.toolHeadConstructionPickaxe.mRegisteredItems) {
            GTJsonArray.addHammerInJson(item.toStack());
        }

        for (JsonElement json : gregtechJson) {
            System.out.print(json.getAsJsonObject().get("name").getAsString());
        }
    }

    public static void parseJsonArr() {
        for (int i = 0; i < gregtechJson.size(); i++) {
            BasicHammer hammer = CustomHammers.addHammerFromJsonObject(gregtechJson.get(i).getAsJsonObject());
            hammer.setCreativeTab(GregTechHelper.gregTechTab).setTextureName(Ref.MODID + ":" + hammer.getUnlocalizedName().substring(hammer.getUnlocalizedName().indexOf('.') + 1));
            hammer.getHammerSettings().setColor(new Color(gregtechJson.get(i).getAsJsonObject().get("Color").getAsInt()));
            if(FMLCommonHandler.instance().getEffectiveSide().isClient())
                addModel(hammer);
        }
    }
    @SideOnly(Side.CLIENT)
    public static void addModel(BasicHammer hammer){
        MinecraftForgeClient.registerItemRenderer(hammer,new HammerSimplyRender(new ResourceLocation(Ref.MODID + ":model/" + hammer.getUnlocalizedName().substring(hammer.getUnlocalizedName().indexOf('.') + 1) + ".png")));
    }

    public static String getNormalOreDict(String oreDict) {
        if (OreDictionary.doesOreNameExist("plate" + oreDict) && OreDictionary.getOres("plate" + oreDict).size() > 0)
            return "plate" + oreDict;
        if (OreDictionary.doesOreNameExist("ingot" + oreDict) && OreDictionary.getOres("ingot" + oreDict).size() > 0)
            return "ingot" + oreDict;
        if (OreDictionary.doesOreNameExist("gem" + oreDict) && OreDictionary.getOres("gem" + oreDict).size() > 0)
            return "gem" + oreDict;
        return oreDict;
    }

    public static String getNormalBlockOreDict(String oreDict) {
        if (OreDictionary.doesOreNameExist("block" + oreDict) && OreDictionary.getOres("block"  + oreDict).size() > 0)
            return "block"  + oreDict;
        if (OreDictionary.doesOreNameExist("blockIngot" + oreDict) && OreDictionary.getOres("blockIngot"  + oreDict).size() > 0)
            return "blockIngot"  + oreDict;
        if (OreDictionary.doesOreNameExist("blockGem" + oreDict) && OreDictionary.getOres("blockGem" + oreDict).size() > 0)
            return "blockGem" + oreDict;
        return oreDict;
    }
}
