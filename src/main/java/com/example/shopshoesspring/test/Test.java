package com.example.shopshoesspring.test;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
@Component
public class Test {
    public String check(String image1P, String image2P) {
        String imagePath1 =image1P ;
        String imagePath2 =image2P ;


        BufferedImage image1 = loadImage(imagePath1);
        BufferedImage image2 = loadImage(imagePath2);

        if (image1 != null && image2 != null) {
            int width = 100;
            int height = 100;
            BufferedImage resizedImage1 = Scalr.resize(image1, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, width, height);
            BufferedImage resizedImage2 = Scalr.resize(image2, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, width, height);

            double matchPercentage = calculateMatchPercentage(resizedImage1, resizedImage2);
            return String.valueOf(matchPercentage);
        } else {
           return null;
        }
    }


    private static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static double calculateMatchPercentage(BufferedImage image1, BufferedImage image2) {
        int width = image1.getWidth();
        int height = image1.getHeight();


        int matchCount = 0;


        try{
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (image1.getRGB(x, y) == image2.getRGB(x, y)) {
                        matchCount++;
                    }
                }
            }
        }catch (Throwable e){

        }
        return ((double) matchCount / (width * height)) * 100;
    }
}
