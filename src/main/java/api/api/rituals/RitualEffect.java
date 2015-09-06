package api.api.rituals;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import api.api.alchemy.energy.ReagentStack;

public abstract class RitualEffect
{
    public abstract void performEffect(IMasterRitualStone ritualStone);

    public boolean startRitual(IMasterRitualStone ritualStone, EntityPlayer player)
    {
        return true;
    }

    public void onRitualBroken(IMasterRitualStone ritualStone, RitualBreakMethod method)
    {

    }

    public abstract int getCostPerRefresh();

    public int getInitialCooldown()
    {
        return 0;
    }

    public abstract List<api.api.rituals.RitualComponent> getRitualComponentList();

    public boolean canDrainReagent(IMasterRitualStone ritualStone, api.api.alchemy.energy.Reagent reagent, int amount, boolean doDrain)
    {
        if (ritualStone == null || reagent == null || amount == 0)
        {
            return false;
        }

        ReagentStack reagentStack = new ReagentStack(reagent, amount);

        ReagentStack stack = ritualStone.drain(ForgeDirection.UNKNOWN, reagentStack, false);

        if (stack != null && stack.amount >= amount)
        {
            if (doDrain)
            {
                ritualStone.drain(ForgeDirection.UNKNOWN, reagentStack, true);
            }

            return true;
        }

        return false;
    }
    
    public LocalRitualStorage getNewLocalStorage()
    {
    	return new LocalRitualStorage();
    }
    
    public void addOffsetRunes(ArrayList<api.api.rituals.RitualComponent> ritualList, int off1, int off2, int y, int rune)
    {
    	ritualList.add(new api.api.rituals.RitualComponent(off1, y, off2, rune));
    	ritualList.add(new api.api.rituals.RitualComponent(off2, y, off1, rune));
    	ritualList.add(new api.api.rituals.RitualComponent(off1, y, -off2, rune));
    	ritualList.add(new api.api.rituals.RitualComponent(-off2, y, off1, rune));
    	ritualList.add(new api.api.rituals.RitualComponent(-off1, y, off2, rune));
    	ritualList.add(new api.api.rituals.RitualComponent(off2, y, -off1, rune));
    	ritualList.add(new api.api.rituals.RitualComponent(-off1, y, -off2, rune));
    	ritualList.add(new api.api.rituals.RitualComponent(-off2, y, -off1, rune));
    }
    
    public void addCornerRunes(ArrayList<api.api.rituals.RitualComponent> ritualList, int off1, int y, int rune)
    {
    	ritualList.add(new api.api.rituals.RitualComponent(off1, y, off1, rune));
    	ritualList.add(new api.api.rituals.RitualComponent(off1, y, -off1, rune));
    	ritualList.add(new api.api.rituals.RitualComponent(-off1, y, -off1, rune));
    	ritualList.add(new api.api.rituals.RitualComponent(-off1, y, off1, rune));
    }
    
    public void addParallelRunes(ArrayList<api.api.rituals.RitualComponent> ritualList, int off1, int y, int rune)
    {
    	ritualList.add(new api.api.rituals.RitualComponent(off1, y, 0, rune));
    	ritualList.add(new api.api.rituals.RitualComponent(-off1, y, 0, rune));
    	ritualList.add(new api.api.rituals.RitualComponent(0, y, -off1, rune));
    	ritualList.add(new api.api.rituals.RitualComponent(0, y, off1, rune));
    }
}
