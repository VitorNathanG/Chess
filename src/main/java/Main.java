public class Main {

	public static void main(String[] args) {
		int testColumn = 5;
		int testRow = 4;
		GameController gameController = new GameController();

		gameController.newPosition(BoardBuilder.newGameBoard());
		gameController.getGame().getBoard().terminalBoard();

		ConsoleCommandParser ccp = new ConsoleCommandParser(gameController);
		while(true) {
			ccp.waitForCommand();
		}
	}
}
