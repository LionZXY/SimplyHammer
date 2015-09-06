package ru.lionzxy.simlyhammer.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import ru.lionzxy.simlyhammer.config.Config;
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
            tag.setBoolean("Modif", false);
            tag.setBoolean("Diamond", false);
            tag.setInteger("Shovel", 0);
            tag.setInteger("ShovelSpeed", 0);
            tag.setInteger("Axe", 0);
            tag.setDouble("AxeSpeed", 0);
            tag.setBoolean("Torch", false);
            hammer.setTagCompound(tag);
        }
        if (findItem(ic, Items.diamond) && hammer != null && hammer.hasTagCompound() && Config.MDiamond && !hammer.getTagCompound().getBoolean("Diamond") && ((BasicHammer) hammer.getItem()).MDiamond) {
            hammer.getTagCompound().setBoolean("Diamond", true);
            hammer.getTagCompound().setBoolean("Modif", true);
        }
        if (findAxe(ic) != null && hammer != null && hammer.hasTagCompound() && Config.MAxe && ((BasicHammer) hammer.getItem()).MAxe) {
            ItemStack itemStack = findAxe(ic);
            hammer.getTagCompound().setInteger("Axe", ((ItemAxe) itemStack.getItem()).func_150913_i().getHarvestLevel());
            hammer.getTagCompound().setDouble("AxeSpeed", ((ItemAxe) itemStack.getItem()).func_150913_i().getEfficiencyOnProperMaterial());
            hammer.getTagCompound().setBoolean("Modif", true);
        }
        if (findShovel(ic) != null && hammer != null && hammer.hasTagCompound() && Config.MShovel && ((BasicHammer) hammer.getItem()).MShovel) {
            ItemStack itemStack = findShovel(ic);
            hammer.getTagCompound().setInteger("Shovel", ((ItemSpade) itemStack.getItem()).func_150913_i().getHarvestLevel());
            hammer.getTagCompound().setDouble("ShovelSpeed", ((ItemSpade) itemStack.getItem()).func_150913_i().getEfficiencyOnProperMaterial());
            hammer.getTagCompound().setBoolean("Modif", true);
        }
        if (findItem(ic, Item.getItemFromBlock(Blocks.torch)) && hammer != null && hammer.hasTagCompound() &&Config.MTorch && ((BasicHammer) hammer.getItem()).MTorch) {
            if (!hammer.getTagCompound().getBoolean("Torch"))
                hammer.getTagCompound().setBoolean("Torch", true);
            else
                hammer.getTagCompound().setBoolean("Torch", false);
            hammer.getTagCompound().setBoolean("Modif", true);
        }


        if (hammer != null && Config.repair)
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

    int findItems(InventoryCrafting ic, BasicHammer item) {
        int itemsFound = 0;
        for (int i = 0; i < ic.getSizeInventory(); i++)
            if (ic.getStackInSlot(i) != null && item.checkMaterial(ic.getStackInSlot(i)))
                itemsFound++;

        return itemsFound;
    }

    public ItemStack findShovel(InventoryCrafting ic) {
        for (int i = 0; i < ic.getSizeInventory(); i++)
            if (ic.getStackInSlot(i) != null && ic.getStackInSlot(i).getItem() instanceof ItemSpade)
                return ic.getStackInSlot(i);

        return null;
    }

    public ItemStack findAxe(InventoryCrafting ic) {
        for (int i = 0; i < ic.getSizeInventory(); i++)
            if (ic.getStackInSlot(i) != null && ic.getStackInSlot(i).getItem() instanceof ItemAxe)
                return ic.getStackInSlot(i);

        return null;
    }


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
