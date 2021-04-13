package pa.lab7.game_extented;

import com.github.javafaker.Faker;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * asks Board instances for tokens
 * receives tokens from board
 * add its list of tokens
 * waits for other players to receive tokens
 */
public class Player implements Runnable {
    static Logger logger = Logger.getLogger(String.valueOf(pa.lab7.game.Player.class));

    private List<Token> tokens = Collections.synchronizedList(new Vector<>());
    private static int instanceCounter = 0;

    private final Board board;
    private final String name;
    private final int id;

    public Player(Board board, String name) {
        this.board = board;
        this.name = name;

        id = instanceCounter;
        instanceCounter++;
    }

    /**
     * try to obtain tokens while there are still left in the board
     */
    public void run() {
        logger.setLevel(Level.INFO);

        while(!board.isMyTurn(this)) {
            this.tokens = Stream.concat(tokens.stream(), board.pickTokens().stream())
                    .collect(Collectors.toList());

            try {
                wait();
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt();
                logger.log(Level.SEVERE, "Thread interrupted", e);
            }

            notifyAll();
        }
    }

    /**
     * generate players with random names
     * @param board - the board from which the player will take tokens
     * @param count - the number of player to be generated
     * @return - list of players
     */
    public static List<Thread> generatePlayers(Board board, int count) {
        Faker faker = new Faker();

        List<Thread> players = new Vector<>();

        for(int i = 0; i < count; i++) {
            players.add(new Thread(new Player(board, faker.name().username())));
        }


        return players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id && Objects.equals(tokens, player.tokens) && Objects.equals(board, player.board) && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}