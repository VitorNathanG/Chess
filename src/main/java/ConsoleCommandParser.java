import java.util.Scanner;

public class ConsoleCommandParser {

	GameController gameController = new GameController();
	
	public ConsoleCommandParser(GameController gameController) {
    	this.gameController = gameController;
    }
	
    public void waitForCommand() {
        Scanner scanner = new Scanner(System.in);
        
        String userInput = scanner.nextLine();
        
        if(!userInput.startsWith("-")) {
            if(userInput.length() != 5) {
                System.out.println("Invalid input");
            } else {

                byte[] position = userInput.getBytes();
                int columnFrom = position[0] - 96;
                int rowFrom = position[1] - 48;
                int columnTo = position[3] - 96;
                int rowTo = position[4] - 48;
                if(!this.gameController.movePiece(columnFrom, rowFrom, columnTo, rowTo)) {
                    System.out.println("Invalid move");
                }
            }
        }

        if(userInput.equals("-end")) {
            System.exit(0);
        } else if (userInput.equals("-new")) {
        	this.gameController.createNewGame(null, null);
        }
        this.gameController.getGame().getBoard().terminalBoard();
    }
}
