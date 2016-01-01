package ru.lionzxy.simlyhammer.utils.gregtech;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.ItemData;
import gregtech.api.world.GT_Worldgen;
import gregtech.api.world.GT_Worldgen_Ore;
import gregtech.common.tools.GT_Tool_Pickaxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import ru.lionzxy.simlyhammer.commons.config.Config;
import ru.lionzxy.simlyhammer.commons.config.GTJsonArray;

import java.awt.*;

/**
 * ru.lionzxy.simlyhammer.utils.gregtech
 * Created by LionZXY on 01.01.2016.
 * SimplyHammer
 */
public class GT5 implements IGregTech {

    @Override
    public boolean isOre(ItemStack ore) {
        for (GT_Worldgen item : GregTech_API.sWorldgenList) {
            if (item instanceof GT_Worldgen_Ore && ore.getItem() instanceof ItemBlock &&
                    ((GT_Worldgen_Ore) item).mBlock == ((ItemBlock) ore.getItem()).field_150939_a &&
                    ((GT_Worldgen_Ore) item).mBlockMeta == ore.getItemDamage())
                return true;
        }
        return false;
    }

    public void addHammerInJson(JsonArray json, Materials m) {
        ItemData tData = ((ItemData) OrePrefixes.toolHeadPickaxe.get(m));
        if (tData != null) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", "gthammer" + tData.mMaterial.mMaterial.name());
            obj.addProperty("UniqId", m.mOreValue);
            obj.addProperty("HarvestLevel", tData.mMaterial.mMaterial.mToolQuality);
            obj.addProperty("Speed", tData.mMaterial.mMaterial.mToolSpeed);
            obj.addProperty("Durability", tData.mMaterial.mMaterial.mDurability * Config.config.get("general", "DurabilityCofic", 4).getInt());
            obj.addProperty("AttackDamage", (tData.mMaterial.mMaterial.mToolQuality * tData.mMaterial.mMaterial.mToolSpeed) / Config.attMinus);
            obj.addProperty("RepairMaterial", GTJsonArray.getNormalOreDict(tData.mMaterial.mMaterial.name()));
            obj.addProperty("CraftMaterial", GTJsonArray.getNormalBlockOreDict(tData.mMaterial.mMaterial.name()));
            obj.addProperty("CraftMaterial2", GTJsonArray.getNormalOreDict(tData.mMaterial.mMaterial.name()));

            obj.addProperty("Color", getRGB(m));
            obj.addProperty("LocalizeName", tData.mMaterial.mMaterial.name() + " Hammer");
            json.add(obj);
        }
    }

    @Override
    public void createNewFile(JsonArray json) {
        for (Materials m : Materials.VALUES) {
            if (((ItemData) OrePrefixes.toolHeadPickaxe.get(m)).mMaterial.mMaterial.mDurability > 1)
                addHammerInJson(json, m);
        }
    }

    public static int getRGB(Materials m) {
        return new Color(m.getRGBA()[0],
                m.getRGBA()[1],
                m.getRGBA()[2],
                m.getRGBA()[3]).getRGB();
    }
}
