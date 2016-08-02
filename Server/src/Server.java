import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

/**
 * Created by MinhThao on 26/07/2016.
 */
public class Server extends Thread {
    private ServerSocket serverSocket;
    private List<Worker> workerList = new Vector<>();
    private static Server instance = null;

    public static Server getInstance () {
        if (instance == null)
            instance = new Server();
        return instance;
    }

    private Server() {
        try {
            serverSocket = new ServerSocket(3393);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean setDeviceState (int id, int newState) {
        for (Worker worker : workerList) {
            System.out.println("id worker: " + worker.id);
            if(worker.id == id) {
                worker.changeDeviceState(newState);
                System.out.println("id = " + id);
                return true;
            }
        }
        return false;
    }

    public boolean setDeviceName (int id, String newName) {
        for (Worker worker : workerList) {
            if (worker.id == id) {

            }
        }
    }

    @Override
    public void run() {
        try {
            waitForConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnection() throws IOException {
        Socket socket;
        while (true) {
            try {
                socket = serverSocket.accept();
                Worker worker = new Worker(socket);
                worker.start();
                System.out.println("device connected");
                workerList.add(worker);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class Worker extends Thread {
        private InputStream in;
        private OutputStream out;
        private Socket socket;
        private int id;
        private int state;
        private String nameDevice;

        private Worker(Socket socket) {
            this.socket = socket;
            try {
                in = socket.getInputStream();
                out = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
                workerList.remove(this);
            }

        }

        @Override
        public void run() {
            try {
                requestIdentification();
                while (true) {
                    state = in.read();
                }
            } catch (IOException e) {
                e.printStackTrace();
                workerList.remove(this);
            }
        }

        private void requestIdentification() throws IOException {
            out.write(0);
            byte[] buffer = new byte[20];
            int length = in.read(buffer);
            id = Integer.parseInt(new String(buffer, 0, length));
        }

        private void changeDeviceState(int state) {
            try {
                byte[] arrayRequest = {1, (byte) state};
                out.write(arrayRequest);
            } catch (Exception e) {
                workerList.remove(this);
            }
        }

    }
}
