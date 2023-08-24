import javax.swing.*;
import java.awt.*;

public class WinnerAnimation extends JLabel implements GameObject {
    private final Game game;

    public WinnerAnimation(Game game) throws Exception {
        this.game = game;
        ImageIcon imageIcon = new ImageIcon("winner.gif");
        setIcon(imageIcon);
    }

    @Override
    public Game getGame() {
        return null;
    }

    @Override
    public void update() {

    }
}
