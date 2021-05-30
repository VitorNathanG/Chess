public class Main {

    public static void main(String[] args) {
        int testColumn = 5;
        int testRow = 4;
        GameController gameController = new GameController();
        gameController.newPosition(new Board()
                .setPieceInPosition(testColumn, testRow, new Piece(PieceType.BISHOP, PieceColor.BLACK))
                .setPieceInPosition(4, 5, new Piece(PieceType.BISHOP, PieceColor.WHITE))
                .setPieceInPosition(6, 5, new Piece(PieceType.BISHOP, PieceColor.WHITE)));
        System.out.println(gameController.positionsCartesianToChessNotation(gameController.getPossibleMoves(testColumn, testRow,
                gameController.getGame().getBoard().getPieceInPosition(testColumn,testRow))));
    }
}
