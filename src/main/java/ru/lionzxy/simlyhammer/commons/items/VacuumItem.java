package ru.lionzxy.simlyhammer.commons.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.lionzxy.simlyhammer.SimplyHammer;

import java.util.List;

/**
 * Created by nikit on 12.09.2015.
 */
public class VacuumItem extends Item {
    public VacuumItem() {
        this.setCreativeTab(SimplyHammer.tabGeneral);
        this.setUnlocalizedName("vacumitem");
        this.setTextureName("simplyhammer:vacumitem");
        this.setMaxStackSize(1);
        GameRegistry.registerItem(this, "vacumitem");
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this),
                " y ", "yxy", " y ",
                'x', Items.ender_pearl,
                'y', Blocks.hopper));
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
        if(!is.hasTagCompound())
            is.setTagCompound(new NBTTagCompound());
    }
}
