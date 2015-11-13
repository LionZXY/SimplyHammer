package ru.lionzxy.simlyhammer.commons.recipe;

import ic2.api.item.IC2Items;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import ru.lionzxy.simlyhammer.commons.items.AddItems;
import ru.lionzxy.simlyhammer.utils.AddHammers;

/**
 * Created by LionZXY on 16.10.2015.
 * SimplyHammer v0.9
 */
public class IC2DrillCrafting implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting ic, World p_77569_2_) {
        for (int i = 0; i < ic.getSizeInventory(); i++)
            if (ic.getStackInSlot(i) == null && i != 6 && i != 8)
                return false;
        if (!containsOresDict(ic.getStackInSlot(0), "ingotSteel"))
            return false;
        if (!containsOresDict(ic.getStackInSlot(1), "drillEu"))
            return false;
        if (!containsOresDict(ic.getStackInSlot(2), "ingotSteel"))
            return false;
        if (!containsOresDict(ic.getStackInSlot(3), "blockSteel"))
            return false;
        if (IC2Items.getItem("mfeUnit") == null || ic.getStackInSlot(4).getItem() != IC2Items.getItem("mfeUnit").getItem())
            return false;
        if (!containsOresDict(ic.getStackInSlot(5), "blockSteel"))
            return false;
        if (!new ItemStack(AddItems.stick, 1, 0).isItemEqual(ic.getStackInSlot(7)))
            return false;
        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting ic) {
        ItemStack output = new ItemStack(AddHammers.IC2Hammer);
        ItemStack inputItem = ic.getStackInSlot(1);
        output.setTagCompound(new NBTTagCompound());
        NBTTagCompound tag = output.getTagCompound();
        if (IC2Items.getItem("miningDrill") != null && inputItem.getItem() == IC2Items.getItem("miningDrill").getItem()) {
            tag.setDouble("HammerSpeed", 8.0F);
            tag.setInteger("HammerHarvestLevel", 2);
        } else if (IC2Items.getItem("diamondDrill") != null && inputItem.getItem() == IC2Items.getItem("diamondDrill").getItem()) {
            tag.setDouble("HammerSpeed", 16.0F);
            tag.setInteger("HammerHarvestLevel", 3);
        } else if (IC2Items.getItem("iridiumDrill") != null && inputItem.getItem() == IC2Items.getItem("iridiumDrill").getItem()) {
            tag.setDouble("HammerSpeed", 24.0F);
            tag.setInteger("HammerHarvestLevel", 3);
            output.addEnchantment(Enchantment.fortune, 3);
        }
        output.setTagCompound(tag);

        return output;
    }

    @Override
    public int getRecipeSize() {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(AddHammers.IC2Hammer);
    }

    public static boolean containsOresDict(ItemStack is, String oredict) {
        int oreDictId = OreDictionary.getOreID(oredict);
        for (int id : OreDictionary.getOreIDs(new ItemStack(is.getItem())))
            if (id == oreDictId)
                return true;
        return false;
    }
}
