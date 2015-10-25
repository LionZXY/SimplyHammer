package ru.lionzxy.simlyhammer.hammers;

import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.Constants;
import org.lwjgl.input.Keyboard;
import ru.lionzxy.simlyhammer.items.AddItems;
import ru.lionzxy.simlyhammer.libs.HammerSettings;
import ru.lionzxy.simlyhammer.utils.AddHammers;
import ru.lionzxy.simlyhammer.utils.HammerUtils;
import ru.lionzxy.simlyhammer.utils.ReflectionHelper;

import java.util.List;

/**
 * Created by LionZXY on 20.10.2015.
 * SimplyHammer v0.9
 */
public class RFHammer extends BasicHammer implements IEnergyContainerItem {
    public static int cost = 200;

    public RFHammer() {
        super(new HammerSettings("rfhammer", 1, 3, 24F, 2024000 * 4, null, false).setRepir(false));
        GameRegistry.registerItem(this, "rfhammer");
        System.out.println("OBJ: " + Item.itemRegistry.getObject("RedstoneArsenal:material:128"));
        if (Loader.isModLoaded("RedstoneArsenal"))
            GameRegistry.addRecipe(new ItemStack(this),
                    "zzz", "xpx", " p ",
                    'x', ReflectionHelper.getItemStackRA("plateFlux"),
                    'p', ReflectionHelper.getItemStackRA("rodObsidianFlux"),
                    'z', ReflectionHelper.getItemStackRA("ingotElectrumFlux"));
        else if(Loader.isModLoaded("ThermalExpansion"))
            GameRegistry.addRecipe(new ItemStack(this),
                    "zzz", "dxd", " p ",
                    'x', HammerUtils.getItemFromString("ThermalExpansion:Cell:3"),
                    'p', new ItemStack(AddItems.stick, 1, 0),
                    'z', HammerUtils.getItemFromString("ThermalFoundation:material:74"),
                    'd', HammerUtils.getItemFromString("ThermalFoundation:Storage:10"));
            else if (Loader.isModLoaded("EnderIO"))
            AddHammers.addCraft(this, null, "blockElectricalSteel", "ingotElectricalSteel");
    }


