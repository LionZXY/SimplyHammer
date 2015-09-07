package ru.lionzxy.simlyhammer.hammers;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase.init.ModItems;
import lumaceon.mods.clockworkphase.item.construct.clockwork.tool.ItemClockworkPickaxe;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.oredict.OreDictionary;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.interfaces.IModifiHammer;
import ru.lionzxy.simlyhammer.utils.AchievementSH;
import ru.lionzxy.simlyhammer.utils.AddHammers;

/**
 * Created by nikit on 07.09.2015.
 */
public class ClockWorkPhaseHammer extends ItemClockworkPickaxe implements IModifiHammer{

    int breakRadius = 1, breakDepth = 0;
    public ToolMaterial toolMaterial;
    public boolean isRepair, isAchiv, MDiamond, MAxe, MShovel, MTorch;


    public ClockWorkPhaseHammer (String name, int breakRadius, int harvestLevel, float speed, int damage, int Enchant,int Attack,
                                 String repairMaterial1, boolean isRepair, boolean isAchiv, boolean MDiamond, boolean MAxe, boolean MShovel, boolean MTorch) {
        super(EnumHelper.addToolMaterial(name, harvestLevel, damage, speed, Attack, Enchant));
        toolMaterial = this.func_150913_i();
        this.setTextureName("simplyhammer:clockworkHammer");
        this.setUnlocalizedName(name);
        this.breakRadius = breakRadius;
        this.setCreativeTab(SimplyHammer.tabGeneral);
        this.setMaxStackSize(1);
        this.isRepair = isRepair;
        this.isAchiv = isAchiv;
        this.MDiamond = MDiamond;
        this.MAxe = MAxe;
        this.MShovel = MShovel;
        this.MTorch = MTorch;
    }


    public String getUnlocalizedName()
    {
        return "item." + "clockworkHammer";
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return "item." + "clockworkHammer";
    }
    @Override
    public Item getItemChangeTo()
    {
        return AddHammers.CWPTemporalHammer;
    }

    public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
        super.onBlockStartBreak(itemstack,X,Y,Z,player);
        if (isAchiv)
            player.addStat(AchievementSH.firstDig, 1);
        MovingObjectPosition mop = BasicHammer.raytraceFromEntity(player.worldObj, player, false, 4.5d);
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
        return super.onBlockStartBreak(itemstack, X, Y, Z, player);
    }
    //Right-click
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ) {
        super.onItemUse(stack, player, world, x, y, z, side, clickX, clickY, clickZ);
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
                super.onBlockDestroyed(player.getCurrentEquippedItem(),world,block,x,y,z,playerEntity);
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
    boolean giveDamage(ItemStack stack, EntityPlayer player) {
        if (stack.getItemDamage() >= stack.getMaxDamage() - 1)
            return false;
            stack.setItemDamage(stack.getItemDamage() + 1);
        return true;
    }
    public boolean isEffective(ItemStack stack, Block block, int meta) {
        System.out.println(stack.getTagCompound().getInteger("Shovel"));
        return block.getHarvestTool(meta) != null && block.getHarvestTool(meta).equals("pickaxe") || (stack.hasTagCompound() && block.getHarvestTool(meta) != null && block.getHarvestTool(meta).equals("axe") && stack.getTagCompound().getInteger("Axe") != 0) || (stack.hasTagCompound() && block.getHarvestTool(meta) != null && block.getHarvestTool(meta).equals("shovel") && stack.getTagCompound().getInteger("Shovel") != 0) || isEffective(block.getMaterial());
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (block.getHarvestTool(meta) == null)
            return 0.5F;
        if (stack.getItemDamage() >= stack.getMaxDamage() - 1)
            return -1F;
        if (block.getHarvestTool(meta).equals("pickaxe"))
            return super.getDigSpeed(stack,block,meta);
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
        return super.getHarvestLevel(stack,toolClass);
    }

    @Override
    public boolean checkMaterial(ItemStack itemStack) {
        return false;
    }

    @Override
    public ItemStack getRepairMaterial() {
        return null;
    }

    @Override
    public boolean getMAxe() {
        return MAxe;
    }

    @Override
    public boolean getMShovel() {
        return MShovel;
    }

    @Override
    public boolean isIsRepair() {
        return isRepair;
    }

    @Override
    public boolean isIsAchiv() {
        return isAchiv;
    }

    @Override
    public boolean getMDiamond() {
        return MDiamond;
    }

    @Override
    public boolean getMTorch() {
        return MTorch;
    }
    protected Material[] getEffectiveMaterials() {
        return materials;
    }

    static Material[] materials = new Material[]{Material.rock, Material.iron, Material.ice, Material.glass, Material.piston, Material.anvil};

    public boolean isEffective(Material material) {
        for (Material m : getEffectiveMaterials())
            if (m == material)
                return true;

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister registry)
    {
        this.itemIcon = registry.registerIcon("simplyhammer:clockworkHammer");
    }

}
