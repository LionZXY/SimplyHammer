package ru.lionzxy.simlyhammer.hammers.core;

import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import ru.lionzxy.simlyhammer.hammers.BasicHammer;
import ru.lionzxy.simlyhammer.libs.HammerSettings;

import java.util.List;

/**
 * Created by LionZXY on 17.10.2015.
 * SimplyHammer v0.9
 */
public abstract class NBTHammer extends BasicHammer {
    public NBTHammer(HammerSettings hammerSettings) {
        super(hammerSettings);
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
        if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            if (itemStack.hasTagCompound()) {
                //if (!itemStack.getTagCompound().getString("ownerName").equals("") && itemStack.getTagCompound() != null)
                list.add(StatCollector.translateToLocal("information.usesLeft") + " " + this.usesLeft(itemStack));
                list.add(StatCollector.translateToLocal("information.harvestLevel") + " " + itemStack.getTagCompound().getInteger("HammerHarvestLevel"));
                list.add(StatCollector.translateToLocal("information.efficiency") + " " + itemStack.getTagCompound().getDouble("HammerSpeed"));
                if (itemStack.hasTagCompound() && itemStack.getTagCompound().getBoolean("Modif")) {
                    list.add("");
                    list.add(StatCollector.translateToLocal("information.modification"));
                    if (itemStack.getTagCompound().getBoolean("Torch"))
                        list.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("modification.Torch"));
                    if (itemStack.getTagCompound().getBoolean("Diamond"))
                        list.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("modification.Diamond"));
                    if (itemStack.getTagCompound().getInteger("Axe") != 0)
                        list.add(EnumChatFormatting.WHITE + StatCollector.translateToLocal("modification.Axe") + " " + itemStack.getTagCompound().getInteger("Axe") + StatCollector.translateToLocal("modification.AxeSpeed") + " " + itemStack.getTagCompound().getDouble("AxeSpeed"));
                    if (itemStack.getTagCompound().getInteger("Shovel") != 0)
                        list.add(EnumChatFormatting.WHITE + StatCollector.translateToLocal("modification.Shovel") + " " + itemStack.getTagCompound().getInteger("Shovel") + StatCollector.translateToLocal("modification.ShovelSpeed") + " " + itemStack.getTagCompound().getDouble("ShovelSpeed"));
                    if (itemStack.getTagCompound().getBoolean("Trash"))
                        list.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("modification.Trash"));

                }
            } else list.add(StatCollector.translateToLocal("information.NotHaveTagCompound"));
        } else list.add(StatCollector.translateToLocal("information.ShiftDialog"));
        this.addIInfo(itemStack, list);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (block.getHarvestTool(meta) == null)
            return 0.5F;
        if (stack.hasTagCompound() && block.getHarvestTool(meta).equals("pickaxe"))
            return (float) stack.getTagCompound().getDouble("HammerSpeed");
        if (stack.hasTagCompound() && block.getHarvestTool(meta).equals("axe") && stack.getTagCompound().getInteger("Axe") != 0)
            return (float) stack.getTagCompound().getDouble("AxeSpeed");
        if (stack.hasTagCompound() && block.getHarvestTool(meta).equals("shovel") && stack.getTagCompound().getInteger("Shovel") != 0)
            return (float) stack.getTagCompound().getDouble("ShovelSpeed");

        return 0.5F;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        // invalid query or wrong toolclass
        if (usesLeftBlock(stack) < 2)
            return -1;
        if (toolClass == null)
            return -1;
        if (toolClass.equals("axe") && stack.hasTagCompound() && stack.getTagCompound().getInteger("Axe") != 0)
            return stack.getTagCompound().getInteger("Axe");
        if (toolClass.equals("shovel") && stack.hasTagCompound() && stack.getTagCompound().getInteger("Shovel") != 0)
            return stack.getTagCompound().getInteger("Shovel");
        if (!toolClass.equals("pickaxe"))
            return -1;
        // tadaaaa
        if (stack.hasTagCompound())
            return stack.getTagCompound().getInteger("HammerHarvestLevel");
        else return toolMaterial.getHarvestLevel();
    }

    @Override
    public boolean onBlockDestroyed(ItemStack is, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_) {
        if ((double) p_150894_3_.getBlockHardness(p_150894_2_, p_150894_4_, p_150894_5_, p_150894_6_) != 0.0D && hammerSettings.getDurability() != -1 && !hammerSettings.isInfinity()) {
            this.damageItem(is,p_150894_7_);
        }
        return true;
    }

    public boolean hitEntity(ItemStack is, EntityLivingBase player, EntityLivingBase p_77644_3_) {
        return this.damageItem(is,player);
    }

    protected abstract int usesLeftBlock(ItemStack is);

    protected abstract String usesLeft(ItemStack is);

    protected abstract void addIInfo(ItemStack is, List list);

    protected abstract boolean damageItem(ItemStack is, EntityLivingBase player);
}