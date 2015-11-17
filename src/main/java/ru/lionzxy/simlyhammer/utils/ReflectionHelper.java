package ru.lionzxy.simlyhammer.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
