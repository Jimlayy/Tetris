package se.liu.jimhe280.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.google.gson.Gson;

public class HighscorePanel extends JPanel
{
    private static final int SHOWN_HIGHSCORES = 10;

    public HighscorePanel(HighscoreList highscores) {
	List<Highscore> scores = highscores.getHighscores();
	int count = Math.min(SHOWN_HIGHSCORES, scores.size());
	setLayout(new GridLayout(count + 1, 1));
	add(new JLabel("Highscores", SwingConstants.CENTER));
	for (int i = 0; i < count; i++) {
	    Highscore hs = scores.get(i);
	    add(new JLabel((i + 1) + ". " + hs.getPlayerName() + ": " + hs.getScore(), SwingConstants.CENTER));
	}
    }

    public static void showHighscoreWindow(HighscoreList highscores) {
	JOptionPane.showMessageDialog(null, new HighscorePanel(highscores),
				      "Highscores", JOptionPane.PLAIN_MESSAGE);
    }
}