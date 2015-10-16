package ru.lionzxy.simlyhammer.recipe;

import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ru.lionzxy.simlyhammer.hammers.BasicHammer;
import ru.lionzxy.simlyhammer.utils.AddHammers;

/**
 * Created by LionZXY on 08.09.2015.
 * SimplyHammer v0.9
 */
public class BindingRecipe extends WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRecipe {
    public BindingRecipe(ItemStack outputItem, ItemStack requiredItem) {
        super(outputItem, requiredItem);
        BindingRegistry.bindingRecipes.add(this);
    }

    public boolean doesRequiredItemMatch(ItemStack testStack) {
        if (!(testStack == null) && testStack.getItem() instanceof BasicHammer) {
            ItemStack output = new ItemStack(AddHammers.BMHammer);
            if (testStack.hasTagCompound()) {
                NBTTagCompound tag = testStack.getTagCompound();
                tag.setDouble("HammerSpeed", ((BasicHammer) testStack.getItem()).getHammerSettings().getEffiency());
                tag.setInteger("HammerHarvestLevel", ((BasicHammer) testStack.getItem()).getHammerSettings().getHarvestLevel());
                output.setTagCompound(testStack.getTagCompound());
                this.outputItem = output;
            } else return false;
            return testStack.getItem() instanceof BasicHammer;
        }
        return false;
    }

    @Override
    public ItemStack getResult(ItemStack inputItem) {
        ItemStack output = new ItemStack(AddHammers.BMHammer);
        NBTTagCompound tag = inputItem.getTagCompound();
        tag.setDouble("HammerSpeed", ((BasicHammer) inputItem.getItem()).getHammerSettings().getEffiency());
        tag.setInteger("HammerHarvestLevel", ((BasicHammer) inputItem.getItem()).getHammerSettings().getHarvestLevel());
        output.setTagCompound(inputItem.getTagCompound());
        return output;
    }
}
