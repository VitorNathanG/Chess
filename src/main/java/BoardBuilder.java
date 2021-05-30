import java.util.ArrayList;

public class BoardBuilder {

    public static Board newGameBoard() {
        Board board = new Board();
        board.setPieceInPosition(1, 1, new Piece(PieceType.ROOK, PieceColor.WHITE));
        board.setPieceInPosition(1, 8, new Piece(PieceType.ROOK, PieceColor.WHITE));
        board.setPieceInPosition(8, 1, new Piece(PieceType.ROOK, PieceColor.WHITE));
        board.setPieceInPosition(8, 8, new Piece(PieceType.ROOK, PieceColor.WHITE));
        board.setPieceInPosition(1, 3, new Piece(PieceType.BISHOP, PieceColor.WHITE));
        board.setPieceInPosition(1, 6, new Piece(PieceType.BISHOP, PieceColor.WHITE));
        board.setPieceInPosition(8, 3, new Piece(PieceType.BISHOP, PieceColor.BLACK));
        board.setPieceInPosition(8, 6, new Piece(PieceType.BISHOP, PieceColor.BLACK));
        board.setPieceInPosition(1, 3, new Piece(PieceType.KNIGHT, PieceColor.WHITE));
        board.setPieceInPosition(1, 6, new Piece(PieceType.KNIGHT, PieceColor.WHITE));
        board.setPieceInPosition(8, 3, new Piece(PieceType.KNIGHT, PieceColor.BLACK));
        board.setPieceInPosition(8, 6, new Piece(PieceType.KNIGHT, PieceColor.BLACK));
        board.setPieceInPosition(1, 4, new Piece(PieceType.QUEEN, PieceColor.WHITE));
        board.setPieceInPosition(1, 5, new Piece(PieceType.KING, PieceColor.WHITE));
        board.setPieceInPosition(8, 4, new Piece(PieceType.QUEEN, PieceColor.BLACK));
        board.setPieceInPosition(8, 5, new Piece(PieceType.KING, PieceColor.BLACK));
        for (int i = 1; i <=8; i++) {
            board.setPieceInPosition(2, i, new Piece(PieceType.PAWN, PieceColor.WHITE));
            board.setPieceInPosition(7, i, new Piece(PieceType.PAWN, PieceColor.WHITE));
        }
        return board;
    }



}
