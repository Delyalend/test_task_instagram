package com.instagram.service;

import com.instagram.model.Image;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.math3.util.Precision;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.codec.binary.Base64.decodeBase64;

@Service
public class ServiceImageImpl implements ServiceImage {

    public List<Image> getReducedImages(List<Image> images, int maxWidth, int maxHeight) {
        List<Image> resizedImages = new ArrayList<>();
        images.forEach(img -> {
            resizedImages.add(resize(img, maxWidth, maxHeight));
        });
        return resizedImages;
    }

    private Image resize(Image img, int maxWidth, int maxHeight) {
        BufferedImage buffImg = getBufferedImageByBase64(img.getSrc());
        BufferedImage buffResizedImg = null;
        int finalHeight = 0;
        int finalWidth = 0;

        int curImgHeight = buffImg.getHeight();
        int curImgWidth = buffImg.getWidth();

        if (curImgHeight > maxHeight || curImgWidth > maxWidth) {
            if (curImgWidth > curImgHeight) {
                finalWidth = maxWidth;
                finalHeight = getHeightByWidth(buffImg, finalWidth);
            } else if (curImgHeight > curImgWidth) {
                finalHeight = maxHeight;
                finalWidth = getWidthByHeight(buffImg, finalHeight);
            } else if(curImgHeight == curImgWidth){
                if (maxHeight > maxWidth) {
                    finalHeight = maxWidth;
                    finalWidth = maxWidth;
                }
                else {
                    finalHeight = maxHeight;
                    finalWidth = maxHeight;
                }
            }
            buffResizedImg = getResizedBufferedImage(buffImg, finalWidth, finalHeight);
            img.setSrc(getBase64ForBufferedImage(buffResizedImg, img.getType()));
        } else {
            img.setSrc(getBase64ForBufferedImage(buffImg, img.getType()));
        }
        return img;
    }

    private BufferedImage getResizedBufferedImage(BufferedImage buffImg, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.drawImage(buffImg, 0, 0, width, height, null);
        graphics2D.dispose();
        return Scalr.resize(resizedImage, Scalr.Mode.FIT_EXACT, width, height);
    }

    private int getHeightByWidth(BufferedImage buffImg, int width) {
        float k = 0;
        if (buffImg.getWidth() > width) {
            k = Precision.round((float) buffImg.getHeight() / (float) buffImg.getWidth(), 5);
            if (buffImg.getHeight() > buffImg.getWidth()) {
                return Math.round(width / k);
            } else if (buffImg.getWidth() > buffImg.getHeight()) {

                return Math.round(width * k);
            } else if (buffImg.getHeight() == buffImg.getWidth()) {
                return width;
            }
        }
        return buffImg.getHeight();
    }

    private int getWidthByHeight(BufferedImage buffImg, int height) {
        float k = 0;
        if (buffImg.getWidth() > height) {
            k = Precision.round((float) buffImg.getWidth() / (float) buffImg.getHeight(), 5);
            if (buffImg.getHeight() > buffImg.getWidth()) {
                return Math.round(height / k);
            } else if (buffImg.getWidth() > buffImg.getHeight()) {

                return Math.round(height * k);
            } else if (buffImg.getHeight() == buffImg.getWidth()) {
                return height;
            }
        }
        return buffImg.getHeight();
    }


    private BufferedImage getBufferedImageByBase64(String imgSrcInBase64) {
        byte[] imgSrcInByte = decodeBase64(imgSrcInBase64);
        BufferedImage buffImg = null;
        try {
            buffImg = ImageIO.read(new ByteArrayInputStream(imgSrcInByte));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffImg;
    }

    private String getBase64ForBufferedImage(BufferedImage bufferedImage, String type) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ImageIO.write(ensureOpaque(bufferedImage), type, bos);
            Base64 b64 = new Base64();
            return b64.encodeAsString(bos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private BufferedImage ensureOpaque(BufferedImage bi) {
        if (bi.getTransparency() == BufferedImage.OPAQUE)
            return bi;
        int width = bi.getWidth();
        int height = bi.getHeight();
        int[] pixels = new int[width * height];
        bi.getRGB(0, 0, width, height, pixels, 0, width);
        BufferedImage bi2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bi2.setRGB(0, 0, width, height, pixels, 0, width);
        return bi2;
    }

}
