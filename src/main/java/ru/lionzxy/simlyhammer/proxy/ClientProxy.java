package ru.lionzxy.simlyhammer.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.client.renders.HammerSimplyRender;
import ru.lionzxy.simlyhammer.utils.AddHammers;

/**
 * Created by nikit on 12.09.2015.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void registerRender() {
            for (Item item : SimplyHammer.hammers)
                MinecraftForgeClient.registerItemRenderer(item, new HammerSimplyRender(item.getUnlocalizedName()));}

    public void registerProxies() {
        super.registerProxies();
        registerRender();
    }
}
