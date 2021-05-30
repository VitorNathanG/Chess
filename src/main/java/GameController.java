import java.util.ArrayList;

public class GameController {

    private Game game;

    public void createNewGame(Player white, Player black) {
        game = new Game(BoardBuilder.newGameBoard(), white, black);
    }

    public void newPosition(Board board) {
        this.game = new Game(board);
    }

    public boolean isUnderAttack(int row, int column, PieceColor color) {
        return true;
    }

    public boolean movePiece(int rowFrom, int columnFrom, int rowTo, int columnTo) {
        Piece selectedPiece = game.getBoard().getPieceInPosition(rowFrom, columnFrom);
        ArrayList<ArrayList<Integer>> possibleMoves = getPossibleMoves(rowFrom, columnFrom, selectedPiece);
        boolean validPosition = false;
        for (ArrayList<Integer> move: possibleMoves) {
            if(move.get(0) == rowTo && move.get(1) == columnTo) {
                validPosition = true;
                break;
            }
        }
        if(!validPosition) {
            return false;
        }
        return false;
    }

    public boolean isKingInCheck(PieceColor color) {
        switch (color) {
            case WHITE:
                return isUnderAttack(game.getRowWhiteKing(), game.getColumnWhiteKing(), PieceColor.BLACK);
        }
        return isUnderAttack(game.getRowBlackKing(), game.getColumnBlackKing(), PieceColor.WHITE);
    }

    public ArrayList<ArrayList<Integer>> getPossibleMoves(int rowFrom, int columnFrom, Piece piece) {
        ArrayList<ArrayList<Integer>> possibleMoves = new ArrayList<ArrayList<Integer>>();
        switch (piece.getPieceColor()) {
            case BLACK: {
                switch (piece.getPieceType()) {
                    case KING: {
                        //Castling King side
                        if (rowFrom == 8 && columnFrom == 5 && game.isBlackCanCastleKingSide() && !isKingInCheck(PieceColor.BLACK)) {
                            if(game.getBoard().getPieceInPosition(8, 6) == null && !isUnderAttack(8, 6,
                                    PieceColor.WHITE)) {
                                if(game.getBoard().getPieceInPosition(8, 7) == null && !isUnderAttack(8, 7,
                                        PieceColor.WHITE)) {
                                    ArrayList<Integer> move = new ArrayList<Integer>();
                                    move.add(8);
                                    move.add(7);
                                    possibleMoves.add(move);
                                }
                            }
                        }
                        //Castling Queen side
                        if (rowFrom == 8 && columnFrom == 5 && game.isBlackCanCastleQueenSide() && !isKingInCheck(PieceColor.BLACK)) {
                            if(game.getBoard().getPieceInPosition(8, 4) == null && !isUnderAttack(8, 4,
                                    PieceColor.WHITE)) {
                                if(game.getBoard().getPieceInPosition(8, 3) == null && !isUnderAttack(8, 3,
                                        PieceColor.WHITE)) {
                                    ArrayList<Integer> move = new ArrayList<Integer>();
                                    move.add(8);
                                    move.add(3);
                                    possibleMoves.add(move);
                                }
                            }
                        }
                        for (int i = -1; i <= 1; i++) {
                            for (int j = -1; j <= 1; j++) {
                                if(isUnderAttack(rowFrom + i, columnFrom + j, PieceColor.WHITE)) {
                                    continue;
                                } else if (i == 0 && j == 0) {
                                    continue;
                                } else if (game.getBoard().getPieceInPosition(rowFrom + i, columnFrom + j) != null) {
                                    if (game.getBoard().getPieceInPosition(rowFrom + i, columnFrom + j).getPieceColor() == PieceColor.BLACK) {
                                        continue;
                                    }
                                }

                                ArrayList<Integer> move = new ArrayList<Integer>();
                                move.add(rowFrom + i);
                                move.add(columnFrom + j);
                                possibleMoves.add(move);
                            }
                        }
                    }
                    case ROOK: {
                        for (int i = rowFrom + 1; i <= 8 ; i++) {
                            ArrayList<Integer> move = new ArrayList<Integer>();
                            move.add(i);
                            move.add(columnFrom);
                            if(game.getBoard().getPieceInPosition(i, columnFrom) == null) {
                                possibleMoves.add(move);
                            } else {
                                if(game.getBoard().getPieceInPosition(i, columnFrom).getPieceColor() == PieceColor.WHITE) {
                                    possibleMoves.add(move);
                                }
                                break;
                            }
                        }
                        for (int i = rowFrom - 1; i > 0 ; i--) {
                            ArrayList<Integer> move = new ArrayList<Integer>();
                            move.add(i);
                            move.add(columnFrom);
                            if(game.getBoard().getPieceInPosition(i, columnFrom) == null) {
                                possibleMoves.add(move);
                            } else {
                                if(game.getBoard().getPieceInPosition(i, columnFrom).getPieceColor() == PieceColor.WHITE) {
                                    possibleMoves.add(move);
                                }
                                break;
                            }
                        }
                        for (int i = columnFrom + 1; i <= 8 ; i++) {
                            ArrayList<Integer> move = new ArrayList<Integer>();
                            move.add(rowFrom);
                            move.add(i);
                            if(game.getBoard().getPieceInPosition(rowFrom, i) == null) {
                                possibleMoves.add(move);
                            } else {
                                if(game.getBoard().getPieceInPosition(rowFrom, i).getPieceColor() == PieceColor.WHITE) {
                                    possibleMoves.add(move);
                                }
                                break;
                            }
                        }
                        for (int i = columnFrom - 1; i > 0 ; i--) {
                            ArrayList<Integer> move = new ArrayList<Integer>();
                            move.add(rowFrom);
                            move.add(i);
                            if(game.getBoard().getPieceInPosition(rowFrom, i) == null) {
                                possibleMoves.add(move);
                            } else {
                                if(game.getBoard().getPieceInPosition(rowFrom, i).getPieceColor() == PieceColor.WHITE) {
                                    possibleMoves.add(move);
                                }
                                break;
                            }
                        }
                        return possibleMoves;
                    }
                    case BISHOP: {
                        for (int i = rowFrom; i < 8; i++) {
                            ArrayList<Integer> move = new ArrayList<Integer>();
                            if (columnFrom + i - rowFrom + 1 <= 8) {
                                move.add(i + 1);
                                move.add(columnFrom + i - rowFrom + 1);
                            } else break;
                            if (game.getBoard().getPieceInPosition(i + 1, columnFrom + i - rowFrom + 1) == null) {
                                possibleMoves.add(move);
                            } else {
                                if (game.getBoard().getPieceInPosition(i + 1, columnFrom + i - rowFrom + 1).getPieceColor() == PieceColor.WHITE) {
                                    possibleMoves.add(move);
                                }
                                break;
                            }
                        }
                        return possibleMoves;
                    }
                }
            }
            case WHITE: {

            }
        }
        return possibleMoves;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
