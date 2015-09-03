package ru.lionzxy.simlyhammer.recipe;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import ru.lionzxy.simlyhammer.hammers.BasicHammer;

/**
 * Created by nikit on 30.08.2015.
 */
public class RecipeRepair implements IRecipe {

    //Проверка на совпадает ли рецепт.
    @Override
    public boolean matches(InventoryCrafting ic, World p_77569_2_) {
        for (int i = 0; i < ic.getSizeInventory(); i++)
            if (ic.getStackInSlot(i) != null && ic.getStackInSlot(i).getItem() instanceof BasicHammer)
                return true;
        return false;
    }

    //Нужный тебе метод. Выдает output крафта.
    @Override
    public ItemStack getCraftingResult(InventoryCrafting ic) {
        ItemStack hammer = null;
        for (int i = 0; i < ic.getSizeInventory(); i++)
            if (ic.getStackInSlot(i) != null && ic.getStackInSlot(i).getItem() instanceof BasicHammer)
                hammer = ic.getStackInSlot(i).copy();
        if (hammer != null && !hammer.hasTagCompound()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setBoolean("Diamond", false);
            hammer.setTagCompound(tag);
        }
        if (findItem(ic, Items.diamond))
            if (hammer != null)
                if (hammer.hasTagCompound())
                    if (!hammer.getTagCompound().getBoolean("Diamond"))
                        hammer.getTagCompound().setBoolean("Diamond", true);

        if (hammer != null)
            if (findItem(ic, ((BasicHammer) hammer.getItem())))
                hammer.setItemDamage(getDamage(hammer, findItems(ic, ((BasicHammer) hammer.getItem()))));
        return hammer;
    }

    //Советую ставить на 9
    @Override
    public int getRecipeSize() {
        return 9;
    }

    //Не советую оставлять null. Будет крашить с другими модами
    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(Items.iron_pickaxe);
    }

    //Не важно
    int findItems(InventoryCrafting ic, BasicHammer item) {
        int itemsFound = 0;
        for (int i = 0; i < ic.getSizeInventory(); i++)
            if (ic.getStackInSlot(i) != null && item.checkMaterial(ic.getStackInSlot(i)))
                itemsFound++;

        return itemsFound;
    }

    //Не важно
    public boolean findItem(InventoryCrafting ic, Item item) {
        for (int i = 0; i < ic.getSizeInventory(); i++)
            if (ic.getStackInSlot(i) != null && ic.getStackInSlot(i).getItem() == item)
                return true;

        return false;
    }

    int getDamage(ItemStack itemStack, int multi) {
        if ((itemStack.getItemDamage() - (itemStack.getMaxDamage() / 10) * multi) > 0)
            return (itemStack.getItemDamage() - (itemStack.getMaxDamage() / 10) * multi);
        else
            return 0;
    }

}
