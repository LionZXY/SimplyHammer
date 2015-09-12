package ru.lionzxy.simlyhammer.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.utils.HammerTab;

/**
 * Created by nikit on 12.09.2015.
 */
public class TrashItem extends Item {
    public TrashItem() {
        this.setCreativeTab(SimplyHammer.tabGeneral);
        this.setUnlocalizedName("trashitem");
        GameRegistry.registerItem(this, "trashitem");
    }



    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
        if(!world.isRemote)
        player.openGui(SimplyHammer.instance, 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return is;
    }
}
