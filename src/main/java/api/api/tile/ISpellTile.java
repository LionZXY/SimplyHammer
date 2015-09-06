package api.api.tile;

import net.minecraftforge.common.util.ForgeDirection;

public interface ISpellTile 
{
	void modifySpellParadigm(api.api.spell.SpellParadigm parad);
	
	boolean canInputRecieveOutput(ForgeDirection output);
}
