package se.liu.jimhe280.tetris;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Heavy extends AbstractFallHandler
{
    @Override public boolean hasCollision(final Board board, final Point oldPosition) {
	Point newPosition = board.getFallingPolyPoint();
	boolean fellStraightDown = newPosition.x == oldPosition.x && newPosition.y == oldPosition.y + 1;

	if (!fellStraightDown) {
	    return super.hasCollision(board, oldPosition);   // vanliga regler
	}

	SquareType[][] polySquares = board.getFallingPoly().getPolySquares();
	List<Point> toBePushedDown = new ArrayList<>();

	for (int y = 0; y < polySquares.length; y++) {
	    for (int x = 0; x < polySquares[0].length; x++) {
		if (polySquares[y][x] == SquareType.EMPTY) {
		    continue;
		}
		int boardX = x + newPosition.x;
		int boardY = y + newPosition.y;
		SquareType square = board.getSquareType(boardX, boardY);

		if (square == SquareType.OUTSIDE) {
		    return true;
		}
		if (square != SquareType.EMPTY) {
		    if (!hasHoleBelow(board, boardX, boardY)) {
			return true;   // Har stöd: blocket fastnar
		    }
		    toBePushedDown.add(new Point(boardX, boardY));
		}
	    }
	}

	// Tryck ner
	for (Point p : toBePushedDown) {
	    board.pushSquaresDown(p.x, p.y);
	}

	// Ta bort full rad
	for (int row = board.getHeight() - 1; row >= 0; row--) {
	    if (board.isFullRow(row)) {
		board.collapseRow(row);
		row++;   // Kolla samma rad igen
	    }
	}
	return false;
    }

    private boolean hasHoleBelow(Board board, int x, int y) {
	for (int row = y + 1; board.getSquareType(x, row) != SquareType.OUTSIDE; row++) {
	    if (board.getSquareType(x, row) == SquareType.EMPTY) {
		return true;
	    }
	}
	return false;
    }

    @Override public String getDescription() {
	return "Heavy";
    }
}