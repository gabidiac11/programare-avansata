package pa.lab7.game;

import javafx.util.Pair;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * generates token of form - t1=(i1,i2), t2=(i2,i3),...,tk=(ik,i1)
 * gives a Player instance thread from its tokens
 */
public class Board {
    static Logger logger = Logger.getLogger(String.valueOf(Board.class));

    private List<Token> tokens;

    public Board(int count) {
        this.tokens = generateTokens(count);
    }

    /**
     * generate consecutive groups of tokens
     * @param count - number desired of tokens
     * @return -
     */
    private static List<Token> generateTokens(int count) {
        List<Token> tokens = new Vector<>();
        for(int i = 0; i < count; i++) {
            tokens.add(new Token(i, i + 1));
        }
        return Collections.synchronizedList(tokens);
    }

    /**
     * indicates to the outside classed that board has wasted all tokens
     * @return
     */
    public boolean noMoreTokensLeft() {
        return this.tokens.size() == 0;
    }

    /**
     * match a sub-list of tokens from the board using the list of tokens the player already has
     *
     * this method tries to find the next or previous token of each token that a player has
     * or else return a random token
     *
     * @param playerTokens - list of tokens the player already has
     * @return -> pair consisting of
     * 1. tokens found
     * 2. list of indexes corresponding to the tokens found - used for ulterior deletion of the extracted items
     */
    private Pair<List<Token>, List<Integer>> getComboTokens(List<Token> playerTokens) {
        List<Token> tokensFound = new Vector<>();
        List<Integer> indexes = new Vector<>();

        for(Token token : playerTokens) {
            for(int i = 0; i < this.tokens.size(); i++) {
                if(Board.tokenAreAdjacent(token, this.tokens.get(i))) {
                    tokensFound.add(this.tokens.get(i));
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

    /**
     * returns a random token and the index corresponding to its place in the list
     * @return
     */
    private Pair<Token, Integer> getOneSingleToken() {
        int index = new Random().nextInt(this.tokens.size());
        Token token = this.tokens.get(index);

        return new Pair<>(token, index);
    }

    /**
     * gets tokens from board, removes them from it, but returns what has been removed to the player
     * @param playerTokens - list of tokens the player already has - used for processing what tokens to give
     * @return - tokens extracted
     */
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

    /**
     * gives tokens to player
     * prints when game have finished -> no tokens left
     * @param playerTokens - list of tokens the player already has - used for processing what tokens to give
     * @return - tokens extracted
     */
    public synchronized List<Token> giveTokens(List<Token> playerTokens) {
        List<Token> tokens = this.gatherTokensForPlayer(playerTokens);

        if(noMoreTokensLeft()) {
            this.onNoMoreLeft();
        }

        return tokens;
    }

    /**
     * prints when game have finished -> no tokens left
     */
    private void onNoMoreLeft() {
        String message = "\n=========--------================GAME-FINISHED================--------=========\n";

        logger.log(Level.INFO, message);
    }

    /**
     * @param token1
     * @param token2
     * @return - true if tokens are consecutive, false otherwise
     */
    private static boolean tokenAreAdjacent(Token token1, Token token2) {
        return token1.getEnd() == token2.getStart() ||
               token1.getStart() == token2.getEnd();
    }
}