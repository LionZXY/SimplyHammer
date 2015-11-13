package ru.lionzxy.simlyhammer.commons.items;

import net.minecraft.item.Item;
import ru.lionzxy.simlyhammer.utils.AddHammers;

/**
 * Created by nikit on 12.09.2015.
 */
public class AddItems {
    public static Item trash, vacuum, autosmelt, loupe, stick, ductape;
    public static void init(){
        trash = new TrashItem();
        vacuum = new VacuumItem();
        autosmelt = new AutoSmeltItem();
        stick = new Stick();
        ductape = new Ductape();
        loupe = new Loupe();
        AddHammers.addAllHammers();
    }
}
