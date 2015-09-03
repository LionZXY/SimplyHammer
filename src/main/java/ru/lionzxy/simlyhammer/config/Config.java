package ru.lionzxy.simlyhammer.config;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by nikit on 02.09.2015.
 */
public class Config {

    public static Configuration config;
    public static boolean pick;

    public static void createConfig() {
        File configFile = new File(Loader.instance().getConfigDir(), "SimplyHammer.cfg");
        config = new Configuration(configFile, "0.5");
        config.getCategory("general");
        pick = config.get("general", "Prospector's Pick", true).getBoolean();
        config.save();
        config.load();
    }

}
