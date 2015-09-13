package ru.lionzxy.simlyhammer.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.interfaces.IVacuum;

/**
 * Created by nikit on 12.09.2015.
 */
public class VacuumItem extends Item {
    public VacuumItem() {
        this.setCreativeTab(SimplyHammer.tabGeneral);
        this.setUnlocalizedName("vacumitem");
        this.setTextureName("simplyhammer:vacumitem");
        this.setMaxStackSize(1);
        GameRegistry.registerItem(this, "vacumitem");
    }
}
