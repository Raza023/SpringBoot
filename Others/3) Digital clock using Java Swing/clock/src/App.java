import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.*;

public class App extends JFrame {
    private JLabel timeLabel;

    public App() {
        // Set up the frame
        setTitle("Digital Clock");
        setSize(1050, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create the time label
        timeLabel = new JLabel();
        timeLabel.setFont(timeLabel.getFont().deriveFont(60f));
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setVerticalAlignment(JLabel.CENTER);

        // Add label to frame
        add(timeLabel);

        // Update time every 1000 milliseconds (1 second)
        Timer timer = new Timer(1000, e -> updateTime());
        timer.start();

        // Display initial time
        updateTime();

        // Make frame visible
        setVisible(true);
    }

    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy, hh:mm:ss a z");
        // Force PST
        sdf.setTimeZone(TimeZone.getTimeZone("PST"));
        String currentTime = sdf.format(Calendar.getInstance().getTime());
        timeLabel.setText(currentTime);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}
