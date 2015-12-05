package ru.lionzxy.simlyhammer.commons.hammers;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.lionzxy.simlyhammer.client.renders.ColouredFX;
import ru.lionzxy.simlyhammer.commons.config.JsonConfig;
import ru.lionzxy.simlyhammer.libs.HammerSettings;
import ru.lionzxy.simlyhammer.utils.AddHammers;

import java.util.Random;

/**
 * Created by LionZXY on 24.11.2015.
 * BetterWorkbench
 */
public class IIEERRAAHammer extends BasicHammer {
    public static float change = 1.0F;

    public IIEERRAAHammer() {
        super(new HammerSettings("IIEERRAAHammer", 1, 4, 5F, 15, null, true));
        change = (float) JsonConfig.get("IIEERRAAHammer", "change", 1.0).getDouble();
        AddHammers.addCraft(this, null, "minecraft:red_flower", "dyePink");
        GameRegistry.registerItem(this, "IIEERRAAHammer");
    }

    @Override
    protected void breakExtraBlock(World world, int x, int y, int z, int sidehit, EntityPlayer playerEntity, int refX, int refY, int refZ) {

        ForgeDirection dir = ForgeDirection.getOrientation(sidehit).getOpposite();
        if (playerEntity.getDisplayName().equals("IIEERRAA")) {
            if (new Random().nextFloat() < change)
                ColouredFX.generateParticles(world, x, y, z, dir, 255F, 182F, 193F);
            super.breakExtraBlock(world, x, y, z, sidehit, playerEntity, refX, refY, refZ);
        } else {
            ColouredFX.generateParticles(world, x, y, z, dir, 0F, 0F, 03F);
        }
    }


}
