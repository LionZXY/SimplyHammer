package ru.lionzxy.simlyhammer.client.resource;

import com.google.gson.JsonElement;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import ru.lionzxy.simlyhammer.commons.config.GTJsonArray;
import ru.lionzxy.simlyhammer.utils.Ref;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by LionZXY on 23.12.2015.
 */
public class ResourceLoader {

    static BufferedImage templare_handle, templare_model_handle;
    static CRGB model_templare, icon_templare;
    BufferedImage templare_head, templare_model_head;

    public ResourceLoader() {
        try {
            templare_head = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Ref.MODID, "textures/templare/templare_head.png")).getInputStream());
            templare_handle = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Ref.MODID, "textures/templare/templare_handle.png")).getInputStream());
            templare_model_head = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Ref.MODID, "textures/templare/templare_model_head.png")).getInputStream());
            templare_model_handle = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Ref.MODID, "textures/templare/templare_model_handle.png")).getInputStream());


            model_templare = new CRGB(templare_model_head, new Color(-6512734));
            icon_templare = new CRGB(templare_head, new Color(-7040110));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addHammerResource(String hammerName, Color color) {
        if (ResourcePack.INSTANCE.resourceExists(new ResourceLocation(Ref.MODID,"textures/items/" + hammerName + ".png")) || ResourcePack.INSTANCE.resourceExists(new ResourceLocation(Ref.MODID,"textures/model/" + hammerName + ".png")))
            return;
        if (model_templare == null || icon_templare == null || templare_handle == null || templare_model_handle == null)
            new ResourceLoader();
        BufferedImage icon = icon_templare.addHead(templare_handle, 15, 1, color.getRGB());
        BufferedImage model = model_templare.addHead(templare_model_handle, 100, 216, color.getRGB());
        try {
            new File(Loader.instance().getConfigDir() + "/SimplyHammers/resource/textures/items/", hammerName + ".png").createNewFile();
            new File(Loader.instance().getConfigDir() + "/SimplyHammers/resource/textures/model/", hammerName + ".png").createNewFile();
            ImageIO.write(icon, "PNG", new File(Loader.instance().getConfigDir() + "/SimplyHammers/resource/textures/items/", hammerName + ".png"));
            ImageIO.write(model, "PNG", new File(Loader.instance().getConfigDir() + "/SimplyHammers/resource/textures/model/", hammerName + ".png"));
            FMLLog.fine("[SimplyHammer] Hammer " + hammerName + " icon and model generated!");
        } catch (Exception e) {
            FMLLog.bigWarning("[SimplyHammer] Error save generate image");
        }
    }

    @SideOnly(Side.CLIENT)
    public static void init() {
        FMLLog.fine("[SimplyHammer] Start add icons for GregTech");
        new File(Loader.instance().getConfigDir() + "/SimplyHammers/resource/textures/items/").mkdirs();
        new File(Loader.instance().getConfigDir() + "/SimplyHammers/resource/textures/model/").mkdirs();
        for (JsonElement json : GTJsonArray.gregtechJson)
            addHammerResource(json.getAsJsonObject().get("name").getAsString(), new Color(json.getAsJsonObject().get("Color").getAsInt()));
    }
}
