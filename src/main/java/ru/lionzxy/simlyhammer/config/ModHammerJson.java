package ru.lionzxy.simlyhammer.config;

import com.google.gson.JsonArray;
import cpw.mods.fml.common.Loader;
import ru.lionzxy.simlyhammer.libs.HammerSettings;
import ru.lionzxy.simlyhammer.utils.AddHammers;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by LionZXY on 16.10.2015.
 * SimplyHammer v0.9
 */
public class ModHammerJson {

    public static JsonArray jsonArray = new JsonArray();
    public static File modJson = new File(Loader.instance().getConfigDir() + "/SimplyHammers", "ModHammers.json");
    public static void save(){
        if(!modJson.canWrite()){
            try {
                modJson.mkdirs();
                modJson.createNewFile();
                try(FileOutputStream os = new FileOutputStream(modJson)){
                    os.write(JsonConfig.getFormatedText(jsonArray.toString()).getBytes());
                }finally {

                }
            }catch (Exception e){

            }}
    }
    public static void addModHammer(String name){

    }

    public static HammerSettings getHammerSettings(String name){
        //public HammerSettings(String name, int breakRadius, int harvestLevel, float speed, int damage, String rm, boolean infinity) {

        if(!modJson.canWrite()){

        } return null;
    }
}
