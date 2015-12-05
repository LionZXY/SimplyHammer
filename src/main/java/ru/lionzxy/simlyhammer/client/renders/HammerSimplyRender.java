package ru.lionzxy.simlyhammer.client.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import ru.lionzxy.simlyhammer.client.models.SimplyHammer;

/**
 * Created by LionZXY on 13.11.2015.
 * BetterWorkbench
 */
public class HammerSimplyRender implements IItemRenderer {
    boolean handRender = true;
    SimplyHammer model;
    ResourceLocation res;

    public HammerSimplyRender(ResourceLocation resourceLocation) {

        this.res = resourceLocation;
        model = new SimplyHammer();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        switch (type) {
            case EQUIPPED:
                return handRender;
            case EQUIPPED_FIRST_PERSON:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        float scale = 0.8F;
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        switch (type) {
            case EQUIPPED_FIRST_PERSON:
                GL11.glScalef(scale, scale, scale);
                GL11.glRotatef(180F, 2.5F, 1F, 0F);
                GL11.glTranslatef(1.3F, 1F, 0F);
                break;
            case EQUIPPED:
                GL11.glScalef(scale, scale, scale);
                GL11.glRotatef(180F, 2F, 1F, 0F);
                GL11.glTranslatef(0.7F, 0F, 0F);
                break;

        }
        Minecraft.getMinecraft().renderEngine.bindTexture(res);
        model.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.07F);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
