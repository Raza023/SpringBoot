
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import reader.QrReader;

public class App {

    public static void main(String[] args) throws Exception {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a QR code image");
            int userSelection = fileChooser.showOpenDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                String decodedText = QrReader.decodeQRCode(filePath);
                // System.out.println("Decoded text: " + decodedText);
                JOptionPane.showMessageDialog(
                        null,
                        "Decoded text: " + decodedText,
                        "QR Code Result",
                        JOptionPane.INFORMATION_MESSAGE,
                        new ImageIcon(filePath)
                );
            } else {
                System.out.println("No file selected.");
            }

            // String decodedText = QrReader.decodeQRCode("qrfile.png");
            // System.out.println("Decoded text: " + decodedText);
        } catch (Exception e) {
            System.out.println("Error fetching QR code text: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
