package pa.lab7.game;

import java.util.List;

/**
 * offer way to instantiate a game (creates board and players and starts the game)
 */
public class Game {
    public static void playGameGenericSetup() {
        Board board = new Board(30);

        List<Thread> playerList = Player.generatePlayers(board, 2);

        for (Thread thread : playerList) {
            thread.start();
        }
    }
    public static void playGameSetup() {
        Board board = new Board(30);

        List<Thread> playerList = Player.generatePlayers(board, 2);

        for (Thread thread : playerList) {
            thread.start();
        }
    }

}
