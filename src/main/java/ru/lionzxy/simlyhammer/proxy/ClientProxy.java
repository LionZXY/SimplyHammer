package ru.lionzxy.simlyhammer.proxy;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.client.renders.HammerSimplyRender;
import ru.lionzxy.simlyhammer.commons.config.Config;
import ru.lionzxy.simlyhammer.interfaces.IModifiHammer;
import ru.lionzxy.simlyhammer.utils.AddHammers;

/**
 * Created by nikit on 12.09.2015.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void registerRender() {
        if (Config.model)
            for (Item item : SimplyHammer.hammers) {
                if (item instanceof IModifiHammer)
                    if (((IModifiHammer) item).getHammerSettings().getModelLocation() != null) {
                        ResourceLocation res = ((IModifiHammer) item).getHammerSettings().getModelLocation();
                        try {
                            if (Minecraft.getMinecraft().getResourceManager().getResource(res) != null)
                                MinecraftForgeClient.registerItemRenderer(item, new HammerSimplyRender(res));
                        } catch (Exception e) {
                            FMLLog.info("[SimplyHammers] Not found model for " + new ItemStack(item).getDisplayName());
                        }
                    }
            }
    }

    public void registerProxies() {
        super.registerProxies();
        registerRender();
    }
}
