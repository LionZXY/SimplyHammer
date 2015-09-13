package ru.lionzxy.simlyhammer.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import ru.lionzxy.simlyhammer.SimplyHammer;

/**
 * Created by nikit on 13.09.2015.
 */
public class AutoSmeltItem extends Item{
    public AutoSmeltItem(){
        this.setCreativeTab(SimplyHammer.tabGeneral);
        this.setUnlocalizedName("smeltitem");
        this.setTextureName("simplyhammer:smeltitem");
        this.setMaxStackSize(1);
        GameRegistry.registerItem(this, "smeltitem");
    }

    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
        if (!world.isRemote)
            player.openGui(SimplyHammer.instance, 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return is;
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
                if (!itemStack.getTagCompound().getBoolean("InvertSmelt") && ItemStack.loadItemStackFromNBT(item).isItemEqual(smelt))
                    return FurnaceRecipes.smelting().getSmeltingResult(smelt) != null ? FurnaceRecipes.smelting().getSmeltingResult(smelt) : smelt;
                else if (itemStack.getTagCompound().getBoolean("InvertSmelt") && !ItemStack.loadItemStackFromNBT(item).isItemEqual(smelt))
                    return FurnaceRecipes.smelting().getSmeltingResult(smelt) != null ? FurnaceRecipes.smelting().getSmeltingResult(smelt) : smelt;
            }
        }
        return smelt;
    }
}
