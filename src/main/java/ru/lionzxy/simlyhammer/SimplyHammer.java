package ru.lionzxy.simlyhammer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import ru.lionzxy.simlyhammer.config.Config;
import ru.lionzxy.simlyhammer.items.AddItems;
import ru.lionzxy.simlyhammer.items.TrashItem;
import ru.lionzxy.simlyhammer.libs.HammerUtils;
import ru.lionzxy.simlyhammer.proxy.CommonProxy;
import ru.lionzxy.simlyhammer.recipe.BindingRecipe;
import ru.lionzxy.simlyhammer.recipe.InvertRecipe;
import ru.lionzxy.simlyhammer.recipe.RecipeRepair;
import ru.lionzxy.simlyhammer.handlers.AchievementSH;
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
    @SidedProxy(clientSide = "ru.lionzxy.simlyhammer.proxy.ClientProxy",serverSide = "ru.lionzxy.simlyhammer.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static SimplyHammer instance;

    public static CreativeTabs tabGeneral;
    public static List<net.minecraft.stats.Achievement> achievements = new ArrayList<net.minecraft.stats.Achievement>();
    public static List<Item> hammers = new ArrayList<Item>();

    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event) {
        Config.createConfig();
        HammerUtils.init();
        tabGeneral = new HammerTab("tabGeneral");
        AddItems.init();
        CustomHammers.addCustomHammers();
        GameRegistry.addRecipe(new RecipeRepair());
        GameRegistry.addRecipe(new InvertRecipe());
        if (Loader.isModLoaded("AWWayofTime"))
            new BindingRecipe(null, null);
        MinecraftForge.EVENT_BUS.register(new AchievementSH());
        FMLCommonHandler.instance().bus().register(new AchievementSH());
        AchievementSH.addAchivement();
        proxy.registerProxies();
        Config.config.save();
    }
}
