package ru.lionzxy.simlyhammer.utils;

import cpw.mods.fml.common.registry.GameRegistry;
import jdk.nashorn.internal.runtime.regexp.joni.EncodingHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by LionZXY on 08.09.2015.
 * SimplyHammer v0.9
 */
public class HammerUtils {
    public static Item openBlocksDevNull;

    public static void init() {
        openBlocksDevNull = GameRegistry.findItem("OpenBlocks", "devnull");
    }

    public static ItemStack getItemFromString(String item) {
        String fromSplit[] = item.split(":");
        return fromSplit.length == 2 ? new ItemStack(GameRegistry.findItem(fromSplit[0], fromSplit[1])) : new ItemStack(GameRegistry.findItem(fromSplit[0], fromSplit[1]), 1, Integer.parseInt(fromSplit[2]));
    }

    public static boolean isItemContainsInOreDict(ItemStack itemStack, String oredict) {
        int oreId = OreDictionary.getOreID(oredict);
        for (int id : OreDictionary.getOreIDs(itemStack))
            if (id == oreId)
                return true;
        return false;
    }

    public static String getSite(String url, String enc) throws Exception {

        StringBuffer stringBuffer = new StringBuffer();
        //curl -o file3.htm -H "User-Agent: Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.1.8) Gecko/20100214 Ubuntu/9.10 (karmic) Firefox/3.5.8" -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,/;q=0.8" -H "Accept-Language: en-us,en;q=0.5" -H "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7" -H "Keep-Alive: 300" -H "Connection: keep-alive" http://www.mygarden.com

        if (url.length() < 8 || !(url.substring(0, 7).equalsIgnoreCase("http://") || url.substring(0, 8).equalsIgnoreCase("https://")))
            url = "http://" + url;

        URL urlObj = new URL(url);
        InputStream inputStream = urlObj.openStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, enc));
        String str = br.readLine();
        while (str != null) {
            stringBuffer.append(str);
            str = br.readLine();
        }

        inputStream.close();
        return stringBuffer.toString();
    }

    public static String checkUpdate(){

        return null;
    }
}
