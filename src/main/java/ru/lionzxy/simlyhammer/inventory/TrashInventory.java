package ru.lionzxy.simlyhammer.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

/**
 * Created by nikit on 12.09.2015.
 */
public class TrashInventory implements IInventory {

    public static final int INV_SIZE = 9;

    /**
     * Inventory's size must be same as number of slots you add to the Container class
     */
    public ItemStack[] inventory = new ItemStack[INV_SIZE];

    /**
     * Provides NBT Tag Compound to reference
     */
    private EntityPlayer player;

    public TrashInventory(ItemStack stack, EntityPlayer player) {
        this.player = player;
        // Just in case the itemstack doesn't yet have an NBT Tag Compound:
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        // note that it's okay to use stack instead of invItem right there
        // both reference the same memory location, so whatever you change using
        // either reference will change in the other

        // Read the inventory contents from NBT
        readFromNBT(stack.getTagCompound());

    }

    public ItemStack getInvItem() {
        return player.inventory.getCurrentItem();
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
            if (stack.stackSize > amount) {
                stack = stack.splitStack(amount);
                markDirty();
            } else {
                setInventorySlotContents(slot, null);
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = getStackInSlot(slot);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        this.inventory[slot] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        markDirty();
    }

    @Override
    public String getInventoryName() {
        return "Trash Inventory";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public void markDirty() {
        for (int i = 0; i < getSizeInventory(); i++) {
            if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0)
                inventory[i] = null;
        }

        writeToNBT(player.inventory.getCurrentItem().getTagCompound());

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    public void allSave() {
        writeToNBT(player.inventory.getCurrentItem().getTagCompound());
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {
        writeToNBT(player.inventory.getCurrentItem().getTagCompound());
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        switch (slot) {
            // case 0: if(itemstack.getItem()== bullet){return true;}break;
            case 1:
                if (itemstack.getItem() == Items.gunpowder) {
                    return true;
                }
                break;
            case 2:
                if (itemstack.getItem() == Items.flint_and_steel) {
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * A custom method to read our inventory from an ItemStack's NBT compound
     */
    public void readFromNBT(NBTTagCompound tagcompound) {
        NBTTagList items = tagcompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound item = /*(NBTTagCompound)*/ items.getCompoundTagAt(i);
            byte slot = item.getByte("Slot");

            // Just double-checking that the saved slot index is within our inventory array bounds
            if (slot >= 0 && slot < getSizeInventory()) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(item);
            }
        }
    }

    /**
     * A custom method to write our inventory to an ItemStack's NBT compound
     */
    public void writeToNBT(NBTTagCompound tagcompound) {
        if (tagcompound != null) {
            tagcompound.setBoolean("Trash",true);
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i] != null && inventory[i].stackSize != 0) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    if (inventory[i].stackSize > this.getInventoryStackLimit())
                        inventory[i].stackSize = this.getInventoryStackLimit();
                    inventory[i].writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }
            tagcompound.setTag("Items", nbttaglist);
        }
    }
}
