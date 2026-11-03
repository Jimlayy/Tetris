package se.liu.jimhe280.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.EnumMap;
import java.util.Map;

public class TetrisComponent extends JComponent implements BoardListener
{
    private Board board;
    private final static EnumMap<SquareType, Color> SQUARE_COLORS = createColorMap();
    private static final int SQUARE_SIZE = 30;

    public TetrisComponent(final Board board) {
	super();
	this.board = board;
	setKeyBinds();
    }

    private static EnumMap<SquareType, Color> createColorMap() {
	 return new EnumMap<>(Map.ofEntries(
		Map.entry(SquareType.EMPTY, Color.GRAY),
		Map.entry(SquareType.I, Color.CYAN),
		Map.entry(SquareType.J, Color.BLUE),
		Map.entry(SquareType.L, Color.ORANGE),
		Map.entry(SquareType.O, Color.YELLOW),
		Map.entry(SquareType.S, Color.GREEN),
		Map.entry(SquareType.T, Color.MAGENTA),
		Map.entry(SquareType.Z, Color.RED))
	);
    }

    public Dimension getPreferredSize(){
	return new Dimension(board.getWidth() * SQUARE_SIZE, board.getHeight() * SQUARE_SIZE);
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	for (int y = 0; y < board.getHeight(); y++) {
	    for (int x = 0; x < board.getWidth(); x++) {
		g2d.setColor(SQUARE_COLORS.get(board.getVisibleSquareType(x, y)));
		g2d.fillRect(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
		// Draw grids
		g2d.setColor(Color.BLACK);
		g2d.drawRect(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
	    }
	}
    }

    @Override public void boardChanged() { repaint(); }

    public void setKeyBinds(){
	final InputMap inputMap = getInputMap();
	final ActionMap actionMap = getActionMap();

	inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "MoveLeft");
	inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "MoveRight");
	inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "RotateRight");
	inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "RotateLeft");

	actionMap.put("MoveLeft", new MoveAction(Direction.LEFT, board));
	actionMap.put("MoveRight", new MoveAction(Direction.RIGHT, board));
	actionMap.put("RotateRight", new RotateAction(Direction.RIGHT, board));
	actionMap.put("RotateLeft", new RotateAction(Direction.LEFT, board));

	// States, betyg 5
	inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "DefaultFall");
	actionMap.put("DefaultFall", new SetFallHandlerAction(board, new DefaultFallHandler()));

	inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "Fallthrough");
	actionMap.put("Fallthrough", new SetFallHandlerAction(board, new Fallthrough()));

	inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0), "Heavy");
	actionMap.put("Heavy", new SetFallHandlerAction(board, new Heavy()));
    }
}
