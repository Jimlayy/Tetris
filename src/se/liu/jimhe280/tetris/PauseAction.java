package se.liu.jimhe280.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PauseAction extends AbstractAction
{
    private Timer timer;

    public PauseAction(Timer timer)
    {
	this.timer = timer;
    }

    @Override public void actionPerformed(final ActionEvent actionEvent) {
	if (timer.isRunning()) {
	    timer.stop();
	} else {
	    timer.start();
	}
    }
}
