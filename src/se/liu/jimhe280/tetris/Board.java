package se.liu.jimhe280.tetris;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.util.Map;
import java.util.Random;
import javax.swing.*;
import java.io.IOException;

public class Board
{
    private SquareType[][] squares;
    private int width, height;
    private HighscoreList highscoreList;
    private final static Random RND = new Random();
    private Poly fallingPoly = null;
    private Point fallingPolyPoint = null;
    private List<BoardListener> listeners = new ArrayList<>();
    private final static int MARGIN = 4;
    private final static int DOUBLE_MARGIN = MARGIN * 2;
    private boolean gameOver = false;
    private Map<Integer, Integer> pointMap = Map.of(1, 100, 2, 300, 3, 500, 4, 800);
    private int score = 0;
    // Betyg 5
    private FallHandler fallHandler = new DefaultFallHandler();

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public HighscoreList getHighscoreList() { return highscoreList; }

    public Poly getFallingPoly() {
	return fallingPoly;
    }

    public Point getFallingPolyPoint() {
	return fallingPolyPoint;
    }

    public int getScore() { return score; }

    public FallHandler getFallHandler() {
	return fallHandler;
    }

    public Board(int width, int height, HighscoreList highscoreList) {
	this.width = width;
	this.height = height;
	this.highscoreList = highscoreList;
	this.squares = new SquareType[height + DOUBLE_MARGIN][width + DOUBLE_MARGIN];
	// Borders
	for (int y = 0; y < height + DOUBLE_MARGIN; y++) {
	    for (int x = 0; x < width + DOUBLE_MARGIN; x++) {
		squares[y][x] = SquareType.OUTSIDE;
	    }
	}
	// Empty board
	for (int y = MARGIN; y < height + MARGIN; y++) {
	    for (int x = MARGIN; x < width + MARGIN; x++) {
		squares[y][x] = SquareType.EMPTY;
	    }
	}

	// TestPoly
	//SquareType[][] fall = new SquareType[4][4];
	//for (int y = 0; y < 4; y++) {
	//    for (int x = 0; x < 4; x++) {
	//	fall[y][x] = SquareType.EMPTY;
	//    }
	//}
	//fall[1][0] = SquareType.I;
	//fall[1][1] = SquareType.I;
	//fall[1][2] = SquareType.I;
	//fall[1][3] = SquareType.I;
	//fallingPoly = new Poly(fall);
	//fallingPolyPoint = new Point(width / 2, height / 2);
    }

    public SquareType getSquareType(int x, int y) {
	return squares[y + MARGIN][x + MARGIN];
    }

    public SquareType getVisibleSquareType(int x, int y) {
	if (fallingPoly == null) {
	    return getSquareType(x, y);
	}

	SquareType[][] fallingPolySquares = fallingPoly.getPolySquares();
	int fallingPolyX = fallingPolyPoint.x;
	int fallingPolyY = fallingPolyPoint.y;

	if (x < fallingPolyX || y < fallingPolyY
	    || x >= fallingPolyX + fallingPolySquares[0].length
	    || y >= fallingPolyY + fallingPolySquares.length){
	    return getSquareType(x, y);
	}

	SquareType fallingSquare = fallingPolySquares[y - fallingPolyY][x - fallingPolyX];
	if (fallingSquare == SquareType.EMPTY) {
	    return getSquareType(x, y);
	}
	else return fallingSquare;
    }

    private void setSquare(int x, int y, SquareType squareType) {
	squares[y + MARGIN][x + MARGIN] = squareType;
    }

