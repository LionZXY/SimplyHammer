package ru.lionzxy.simlyhammer.hammers;

import com.google.common.collect.Multimap;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.world.BlockEvent;
import org.lwjgl.input.Keyboard;
import ru.lionzxy.simlyhammer.interfaces.IModifiHammer;
import ru.lionzxy.simlyhammer.interfaces.ISmelt;
import ru.lionzxy.simlyhammer.interfaces.ITrash;
import ru.lionzxy.simlyhammer.interfaces.IVacuum;
import ru.lionzxy.simlyhammer.libs.HammerSettings;
import ru.lionzxy.simlyhammer.utils.HammerUtils;
import ru.lionzxy.simlyhammer.handlers.AchievementSH;
import ru.lionzxy.simlyhammer.SimplyHammer;

import java.util.List;

/**
 * Created by nikit on 30.08.2015.
 */
public class BasicHammer extends ItemTool implements IModifiHammer, ITrash, IVacuum, ISmelt {
    public HammerSettings hammerSettings;

    public BasicHammer(HammerSettings hammerSettings) {
        this(hammerSettings, "simplyhammer:" + hammerSettings.getUnlocalizeName());
    }

    public BasicHammer(HammerSettings hammerSettings, String texturename) {
        super(1F, hammerSettings.getMaterial(), null);
        this.hammerSettings = hammerSettings;
        this.setTextureName(texturename);
        this.setCreativeTab(SimplyHammer.tabGeneral);
        this.setMaxStackSize(1);
        this.setUnlocalizedName(hammerSettings.getUnlocalizeName());
    }


    public String getItemStackDisplayName(ItemStack p_77653_1_) {
        return hammerSettings.getLocalizeName();
    }

    public boolean checkMaterial(ItemStack itemStack) {
        return hammerSettings.checkMaterial(itemStack);
    }

    @Override
    public HammerSettings getHammerSettings() {
        return hammerSettings;
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (block.getHarvestTool(meta) == null)
            return hammerSettings.getEffiency();
        if (stack.getItemDamage() >= stack.getMaxDamage() - 1)
            return -1F;
        if (block.getHarvestTool(meta).equals("pickaxe"))
            return hammerSettings.getEffiency();
        if (stack.hasTagCompound() && block.getHarvestTool(meta).equals("axe") && stack.getTagCompound().getInteger("Axe") != 0)
            return (float) stack.getTagCompound().getDouble("AxeSpeed");
        if (stack.hasTagCompound() && block.getHarvestTool(meta).equals("shovel") && stack.getTagCompound().getInteger("Shovel") != 0)
            return (float) stack.getTagCompound().getDouble("ShovelSpeed");

        return 0.5F;
    }

    @Override
    public boolean func_150897_b(Block block) {
        return true;
        //return isEffective(block.getMaterial());
    }

    public boolean isEffective(ItemStack stack, Block block, int meta) {
        return block.getHarvestTool(meta) == null || (block.getHarvestTool(meta).equals("pickaxe") || (stack.hasTagCompound() && block.getHarvestTool(meta) != null && block.getHarvestTool(meta).equals("axe") && stack.getTagCompound().getInteger("Axe") != 0) || (stack.hasTagCompound() && block.getHarvestTool(meta) != null && block.getHarvestTool(meta).equals("shovel") && stack.getTagCompound().getInteger("Shovel") != 0) || isEffective(block.getMaterial()));
    }

