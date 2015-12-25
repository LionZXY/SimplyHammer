package ru.lionzxy.simlyhammer.client.resource;

import cpw.mods.fml.common.Loader;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

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

    public static HashMap<String,BufferedImage> res = new HashMap<>();

    @Override
    public InputStream getInputStream(ResourceLocation rl) throws IOException {
        if (!resourceExists(rl)) {
            return null;
        }
        return new FileInputStream(new File(Loader.instance().getConfigDir() + "/SimplyHammers/resource/" + rl.getResourceDomain(), rl.getResourcePath()));
    }

    @Override
    public boolean resourceExists(ResourceLocation rl) {
        File fileRequested = new File(Loader.instance().getConfigDir() + "/SimplyHammers/resource/" + rl.getResourceDomain(), rl.getResourcePath());
        if (fileRequested.exists()) {
            return true;
        }
        return false;
    }

    @Override
    public Set getResourceDomains() {
        File folder = new File(Loader.instance().getConfigDir() + "/SimplyHammers/resource");
        if (!folder.exists()) {
            folder.mkdir();
        }
        String[] content = folder.list();

        HashSet<String> folders = new HashSet();
        for (String s : content)
        {
            File f = new File(folder, s);
            if ((f.exists()) && (f.isDirectory())) {
                folders.add(f.getName());
            }
        }
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
        return "SimplyHammersResource";
    }


}
