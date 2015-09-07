package ru.lionzxy.simlyhammer.utils;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.config.Config;
import ru.lionzxy.simlyhammer.hammers.BasicHammer;

/**
 * Created by nikit on 06.09.2015.
 */
public class CustomHammers {
    static public void addCustomHammers(){
        for(int i = 0; i<Config.customHammer;i++){
            addCustomHammer(i);
        }
    }
    static void addCustomHammer(int numb){
        String name =Config.config.get("hamm"+numb,"Name","hamm"+numb).getString();
        SimplyHammer.hammers.add(new BasicHammer(name,
                Config.config.get("hamm"+numb,"LocalizeName","Simply Hammer #"+numb).getString(),
                Config.config.get("hamm"+numb,"TextureName","simplyhammer:hamm"+numb).getString(),
                Config.config.get("hamm"+numb, "BreakRadius", 1).getInt(),
                Config.config.get("hamm"+numb, "HarvestLevel", 3).getInt(),
                (float) Config.config.get("hamm"+numb, "Speed", (double) 1F).getDouble(),
                Config.config.get("hamm"+numb, "Durability", 2000).getInt(),
                Config.config.get("hamm"+numb, "Enchant", (int) 10).getInt(),
                Config.config.get("hamm"+numb, "AttackDamage", (int) (1F * 1000) / 2000).getInt(),
                Config.config.get("hamm"+numb, "RepairMaterial", "ingotIron").getString(),
                Config.config.get("hamm"+numb, "Repairable", true).getBoolean(),
                Config.config.get("hamm"+numb, "GetAchievement", true).getBoolean(),
                Config.config.get("hamm"+numb, "DiamondModif", true).getBoolean(),
                Config.config.get("hamm"+numb, "AxeModif", true).getBoolean(),
                Config.config.get("hamm"+numb, "ShovelModif", true).getBoolean(),
                Config.config.get("hamm"+numb, "TorchModif", true).getBoolean(),
                Config.config.get("hamm"+numb, "Infinity", false).getBoolean()));
        int thisPos = SimplyHammer.hammers.size() - 1;
        GameRegistry.registerItem(SimplyHammer.hammers.get(thisPos), name);
        AddHammers.addCraft(SimplyHammer.hammers.get(thisPos), name, "ingotIron", "blockIron");
    }
}
