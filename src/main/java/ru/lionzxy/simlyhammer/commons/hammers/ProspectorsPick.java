package ru.lionzxy.simlyhammer.commons.hammers;

import cpw.mods.fml.common.Loader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.commons.config.Config;
import ru.lionzxy.simlyhammer.commons.handlers.AchievementSH;
import ru.lionzxy.simlyhammer.utils.GregTechHelper;

/**
 * Created by nikit on 31.08.2015.
 */
public class ProspectorsPick extends Item {
    int radiusPP;

    public ProspectorsPick() {
        setUnlocalizedName("prospectorsPick");
        setCreativeTab(SimplyHammer.tabGeneral);
        setTextureName("simplyhammer:prospectorsPick");
        setMaxDamage(Config.config.get("prospectorsPick", "MaxDamage", 6).getInt());
        radiusPP = Config.config.get("prospectorsPick", "Radius", 21).getInt();
        setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ) {
        if (!world.isRemote) {
            if (Config.config.get("prospectorsPick", "Achievement", true).getBoolean())
                player.addStat(AchievementSH.firstResearch, 1);
            int radius = radiusPP;
            stack.damageItem(1, player);
            ItemStack itemStack;
            for (int length = 0; length < radius; length++)
                for (int i = 0; i < radius; i++) {
                    itemStack = checkRadius(world, i, side, x, y, z, length);
                    if (itemStack != null) {
                        player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("prospectorPick.Found") + " " + itemStack.getDisplayName()));
                        return true;
                    }
                }
            player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("prospectorPick.NotFoundOre")));
        }
        return false;
    }

    ItemStack getBlockWithSide(World world, int side, int posX, int posY, int posZ, int addX, int addY, int addZ) {
        int x, y, z;
        switch (side) {
            case 0:
                x = posX + addX;
                y = posY + addZ;
                z = posZ + addY;
                break;
            case 1:
                x = posX + addX;
                y = posY - addZ;
                z = posZ + addY;
                break;
            case 2:
                x = posX - addX;
                y = posY - addY;
                z = posZ + addZ;
                break;
            case 3:
                x = posX + addX;
                y = posY - addY;
                z = posZ + addZ;
                break;
            case 4:
                x = posX - addZ;
                y = posY - addY;
                z = posZ + addX;
                break;
            case 5:
                x = posX - addZ;
                y = posY - addY;
                z = posZ - addX;
                break;
            default:
                x = 0;
                y = 0;
                z = 0;
        }
        return new ItemStack(world.getBlock(x, y, z), world.getBlockMetadata(x, y, z));
    }

    ItemStack checkRadius(World world, int radius, int side, int x, int y, int z, int lentgh) {
        if (radius == 0)
            if (checkToOre(getBlockWithSide(world, side, x, y, z, 0, 0, lentgh)))
                return getBlockWithSide(world, side, x, y, z, 0, 0, lentgh);
        int i;
        for (i = -radius; i < radius; i++)
            if (checkToOre(getBlockWithSide(world, side, x, y, z, i, -radius, lentgh)))
                return getBlockWithSide(world, side, x, y, z, i, -radius, lentgh);
        for (i = -radius; i < radius; i++)
            if (checkToOre(getBlockWithSide(world, side, x, y, z, radius, i, lentgh)))
                return getBlockWithSide(world, side, x, y, z, radius, i, lentgh);
        for (i = radius; i > -radius; i--)
            if (checkToOre(getBlockWithSide(world, side, x, y, z, i, radius, lentgh)))
                return getBlockWithSide(world, side, x, y, z, i, radius, lentgh);
        for (i = radius; i > -radius; i--)
            if (checkToOre(getBlockWithSide(world, side, x, y, z, -radius, i, lentgh)))
                return getBlockWithSide(world, side, x, y, z, -radius, i, lentgh);

        return null;
    }

    boolean checkToOre(ItemStack itemStack) {
        for (int i : OreDictionary.getOreIDs(itemStack))
            if (OreDictionary.getOreName(i).substring(0, 3).equalsIgnoreCase("ore"))
                return true;
        if(Loader.isModLoaded("gregapi"))
            return GregTechHelper.isOre(itemStack);
        return false;

    }

}
