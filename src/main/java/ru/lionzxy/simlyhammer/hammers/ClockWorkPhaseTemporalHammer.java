package ru.lionzxy.simlyhammer.hammers;

import lumaceon.mods.clockworkphase.item.construct.clockwork.tool.ItemTemporalClockworkPickaxe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.lionzxy.simlyhammer.utils.AddHammers;

/**
 * Created by nikit on 07.09.2015.
 */
public class ClockWorkPhaseTemporalHammer extends ItemTemporalClockworkPickaxe {
    public ClockWorkPhaseTemporalHammer(ToolMaterial mat) {
        super(mat);
        this.setUnlocalizedName("cwpTemporalHammer");
        this.setTextureName("simplyhammer:" + getUnlocalizedName());
    }

    @Override
    public Item getItemChangeTo() {
        return AddHammers.CWPHammer;
    }

    public String getUnlocalizedName() {
        return "item." + "cwpTemporalHammer";
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack p_77667_1_) {
        return "item." + "cwpTemporalHammer";
    }
}
