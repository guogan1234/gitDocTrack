/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.service.createBarCode;

/**
 * Created by Amber Wang on 2017/11/27 17:24.
 */

import com.avp.mem.njpb.util.MagicNumber;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

public final class MatrixToImageWriter {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private MatrixToImageWriter() {
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (y + MagicNumber.FIVE_ZERO >= height-MagicNumber.FIVE_ZERO) {
                    image.setRGB(x, y, WHITE);
                }else{
                    image.setRGB(x, y, matrix.get(x, y + MagicNumber.FIVE_ZERO) ? BLACK : WHITE);
                }



            }
        }
        return image;
    }


    public static BufferedImage writeToFile(BitMatrix matrix, String format)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        return image;
    }


    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

}
