package ru.lionzxy.simlyhammer.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.config.Property;

import java.io.*;

/**
 * Created by nikit_000 on 06.10.2015.
 */
public class JsonConfig {
    File jsonFile = new File(Loader.instance().getConfigDir(), "SimplyHammers.json");
    JsonObject mainJson = new JsonObject();

    public JsonConfig() {
        try {
            if (!jsonFile.canWrite()) {
                jsonFile.getParentFile().mkdirs();
                jsonFile.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            FileOutputStream os = new FileOutputStream(jsonFile);
            os.write(getFormatedText(mainJson.toString()).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {

        jsonFile.getParentFile().mkdirs();
        try {
            jsonFile.createNewFile();
            mainJson = new JsonParser().parse(new JsonReader(new FileReader(jsonFile))).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Property get(String category, String key, boolean defaultValue) {
        if (mainJson.get(category) == null) {
            mainJson.add(category, new JsonObject());
        }
        if (mainJson.getAsJsonObject(category).get(key) == null) {
            mainJson.getAsJsonObject(category).addProperty(key, defaultValue);
        }
        Property property = new Property(category, key, Property.Type.BOOLEAN);
        property.set(mainJson.getAsJsonObject(category).get(key).getAsBoolean());
        return property;
    }

    public Property get(String category, String key, int defaultValue) {
        if (mainJson.get(category) == null) {
            mainJson.add(category, new JsonObject());
        }
        if (mainJson.getAsJsonObject(category).get(key) == null) {
            mainJson.getAsJsonObject(category).addProperty(key, defaultValue);
        }

        Property property = new Property(category, key, Property.Type.INTEGER);
        property.set(mainJson.getAsJsonObject(category).get(key).getAsInt());
        return property;
    }


    public Property get(String category, String key, String defaultValue) {
        if (mainJson.get(category) == null) {
            mainJson.add(category, new JsonObject());
        }
        if (mainJson.getAsJsonObject(category).get(key) == null) {
            mainJson.getAsJsonObject(category).addProperty(key, defaultValue);
        }

        Property property = new Property(category, key, Property.Type.STRING);
        property.set(mainJson.getAsJsonObject(category).get(key).getAsString());
        return property;
    }


    public Property get(String category, String key, double defaultValue) {
        if (mainJson.get(category) == null) {
            mainJson.add(category, new JsonObject());
        }
        if (mainJson.getAsJsonObject(category).get(key) == null) {
            mainJson.getAsJsonObject(category).addProperty(key, defaultValue);
        }

        Property property = new Property(category, key, Property.Type.DOUBLE);
        property.set(mainJson.getAsJsonObject(category).get(key).getAsDouble());
        return property;
    }

    public static String getFormatedText(String in){
        StringBuilder sb = new StringBuilder();
        boolean isIgnore = false;
        int tabCount = 0;
        int b;
        for(int i = 0; i < in.length();i++){
            sb.append(in.charAt(i));
            if(in.charAt(i) == '\"')
                isIgnore=!isIgnore;
            if(!isIgnore)
            switch (in.charAt(i)){
                case '{':
                case '[':
                    tabCount++;
                case ',':
                    sb.append('\n');
                    for(b = 0; b < tabCount; b++)
                        sb.append('\t');
                    break;
                case '}':
                case ']':
                    tabCount--;
                    sb.deleteCharAt(sb.length()-1);
                    sb.append("\n");
                    for(b = 0; b < tabCount; b++)
                        sb.append('\t');
                    sb.append(in.charAt(i));
            }
        }
        return sb.toString();
    }
}