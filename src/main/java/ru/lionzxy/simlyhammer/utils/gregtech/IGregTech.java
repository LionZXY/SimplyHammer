package ru.lionzxy.simlyhammer.utils.gregtech;

import com.google.gson.JsonArray;
import net.minecraft.item.ItemStack;

/**
 * ru.lionzxy.simlyhammer.utils.gregtech
 * Created by LionZXY on 01.01.2016.
 * SimplyHammer
 */
public interface IGregTech {

    boolean isOre(ItemStack ore);

    void createNewFile(JsonArray json);


}
