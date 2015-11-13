package ru.lionzxy.simlyhammer.commons.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.commons.recipe.DuctapeRecipe;

/**
 * Created by LionZXY on 17.10.2015.
 * SimplyHammer v0.9
 */
public class Ductape extends Item {
    public Ductape() {
        this.setContainerItem(this);
        this.setCreativeTab(SimplyHammer.tabGeneral);
        this.setUnlocalizedName("ductape");
        this.setMaxDamage(10);
        this.setTextureName("simplyhammer:ductape");
        GameRegistry.addRecipe(new DuctapeRecipe());
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AddItems.stick, 1, 0),
                "  x", " x ", "z  ",
                'x', "logWood",
                'z', new ItemStack(this)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(this, 1, 0), "slimeball", new ItemStack(Items.paper), new ItemStack(Items.coal)));
        GameRegistry.registerItem(this, "ductape");
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack is) {

        return false;
    }

    public ItemStack getContainerItem(ItemStack is) {
        is.setItemDamage(is.getItemDamage() + 1);
        return is;
    }
}
