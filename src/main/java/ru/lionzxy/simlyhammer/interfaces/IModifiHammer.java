package ru.lionzxy.simlyhammer.interfaces;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.oredict.OreDictionary;
import ru.lionzxy.simlyhammer.SimplyHammer;

/**
 * Created by nikit on 07.09.2015.
 */
public interface IModifiHammer {
    int breakRadius = 1, breakDepth = 0, oreDictId = 0;

    boolean checkMaterial(ItemStack itemStack);

    ItemStack getRepairMaterial();

    boolean getMAxe();
    boolean getMShovel();
    boolean isIsRepair();
    boolean isIsAchiv();
    boolean getMDiamond();
    boolean getMTorch();

}
