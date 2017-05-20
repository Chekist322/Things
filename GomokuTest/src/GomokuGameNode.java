import java.util.HashSet;
import java.util.Set;

public class GomokuGameNode {


	private GomokuBoard board;
	private GomokuBoard.Piece currentPlayer;
	
	private int row;
	private int col;
	
	private GomokuBoard.Piece computerPiece;
	
	GomokuGameNode(GomokuBoard other, GomokuBoard.Piece currentPlayer) {
		this.board         = other.createCopy();
		this.currentPlayer = currentPlayer;
		this.computerPiece = currentPlayer;
	}
	
	private GomokuGameNode(GomokuBoard GomokuBoard, GomokuBoard.Piece currentPlayer, int row, int col) {
		this(GomokuBoard, currentPlayer);
		this.row = row;
		this.col = col;
	}


	Move getMove() {
		return new Move(row, col);
	}
	
	/**
	 * 
	 * @return true если на доске победа или ничья
	 */
	boolean isTerminalNode() {
		boolean terminal = false;
		
		if (this.board.isFull()) {
			terminal = true;
		} else if (this.board.isWinner(GomokuBoard.Piece.X)) {
			terminal = true;
		} else if (this.board.isWinner(GomokuBoard.Piece.O)) {
			terminal = true;
		}
		return terminal;
	}
	
	boolean isComputerWin() {
		return this.board.isWinner(computerPiece);
	}

	private GomokuBoard.Piece getNextPlayer() {
		if (this.currentPlayer == GomokuBoard.Piece.X) {
			return GomokuBoard.Piece.O;
		} else {
			return GomokuBoard.Piece.X;
		}
	}
	
	public String toString() {

		return "(" + row + "," + col + ")";
	}

	Set<GomokuGameNode> getChildren() {
		Set<GomokuGameNode> children = new HashSet<>();
		
		Set<Move> adjacentMoves = this.getAdjacentMoves();
		
		for (Move move : adjacentMoves) {
			// Создает новую доску с текущим возможным ходом
			GomokuBoard childBoard = board.createCopy();
			
			// Попытка добавить фишку на доску
			boolean success = childBoard.addPiece(move.getRow(), move.getCol(), currentPlayer);

			// Если неверный ход - пропуск
			if (!success) {
				continue;
			}
		
			// Хранит потомка дерева
			GomokuGameNode child = new GomokuGameNode(childBoard, getNextPlayer(), move.getRow(), move.getCol());
			children.add(child);
		}
		return children;
	}
	
	private Set<Move> getAdjacentMoves() {
		HashSet<Move> moves = new HashSet<>();
		
		for (int row = 0; row < GomokuBoard.SIZE; row++) {
			for (int col = 0; col < GomokuBoard.SIZE; col++) {
				if (!board.isOccupied(row, col)) {
					if (hasNeighbor(row, col)) {
						moves.add(new Move(row, col));
					}
				}
			}
		}
		return moves;
	}
	
	private boolean hasNeighbor(int row, int col) {
		boolean neighbor = false;
		
		if (board.isOccupied(row + 1, col - 1)  ||
			board.isOccupied(row + 1, col)  ||
			board.isOccupied(row + 1, col + 1)  ||

			board.isOccupied(row, col - 1)  ||
			board.isOccupied(row, col + 1)  ||

			board.isOccupied(row - 1, col - 1)  ||
			board.isOccupied(row - 1, col)  ||
			board.isOccupied(row - 1, col + 1)
			) { 
			neighbor = true;
		}
	
		return neighbor;
	}
	
