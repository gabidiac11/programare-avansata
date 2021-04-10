package pa.lab7.game;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
public class Token {
    @Getter
    private final int start;
    @Getter
    private final int end;

    @Override
    public String toString() {
        return "Token{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
