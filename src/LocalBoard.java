import javax.swing.*;
import java.awt.*;
import java.util.*;

public class LocalBoard extends JPanel implements GameObject {
    private final Game game;

    public LocalBoard(Game game) {
        this.game = game;
        setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            add(new LocalCell(game, this));
        }
        setBorder(BorderFactory.createLineBorder(new Color(76, 70, 70), 2));
    }

    public void updateWinningLineIfExists(WinCheck winCheck, Player player) {
        if (winCheck.isLocalWinner(player, this)) {
            LocalCell cell1 = this.getCellAt(winCheck.getX1(), winCheck.getY1());
            LocalCell cell2 = this.getCellAt(winCheck.getX2(), winCheck.getY2());
            LocalCell cell3 = this.getCellAt(winCheck.getX3(), winCheck.getY3());
            cell1.setBackground(player.getColor());
            cell2.setBackground(player.getColor());
            cell3.setBackground(player.getColor());
        }
    }

    public void setCell(char cellCharacter, int index) {
        LocalCell cell = (LocalCell) getComponent(index);
        if (cellCharacter == game.getFirstPlayer().getCharacter()) {
            cell.setOwner(game.getFirstPlayer());
            cell.setText(String.valueOf(game.getFirstPlayer().getCharacter()));
        } else {
            cell.setOwner(game.getSecondPlayer());
            cell.setText(String.valueOf(game.getSecondPlayer().getCharacter()));
        }
    }

    public boolean isDone() {
        if (game.getWinCheck().isLocalWinner(game.getFirstPlayer(), this)
                || game.getWinCheck().isLocalWinner(game.getSecondPlayer(), this)) {
            return true;
        }
        for (LocalCell cell : getCells()) {
            if (cell.getOwner() == null) {
                return false;
            }
        }
        return true;
    }

    public Point findCellCoordinates(LocalCell cell) {
        int index = 0;
        for (LocalCell currentCell : getCells()) {
            if (cell.equals(currentCell)) {
                return new Point(index % 3, index / 3);
            }
            index++;
        }
        throw new IllegalStateException();
    }

    public void setEnabled(boolean isEnabled) {
        for (LocalCell cell : getCells()) {
            cell.setEnabled(isEnabled);
        }
    }

    public java.util.List<LocalCell> getCells() {
        java.util.List<LocalCell> cells = new ArrayList<>();
        for (Component component : getComponents()) {
            cells.add((LocalCell) component);
        }
        return cells;
    }

    public LocalCell getCellAt(int x, int y) {
        int index = x + y * 3;
        return getCells().get(index);
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void update() {

    }
}
