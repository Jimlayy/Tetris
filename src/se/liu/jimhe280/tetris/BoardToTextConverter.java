package se.liu.jimhe280.tetris;

public class BoardToTextConverter
{

    public static String convertToString(Board board){
	StringBuilder sb = new StringBuilder();
	for (int y = 0; y < board.getHeight(); y++) {
	    for (int x = 0; x < board.getWidth(); x++) {
		switch (board.getVisibleSquareType(x, y)) {
		    case EMPTY:
			sb.append("- ");
			break;
		    case I:
			sb.append("I ");
			break;
		    case O:
			sb.append("O ");
			break;
		    case T:
			sb.append("T ");
			break;
		    case S:
			sb.append("S ");
			break;
		    case Z:
			sb.append("Z ");
			break;
		    case J:
			sb.append("J ");
			break;
		    case L:
			sb.append("L ");
			break;
		    default:
			throw new IllegalStateException("Unexpected value: " + board.getSquareType(x, y));
		}
	    }
	    sb.append('\n');
	}
	return sb.toString();
    }
}
