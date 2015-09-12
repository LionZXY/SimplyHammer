package ru.lionzxy.simlyhammer.hammers;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.ItemType;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import ru.lionzxy.simlyhammer.config.Config;
import ru.lionzxy.simlyhammer.interfaces.ITrash;
import ru.lionzxy.simlyhammer.interfaces.IVacuum;
import ru.lionzxy.simlyhammer.libs.HammerSettings;
import ru.lionzxy.simlyhammer.utils.AddHammers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikit on 06.09.2015.
 */
public class BoundHammer extends BasicHammer implements IBindable {
    @SideOnly(Side.CLIENT)
    private IIcon activeIcon;
    @SideOnly(Side.CLIENT)
    private IIcon passiveIcon;
    int energyUsed = 5;

    public BoundHammer(HammerSettings hammerSettings) {
        super(hammerSettings);
        this.hammerSettings = hammerSettings;
        this.hammerSettings.setInfinity();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("simplyhammer:bloodHammer");
        this.activeIcon = iconRegister.registerIcon("simplyhammer:bloodHammer");
        this.passiveIcon = iconRegister.registerIcon("AlchemicalWizardry:SheathedItem");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound tag = stack.getTagCompound();

        if (tag.getBoolean("isActive")) {
            return this.activeIcon;
        } else {
            return this.passiveIcon;
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
        if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            if (itemStack.hasTagCompound()) {
                if (!itemStack.getTagCompound().getString("ownerName").equals("") && itemStack.getTagCompound() != null)
                    list.add(StatCollector.translateToLocal("information.usesLeft") + " " + SoulNetworkHandler.getCurrentEssence(itemStack.getTagCompound().getString("ownerName")) + " " + StatCollector.translateToLocal("information.LP") + " " + StatCollector.translateToLocal("information.LPtoTick"));
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
                    if  (itemStack.getTagCompound().getBoolean("Trash"))
                        list.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("modification.Trash"));

                }
            } else list.add(StatCollector.translateToLocal("information.NotHaveTagCompound"));
        } else list.add(StatCollector.translateToLocal("information.ShiftDialog"));
        if (!(itemStack.getTagCompound() == null)) {
            if (itemStack.getTagCompound().getBoolean("isActive")) {
                list.add(StatCollector.translateToLocal("tooltip.sigil.state.activated"));
            } else {
                list.add(StatCollector.translateToLocal("tooltip.sigil.state.deactivated"));
            }

            if (!itemStack.getTagCompound().getString("ownerName").equals("")) {
                list.add(StatCollector.translateToLocal("tooltip.owner.currentowner") + " " + itemStack.getTagCompound().getString("ownerName"));
            }
        }
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase player) {
        if (player instanceof EntityPlayer) {
            SoulNetworkHandler.syphonAndDamageFromNetwork(itemStack, (EntityPlayer) player, getEnergyUsed());
        }
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
        if (!EnergyItems.checkAndSetItemOwner(par1ItemStack, par3EntityPlayer) || par3EntityPlayer.isSneaking()) {
            this.setActivated(par1ItemStack, !getActivated(par1ItemStack));
            par1ItemStack.getTagCompound().setInteger("worldTimeDelay", (int) (par2World.getWorldTime() - 1) % 200);
            return par1ItemStack;
        }

        if (par2World.isRemote) {
            return par1ItemStack;
        }

        if (!getActivated(par1ItemStack) || SpellHelper.isFakePlayer(par2World, par3EntityPlayer)) {
            return par1ItemStack;
        }


        if (par3EntityPlayer.isPotionActive(AlchemicalWizardry.customPotionInhibit)) {
            return par1ItemStack;
        }

        if (!SoulNetworkHandler.syphonAndDamageFromNetwork(par1ItemStack, par3EntityPlayer, 10000)) {
            return par1ItemStack;
        }

        Vec3 blockVec = SpellHelper.getEntityBlockVector(par3EntityPlayer);
        int posX = (int) (blockVec.xCoord);
        int posY = (int) (blockVec.yCoord);
        int posZ = (int) (blockVec.zCoord);
        boolean silkTouch = EnchantmentHelper.getSilkTouchModifier(par3EntityPlayer);
        int fortuneLvl = EnchantmentHelper.getFortuneModifier(par3EntityPlayer);

        HashMultiset<ItemType> dropMultiset = HashMultiset.create();

        for (int i = -5; i <= 5; i++) {
            for (int j = -5; j <= 5; j++) {
                for (int k = -5; k <= 5; k++) {
                    Block block = par2World.getBlock(posX + i, posY + j, posZ + k);
                    int meta = par2World.getBlockMetadata(posX + i, posY + j, posZ + k);

                    if (block != null && block.getBlockHardness(par2World, posX + i, posY + j, posZ + k) != -1) {
                        float str = func_150893_a(par1ItemStack, block);

                        if (str > 1.1f && par2World.canMineBlock(par3EntityPlayer, posX + i, posY + j, posZ + k)) {
                            if (silkTouch && block.canSilkHarvest(par2World, par3EntityPlayer, posX + i, posY + j, posZ + k, meta)) {
                                dropMultiset.add(new ItemType(block, meta));
                            } else {
                                ArrayList<ItemStack> itemDropList = block.getDrops(par2World, posX + i, posY + j, posZ + k, meta, fortuneLvl);

                                if (itemDropList != null) {
                                    for (ItemStack stack : itemDropList)
                                        dropMultiset.add(ItemType.fromStack(stack), stack.stackSize);
                                }
                            }

                            par2World.setBlockToAir(posX + i, posY + j, posZ + k);
                        }
                    }
                }
            }
        }

        dropMultisetStacks(dropMultiset, par2World, posX, posY + par3EntityPlayer.getEyeHeight(), posZ);

        return par1ItemStack;
    }

    public void setActivated(ItemStack par1ItemStack, boolean newActivated) {
        NBTTagCompound itemTag = par1ItemStack.getTagCompound();

        if (itemTag == null) {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }

        itemTag.setBoolean("isActive", newActivated);
    }

    public boolean getActivated(ItemStack par1ItemStack) {
        if (!par1ItemStack.hasTagCompound()) {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound itemTag = par1ItemStack.getTagCompound();

        return itemTag.getBoolean("isActive");
    }

    public static void dropMultisetStacks(Multiset<ItemType> dropMultiset, World world, double x, double y, double z) {
        for (Multiset.Entry<ItemType> entry : dropMultiset.entrySet()) {
            int count = entry.getCount();
            ItemType type = entry.getElement();
            int maxStackSize = type.item.getItemStackLimit(type.createStack(1));

            //Drop in groups of maximum size
            while (count >= maxStackSize) {
                world.spawnEntityInWorld(new EntityItem(world, x, y, z, type.createStack(maxStackSize)));
                count -= maxStackSize;
            }
            //Drop remainder
            if (count > 0)
                world.spawnEntityInWorld(new EntityItem(world, x, y, z, type.createStack(count)));
        }
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
        if (!(par3Entity instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer par3EntityPlayer = (EntityPlayer) par3Entity;

        if (par1ItemStack.getTagCompound() == null) {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }

        if (par2World.getWorldTime() % 200 == par1ItemStack.getTagCompound().getInteger("worldTimeDelay") && par1ItemStack.getTagCompound().getBoolean("isActive")) {
            if (!par3EntityPlayer.capabilities.isCreativeMode) {
                if (!EnergyItems.syphonBatteries(par1ItemStack, par3EntityPlayer, 20)) {
                    this.setActivated(par1ItemStack, false);
                }
            }
        }

        par1ItemStack.setItemDamage(0);
    }

    @Override
    boolean giveDamage(ItemStack stack, EntityPlayer player) {

        SoulNetworkHandler.syphonAndDamageFromNetwork(stack, (EntityPlayer) player, getEnergyUsed());
        return true;
    }

    public int getEnergyUsed() {
        return this.energyUsed;
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

    public static void addBMHammer(String name, int breakRadius, int harvestLevel, float speed, int damage) {
        if (Config.config.get("general", name, true).getBoolean()) {
            AddHammers.BMHammer = new BoundHammer(new HammerSettings(name, breakRadius, harvestLevel, speed, damage, null, true));
            GameRegistry.registerItem(AddHammers.BMHammer, name);
        }
    }
}
