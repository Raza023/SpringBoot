package reader;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class QrReader {

    public static String decodeQRCode(String filePath) throws Exception {
        BufferedImage bufferedImage = ImageIO.read(new File(filePath));
        BinaryBitmap binaryBitmap = new BinaryBitmap(
                new HybridBinarizer(
                        new BufferedImageLuminanceSource(bufferedImage)
                )
        );

        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }

}
