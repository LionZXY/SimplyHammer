package api.api.alchemy.energy;

public final class ReagentContainerInfo
{
    public final ReagentStack reagent;
    public final int capacity;

    public ReagentContainerInfo(ReagentStack reagent, int capacity)
    {
        this.reagent = reagent;
        this.capacity = capacity;
    }

    public ReagentContainerInfo(api.api.alchemy.energy.IReagentContainer tank)
    {
        this.reagent = tank.getReagent();
        this.capacity = tank.getCapacity();
    }
}
