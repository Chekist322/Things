public class Move {
	private int row;
	private int col;
	
	Move(int row, int col) {
		this.row = row;
		this.col = col;
	}

	int getRow() {
		return row;
	}

	int getCol() {
		return col;
	}
	
	public String toString() {
		return String.valueOf(row) + "," + col;
	}
} // End class Move
