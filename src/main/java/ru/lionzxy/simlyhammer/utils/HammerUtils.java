package ru.lionzxy.simlyhammer.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import ru.lionzxy.simlyhammer.commons.config.Config;
import ru.lionzxy.simlyhammer.libs.HammerSettings;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * Created by LionZXY on 08.09.2015.
 * SimplyHammer v0.9
 */
public class HammerUtils {
    public static Item openBlocksDevNull;
    public static int translateToLocal = -1;

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

    public static void checkUpdate(EntityPlayer player) {
        if (Config.checkUpdate) {
            try {
                JsonObject object = new JsonParser().parse(getSite("https://widget.mcf.li/mc-mods/minecraft/237338-simply-hammers.json", "utf8")).getAsJsonObject().get("versions").getAsJsonObject().get(Ref.MINECRAFTVERSION).getAsJsonArray().get(0).getAsJsonObject();
                if (findWord(object.get("name").getAsString(), Ref.VERSION, false) == -1 && object.get("type").getAsString().equalsIgnoreCase("release")) {
                    player.addChatComponentMessage(new ChatComponentTranslation(EnumChatFormatting.WHITE + "Update " + object.get("name") + " are available on "));
                    IChatComponent component = IChatComponent.Serializer.func_150699_a("{\n" +
                            "\"text\":\"Curse\",\n" +
                            "\"color\":\"yellow\",\n" +
                            "\"hoverEvent\":{\n" +
                            "\"action\":\"show_text\",\n" +
                            "\"value\":{\n" +
                            "\"text\":\"Click this button for download latest version\",\n" +
                            "\"color\":\"yellow\"\n" +
                            "}\n" +
                            "},\n" +
                            "\"clickEvent\":{\n" +
                            "\"action\":\"open_url\",\n" +
                            "\"value\":\"" + object.get("url").getAsString() + "\"\n" +
                            "}\n" +
                            "}");
                    player.addChatComponentMessage(component);
                }
            } catch (Exception e) {
                player.addChatComponentMessage(new ChatComponentTranslation("[Simply Hammer] Update check aborted!"));
            }
        }
    }

    public static int findWord(String from, String word, boolean ignoreCase) {
        for (int i = 0; i < from.length(); i++)
            if (equalsChar(from.charAt(i), word.charAt(0), ignoreCase))
                if (checkAllWord(from, i, word, ignoreCase))
                    return i;
        return -1;
    }

    static boolean equalsChar(char a, char b, boolean ignoreCase) {
        return ignoreCase ? (Character.toLowerCase(a) == Character.toLowerCase(b)) || (Character.toTitleCase(a) == Character.toTitleCase(b)) : a == b;
    }

    static boolean checkAllWord(String from, int charp, String word, boolean ignoreCase) {
        if (from.length() >= charp + word.length()) {
            for (int i = 1; i < word.length(); i++)
                if (!equalsChar(from.charAt(charp + i), word.charAt(i), ignoreCase))
                    return false;
        } else return false;
        return true;
    }

    public static void addInformation(List list, HammerSettings hammerSettings, ItemStack itemStack){

        if (translateToLocal == -1) {
            int tmp = 0;
            while (!StatCollector.translateToLocal("information.hammer.simply." + tmp).equalsIgnoreCase("information.hammer.simply." + tmp)) {
                tmp += 1;
            }
            translateToLocal = tmp;
        }

        String usesLeft = (itemStack.getMaxDamage() - itemStack.getItemDamage()) + "",
                harvestLevel = hammerSettings.getHarvestLevel() + "",
                repairMaterial = hammerSettings.isRepair() ? hammerSettings.getRepairMaterial() : hammerSettings.isInfinity() ? StatCollector.translateToLocal("information.hammer.simply.infinity") : StatCollector.translateToLocal("information.hammer.simply.noRepairable"),
                efficiency = hammerSettings.getEffiency() + "";

        for (int i = 0; i < translateToLocal; i++) {
            list.add(StatCollector.translateToLocal("information.hammer.simply." + i).replaceAll("%usesLeft%", usesLeft).
                    replaceAll("%harvestLevel%", harvestLevel).replaceAll("%repairMaterial%", repairMaterial).
                    replaceAll("%efficiency%", efficiency));
        }
    }
}
