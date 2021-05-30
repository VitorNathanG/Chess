import java.util.ArrayList;

public class Board {

    private Piece[][] pieces = new Piece[9][9];

    public Board() {

    }

    public Board setPieceInPosition(int row, int column, Piece piece) {
        this.pieces[row][column] = piece;
        return this;
    }

    public Piece getPieceInPosition(int row, int column) {
        return this.pieces[row][column];
    }

}
