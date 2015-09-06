package ru.lionzxy.simlyhammer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import ru.lionzxy.simlyhammer.config.Config;
import ru.lionzxy.simlyhammer.recipe.BindingRecipe;
import ru.lionzxy.simlyhammer.recipe.RecipeRepair;
import ru.lionzxy.simlyhammer.utils.AchievementSH;
import ru.lionzxy.simlyhammer.utils.AddHammers;
import ru.lionzxy.simlyhammer.utils.CustomHammers;
import ru.lionzxy.simlyhammer.utils.HammerTab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikit on 30.08.2015.
 */
@Mod(modid = "simplyhammer", name = "Simply Hammer", version = "0.7")
public class SimplyHammer {
    public static CreativeTabs tabGeneral;
    public static List<net.minecraft.stats.Achievement> achievements = new ArrayList<net.minecraft.stats.Achievement>();
    public static List<Item> hammers = new ArrayList<Item>();

    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event) {
        Config.createConfig();
        tabGeneral = new HammerTab("tabGeneral");
        AddHammers.addAllHammers();
        CustomHammers.addCustomHammers();
        GameRegistry.addRecipe(new RecipeRepair());
        if (Loader.isModLoaded("AWWayofTime"))
            new BindingRecipe(null, null);
        FMLCommonHandler.instance().bus().register(new AchievementSH());
        AchievementSH.addAchivement();
        Config.config.save();
    }
}
