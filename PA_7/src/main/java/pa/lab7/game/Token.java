package pa.lab7.game;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@EqualsAndHashCode
public class Token {
    @Getter
    private final int start;
    @Getter
    private final int end;


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
