package pa.lab7.game;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class Token {
    @Getter
    private final int start;
    @Getter
    private final int end;

    public Token(int start, int end) {
        this.start = start;
        this.end = end;

    }

    public static int compare(Token o1, Token o2) {
        return Integer.compare(o1.start, o2.start);
    }

    public static String tokenListToString(List<Token> tokens) {
        return tokens.stream()
                .map(Token::toString)
                .collect(Collectors.joining(","));
    }

    @Override
    public String toString() {
        return String.format("(%d - %d)", this.start, this.end);
    }
}
