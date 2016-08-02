import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by MinhThao on 19/07/2016.
 */
public class Device implements ActionListener {
    public static  final int STATE_ON = 1;
    public static  final int STATE_OFF = 0;
    private JButton button;
    private Socket socket;
    private int id;
    private volatile int state = 0;

    public Device (int id) {
        button = new JButton("" + id);
        button.setSize(100, 50);
        button.addActionListener(this);
        this.id = id;
        setButtonState(isOn());
    }

    public void registerDevice (JComponent container) {
        container.add(button);
    }

    public void startDevice (final String ip, final int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(InetAddress.getByName(ip), port);
                    System.out.println("device connected");
                    whileForRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void whileForRequest() throws IOException {
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        while (true) {
            int command = in.read();
            switch (command) {
                case 0:
                    responseId(out);
                    System.out.println("id is sent");
                    break;
                case 1:
                    processStateRequest(in);
                    responseState(out);
                    System.out.println("state is sent");
                    break;
            }
        }
    }

    private void processStateRequest(InputStream in) throws IOException {
        int stateDevice = in.read();
        this.state = stateDevice;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setButtonState(isOn());
            }
        });
    }

    private void responseState(OutputStream out) throws IOException {
        out.write(("" + state).getBytes());
    }

    private void responseId(OutputStream out) throws IOException {
        out.write(("" + id).getBytes());
    }

    private void setButtonState (boolean isOn) {
        button.setBackground(isOn ? Color.GREEN : Color.RED);
    }

    private boolean isOn () {
        return state == STATE_ON;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        state = isOn() ? STATE_OFF : STATE_ON;
        setButtonState(isOn());
    }
}
