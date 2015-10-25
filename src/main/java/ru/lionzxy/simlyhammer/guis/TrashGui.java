package ru.lionzxy.simlyhammer.guis;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import ru.lionzxy.simlyhammer.containers.TrashContainer;
import ru.lionzxy.simlyhammer.inventory.TrashInventory;

/**
 * Created by nikit on 12.09.2015.
 */
public class TrashGui extends GuiContainer {
    int guiWidth = 175;
    int guiHeight = 122; //118
    String name;
    private ResourceLocation textureLocation = new ResourceLocation("simplyhammer", "textures/gui/trashGui.png");

    public TrashGui(TrashContainer container, String name) {
        super(container);
        this.name = name;

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRendererObj.drawString(name, 8, 27, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(textureLocation);
        int guiX = (width - guiWidth) / 2;
        int guiY = (height - guiHeight) / 2;
        this.drawTexturedModalRect(guiX, guiY, 0, 0, guiWidth, guiHeight);
    }
}
