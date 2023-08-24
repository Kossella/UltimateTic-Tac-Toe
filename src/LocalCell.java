import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LocalCell extends JButton implements GameObject, ActionListener {
    private final Game game;
    private Player owner;
    private final LocalBoard localBoard;

    public LocalCell(Game game, LocalBoard localBoard) {
        this.game = game;
        this.localBoard = localBoard;
        setFont(new Font("Consolas", Font.BOLD, 35));
        setFocusable(false);
        addActionListener(this);
    }

    public LocalBoard getLocalBoard() {
        return localBoard;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void update() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.onLocalCellClick(this);
    }
}
