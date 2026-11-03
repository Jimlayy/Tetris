package se.liu.jimhe280.tetris;

import java.awt.*;
import java.util.ArrayList;

public class Poly
{
    private SquareType[][] polySquares;

    public Poly(SquareType[][] poly) {
        this.polySquares = poly;
    }

    public SquareType[][] getPolySquares() {
        return polySquares;
    }

    public Poly rotateRight() {

        Poly newPoly = new Poly(new SquareType[this.polySquares.length][this.polySquares.length]);

        for (int r = 0; r < this.polySquares.length; r++) {
            for (int c = 0; c < this.polySquares.length; c++){
                newPoly.polySquares[c][this.polySquares.length-1-r] = this.polySquares[r][c];
            }
        }

        return newPoly;
    }

    public Poly rotateLeft() {
        Poly rotated1 = rotateRight();
        Poly rotated2 = rotated1.rotateRight();
        Poly newPoly = rotated2.rotateRight();

        return newPoly;
    }
}
