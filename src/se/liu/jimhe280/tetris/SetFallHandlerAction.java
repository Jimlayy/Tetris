package se.liu.jimhe280.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SetFallHandlerAction extends AbstractAction
{
    private Board board;
    private FallHandler fallHandler;

    public SetFallHandlerAction(Board board, FallHandler fallHandler)
    {
	this.board = board;
	this.fallHandler = fallHandler;
    }

    @Override public void actionPerformed(final ActionEvent actionEvent) {
	board.setFallHandler(fallHandler);
    }
}