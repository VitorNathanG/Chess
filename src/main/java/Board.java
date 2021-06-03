import java.util.ArrayList;

public class Board {

    private Piece[][] pieces = new Piece[9][9];
    private int rowWhiteKing = 1;
    private int columnWhiteKing = 5;
    private int rowBlackKing = 8;
    private int columnBlackKing = 5;

    public Board() {

    }

    public Board setPieceInPosition(int column, int row, Piece piece) {
        if(piece == null) {
            pieces[column][row] = null;
            return this;
        }
        if(piece.getPieceType().equals(PieceType.KING)) {
            switch (piece.getPieceColor()){
                case WHITE -> {
                    columnWhiteKing = column;
                    rowWhiteKing = row;
                }
                default -> {
                    columnBlackKing = column;
                    rowBlackKing = row;
                }
            }
        }
        this.pieces[column][row] = piece;
        return this;
    }

    public Piece getPieceInPosition(int column, int row) {
        return this.pieces[column][row];
    }

    public Board cloneBoard() {
        Board board = new Board();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if(this.getPieceInPosition(i, j) == null){
                    continue;
                }
                board.setPieceInPosition(i, j, this.getPieceInPosition(i, j));
            }
        }
        return board;
    }

    public int getRowWhiteKing() {
        return rowWhiteKing;
    }

    public void setRowWhiteKing(int rowWhiteKing) {
        this.rowWhiteKing = rowWhiteKing;
    }

    public int getColumnWhiteKing() {
        return columnWhiteKing;
    }

    public void setColumnWhiteKing(int columnWhiteKing) {
        this.columnWhiteKing = columnWhiteKing;
    }

    public int getRowBlackKing() {
        return rowBlackKing;
    }

    public void setRowBlackKing(int rowBlackKing) {
        this.rowBlackKing = rowBlackKing;
    }

    public int getColumnBlackKing() {
        return columnBlackKing;
    }

    public void setColumnBlackKing(int columnBlackKing) {
        this.columnBlackKing = columnBlackKing;
    }

    public void terminalBoard() {
        System.out.println("----------------------------");
        for (int i = 8; i > 0; i--) {
            System.out.print(i + "  ");
            System.out.print("|");
            for (int j = 1; j < 9; j++) {
                if (pieces[j][i] == null) {
                    System.out.print("  |");
                    continue;
                } else {
                    String pos = "";
                    switch (pieces[j][i].getPieceColor()){
                        case WHITE -> pos += "W";
                        default -> pos += "B";
                    }
                    switch (pieces[j][i].getPieceType()) {
                        case KNIGHT -> pos += "k";
                        case QUEEN -> pos += "Q";
                        case KING -> pos += "K";
                        case PAWN -> pos += "P";
                        case ROOK -> pos += "R";
                        case BISHOP -> pos += "B";
                        default -> pos += "K";
                    }
                    System.out.print(pos + "|");
                }
            }
            System.out.println();
        }
        System.out.println("     a  b  c  d  e  f  g  h");
        System.out.println("----------------------------");
    }
}
