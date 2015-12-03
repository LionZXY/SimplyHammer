package ru.lionzxy.simlyhammer.client.renders;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFireworkSparkFX;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by LionZXY on 24.11.2015.
 * BetterWorkbench
 */
public class ColouredFX extends EntityFireworkSparkFX {
    public ColouredFX(World world, double x, double y, double z, double mX, double mY, double mZ, EffectRenderer renderer, float red, float green, float blue) {
        super(world, x, y, z, mX, mY, mZ, renderer);
        super.setRBGColorF(red, green, blue);
        this.particleMaxAge = 100 + this.rand.nextInt(12);

    }

    @SideOnly(Side.CLIENT)
    public static void generateParticles(World worldObj, int xCoord, int yCoord, int zCoord, ForgeDirection direction, float red, float green, float blue) {
        if (!worldObj.isRemote) return;
        double motionX = worldObj.rand.nextGaussian() * 0.1D;
        double motionY = worldObj.rand.nextGaussian() * 0.1D;
        double motionZ = worldObj.rand.nextGaussian() * 0.1D;
        double x = xCoord + 0.25 + worldObj.rand.nextFloat() / 2;
        double y = yCoord + 0.25 + worldObj.rand.nextFloat() / 2;
        double z = zCoord + 0.25 + worldObj.rand.nextFloat() / 2;

        double offset = 0.1;

        if (direction == ForgeDirection.NORTH)
            z = zCoord + offset;
        if (direction == ForgeDirection.SOUTH)
            z = zCoord + 1 - offset;
        if (direction == ForgeDirection.EAST)
            x = xCoord + 1 - offset;
        if (direction == ForgeDirection.WEST)
            x = xCoord + offset;

        ColouredFX fx = new ColouredFX(worldObj, x, y, z, motionX, motionY, motionZ, Minecraft.getMinecraft().effectRenderer, red / 256, green / 256, blue / 256);
        Minecraft.getMinecraft().effectRenderer.addEffect(fx);

    }
}
