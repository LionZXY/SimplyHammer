package ru.lionzxy.simlyhammer.utils.gregtech;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gregapi.code.ItemStackContainer;
import gregapi.data.OP;
import gregapi.oredict.OreDictItemData;
import gregapi.util.OM;
import net.minecraft.item.ItemStack;
import ru.lionzxy.simlyhammer.commons.config.Config;
import ru.lionzxy.simlyhammer.commons.config.GTJsonArray;

import java.awt.*;

/**
 * ru.lionzxy.simlyhammer.utils.gregtech
 * Created by LionZXY on 01.01.2016.
 * SimplyHammer
 */
public class GT6 implements IGregTech {
    @Override
    public boolean isOre(ItemStack ore) {
        for (ItemStackContainer item : OP.ore.mRegisteredItems) {
            if (item.mItem == ore.getItem())
                return true;
        }
        return false;
    }

    public void addHammerInJson(JsonArray json, ItemStack itemStack) {
        OreDictItemData tData = OM.data(itemStack);
        if (tData != null) {

            //if (tData.mPrefix == OP.toolHeadPickaxe) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", itemStack.getUnlocalizedName() + itemStack.getItemDamage());
            obj.addProperty("UniqId", itemStack.getItemDamage());
            obj.addProperty("HarvestLevel", tData.mMaterial.mMaterial.mToolQuality);
            obj.addProperty("Speed", tData.mMaterial.mMaterial.mToolSpeed);
            obj.addProperty("Durability", tData.mMaterial.mMaterial.mToolDurability * Config.config.get("general", "DurabilityCofic", 4).getInt());
            obj.addProperty("AttackDamage", (tData.mMaterial.mMaterial.mToolQuality * tData.mMaterial.mMaterial.mToolSpeed) / Config.attMinus);
            obj.addProperty("RepairMaterial", GTJsonArray.getNormalOreDict(tData.mMaterial.mMaterial.mNameInternal));
            obj.addProperty("CraftMaterial", GTJsonArray.getNormalBlockOreDict(tData.mMaterial.mMaterial.mNameInternal));
            obj.addProperty("CraftMaterial2", GTJsonArray.getNormalOreDict(tData.mMaterial.mMaterial.mNameInternal));

            obj.addProperty("Color", new Color(tData.mMaterial.mMaterial.mRGBaSolid[0],
                    tData.mMaterial.mMaterial.mRGBaSolid[1],
                    tData.mMaterial.mMaterial.mRGBaSolid[2],
                    tData.mMaterial.mMaterial.mRGBaSolid[3]).getRGB());
            obj.addProperty("LocalizeName", tData.mMaterial.mMaterial.getLocal() + " Hammer");
            json.add(obj);
        }
    }

    @Override
    public void createNewFile(JsonArray gregtechJson) {
        for (ItemStackContainer item : OP.toolHeadConstructionPickaxe.mRegisteredItems) {
            this.addHammerInJson(gregtechJson, item.toStack());
        }

        for (JsonElement json : gregtechJson) {
            System.out.print(json.getAsJsonObject().get("name").getAsString());
        }
    }
}