	int getObjectiveValue(GomokuBoard.Piece computerPiece) {
		int objValue = 0;
		
		GomokuBoard.Piece humanPiece = GomokuBoard.Piece.X;
		
		if (computerPiece.equals(GomokuBoard.Piece.X)) {
			humanPiece = GomokuBoard.Piece.O;
		}
		
		if (board.isWinner(computerPiece)) {
			objValue = 100000;
		} else if (board.isWinner(humanPiece)) {
			objValue = -100000;
		} else {
			int numHumanFours = 0;
			int numComputerFours = 0;

			int numHumanThrees = 0;
			int numComputerThrees = 0;

			GomokuBoard.Piece p1, p2, p3, p4, p5, p6, p7;
			
			// Смотрит на существующую доску
			for (int i = 0; i < GomokuBoard.SIZE; i++) {
				for (int j = 0; j < GomokuBoard.SIZE; j++) {

					// Вертикальная линия
					p1 = board.getPiece(i, j);
					p2 = board.getPiece(i+1, j);
					p3 = board.getPiece(i+2, j);
					p4 = board.getPiece(i+3, j);
					p5 = board.getPiece(i+4, j);
					p6 = board.getPiece(i+5, j);
					p7 = board.getPiece(i+6, j);

					// Проверяет на 4 совпадения
					if (hasFourThreat(humanPiece, p1, p2, p3, p4, p5, p6)) {
						numHumanFours++;
					} else if (hasFourThreat(computerPiece, p1, p2, p3, p4, p5, p6)) {
						numComputerFours++;
					}
					
					// Проверяет на 3 совпадения
					if (hasThreeThreat(humanPiece, p1, p2, p3, p4, p5, p6, p7)) {
						numHumanThrees++;
					} else if (hasThreeThreat(computerPiece, p1, p2, p3, p4, p5, p6, p7)) {
						numComputerThrees++;
					}

					// Горизонтальная линия
					p1 = board.getPiece(i, j);
					p2 = board.getPiece(i, j+1);
					p3 = board.getPiece(i, j+2);
					p4 = board.getPiece(i, j+3);
					p5 = board.getPiece(i, j+4);
					p6 = board.getPiece(i, j+5);
					p7 = board.getPiece(i, j+6);
					
					// Проверяет на 4 совпадения
					if (hasFourThreat(humanPiece, p1, p2, p3, p4, p5, p6)) {
						numHumanFours++;
					} else if (hasFourThreat(computerPiece, p1, p2, p3, p4, p5, p6)) {
						numComputerFours++;
					}
					
					// Проверяет на 3 совпадения
					if (hasThreeThreat(humanPiece, p1, p2, p3, p4, p5, p6, p7)) {
						numHumanThrees++;
					} else if (hasThreeThreat(computerPiece, p1, p2, p3, p4, p5, p6, p7)) {
						numComputerThrees++;
					}
					
					// Диагональная линия A
					p1 = board.getPiece(i, j);
					p2 = board.getPiece(i+1, j+1);
					p3 = board.getPiece(i+2, j+2);
					p4 = board.getPiece(i+3, j+3);
					p5 = board.getPiece(i+4, j+4);
					p6 = board.getPiece(i+5, j+5);
					p7 = board.getPiece(i+6, j+6);

					// Проверяет на 4 совпадения
					if (hasFourThreat(humanPiece, p1, p2, p3, p4, p5, p6)) {
						numHumanFours++;
					} else if (hasFourThreat(computerPiece, p1, p2, p3, p4, p5, p6)) {
						numComputerFours++;
					}
					
					// Проверяет на 3 совпадения
					if (hasThreeThreat(humanPiece, p1, p2, p3, p4, p5, p6, p7)) {
						numHumanThrees++;
					} else if (hasThreeThreat(computerPiece, p1, p2, p3, p4, p5, p6, p7)) {
						numComputerThrees++;
					}
					
					// Диагональная линия B
					p1 = board.getPiece(i, j);
					p2 = board.getPiece(i+1, j-1);
					p3 = board.getPiece(i+2, j-2);
					p4 = board.getPiece(i+3, j-3);
					p5 = board.getPiece(i+4, j-4);
					p6 = board.getPiece(i+5, j-5);
					p7 = board.getPiece(i+6, j-6);
					

					// Проверяет на 4 совпадения
					if (hasFourThreat(humanPiece, p1, p2, p3, p4, p5, p6)) {
						numHumanFours++;
					} else if (hasFourThreat(computerPiece, p1, p2, p3, p4, p5, p6)) {
						numComputerFours++;
					}
					
					// Проверяет на 3 совпадения
					if (hasThreeThreat(humanPiece, p1, p2, p3, p4, p5, p6, p7)) {
						numHumanThrees++;
					} else if (hasThreeThreat(computerPiece, p1, p2, p3, p4, p5, p6, p7)) {
						numComputerThrees++;
					}
				}
			}

			objValue += numHumanFours  * -400;
			objValue += numHumanThrees * -300;
			objValue += numComputerFours  * 400;
			objValue += numComputerThrees * 300;				
		}

		return objValue;
	}

	private boolean hasFourThreat(GomokuBoard.Piece humanPiece, GomokuBoard.Piece p1, GomokuBoard.Piece p2,
								  GomokuBoard.Piece p3, GomokuBoard.Piece p4, GomokuBoard.Piece p5, GomokuBoard.Piece p6) {
		return isFour(humanPiece, p1, p2, p3, p4, p5) ||
			isStraightFour(humanPiece, p1, p2, p3, p4, p5, p6);
	}

	private boolean hasThreeThreat(GomokuBoard.Piece humanPiece, GomokuBoard.Piece p1, GomokuBoard.Piece p2,
								   GomokuBoard.Piece p3, GomokuBoard.Piece p4, GomokuBoard.Piece p5, GomokuBoard.Piece p6, GomokuBoard.Piece p7) {
		return isThree(humanPiece, p1, p2, p3, p4, p5, p6) ||
			isThree(humanPiece, p1, p2, p3, p4, p5, p6, p7) || 
			isBrokenThree(humanPiece, p1, p2, p3, p4, p5, p6);
	}
		
