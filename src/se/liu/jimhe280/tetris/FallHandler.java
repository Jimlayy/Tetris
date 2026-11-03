package se.liu.jimhe280.tetris;

import java.awt.Point;

public interface FallHandler
{
    boolean hasCollision(Board board, final Point oldPosition);

    String getDescription();
}
