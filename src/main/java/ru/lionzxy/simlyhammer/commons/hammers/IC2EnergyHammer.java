package ru.lionzxy.simlyhammer.commons.hammers;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IBoxable;
import ic2.api.item.IC2Items;
import ic2.api.item.IElectricItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.commons.config.JsonConfig;
import ru.lionzxy.simlyhammer.commons.hammers.core.NBTHammer;
import ru.lionzxy.simlyhammer.commons.items.AddItems;
import ru.lionzxy.simlyhammer.commons.recipe.IC2DrillCrafting;
import ru.lionzxy.simlyhammer.libs.HammerSettings;
import ru.lionzxy.simlyhammer.utils.AddHammers;

import java.util.List;

/**
 * Created by nikit_000 on 16.10.2015.
 */
public class IC2EnergyHammer extends NBTHammer implements IElectricItem, IBoxable {
    public static int cost = 50;

    public IC2EnergyHammer() {
        super(new HammerSettings("ic2hammer", 1, 2, 4F, 2024000, null, false).setRepair(false));
        //String name, int breakRadius, int harvestLevel, float speed, int damage, String rm, boolean infinity
        cost = JsonConfig.get("ic2hammer", "cost", 50).getInt();
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
        return this.getHammerSettings().getDurability();
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
            stack.getTagCompound().setInteger("charge", stack.getTagCompound().getInteger("charge") - getCost(stack));
            if (stack.getTagCompound().getInteger("charge") >= getCost(stack))
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
        return is.getTagCompound().getInteger("charge") / getCost(is);
    }

    @Override
    protected String usesLeft(ItemStack is) {
        if (!is.hasTagCompound())
            is.setTagCompound(new NBTTagCompound());
        return EnumChatFormatting.WHITE.toString() + (int) (is.getTagCompound().getInteger("charge") / getCost(is)) + EnumChatFormatting.GRAY + StatCollector.translateToLocal("information.blocks") + " (" + getCost(is) + " EU/Block)";
    }

    @Override
    protected void addIInfo(ItemStack is, List list) {
        if (!is.hasTagCompound())
            is.setTagCompound(new NBTTagCompound());
        list.add("Energy: " + is.getTagCompound().getInteger("charge") + " / " + (int) this.getMaxCharge(is));
    }

    @Override
    protected boolean damageItem(ItemStack is, EntityLivingBase player) {
        if (player instanceof EntityPlayer)
            return giveDamage(is, (EntityPlayer) player);
        else return false;
    }

    public int getCost(ItemStack is) {
        if (is.hasTagCompound() && is.getTagCompound().getDouble("HammerSpeed") != 0)
            return (int) (cost * (is.getTagCompound().getDouble("HammerSpeed") / 8) / (EnchantmentHelper.getEnchantmentLevel(34, is) + 1));
        return cost / (EnchantmentHelper.getEnchantmentLevel(34, is) + 1);
    }

    public static void init(){
        FMLLog.info("Register oredict drill");
        try {
            OreDictionary.registerOre("drillEu", IC2Items.getItem("miningDrill").getItem());
            OreDictionary.registerOre("drillEu", IC2Items.getItem("diamondDrill").getItem());
            OreDictionary.registerOre("drillEu", IC2Items.getItem("iridiumDrill").getItem());
            //OreDictionary.registerOre("ingotSteel", IC2Items.getItem("advIronIngot").getItem());
            OreDictionary.registerOre("blockSteel", IC2Items.getItem("advironblock").getItem());
        } catch (NullPointerException e) {
            FMLLog.bigWarning("[SimplyHammers] NOT FOUND SOME IC2 ITEMS. EU Hammer not crafting!!!");
            e.printStackTrace();
        }
        AddHammers.IC2Hammer = new IC2EnergyHammer();
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AddHammers.IC2Hammer),
                "xyx", "zpz", " k ",
                'x', "blockSteel",
                'y', "drillEu",
                'z', "ingotSteel",
                'p', IC2Items.getItem("mfeUnit"),
                'k', new ItemStack(AddItems.stick, 1, 0)
        ));
        GameRegistry.addRecipe(new IC2DrillCrafting());
    }


}
