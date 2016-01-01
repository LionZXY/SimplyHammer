package ru.lionzxy.simlyhammer.utils.gregtech;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.commons.config.GTJsonArray;


/**
 * Created by LionZXY on 24.12.2015.
 */
public class GregTechHelper {
    public static CreativeTabs gregTechTab;

    public static IGregTech gregTech;

    static {
        int version;
        if (SimplyHammer.getModContainer("gregtech").getVersion().equalsIgnoreCase("GT6-MC1710"))
            version = 6;
        else version = 5;
        try {
            Class gtclass = Class.forName("ru.lionzxy.simlyhammer.utils.gregtech.GT" + version);
            gregTech = (IGregTech) gtclass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isOre(ItemStack is) {
        return gregTech.isOre(is);
    }

    public static void addHammerServer() {
        GTJsonArray.init();
    }

}
