package ru.lionzxy.simlyhammer.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.lionzxy.simlyhammer.items.AddItems;

import java.util.Objects;

/**
 * Created by LionZXY on 22.10.2015.
 * SimplyHammer v0.9
 */
public class ReflectionHelper {
    /*
    * 	public static ItemStack getItem(String name) {
		try {
			if (Ic2Items == null) Ic2Items = Class.forName(getPackage() + ".core.Ic2Items");

			Object ret = Ic2Items.getField(name).get(null);

			if (ret instanceof ItemStack) {
				return (ItemStack) ret;
			}
			return null;
		} catch (Exception e) {
			System.out.println("IC2 API: Call getItem failed for "+name);

			return null;
		}
	}*/
    public static ItemStack getItemStackRA(String nameVar) {
        try {
            Class<?> classRA = Class.forName("cofh.redstonearsenal.item.RAItems");
            System.out.println(classRA.getField(nameVar).getName()); //[17:21:04] [Client thread/INFO] [STDOUT]: [ru.lionzxy.simlyhammer.utils.ReflectionHelper:getItemStackRA:35]: rodObsidianFlux
            System.out.println(classRA.getField(nameVar).toString());//[17:21:04] [Client thread/INFO] [STDOUT]: [ru.lionzxy.simlyhammer.utils.ReflectionHelper:getItemStackRA:36]: public static net.minecraft.item.ItemStack cofh.redstonearsenal.item.RAItems.rodObsidianFlux
            System.out.println(classRA.getField(nameVar).get(null));//[17:21:04] [Client thread/INFO] [STDOUT]: [ru.lionzxy.simlyhammer.utils.ReflectionHelper:getItemStackRA:37]: null
            Object obj = classRA.getField(nameVar).get(null);
            if (obj instanceof ItemStack)
                return (ItemStack) obj;
            if (obj instanceof Item)
                return new ItemStack((Item) obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
