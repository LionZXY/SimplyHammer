package ru.lionzxy.simlyhammer.commons.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.lionzxy.simlyhammer.SimplyHammer;

/**
 * Created by nikit_000 on 06.10.2015.
 */
public class Loupe extends Item {
    private static int translateToLocal = -1;

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
        int tmp = 0;
        while (!StatCollector.translateToLocal("information.loupe." + tmp).equalsIgnoreCase("information.loupe." + tmp)) {
            tmp += 1;
        }
        translateToLocal = tmp;
    }


    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ) {
        if (world.isRemote) {
            return true;
        } else {
            Block blockop = world.getBlock(x, y, z);
            if (blockop != null) {
                String localizeName = StatCollector.translateToLocal("information.loupe.NotFound.LocalizeName"),
                        unlocalizeName = StatCollector.translateToLocal("information.loupe.NotFound.UnLocalizeName"),
                        updateTickInNanosec = StatCollector.translateToLocal("information.loupe.NotFound.tick"),
                        updateTickInMilisec = StatCollector.translateToLocal("information.loupe.NotFound.tick"),
                        texturePath = StatCollector.translateToLocal("information.loupe.NotFound.TexturePath"),
                        harvestTool = StatCollector.translateToLocal("information.loupe.NotFound.HarvestLevel");

                if (blockop.getHarvestTool(world.getBlockMetadata(x, y, z)) != null)
                    harvestTool = blockop.getHarvestTool(world.getBlockMetadata(x, y, z));

                if (Item.getItemFromBlock(blockop) != null) {
                    localizeName = new ItemStack(Item.getItemFromBlock(blockop)).getDisplayName();
                    unlocalizeName = Item.getItemFromBlock(blockop).getUnlocalizedName();
                        texturePath = blockop.getIcon(side, world.getBlockMetadata(x, y, z)).getIconName();
                }

                if (world.getTileEntity(x, y, z) != null) {
                    TileEntity te = world.getTileEntity(x, y, z);
                    //GameRegistry

                    long startTime = System.nanoTime();
                    te.updateEntity();
                    long endTime = System.nanoTime();
                    updateTickInNanosec = (endTime - startTime) + "";
                    updateTickInMilisec = (float) (endTime - startTime) / 1000000 + "";
                }
                for (int i = 0; i < translateToLocal; i++) {
                    player.addChatComponentMessage(new ChatComponentText(
                            StatCollector.translateToLocal("information.loupe." + i).replaceAll("%localizeName%", localizeName).
                                    replaceAll("%unlocalizeName%", unlocalizeName).replaceAll("%harvestTool%", harvestTool)
                                    .replaceAll("%updateTickInNanosec%", updateTickInNanosec).replaceAll("%updateTickInMilisec%", updateTickInMilisec)
                                    .replaceAll("%texturepath%", texturePath)
                    ));
                }
            }
            return false;
        }
    }

}
