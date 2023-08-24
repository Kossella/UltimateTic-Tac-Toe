import java.awt.*;

public class HumanPlayer extends Player {
    private final Game game;
    private final char character;
    private final Color color;

    public HumanPlayer(Game game, char character, Color color) {
        this.game = game;
        this.character = character;
        this.color = color;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void update() {
    }

    @Override
    public char getCharacter() {
        return character;
    }

    @Override
    public Color getColor() {
        return color;
    }
}
