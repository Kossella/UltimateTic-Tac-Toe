import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel implements GameObject {
    private final JLabel textField = new JLabel();
    private final Game game;

    public TitlePanel(Game game) {
        this.game = game;
        textField.setBackground(new Color(50, 50, 50));
        textField.setForeground(new Color(161, 67, 171));
        textField.setFont(new Font("Ink Free", Font.BOLD, 70));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("Tic Tac Toe");
        textField.setOpaque(true);
        setLayout(new BorderLayout());
        setBounds(0, 0, 800, 100);
        add(textField);
    }

    public void setText(String text) {
        textField.setText(text);
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void update() {

    }
}
