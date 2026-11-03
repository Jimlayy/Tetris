package se.liu.jimhe280.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MoveAction extends AbstractAction
{
    private Direction direction;
    private Board board;

    public MoveAction(Direction direction, Board board)
    {
	this.direction = direction;
	this.board = board;
    }

    @Override public void actionPerformed(final ActionEvent actionEvent) {
	board.move(direction);
    }
}
