import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by MinhThao on 23/07/2016.
 */
public class MainWindow extends JFrame {
    DevicePanel devicePanel;

    public MainWindow () {
        devicePanel = new DevicePanel(10);
        setLayout(new BorderLayout());
        getContentPane().add(devicePanel, BorderLayout.CENTER);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void start (String ip, int port) {
        devicePanel.setup(ip, port);
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
        mainWindow.start("localhost", 3393);
        System.out.println("Client: Connected!");
    }
}
