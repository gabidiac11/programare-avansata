package pa.lab7.game_extented;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.awt.*;
import java.util.Random;

@EqualsAndHashCode
public class Token {
    private static final int WIDTH = 30;

    @Getter
    private final int start;
    @Getter
    private final int end;
    private int shapeX;
    private int shapeY;

    private Color color;

    private Player player = null;

    public Token(int start, int end) {
        this.start = start;
        this.end = end;

        shapeX = start * WIDTH;
        shapeY = start / 10 + 1;

        this.color = generateRandomColor();
    }

    private static Color generateRandomColor() {
        Color[] palletOfColors = new Color[]{
                Color.black,
                Color.blue,
                Color.red,
                Color.gray,
                Color.yellow,
                Color.cyan
        };

        return palletOfColors[new Random().nextInt(palletOfColors.length)];
    }

    public boolean locationIncludedInShape(int x, int y) {
        int x1 = shapeX;
        int y1 = shapeY;
        int x2 = shapeX + WIDTH;
        int y2 = shapeY + WIDTH;

        if (x > x1 && x < x2 && y > y1 && y < y2) {
            return true;
        }

        return false;
    }

    public void drawShape(Canvas canvas) {
        this.draw(canvas, this.color);
    }

    public void draw(Canvas canvas, Color color) {
        Graphics graphics = canvas.getGraphics();
        graphics.setColor(color);

        try {
            graphics.fillRect(shapeX, shapeY, WIDTH, WIDTH);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void assignPlayer(Player player) {
        this.player = player;
        this.color = Color.white;
    }

    @Override
    public String toString() {
        return String.format("(%d - %d)", this.start, this.end);
    }
}

