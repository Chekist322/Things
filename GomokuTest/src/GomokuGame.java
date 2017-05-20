import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GomokuGame {

	private GomokuBoard board;
	
	public enum Player { HUMAN, COMPUTER }
		
	// Поля
	private BufferedReader input;
	
	private Player currentPlayer;
	private Player startPlayer;
	
	private GomokuBoard.Piece humanPiece;
	private GomokuBoard.Piece computerPiece;
	
	private GomokuAI ai;
	

	//Инициализация потока ввода и создание доски.
	private GomokuGame() {
		this.input = new BufferedReader(new InputStreamReader(System.in));
		this.board = new GomokuBoard();
	}		

	private int getChoice(String question) {
		String choice;
		int col;
		
		// Показать выбор
		System.out.print(question);
		
		// Читать ввод
		choice = getInput();
		System.out.println();
		
		try {
			col = Integer.parseInt(choice);
		} catch (NumberFormatException e) {
			System.out.println("Enter a valid value (0-14)");
			return getChoice(question);
		}

		if (col > 14 || col < 0){
			System.out.println("Enter a valid value (0-14)");
			return getChoice(question);
		}

		return col;
	}
	
	




	/**
	 * @return фишка пользователя
	 */
	private GomokuBoard.Piece getHumanPiece() {
		return humanPiece;
	}

	/**
	 * @return фишка компьютера
	 */

	private void initialize() {
		board.reset();
		if (getStartPlayer() == Player.HUMAN) {
			humanPiece    = GomokuBoard.Piece.X;
			computerPiece = GomokuBoard.Piece.O;
		} else {
			humanPiece    = GomokuBoard.Piece.O;
			computerPiece = GomokuBoard.Piece.X;

			board.addPiece(7, 7, computerPiece);
			this.switchPlayers();
		}
		ai = new GomokuAI(computerPiece);
		
		board.printBoard();

	}
	

	
	/**
	 * @return считанный ввод
	 */
	private String getInput() {
		String line = "";
		try {
			return this.input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;		
	}

	/**
	 * Спрашивает пользователя, будет ли он ходить первым
	 */
	private void askStartPlayer() {
		System.out.print("Do you want to go first? [y] ");
		
		String choice = this.getInput();

		if (choice.equalsIgnoreCase("y")) {
			startPlayer = Player.HUMAN;
		} else {
			startPlayer = Player.COMPUTER;
		}
		
		currentPlayer = startPlayer;
	}

	/**
	 * @return Получает начинавшего игрока.
	 */
	private Player getStartPlayer() {
		return startPlayer;
	}

	/**
	 * Передача хода
	*/
	private void switchPlayers() {
		if (currentPlayer == Player.COMPUTER) {
			currentPlayer = Player.HUMAN;
		} else {
			currentPlayer = Player.COMPUTER;
		}
	}
	
	/**
	 * Спрашивает пользователя о повторе сессии
	 * @return true если играть снова, false иначе
	 */
	private boolean askPlayAgain() {
		System.out.print("Do you want to play again? [y] ");
		String choice = this.getInput();
		return choice.equalsIgnoreCase("y");
	}

	/**
	 * Display the winner, or tie game.
	 */
	private void displayWinner() {
		if (isComputerWinner()) {
			System.out.println("Computer wins!");
		} else if (isHumanWinner()) {
			System.out.println("You win!");
		} else {
			System.out.println("Tie Game!");
		}
	}
	
	private void playComputerTurn() {
		// Получить ход компьютера из AI.
		Move move = ai.getMove(board);
		
		if (move != null) {
			System.out.println("Computer moves to " + move);
			board.addPiece(move.getRow(), move.getCol(), computerPiece);
		} else {
			System.out.println("move was null!");
		}
	}

	private void playHumanTurn() {
		// Получить выбор
		int row = getChoice("Which row? ");
		int col = getChoice("Which column? ");

		if (!board.addPiece(row, col, getHumanPiece())) {
			System.out.println("Enter a valid move");
			playHumanTurn();
		}

		System.out.println("You moved to (" + row + "," + col + ")" );

	}

	
	/**
	 * @return true если победа или ничья.
	 */
	private boolean isFinished() {
		boolean done = false;
		
		if (isComputerWinner() || isHumanWinner()) {
			done = true;
		} else if (board.isFull()) {
			done = true;
		}
		return done;
	}

	/**
	 * @return true если выиграл AI
	 */
	private boolean isComputerWinner() {
		return board.isWinner(computerPiece);
	}

	/**
	 * @return true если выиграл игрок
	 */
	private boolean isHumanWinner() {
		return board.isWinner(humanPiece);
	}
	
	/**
	 * Запускает главный цикл
	 */
	private void mainLoop() {
		do {
			askStartPlayer();			
			initialize();

			// Проверяет игру на окончание
			while (!isFinished()) {				
				if (currentPlayer.equals(Player.HUMAN)) {
					playHumanTurn();
				} else {
					playComputerTurn();
				}				
				
				// Отображение доски
				board.printBoard();
				
				// Переключение игроков
				switchPlayers();
			}
			// Отображение победителя
			displayWinner();
		} while (askPlayAgain());
	}
	
	/**
	 * Главный метод запуска
	 * @param args не используется
	 */
	public static void main(String [] args) {
		GomokuGame game = new GomokuGame();
		game.mainLoop();
	}
} // конец класса GomokeGame