    public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
        if (hammerSettings.isAchive())
            player.addStat(AchievementSH.firstDig, 1);
        if (!player.isSneaking()) {
            MovingObjectPosition mop = raytraceFromEntity(player.worldObj, player, false, 4.5d);
            if (mop == null)
                return false;
            int sideHit = mop.sideHit;
            int xRange = breakRadius;
            int yRange = breakRadius;
            int zRange = breakDepth;
            switch (sideHit) {
                case 0:
                case 1:
                    yRange = breakDepth;
                    zRange = breakRadius;
                    break;
                case 2:
                case 3:
                    xRange = breakRadius;
                    zRange = breakDepth;
                    break;
                case 4:
                case 5:
                    xRange = breakDepth;
                    zRange = breakRadius;
                    break;
            }

            for (int xPos = X - xRange; xPos <= X + xRange; xPos++)
                for (int yPos = Y - yRange; yPos <= Y + yRange; yPos++)
                    for (int zPos = Z - zRange; zPos <= Z + zRange; zPos++) {
                        // don't break the originally already broken block, duh
                        if (xPos == X && yPos == Y && zPos == Z)
                            continue;

                        if (!super.onBlockStartBreak(itemstack, xPos, yPos, zPos, player))
                            breakExtraBlock(player.worldObj, xPos, yPos, zPos, sideHit, player, X, Y, Z);
                    }
        }
        return super.onBlockStartBreak(itemstack, X, Y, Z, player);
    }

    //Right-click
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ) {
        if (hammerSettings.isAchive())
            player.addStat(AchievementSH.placeBlock, 1);
        boolean used = false;
        int hotbarSlot = player.inventory.currentItem;
        int itemSlot = hotbarSlot == 0 ? 8 : hotbarSlot + 1;
        if (stack.hasTagCompound() && stack.getTagCompound().getBoolean("Torch") && stack.getTagCompound().getInteger("TorchID") > 0)
            for (int i = 0; i < player.inventory.getSizeInventory(); i++)
                if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock &&
                        Block.getIdFromBlock(((ItemBlock) player.inventory.getStackInSlot(i).getItem()).field_150939_a) == stack.getTagCompound().getInteger("TorchID")) {
                    itemSlot = i;
                    break;
                }

        ItemStack nearbyStack;

        if (hotbarSlot < 8) {
            nearbyStack = player.inventory.getStackInSlot(itemSlot);
            if (nearbyStack != null) {
                Item item = nearbyStack.getItem();

                if (item instanceof ItemBlock) {
                    int posX = x;
                    int posY = y;
                    int posZ = z;

                    switch (side) {
                        case 0:
                            --posY;
                            break;
                        case 1:
                            ++posY;
                            break;
                        case 2:
                            --posZ;
                            break;
                        case 3:
                            ++posZ;
                            break;
                        case 4:
                            --posX;
                            break;
                        case 5:
                            ++posX;
                            break;
                    }

                    AxisAlignedBB blockBounds = AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + 1, posY + 1, posZ + 1);
                    AxisAlignedBB playerBounds = player.boundingBox;

                    if (item instanceof ItemBlock) {
                        Block blockToPlace = ((ItemBlock) item).field_150939_a;
                        if (blockToPlace.getMaterial().blocksMovement()) {
                            if (playerBounds.intersectsWith(blockBounds))
                                return false;
                        }
                    }

                    int dmg = nearbyStack.getItemDamage();
                    int count = nearbyStack.stackSize;
                    if (item == HammerUtils.openBlocksDevNull) {
                        //Openblocks uses current inventory slot, so we have to do this...
                        player.inventory.currentItem = itemSlot;
                        item.onItemUse(nearbyStack, player, world, x, y, z, side, clickX, clickY, clickZ);
                        player.inventory.currentItem = hotbarSlot;
                        player.swingItem();
                    } else
                        used = item.onItemUse(nearbyStack, player, world, x, y, z, side, clickX, clickY, clickZ);

                    // handle creative mode
                    if (player.capabilities.isCreativeMode) {
                        // fun fact: vanilla minecraft does it exactly the same way
                        nearbyStack.setItemDamage(dmg);
                        player.inventoryContainer.getSlot(itemSlot).onSlotChanged();
                        nearbyStack.stackSize = count;
                        player.inventory.inventoryChanged = true;
                    }
                    if (nearbyStack.stackSize < 1) {
                        nearbyStack = null;
                        player.inventory.setInventorySlotContents(itemSlot, null);
                        //player.inventory.inventoryChanged = true;
                    }
                }
            }
        }

        return used;
    }

    protected void breakExtraBlock(World world, int x, int y, int z, int sidehit, EntityPlayer playerEntity, int refX, int refY, int refZ) {
        // prevent calling that stuff for air blocks, could lead to unexpected behaviour since it fires events
        if (world.isAirBlock(x, y, z))
            return;

        // what?
        if (!(playerEntity instanceof EntityPlayerMP))
            return;
        EntityPlayerMP player = (EntityPlayerMP) playerEntity;

        // check if the block can be broken, since extra block breaks shouldn't instantly break stuff like obsidian
        // or precious ores you can't harvest while mining stone
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        //Fix bug with break bedrock
        if (block.getBlockHardness(world, x, y, z) <= 0)
            return;
        // only effective materials
        if (!isEffective(player.getCurrentEquippedItem(), block, meta))
            return;

        if (!this.giveDamage(player.getCurrentEquippedItem(), playerEntity))
            return;
        Block refBlock = world.getBlock(refX, refY, refZ);
        float refStrength = ForgeHooks.blockStrength(refBlock, player, world, refX, refY, refZ);
        float strength = ForgeHooks.blockStrength(block, player, world, x, y, z);

        // only harvestable blocks that aren't impossibly slow to harvest
        if (!ForgeHooks.canHarvestBlock(block, player, meta) || refStrength / strength > 10f)
            return;

        // send the blockbreak event
        BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, player.theItemInWorldManager.getGameType(), player, x, y, z);
        if (event.isCanceled())
            return;
        if (player.capabilities.isCreativeMode) {
            block.onBlockHarvested(world, x, y, z, meta, player);
            if (block.removedByPlayer(world, player, x, y, z, false))
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);

            // send update to client
            if (!world.isRemote) {
                player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
            }
            return;
        }

        // callback to the tool the player uses. Called on both sides. This damages the tool n stuff.

        // server sided handling
        if (!world.isRemote) {
            // serverside we reproduce ItemInWorldManager.tryHarvestBlock

            // ItemInWorldManager.removeBlock
            block.onBlockHarvested(world, x, y, z, meta, player);

            if (block.removedByPlayer(world, player, x, y, z, true)) // boolean is if block can be harvested, checked above
            {
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);
                block.harvestBlock(world, player, x, y, z, meta);
                block.dropXpOnBlockBreak(world, x, y, z, event.getExpToDrop());
            }

            // always send block update to client
            player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
        }
        // client sided handling
        else {
            //PlayerControllerMP pcmp = Minecraft.getMinecraft().playerController;
            // clientside we do a "this clock has been clicked on long enough to be broken" call. This should not send any new packets
            // the code above, executed on the server, sends a block-updates that give us the correct state of the block we destroy.

            // following code can be found in PlayerControllerMP.onPlayerDestroyBlock
            world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
            if (block.removedByPlayer(world, player, x, y, z, true)) {
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);
            }
            // callback to the tool
            ItemStack itemstack = player.getCurrentEquippedItem();
            if (itemstack != null) {
                itemstack.func_150999_a(world, block, x, y, z, player);

                if (itemstack.stackSize == 0) {
                    player.destroyCurrentEquippedItem();
                }
            }

            // send an update to the server, so we get an update back
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(2, x, y, z, Minecraft.getMinecraft().objectMouseOver.sideHit));
        }
    }


    public static MovingObjectPosition raytraceFromEntity(World world, Entity player, boolean par3, double range) {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f;
        if (!world.isRemote && player instanceof EntityPlayer)
            d1 += 1.62D;
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = range;
        if (player instanceof EntityPlayerMP) {
            d3 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
        return world.func_147447_a(vec3, vec31, par3, !par3, par3);
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    public Multimap getAttributeModifiers(ItemStack stack) {
        Multimap multimap = super.getAttributeModifiers(stack);

        //if (stack.getItemDamage() >= stack.getMaxDamage() - 1)
        //    multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double) 0, 0));
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double) 0, 0));
        return multimap;
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
                list.add(StatCollector.translateToLocal("information.usesLeft") + " " + EnumChatFormatting.WHITE + (itemStack.getMaxDamage() - itemStack.getItemDamage()) + EnumChatFormatting.GRAY + StatCollector.translateToLocal("information.blocks"));
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

    }


    public int getItemEnchantability() {
        return hammerSettings.getEnchant();
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
        return hammerSettings.getHarvestLevel();
    }


    public boolean isEffective(Material material) {
        //???
        if (material == null)
            return true;
        for (Material m : getEffectiveMaterials())
            if (m == material)
                return true;

        return false;
    }

    protected Material[] getEffectiveMaterials() {
        return materials;
    }

    public static Material[] materials = new Material[]{Material.rock, Material.iron, Material.ice, Material.glass, Material.piston, Material.anvil};

    public int getMaxDamage(ItemStack stack) {
        /**
         * Returns the maximum damage an item can take.
         */
        if (stack.hasTagCompound() && stack.getTagCompound().getBoolean("Diamond"))
            return getMaxDamage() + 500;
        return getMaxDamage();
    }

    public boolean giveDamage(ItemStack stack, EntityPlayer player) {
        if (stack.getItemDamage() >= stack.getMaxDamage() - 1)
            return false;
        if (!hammerSettings.isInfinity())
            stack.setItemDamage(stack.getItemDamage() + 1);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_) {
        if ((double) p_150894_3_.getBlockHardness(p_150894_2_, p_150894_4_, p_150894_5_, p_150894_6_) != 0.0D && hammerSettings.getDurability() != -1 && !hammerSettings.isInfinity()) {
            p_150894_1_.damageItem(1, p_150894_7_);
        }
        return true;
    }

    public boolean hitEntity(ItemStack is, EntityLivingBase entity, EntityLivingBase player) {
        if (player instanceof EntityPlayer)
            return this.giveDamage(is, (EntityPlayer) player);
        else return false;
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack is, int p_82790_2_) {
        if (!is.hasTagCompound())
            is.setTagCompound(new NBTTagCompound());

        if (is.getTagCompound().getInteger("Color") == 0)
            return 16777215;
        else return is.getTagCompound().getInteger("Color");
    }

}
