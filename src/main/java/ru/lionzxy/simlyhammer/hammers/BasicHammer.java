package ru.lionzxy.simlyhammer.hammers;

import com.google.common.collect.Multimap;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.input.Keyboard;
import ru.lionzxy.simlyhammer.utils.AchievementSH;
import ru.lionzxy.simlyhammer.SimplyHammer;

import java.util.List;

/**
 * Created by nikit on 30.08.2015.
 */
public class BasicHammer extends ItemTool {
    int breakRadius = 1, breakDepth = 0, oreDictId = 0;
    private Item repairMaterial;
    ToolMaterial toolMaterial;
    public boolean isRepair, isAchiv, MDiamond, MAxe, MShovel, MTorch;
    String localizeName;


    public BasicHammer(String name,String localizeName, String texturename, int breakRadius, int harvestLevel, float speed, int damage, int Enchant,
                       String repairMaterial1, boolean isRepair, boolean isAchiv, boolean MDiamond, boolean MAxe, boolean MShovel, boolean MTorch) {
        super(1F, EnumHelper.addToolMaterial(name, harvestLevel, damage, speed, speed * harvestLevel, Enchant), null);
        toolMaterial = this.func_150913_i();
        this.setTextureName(texturename);
        this.setUnlocalizedName(name);
        this.breakRadius = breakRadius;
        this.setCreativeTab(SimplyHammer.tabGeneral);
        this.setMaxDamage(toolMaterial.getMaxUses());
        if (repairMaterial1.indexOf(':') != -1)
            repairMaterial = GameRegistry.findItem(repairMaterial1.substring(0, repairMaterial1.indexOf(':')), repairMaterial1.substring(repairMaterial1.indexOf(':')));
        else
            oreDictId = OreDictionary.getOreID(repairMaterial1);
        this.setMaxStackSize(1);
        this.isRepair = isRepair;
        this.isAchiv = isAchiv;
        this.MDiamond = MDiamond;
        this.MAxe = MAxe;
        this.MShovel = MShovel;
        this.MTorch = MTorch;
        this.localizeName = localizeName;
    }

    public BasicHammer(String name, int breakRadius, int harvestLevel, float speed, int damage, int Enchant,
                       String repairMaterial1, boolean isRepair, boolean isAchiv, boolean MDiamond, boolean MAxe, boolean MShovel, boolean MTorch) {
        this(name,null, "simplyhammer:" + name, breakRadius, harvestLevel, speed, damage, Enchant, repairMaterial1, isRepair, isAchiv, MDiamond, MAxe, MShovel, MTorch);

    }

