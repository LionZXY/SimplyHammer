package ru.lionzxy.simlyhammer.client.resource;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import ru.lionzxy.simlyhammer.utils.Ref;

import javax.annotation.Resource;
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
        if (ResourcePack.res.containsKey(hammerName + "Icon") || ResourcePack.res.containsKey(hammerName + "Model"))
            return;
        if (model_templare == null || icon_templare == null || templare_handle == null || templare_model_handle == null)
            new ResourceLoader();
        BufferedImage icon = icon_templare.addHead(templare_handle, 15, 1, color.getRGB());
        BufferedImage model = model_templare.addHead(templare_model_handle, 100, 216, color.getRGB());
        ResourcePack.res.put(hammerName + "Icon", icon);
        ResourcePack.res.put(hammerName + "Model", model);
        try {
            new File(Loader.instance().getConfigDir() + "/SimplyHammers/resource/", hammerName + "Icon.png").createNewFile();
            new File(Loader.instance().getConfigDir() + "/SimplyHammers/resource/", hammerName + "Model.png").createNewFile();
            ImageIO.write(icon, "PNG", new File(Loader.instance().getConfigDir() + "/SimplyHammers/resource/", hammerName + "Icon.png"));
            ImageIO.write(model, "PNG", new File(Loader.instance().getConfigDir() + "/SimplyHammers/resource/", hammerName + "Model.png"));
        } catch (Exception e) {
            FMLLog.bigWarning("[SimplyHammer] Error save generate image");
        }
    }

    public static void loadAllTexture(String startPath, String fromFolder) {
        File folder = new File(fromFolder);
        try {
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    ResourcePack.res.put(file.getName().substring(0, file.getName().lastIndexOf('.')),
                            ImageIO.read(file));
                    FMLLog.fine("Resource " + file.getName() + " load. Available on path: \"" + startPath + file.getName().substring(0, file.getName().lastIndexOf('.')) + "\"");
                } else if (file.isDirectory())
                    loadAllTexture(startPath + folder.getName() + "/", fromFolder + folder.getName() + "/");
            }
        } catch (Exception e) {
        }
    }
}
