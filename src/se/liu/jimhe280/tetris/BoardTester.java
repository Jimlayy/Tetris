package se.liu.jimhe280.tetris;

import javax.swing.*;
import java.io.IOException;

public class BoardTester
{
    public static void main(String[] args) {
	HighscoreList highscoreList = null;

	// betyg 4 läsa in highscorelist + kunna försöka igen vid fel
	while (highscoreList == null) {
	    try {
		highscoreList = HighscoreList.loadHighscoreList();
	    } catch (IOException e) {
		int choice = JOptionPane.showConfirmDialog(null,
							   "Kunde inte läsa in highscore-listan:\n" + e.getMessage()
							   + "\n\nVill du försöka igen?\n(Nej startar med en tom lista som skriver över den gamla när du sparar.)",
							   "Fel vid inläsning", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
		if (choice != JOptionPane.YES_OPTION) {
		    highscoreList = new HighscoreList();
		}
	    }
	}

	// Visa bild innan
	JFrame startScreen = IconPainter.showStartScreen();
	try {
	    Thread.sleep(2000);
	} catch (InterruptedException ignored) {}
	startScreen.dispose();

	Board board = new Board(10, 20, highscoreList);
	//System.out.println(BoardToTextConverter.convertToString(board));
	TetrisViewer viewer = new TetrisViewer(board);
	viewer.show();
	//System.out.println("######");
	//for (int i = 0; i < 10; i++) {
	//    board.randomizeBoard();
	//}
	//System.out.println(BoardToTextConverter.convertToString(board));
	//viewer.show();
    }
}
