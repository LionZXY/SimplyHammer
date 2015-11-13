package ru.lionzxy.simlyhammer.commons.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.lionzxy.simlyhammer.SimplyHammer;

import java.util.List;

/**
 * Created by nikit on 13.09.2015.
 */
public class AutoSmeltItem extends Item {
    @SideOnly(Side.CLIENT)
    protected IIcon invertIcon;

    public AutoSmeltItem() {
        this.setCreativeTab(SimplyHammer.tabGeneral);
        this.setUnlocalizedName("smeltitem");
        this.setTextureName("simplyhammer:smeltitem");
        this.setMaxStackSize(1);
        GameRegistry.registerItem(this, "smeltitem");
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this),
                "xyx", "yxy", "xyx",
                'x', Blocks.furnace,
                'y', "blockCoal"));
    }

    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
        if (!world.isRemote)
            player.openGui(SimplyHammer.instance, 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return is;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
        if (is.hasTagCompound()) {
            if (is.getTagCompound().getBoolean("Invert"))
                list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("trash.Inverted"));
            list.add(StatCollector.translateToLocal("trash.IgnoreList"));
            for (int i = 0; i < is.getTagCompound().getTagList("Items", Constants.NBT.TAG_COMPOUND).tagCount(); ++i) {
                NBTTagCompound item = /*(NBTTagCompound)*/ is.getTagCompound().getTagList("Items", Constants.NBT.TAG_COMPOUND).getCompoundTagAt(i);
                list.add(ItemStack.loadItemStackFromNBT(item).getDisplayName());
            }
        }
    }


    public static ItemStack getSmelt(ItemStack smelt, ItemStack itemStack) {
        if (!itemStack.hasTagCompound() || !itemStack.getTagCompound().getBoolean("Smelt"))
            return smelt;
        NBTTagList items = itemStack.getTagCompound().getTagList("ItemsSmelt", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound item = /*(NBTTagCompound)*/ items.getCompoundTagAt(i);
            byte slot = item.getByte("Slot");

            // Just double-checking that the saved slot index is within our inventory array bounds
            if (slot >= 0 && slot < 9) {
                if (!itemStack.getTagCompound().getBoolean("Invert") && ItemStack.loadItemStackFromNBT(item).isItemEqual(smelt))
                    return FurnaceRecipes.smelting().getSmeltingResult(smelt) != null ? getOneSmelt(smelt) : smelt;
                else if (itemStack.getTagCompound().getBoolean("Invert") && !ItemStack.loadItemStackFromNBT(item).isItemEqual(smelt))
                    return FurnaceRecipes.smelting().getSmeltingResult(smelt) != null ? getOneSmelt(smelt) : smelt;
            }
        }
        return smelt;
    }

    private static ItemStack getOneSmelt(ItemStack smelt) {
        int tmp = smelt.stackSize;
        smelt = FurnaceRecipes.smelting().getSmeltingResult(smelt);
        smelt.stackSize = tmp;
        return smelt;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack is)
    {   if(!is.hasTagCompound() || !is.getTagCompound().getBoolean("Invert"))
        return itemIcon;
    else return invertIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_)
    {
        this.invertIcon = p_94581_1_.registerIcon("simplyhammer:smeltitemInvert");
        this.itemIcon = p_94581_1_.registerIcon("simplyhammer:smeltitem");
    }
}
