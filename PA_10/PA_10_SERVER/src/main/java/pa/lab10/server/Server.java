package pa.lab10.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 8100;
    private ThreadProvider threadProvider;

    public Server() throws IOException {
        threadProvider = new ThreadProvider();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                new Thread(new ClientThread(serverSocket.accept(), threadProvider)).start();
            }

        } catch (IOException e) {
            System.err. println ("Ooops... " + e);
        } finally {
            serverSocket.close();
        }
    }
}