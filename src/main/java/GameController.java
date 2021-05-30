import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameController {

    private Game game;

    {

    }

    public void createNewGame(Player white, Player black) {
        game = new Game(BoardBuilder.newGameBoard(), white, black);
    }

    public void newPosition(Board board) {
        this.game = new Game(board);
    }

    public boolean isUnderAttack(int column, int row, PieceColor color) {
        return true;
    }

    public boolean movePiece(int columnFrom, int rowFrom, int columnTo, int rowTo) {
        ArrayList<ArrayList<Integer>> possibleMoves = getPossibleMoves(columnFrom, rowFrom);
        boolean validPosition = false;
        for (ArrayList<Integer> move : possibleMoves) {
            if (move.get(0) == columnTo && move.get(1) == rowTo) {
                validPosition = true;
                break;
            }
        }
        if (!validPosition) {
            return false;
        }
        return false;
    }

    public boolean isKingInCheck(PieceColor color) {
        switch (color) {
            case WHITE:
                return isUnderAttack(game.getColumnWhiteKing(), game.getRowWhiteKing(), PieceColor.BLACK);
        }
        return isUnderAttack(game.getColumnBlackKing(), game.getRowBlackKing(), PieceColor.WHITE);
    }

    public ArrayList<ArrayList<Integer>> getPossibleMoves(int columnFrom, int rowFrom) {
        Piece pieceMoving = game.getBoard().getPieceInPosition(columnFrom, rowFrom);
        ArrayList<ArrayList<Integer>> possibleMoves = new ArrayList<ArrayList<Integer>>();
        PieceColor whoDefends = PieceColor.BLACK;
        switch (pieceMoving.getPieceColor()) {
            case BLACK:
                whoDefends = PieceColor.WHITE;
            case WHITE:
                whoDefends = PieceColor.BLACK;
        }
        switch (pieceMoving.getPieceType()) {
            case KING: {
                switch (pieceMoving.getPieceColor()) {
                    case BLACK: {
                        //Castling King side
                        if (rowFrom == 8 && columnFrom == 5 && game.isBlackCanCastleKingSide() && !isKingInCheck(PieceColor.BLACK)) {
                            if (game.getBoard().getPieceInPosition(6, 8) == null && !isUnderAttack(6, 8,
                                    PieceColor.WHITE)) {
                                if (game.getBoard().getPieceInPosition(7, 8) == null && !isUnderAttack(7, 8,
                                        PieceColor.WHITE)) {
                                    ArrayList<Integer> move = new ArrayList<Integer>();
                                    move.add(7);
                                    move.add(8);
                                    possibleMoves.add(move);
                                }
                            }
                        }
                        //Castling Queen side
                        if (rowFrom == 8 && columnFrom == 5 && game.isBlackCanCastleQueenSide() && !isKingInCheck(PieceColor.BLACK)) {
                            if (game.getBoard().getPieceInPosition(4, 8) == null && !isUnderAttack(4, 8,
                                    PieceColor.WHITE)) {
                                if (game.getBoard().getPieceInPosition(3, 8) == null && !isUnderAttack(3, 8,
                                        PieceColor.WHITE)) {
                                    ArrayList<Integer> move = new ArrayList<Integer>();
                                    move.add(3);
                                    move.add(8);
                                    possibleMoves.add(move);
                                }
                            }
                        }
                        for (int i = -1; i <= 1; i++) {
                            for (int j = -1; j <= 1; j++) {
                                if (isUnderAttack(columnFrom + i, rowFrom + j, PieceColor.WHITE)) {
                                    continue;
                                } else if (i == 0 && j == 0) {
                                    continue;
                                } else if (game.getBoard().getPieceInPosition(columnFrom + i, rowFrom + j) != null) {
                                    if (game.getBoard().getPieceInPosition(columnFrom + i, rowFrom + j).getPieceColor() == PieceColor.BLACK) {
                                        continue;
                                    }
                                }

                                ArrayList<Integer> move = new ArrayList<Integer>();
                                move.add(columnFrom + i);
                                move.add(rowFrom + j);
                                possibleMoves.add(move);
                            }
                        }
                        return possibleMoves;
                    }
                }
            }
            case ROOK:
                return generateParallelMoves(columnFrom, rowFrom);
            case BISHOP:
                return generateDiagonalMoves(columnFrom, rowFrom);
            case QUEEN: {
                possibleMoves = generateParallelMoves(columnFrom, rowFrom);
                possibleMoves.addAll(generateDiagonalMoves(columnFrom, rowFrom));
                return possibleMoves;
            }
        }
        return possibleMoves;
    }

    private ArrayList<ArrayList<Integer>> generateParallelMoves(int columnFrom, int rowFrom) {
        Piece pieceMoving = game.getBoard().getPieceInPosition(columnFrom, rowFrom);
        ArrayList<ArrayList<Integer>> possibleMoves = new ArrayList<ArrayList<Integer>>();
        PieceColor whoDefends = PieceColor.BLACK;
        switch (pieceMoving.getPieceColor()) {
            case WHITE -> whoDefends = PieceColor.BLACK;
            case BLACK -> whoDefends = PieceColor.WHITE;
        }
        for (int i = 1; i <= 8; i++) {
            ArrayList<Integer> move = new ArrayList<Integer>();
            if (rowFrom + i < 9) {
                move.add(columnFrom);
                move.add(rowFrom + i);
            } else {
                break;
            }
            if (game.getBoard().getPieceInPosition(columnFrom, rowFrom + i) == null) {
                possibleMoves.add(move);
            } else {
                if (game.getBoard().getPieceInPosition(columnFrom, rowFrom + i).getPieceColor() == whoDefends) {
                    possibleMoves.add(move);
                }
                break;
            }
        }
        for (int i = 1; i <= 8; i++) {
            ArrayList<Integer> move = new ArrayList<Integer>();
            if (rowFrom - i > 0) {
                move.add(columnFrom);
                move.add(rowFrom - i);
            } else {
                break;
            }
            if (game.getBoard().getPieceInPosition(columnFrom, rowFrom - i) == null) {
                possibleMoves.add(move);
            } else {
                if (game.getBoard().getPieceInPosition(columnFrom, rowFrom - i).getPieceColor() == whoDefends) {
                    possibleMoves.add(move);
                }
                break;
            }
        }
        for (int i = 1; i <= 8; i++) {
            ArrayList<Integer> move = new ArrayList<Integer>();
            if (columnFrom - i > 0) {
                move.add(columnFrom - i);
                move.add(rowFrom);
            } else {
                break;
            }
            if (game.getBoard().getPieceInPosition(columnFrom - i, rowFrom) == null) {
                possibleMoves.add(move);
            } else {
                if (game.getBoard().getPieceInPosition(columnFrom - i, rowFrom).getPieceColor() == whoDefends) {
                    possibleMoves.add(move);
                }
                break;
            }
        }
        for (int i = 1; i <= 8; i++) {
            ArrayList<Integer> move = new ArrayList<Integer>();
            if (columnFrom + i < 9) {
                move.add(columnFrom + i);
                move.add(rowFrom);
            } else {
                break;
            }
            if (game.getBoard().getPieceInPosition(columnFrom + i, rowFrom) == null) {
                possibleMoves.add(move);
            } else {
                if (game.getBoard().getPieceInPosition(columnFrom + i, rowFrom).getPieceColor() == whoDefends) {
                    possibleMoves.add(move);
                }
                break;
            }
        }
        return possibleMoves;
    }

    public ArrayList<ArrayList<Integer>> generateDiagonalMoves(int columnFrom, int rowFrom) {
        Piece pieceMoving = game.getBoard().getPieceInPosition(columnFrom, rowFrom);
        ArrayList<ArrayList<Integer>> possibleMoves = new ArrayList<ArrayList<Integer>>();
        PieceColor whoDefends = PieceColor.BLACK;
        switch (pieceMoving.getPieceColor()) {
            case WHITE -> whoDefends = PieceColor.BLACK;
            case BLACK -> whoDefends = PieceColor.WHITE;
        }
        for (int i = 1; i < 8; i++) {
            ArrayList<Integer> move = new ArrayList<Integer>();
            if (columnFrom + i < 9) {
                if (rowFrom + i < 9) {
                    move.add(columnFrom + i);
                    move.add(rowFrom + i);
                } else {
                    break;
                }
            } else {
                break;
            }
            if (game.getBoard().getPieceInPosition(columnFrom + i, rowFrom + i) == null) {
                possibleMoves.add(move);
            } else {
                if (game.getBoard().getPieceInPosition(columnFrom + i, rowFrom + i).getPieceColor() == whoDefends) {
                    possibleMoves.add(move);
                }
                break;
            }
        }
        for (int i = 1; i < 8; i++) {
            ArrayList<Integer> move = new ArrayList<Integer>();
            if (columnFrom - i > 0) {
                if (rowFrom + i < 9) {
                    move.add(columnFrom - i);
                    move.add(rowFrom + i);
                } else {
                    break;
                }
            } else {
                break;
            }
            if (game.getBoard().getPieceInPosition(columnFrom - i, rowFrom + i) == null) {
                possibleMoves.add(move);
            } else {
                if (game.getBoard().getPieceInPosition(columnFrom - i, rowFrom + i).getPieceColor() == whoDefends) {
                    possibleMoves.add(move);
                }
                break;
            }
        }
        for (int i = 1; i < 8; i++) {
            ArrayList<Integer> move = new ArrayList<Integer>();
            if (columnFrom - i > 0) {
                if (rowFrom - i > 0) {
                    move.add(columnFrom - i);
                    move.add(rowFrom - i);
                } else {
                    break;
                }
            } else {
                break;
            }
            if (game.getBoard().getPieceInPosition(columnFrom - i, rowFrom - i) == null) {
                possibleMoves.add(move);
            } else {
                if (game.getBoard().getPieceInPosition(columnFrom - i, rowFrom - i).getPieceColor() == whoDefends) {
                    possibleMoves.add(move);
                }
                break;
            }
        }
        for (int i = 1; i < 8; i++) {
            ArrayList<Integer> move = new ArrayList<Integer>();
            if (columnFrom + i < 9) {
                if (rowFrom - i > 0) {
                    move.add(columnFrom + i);
                    move.add(rowFrom - i);
                } else {
                    break;
                }
            } else {
                break;
            }
            if (game.getBoard().getPieceInPosition(columnFrom + i, rowFrom - i) == null) {
                possibleMoves.add(move);
            } else {
                if (game.getBoard().getPieceInPosition(columnFrom + i, rowFrom - i).getPieceColor() == whoDefends) {
                    possibleMoves.add(move);
                }
                break;
            }
        }
        return possibleMoves;
    }


    public List<String> positionsCartesianToChessNotation(ArrayList<ArrayList<Integer>> positions) {
        return positions.stream().map(position -> "" + numberToLetter(position.get(0)) + position.get(1)).collect(Collectors.toList());
    }

    private String numberToLetter(int i) {
        switch (i) {
            case 1:
                return "a";
            case 2:
                return "b";
            case 3:
                return "c";
            case 4:
                return "d";
            case 5:
                return "e";
            case 6:
                return "f";
            case 7:
                return "g";
            case 8:
                return "h";
            default:
                return "error";
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
