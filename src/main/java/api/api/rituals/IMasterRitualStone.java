package api.api.rituals;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface IMasterRitualStone extends api.api.alchemy.energy.ISegmentedReagentHandler
{
    void performRitual(World world, int x, int y, int z, String ritualID);

    String getOwner();

    void setCooldown(int newCooldown);

    int getCooldown();

    void setVar1(int newVar1);

    int getVar1();

    void setActive(boolean active);

    int getDirection();

    World getWorld();

    int getXCoord();

    int getYCoord();

    int getZCoord();

    NBTTagCompound getCustomRitualTag();

    void setCustomRitualTag(NBTTagCompound tag);
    
    boolean areTanksEmpty();
    
    int getRunningTime();
    
    LocalRitualStorage getLocalStorage();
    
    void setLocalStorage(LocalRitualStorage storage);
}
