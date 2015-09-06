
package api.api.alchemy.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TileSegmentedReagentHandler extends TileEntity implements ISegmentedReagentHandler
{
    protected api.api.alchemy.energy.ReagentContainer[] tanks;
    protected Map<api.api.alchemy.energy.Reagent, Integer> attunedTankMap;

    public TileSegmentedReagentHandler()
    {
        this(1);
    }

    public TileSegmentedReagentHandler(int numberOfTanks)
    {
        this(numberOfTanks, 1000);
    }

    public TileSegmentedReagentHandler(int numberOfTanks, int tankSize)
    {
        super();

        this.attunedTankMap = new HashMap();
        this.tanks = new api.api.alchemy.energy.ReagentContainer[numberOfTanks];
        for (int i = 0; i < numberOfTanks; i++)
        {
            this.tanks[i] = new api.api.alchemy.energy.ReagentContainer(tankSize);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        NBTTagList tagList = tag.getTagList("reagentTanks", Constants.NBT.TAG_COMPOUND);

        int size = tagList.tagCount();
        this.tanks = new api.api.alchemy.energy.ReagentContainer[size];

        for (int i = 0; i < size; i++)
        {
            NBTTagCompound savedTag = tagList.getCompoundTagAt(i);
            this.tanks[i] = api.api.alchemy.energy.ReagentContainer.readFromNBT(savedTag);
        }

        NBTTagList attunedTagList = tag.getTagList("attunedTankMap", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < attunedTagList.tagCount(); i++)
        {
            NBTTagCompound savedTag = attunedTagList.getCompoundTagAt(i);
            api.api.alchemy.energy.Reagent reagent = api.api.alchemy.energy.ReagentRegistry.getReagentForKey(savedTag.getString("reagent"));
            this.attunedTankMap.put(reagent, savedTag.getInteger("amount"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);

        NBTTagList tagList = new NBTTagList();

        for (int i = 0; i < this.tanks.length; i++)
        {
            NBTTagCompound savedTag = new NBTTagCompound();
            if (this.tanks[i] != null)
            {
                this.tanks[i].writeToNBT(savedTag);
            }
            tagList.appendTag(savedTag);
        }

        tag.setTag("reagentTanks", tagList);

        NBTTagList attunedTagList = new NBTTagList();

        for (Entry<api.api.alchemy.energy.Reagent, Integer> entry : this.attunedTankMap.entrySet())
        {
            NBTTagCompound savedTag = new NBTTagCompound();
            savedTag.setString("reagent", api.api.alchemy.energy.ReagentRegistry.getKeyForReagent(entry.getKey()));
            savedTag.setInteger("amount", entry.getValue());
            attunedTagList.appendTag(savedTag);
        }

        tag.setTag("attunedTankMap", attunedTagList);
    }

    /* ISegmentedReagentHandler */
    @Override
    public int fill(ForgeDirection from, api.api.alchemy.energy.ReagentStack resource, boolean doFill)
    {
        int totalFill = 0;

        boolean useTankLimit = !this.attunedTankMap.isEmpty();

        if (resource != null)
        {
            int totalTanksFillable = useTankLimit ? this.getTanksTunedToReagent(resource.reagent) : this.tanks.length;
            int tanksFilled = 0;

            int maxFill = resource.amount;

            for (int i = this.tanks.length - 1; i >= 0; i--)
            {
                api.api.alchemy.energy.ReagentStack remainingStack = resource.copy();
                remainingStack.amount = maxFill - totalFill;

                boolean doesReagentMatch = tanks[i].getReagent() == null ? false : tanks[i].getReagent().isReagentEqual(remainingStack);

                if (doesReagentMatch)
                {
                    totalFill += tanks[i].fill(remainingStack, doFill);
                    tanksFilled++;
                } else
                {
                    continue;
                }

                if (totalFill >= maxFill || tanksFilled >= totalTanksFillable)
                {
                    return totalFill;
                }
            }

            if (tanksFilled >= totalTanksFillable)
            {
                return totalFill;
            }

            for (int i = this.tanks.length - 1; i >= 0; i--)
            {
                api.api.alchemy.energy.ReagentStack remainingStack = resource.copy();
                remainingStack.amount = maxFill - totalFill;

                boolean isTankEmpty = tanks[i].getReagent() == null;

                if (isTankEmpty)
                {
                    totalFill += tanks[i].fill(remainingStack, doFill);
                    tanksFilled++;
                } else
                {
                    continue;
                }

                if (totalFill >= maxFill || tanksFilled >= totalTanksFillable)
                {
                    return totalFill;
                }
            }
        }
        return totalFill;
    }

    @Override
    public api.api.alchemy.energy.ReagentStack drain(ForgeDirection from, api.api.alchemy.energy.ReagentStack resource, boolean doDrain)
    {
        if (resource == null)
        {
            return null;
        }

        int maxDrain = resource.amount;
        api.api.alchemy.energy.Reagent reagent = resource.reagent;
        int drained = 0;

        for (int i = 0; i < tanks.length; i++)
        {
            if (drained >= maxDrain)
            {
                break;
            }

            if (resource.isReagentEqual(tanks[i].getReagent()))
            {
                api.api.alchemy.energy.ReagentStack drainStack = tanks[i].drain(maxDrain - drained, doDrain);
                if (drainStack != null)
                {
                    drained += drainStack.amount;
                }
            }
        }

        return new api.api.alchemy.energy.ReagentStack(reagent, drained);
    }

    /* Only returns the amount from the first available tank */
    @Override
    public api.api.alchemy.energy.ReagentStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        for (int i = 0; i < tanks.length; i++)
        {
            api.api.alchemy.energy.ReagentStack stack = tanks[i].drain(maxDrain, doDrain);
            if (stack != null)
            {
                return stack;
            }
        }

        return null;
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
    public api.api.alchemy.energy.ReagentContainerInfo[] getContainerInfo(ForgeDirection from)
    {
        api.api.alchemy.energy.ReagentContainerInfo[] info = new api.api.alchemy.energy.ReagentContainerInfo[this.getNumberOfTanks()];
        for (int i = 0; i < this.getNumberOfTanks(); i++)
        {
            info[i] = tanks[i].getInfo();
        }
        return info;
    }

    @Override
    public int getNumberOfTanks()
    {
        return tanks.length;
    }

    @Override
    public int getTanksTunedToReagent(api.api.alchemy.energy.Reagent reagent)
    {
        if (this.attunedTankMap.containsKey(reagent) && this.attunedTankMap.get(reagent) != null)
        {
            return this.attunedTankMap.get(reagent);
        }
        return 0;
    }

    @Override
    public void setTanksTunedToReagent(api.api.alchemy.energy.Reagent reagent, int total)
    {
        if (total == 0 && this.attunedTankMap.containsKey(reagent))
        {
            this.attunedTankMap.remove(reagent);
            return;
        }

        this.attunedTankMap.put(reagent, total);
    }

    @Override
    public Map<api.api.alchemy.energy.Reagent, Integer> getAttunedTankMap()
    {
        return this.attunedTankMap;
    }

    public boolean areTanksEmpty()
    {
        for (int i = 0; i < this.tanks.length; i++)
        {
            if (tanks[i] != null && tanks[i].reagentStack != null)
            {
                return false;
            }
        }

        return true;
    }
}