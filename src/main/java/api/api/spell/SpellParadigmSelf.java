package api.api.spell;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import api.api.soulNetwork.SoulNetworkHandler;

public class SpellParadigmSelf extends SpellParadigm
{
    public List<api.api.spell.ISelfSpellEffect> selfSpellEffectList;

    public SpellParadigmSelf()
    {
        selfSpellEffectList = new ArrayList();
    }

    @Override
    public void enhanceParadigm(api.api.spell.SpellEnhancement enh)
    {

    }

    @Override
    public void castSpell(World world, EntityPlayer entityPlayer, ItemStack itemStack)
    {
        this.applyAllSpellEffects();
        
        int cost = this.getTotalCost();
        
        if(!SoulNetworkHandler.syphonAndDamageFromNetwork(itemStack, entityPlayer, cost))
        {
        	return;
        }

        for (api.api.spell.ISelfSpellEffect eff : selfSpellEffectList)
        {
            eff.onSelfUse(world, entityPlayer);
        }
    }

    public void addSelfSpellEffect(api.api.spell.ISelfSpellEffect eff)
    {
        if (eff != null)
        {
            this.selfSpellEffectList.add(eff);
        }
    }

    @Override
    public int getDefaultCost()
    {
        return 100;
    }

}
