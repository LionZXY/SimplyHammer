package ru.lionzxy.simlyhammer.utils;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.config.Config;
import ru.lionzxy.simlyhammer.hammers.BasicHammer;
import ru.lionzxy.simlyhammer.interfaces.IModifiHammer;
import ru.lionzxy.simlyhammer.libs.HammerSettings;

/**
 * Created by nikit on 06.09.2015.
 */
public class CustomHammers {
    static public void addCustomHammers() {
        for (int i = 0; i < Config.customHammer; i++) {
            addCustomHammer(i);
        }
    }

    static void addCustomHammer(int numb) {
        String name = Config.config.get("hamm" + numb, "Name", "hamm" + numb).getString();
        SimplyHammer.hammers.add(new BasicHammer(new HammerSettings("hamm" + numb, 1, 3, 1F, 2000, "ingotIron", false)));
        int thisPos = SimplyHammer.hammers.size() - 1;
        ((IModifiHammer) SimplyHammer.hammers.get(thisPos)).getHammerSettings().setLocalizeName("Simply Hammer #" + numb);
        GameRegistry.registerItem(SimplyHammer.hammers.get(thisPos), name);
        AddHammers.addCraft(SimplyHammer.hammers.get(thisPos), name, "ingotIron", "blockIron");
    }
}
