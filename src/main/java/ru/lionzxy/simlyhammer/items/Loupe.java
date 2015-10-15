package ru.lionzxy.simlyhammer.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import ru.lionzxy.simlyhammer.SimplyHammer;

/**
 * Created by nikit_000 on 06.10.2015.
 */
public class Loupe extends Item {
    Loupe() {
        super();
        super.setCreativeTab(SimplyHammer.tabGeneral);
        super.setMaxStackSize(1);
        super.setUnlocalizedName("loupe");
        super.setTextureName("simplyhammer:loupe");
        GameRegistry.registerItem(this, "loupe");
    }


    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ) {
        if (world.isRemote)
            return true;
        Block blockop = world.getBlock(x, y, z);
        if(blockop != null){
            player.addChatComponentMessage(new ChatComponentText("Name: " + new ItemStack(blockop).getDisplayName()));
            player.addChatComponentMessage(new ChatComponentText("Material: " + blockop.getMaterial()));
            player.addChatComponentMessage(new ChatComponentText("Harvest Tool: " + blockop.getHarvestTool(world.getBlockMetadata(x,y,z))));
            player.addChatComponentMessage(new ChatComponentText("Harvest Level: " + blockop.getHarvestLevel(world.getBlockMetadata(x,y,z))));
            player.addChatComponentMessage(new ChatComponentText("" + blockop.getUnlocalizedName()));
        }
        return false;
    }

}
