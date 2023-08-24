import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.io.*;

public class Game extends JFrame implements GameObject, Runnable {
    private final TitlePanel titlePanel = new TitlePanel(this);
    private final MainMenu mainMenu = new MainMenu(this);
    private WinnerAnimation winnerAnimation;
    private GlobalBoard globalBoard;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private Player activePlayer;
    private WinCheck winCheck;

    public Game() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        getContentPane().setBackground(new Color(60, 60, 60));
        setLayout(new BorderLayout());
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(mainMenu, BorderLayout.NORTH);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        try {
            winnerAnimation = new WinnerAnimation(this);
            headerPanel.add(winnerAnimation, BorderLayout.EAST);
            winnerAnimation.setVisible(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        add(headerPanel, BorderLayout.NORTH);
        setVisible(true);
        new Thread(this).start();
        firstPlayer = new HumanPlayer(this, 'O', Color.ORANGE);
        secondPlayer = new HumanPlayer(this, 'X', Color.GRAY);
    }

    public void restart(File loadFileName) {
        if (globalBoard != null) {
            remove(globalBoard);
        }
        if (loadFileName == null) {
            globalBoard = new GlobalBoard(this);
        } else {
            try {
                globalBoard = GlobalBoard.loadFromFile(this, loadFileName);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
        add(globalBoard, BorderLayout.CENTER);
        activePlayer = firstPlayer;
        winCheck = new WinCheck(this);
        globalBoard.tryActivateLocalBoardAt(0, 0);
        presentActivePlayer();
        winnerAnimation.setVisible(false);
    }

    public boolean isFinished() {
        return globalBoard.isDone();
    }

    public void toggleActivePlayer() {
        if (activePlayer == firstPlayer) {
            activePlayer = secondPlayer;
        } else {
            activePlayer = firstPlayer;
        }
        presentActivePlayer();
    }

    @Override
    public Game getGame() {
        return this;
    }

    @Override
    public void update() {

    }

    @Override
    public void run() {
        while (true) {
            invalidate();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public TitlePanel getTitlePanel() {
        return titlePanel;
    }

    public GlobalBoard getGlobalBoard() {
        return globalBoard;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public WinCheck getWinCheck() {
        return winCheck;
    }

    private void presentActivePlayer() {
        titlePanel.setText("Gracz: " + activePlayer.getCharacter());
    }

    private void updateGlobalBoardWinner() {
        if (isFinished())
        {
            if (winCheck.isGlobalWinner(activePlayer)) {
                titlePanel.setText("Zwycięzca: " + activePlayer.getCharacter());
                winnerAnimation.setVisible(true);
            }
            else
            {
                titlePanel.setText("Remis");
            }
            globalBoard.setEnabled(false);
        }
    }

    private void updateLocalBoardWinner(LocalBoard localBoard) {
        if (winCheck.isLocalWinner(activePlayer, localBoard)) {
            LocalCell cell1 = localBoard.getCellAt(winCheck.getX1(), winCheck.getY1());
            LocalCell cell2 = localBoard.getCellAt(winCheck.getX2(), winCheck.getY2());
            LocalCell cell3 = localBoard.getCellAt(winCheck.getX3(), winCheck.getY3());
            cell1.setBackground(activePlayer.getColor());
            cell2.setBackground(activePlayer.getColor());
            cell3.setBackground(activePlayer.getColor());
        }
    }

    /* SEKCJA METOD ZDARZENIOWYCH */

    void onLocalCellClick(LocalCell localCell) {
        if (localCell.getOwner() != null) {
            return;
        }
        localCell.setOwner(getActivePlayer());
        localCell.setText(String.valueOf(localCell.getOwner().getCharacter()));
        LocalBoard localBoard = localCell.getLocalBoard();
        Point cellCoordinates = localBoard.findCellCoordinates(localCell);
        globalBoard.tryActivateLocalBoardAt(cellCoordinates.x, cellCoordinates.y);

        updateLocalBoardWinner(localBoard);
        updateGlobalBoardWinner();

        if (!isFinished()) {
            toggleActivePlayer();
        }
    }

    void onRestartMenuItemClick() {
        restart(null);
    }

    void onSaveMenuItemClick() {
        if (globalBoard == null) {
            JOptionPane.showMessageDialog(this, "Nie rozpoczęto gry, którą można zapisać!");
            return;
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Pliki tekstowe", "txt"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                globalBoard.saveToFile(chooser.getSelectedFile());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }

    void onLoadMenuItemClick() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Pliki tekstowe", "txt"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                restart(chooser.getSelectedFile());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }
}
