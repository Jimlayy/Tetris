package se.liu.jimhe280.tetris;

public class Highscore
{
    private String playerName = null;
    private int score = 0;

    public Highscore() {}

    public Highscore(final String playerName, final int score) {
	this.playerName = playerName;
	this.score = score;
    }

    public String getPlayerName() {
	return playerName;
    }

    public int getScore() {
	return score;
    }
}
