package ru.lionzxy.simlyhammer.client.resource;

import cpw.mods.fml.common.Loader;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import ru.lionzxy.simlyhammer.utils.Ref;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by LionZXY on 23.12.2015.
 * BetterWorkbench
 */
public class ResourcePack implements IResourcePack {

    public static ResourcePack INSTANCE = new ResourcePack();

    @Override
    public InputStream getInputStream(ResourceLocation rl) throws IOException {
        if (!resourceExists(rl)) {
            return null;
        }
        return new FileInputStream(new File(Loader.instance().getConfigDir() + "/SimplyHammers/resource/", rl.getResourcePath()));
    }

    @Override
    public boolean resourceExists(ResourceLocation rl) {
        File fileRequested = new File(Loader.instance().getConfigDir() + "/SimplyHammers/resource/", rl.getResourcePath() + ".png");
        if (fileRequested.exists()) {
            System.out.println(Loader.instance().getConfigDir() + "/SimplyHammers/resource/" + rl.getResourcePath() + ".png");
            return true;
        }
        return false;
    }

    @Override
    public Set getResourceDomains() {
        HashSet<String> folders = new HashSet();
        folders.add(Ref.MODID);
        return folders;
    }

    @Override
    public IMetadataSection getPackMetadata(IMetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
        return null;
    }

    @Override
    public BufferedImage getPackImage() throws IOException {
        return null;
    }

    @Override
    public String getPackName() {
        return Ref.MODID;
    }


}
