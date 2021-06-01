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
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                System.out.println(game.getBoard().getPieceInPosition(i, j));
                if (game.getBoard().getPieceInPosition(i, j) == null) {
                    continue;
                }
                if(game.getBoard().getPieceInPosition(i, j).getPieceColor().equals(color) && game.getBoard().getPieceInPosition(i, j).getPieceType().equals(PieceType.KING)){
                } else if (game.getBoard().getPieceInPosition(i, j).getPieceColor().equals(color)) {
                    ArrayList<ArrayList<Integer>> possibleMoves = getValidMoves(i, j);

                    if (possibleMoves.stream().anyMatch(move -> (move.get(0) == column && move.get(1) == row))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean movePiece(int columnFrom, int rowFrom, int columnTo, int rowTo) {
        if (game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceColor() != game.getTurn()) {
            System.out.println("It's the other player's move");
            return false;
        }
        ArrayList<ArrayList<Integer>> validMoves = getValidMoves(columnFrom, rowFrom);
        PieceColor notTurn = (game.getTurn() == PieceColor.BLACK ? PieceColor.WHITE : PieceColor.BLACK);
        boolean isNextMoveEnPassant = false;
        boolean isAValidMove =
                validMoves.stream().anyMatch(move -> columnTo == move.get(0) && rowTo == move.get(1));
        if (isAValidMove) {
            //An en passant capture
            if (game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceType() == PieceType.PAWN && columnFrom !=
                    columnTo && game.getBoard().getPieceInPosition(columnTo, rowTo) == null) {
                game.getBoard()
                        .setPieceInPosition(columnTo, rowTo, game.getBoard().getPieceInPosition(columnFrom, rowFrom))
                        .setPieceInPosition(columnFrom, rowFrom, null)
                        .setPieceInPosition(columnTo, rowFrom, null)
                ;
            //A player allows an en passant capture in the next move
            } else if(game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceType() == PieceType.PAWN && Math.abs(rowFrom - rowTo) == 2) {
                isNextMoveEnPassant = true;
                game.getBoard()
                        .setPieceInPosition(columnTo, rowTo, game.getBoard().getPieceInPosition(columnFrom, rowFrom))
                        .setPieceInPosition(columnFrom, rowFrom, null)
                ;
                game.setEnPassantPosition(new int[]{columnTo, rowTo - (rowFrom - rowTo)/2});
            //A player castles king side
            } else if(game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceType() == PieceType.KING && columnTo - columnFrom == 2) {
                game.getBoard()
                        .setPieceInPosition(columnTo, rowTo, game.getBoard().getPieceInPosition(columnFrom, rowFrom))
                        .setPieceInPosition(columnFrom, rowFrom, null)
                        .setPieceInPosition(columnTo-1, rowTo, game.getBoard().getPieceInPosition(8, rowFrom))
                        .setPieceInPosition(8, rowFrom, null)
                        ;
                if(game.getTurn() == PieceColor.WHITE) {
                    game.setWhiteCanCastleQueenSide(false);
                    game.setWhiteCanCastleKingSide(false);
                } else {
                    game.setBlackCanCastleQueenSide(false);
                    game.setBlackCanCastleKingSide(false);
                }
                //A player castles queen side
            } else if(game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceType() == PieceType.KING && columnTo - columnFrom == -2) {
                game.getBoard()
                        .setPieceInPosition(columnTo, rowTo, game.getBoard().getPieceInPosition(columnFrom, rowFrom))
                        .setPieceInPosition(columnFrom, rowFrom, null)
                        .setPieceInPosition(columnTo + 1, rowTo, game.getBoard().getPieceInPosition(1, rowFrom))
                        .setPieceInPosition(1, rowFrom, null)
                ;
                if(game.getTurn() == PieceColor.WHITE) {
                    game.setWhiteCanCastleQueenSide(false);
                    game.setWhiteCanCastleKingSide(false);
                } else {
                    game.setBlackCanCastleQueenSide(false);
                    game.setBlackCanCastleKingSide(false);
                }
                //Removes the castling possibility if not used in the first king's move
            } else if (game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceType() == PieceType.KING) {
                game.getBoard()
                        .setPieceInPosition(columnTo, rowTo, game.getBoard().getPieceInPosition(columnFrom, rowFrom))
                        .setPieceInPosition(columnFrom, rowFrom, null)
                ;
                if(game.getTurn() == PieceColor.WHITE) {
                    game.setWhiteCanCastleQueenSide(false);
                    game.setWhiteCanCastleKingSide(false);
                } else {
                    game.setBlackCanCastleQueenSide(false);
                    game.setBlackCanCastleKingSide(false);
                }
            //White moves the queen rook, disabling the possibility to castle queen side
            } else if(columnFrom == 1 && rowFrom == 1 && game.isWhiteCanCastleQueenSide()) {
                game.getBoard()
                        .setPieceInPosition(columnTo, rowTo, game.getBoard().getPieceInPosition(columnFrom, rowFrom))
                        .setPieceInPosition(columnFrom, rowFrom, null)
                ;
                game.setWhiteCanCastleQueenSide(false);
                //White moves the king rook, disabling the possibility to castle king side
            } else if(columnFrom == 8 && rowFrom == 1 && game.isWhiteCanCastleKingSide()) {
                game.getBoard()
                        .setPieceInPosition(columnTo, rowTo, game.getBoard().getPieceInPosition(columnFrom, rowFrom))
                        .setPieceInPosition(columnFrom, rowFrom, null)
                ;
                game.setWhiteCanCastleKingSide(false);
                //Black moves the queen rook, disabling the possibility to castle queen side
            } else if(columnFrom == 1 && rowFrom == 8 && game.isBlackCanCastleQueenSide()) {
                game.getBoard()
                        .setPieceInPosition(columnTo, rowTo, game.getBoard().getPieceInPosition(columnFrom, rowFrom))
                        .setPieceInPosition(columnFrom, rowFrom, null)
                ;
                game.setBlackCanCastleQueenSide(false);
                //Black moves the king rook, disabling the possibility to castle king side
            } else if(columnFrom == 8 && rowFrom == 8 && game.isBlackCanCastleKingSide()) {
                game.getBoard()
                        .setPieceInPosition(columnTo, rowTo, game.getBoard().getPieceInPosition(columnFrom, rowFrom))
                        .setPieceInPosition(columnFrom, rowFrom, null)
                ;
                game.setBlackCanCastleKingSide(false);
            } else {
                game.getBoard()
                        .setPieceInPosition(columnTo, rowTo, game.getBoard().getPieceInPosition(columnFrom, rowFrom))
                        .setPieceInPosition(columnFrom, rowFrom, null);
            }
            verifyIfMoveIsACheck(columnTo, rowTo);
            switch (game.getTurn()) {
                case WHITE -> game.setTurn(PieceColor.BLACK);
                case BLACK -> game.setTurn(PieceColor.WHITE);
            }
            game.setNextMoveCanEnPassant(false);
            if(isNextMoveEnPassant) {
                game.setNextMoveCanEnPassant(true);
            }
            game.getBoard().terminalBoard();
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyIfMoveIsACheck(int columnTo, int rowTo) {
        ArrayList<ArrayList<Integer>> validNextMoves = getValidMoves(columnTo, rowTo);
        PieceColor pieceColor = game.getBoard().getPieceInPosition(columnTo, rowTo).getPieceColor();
        boolean isCheck;
        if (pieceColor.equals(PieceColor.BLACK)) {
            if (validNextMoves.stream().anyMatch(move -> game.getBoard().getColumnWhiteKing() == move.get(0) && game.getBoard().getRowWhiteKing() == move.get(1))) {
                game.setWhiteInCheck(true);
                return true;
            }
        } else {
            if (validNextMoves.stream().anyMatch(move -> game.getBoard().getColumnBlackKing() == move.get(0) && game.getBoard().getRowBlackKing() == move.get(1))) {
                game.setBlackInCheck(true);
                return true;
            }
        }
        return false;
    }

    public boolean isKingInCheck(PieceColor color) {
        switch (color) {
            case WHITE:
                return getGame().isWhiteInCheck();
        }
        return getGame().isBlackInCheck();
    }

    private ArrayList<ArrayList<Integer>> getPossibleMoves(int columnFrom, int rowFrom) {
        Piece pieceMoving = game.getBoard().getPieceInPosition(columnFrom, rowFrom);
        PieceColor oposingColor = (game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceColor() == PieceColor.BLACK ?
                PieceColor.WHITE : PieceColor.BLACK);
        ArrayList<ArrayList<Integer>> possibleMoves = new ArrayList<>();
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
                                    ArrayList<Integer> move = new ArrayList<>();
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
                                    ArrayList<Integer> move = new ArrayList<>();
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

                                ArrayList<Integer> move = new ArrayList<>();
                                move.add(columnFrom + i);
                                move.add(rowFrom + j);
                                possibleMoves.add(move);
                            }
                        }
                        return possibleMoves;
                    }
                    case WHITE: {
                        //Castling King side
                        if (rowFrom == 1 && columnFrom == 5 && game.isWhiteCanCastleKingSide() && !isKingInCheck(PieceColor.WHITE)) {
                            if (game.getBoard().getPieceInPosition(6, 1) == null && !isUnderAttack(6, 1,
                                    PieceColor.BLACK)) {
                                if (game.getBoard().getPieceInPosition(7, 1) == null && !isUnderAttack(7, 1,
                                        PieceColor.BLACK)) {
                                    ArrayList<Integer> move = new ArrayList<>();
                                    move.add(7);
                                    move.add(1);
                                    possibleMoves.add(move);
                                }
                            }
                        }
                        //Castling Queen side
                        if (rowFrom == 1 && columnFrom == 5 && game.isWhiteCanCastleQueenSide() && !isKingInCheck(PieceColor.WHITE)) {
                            if (game.getBoard().getPieceInPosition(4, 1) == null && !isUnderAttack(4, 1,
                                    PieceColor.WHITE)) {
                                if (game.getBoard().getPieceInPosition(3, 1) == null && !isUnderAttack(3, 1,
                                        PieceColor.WHITE)) {
                                    ArrayList<Integer> move = new ArrayList<>();
                                    move.add(3);
                                    move.add(1);
                                    possibleMoves.add(move);
                                }
                            }
                        }
                        for (int i = -1; i <= 1; i++) {
                            for (int j = -1; j <= 1; j++) {
                                if (isUnderAttack(columnFrom + i, rowFrom + j, PieceColor.BLACK)) {
                                    continue;
                                } else if (i == 0 && j == 0) {
                                    continue;
                                } else if (game.getBoard().getPieceInPosition(columnFrom + i, rowFrom + j) != null) {
                                    if (game.getBoard().getPieceInPosition(columnFrom + i, rowFrom + j).getPieceColor() == PieceColor.WHITE) {
                                        continue;
                                    }
                                }
                                ArrayList<Integer> move = new ArrayList<>();
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
            case PAWN:
                switch (game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceColor()) {
                    case WHITE -> {
                        if (rowFrom == 2) {
                            if (game.getBoard().getPieceInPosition(columnFrom, 3) == null) {
                                ArrayList<Integer> move = new ArrayList<>();
                                move.add(columnFrom);
                                move.add(3);
                                possibleMoves.add(move);
                            }
                            if (game.getBoard().getPieceInPosition(columnFrom, 4) == null) {
                                ArrayList<Integer> move = new ArrayList<>();
                                move.add(columnFrom);
                                move.add(4);
                                possibleMoves.add(move);
                            }
                        } else {
                            if (game.getBoard().getPieceInPosition(columnFrom, rowFrom + 1) == null) {
                                ArrayList<Integer> move = new ArrayList<>();
                                move.add(columnFrom);
                                move.add(rowFrom + 1);
                                possibleMoves.add(move);
                            }
                        }
                        if (game.getBoard().getPieceInPosition(columnFrom - 1, rowFrom + 1) != null &&
                                game.getBoard().getPieceInPosition(columnFrom - 1, rowFrom + 1).getPieceColor() == PieceColor.BLACK) {
                            ArrayList<Integer> move = new ArrayList<>();
                            move.add(columnFrom - 1);
                            move.add(rowFrom + 1);
                            possibleMoves.add(move);
                        }
                        if (game.getBoard().getPieceInPosition(columnFrom + 1, rowFrom + 1) != null &&
                                game.getBoard().getPieceInPosition(columnFrom + 1, rowFrom + 1).getPieceColor() == PieceColor.BLACK) {
                            ArrayList<Integer> move = new ArrayList<>();
                            move.add(columnFrom + 1);
                            move.add(rowFrom + 1);
                            possibleMoves.add(move);
                        }
                    }
                    case BLACK -> {
                        if (rowFrom == 7) {
                            if (game.getBoard().getPieceInPosition(columnFrom, 6) == null) {
                                ArrayList<Integer> move = new ArrayList<>();
                                move.add(columnFrom);
                                move.add(6);
                                possibleMoves.add(move);
                            }
                            if (game.getBoard().getPieceInPosition(columnFrom, 5) == null) {
                                ArrayList<Integer> move = new ArrayList<>();
                                move.add(columnFrom);
                                move.add(5);
                                possibleMoves.add(move);
                            }
                        } else {
                            if (game.getBoard().getPieceInPosition(columnFrom, rowFrom - 1) == null) {
                                ArrayList<Integer> move = new ArrayList<>();
                                move.add(columnFrom);
                                move.add(rowFrom - 1);
                                possibleMoves.add(move);
                            }
                        }
                        if (game.getBoard().getPieceInPosition(columnFrom - 1, rowFrom - 1) != null &&
                                game.getBoard().getPieceInPosition(columnFrom - 1, rowFrom - 1).getPieceColor() == PieceColor.WHITE) {
                            ArrayList<Integer> move = new ArrayList<>();
                            move.add(columnFrom - 1);
                            move.add(rowFrom - 1);
                            possibleMoves.add(move);
                        }
                        if (columnFrom + 1 < 9 && game.getBoard().getPieceInPosition(columnFrom + 1, rowFrom - 1) != null &&
                                game.getBoard().getPieceInPosition(columnFrom + 1, rowFrom - 1).getPieceColor() == PieceColor.WHITE) {
                            ArrayList<Integer> move = new ArrayList<>();
                            move.add(columnFrom + 1);
                            move.add(rowFrom - 1);
                            possibleMoves.add(move);
                        }
                    }
                }
                return possibleMoves;
            case KNIGHT:
                int[] columnSequence = {-1, 1, 1, -1, -2, 2, 2, -2};
                int[] rowSequence = {-2, -2, 2, 2, -1, -1, 1, 1};

                for (int i = 0; i < 8; i++) {
                    if (isWithinTheBoard(columnFrom + columnSequence[i], rowFrom + rowSequence[i])) {
                        if (game.getBoard().getPieceInPosition(columnFrom + columnSequence[i], rowFrom + rowSequence[i]) == null ||
                                game.getBoard().getPieceInPosition(columnFrom + columnSequence[i], rowFrom + rowSequence[i]).getPieceColor().equals(oposingColor)) {
                            ArrayList<Integer> move1 = new ArrayList<>();
                            move1.add(columnFrom + columnSequence[i]);
                            move1.add(rowFrom + rowSequence[i]);
                            possibleMoves.add(move1);
                        }
                    }
                }
                return possibleMoves;
        }
        return possibleMoves;
    }

    public boolean isWithinTheBoard(int column, int row) {
        if (column > 0 && column < 9) {
            if (row > 0 && row < 9) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<ArrayList<Integer>> getValidMoves(int columnFrom, int rowFrom) {
        ArrayList<ArrayList<Integer>> possibleMoves = getPossibleMoves(columnFrom, rowFrom);
        ArrayList<ArrayList<Integer>> validMoves = new ArrayList<>();
        for (ArrayList<Integer> move : possibleMoves) {
            GameController gm = new GameController();
            gm.newPosition(game.getBoard().cloneBoard().setPieceInPosition(move.get(0), move.get(1), game.getBoard().getPieceInPosition(columnFrom, rowFrom))
                            .setPieceInPosition(columnFrom, rowFrom, null)
                    );
            if (!gm.isKingInCheck(this.game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceColor())) {
                validMoves.add(move);
            }
        }
        return validMoves;
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
