import java.util.ArrayList;

public class Move {

	private int columnFrom;
	private int rowFrom;
	private int columnTo;
	private int rowTo;
	private PieceType pieceType;
	private PieceColor pieceColor;

	public Move(int columnFrom, int rowFrom, int columnTo, int rowTo, PieceType pieceType, PieceColor pieceColor) {
		super();
		this.columnFrom = columnFrom;
		this.rowFrom = rowFrom;
		this.columnTo = columnTo;
		this.rowTo = rowTo;
		this.pieceType = pieceType;
		this.pieceColor = pieceColor;
	}

	public int getColumnFrom() {
		return this.columnFrom;
	}

	public void setColumnFrom(int columnFrom) {
		this.columnFrom = columnFrom;
	}

	public int getRowFrom() {
		return this.rowFrom;
	}

	public void setRowFrom(int rowFrom) {
		this.rowFrom = rowFrom;
	}

	public int getColumnTo() {
		return this.columnTo;
	}

	public void setColumnTo(int columnTo) {
		this.columnTo = columnTo;
	}

	public int getRowTo() {
		return this.rowTo;
	}

	public void setRowTo(int rowTo) {
		this.rowTo = rowTo;
	}

	public PieceType getPieceType() {
		return this.pieceType;
	}

	public void setPieceType(PieceType pieceType) {
		this.pieceType = pieceType;
	}

	public PieceColor getPieceColor() {
		return this.pieceColor;
	}

	public void setPieceColor(PieceColor pieceColor) {
		this.pieceColor = pieceColor;
	}

	@Override
	public String toString() {
		return "Move: " + this.pieceType.toString() + " at " + GameController.numberToLetter(columnFrom) + rowFrom
				+ " to " + GameController.numberToLetter(columnTo) + this.rowTo;
	}

}
