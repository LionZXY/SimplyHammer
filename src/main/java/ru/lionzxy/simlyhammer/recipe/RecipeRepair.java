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
import net.minecraftforge.common.util.Constants;
import ru.lionzxy.simlyhammer.config.Config;
import ru.lionzxy.simlyhammer.interfaces.IModifiHammer;
import ru.lionzxy.simlyhammer.items.AddItems;
import ru.lionzxy.simlyhammer.items.TrashItem;

/**
 * Created by nikit on 30.08.2015.
 */
public class RecipeRepair implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting ic, World p_77569_2_) {
        for (int i = 0; i < ic.getSizeInventory(); i++)
            if (ic.getStackInSlot(i) != null && ic.getStackInSlot(i).getItem() instanceof IModifiHammer)
                return true;
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting ic) {
        ItemStack hammer = null;
        for (int i = 0; i < ic.getSizeInventory(); i++)
            if (ic.getStackInSlot(i) != null && ic.getStackInSlot(i).getItem() instanceof IModifiHammer)
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
            tag.setBoolean("Trash", false);
            tag.setBoolean("Vacuum", false);
            tag.setBoolean("Smelt", false);
            hammer.setTagCompound(tag);
        }
        if (findItem(ic, Items.diamond) && hammer != null && hammer.hasTagCompound() && Config.MDiamond && !hammer.getTagCompound().getBoolean("Diamond") && ((IModifiHammer) hammer.getItem()).getHammerSettings().getMDiamond()) {
            hammer.getTagCompound().setBoolean("Diamond", true);
            hammer.getTagCompound().setBoolean("Modif", true);
        }
        if (findAxe(ic) != null && hammer != null && hammer.hasTagCompound() && Config.MAxe && ((IModifiHammer) hammer.getItem()).getHammerSettings().getMAxe()) {
            ItemStack itemStack = findAxe(ic);
            hammer.getTagCompound().setInteger("Axe", ((ItemAxe) itemStack.getItem()).func_150913_i().getHarvestLevel());
            hammer.getTagCompound().setDouble("AxeSpeed", ((ItemAxe) itemStack.getItem()).func_150913_i().getEfficiencyOnProperMaterial());
            hammer.getTagCompound().setBoolean("Modif", true);
        }
        if (findShovel(ic) != null && hammer != null && hammer.hasTagCompound() && Config.MShovel && ((IModifiHammer) hammer.getItem()).getHammerSettings().getMShovel()) {
            ItemStack itemStack = findShovel(ic);
            hammer.getTagCompound().setInteger("Shovel", ((ItemSpade) itemStack.getItem()).func_150913_i().getHarvestLevel());
            hammer.getTagCompound().setDouble("ShovelSpeed", ((ItemSpade) itemStack.getItem()).func_150913_i().getEfficiencyOnProperMaterial());
            hammer.getTagCompound().setBoolean("Modif", true);
        }
        if (findItem(ic, Item.getItemFromBlock(Blocks.torch)) && hammer != null && hammer.hasTagCompound() && Config.MTorch && ((IModifiHammer) hammer.getItem()).getHammerSettings().getMTorch()) {
            if (!hammer.getTagCompound().getBoolean("Torch"))
                hammer.getTagCompound().setBoolean("Torch", true);
            else
                hammer.getTagCompound().setBoolean("Torch", false);
            hammer.getTagCompound().setBoolean("Modif", true);
        }

        if (findItem(ic, AddItems.trash) && hammer != null && hammer.hasTagCompound() && Config.MTrash && ((IModifiHammer) hammer.getItem()).getHammerSettings().getMTrash())
            if (!hammer.getTagCompound().getBoolean("Trash")) {
                hammer.getTagCompound().setBoolean("Trash", true);
                hammer.getTagCompound().setTag("Items", getItem(ic, AddItems.trash).getTagCompound().getTagList("Items", Constants.NBT.TAG_COMPOUND));
                hammer.getTagCompound().setBoolean("Invert", getItem(ic, AddItems.trash).getTagCompound().getBoolean("Invert"));
                hammer.getTagCompound().setBoolean("Modif", true);
            } else
                hammer.getTagCompound().setBoolean("Trash", false);


        if (findItem(ic, AddItems.vacuum) && hammer != null && hammer.hasTagCompound() && Config.MVacuum && ((IModifiHammer) hammer.getItem()).getHammerSettings().getMVacuum())
            if (!hammer.getTagCompound().getBoolean("Vacuum")){
                hammer.getTagCompound().setBoolean("Vacuum", true);
                hammer.getTagCompound().setBoolean("Modif", true);}
            else
                hammer.getTagCompound().setBoolean("Vacuum", false);

        if (findItem(ic, AddItems.autosmelt) && hammer != null && hammer.hasTagCompound() && Config.MSmelt && ((IModifiHammer) hammer.getItem()).getHammerSettings().getMSmelt())
            if (!hammer.getTagCompound().getBoolean("Smelt")){
                hammer.getTagCompound().setBoolean("Smelt", true);
                hammer.getTagCompound().setTag("ItemsSmelt", getItem(ic, AddItems.autosmelt).getTagCompound().getTagList("Items", Constants.NBT.TAG_COMPOUND));
                hammer.getTagCompound().setBoolean("InvertSmelt", getItem(ic, AddItems.autosmelt).getTagCompound().getBoolean("Invert"));
                hammer.getTagCompound().setBoolean("Modif", true);}
            else
                hammer.getTagCompound().setBoolean("Smelt", false);

        if (hammer != null && Config.repair && ((IModifiHammer) hammer.getItem()).getHammerSettings().isRepair())
            if (findItem(ic, hammer.getItem()))
                hammer.setItemDamage(getDamage(hammer, findItems(ic, ((IModifiHammer) hammer.getItem()))));
        return hammer;
    }

    @Override
    public int getRecipeSize() {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(Items.iron_pickaxe);
    }

    int findItems(InventoryCrafting ic, IModifiHammer item) {
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

    public ItemStack getItem(InventoryCrafting ic, Item item) {
        for (int i = 0; i < ic.getSizeInventory(); i++)
            if (ic.getStackInSlot(i) != null && ic.getStackInSlot(i).getItem() == item)
                return ic.getStackInSlot(i);
        return null;
    }

    int getDamage(ItemStack itemStack, int multi) {
        if ((itemStack.getItemDamage() - (itemStack.getMaxDamage() / 10) * multi) > 0)
            return (itemStack.getItemDamage() - (itemStack.getMaxDamage() / 10) * multi);
        else
            return 0;
    }

}
