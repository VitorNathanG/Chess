import java.util.Scanner;

public class ConsoleCommandParser {

	GameController gameController = new GameController();
	
	public ConsoleCommandParser(GameController gameController) {
    	this.gameController = gameController;
    }
	
    public void waitForNextCommand() {
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        
        if(!userInput.startsWith("-")) {
        	byte[] position = userInput.getBytes();
        	int columnFrom = position[0] - 96;
        	int rowFrom = position[1] - 48;
        	int columnTo = position[3] - 96;
        	int rowTo = position[4] - 48;
        	this.gameController.movePiece(columnFrom, rowFrom, columnTo, rowTo);
        }

        if(userInput.equals("-end")) {
            System.exit(0);
        } else if (userInput.equals("-new")) {
        	this.gameController.createNewGame(null, null);
        }
        this.gameController.getGame().getBoard().terminalBoard();
        scanner.close();
    }
    
    
}
