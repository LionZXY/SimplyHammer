package ru.lionzxy.simlyhammer.config;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by nikit on 02.09.2015.
 */
public class Config {

    public static Configuration config;
    public static boolean pick, MTorch, MDiamond, MAxe, MShovel, repair, MTrash, MVacuum, MCheckVacuum, MCheckTrash, MSmelt;
    public static int customHammer;

    public static void createConfig() {
        File configFile = new File(Loader.instance().getConfigDir(), "SimplyHammer.cfg");
        config = new Configuration(configFile, "0.7");
        config.getCategory("general");
        pick = config.get("general", "Prospector's Pick", true).getBoolean();
        MTorch = Config.config.get("modif", "TorchModif", true).getBoolean();
        MShovel = Config.config.get("modif", "ShovelModif", true).getBoolean();
        MAxe = Config.config.get("modif", "AxeModif", true).getBoolean();
        MDiamond = Config.config.get("modif", "DiamondModif", true).getBoolean();
        MTrash = Config.config.get("modif", "TrashModif", true).getBoolean();
        MCheckTrash = Config.config.get("modif", "TrashCheckModif", true, "Check every pickup item all inventory slot").getBoolean();
        MVacuum = Config.config.get("modif", "VacuumModif", true).getBoolean();
        MCheckVacuum = Config.config.get("modif", "VacuumCheckModif", true, "Check every harvest block all inventory slot").getBoolean();
        repair = Config.config.get("modif", "RepairTool", true).getBoolean();
        MSmelt = Config.config.get("modif", "SmeltModif", true).getBoolean();
        customHammer = Config.config.get("general", "customHammer", 0).getInt();
        config.save();
        config.load();
    }

}
