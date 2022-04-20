package pieces;

import java.util.Objects;

public class Pieces {
    private int leftSide;
    private int rightSide;

    public Pieces(int leftSide, int rightSide){
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public int getLeftSide() {
        return leftSide;
    }

    public void setLeftSide(int leftSide) {
        this.leftSide = leftSide;
    }

    public int getRightSide() {
        return rightSide;
    }

    public void setRightSide(int rightSide) {
        this.rightSide = rightSide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pieces pieces = (Pieces) o;
        return leftSide == pieces.leftSide && rightSide == pieces.rightSide;
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftSide, rightSide);
    }

    @Override
    public String toString() {
        return " (" + leftSide + " : " + rightSide + ") ";
    }

}

