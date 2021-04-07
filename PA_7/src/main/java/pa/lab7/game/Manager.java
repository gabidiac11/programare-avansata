package pa.lab7.game;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
public class Manager implements Runnable {
    static Logger logger = Logger.getLogger(String.valueOf(Manager.class));

    private Board board;

    public void run() {
        for(String receivedMessage = board.receive();
            !board.noMoreTokensLeft();
            receivedMessage = board.receive()) {

            System.out.println(receivedMessage);

            // ...
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.SEVERE, "Thread interrupted", e);
            }
        }
    }
}