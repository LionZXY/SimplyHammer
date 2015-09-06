package api.api.alchemy.energy;

import net.minecraftforge.common.util.ForgeDirection;

public interface IReagentHandler
{
    int fill(ForgeDirection from, ReagentStack resource, boolean doFill);

    ReagentStack drain(ForgeDirection from, ReagentStack resource, boolean doDrain);

    ReagentStack drain(ForgeDirection from, int maxDrain, boolean doDrain);

    boolean canFill(ForgeDirection from, api.api.alchemy.energy.Reagent reagent);

    boolean canDrain(ForgeDirection from, api.api.alchemy.energy.Reagent reagent);

    api.api.alchemy.energy.ReagentContainerInfo[] getContainerInfo(ForgeDirection from);
}
