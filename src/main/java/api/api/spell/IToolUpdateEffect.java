package api.api.spell;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IToolUpdateEffect
{
    int onUpdate(ItemStack toolStack, World world, Entity par3Entity, int invSlot, boolean inHand);
}
