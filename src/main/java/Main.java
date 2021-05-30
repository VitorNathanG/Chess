public class Main {

    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.newPosition(new Board()
                .setPieceInPosition(1, 1, new Piece(PieceType.BISHOP, PieceColor.BLACK))
                .setPieceInPosition(5, 7, new Piece(PieceType.ROOK, PieceColor.BLACK))
                );
        System.out.println(gameController.getPossibleMoves(1, 1,
                gameController.getGame().getBoard().getPieceInPosition(1,1)).toString());
    }
}