    @Override
    public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
        int receive = 8096 * 4;
        if(simulate)
            return receive;
        if (maxReceive < receive)
            receive = maxReceive;
        if (!container.hasTagCompound())
            container.setTagCompound(new NBTTagCompound());
        if ((this.getMaxEnergyStored(container) - this.getEnergyStored(container)) >= receive) {
            container.getTagCompound().setInteger("energy", this.getEnergyStored(container) + receive);
            return receive;
        } else return 0;
    }

    @Override
    public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
        int extract = 8096 * 4;
        if(simulate)
            return extract;
        if (maxExtract < extract)
            extract = maxExtract;
        if (!container.hasTagCompound())
            container.setTagCompound(new NBTTagCompound());
        if (this.getEnergyStored(container) >= extract) {
            container.getTagCompound().setInteger("energy", this.getEnergyStored(container) - extract);
            return extract;
        } else return 0;
    }

    @Override
    public int getEnergyStored(ItemStack container) {
        if (!container.hasTagCompound())
            container.setTagCompound(new NBTTagCompound());
        return container.getTagCompound().getInteger("energy");
    }

    @Override
    public int getMaxEnergyStored(ItemStack container) {
        return this.getHammerSettings().getDurability();
    }

    @Override
    public boolean giveDamage(ItemStack stack, EntityPlayer player) {
        if (stack.hasTagCompound() && getEnergyStored(stack) >= getCost(stack)) {
            stack.getTagCompound().setInteger("energy", getEnergyStored(stack) - getCost(stack));
            return true;
        }
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (stack.hasTagCompound())
            return 1 - (double) (getEnergyStored(stack) / getMaxEnergyStored(stack));
            //if (stack.hasTagCompound())
            //    return (double) ((IC2EnergyHammer) stack.getItem()).getMaxCharge(stack) / stack.getTagCompound().getInteger("charge");
        else return 0.0;
    }

    @Override
    public int getDamage(ItemStack stack) {
        return this.getMaxEnergyStored(stack) - this.getEnergyStored(stack);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                if (itemStack.getTagCompound().getBoolean("Invert"))
                    list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("trash.Inverted"));
                if (itemStack.hasTagCompound() && itemStack.getTagCompound().getBoolean("Trash")) {
                    list.add(StatCollector.translateToLocal("trash.IgnoreList"));
                    for (int i = 0; i < itemStack.getTagCompound().getTagList("Items", Constants.NBT.TAG_COMPOUND).tagCount(); ++i) {
                        NBTTagCompound item = /*(NBTTagCompound)*/ itemStack.getTagCompound().getTagList("Items", Constants.NBT.TAG_COMPOUND).getCompoundTagAt(i);
                        list.add(ItemStack.loadItemStackFromNBT(item).getDisplayName());
                    }
                }
            } else {
                list.add(StatCollector.translateToLocal("information.usesLeft") + " " + EnumChatFormatting.WHITE + this.getEnergyStored(itemStack) / getCost(itemStack) + EnumChatFormatting.GRAY + " " + StatCollector.translateToLocal("information.blocks") + " (" + getCost(itemStack) + " RF/Block)");
                list.add(StatCollector.translateToLocal("information.harvestLevel") + " " + hammerSettings.getHarvestLevel());
                if (hammerSettings.isRepair())
                    list.add(StatCollector.translateToLocal("information.repairMaterial") + " " + hammerSettings.getRepairMaterial());
                else if (hammerSettings.isInfinity())
                    list.add(StatCollector.translateToLocal("information.infinity"));
                else list.add(StatCollector.translateToLocal("information.noRepairable"));
                list.add(StatCollector.translateToLocal("information.efficiency") + " " + hammerSettings.getEffiency());
                if (itemStack.hasTagCompound()) {
                    if (itemStack.getTagCompound().getBoolean("Modif")) {
                        list.add("");
                        list.add(StatCollector.translateToLocal("information.modification"));
                        if (itemStack.getTagCompound().getBoolean("Torch"))
                            list.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("modification.Torch") + "(" +
                                    new ItemStack(Block.getBlockById(itemStack.getTagCompound().getInteger("TorchID"))).getDisplayName() + ")");
                        if (itemStack.getTagCompound().getBoolean("Diamond"))
                            list.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("modification.Diamond"));
                        if (itemStack.getTagCompound().getInteger("Axe") != 0)
                            list.add(EnumChatFormatting.WHITE + StatCollector.translateToLocal("modification.Axe") + " " + itemStack.getTagCompound().getInteger("Axe") + StatCollector.translateToLocal("modification.AxeSpeed") + " " + itemStack.getTagCompound().getDouble("AxeSpeed"));
                        if (itemStack.getTagCompound().getInteger("Shovel") != 0)
                            list.add(EnumChatFormatting.WHITE + StatCollector.translateToLocal("modification.Shovel") + " " + itemStack.getTagCompound().getInteger("Shovel") + StatCollector.translateToLocal("modification.ShovelSpeed") + " " + itemStack.getTagCompound().getDouble("ShovelSpeed"));
                        if (itemStack.getTagCompound().getBoolean("Trash"))
                            list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("modification.Trash"));
                        if (itemStack.getTagCompound().getBoolean("Vacuum"))
                            list.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("modification.Vacuum"));
                        if (itemStack.getTagCompound().getBoolean("Smelt"))
                            list.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("modification.Smelt"));

                    }
                } else {
                    list.add(EnumChatFormatting.RED + "PLACE ITEM IN CRAFT WINDOW!!!");
                }
            }
        } else {
            list.add(StatCollector.translateToLocal("information.ShiftDialog"));
            if (itemStack.hasTagCompound() && itemStack.getTagCompound().getBoolean("Trash"))
                list.add(StatCollector.translateToLocal("information.CtrlShiftDialog"));
        }

        list.add("Energy: " + EnumChatFormatting.WHITE + this.getEnergyStored(itemStack) + EnumChatFormatting.GRAY + " / " + this.getMaxEnergyStored(itemStack));

    }

    public String getItemStackDisplayName(ItemStack p_77653_1_) {
        return EnumChatFormatting.YELLOW + ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(p_77653_1_) + ".name")).trim();
    }


    public int getCost(ItemStack is) {

        return cost / (EnchantmentHelper.getEnchantmentLevel(34, is) + 1);
    }
}