    public void randomizeBoard() {
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		squares[y][x] = SquareType.values()[RND.nextInt(SquareType.values().length)];
	    }
	}
	notifyListeners();
    }

    public void addBoardListener(BoardListener bl){ listeners.add(bl); }

    private void notifyListeners(){
	for (BoardListener bl : listeners){
	    bl.boardChanged();
	}
    }

    public void tick() throws IOException {
	// Game over, don't do anything
	if (gameOver) return;
	// Create new fallingPoly
	if (fallingPoly == null){
	    // Resets state
	    setFallHandler(new DefaultFallHandler());

	    TetrominoMaker maker = new TetrominoMaker();
	    fallingPoly = maker.getPoly(RND.nextInt(maker.getNumberOfTypes() - 1));

	    // For testing
	    //fallingPoly = maker.getPoly(3);

	    fallingPolyPoint = new Point(width / 2 - fallingPoly.getPolySquares()[0].length / 2, 0);
	    if (hasCollision(new Point(fallingPolyPoint))) { gameOver(); }
	}
	// Move fallingPoly
	else{
	    Point oldPosition = new Point(fallingPolyPoint);
	    fallingPolyPoint.y += 1;
	    if (hasCollision(oldPosition)) {
		fallingPolyPoint.y -= 1;
		placeFallingPoly();
		fallingPoly = null;
		clearFullRows();
	    }
	}
	notifyListeners();
    }

    public void move(Direction direction) {
	if (fallingPoly == null) return;
	Point oldPosition = new Point(fallingPolyPoint);
	switch (direction) {
	    case LEFT:
		fallingPolyPoint.x -= 1;
		if (hasCollision(oldPosition)) { fallingPolyPoint.x += 1; }
		break;
	    case RIGHT:
		fallingPolyPoint.x += 1;
		if (hasCollision(oldPosition)) { fallingPolyPoint.x -= 1; }
		break;
	}
	notifyListeners();
    }

    // Ändrat för betyg 5
    private boolean hasCollision(Point oldPosition) {
	return fallHandler.hasCollision(this, oldPosition);
    }

    public void rotate(Direction dir) {
	if (fallingPoly == null) return;

	Poly oldFallingPoly = fallingPoly;
	switch (dir) {
	    case LEFT:
		fallingPoly = fallingPoly.rotateLeft();
		break;
	    case RIGHT:
		fallingPoly = fallingPoly.rotateRight();
	}
	if (hasCollision(new Point(fallingPolyPoint))) {
	    fallingPoly = oldFallingPoly;
	}
	notifyListeners();
    }

    public void placeFallingPoly(){
	SquareType[][] fallingPolySquares = fallingPoly.getPolySquares();
	for (int y = 0; y < fallingPolySquares.length; y++) {
	    for (int x = 0; x < fallingPolySquares[0].length; x++) {
		if (fallingPolySquares[y][x] != SquareType.EMPTY) {
		    setSquare(x + fallingPolyPoint.x, y + fallingPolyPoint.y, fallingPolySquares[y][x]);
		}
	    }
	}
    }

    // public för Heavy
    public boolean isFullRow(final int row) {
	for (int x = 0; x < width; x++) {
	    if (getSquareType(x, row) == SquareType.EMPTY) { return false; }
	}
	return true;
    }

    private void removeRow(final int row) {
	// Move down rows
	for (int y = row; y > 0; y--) {
	    for (int x = 0; x < width; x++) {
		setSquare(x, y, getSquareType(x, y-1));
	    }
	}
	// Add new empty row at top
	for (int x = 0; x < width; x++) {
	    setSquare(x, 0, SquareType.EMPTY);
	}
    }

    private void clearFullRows() {
	int rowsCleared = 0;

	for (int row = height - 1; row >= 0; row--) {
	    if (isFullRow(row)) {
		removeRow(row);
		rowsCleared++;
		row++; // Recheck row
	    }
	}

	// Update score
	if (rowsCleared > 0) {
	    score += pointMap.getOrDefault(rowsCleared, 0);
	}

	notifyListeners();
    }

    private void gameOver() throws IOException {
	gameOver = true;
	if (score != 0) {
	    String playerName =
		    (String) JOptionPane.showInputDialog(
			    null,
			    "Enter name:",
			    "Game Over!",
			    JOptionPane.PLAIN_MESSAGE,
			    null,
			    null,
			    null);
	    Highscore newHighscore = new Highscore(playerName, score);
	    highscoreList.addScore(newHighscore);
	    HighscorePanel.showHighscoreWindow(highscoreList);
	    highscoreList.saveHighscoreList();
	}
	else {
	    HighscorePanel.showHighscoreWindow(highscoreList);
	}
    }

    // Byta state (betyg 5)
    public void setFallHandler(final FallHandler fallHandler) {
	this.fallHandler = fallHandler;
    }

    // Betyg 5 heavy, trycker ner kvadrater under x,y
    public void pushSquaresDown(int x, int y) {
	int hole = y + 1;
	while (getSquareType(x, hole) != SquareType.EMPTY) {
	    hole++;
	}
	for (int row = hole; row > y; row--) {
	    setSquare(x, row, getSquareType(x, row - 1));
	}
	setSquare(x, y, SquareType.EMPTY);
    }

    // Heavy
    public void collapseRow(int row) {
	removeRow(row);
	score += pointMap.get(1);
    }
}
