import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

class GomokuAI {

	// Константы
	private static final int MINMAX_DEPTH = 3;//4;
	
	// Поля
	private GomokuBoard.Piece computerPiece;

	
	/**
	 * Инициализация AI
	 * @param computerPiece символ компьютера (X или O)
	 */
	GomokuAI(GomokuBoard.Piece computerPiece) {

		this.computerPiece = computerPiece;

	}
	
	private class Result {
		private int score;
		
		ArrayList<GomokuGameNode> gameNodes;
		Result() {
			gameNodes = new ArrayList<>();
			this.score = 0;
		}
		
		Move getMove() {
			Move move = null;
			GomokuGameNode result = gameNodes.get(0);
			if (result != null) {
				move = result.getMove();
			}
			return move;
		}

		int getScore() {
			return score;
		}

		void setScore(int score) {
			this.score = score;
		}
		
		void add(GomokuGameNode node) {
			this.gameNodes.add(node);
		}

		void addAll(Collection<GomokuGameNode> c) {
			this.gameNodes.addAll(c);
		}

	}
	
	private Result minimaxHelper(GomokuGameNode node, int depth, int alpha, int beta) {
		Result result;
		
		Set<GomokuGameNode> childNodes = node.getChildren();
		
		for (GomokuGameNode child : childNodes) {
			if (child.isComputerWin()) {
				result = new Result();
				result.setScore(child.getObjectiveValue(computerPiece));
				result.add(child);
				return result;
			}
		}
		
		result = minimax(node, depth, alpha, beta, true);
		
		return result;
	}
	
	private Result minimax(GomokuGameNode node, int depth, int alpha, int beta, boolean needMax) {
		if (depth <= 0 || node.isTerminalNode()) {
			Result result = new Result();
			int score = node.getObjectiveValue(computerPiece);
			result.setScore(score);
			return result;
		}

		Result best = new Result();
		
		for (GomokuGameNode child : node.getChildren()) {
			Result result2 = minimax(child, depth-1, alpha, beta, !needMax);
			
			int score = result2.getScore();
			if (needMax) {  // Do Max
				if (score > alpha) {
					alpha = score;
					best = new Result();
					best.add(child);
					best.addAll(result2.gameNodes);
				}
				if (beta <= alpha) {
					break;
				}
			} else { // Do Min
				if (score < beta) {
					
					best = new Result();
					best.add(child);
					best.addAll(result2.gameNodes);
					
					beta = score;
				}
				if (beta <= alpha) {
					break;
				}
			}
		}
		int retval = needMax ? alpha : beta;		
		best.setScore(retval);
		return best;
	}
	
	/**
	 * Даны игровые ситуации, выбирает лучшую
	 * @param board игровая доска
	 * @return ход (row, col)
	 */
	Move getMove(GomokuBoard board) {
		Move move;

		GomokuGameNode node = new GomokuGameNode(board, computerPiece);
		
		// Вызывает Min Max
		Result result = this.minimaxHelper(node, MINMAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);

		move  = result.getMove();
		
		return move;
	}
} // конец класса GomokuAI
