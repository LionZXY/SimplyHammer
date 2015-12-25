package ru.lionzxy.simlyhammer.commons.handlers;

import cpw.mods.fml.common.Loader;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.OreDictionary;
import ru.lionzxy.simlyhammer.interfaces.IModifiHammer;
import ru.lionzxy.simlyhammer.utils.GregTechHelper;

import java.util.List;

/**
 * Created by LionZXY on 17.10.2015.
 * SimplyHammer v0.9
 */
public class CommandHandler implements ICommand {
    @Override
    public String getCommandName() {
        return "sh";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "sh <iteminfo>";
    }

    @Override
    public List getCommandAliases() {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length != 1)
            sender.addChatMessage(new ChatComponentText("Usage: sh <iteminfo>"));
        else {
            if (args[0].equals("iteminfo")) {
                if (sender instanceof EntityPlayer) {
                    ItemStack is = ((EntityPlayer) sender).getCurrentEquippedItem();
                    if (is == null)
                        return;
                    ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText("1. Name: " + is.getDisplayName()));
                    ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText("2. Item Path: " + Item.itemRegistry.getNameForObject(is.getItem())));
                    ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText("3. Damage: " + is.getItemDamage()));
                    ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText("4. OreDict: "));

                    for (int i : OreDictionary.getOreIDs(is))
                        ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText(OreDictionary.getOreName(i)));

                    ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText("================"));

                    if (is.hasTagCompound() && is.getItem() instanceof IModifiHammer) {
                        ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText("5. Item List: "));
                        for (int i = 0; i < is.getTagCompound().getTagList("ItemStacksInHammer", Constants.NBT.TAG_COMPOUND).tagCount(); i++)
                            ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText(ItemStack.loadItemStackFromNBT(is.getTagCompound().getTagList("ItemStacksInHammer", Constants.NBT.TAG_COMPOUND).getCompoundTagAt(i)).getDisplayName() + " x" + ItemStack.loadItemStackFromNBT(is.getTagCompound().getTagList("ItemStacksInHammer", Constants.NBT.TAG_COMPOUND).getCompoundTagAt(i)).stackSize));
                        ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText("================"));
                         }
                    if(Loader.isModLoaded("gregapi"))
                        ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText("5. Is Pickaxe: " + GregTechHelper.isPickaxe(is)));


                }
            } else sender.addChatMessage(new ChatComponentText("Usage: sh <iteminfo>"));
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
