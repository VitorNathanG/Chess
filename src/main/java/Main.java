public class Main {

    public static void main(String[] args) {
        int testColumn = 5;
        int testRow = 1;
        GameController gameController = new GameController();
//        gameController.newPosition(new Board()
//                .setPieceInPosition(testColumn, testRow, new Piece(PieceType.KING, PieceColor.BLACK))
//                .setPieceInPosition(6, 4, new Piece(PieceType.KNIGHT, PieceColor.WHITE))
//                .setPieceInPosition(6, 3, new Piece(PieceType.QUEEN, PieceColor.WHITE))
//        );
        gameController.newPosition(BoardBuilder.newGameBoard());
//        System.out.println(gameController.positionsCartesianToChessNotation(gameController.getValidMoves(testColumn, testRow)));
        gameController.getGame().getBoard().terminalBoard();
        System.out.println(gameController.movePiece(5, 2, 5, 4));
        gameController.getGame().getBoard().terminalBoard();
    }
}
