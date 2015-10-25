package ru.lionzxy.simlyhammer.interfaces;

import net.minecraft.item.ItemStack;
import ru.lionzxy.simlyhammer.libs.HammerSettings;

/**
 * Created by nikit on 07.09.2015.
 */
public interface IModifiHammer {
    int breakRadius = 1, breakDepth = 0, oreDictId = 0;

    boolean checkMaterial(ItemStack itemStack);

    HammerSettings getHammerSettings();

}
