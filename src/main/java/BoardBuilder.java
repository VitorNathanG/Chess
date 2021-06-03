import java.util.ArrayList;

public class BoardBuilder {

    public static Board newGameBoard() {
        Board board = new Board();
        board.setPieceInPosition(1, 1, new Piece(PieceType.ROOK, PieceColor.WHITE));
        board.setPieceInPosition(8, 1, new Piece(PieceType.ROOK, PieceColor.WHITE));
        board.setPieceInPosition(1, 8, new Piece(PieceType.ROOK, PieceColor.BLACK));
        board.setPieceInPosition(8, 8, new Piece(PieceType.ROOK, PieceColor.BLACK));
        board.setPieceInPosition(3, 1, new Piece(PieceType.BISHOP, PieceColor.WHITE));
        board.setPieceInPosition(6, 1, new Piece(PieceType.BISHOP, PieceColor.WHITE));
        board.setPieceInPosition(3, 8, new Piece(PieceType.BISHOP, PieceColor.BLACK));
        board.setPieceInPosition(6, 8, new Piece(PieceType.BISHOP, PieceColor.BLACK));
        board.setPieceInPosition(2, 1, new Piece(PieceType.KNIGHT, PieceColor.WHITE));
        board.setPieceInPosition(7, 1, new Piece(PieceType.KNIGHT, PieceColor.WHITE));
        board.setPieceInPosition(2, 8, new Piece(PieceType.KNIGHT, PieceColor.BLACK));
        board.setPieceInPosition(7, 8, new Piece(PieceType.KNIGHT, PieceColor.BLACK));
        board.setPieceInPosition(4, 1, new Piece(PieceType.QUEEN, PieceColor.WHITE));
        board.setPieceInPosition(5, 1, new Piece(PieceType.KING, PieceColor.WHITE));
        board.setPieceInPosition(4, 8, new Piece(PieceType.QUEEN, PieceColor.BLACK));
        board.setPieceInPosition(5, 8, new Piece(PieceType.KING, PieceColor.BLACK));
        for (int i = 1; i <=8; i++) {
            board.setPieceInPosition(i, 2, new Piece(PieceType.PAWN, PieceColor.WHITE));
            board.setPieceInPosition(i, 7, new Piece(PieceType.PAWN, PieceColor.BLACK));
        }
        return board;
    }



}
