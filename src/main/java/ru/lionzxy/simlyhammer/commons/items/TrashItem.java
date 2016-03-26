package ru.lionzxy.simlyhammer.commons.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.commons.config.Config;
import ru.lionzxy.simlyhammer.interfaces.IModifiHammer;
import ru.lionzxy.simlyhammer.interfaces.ITrash;
import ru.lionzxy.simlyhammer.interfaces.IUpgradeHammer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikit on 12.09.2015.
 */
public class TrashItem extends Item implements ITrash, IUpgradeHammer{

    @SideOnly(Side.CLIENT)
    protected IIcon invertIcon;

    public TrashItem() {
        this.setCreativeTab(SimplyHammer.tabGeneral);
        this.setUnlocalizedName("trashitem");
        this.setTextureName("simplyhammer:trashitem");
        this.setMaxStackSize(1);
        GameRegistry.registerItem(this, "trashitem");
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this),
                "xxx", "yzy", "yyy",
                'x', "cobblestone",
                'y', Blocks.chest,
                'z', "stone"));
    }


    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
        if (!world.isRemote)
            player.openGui(SimplyHammer.instance, 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return is;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
        if (!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
            itemStack.getTagCompound().setBoolean("Trash", true);
            itemStack.getTagCompound().setTag("Items", new NBTTagList());
        }
        if (itemStack.getTagCompound().getBoolean("Invert"))
            list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("trash.Inverted"));
        list.add(StatCollector.translateToLocal("trash.IgnoreList"));
        for (int i = 0; i < itemStack.getTagCompound().getTagList("Items", Constants.NBT.TAG_COMPOUND).tagCount(); ++i) {
            NBTTagCompound item = /*(NBTTagCompound)*/ itemStack.getTagCompound().getTagList("Items", Constants.NBT.TAG_COMPOUND).getCompoundTagAt(i);
            list.add(ItemStack.loadItemStackFromNBT(item).getDisplayName());
        }

    }


    public static boolean isTrash(ItemStack trash, ItemStack itemStack) {
        if (!itemStack.hasTagCompound() || !itemStack.getTagCompound().getBoolean("Trash") || !(itemStack.getItem() instanceof ITrash))
            return false;
        NBTTagList items = itemStack.getTagCompound().getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound item = /*(NBTTagCompound)*/ items.getCompoundTagAt(i);
            byte slot = item.getByte("Slot");

            // Just double-checking that the saved slot index is within our inventory array bounds
            if (slot >= 0 && slot < 9) {
                if (!itemStack.getTagCompound().getBoolean("Invert") && ItemStack.loadItemStackFromNBT(item).isItemEqual(trash))
                    return true;
                else if (itemStack.getTagCompound().getBoolean("Invert") && !ItemStack.loadItemStackFromNBT(item).isItemEqual(trash))
                    return true;
            }
        }
        return false;
    }

    public static void removeTrash(ArrayList<ItemStack> itemStacks, ItemStack itemStack) {
        for (int i = 0; i < itemStacks.size(); i++)
            if (isTrash(itemStacks.get(i), itemStack))
                itemStacks.remove(i);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack is) {
        if (!is.hasTagCompound() || !is.getTagCompound().getBoolean("Invert"))
            return itemIcon;
        else return invertIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_) {
        this.invertIcon = p_94581_1_.registerIcon("simplyhammer:trashitemInvert");
        this.itemIcon = p_94581_1_.registerIcon("simplyhammer:trashitem");
    }

    @Override
    public void upgradeHammer(InventoryCrafting ic, ItemStack hammer, ItemStack itemFound) {
        if (itemFound != null && itemFound.hasTagCompound() && hammer != null && hammer.hasTagCompound() && Config.MTrash && ((IModifiHammer) hammer.getItem()).getHammerSettings().getMTrash())
            if (!hammer.getTagCompound().getBoolean("Trash")) {
                hammer.getTagCompound().setBoolean("Trash", true);
                hammer.getTagCompound().setTag("Items", itemFound.getTagCompound().getTagList("Items", Constants.NBT.TAG_COMPOUND));
                hammer.getTagCompound().setBoolean("Invert", itemFound.getTagCompound().getBoolean("Invert"));
                hammer.getTagCompound().setBoolean("Modif", true);
            } else
                hammer.getTagCompound().setBoolean("Trash", false);


    }
}
