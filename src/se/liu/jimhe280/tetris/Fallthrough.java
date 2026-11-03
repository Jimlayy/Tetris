package se.liu.jimhe280.tetris;

import java.awt.*;

public class Fallthrough extends AbstractFallHandler
{
    @Override protected boolean isCollision(final SquareType boardSquare) {
	return boardSquare == SquareType.OUTSIDE;
    }

    @Override public String getDescription() {
	return "Fallthrough";
    }
}
