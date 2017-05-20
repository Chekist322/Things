class GomokuBoard {
	
	public enum Piece { INVALID, EMPTY, X, O }

    // Игровая доска
	private Piece [][] board;

	// размер доски
	static final int SIZE = 15;
	
	/**
	 * Создает доску и заполняет пустыми клетками
	 */

	GomokuBoard() {
		board = new Piece[SIZE][SIZE];
		this.reset();
	}
	
	/**
	 * Создает копию существующей доски
	 * @return копию доски
	 */
    GomokuBoard createCopy() {
		GomokuBoard other = new GomokuBoard();
		
		for (int i = 0; i < SIZE; i++) {
            System.arraycopy(this.board[i], 0, other.board[i], 0, SIZE);
		}	
		return other;
	}

	/**
	 * Ставит все клетки пустыми
	 */
    void reset() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				this.board[i][j] = Piece.EMPTY;
			}
		}
	}

	Piece getPiece(int row, int col) {
		Piece p = Piece.INVALID;
		
		if (isValidRow(row) && isValidColumn(col)) {
			p = board[row][col];
		}
			
		return p;
	}
	private boolean isValidRow(int row) {
		return (row >= 0 && row < SIZE);
	}

	private boolean isValidColumn(int column) {
		return (column >= 0 && column < SIZE);
	}

	
	/**
	 * @param row
	 * @param col
	 * @return true если не пустота
	 */
    boolean isOccupied(int row, int col) {
		boolean occupied;

		if (row < 0 || col < 0 || row >= SIZE || col >= SIZE) {
			occupied = false;
		} else {
			occupied = (!(board[row][col] == Piece.EMPTY));
		}
		return occupied;
	}
	
	/**
	 * Добавляет клетку на доску
	 * @param row
	 * @param col
	 * @param p
	 * @return false если место занято, true иначе
	 */
    boolean addPiece(int row, int col, Piece p) {
		
		if (isOccupied(row, col)) {
			return false;
		} else {
			board[row][col] = p;
		}
		return true;
	}

	void printBoard() {
		System.out.println("                       1 1 1 1 1");
		System.out.println("   0 1 2 3 4 5 6 7 8 9 0 1 2 3 4");
		for (int i = SIZE - 1; i >= 0; i--) {
			printRowHeadings(i);

			for (int j = 0; j < SIZE; j++) {
				this.printPiece(board[i][j]);
			}
			System.out.println();
		}

	}
	/**
	 * Отображает доску
	 */


	private void printRowHeadings(int i) {
		// Print row headings
		if (i < 10) {
			System.out.print(0);
		}
		System.out.print(i);
		System.out.print(" ");
	}

	private void printPiece(Piece p) {
		String pieceStr = ".";
		if (p.equals(Piece.X)) {
			pieceStr = "X";
		} else if (p.equals(Piece.O)) {
			pieceStr = "O";
		}
		System.out.print("");
		System.out.print(pieceStr);
		System.out.print(" ");
	}

	/**
	 * @return true если доска заполнена
	 */
    boolean isFull() {
		boolean full = true;
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (this.board[i][j] == Piece.EMPTY) {
					full = false;
					break;
				}
			}
		}
		return full;
	}

	/**
	 * @param p клетка
	 * @return true если клетка победная
	 */
    boolean isWinner(Piece p) {
		boolean won = false;
		
		if (hasHorizontalWin(p)) {
			won = true;
		} else if (hasVerticalWin(p)) {
			won = true;
		} else if (hasDiagonalWin(p)) {
			won = true;
		}
		return won;
	}
	
	private boolean hasHorizontalWin(Piece p) {
		boolean won = false;

		for (int i = 0; i <= SIZE - 5; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j]   == p &&
					board[i+1][j] == p &&
					board[i+2][j] == p &&
					board[i+3][j] == p &&
					board[i+4][j] == p) {
					won = true;
					break;
				}
			}
		}
		
		return won;
	}
	
	private boolean hasVerticalWin(Piece p) {
		boolean won = false;

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j <= SIZE - 5; j++) {
				if (board[i][j]   == p &&
					board[i][j+1] == p &&
					board[i][j+2] == p &&
					board[i][j+3] == p &&
					board[i][j+4] == p) {
					won = true;
					break;
				}
			}
		}
		
		return won;
	}
	
	private boolean hasDiagonalWin(Piece p) {
		boolean won = false;
		
		// Check the first diagonal
		for (int i = 0; i <= SIZE - 5; i++) {
			for (int j = 0; j <= SIZE - 5; j++) {
				if (board[i][j]   == p &&
					board[i+1][j+1] == p &&
					board[i+2][j+2] == p &&
					board[i+3][j+3] == p &&
					board[i+4][j+4] == p) {
					won = true;
					break;
				}
			}
		}
		
		// Проверка второй диагонали
		for (int i = 0; i <= SIZE - 5 ; i++) {  // Rows 0..10
			for (int j = 4; j < SIZE; j++) {    // Cols 4..14
				if (board[i][j]   == p &&
					board[i+1][j-1] == p &&
					board[i+2][j-2] == p &&
					board[i+3][j-3] == p &&
					board[i+4][j-4] == p) {
					won = true;
					break;
				}
			}
		}
		return won;
	}

} // конец класса GomokuBoard
