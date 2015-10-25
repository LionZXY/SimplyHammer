package ru.lionzxy.simlyhammer.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.utils.AddHammers;

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
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), " x ", "xyx", " xz",
                'x', "paneGlass",
                'y', "plankWood",
                'z', new ItemStack(AddItems.stick, 1, 0)));
    }


    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ) {
        if (world.isRemote) {

            return true;
        } else {
            Block blockop = world.getBlock(x, y, z);
            if (blockop != null) {
                player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GRAY + "=============="));
                if (new ItemStack(Item.getItemFromBlock(blockop)).getItem() != null)
                    player.addChatComponentMessage(new ChatComponentText("Name: " + new ItemStack(Item.getItemFromBlock(blockop)).getDisplayName()));
                if (world.getTileEntity(x, y, z) != null) {
                    TileEntity te = world.getTileEntity(x, y, z);

                    long startTime = System.nanoTime();
                    te.updateEntity();
                    long endTime = System.nanoTime();
                    player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "Nanoseconds per tick: " + EnumChatFormatting.RED + (endTime - startTime) + EnumChatFormatting.WHITE + " (" + (float) (endTime - startTime) / 1000000 + " Ms)"));
                }
                player.addChatComponentMessage(new ChatComponentText("Harvest Tool: " + blockop.getHarvestTool(world.getBlockMetadata(x, y, z))));
                player.addChatComponentMessage(new ChatComponentText("Harvest Level: " + blockop.getHarvestLevel(world.getBlockMetadata(x, y, z))));
                player.addChatComponentMessage(new ChatComponentText("Unlocalize name: " + blockop.getUnlocalizedName()));

                player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GRAY + "=============="));
            }
            return false;
        }
    }

}
