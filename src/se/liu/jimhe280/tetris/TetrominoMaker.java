package se.liu.jimhe280.tetris;

public class TetrominoMaker
{
    public int getNumberOfTypes(){
	return SquareType.values().length - 1;
    }

    public Poly getPoly(int type) {
	if (type < 0 || type >= getNumberOfTypes()) {
	    throw new IllegalArgumentException("Invalid index: " + type);
	}
	Poly poly;
	SquareType[][] polySquares;
	switch (type) {
	    case 0:
		poly = getPolyI();
		break;
	    case 1:
		poly = getPolyJ();
		break;
	    case 2:
		poly = getPolyL();
		break;
	    case 3:
		poly = getPolyO();
		break;
	    case 4:
		poly = getPolyS();
		break;
	    case 5:
		poly = getPolyT();
		break;
	    case 6:
		poly = getPolyZ();
		break;
	    default:
		throw new IllegalStateException("Unexpected value: " + type);
	}
	return poly;
    }

    private void setAllEmpty(SquareType[][] polySquares) {
	for (int x = 0; x < polySquares[0].length; x++) {
	    for (int y = 0; y < polySquares.length; y++) {
		polySquares[x][y] = SquareType.EMPTY;
	    }
	}
    }

    private Poly getPolyI() {
	SquareType[][] polySquares = new SquareType[4][4];
	setAllEmpty(polySquares);
	polySquares[1][0] = SquareType.I;
	polySquares[1][1] = SquareType.I;
	polySquares[1][2] = SquareType.I;
	polySquares[1][3] = SquareType.I;
	return new Poly(polySquares);
    }

    private Poly getPolyJ() {
	SquareType[][] polySquares = new SquareType[3][3];
	setAllEmpty(polySquares);
	polySquares[0][0] = SquareType.J;
	polySquares[1][0] = SquareType.J;
	polySquares[1][1] = SquareType.J;
	polySquares[1][2] = SquareType.J;
	return new Poly(polySquares);
    }

    private Poly getPolyL() {
	SquareType[][] polySquares = new SquareType[3][3];
	setAllEmpty(polySquares);
	polySquares[1][0] = SquareType.L;
	polySquares[1][1] = SquareType.L;
	polySquares[1][2] = SquareType.L;
	polySquares[0][2] = SquareType.L;
	return new Poly(polySquares);
    }

    private Poly getPolyO() {
	SquareType[][] polySquares = new SquareType[2][2];
	polySquares[0][0] = SquareType.O;
	polySquares[0][1] = SquareType.O;
	polySquares[1][0] = SquareType.O;
	polySquares[1][1] = SquareType.O;
	return new Poly(polySquares);
    }

    private Poly getPolyS() {
	SquareType[][] polySquares = new SquareType[3][3];
	setAllEmpty(polySquares);
	polySquares[1][0] = SquareType.S;
	polySquares[1][1] = SquareType.S;
	polySquares[0][1] = SquareType.S;
	polySquares[0][2] = SquareType.S;
	return new Poly(polySquares);
    }

    private Poly getPolyT() {
	SquareType[][] polySquares = new SquareType[3][3];
	setAllEmpty(polySquares);
	polySquares[1][0] = SquareType.T;
	polySquares[1][1] = SquareType.T;
	polySquares[1][2] = SquareType.T;
	polySquares[0][1] = SquareType.T;
	return new Poly(polySquares);
    }

    private Poly getPolyZ() {
	SquareType[][] polySquares = new SquareType[3][3];
	setAllEmpty(polySquares);
	polySquares[0][0] = SquareType.Z;
	polySquares[0][1] = SquareType.Z;
	polySquares[1][1] = SquareType.Z;
	polySquares[1][2] = SquareType.Z;
	return new Poly(polySquares);
    }
}
