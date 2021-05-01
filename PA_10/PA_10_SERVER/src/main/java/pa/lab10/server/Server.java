package pa.lab10.server;

import pa.lab10.server.chart.Chart;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Date;

public class Server {
    public static final int PORT = 8100;
    private ThreadProvider threadProvider;

    public Server() throws IOException {
        threadProvider = new ThreadProvider();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(1000);

            while (!threadProvider.getIsExiting()) {
                try {
                    new Thread(new ClientThread(serverSocket.accept(), threadProvider)).start();
                } catch (SocketTimeoutException timeoutException) {
                }
            }

        } catch (IOException e) {
            System.err. println ("Ooops... " + e);
        } finally {
            Chart.createChart(threadProvider.getAllUsers());
            serverSocket.close();
        }

    }
}