package se.liu.jimhe280.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class TetrisViewer
{
    // Uppsnabbning (Timer)
    private static final int START_DELAY = 150;
    private static final int MIN_DELAY = 50;
    private static final int SPEEDUP_STEP = 5;
    private static final int TICKS_PER_SPEEDUP = 50;

    private Board board;

    public TetrisViewer(final Board board) {
	this.board = board;
    }

    public void show(){
	// -- TextArea
	//JFrame frame = new JFrame("Tetris Viewer");
	//JTextArea textArea = new JTextArea(board.getWidth(), board.getHeight());
	//textArea.setText(BoardToTextConverter.convertToString(board));
	//frame.setLayout(new BorderLayout());
	//frame.add(textArea, BorderLayout.CENTER);
	//textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
	//frame.pack();
	//frame.setVisible(true);

	// -- Panel
	JFrame frame = new JFrame("Tetris");
	frame.setLayout(new BorderLayout());
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Menu
	final JMenuBar menuBar = new JMenuBar();
	final JMenu file = new JMenu("File");
	final JMenuItem newGame = new JMenuItem("New Game");
	file.add(newGame);
	newGame.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		frame.dispose();

		TetrisViewer viewer = new TetrisViewer(new Board(board.getWidth(), board.getHeight(), board.getHighscoreList()));
		viewer.show();
	    }
	});
	file.addSeparator();
	final JMenuItem exit = new JMenuItem("Avsluta");
	file.add(exit);
	exit.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int choice = JOptionPane.showConfirmDialog(null, "Avsluta?", "Avsluta", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
		    System.exit(0);
		}
	    }
	});
	menuBar.add(file);
	frame.setJMenuBar(menuBar);

	// Tetriscomponent
	TetrisComponent component = new TetrisComponent(board);
	board.addBoardListener(component);
	component.setSize(component.getPreferredSize());
	frame.add(component, BorderLayout.CENTER);

	// Score
	JLabel scoreLabel = new JLabel("Score: " + board.getScore());
	scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 20));

	// State
	JLabel stateLabel = new JLabel(board.getFallHandler().getDescription());
	stateLabel.setFont(new Font("Monospaced", Font.BOLD, 20));

	// Score + State överst
	JPanel infoPanel = new JPanel(new BorderLayout());
	infoPanel.add(scoreLabel, BorderLayout.WEST);
	infoPanel.add(stateLabel, BorderLayout.EAST);
	frame.add(infoPanel, BorderLayout.NORTH);

	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);

	// -- Timer
	final Action doOneStep = new AbstractAction() {
	    private int tickCount = 0; // Används för gradvis uppsnabbning

	    public void actionPerformed(ActionEvent e) {
		// Gå ett steg i spelet!

		//board.randomizeBoard();
		try {
		    board.tick();
		} catch (IOException ex) {
		    handleSaveError(ex);
		}
		scoreLabel.setText("Score: " + board.getScore());

		// Gradvis uppsnabbning
		tickCount++;
		if (tickCount % TICKS_PER_SPEEDUP == 0) {
		    Timer timer = (Timer) e.getSource();
		    timer.setDelay(Math.max(MIN_DELAY, timer.getDelay() - SPEEDUP_STEP));
		}

		// Show state
		stateLabel.setText(board.getFallHandler().getDescription());
	    }
	};

	final Timer clockTimer = new Timer(START_DELAY, doOneStep);

	// Pause on P
	component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "PauseGame");
	component.getActionMap().put("PauseGame", new PauseAction(clockTimer));

	clockTimer.setCoalesce(true);
	clockTimer.start();

	//clockTimer.stop();
    }

    // betyg 4 felhantering
    private void handleSaveError(IOException firstError) {
	IOException error = firstError;
	while (error != null) {
	    int choice = JOptionPane.showConfirmDialog(null,
						       "Kunde inte spara highscore-listan:\n" + error.getMessage() + "\n\nVill du försöka igen?",
						       "Fel vid sparning", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
	    if (choice != JOptionPane.YES_OPTION) {
		return;
	    }
	    try {
		board.getHighscoreList().saveHighscoreList();
		error = null;   // lyckades, då avslutas loopen
	    } catch (IOException e) {
		error = e;      // misslyckades igen, då frågar vi igen
	    }
	}
    }
}
