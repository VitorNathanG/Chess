public class Game {
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player winner = null;

    private PieceColor turn = PieceColor.WHITE;
    private boolean whiteCanCastleKingSide = true;
    private boolean whiteCanCastleQueenSide = true;
    private boolean blackCanCastleKingSide = true;
    private boolean blackCanCastleQueenSide = true;
    private boolean nextMoveCanEnPassant;

    public Game(Board board, Player whitePlayer, Player blackPlayer) {
        this.board = board;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }

    public Game(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public PieceColor getTurn() {
        return turn;
    }

    public void setTurn(PieceColor turn) {
        this.turn = turn;
    }

    public boolean isWhiteCanCastleKingSide() {
        return whiteCanCastleKingSide;
    }

    public void setWhiteCanCastleKingSide(boolean whiteCanCastleKingSide) {
        this.whiteCanCastleKingSide = whiteCanCastleKingSide;
    }

    public boolean isWhiteCanCastleQueenSide() {
        return whiteCanCastleQueenSide;
    }

    public void setWhiteCanCastleQueenSide(boolean whiteCanCastleQueenSide) {
        this.whiteCanCastleQueenSide = whiteCanCastleQueenSide;
    }

    public boolean isBlackCanCastleKingSide() {
        return blackCanCastleKingSide;
    }

    public void setBlackCanCastleKingSide(boolean blackCanCastleKingSide) {
        this.blackCanCastleKingSide = blackCanCastleKingSide;
    }

    public boolean isBlackCanCastleQueenSide() {
        return blackCanCastleQueenSide;
    }

    public void setBlackCanCastleQueenSide(boolean blackCanCastleQueenSide) {
        this.blackCanCastleQueenSide = blackCanCastleQueenSide;
    }

    public boolean isNextMoveCanEnPassant() {
        return nextMoveCanEnPassant;
    }

    public void setNextMoveCanEnPassant(boolean nextMoveCanEnPassant) {
        this.nextMoveCanEnPassant = nextMoveCanEnPassant;
    }
}
