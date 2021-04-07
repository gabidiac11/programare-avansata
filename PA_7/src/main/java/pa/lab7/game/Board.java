package pa.lab7.game;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Board {
    static Logger logger = Logger.getLogger(String.valueOf(Board.class));

    private List<String> tokens;

    private String packet;

    // True if receiver should wait
    // False if sender should wait
    private boolean transfer = true;

    public Board(List<String> tokens) {
        this.tokens = Collections.synchronizedList(tokens);

    }

    public synchronized void send(String packet) {
        while (!transfer) {
            try {
                wait();
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt();
                logger.log(Level.SEVERE, "Thread interrupted", e);
            }
        }
        transfer = false;

        this.packet = packet;
        notifyAll();
    }

    public boolean noMoreTokensLeft() {
        return this.tokens.size() == 0;
    }

    public synchronized String receive() {
        while (transfer) {
            try {
                wait();
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt();
                logger.log(Level.SEVERE, "Thread interrupted", e);
            }
        }
        transfer = true;

        notifyAll();
        return packet;
    }
}