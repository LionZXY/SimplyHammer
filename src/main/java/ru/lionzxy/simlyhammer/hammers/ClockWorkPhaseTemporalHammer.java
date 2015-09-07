package ru.lionzxy.simlyhammer.hammers;

import lumaceon.mods.clockworkphase.item.construct.clockwork.tool.ItemTemporalClockworkPickaxe;
import net.minecraft.item.Item;
import ru.lionzxy.simlyhammer.utils.AddHammers;

/**
 * Created by nikit on 07.09.2015.
 */
public class ClockWorkPhaseTemporalHammer extends ItemTemporalClockworkPickaxe {
    public ClockWorkPhaseTemporalHammer(ToolMaterial mat) {
        super(mat);
    }
    @Override
    public Item getItemChangeTo()
    {
        return AddHammers.CWPHammer;
    }
}
