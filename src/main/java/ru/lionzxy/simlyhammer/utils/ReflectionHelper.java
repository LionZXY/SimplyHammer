package ru.lionzxy.simlyhammer.utils;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
            Object obj = classRA.getDeclaredField(nameVar).get(null);
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

    public static List getResourceList(){
        Field f;
        try {
            f = Minecraft.class.getDeclaredField("defaultResourcePacks");
        } catch (Exception e) {
            try {
                f = Minecraft.class.getDeclaredField("field_110449_ao");
            } catch (NoSuchFieldException e1) {
                return new ArrayList();
            }
        }
        try {
            f.setAccessible(true);
            return (List) f.get(Minecraft.getMinecraft());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
