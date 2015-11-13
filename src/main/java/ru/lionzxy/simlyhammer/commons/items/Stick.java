package ru.lionzxy.simlyhammer.commons.items;


import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.lionzxy.simlyhammer.SimplyHammer;

import java.util.List;

/**
 * Created by LionZXY on 17.10.2015.
 * SimplyHammer v0.9
 */
public class Stick extends Item {
    private ItemStack emptyItem = null;
    private static int maxDamage = 10;
    public IIcon[] icons = new IIcon[2];

    public Stick() {
        this.setHasSubtypes(true);
        this.setCreativeTab(SimplyHammer.tabGeneral);
        this.setUnlocalizedName("itemHammer");
        GameRegistry.registerItem(this, "stickHammer");


        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 16, 1),
                "xxx", " x ", " x ",
                'x', "ingotIron"));
    }


    @Override
    public void registerIcons(IIconRegister reg) {
        for (int i = 0; i < icons.length; i++) {
            this.icons[i] = reg.registerIcon("simplyhammer:metaitem/" + i);
        }
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        if (meta > icons.length - 1)
            meta = 0;

        return this.icons[meta];
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < icons.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + "_" + stack.getItemDamage();
    }


}
