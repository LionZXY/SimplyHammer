package ru.lionzxy.simlyhammer.libs;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import ru.lionzxy.simlyhammer.utils.Ref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LionZXY on 04.12.2015.
 * BetterWorkbench
 */
public class HammerUpgrade {

    public static final int RENDERPASS = 5;
    public static HashMap<String, IIcon> upgrades = new HashMap<>();

    public static void registerIcon(IIconRegister register) {
        if (upgrades.size() == 0) {
            String startTexture = Ref.MODID + ":upgrades/";
        }

    }

    public static IIcon getIcon(ItemStack is, int renderpass, IIcon defaultIcon) {
        switch (renderpass) {
            case 1: {
            }
            default:
                return defaultIcon;
        }

    }
}
