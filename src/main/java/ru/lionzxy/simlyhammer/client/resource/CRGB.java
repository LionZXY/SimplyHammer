package ru.lionzxy.simlyhammer.client.resource;

import org.apache.commons.lang3.SerializationUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by LionZXY on 22.12.2015.
 */
public class CRGB {

    Color[][] pic;

    public CRGB(BufferedImage buf, Color MAIN_COLOUR) {
        pic = new Color[buf.getWidth()][buf.getHeight()];
        for (int y = 0; y < buf.getHeight(); y++) {
            for (int x = 0; x < buf.getWidth(); x++) {
                Color tmp = new Color(buf.getRGB(x, y));
                if (!tmp.equals(Color.white))
                    if (tmp.equals(Color.black))
                        pic[x][y] = Color.black;
                    else {
                        pic[x][y] = new Color(tmp.getRed() - MAIN_COLOUR.getRed(),
                                tmp.getGreen() - MAIN_COLOUR.getGreen(),
                                tmp.getBlue() - MAIN_COLOUR.getBlue());
                    }
                System.out.print(pic[x][y] + " ");
            }
            System.out.println();
        }
    }

    public BufferedImage addHead(BufferedImage image, int startX, int startY, int rgb) {
        int red = new Color(rgb).getRed();
        int green = new Color(rgb).getGreen();
        int blue = new Color(rgb).getBlue();

        for (int x = 0; x < pic.length; x++)
            for (int y = 0; y < pic[x].length; y++)
                if (pic[x][y] != null)
                    if (pic[x][y].equals(Color.black))
                        image.setRGB(startX + x, startY + y, Color.BLACK.getRGB());
                    else image.setRGB(startX + x, startY + y,
                            new Color((pic[x][y].getRed() + red) / 2, (pic[x][y].getGreen() + green) / 2, (pic[x][y].getBlue() + blue) / 2).getRGB());
        return image;
    }


}
