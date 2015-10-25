package ru.lionzxy.simlyhammer.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import net.minecraft.crash.CrashReport;
import net.minecraftforge.common.config.Property;

import java.io.*;

/**
 * Created by nikit_000 on 06.10.2015.
 */
public class JsonConfig {

    private static JsonObject mainJson = new JsonObject();
    static File jsonFile = new File(Loader.instance().getConfigDir() + "/SimplyHammers", "ModHammers.json");


    public static void save() {

        if (!jsonFile.canWrite()) {
            try {
                jsonFile.getParentFile().mkdirs();
                jsonFile.createNewFile();
            } catch (Exception e) {
                FMLLog.bigWarning("Can't create json mod config!");
                e.printStackTrace();
            }
        }

        try {
            FileOutputStream os = new FileOutputStream(jsonFile);
            os.write(getFormatedText(mainJson.toString()).getBytes());
            os.close();
        } catch (Exception e) {
            FMLLog.bigWarning("Can't save json mod config!");
            e.printStackTrace();
        }
    }

    public static void load() {

        if (!jsonFile.canWrite()) {
            try {
                jsonFile.getParentFile().mkdirs();
                jsonFile.createNewFile();
            } catch (Exception e) {
                FMLLog.bigWarning("Can't create json mod config!");
                e.printStackTrace();
            }
        }

        try {
            mainJson = new JsonParser().parse(new FileReader(jsonFile)).getAsJsonObject();
        } catch (Exception e) {
            FMLLog.bigWarning("Can't load json mod config!");
            e.printStackTrace();
        }
    }

    public static Property get(String category, String name, boolean defaultValue) {
        Property prop = new Property(category, name, Property.Type.BOOLEAN);

        try {
            if (mainJson.get(category) == null)
                mainJson.add(category, new JsonObject());
            JsonObject obj = mainJson.get(category).getAsJsonObject();

            if (obj.get(name) == null)
                obj.addProperty(name, defaultValue);

            prop.set(obj.get(name).getAsBoolean());
        } catch (Exception e) {
            CrashReport.makeCrashReport(e, "Not read/write/parse config file SimplyHammers");
            e.printStackTrace();
            return null;
        }

        return prop;
    }

    public static Property get(String category, String name, int defaultValue) {
        Property prop = new Property(category, name, Property.Type.INTEGER);

        try {
            if (mainJson.get(category) == null)
                mainJson.add(category, new JsonObject());
            JsonObject obj = mainJson.get(category).getAsJsonObject();

            if (obj.get(name) == null)
                obj.addProperty(name, defaultValue);

            prop.set(obj.get(name).getAsInt());
        } catch (Exception e) {
            CrashReport.makeCrashReport(e, "Not read/write/parse config file SimplyHammers");
            e.printStackTrace();
            return null;
        }

        return prop;
    }

    public static Property get(String category, String name, double defaultValue) {
        Property prop = new Property(category, name, Property.Type.DOUBLE);

        try {
            if (mainJson.get(category) == null)
                mainJson.add(category, new JsonObject());
            JsonObject obj = mainJson.get(category).getAsJsonObject();

            if (obj.get(name) == null)
                obj.addProperty(name, defaultValue);

            prop.set(obj.get(name).getAsDouble());
        } catch (Exception e) {
            CrashReport.makeCrashReport(e, "Not read/write/parse config file SimplyHammers");
            e.printStackTrace();
            return null;
        }

        return prop;
    }

    public static Property get(String category, String name, String defaultValue) {
        Property prop = new Property(category, name, Property.Type.STRING);

        try {
            if (mainJson.get(category) == null)
                mainJson.add(category, new JsonObject());
            JsonObject obj = mainJson.get(category).getAsJsonObject();

            if (obj.get(name) == null)
                obj.addProperty(name, defaultValue);

            prop.set(obj.get(name).getAsString());
        } catch (Exception e) {
            CrashReport.makeCrashReport(e, "Not read/write/parse config file SimplyHammers");
            e.printStackTrace();
            return null;
        }

        return prop;
    }

    public static String getFormatedText(String in) {
        StringBuilder sb = new StringBuilder();
        boolean isIgnore = false;
        int tabCount = 0;
        int b;
        for (int i = 0; i < in.length(); i++) {
            sb.append(in.charAt(i));
            if (in.charAt(i) == '\"')
                isIgnore = !isIgnore;
            if (!isIgnore)
                switch (in.charAt(i)) {
                    case '{':
                    case '[':
                        tabCount++;
                    case ',':
                        sb.append('\n');
                        for (b = 0; b < tabCount; b++)
                            sb.append('\t');
                        break;
                    case '}':
                    case ']':
                        tabCount--;
                        sb.deleteCharAt(sb.length() - 1);
                        sb.append("\n");
                        for (b = 0; b < tabCount; b++)
                            sb.append('\t');
                        sb.append(in.charAt(i));
                }
        }
        return sb.toString();
    }
}