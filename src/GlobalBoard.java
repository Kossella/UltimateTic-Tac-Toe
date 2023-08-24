import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class GlobalBoard extends JPanel implements GameObject {
    private final Game game;

    public GlobalBoard(Game game) {
        this.game = game;
        setLayout(new GridLayout(3, 3));
        setBackground(new Color(76, 70, 70));
        for (int i = 0; i < 9; i++) {
            add(new LocalBoard(game));
        }
    }

    public void saveToFile(File file) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (LocalBoard board : getLocalBoards()) {
            int counter = 1;
            for (LocalCell cell : board.getCells()) {
                if (cell.getOwner() == null) {
                    writer.write("? ");
                } else {
                    writer.write(cell.getText() + " ");
                }
                if(counter % 3 == 0)
                    writer.newLine();
                counter++;
            }
        }
        writer.close();
    }

    public static GlobalBoard loadFromFile(Game game, File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        GlobalBoard board = new GlobalBoard(game);

        WinCheck winCheck = new WinCheck(game);

        int c, globalIndex = 0, localIndex = 0;

        while ((c = reader.read()) != -1 && globalIndex != 9) {
            LocalBoard localBoard = (LocalBoard) board.getComponent(globalIndex);
            boolean shouldIncrement = false;
            if (c == 'O' || c == 'X') {
                localBoard.setCell((char)c, localIndex);
                shouldIncrement = true;
            } else if (c == '?') {
                shouldIncrement = true;
            }

            if (shouldIncrement) {

                localIndex++;

                if (localIndex == 9) {

                    localBoard.updateWinningLineIfExists(winCheck, game.getFirstPlayer());
                    localBoard.updateWinningLineIfExists(winCheck, game.getSecondPlayer());

                    globalIndex++;
                    localIndex = 0;
                }
            }
        }
        return board;
    }

    public boolean isDone() {
        if (game.getWinCheck().isGlobalWinner(game.getFirstPlayer())
        || game.getWinCheck().isGlobalWinner(game.getSecondPlayer())) {
            return true;
        }
        for (LocalBoard board : getLocalBoards()) {
            if (!board.isDone()) {
                return false;
            }
        }
        return true;
    }

    public void setEnabled(boolean isEnabled) {
        for (LocalBoard board : getLocalBoards()) {
            board.setEnabled(isEnabled);
        }
    }

    public java.util.List<LocalBoard> getLocalBoards() {
        java.util.List<LocalBoard> boards = new ArrayList<>();
        for (Component component : getComponents()) {
            boards.add((LocalBoard) component);
        }
        return boards;
    }

    public LocalBoard tryActivateLocalBoardAt(int x, int y) {
        setEnabled(false);
        LocalBoard localBoard = getLocalBoardAt(x, y);
        if (!localBoard.isDone()) {
            localBoard.setEnabled(true);
        } else {
            for (LocalBoard board : getLocalBoards()) {
                if (!board.isDone()) {
                    localBoard = board;
                    localBoard.setEnabled(true);
                    break;
                }
            }
        }
        return localBoard;
    }

    public LocalBoard getLocalBoardAt(int x, int y) {
        int index = x + y * 3;
        return getLocalBoards().get(index);
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void update() {

    }
}
