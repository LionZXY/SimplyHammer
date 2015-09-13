package ru.lionzxy.simlyhammer.items;

import net.minecraft.item.Item;
import ru.lionzxy.simlyhammer.utils.AddHammers;

/**
 * Created by nikit on 12.09.2015.
 */
public class AddItems {
    public static Item trash, vacuum, autosmelt;
    public static void init(){
        AddHammers.addAllHammers();
        trash = new TrashItem();
        vacuum = new VacuumItem();
        autosmelt = new AutoSmeltItem();
    }
}
