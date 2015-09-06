package ru.lionzxy.simlyhammer.config;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by nikit on 02.09.2015.
 */
public class Config {

    public static Configuration config;
    public static boolean pick,MTorch,MDiamond,MAxe,MShovel,repair;
    public static int customHammer;

    public static void createConfig() {
        File configFile = new File(Loader.instance().getConfigDir(), "SimplyHammer.cfg");
        config = new Configuration(configFile, "0.6");
        config.getCategory("general");
        pick = config.get("general", "Prospector's Pick", true).getBoolean();
        MTorch =  Config.config.get("general", "TorchModif", true).getBoolean();
        MShovel = Config.config.get("general", "ShovelModif", true).getBoolean();
        MAxe = Config.config.get("general", "AxeModif", true).getBoolean();
        MDiamond = Config.config.get("general", "DiamondModif", true).getBoolean();
        repair = Config.config.get("general", "RepairTool", true).getBoolean();
        customHammer = Config.config.get("general","customHammer",0).getInt();
        config.save();
        config.load();
    }

}
