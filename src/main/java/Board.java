import java.util.ArrayList;

public class Board {

    private Piece[][] pieces = new Piece[9][9];

    public Board() {

    }

    public Board setPieceInPosition(int column, int row, Piece piece) {
        this.pieces[column][row] = piece;
        return this;
    }

    public Piece getPieceInPosition(int column, int row) {
        return this.pieces[column][row];
    }

}
