package pa.lab10.server;

import pa.lab10.server.chart.Chart;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

/**
 * listens for new connections
 * creates new threads to take care of established connections
 * stops adding new connections when the ThreadProvider (common resource for all threads) signals that the process needs to stop
 * creates the char representing the interactions between users (at last)
 */
public class Server {
    public static final int PORT = 8100;

    public Server() throws IOException {
        ThreadProvider threadProvider = new ThreadProvider();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(1000);

            /*
             * listen to new connection while the server is not in the process of exiting
             * implement a timeout between each listening sessions and catch only that in order actually check if everything has to be stopped
             * the timeout error must be catch in order to achieve that
             */
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