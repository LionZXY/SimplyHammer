package ru.lionzxy.simlyhammer;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import ru.lionzxy.simlyhammer.commons.config.Config;
import ru.lionzxy.simlyhammer.commons.hammers.RFHammer;
import ru.lionzxy.simlyhammer.commons.handlers.CommandHandler;
import ru.lionzxy.simlyhammer.commons.handlers.CommonHandlerSH;
import ru.lionzxy.simlyhammer.commons.items.AddItems;
import ru.lionzxy.simlyhammer.commons.recipe.BindingRecipe;
import ru.lionzxy.simlyhammer.commons.recipe.InvertRecipe;
import ru.lionzxy.simlyhammer.commons.recipe.RecipeRepair;
import ru.lionzxy.simlyhammer.proxy.CommonProxy;
import ru.lionzxy.simlyhammer.utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikit on 30.08.2015.
 */
@Mod(modid = Ref.MODID, name = "Simply Hammers", version = Ref.VERSION)
public class SimplyHammer {
    @SidedProxy(clientSide = "ru.lionzxy.simlyhammer.proxy.ClientProxy", serverSide = "ru.lionzxy.simlyhammer.proxy.CommonProxy")
    public static CommonProxy proxy;
    /*
    -Add durability coficient
    -Fix Dissambler crash on server
    * */
    @Mod.Instance
    public static SimplyHammer instance;

    public static CreativeTabs tabGeneral;
    public static List<net.minecraft.stats.Achievement> achievements = new ArrayList<net.minecraft.stats.Achievement>();
    public static List<Item> hammers = new ArrayList<Item>();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.createConfig();
        HammerUtils.init();
        tabGeneral = new HammerTab("tabGeneral");
        AddItems.init();
        CustomHammers.addCustomHammers();
        GameRegistry.addRecipe(new RecipeRepair());
        GameRegistry.addRecipe(new InvertRecipe());
        if (Loader.isModLoaded("AWWayofTime"))
            new BindingRecipe(null, null);
        CommonHandlerSH.addAchivement();
        proxy.registerProxies();
        Config.saveConfig();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("gregapi") && getModContainer("gregapi").getVersion().equalsIgnoreCase("GT6-MC1710"))
            proxy.addGregTechIntegration();
        if (AddHammers.RFHammerv != null)
            ((RFHammer) AddHammers.RFHammerv).addRecipe();
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandHandler());
    }

    public static ModContainer getModContainer(String modId)
    {
        for (ModContainer mod : Loader.instance().getActiveModList())
        {
            if (mod.getModId().equalsIgnoreCase(modId))
            {
                return mod;
            }
        }
        return getModContainer(Ref.MODID);
    }
}
