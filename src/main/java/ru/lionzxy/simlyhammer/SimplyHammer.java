package ru.lionzxy.simlyhammer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import ru.lionzxy.simlyhammer.config.Config;
import ru.lionzxy.simlyhammer.recipe.RecipeRepair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikit on 30.08.2015.
 */
@Mod(modid = "simplyhammer", name = "Simply Hammer", version = "0.4")
public class SimplyHammer {
    public static CreativeTabs tabGeneral;
    public static List<net.minecraft.stats.Achievement> achievements = new ArrayList<net.minecraft.stats.Achievement>();
    public static List<Item> hammers = new ArrayList<Item>();

    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event) {
        Config.createConfig();
        tabGeneral = new HammerTab("tabGeneral");
        AddHammers.addAllHammers();
        GameRegistry.addRecipe(new RecipeRepair());
        Config.config.save();
        FMLCommonHandler.instance().bus().register(new AchievementSH());
        AchievementSH.addAchivement();

    }
}
