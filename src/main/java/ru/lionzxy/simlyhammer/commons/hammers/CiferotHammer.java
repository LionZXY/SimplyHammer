package ru.lionzxy.simlyhammer.commons.hammers;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.lionzxy.simlyhammer.client.renders.ColouredFX;
import ru.lionzxy.simlyhammer.libs.HammerSettings;
import ru.lionzxy.simlyhammer.utils.AddHammers;

import java.util.Random;

/**
 * Created by LionZXY on 24.12.2015.
 */
public class CiferotHammer extends BasicHammer{

    public CiferotHammer() {
        super(new HammerSettings("ciferotHammer",3,12,30));
        AddHammers.addCraft(this,null,"blockIron","blockSteel");
        GameRegistry.registerItem(this,hammerSettings.getUnlocalizeName());
        hammerSettings.setLocalizeName("Ciferot's Hammer");
    }

    @Override
    public boolean hitEntity(ItemStack is, EntityLivingBase entity, EntityLivingBase player) {
        if (player instanceof EntityPlayer && ((EntityPlayer) player).getDisplayName().equalsIgnoreCase("Ciferot"))
            return this.giveDamage(is, (EntityPlayer) player);
        else return false;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer playerEntity) {
        if(playerEntity.getDisplayName().equals("Ciferot") || playerEntity.capabilities.isCreativeMode)
            super.onBlockStartBreak(itemstack, X, Y, Z, playerEntity);
        return false;
    }




}
