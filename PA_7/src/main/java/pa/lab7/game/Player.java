package pa.lab7.game;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
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
    static Logger logger = Logger.getLogger(String.valueOf(Player.class));

    private List<Token> tokens = Collections.synchronizedList(new Vector<>());

    private final Board board;
    private final String name;

    public Player(Board board, String name) {
        this.board = board;
        this.name = name;
    }

    /**
     * prints what new tokens a player has won
     * @param tokens - tokens won
     *
     * Example:
     * NEW TOKENS for assunta.thompson:
     * (28 - 29)
     * -------------------
     */
    private void printPlayerGotTokens(List<Token> tokens) {
        String message = String.format("\n\nNEW TOKENS for %s:", this.name);
        message += "\n" + Token.tokenListToString(tokens);
        message += "\n-------------------\n";

        logger.log(Level.INFO, message);
    }

    /**
     * try to obtain tokens while there are still left in the board
     */
    public void run() {
        logger.setLevel(Level.INFO);

        while(!board.noMoreTokensLeft()) {
           List<Token> newTokens = board.giveTokens(this.tokens);

           this.tokens = Stream.concat(tokens.stream(), newTokens.stream())
                   .collect(Collectors.toList());

            if(newTokens.size() > 0) {
                this.printPlayerGotTokens(newTokens);
            }

           /* prevent the first player run to have to upper hand */
           try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException e)  {
               Thread.currentThread().interrupt();
               logger.log(Level.SEVERE, "Thread interrupted", e);
           }
        }

        this.printScore();
    }

    /**
     * prints the biggest consecutive sequence of tokens
     *
     * Example:
     * francesco.lowe has won 15 points:
     * (0 - 1),(1 - 2),(2 - 3),(3 - 4),(4 - 5),(5 - 6),(6 - 7),(7 - 8),(8 - 9),(9 - 10),(10 - 11),(11 - 12),(12 - 13),(13 - 14),(14 - 15)
     */
    public void printScore() {
        List<List<Token>> tokenBySequence = this.getSequences();
        String message;

        if(tokenBySequence.size() > 0) {
            /* this score is the longest sequence **/
            message = String.format("\n%s has won %d points:\n", this.name, tokenBySequence.get(0).size());
            message += Token.tokenListToString(tokenBySequence.get(0));
            message += "\n\n";
        } else {
          message = String.format("\n%s has won 0 points\n", this.name);
        }

        logger.log(Level.INFO, message);
    }

    /**
     * divides the list of tokens based on consecutive-ity
     *
     * @return - a sorted group of tokens, each sorted by Token.start (the first list contains the longest sequence)
     */
    private List<List<Token>> getSequences() {
        this.tokens = this.tokens.stream().sorted(Token::compare)
                .collect(Collectors.toList());

        Set<Integer> indexes  = new HashSet<>();
        List<List<Token>> list = new Vector<>();

        for(int i = 0; i < this.tokens.size(); i++) {
            if(indexes.contains(i)) {
                continue;
            }

            List<Token> listItem = new Vector<>();
            listItem.add(tokens.get(i));
            indexes.add(i);

            for(int ii = i+1; ii < this.tokens.size(); ii++) {
                Token item = listItem.get(listItem.size()-1);

                if(this.tokens.get(ii).getStart() - item.getStart() > 1) {
                    break;
                }

                listItem.add(this.tokens.get(ii));
                indexes.add(ii);
            }

            list.add(listItem);
        }

        list =  list.stream().sorted((l1, l2) -> Integer.compare(l2.size(), l2.size()))
                .collect(Collectors.toList());

        return list;
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
}