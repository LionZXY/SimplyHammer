package ru.lionzxy.simlyhammer.commons.recipe;


import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import ru.lionzxy.simlyhammer.commons.items.AddItems;
import ru.lionzxy.simlyhammer.commons.items.Ductape;
import ru.lionzxy.simlyhammer.utils.HammerUtils;

/**
 * Created by LionZXY on 12.11.2015.
 * BetterWorkbench
 */
public class DuctapeRecipe implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting ic, World world) {
        if (ic.getSizeInventory() == 9) {
            if (HammerUtils.isItemContainsInOreDict(ic.getStackInSlot(2), "logWood") &&
                    HammerUtils.isItemContainsInOreDict(ic.getStackInSlot(4), "logWood") &&
                    ic.getStackInSlot(6) != null && ic.getStackInSlot(6).getItem() instanceof Ductape)
                return true;
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting ic) {
        if (ic.getSizeInventory() == 9) {
            if (HammerUtils.isItemContainsInOreDict(ic.getStackInSlot(2), "logWood") &&
                    HammerUtils.isItemContainsInOreDict(ic.getStackInSlot(4), "logWood") &&
                    ic.getStackInSlot(6) != null && ic.getStackInSlot(6).getItem() instanceof Ductape) {
                return new ItemStack(AddItems.stick, 1, 0);
            }

        }

        return null;
    }

    @Override
    public int getRecipeSize() {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(AddItems.stick, 1, 0);
    }
}
