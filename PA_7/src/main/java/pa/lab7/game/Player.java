package pa.lab7.game;

import com.github.javafaker.Faker;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Player implements Runnable {
    static Logger logger = Logger.getLogger(String.valueOf(Player.class));

    private List<Token> tokens;

    private final Board board;
    private final String name;

    public Player(Board board, String name) {
        this.tokens = Collections.synchronizedList(new Vector<>());
        this.board = board;
        this.name = name;
    }

    private void printTokenReceived(List<Token> tokens) {
        String message = String.format("\nNEW TOKENS FOR '%s':", this.name);
        for(Token token : tokens) {
            message += "\n" + token.toString();
        }
        logger.log(Level.INFO, String.format("%s\n ------------------", message));
    }

    public void run() {
        logger.setLevel(Level.INFO);

        while(!board.noMoreTokensLeft()) {
           List<Token> newTokens = board.giveTokens(this.tokens, this.name);

           this.tokens = Stream.concat(tokens.stream(), newTokens.stream())
                   .collect(Collectors.toList());
        }

        this.printScore();
    }

    public void printScore() {
        String message = String.format("\n%s has won %d points\n", this.name, this.tokens.size());

        System.out.printf("%s", message);
    }

    public static List<Thread> generatePlayers(Board board, int count) {
        Faker faker = new Faker();

        List<Thread> players = new Vector<>();

        for(int i = 0; i < count; i++) {
            players.add(new Thread(new Player(board, faker.name().username())));
        }

        return players;
    }
}