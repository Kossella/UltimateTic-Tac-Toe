import javax.swing.*;
import java.awt.event.*;

public class MainMenu extends JMenuBar implements GameObject, ActionListener {
    private final Game game;
    private final JMenu actionMenu = new JMenu("Akcje");
    private final JMenuItem restartGameItem = new JMenuItem("Nowa gra");
    private final JMenuItem saveGameItem = new JMenuItem("Zapisz grę");
    private final JMenuItem loadGameItem = new JMenuItem("Wczytaj grę");

    public MainMenu(Game game) {
        this.game = game;
        actionMenu.add(restartGameItem);
        actionMenu.add(saveGameItem);
        actionMenu.add(loadGameItem);
        add(actionMenu);
        restartGameItem.addActionListener(this);
        saveGameItem.addActionListener(this);
        loadGameItem.addActionListener(this);
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
        if (e.getSource() == restartGameItem) {
            game.onRestartMenuItemClick();
        } else if (e.getSource() == saveGameItem) {
            game.onSaveMenuItemClick();
        } else if (e.getSource() == loadGameItem) {
            game.onLoadMenuItemClick();
        }
    }
}
