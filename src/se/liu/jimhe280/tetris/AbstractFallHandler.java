package se.liu.jimhe280.tetris;

import java.awt.Point;

public abstract class AbstractFallHandler implements FallHandler
{
    @Override public boolean hasCollision(final Board board, final Point oldPosition) {
	Point fallingPolyPoint = board.getFallingPolyPoint();
	SquareType[][] fallingPolySquares = board.getFallingPoly().getPolySquares();
	for (int y = 0; y < fallingPolySquares.length; y++) {
	    for (int x = 0; x < fallingPolySquares[0].length; x++) {
		if (fallingPolySquares[y][x] != SquareType.EMPTY
		    && isCollision(board.getSquareType(x + fallingPolyPoint.x, y + fallingPolyPoint.y))) {
		    return true;
		}
	    }
	}
	return false;
    }

    protected boolean isCollision(SquareType boardSquare) {
	return boardSquare != SquareType.EMPTY;
    }
}