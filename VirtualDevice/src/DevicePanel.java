import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by MinhThao on 23/07/2016.
 */
public class DevicePanel extends JPanel{
    private int numberOfDevices;
    private ArrayList <Device> devices = new ArrayList<>();

    public DevicePanel (int numberOfDevices) {
        this.numberOfDevices = numberOfDevices;
        setLayout(new FlowLayout());
    }

    public void setup (String ip, int port) {
        for(int i = 0; i < numberOfDevices; i++) {
            Device device = new Device(i);
            device.registerDevice(this);
            device.startDevice(ip, port);
            devices.add(device);
        }
    }
}
