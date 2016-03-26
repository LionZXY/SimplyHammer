package ru.lionzxy.simlyhammer.client.models;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created by LionZXY on 13.11.2015.
 * BetterWorkbench
 * Big Thank QuImUfu
 */

public class SimplyHammer extends ModelBase {
    public ModelRenderer MainHandle;
    public ModelRenderer SideHandle1;
    public ModelRenderer SideHandle2;
    public ModelRenderer Part1;
    public ModelRenderer Part2;
    public ModelRenderer Part3;
    public ModelRenderer Part4;
    public ModelRenderer MainStick;
    public ModelRenderer SideStick2;
    public ModelRenderer SideStick1;
    public ModelRenderer CorePart;
    public ModelRenderer HandleConnector;
    public ModelRenderer FPart1;
    public ModelRenderer FPart2;
    public ModelRenderer FPart3;
    public ModelRenderer FPart4;
    public ModelRenderer OPart1;
    public ModelRenderer OPart2;
    public ModelRenderer UNDERPart;
    public ModelRenderer UPPERPart;
    public ModelRenderer upperPart;
    public ModelRenderer underPart;

    public SimplyHammer() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.HandleConnector = new ModelRenderer(this, 26, 0);
        this.HandleConnector.setRotationPoint(-0.53F, -17.0F, -0.53F);
        this.HandleConnector.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.underPart = new ModelRenderer(this, 0, 20);
        this.underPart.setRotationPoint(-5.5F, -11.5F, -1.0F);
        this.underPart.addBox(0.0F, 0.0F, 0.0F, 13, 1, 4, 0.0F);
        this.MainHandle = new ModelRenderer(this, 14, 38);
        this.MainHandle.setRotationPoint(-0.53F, 5.0F, 0.0F);
        this.MainHandle.addBox(0.0F, 0.0F, 0.0F, 3, 15, 2, 0.0F);
        this.upperPart = new ModelRenderer(this, 0, 20);
        this.upperPart.setRotationPoint(-5.5F, -16.9F, -1.0F);
        this.upperPart.addBox(0.0F, 0.0F, 0.0F, 13, 1, 4, 0.0F);
        this.SideStick1 = new ModelRenderer(this, 30, 27);
        this.SideStick1.setRotationPoint(1.3F, -17.3F, 0.5F);
        this.SideStick1.addBox(0.0F, 0.0F, 0.0F, 1, 23, 1, 0.0F);
        this.UNDERPart = new ModelRenderer(this, 0, 12);
        this.UNDERPart.setRotationPoint(-6.0F, -11.7F, -1.5F);
        this.UNDERPart.addBox(0.0F, 0.0F, 0.0F, 14, 1, 5, 0.0F);
        this.SideHandle1 = new ModelRenderer(this, 6, 38);
        this.SideHandle1.setRotationPoint(0.0F, 5.0F, 1.2F);
        this.SideHandle1.addBox(0.0F, 0.0F, 0.0F, 2, 15, 1, 0.0F);
        this.UPPERPart = new ModelRenderer(this, 0, 12);
        this.UPPERPart.setRotationPoint(-6.0F, -16.7F, -1.5F);
        this.UPPERPart.addBox(0.0F, 0.0F, 0.0F, 14, 1, 5, 0.0F);
        this.SideHandle2 = new ModelRenderer(this, 6, 38);
        this.SideHandle2.setRotationPoint(0.0F, 5.0F, -0.2F);
        this.SideHandle2.addBox(0.0F, 0.0F, 0.0F, 2, 15, 1, 0.0F);
        this.MainStick = new ModelRenderer(this, 40, 0);
        this.MainStick.setRotationPoint(0.0F, -17.3F, 0.0F);
        this.MainStick.addBox(0.0F, 0.0F, 0.0F, 2, 34, 2, 0.0F);
        this.SideStick2 = new ModelRenderer(this, 30, 27);
        this.SideStick2.setRotationPoint(0.0F, -17.3F, 0.5F);
        this.SideStick2.addBox(-0.3F, 0.0F, 0.0F, 1, 23, 1, 0.0F);
        this.OPart2 = new ModelRenderer(this, 8, 0);
        this.OPart2.setRotationPoint(5.3F, -16.2F, -1.5F);
        this.OPart2.addBox(0.0F, 0.0F, 0.0F, 3, 5, 5, 0.0F);
        this.FPart1 = new ModelRenderer(this, 0, 0);
        this.FPart1.setRotationPoint(3.6F, -16.0F, 2.0F);
        this.FPart1.addBox(0.0F, 0.0F, 0.0F, 2, 5, 1, 0.0F);
        this.setRotateAngle(FPart1, 0.0F, -0.2617993877991494F, 0.0F);
        this.FPart4 = new ModelRenderer(this, 0, 0);
        this.FPart4.setRotationPoint(3.3F, -16.0F, -1.1F);
        this.FPart4.addBox(0.0F, 0.0F, 0.0F, 2, 5, 1, 0.0F);
        this.setRotateAngle(FPart4, 0.0F, 0.19198621771937624F, 0.0F);
        this.FPart2 = new ModelRenderer(this, 0, 0);
        this.FPart2.setRotationPoint(-3.4F, -16.0F, -1.5F);
        this.FPart2.addBox(0.0F, 0.0F, 0.0F, 2, 5, 1, 0.0F);
        this.setRotateAngle(FPart2, 0.0F, -0.24434609527920614F, 0.0F);
        this.CorePart = new ModelRenderer(this, 0, 27);
        this.CorePart.setRotationPoint(-4.0F, -16.0F, -1.0F);
        this.CorePart.addBox(0.0F, 0.0F, 0.0F, 10, 5, 4, 0.0F);
        this.Part4 = new ModelRenderer(this, 0, 38);
        this.Part4.setRotationPoint(1.2F, 5.0F, 1.1F);
        this.Part4.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.OPart1 = new ModelRenderer(this, 8, 0);
        this.OPart1.setRotationPoint(-6.3F, -16.2F, -1.5F);
        this.OPart1.mirror = true;
        this.OPart1.addBox(0.0F, 0.0F, 0.0F, 3, 5, 5, 0.0F);
        this.FPart3 = new ModelRenderer(this, 0, 0);
        this.FPart3.setRotationPoint(-3.5F, -16.0F, 2.5F);
        this.FPart3.addBox(0.0F, 0.0F, 0.0F, 2, 5, 1, 0.0F);
        this.setRotateAngle(FPart3, 0.0F, 0.22689280275926282F, 0.0F);
        this.Part1 = new ModelRenderer(this, 0, 38);
        this.Part1.setRotationPoint(-0.2F, 5.0F, -0.1F);
        this.Part1.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.Part2 = new ModelRenderer(this, 0, 38);
        this.Part2.setRotationPoint(1.2F, 5.0F, -0.1F);
        this.Part2.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.Part3 = new ModelRenderer(this, 0, 38);
        this.Part3.setRotationPoint(-0.2F, 5.0F, 1.1F);
        this.Part3.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.HandleConnector.render(f5);
        this.underPart.render(f5);
        this.MainHandle.render(f5);
        this.upperPart.render(f5);
        this.SideStick1.render(f5);
        this.UNDERPart.render(f5);
        this.SideHandle1.render(f5);
        this.UPPERPart.render(f5);
        this.SideHandle2.render(f5);
        this.MainStick.render(f5);
        this.SideStick2.render(f5);
        this.OPart2.render(f5);
        this.FPart1.render(f5);
        this.FPart4.render(f5);
        this.FPart2.render(f5);
        this.CorePart.render(f5);
        this.Part4.render(f5);
        this.OPart1.render(f5);
        this.FPart3.render(f5);
        this.Part1.render(f5);
        this.Part2.render(f5);
        this.Part3.render(f5);
    }

    /**
     * This is a helper function to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
