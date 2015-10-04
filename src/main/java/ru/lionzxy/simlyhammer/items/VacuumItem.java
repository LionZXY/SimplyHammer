package ru.lionzxy.simlyhammer.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.interfaces.IVacuum;

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
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
        if(!is.hasTagCompound())
            list.add(EnumChatFormatting.RED + "PLACE ITEM IN CRAFT WINDOW!!!");
    }
}
