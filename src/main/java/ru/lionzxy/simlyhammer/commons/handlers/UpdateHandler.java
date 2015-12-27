package ru.lionzxy.simlyhammer.commons.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import ru.lionzxy.simlyhammer.utils.HammerUtils;

/**
 * Created by admin on 27.12.2015.
 */
public class UpdateHandler {
    public static boolean triedToWarnPlayer = false;
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().thePlayer != null && !triedToWarnPlayer) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            HammerUtils.checkUpdate(player);
            triedToWarnPlayer = true;
        }
    }
}
