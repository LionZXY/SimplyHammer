package ru.lionzxy.simlyhammer.recipe;

import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ru.lionzxy.simlyhammer.hammers.BasicHammer;
import ru.lionzxy.simlyhammer.utils.AddHammers;

/**
 * Created by nikit on 06.09.2015.
 */
public class BindingRecipe extends WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRecipe {
    public BindingRecipe(ItemStack outputItem, ItemStack requiredItem) {
        super(outputItem, requiredItem);
        BindingRegistry.bindingRecipes.add(this);
    }

    public boolean doesRequiredItemMatch(ItemStack testStack) {
        if (!(testStack == null) && testStack.getItem() instanceof BasicHammer) {
            ItemStack output = new ItemStack(AddHammers.BMHammer);
            if(testStack.hasTagCompound()){
            NBTTagCompound tag = testStack.getTagCompound();
            System.out.println(((BasicHammer) testStack.getItem()).toolMaterial.getEfficiencyOnProperMaterial());
            tag.setDouble("HammerSpeed", ((BasicHammer) testStack.getItem()).toolMaterial.getEfficiencyOnProperMaterial());
            System.out.println(((BasicHammer) testStack.getItem()).toolMaterial.getHarvestLevel());
            tag.setInteger("HammerHarvestLevel", ((BasicHammer) testStack.getItem()).toolMaterial.getHarvestLevel());
            output.setTagCompound(testStack.getTagCompound());
            this.outputItem = output;}else return false;
            return testStack.getItem() instanceof BasicHammer;
        }
        return false;
    }

    @Override
    public ItemStack getResult(ItemStack inputItem) {
        ItemStack output = new ItemStack(AddHammers.BMHammer);
        NBTTagCompound tag = inputItem.getTagCompound();
        tag.setDouble("HammerSpeed", ((BasicHammer) inputItem.getItem()).toolMaterial.getEfficiencyOnProperMaterial());
        tag.setInteger("HammerHarvestLevel", ((BasicHammer) inputItem.getItem()).toolMaterial.getHarvestLevel());
        output.setTagCompound(inputItem.getTagCompound());
        return output;
    }
}
