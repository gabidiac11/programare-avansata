package pa.lab7.game_extented;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

public class Timer implements Runnable {
    private static final int MAX_TIME = 1000 * 120;
    private static final int INCREASE = 1000;

    private int currentTime = 0;
    private final JLabel jLabel;

    public Timer(JLabel jLabel) {
        this.jLabel = jLabel;
    }

    @Override
    public void run() {
        while(this.currentTime < MAX_TIME) {
            jLabel.setText(String.format("%d seconds left", this.currentTime / 1000));
            currentTime += INCREASE;

            try {
                Thread.sleep(INCREASE);
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt();
            }
        }
    }
}
