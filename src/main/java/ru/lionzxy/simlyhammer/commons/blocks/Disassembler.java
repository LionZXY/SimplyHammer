package ru.lionzxy.simlyhammer.commons.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.commons.items.AddItems;
import ru.lionzxy.simlyhammer.interfaces.IModifiHammer;

/**
 * Created by LionZXY on 03.12.2015.
 * BetterWorkbench
 */
public class Disassembler extends Block {
    public Disassembler() {
        super(Material.iron);
        this.setBlockName("disassembler");
        this.setCreativeTab(SimplyHammer.tabGeneral);
        GameRegistry.registerBlock(this, "disassembler");

        GameRegistry.addRecipe(new ItemStack(this), "xyx", "yzy", "xmx",
                'x', new ItemStack(AddItems.stick, 1, 1),
                'z', new ItemStack(AddItems.loupe),
                'm', new ItemStack(AddItems.ductape),
                'y', new ItemStack(Blocks.dropper));
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (!world.isRemote && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof IModifiHammer && player.getCurrentEquippedItem().hasTagCompound()) {
            ItemStack is = player.getCurrentEquippedItem();
            if (is.hasTagCompound() && is.getItem() instanceof IModifiHammer)
                for (int i = 0; i < is.getTagCompound().getTagList("ItemStacksInHammer", Constants.NBT.TAG_COMPOUND).tagCount(); i++)
                    world.spawnEntityInWorld(new EntityItem(world, x, y, z, ItemStack.loadItemStackFromNBT(is.getTagCompound().getTagList("ItemStacksInHammer", Constants.NBT.TAG_COMPOUND).getCompoundTagAt(i))));


            player.inventory.setInventorySlotContents(player.inventory.currentItem,
                    new ItemStack(player.getCurrentEquippedItem().getItem(), player.getCurrentEquippedItem().stackSize, player.getCurrentEquippedItem().getItemDamage()));

            return true;
        }
        return false;
    }
}
