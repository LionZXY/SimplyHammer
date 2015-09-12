package ru.lionzxy.simlyhammer.handlers;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ru.lionzxy.simlyhammer.containers.TrashContainer;
import ru.lionzxy.simlyhammer.guis.TrashGui;

/**
 * Created by nikit on 12.09.2015.
 */
public class GuiHandlers implements IGuiHandler {
    public static final int TRASH = 1;
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID) {
            case TRASH:
                return new TrashContainer(player, player.inventory);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Object server = getServerGuiElement(ID, player, world, x, y, z);
        switch(ID) {
            case TRASH:
                return new TrashGui((TrashContainer) server);
            default:
                return null;
        }
    }
}
