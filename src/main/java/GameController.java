import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameController {

	private Game game;

	public void createNewGame(Player white, Player black) {
		this.game = new Game(BoardBuilder.newGameBoard(), white, black);
	}

	public void newPosition(Board board) {
		this.game = new Game(board);
	}

	public boolean isUnderAttack(int column, int row, PieceColor color) {
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				if (this.game.getBoard().getPieceInPosition(i, j) == null) {
					continue;
				}
				if (this.game.getBoard().getPieceInPosition(i, j).getPieceColor().equals(color)
						&& this.game.getBoard().getPieceInPosition(i, j).getPieceType().equals(PieceType.KING)) {
				} else if (this.game.getBoard().getPieceInPosition(i, j).getPieceColor().equals(color)) {
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
		if (this.game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceColor() != this.game.getTurn()) {
			System.out.println("It's the other player's move");
			return false;
		}
		ArrayList<ArrayList<Integer>> validMoves = getValidMoves(columnFrom, rowFrom);
		PieceColor notTurn = (this.game.getTurn() == PieceColor.BLACK ? PieceColor.WHITE : PieceColor.BLACK);
		boolean isNextMoveEnPassant = false;
		boolean isAValidMove = validMoves.stream().anyMatch(move -> columnTo == move.get(0) && rowTo == move.get(1));
		if (isAValidMove) {
			// An en passant capture
			if (this.game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceType() == PieceType.PAWN
					&& columnFrom != columnTo && this.game.getBoard().getPieceInPosition(columnTo, rowTo) == null) {
				this.game.getBoard()
						.setPieceInPosition(columnTo, rowTo,
								this.game.getBoard().getPieceInPosition(columnFrom, rowFrom))
						.setPieceInPosition(columnFrom, rowFrom, null).setPieceInPosition(columnTo, rowFrom, null);
				// A player allows an en passant capture in the next move
			} else if (this.game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceType() == PieceType.PAWN
					&& Math.abs(rowFrom - rowTo) == 2) {
				isNextMoveEnPassant = true;
				this.game.getBoard()
						.setPieceInPosition(columnTo, rowTo,
								this.game.getBoard().getPieceInPosition(columnFrom, rowFrom))
						.setPieceInPosition(columnFrom, rowFrom, null);
				this.game.setEnPassantPosition(new int[] { columnTo, rowFrom + (rowTo - rowFrom) / 2 });
				// A player castles king side
			} else if (this.game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceType() == PieceType.KING
					&& columnTo - columnFrom == 2) {
				this.game.getBoard()
						.setPieceInPosition(columnTo, rowTo,
								this.game.getBoard().getPieceInPosition(columnFrom, rowFrom))
						.setPieceInPosition(columnFrom, rowFrom, null)
						.setPieceInPosition(columnTo - 1, rowTo, this.game.getBoard().getPieceInPosition(8, rowFrom))
						.setPieceInPosition(8, rowFrom, null);
				if (this.game.getTurn() == PieceColor.WHITE) {
					this.game.setWhiteCanCastleQueenSide(false);
					this.game.setWhiteCanCastleKingSide(false);
				} else {
					this.game.setBlackCanCastleQueenSide(false);
					this.game.setBlackCanCastleKingSide(false);
				}
				// A player castles queen side
			} else if (this.game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceType() == PieceType.KING
					&& columnTo - columnFrom == -2) {
				this.game.getBoard()
						.setPieceInPosition(columnTo, rowTo,
								this.game.getBoard().getPieceInPosition(columnFrom, rowFrom))
						.setPieceInPosition(columnFrom, rowFrom, null)
						.setPieceInPosition(columnTo + 1, rowTo, this.game.getBoard().getPieceInPosition(1, rowFrom))
						.setPieceInPosition(1, rowFrom, null);
				if (this.game.getTurn() == PieceColor.WHITE) {
					this.game.setWhiteCanCastleQueenSide(false);
					this.game.setWhiteCanCastleKingSide(false);
				} else {
					this.game.setBlackCanCastleQueenSide(false);
					this.game.setBlackCanCastleKingSide(false);
				}
				// Removes the castling possibility if not used in the first king's move
			} else if (this.game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceType() == PieceType.KING) {
				this.game.getBoard()
						.setPieceInPosition(columnTo, rowTo,
								this.game.getBoard().getPieceInPosition(columnFrom, rowFrom))
						.setPieceInPosition(columnFrom, rowFrom, null);
				if (this.game.getTurn() == PieceColor.WHITE) {
					this.game.setWhiteCanCastleQueenSide(false);
					this.game.setWhiteCanCastleKingSide(false);
				} else {
					this.game.setBlackCanCastleQueenSide(false);
					this.game.setBlackCanCastleKingSide(false);
				}
				// White moves the queen rook, disabling the possibility to castle queen side
			} else if (columnFrom == 1 && rowFrom == 1 && this.game.isWhiteCanCastleQueenSide()) {
				this.game.getBoard()
						.setPieceInPosition(columnTo, rowTo,
								this.game.getBoard().getPieceInPosition(columnFrom, rowFrom))
						.setPieceInPosition(columnFrom, rowFrom, null);
				this.game.setWhiteCanCastleQueenSide(false);
				// White moves the king rook, disabling the possibility to castle king side
			} else if (columnFrom == 8 && rowFrom == 1 && this.game.isWhiteCanCastleKingSide()) {
				this.game.getBoard()
						.setPieceInPosition(columnTo, rowTo,
								this.game.getBoard().getPieceInPosition(columnFrom, rowFrom))
						.setPieceInPosition(columnFrom, rowFrom, null);
				this.game.setWhiteCanCastleKingSide(false);
				// Black moves the queen rook, disabling the possibility to castle queen side
			} else if (columnFrom == 1 && rowFrom == 8 && this.game.isBlackCanCastleQueenSide()) {
				this.game.getBoard()
						.setPieceInPosition(columnTo, rowTo,
								this.game.getBoard().getPieceInPosition(columnFrom, rowFrom))
						.setPieceInPosition(columnFrom, rowFrom, null);
				this.game.setBlackCanCastleQueenSide(false);
				// Black moves the king rook, disabling the possibility to castle king side
			} else if (columnFrom == 8 && rowFrom == 8 && this.game.isBlackCanCastleKingSide()) {
				this.game.getBoard()
						.setPieceInPosition(columnTo, rowTo,
								this.game.getBoard().getPieceInPosition(columnFrom, rowFrom))
						.setPieceInPosition(columnFrom, rowFrom, null);
				this.game.setBlackCanCastleKingSide(false);
			} else {
				this.game.getBoard()
						.setPieceInPosition(columnTo, rowTo,
								this.game.getBoard().getPieceInPosition(columnFrom, rowFrom))
						.setPieceInPosition(columnFrom, rowFrom, null);
			}
			verifyIfMoveIsACheck(columnTo, rowTo);
			switch (this.game.getTurn()) {
			case WHITE -> this.game.setTurn(PieceColor.BLACK);
			case BLACK -> this.game.setTurn(PieceColor.WHITE);
			}
			this.game.setNextMoveCanEnPassant(false);
			if (isNextMoveEnPassant) {
				this.game.setNextMoveCanEnPassant(true);
			} else {
				this.game.setEnPassantPosition(null);
			}
			this.game.getBoard().terminalBoard();
			return true;
		} else {
			return false;
		}
	}

	public boolean verifyIfMoveIsACheck(int columnTo, int rowTo) {
		ArrayList<ArrayList<Integer>> validNextMoves = getValidMoves(columnTo, rowTo);
		PieceColor pieceColor = this.game.getBoard().getPieceInPosition(columnTo, rowTo).getPieceColor();
		PieceColor whoDefends = (pieceColor == PieceColor.BLACK ? PieceColor.WHITE : PieceColor.BLACK);
		if (pieceColor.equals(PieceColor.BLACK)) {
			return isUnderAttack(this.game.getBoard().getColumnWhiteKing(), this.game.getBoard().getRowWhiteKing(),
					whoDefends);
		}
		return isUnderAttack(this.game.getBoard().getColumnBlackKing(), this.game.getBoard().getRowBlackKing(),
				whoDefends);
	}

	public boolean isKingInCheck(PieceColor color) {
		return (color.equals(PieceColor.WHITE) ? getGame().isWhiteInCheck() : getGame().isBlackInCheck());
	}

	private ArrayList<ArrayList<Integer>> getPossibleMoves(int columnFrom, int rowFrom) {
		Piece pieceMoving = this.game.getBoard().getPieceInPosition(columnFrom, rowFrom);
		PieceColor oposingColor = (this.game.getBoard().getPieceInPosition(columnFrom, rowFrom)
				.getPieceColor() == PieceColor.BLACK ? PieceColor.WHITE : PieceColor.BLACK);
		ArrayList<ArrayList<Integer>> possibleMoves = new ArrayList<>();
		switch (pieceMoving.getPieceType()) {
		case KING: {
			switch (pieceMoving.getPieceColor()) {
			case BLACK: {
				// Castling King side
				if (rowFrom == 8 && columnFrom == 5 && this.game.isBlackCanCastleKingSide()
						&& !isKingInCheck(PieceColor.BLACK)) {
					if (this.game.getBoard().getPieceInPosition(6, 8) == null
							&& !isUnderAttack(6, 8, PieceColor.WHITE)) {
						if (this.game.getBoard().getPieceInPosition(7, 8) == null
								&& !isUnderAttack(7, 8, PieceColor.WHITE)) {
							ArrayList<Integer> move = new ArrayList<>();
							move.add(7);
							move.add(8);
							possibleMoves.add(move);
						}
					}
				}
				// Castling Queen side
				if (rowFrom == 8 && columnFrom == 5 && this.game.isBlackCanCastleQueenSide()
						&& !isKingInCheck(PieceColor.BLACK)) {
					if (this.game.getBoard().getPieceInPosition(4, 8) == null
							&& !isUnderAttack(4, 8, PieceColor.WHITE)) {
						if (this.game.getBoard().getPieceInPosition(3, 8) == null
								&& !isUnderAttack(3, 8, PieceColor.WHITE)) {
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
						} else if (!isWithinTheBoard(columnFrom + i, rowFrom + j)) {
							continue;
						} else if (this.game.getBoard().getPieceInPosition(columnFrom + i, rowFrom + j) != null) {
							if (this.game.getBoard().getPieceInPosition(columnFrom + i, rowFrom + j)
									.getPieceColor() == PieceColor.BLACK) {
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
				// Castling King side
				if (rowFrom == 1 && columnFrom == 5 && this.game.isWhiteCanCastleKingSide()
						&& !isKingInCheck(PieceColor.WHITE)) {
					if (this.game.getBoard().getPieceInPosition(6, 1) == null
							&& !isUnderAttack(6, 1, PieceColor.BLACK)) {
						if (this.game.getBoard().getPieceInPosition(7, 1) == null
								&& !isUnderAttack(7, 1, PieceColor.BLACK)) {
							ArrayList<Integer> move = new ArrayList<>();
							move.add(7);
							move.add(1);
							possibleMoves.add(move);
						}
					}
				}
				// Castling Queen side
				if (rowFrom == 1 && columnFrom == 5 && this.game.isWhiteCanCastleQueenSide()
						&& !isKingInCheck(PieceColor.WHITE)) {
					if (this.game.getBoard().getPieceInPosition(4, 1) == null
							&& !isUnderAttack(4, 1, PieceColor.WHITE)) {
						if (this.game.getBoard().getPieceInPosition(3, 1) == null
								&& !isUnderAttack(3, 1, PieceColor.WHITE)) {
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
						} else if (!isWithinTheBoard(columnFrom + i, rowFrom + j)) {
							continue;
						} else if (this.game.getBoard().getPieceInPosition(columnFrom + i, rowFrom + j) != null) {
							if (this.game.getBoard().getPieceInPosition(columnFrom + i, rowFrom + j)
									.getPieceColor() == PieceColor.WHITE) {
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
			return possibleMoves;
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
			switch (this.game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceColor()) {
			case WHITE -> {
				// First move
				if (rowFrom == 2) {
					if (this.game.getBoard().getPieceInPosition(columnFrom, 3) == null) {
						ArrayList<Integer> move = new ArrayList<>();
						move.add(columnFrom);
						move.add(3);
						possibleMoves.add(move);
					}
					if (this.game.getBoard().getPieceInPosition(columnFrom, 4) == null) {
						ArrayList<Integer> move = new ArrayList<>();
						move.add(columnFrom);
						move.add(4);
						possibleMoves.add(move);
					}
					// Not the pawn's first move
				} else {
					if (isWithinTheBoard(columnFrom, rowFrom + 1)
							&& this.game.getBoard().getPieceInPosition(columnFrom, rowFrom + 1) == null) {
						ArrayList<Integer> move = new ArrayList<>();
						move.add(columnFrom);
						move.add(rowFrom + 1);
						possibleMoves.add(move);
					}
				}
				// Capture to the left
				if (isWithinTheBoard(columnFrom - 1, rowFrom + 1)
						&& this.game.getBoard().getPieceInPosition(columnFrom - 1, rowFrom + 1) != null
						&& this.game.getBoard().getPieceInPosition(columnFrom - 1, rowFrom + 1)
								.getPieceColor() == PieceColor.BLACK) {
					ArrayList<Integer> move = new ArrayList<>();
					move.add(columnFrom - 1);
					move.add(rowFrom + 1);
					possibleMoves.add(move);
				}
				// Capture to the right
				if (isWithinTheBoard(columnFrom + 1, rowFrom + 1)
						&& this.game.getBoard().getPieceInPosition(columnFrom + 1, rowFrom + 1) != null
						&& this.game.getBoard().getPieceInPosition(columnFrom + 1, rowFrom + 1)
								.getPieceColor() == PieceColor.BLACK) {
					ArrayList<Integer> move = new ArrayList<>();
					move.add(columnFrom + 1);
					move.add(rowFrom + 1);
					possibleMoves.add(move);
				}
				if (this.game.isNextMoveCanEnPassant()) {
					// En passant capture to the right
					if (columnFrom + 1 == this.game.getEnPassantPosition()[0]
							&& rowFrom + 1 == this.game.getEnPassantPosition()[1]) {
						ArrayList<Integer> move = new ArrayList<>();
						move.add(columnFrom + 1);
						move.add(rowFrom + 1);
						possibleMoves.add(move);
					}
					// En passant capture to the left
					if (columnFrom - 1 == this.game.getEnPassantPosition()[0]
							&& rowFrom + 1 == this.game.getEnPassantPosition()[1]) {
						ArrayList<Integer> move = new ArrayList<>();
						move.add(columnFrom - 1);
						move.add(rowFrom + 1);
						possibleMoves.add(move);
					}
				}

			}
			case BLACK -> {
				if (rowFrom == 7) {
					if (this.game.getBoard().getPieceInPosition(columnFrom, 6) == null) {
						ArrayList<Integer> move = new ArrayList<>();
						move.add(columnFrom);
						move.add(6);
						possibleMoves.add(move);
					}
					if (this.game.getBoard().getPieceInPosition(columnFrom, 5) == null) {
						ArrayList<Integer> move = new ArrayList<>();
						move.add(columnFrom);
						move.add(5);
						possibleMoves.add(move);
					}
				} else {
					if (this.game.getBoard().getPieceInPosition(columnFrom, rowFrom - 1) == null) {
						ArrayList<Integer> move = new ArrayList<>();
						move.add(columnFrom);
						move.add(rowFrom - 1);
						possibleMoves.add(move);
					}
				}
				if (this.game.getBoard().getPieceInPosition(columnFrom - 1, rowFrom - 1) != null && this.game.getBoard()
						.getPieceInPosition(columnFrom - 1, rowFrom - 1).getPieceColor() == PieceColor.WHITE) {
					ArrayList<Integer> move = new ArrayList<>();
					move.add(columnFrom - 1);
					move.add(rowFrom - 1);
					possibleMoves.add(move);
				}
				if (columnFrom + 1 < 9 && this.game.getBoard().getPieceInPosition(columnFrom + 1, rowFrom - 1) != null
						&& this.game.getBoard().getPieceInPosition(columnFrom + 1, rowFrom - 1)
								.getPieceColor() == PieceColor.WHITE) {
					ArrayList<Integer> move = new ArrayList<>();
					move.add(columnFrom + 1);
					move.add(rowFrom - 1);
					possibleMoves.add(move);
				}
			}
			}
			return possibleMoves;
		case KNIGHT:
			int[] columnSequence = { -1, 1, 1, -1, -2, 2, 2, -2 };
			int[] rowSequence = { -2, -2, 2, 2, -1, -1, 1, 1 };

			for (int i = 0; i < 8; i++) {
				if (isWithinTheBoard(columnFrom + columnSequence[i], rowFrom + rowSequence[i]) && (this.game.getBoard()
						.getPieceInPosition(columnFrom + columnSequence[i], rowFrom + rowSequence[i]) == null
						|| this.game.getBoard()
								.getPieceInPosition(columnFrom + columnSequence[i], rowFrom + rowSequence[i])
								.getPieceColor().equals(oposingColor))) {
					ArrayList<Integer> move1 = new ArrayList<>();
					move1.add(columnFrom + columnSequence[i]);
					move1.add(rowFrom + rowSequence[i]);
					possibleMoves.add(move1);

				}
			}
			return possibleMoves;
		}
		return possibleMoves;
	}

	public boolean isWithinTheBoard(int column, int row) {
		if (column > 0 && column < 9 && row > 0 && row < 9) {
			return true;
		}
		return false;
	}

	public ArrayList<ArrayList<Integer>> getValidMoves(int columnFrom, int rowFrom) {
		ArrayList<ArrayList<Integer>> possibleMoves = getPossibleMoves(columnFrom, rowFrom);
		ArrayList<ArrayList<Integer>> validMoves = new ArrayList<>();
		for (ArrayList<Integer> move : possibleMoves) {
			GameController gm = new GameController();
			gm.newPosition(this.game.getBoard().cloneBoard()
					.setPieceInPosition(move.get(0), move.get(1),
							this.game.getBoard().getPieceInPosition(columnFrom, rowFrom))
					.setPieceInPosition(columnFrom, rowFrom, null));
			if (!gm.isKingInCheck(this.game.getBoard().getPieceInPosition(columnFrom, rowFrom).getPieceColor())) {
				validMoves.add(move);
			}
		}
		return validMoves;
	}

	private ArrayList<ArrayList<Integer>> generateParallelMoves(int columnFrom, int rowFrom) {
		Piece pieceMoving = this.game.getBoard().getPieceInPosition(columnFrom, rowFrom);
		ArrayList<ArrayList<Integer>> possibleMoves = new ArrayList<ArrayList<Integer>>();
		PieceColor whoDefends = (pieceMoving.getPieceColor() == PieceColor.BLACK ? PieceColor.WHITE : PieceColor.BLACK);
		for (int i = 1; i <= 8; i++) {
			ArrayList<Integer> move = new ArrayList<Integer>();
			if (rowFrom + i < 9) {
				move.add(columnFrom);
				move.add(rowFrom + i);
			} else {
				break;
			}
			if (this.game.getBoard().getPieceInPosition(columnFrom, rowFrom + i) == null) {
				possibleMoves.add(move);
			} else {
				if (this.game.getBoard().getPieceInPosition(columnFrom, rowFrom + i).getPieceColor() == whoDefends) {
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
			if (this.game.getBoard().getPieceInPosition(columnFrom, rowFrom - i) == null) {
				possibleMoves.add(move);
			} else {
				if (this.game.getBoard().getPieceInPosition(columnFrom, rowFrom - i).getPieceColor() == whoDefends) {
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
			if (this.game.getBoard().getPieceInPosition(columnFrom - i, rowFrom) == null) {
				possibleMoves.add(move);
			} else {
				if (this.game.getBoard().getPieceInPosition(columnFrom - i, rowFrom).getPieceColor() == whoDefends) {
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
			if (this.game.getBoard().getPieceInPosition(columnFrom + i, rowFrom) == null) {
				possibleMoves.add(move);
			} else {
				if (this.game.getBoard().getPieceInPosition(columnFrom + i, rowFrom).getPieceColor() == whoDefends) {
					possibleMoves.add(move);
				}
				break;
			}
		}
		return possibleMoves;
	}

	public ArrayList<ArrayList<Integer>> generateDiagonalMoves(int columnFrom, int rowFrom) {
		Piece pieceMoving = this.game.getBoard().getPieceInPosition(columnFrom, rowFrom);
		ArrayList<ArrayList<Integer>> possibleMoves = new ArrayList<ArrayList<Integer>>();
		PieceColor whoDefends = (pieceMoving.getPieceColor() == PieceColor.BLACK ? PieceColor.WHITE : PieceColor.BLACK);
		for (int i = 1; i < 8; i++) {
			ArrayList<Integer> move = new ArrayList<Integer>();
			if (columnFrom + i < 9 && rowFrom + i < 9) {
				move.add(columnFrom + i);
				move.add(rowFrom + i);
			} else {
				break;
			}
			if (this.game.getBoard().getPieceInPosition(columnFrom + i, rowFrom + i) == null) {
				possibleMoves.add(move);
			} else {
				if (this.game.getBoard().getPieceInPosition(columnFrom + i, rowFrom + i)
						.getPieceColor() == whoDefends) {
					possibleMoves.add(move);
				}
				break;
			}
		}
		for (int i = 1; i < 8; i++) {
			ArrayList<Integer> move = new ArrayList<Integer>();
			if (columnFrom - i > 0 && rowFrom + i < 9) {
				move.add(columnFrom - i);
				move.add(rowFrom + i);
			} else {
				break;
			}
			if (this.game.getBoard().getPieceInPosition(columnFrom - i, rowFrom + i) == null) {
				possibleMoves.add(move);
			} else {
				if (this.game.getBoard().getPieceInPosition(columnFrom - i, rowFrom + i)
						.getPieceColor() == whoDefends) {
					possibleMoves.add(move);
				}
				break;
			}
		}
		for (int i = 1; i < 8; i++) {
			ArrayList<Integer> move = new ArrayList<Integer>();
			if (columnFrom - i > 0 && rowFrom - i > 0) {
				move.add(columnFrom - i);
				move.add(rowFrom - i);
			} else {
				break;
			}
			if (this.game.getBoard().getPieceInPosition(columnFrom - i, rowFrom - i) == null) {
				possibleMoves.add(move);
			} else {
				if (this.game.getBoard().getPieceInPosition(columnFrom - i, rowFrom - i)
						.getPieceColor() == whoDefends) {
					possibleMoves.add(move);
				}
				break;
			}
		}
		for (int i = 1; i < 8; i++) {
			ArrayList<Integer> move = new ArrayList<Integer>();
			if (columnFrom + i < 9 && rowFrom - i > 0) {
				move.add(columnFrom + i);
				move.add(rowFrom - i);
			} else {
				break;
			}
			if (this.game.getBoard().getPieceInPosition(columnFrom + i, rowFrom - i) == null) {
				possibleMoves.add(move);
			} else {
				if (this.game.getBoard().getPieceInPosition(columnFrom + i, rowFrom - i)
						.getPieceColor() == whoDefends) {
					possibleMoves.add(move);
				}
				break;
			}
		}
		return possibleMoves;
	}

	public ArrayList<Move> generateAllValidMoves(PieceColor pieceColor) {
		ArrayList<Move> allValidMoves = new ArrayList<Move>();
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				if (this.game.getBoard().getPieceInPosition(i, j) != null
						&& this.game.getBoard().getPieceInPosition(i, j).getPieceColor().equals(pieceColor)) {
					ArrayList<ArrayList<Integer>> movimentos = this.getValidMoves(i, j);
					for (ArrayList<Integer> movimento : movimentos) {
						allValidMoves.add(new Move(i, j, movimento.get(0), movimento.get(1),
								this.game.getBoard().getPieceInPosition(i, j).getPieceType(),
								this.game.getBoard().getPieceInPosition(i, j).getPieceColor()));
					}
				}
			}
		}
		return allValidMoves;
	}

	public void showAllValidMoves(PieceColor pieceColor) {
		generateAllValidMoves(pieceColor).stream().forEach(System.out::println);
	}

	public static List<String> positionsCartesianToChessNotation(ArrayList<ArrayList<Integer>> positions) {
		return positions.stream().map(position -> "" + numberToLetter(position.get(0)) + position.get(1))
				.collect(Collectors.toList());
	}

	public static String numberToLetter(int i) {
		return String.valueOf((char) (i + 96));
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
