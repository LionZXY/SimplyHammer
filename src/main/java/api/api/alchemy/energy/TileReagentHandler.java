
package api.api.alchemy.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileReagentHandler extends TileEntity implements IReagentHandler
{
    protected ReagentContainer tank = new ReagentContainer(4000);

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        tank.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tank.writeToNBT(tag);
    }

    /* IReagentHandler */
    @Override
    public int fill(ForgeDirection from, api.api.alchemy.energy.ReagentStack resource, boolean doFill)
    {
        return tank.fill(resource, doFill);
    }

    @Override
    public api.api.alchemy.energy.ReagentStack drain(ForgeDirection from, api.api.alchemy.energy.ReagentStack resource, boolean doDrain)
    {
        if (resource == null || !resource.isReagentEqual(tank.getReagent()))
        {
            return null;
        }
        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public api.api.alchemy.energy.ReagentStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, api.api.alchemy.energy.Reagent reagent)
    {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, api.api.alchemy.energy.Reagent reagent)
    {
        return true;
    }

    @Override
    public ReagentContainerInfo[] getContainerInfo(ForgeDirection from)
    {
        return new ReagentContainerInfo[]{tank.getInfo()};
    }
}