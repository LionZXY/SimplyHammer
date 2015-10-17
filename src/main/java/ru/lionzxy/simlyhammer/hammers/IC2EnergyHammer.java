package ru.lionzxy.simlyhammer.hammers;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IBoxable;
import ic2.api.item.IElectricItem;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.hammers.core.NBTHammer;
import ru.lionzxy.simlyhammer.libs.HammerSettings;

import java.util.List;

/**
 * Created by nikit_000 on 16.10.2015.
 */
public class IC2EnergyHammer extends NBTHammer implements IElectricItem, IBoxable {

    public IC2EnergyHammer() {
        super(new HammerSettings("ic2hammer", 1, 2, 4F, 10, null, false));
        //String name, int breakRadius, int harvestLevel, float speed, int damage, String rm, boolean infinity
        this.setUnlocalizedName("ic2hammer");
        this.setCreativeTab(SimplyHammer.tabGeneral);
        GameRegistry.registerItem(this, "ic2hammer");
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack itemstack) {
        return true;
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
    }

    @Override
    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return 2024000;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 2;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return 8096;
    }

    @Override
    public boolean giveDamage(ItemStack stack, EntityPlayer player) {
        if (stack.hasTagCompound()) {
            stack.getTagCompound().setInteger("charge", stack.getTagCompound().getInteger("charge") - 50);
            if (stack.getTagCompound().getInteger("charge") >= 50)
                return true;
        }
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        //if (stack.hasTagCompound())System.out.println((double) stack.getTagCompound().getInteger("charge") / ((IC2EnergyHammer) stack.getItem()).getMaxCharge(stack));
        if (stack.hasTagCompound())
            return 1 - (double) stack.getTagCompound().getInteger("charge") / ((IC2EnergyHammer) stack.getItem()).getMaxCharge(stack);
            //if (stack.hasTagCompound())
            //    return (double) ((IC2EnergyHammer) stack.getItem()).getMaxCharge(stack) / stack.getTagCompound().getInteger("charge");
        else return 0.0;
    }

    @Override
    protected int usesLeftBlock(ItemStack is) {
        if (!is.hasTagCompound())
            is.setTagCompound(new NBTTagCompound());
        return is.getTagCompound().getInteger("charge") / 50;
    }

    @Override
    protected String usesLeft(ItemStack is) {
        if (!is.hasTagCompound())
            is.setTagCompound(new NBTTagCompound());
        return EnumChatFormatting.WHITE.toString() + (int) (is.getTagCompound().getInteger("charge") / 50) + EnumChatFormatting.GRAY + StatCollector.translateToLocal("information.blocks");
    }

    @Override
    protected void addIInfo(ItemStack is, List list) {
        if (!is.hasTagCompound())
            is.setTagCompound(new NBTTagCompound());
        list.add("Energy: " + is.getTagCompound().getInteger("charge") + " / " + (int) this.getMaxCharge(is));
    }

    @Override
    protected boolean damageItem(ItemStack is, EntityLivingBase player) {
        return giveDamage(is,(EntityPlayer) player);
    }


}
