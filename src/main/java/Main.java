public class Main {

	public static void main(String[] args) {
		int testColumn = 5;
		int testRow = 4;
		GameController gameController = new GameController();

		gameController.newPosition(BoardBuilder.newGameBoard());
		gameController.showAllValidMoves(PieceColor.WHITE);
		gameController.movePiece(5, 2, 5, 4);
		gameController.movePiece(8, 7, 8, 6);
		gameController.movePiece(5, 4, 5, 5);
		gameController.movePiece(4, 7, 4, 5);
		gameController.showAllValidMoves(PieceColor.WHITE);
		System.out.println(gameController.getGame().isNextMoveCanEnPassant()); 
		gameController.movePiece(5, 5, 4, 6);
		
//		gameController.movePiece(5, 7, 5, 5);
//		gameController.movePiece(6, 1, 5, 3);
//		gameController.movePiece(4, 7, 4, 5);
//		gameController.movePiece(6, 2, 6, 4);
//		gameController.movePiece(3, 7, 3, 5);
//		gameController.movePiece(7, 1, 6, 2);
//		gameController.movePiece(2, 7, 2, 5);
//		gameController.movePiece(5, 1, 7, 1);

		
		// System.out.println(gameController.getValidMoves(testColumn, testRow));
	}
}
