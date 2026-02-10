
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class App {

    public static void main(String[] args) throws Exception {
        File file = new File("qrfile.png");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            String content = "This is some text to be added in qr code.";
            ByteArrayOutputStream out = QRCode.from(content).to(ImageType.PNG).withSize(250, 250).stream();
            fos.write(out.toByteArray());
            System.out.println("QR code generated successfully.");
        } catch (Exception e) {
            System.out.println("Error generating QR code: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
