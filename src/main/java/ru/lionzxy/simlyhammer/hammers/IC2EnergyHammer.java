package ru.lionzxy.simlyhammer.hammers;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IBoxable;
import ic2.api.item.IElectricItem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.libs.HammerSettings;

/**
 * Created by nikit_000 on 16.10.2015.
 */
public class IC2EnergyHammer extends BasicHammer implements IElectricItem, IBoxable {

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
        return true;
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
    boolean giveDamage(ItemStack stack, EntityPlayer player) {
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
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (block.getHarvestTool(meta) == null)
            return hammerSettings.getEffiency();
        if (stack.getTagCompound().getInteger("charge") < 50)
            return -1F;
        if (block.getHarvestTool(meta).equals("pickaxe"))
            return hammerSettings.getEffiency();
        if (stack.hasTagCompound() && block.getHarvestTool(meta).equals("axe") && stack.getTagCompound().getInteger("Axe") != 0)
            return (float) stack.getTagCompound().getDouble("AxeSpeed");
        if (stack.hasTagCompound() && block.getHarvestTool(meta).equals("shovel") && stack.getTagCompound().getInteger("Shovel") != 0)
            return (float) stack.getTagCompound().getDouble("ShovelSpeed");

        return 0.5F;
    }
}
