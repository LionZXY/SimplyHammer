package ru.lionzxy.simlyhammer.commons.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.oredict.OreDictionary;
import ru.lionzxy.simlyhammer.client.renders.HammerSimplyRender;
import ru.lionzxy.simlyhammer.commons.hammers.BasicHammer;
import ru.lionzxy.simlyhammer.utils.CustomHammers;
import ru.lionzxy.simlyhammer.utils.Ref;
import ru.lionzxy.simlyhammer.utils.gregtech.GregTechHelper;

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
        GregTechHelper.gregTech.createNewFile(gregtechJson);
    }

    public static void parseJsonArr() {
        for (int i = 0; i < gregtechJson.size(); i++) {
            BasicHammer hammer = CustomHammers.addHammerFromJsonObject(gregtechJson.get(i).getAsJsonObject());
            hammer.setCreativeTab(GregTechHelper.gregTechTab).setTextureName(Ref.MODID + ":" + hammer.getUnlocalizedName().substring(hammer.getUnlocalizedName().indexOf('.') + 1));
            hammer.getHammerSettings().setColor(new Color(gregtechJson.get(i).getAsJsonObject().get("Color").getAsInt()));
            if (FMLCommonHandler.instance().getEffectiveSide().isClient())
                addModel(hammer);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void addModel(BasicHammer hammer) {
        MinecraftForgeClient.registerItemRenderer(hammer, new HammerSimplyRender(new ResourceLocation(Ref.MODID + ":textures/model/" + hammer.getUnlocalizedName().substring(hammer.getUnlocalizedName().indexOf('.') + 1) + ".png")));
    }

    public static String getNormalOreDict(String oreDict) {
        if (OreDictionary.doesOreNameExist("plate" + oreDict) && OreDictionary.getOres("plate" + oreDict).size() > 0)
            return "plate" + oreDict;
        if (OreDictionary.doesOreNameExist("ingot" + oreDict) && OreDictionary.getOres("ingot" + oreDict).size() > 0)
            return "ingot" + oreDict;
        if (OreDictionary.doesOreNameExist("gem" + oreDict) && OreDictionary.getOres("gem" + oreDict).size() > 0)
            return "gem" + oreDict;
        return oreDict.replaceFirst(String.valueOf(oreDict.charAt(0)), String.valueOf(Character.toLowerCase(oreDict.charAt(0))));
    }

    public static String getNormalBlockOreDict(String oreDict) {
        if (OreDictionary.doesOreNameExist("block" + oreDict) && OreDictionary.getOres("block" + oreDict).size() > 0)
            return "block" + oreDict;
        if (OreDictionary.doesOreNameExist("blockIngot" + oreDict) && OreDictionary.getOres("blockIngot" + oreDict).size() > 0)
            return "blockIngot" + oreDict;
        if (OreDictionary.doesOreNameExist("blockGem" + oreDict) && OreDictionary.getOres("blockGem" + oreDict).size() > 0)
            return "blockGem" + oreDict;
        return oreDict;
    }
}
