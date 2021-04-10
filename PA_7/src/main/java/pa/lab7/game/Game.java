package pa.lab7.game;

import java.util.List;

public class Game {
    public static void playGameGenericSetup() {
        Board board = new Board(30);

        List<Thread> playerList = Player.generatePlayers(board, 2);

        for (Thread thread : playerList) {
            thread.start();
        }
    }
}