	/**
	 * Определяет если ситуация "четверки" определена как:
	 * Линия из 5 клеток состоит из:
	 * (4) ATTACKER, and (1) EMPTY
	 */	
	private boolean isFour(GomokuBoard.Piece p, GomokuBoard.Piece p1, GomokuBoard.Piece p2, GomokuBoard.Piece p3, GomokuBoard.Piece p4, GomokuBoard.Piece p5) {
		boolean four = false;
		
		int numPiece = 0;
		int numEmpty = 0;
		
		if (p1 == p) {
			numPiece++;
		} else if (p1 == GomokuBoard.Piece.EMPTY) {
			numEmpty++;
		}
		
		if (p2 == p) {
			numPiece++;
		} else if (p2 == GomokuBoard.Piece.EMPTY) {
			numEmpty++;
		}

		if (p3 == p) {
			numPiece++;
		} else if (p3 == GomokuBoard.Piece.EMPTY) {
			numEmpty++;
		}

		if (p4 == p) {
			numPiece++;
		} else if (p4 == GomokuBoard.Piece.EMPTY) {
			numEmpty++;
		}
		if (p5 == p) {
			numPiece++;
		} else if (p5 == GomokuBoard.Piece.EMPTY) {
			numEmpty++;
		}

		// Проверка для 4 атакующих и 1 пустой клетки
		// в любом порядке
		if (numPiece == 4 && numEmpty == 1) {
			four = true;
		}
		return four;
	}
	
	/**
	 * Определяет если ситуация "четверки" определена как:
	 * Линия из 7 клеток состоит из:
	 * EMPTY, ATTACKER, ATTACKER, ATTACKER, ATTACKER, EMPTY
	 */
	private boolean isStraightFour(GomokuBoard.Piece p, GomokuBoard.Piece p1, GomokuBoard.Piece p2, GomokuBoard.Piece p3, GomokuBoard.Piece p4, GomokuBoard.Piece p5, GomokuBoard.Piece p6) {
		return (p1 == GomokuBoard.Piece.EMPTY &&
			p2 == p && p3 == p && p4 == p && p5 == p &&
			p6 == GomokuBoard.Piece.EMPTY);
	}
	/**
	 * Определяет если ситуация "тройки" определена как:
	 * Линия из 7 клеток состоит из:
	 * EMPTY, EMPTY, ATTACKER, ATTACKER, ATTACKER, EMPTY, EMPTY
	 */
	private boolean isThree(GomokuBoard.Piece p, GomokuBoard.Piece p1, GomokuBoard.Piece p2, GomokuBoard.Piece p3, GomokuBoard.Piece p4, GomokuBoard.Piece p5, GomokuBoard.Piece p6, GomokuBoard.Piece p7) {
		boolean three = false;
		
		if (p1 == GomokuBoard.Piece.EMPTY && p2 == GomokuBoard.Piece.EMPTY &&
			p3 == p && p4 == p && p5 == p &&
			p6 == GomokuBoard.Piece.EMPTY && p7 == GomokuBoard.Piece.EMPTY) {
			three = true;
		}
		
		return three;
	}
	
	/**
	 * Определяет если ситуация "тройки" определена как:
	 * Линия из 6 клеток состоит из:
	 * EMPTY, ATTACKER, ATTACKER, ATTACKER, EMPTY, EMPTY или
	 * EMPTY, EMPTY, ATTACKER, ATTACKER, ATTACKER, EMPTY
	 */
	private boolean isThree(GomokuBoard.Piece p, GomokuBoard.Piece p1, GomokuBoard.Piece p2, GomokuBoard.Piece p3, GomokuBoard.Piece p4, GomokuBoard.Piece p5, GomokuBoard.Piece p6) {
		boolean three = false;
		
		if (p1 == GomokuBoard.Piece.EMPTY && p6 == GomokuBoard.Piece.EMPTY) {
			if ( 	(p2 == p && p3 == p && p4 == p && p5 == GomokuBoard.Piece.EMPTY) ||
					(p2 == GomokuBoard.Piece.EMPTY && p3 == p && p4 == p && p5 == p) ) {
				three = true;
			}
		}
		
		return three;
	}
	
	/**
	 * Определяет если ситуация "Разбитой тройки" определена как:
	 * На линии из 6 клеток присутствует 3 не соединенных с собой
	 * и соседние свободны:
	 * EMPTY, ATTACKER, ATTACKER, EMPTY, ATTACKER, EMPTY или
	 * EMPTY, ATTACKER, EMPTY, ATTACKER, ATTACKER, EMPTY
	 * @param p
	 * @return номер разбитой тройки
	 */
	private boolean isBrokenThree(GomokuBoard.Piece p, GomokuBoard.Piece p1, GomokuBoard.Piece p2, GomokuBoard.Piece p3, GomokuBoard.Piece p4, GomokuBoard.Piece p5, GomokuBoard.Piece p6) {
		boolean three = false;
		
		if (p1 == GomokuBoard.Piece.EMPTY && p6 == GomokuBoard.Piece.EMPTY) {
			if ( (p2 == p && p3 == p && p4 == GomokuBoard.Piece.EMPTY && p5 == p) ||
				 (p2 == p && p3 == GomokuBoard.Piece.EMPTY && p4 == p && p5 == p) ) {
				three = true;
			}
		}
		
		return three;
	}	
}	// конец класса GomokuGameNode
