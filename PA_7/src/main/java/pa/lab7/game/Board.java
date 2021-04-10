package pa.lab7.game;

import javafx.util.Pair;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    static Logger logger = Logger.getLogger(String.valueOf(Board.class));

    private List<Token> tokens;
    private final int count;

    private String packet;

    // True if receiver should wait
    // False if sender should wait
    private boolean transfer = false;

    public Board(int count) {
        this.tokens = generateTokens(count);
        this.count = count;
    }

    private static List<Token> generateTokens(int count) {
        List<Token> tokens = new Vector<>();
        for(int i = 0; i < count; i++) {
            tokens.add(new Token(i, i + 1));
        }

        return Collections.synchronizedList(tokens);
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

    /**
     * find consecutive
     * @param playerTokens
     * @return -> pair consisting of
     * 1. tokens found
     * 2. list of indexes corresponding to the tokens found
     */
    private Pair<List<Token>, List<Integer>> getComboTokens(List<Token> playerTokens) {
        List<Token> tokensFound = new Vector<>();
        List<Integer> indexes = new Vector<>();

        for(Token token : playerTokens) {
            for(int i = 0; i < this.tokens.size(); i++) {
                if(Board.tokenAreAjacent(token, this.tokens.get(i))) {
                    tokensFound.add(token);
                    indexes.add(i);
                }
            }
        }

        if(tokensFound.size() == 0) {
            Pair<Token, Integer> result = this.getOneSingleToken();
            tokensFound.add(result.getKey());
            indexes.add(result.getValue());
        }

        return new Pair<>(tokensFound, indexes);
    }

    private Pair<Token, Integer> getOneSingleToken() {
        int index = new Random().nextInt(this.tokens.size());
        Token token = this.tokens.get(index);

        return new Pair<>(token, index);
    }

    private List<Token> gatherTokensForPlayer(List<Token> playerTokens) {

        if(tokens.size() == 0) {
            return new Vector<>();
        }

        /* get and filter by elements that had been extracted using the combo method */
        Pair<List<Token>, List<Integer>> comboResult = getComboTokens(playerTokens);
        this.tokens = IntStream.range(0, this.tokens.size())
                .filter(i -> !comboResult.getValue().contains(i))
                .mapToObj(this.tokens::get)
                .collect(Collectors.toList());


        return comboResult.getKey();
    }

    public synchronized List<Token> giveTokens(List<Token> playerTokens, String playerName) {


        List<Token> newTokens = this.gatherTokensForPlayer(playerTokens);

        System.out.printf("\n\najung aicisa %s -- %s,---- %d", Boolean.toString(this.transfer), playerName, this.tokens.size());

        return newTokens;
    }

    private static boolean tokenAreAjacent(Token token1, Token token2) {
        return token1.getEnd() == token2.getStart() ||
               token1.getStart() == token2.getEnd();
    }
}