package se.liu.jimhe280.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RotateAction extends AbstractAction
{
    private Direction direction;
    private Board board;

    public RotateAction(Direction direction, Board board)
    {
	this.direction = direction;
	this.board = board;
    }

    @Override public void actionPerformed(final ActionEvent actionEvent) {
	board.rotate(direction);
    }
}
