package api.api.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface ISelfSpellEffect
{
    void onSelfUse(World world, EntityPlayer player);
}
