package ru.lionzxy.simlyhammer.guis;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.lionzxy.simlyhammer.containers.TrashContainer;
import ru.lionzxy.simlyhammer.inventory.TrashInventory;

/**
 * Created by nikit on 12.09.2015.
 */
public class TrashGui extends GuiContainer {
    int guiWidth = 175;
    int guiHeight = 118;
    private ResourceLocation textureLocation = new ResourceLocation("simplyhammer", "textures/gui/trashGui.png");

    public TrashGui(TrashContainer container) {
        super(container);
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        int guiX = (width - guiWidth) / 2;
        int guiY = (height - guiHeight) / 2;
        GL11.glColor4f(1, 1, 1, 1);
        drawDefaultBackground();
        mc.renderEngine.bindTexture(textureLocation);
        drawTexturedModalRect(guiX, guiY, 0, 0, guiWidth, guiHeight);
        super.drawScreen(x, y, ticks);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1, 1, 1, 1);
        int guiX = (width - guiWidth) / 2;
        int guiY = (height - guiHeight) / 2;
        this.drawTexturedModalRect(guiX, guiY, 0, 0, guiWidth, guiHeight);
    }

    @Override
    protected void handleMouseClick(Slot p_146984_1_, int p_146984_2_, int p_146984_3_, int p_146984_4_) {
        super.handleMouseClick(p_146984_1_, p_146984_2_, p_146984_3_, p_146984_4_);
    }
}