    public String getItemStackDisplayName(ItemStack p_77653_1_) {
        if (localizeName == null)
            return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(p_77653_1_) + ".name")).trim();
        else
            return localizeName;
    }

    public BasicHammer(String name, int breakRadius, int harvestLevel, float speed, int damage, int Enchant) {
        super(1F, EnumHelper.addToolMaterial(name, harvestLevel, damage, speed, speed * harvestLevel, Enchant), null);
        this.toolMaterial = this.func_150913_i();
        this.setTextureName("simplyhammer:" + name);
        this.setUnlocalizedName(name);
        this.breakRadius = breakRadius;
        this.setCreativeTab(SimplyHammer.tabGeneral);
        this.setMaxDamage(toolMaterial.getMaxUses());
        this.setMaxStackSize(1);
    }

    public boolean checkMaterial(ItemStack itemStack) {
        if (!isRepair)
            return false;
        if (repairMaterial != null)
            return itemStack.getItem() == repairMaterial;
        if (oreDictId != 0) {
            int[] oreIds = OreDictionary.getOreIDs(itemStack);

            for (int i = 0; i < oreIds.length; i++)
                if (oreDictId == oreIds[i])
                    return true;
        }
        return false;
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (block.getHarvestTool(meta) == null)
            return 0.5F;
        if (stack.getItemDamage() >= stack.getMaxDamage() - 1)
            return -1F;
        if (block.getHarvestTool(meta).equals("pickaxe"))
            return toolMaterial.getEfficiencyOnProperMaterial();
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
        if (block.getHarvestTool(meta) != null && block.getHarvestTool(meta).equals("pickaxe") ||
                (stack.hasTagCompound() && block.getHarvestTool(meta) != null && block.getHarvestTool(meta).equals("axe") && stack.getTagCompound().getInteger("Axe") != 0) ||
                (stack.hasTagCompound() && block.getHarvestTool(meta) != null && block.getHarvestTool(meta).equals("shovel") && stack.getTagCompound().getInteger("Shovel") != 0))
            return true;

        else return isEffective(block.getMaterial());
    }

    public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
        if (isAchiv)
            player.addStat(AchievementSH.firstDig, 1);
        MovingObjectPosition mop = raytraceFromEntity(player.worldObj, player, false, 4.5d);
        if (mop == null)
            return false;
        int sideHit = mop.sideHit;
        World world = player.worldObj;
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
        return super.onBlockStartBreak(itemstack, X, Y, Z, player);
    }

    //Right-click
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ) {
        if (world.isRemote)
            return true;
        if (isAchiv)
            player.addStat(AchievementSH.placeBlock, 1);
        boolean used = false;
        int hotbarSlot = player.inventory.currentItem;
        int itemSlot = hotbarSlot == 0 ? 8 : hotbarSlot + 1;
        if (stack.hasTagCompound() && stack.getTagCompound().getBoolean("Torch"))
            for (int i = 0; i < player.inventory.getSizeInventory(); i++)
                if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() == Item.getItemFromBlock(Blocks.torch)) {
                    itemSlot = i;
                    break;
                }

        ItemStack nearbyStack = null;

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
                    /*if (item == TinkerTools.openBlocksDevNull)
                    {
                        //Openblocks uses current inventory slot, so we have to do this...
                        player.inventory.currentItem=itemSlot;
                        item.onItemUse(nearbyStack, player, world, x, y, z, side, clickX, clickY, clickZ);
                        player.inventory.currentItem=hotbarSlot;
                        player.swingItem();
                    }
                    else*/
                    used = item.onItemUse(nearbyStack, player, world, x, y, z, side, clickX, clickY, clickZ);

                    // handle creative mode
                    if (player.capabilities.isCreativeMode) {
                        // fun fact: vanilla minecraft does it exactly the same way
                        nearbyStack.setItemDamage(dmg);
                        nearbyStack.stackSize = count;
                    }
                    if (nearbyStack.stackSize < 1) {
                        nearbyStack = null;
                        player.inventory.setInventorySlotContents(itemSlot, null);
                    }
                }
            }
        }
        return used;
    }

    //Нагло слямзил у Tinkers Construct
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

        // only effective materials
        if (!isEffective(player.getCurrentEquippedItem(), block, meta))
            return;

        if (!this.giveDamage(player.getCurrentEquippedItem()))
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
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double) toolMaterial.getDamageVsEntity(), 0));
        return multimap;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            list.add(StatCollector.translateToLocal("information.placeBlock"));
            list.add(StatCollector.translateToLocal("information.line"));
            list.add(StatCollector.translateToLocal("information.usesLeft") + " " + (itemStack.getMaxDamage() - itemStack.getItemDamage()) + StatCollector.translateToLocal("information.blocks"));
            list.add(StatCollector.translateToLocal("information.harvestLevel") + " " + toolMaterial.getHarvestLevel());
            if (isRepair)
                list.add(StatCollector.translateToLocal("information.repairMaterial") + " " + getRepairMaterial().getDisplayName());
            else list.add(StatCollector.translateToLocal("information.noRepairable"));
            list.add(StatCollector.translateToLocal("information.efficiency") + " " + toolMaterial.getEfficiencyOnProperMaterial());
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
            }
        } else list.add(StatCollector.translateToLocal("information.ShiftDialog"));
    }


    public int getItemEnchantability() {
        return toolMaterial.getEnchantability();
    }

    public ItemStack getRepairMaterial() {
        if (repairMaterial != null)
            return new ItemStack(repairMaterial);
        if (oreDictId != 0)
            return OreDictionary.getOres(OreDictionary.getOreName(oreDictId)).get(0);
        return new ItemStack(Items.stick);
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
        return toolMaterial.getHarvestLevel();
    }


    public boolean isEffective(Material material) {
        for (Material m : getEffectiveMaterials())
            if (m == material)
                return true;

        return false;
    }

    protected Material[] getEffectiveMaterials() {
        return materials;
    }

    static Material[] materials = new Material[]{Material.rock, Material.iron, Material.ice, Material.glass, Material.piston, Material.anvil};

    public int getMaxDamage(ItemStack stack) {
        /**
         * Returns the maximum damage an item can take.
         */
        if (stack.hasTagCompound() && stack.getTagCompound().getBoolean("Diamond"))
            return getMaxDamage() + 500;
        return getMaxDamage();
    }

    boolean giveDamage(ItemStack stack) {
        if (stack.getItemDamage() >= stack.getMaxDamage() - 1)
            return false;
        stack.setItemDamage(stack.getItemDamage() + 1);
        return true;
    }

}
