package ru.lionzxy.simlyhammer.proxy;

import cpw.mods.fml.common.network.NetworkRegistry;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.commons.handlers.GuiHandlers;
import ru.lionzxy.simlyhammer.utils.GregTechHelper;
import ru.lionzxy.simlyhammer.utils.HammerTab;

/**
 * Created by nikit on 12.09.2015.
 */
public class CommonProxy {

    public void registerRender() {

    }

    public void registerProxies() {
        NetworkRegistry.INSTANCE.registerGuiHandler(SimplyHammer.instance, new GuiHandlers());
    }

    public void addGregTechIntegration(){
        GregTechHelper.gregTechTab = new HammerTab("GregTechHammer");
        GregTechHelper.addHammerServer();
    }
}
