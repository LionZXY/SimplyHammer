package ru.lionzxy.simlyhammer.commons.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;
import ru.lionzxy.simlyhammer.commons.items.AddItems;

/**
 * Created by LionZXY on 08.09.2015.
 * SimplyHammer v0.9
 */
public class InvertRecipe implements IRecipe {
    public InvertRecipe(){

        RecipeSorter.setCategory(this.getClass(), RecipeSorter.Category.SHAPELESS);
    }
    @Override
    public boolean matches(InventoryCrafting ic, World p_77569_2_) {
        for (int i = 0; i < ic.getSizeInventory(); i++)
            if (ic.getStackInSlot(i) != null && (ic.getStackInSlot(i).getItem() == AddItems.trash || ic.getStackInSlot(i).getItem() == AddItems.autosmelt))
                return true;
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting ic) {
        ItemStack trash = null;
        for (int i = 0; i < ic.getSizeInventory(); i++)
            if (ic.getStackInSlot(i) != null && (ic.getStackInSlot(i).getItem() == AddItems.trash || ic.getStackInSlot(i).getItem() == AddItems.autosmelt))
                trash = ic.getStackInSlot(i).copy();
        if(trash != null && !trash.hasTagCompound())
            trash.setTagCompound(new NBTTagCompound());
        if (trash != null && trash.hasTagCompound())
            for (int i = 0; i < ic.getSizeInventory(); i++)
                if (ic.getStackInSlot(i) != null && ic.getStackInSlot(i).getItem() == Item.getItemFromBlock(Blocks.redstone_torch))
                    trash.getTagCompound().setBoolean("Invert", !trash.getTagCompound().getBoolean("Invert"));

        return trash;
    }

    @Override
    public int getRecipeSize() {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(AddItems.trash);
    }
}
