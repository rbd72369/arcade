package cs1302.arcade;

import java.util.Scanner;

/**
 * This class represents a Reversi game.
 *
 * @author Timothy Xu <thx35333@uga.edu>
 */
public class ReversiOld extends Board {
	public int scoreX = 0;
	public int scoreO = 0;
	/**
	 * Constructs an object instance of the Reversi class using the
	 * <code>player</code> and <code>player</code> values.
	 * 
	 * @param player1
	 *            A player of type ComputerPLayer, HumanPlayer,
	 *            RandomComputerPLayer, or IntelligentComputerPlayer
	 * @param player2
	 *            A player of type ComputerPLayer, HumanPlayer,
	 *            RandomComputerPlayer, or IntelligentComputerPlayer.
	 */
	public ReversiOld(Player player1, Player player2) {
		// created a letterArray and array for dark/white pieces.
		String[][] letterArray = new String[8][8];

		// fills the letter array with . values (meaning none of the spaces have pieces)
		for (int row = 0; row < letterArray.length; row++) {
			for (int col = 0; col < letterArray[row].length; col++) {
				letterArray[row][col] = ".";
			}
		}

		// sets the middle square as XOXO. Even though it says [4, 4] on the grid it's
		// really [3, 3]
		letterArray[3][3] = "X";
		letterArray[3][4] = "O";
		letterArray[4][3] = "O";
		letterArray[4][4] = "X";

		this.letterArray = letterArray;
		System.out.println("\nWelcome to Reversi!  Moves should be entered in \"[row] [column]\" format.");
	
//		run(player1, player2);
	}

	/**
	 * Starts the game and execute the game loop. It will start the game loop
	 * regardless of the parameter type. Whether it be two players, two computers,
	 * or one player and one computer.
	 * 
	 * @param player1
	 *            First player
	 * @param player2
	 *            Second player
	 */
	public void run(Player player1, Player player2) {
		if (player1 instanceof HumanPlayer && player2 instanceof HumanPlayer) {
			while (true) {
				getPossibleMoves(player1); // shows possible moves
				checkEndGame(); // checks updated board to see if game should end
				//printBoard(); // prints board
				promptTurn(player1); // prompts player 1's turn and reads input
				clearPossibleMoves(); // clears board of possible moves after a player's turn

				getPossibleMoves(player2); // shows player 2's possible moves
				checkEndGame(); // checks updated board to see if game should end
				//printBoard(); // prints board
				promptTurn(player2); // prompts player 2's turn and reads input
				clearPossibleMoves(); // clears possible moves after turn
			}
		}
	}

	/**
	 * Reads user input.
	 * 
	 * @param Player
	 *            the type of Player of the game
	 */
	public void promptTurn(Player player) {
		Scanner scan = new Scanner(System.in);
		// displays message depending on whether it is a dark or light player
		if (player.white == true) {
			System.out.print("\nEnter your move, O player: ");
		} else if (player.white == false) {
			System.out.print("\nEnter your move, X player: ");
		}
		if (scan.hasNextInt() == true && scan.hasNextInt() == true) { // if it has two ints it will scan
			int row = scan.nextInt(); // row integer
			int col = scan.nextInt(); // col integer
			if (isInBounds(row - 1, col - 1) == false) { // prints out of bounds message
				System.out.println("Error, out of bounds.");
				//printBoard();
				promptTurn(player); // restarts turn
			} else if (!letterArray[row - 1][col - 1].equals("_")) { // if it doesn't equal "_" print an error
				System.out.println("Error, not a possible move.");
				//printBoard();
				promptTurn(player); // restarts turn
			} else {
				if (player.white == true) { // puts a white piece down
					setLightPiece(row - 1, col - 1);
					flipPieces(row - 1, col - 1);
				} else if (player.white == false) { // puts a dark piece down
					setDarkPiece(row - 1, col - 1);
					flipPieces(row - 1, col - 1);
				}
			}
		} else {
			System.out.println("Error, invalid command. Try again!");
		}
	}

	/**
	 * Checks to see if the game should end. If the board is full or if there are no
	 * more available moves the game should end.
	 */
	public void checkEndGame() {
		boolean canMakeAMove = false; // false: can't make a move. True: can make a move
		int numAvailableSpaces = 64;
		for (int row = 0; row < 8; row++) { // checks number of available spaces. 64 possible spaces. If board is full
											// numAvailable will be 0
			for (int col = 0; col < 8; col++) {
				if (letterArray[row][col].equals("X") || letterArray[row][col].equals("O")) {
					numAvailableSpaces--;
				}
			}
		}
		for (int row = 0; row < 8; row++) { // if there is a possible move on the board, canMakeAMove will be true
			for (int col = 0; col < 8; col++) {
				if (letterArray[row][col].equals("_")) {
					canMakeAMove = true;
				}
			}
		}
		if (numAvailableSpaces == 0 || canMakeAMove == false) { // if the winning conditions have been met, the user
																// wins
			endGame();
		}
	}

	/**
	 *  UPDATES SCORE
	 */
	public void endGame() {
		int scoreX = 0;
		int scoreO = 0;
		String winner = "";
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (letterArray[row][col].equals("X")) {
					scoreX++;
				}
				if (letterArray[row][col].equals("O")) {
					scoreO++;
				}
			}
		}
		if (scoreX > scoreO) {
			winner = "X Player";
		} else {
			winner = "O Player";
		}
		//printBoard();
//		if (scoreX == scoreO) {
//			System.out.println("\nIt's a tie!\n\nX Score: " + scoreX + "\tO Score: " + scoreO);
//		} else {
//			System.out.println("\n" + winner + " is the winner!\n\nX Score: " + scoreX + "\tO Score: " + scoreO);
//		}
		this.scoreX = scoreX;
		this.scoreO = scoreO;
		//System.exit(0);
	}

	/**
	 * Shows possible moves for the player.
	 * 
	 * @param player
	 *            The player with his respective possible moves.
	 */
	public void getPossibleMoves(Player player) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				northPossibleMoves(row, col, player);
				northEastPossibleMoves(row, col, player);
				eastPossibleMoves(row, col, player);
				southEastPossibleMoves(row, col, player);
				southPossibleMoves(row, col, player);
				southWestPossibleMoves(row, col, player);
				westPossibleMoves(row, col, player);
				northWestPossibleMoves(row, col, player);
			}
		}
	}

	/**
	 * Indicates whether or not the square above is a possible move.
	 *
	 * @param row
	 *            the row index of the square
	 * @param col
	 *            the column index of the square
	 * @param player
	 *            the player's possible moves
	 */
	public void northPossibleMoves(int row, int col, Player player) {
		String oppPiece = "";
		String yourPiece = "";
		if (player.white == true) {
			oppPiece = "O";
			yourPiece = "X";
		} else if (player.white == false) {
			oppPiece = "X";
			yourPiece = "O";
		}
		if (isInBounds(row + 1, col) && letterArray[row][col].equals(oppPiece)
				&& letterArray[row + 1][col].equals(yourPiece)) { // checks +1
			while (row < 8 && row >= 0) { // checks through every row // changed it
				if (letterArray[row][col] == oppPiece) { // finds if the opposing piece is there
					row--; // keeps on checking
				} else if (letterArray[row][col] == ".") { // finds an empty space
					letterArray[row][col] = "_"; // marks a possible move on the board
					row = 8; // stops checking
				} else {
					row = 8; // finds a piece on the same team, stops checking
				}
			}
		} else {
			return;
		}
	}

	/**
	 * Indicates whether or not the square below is a possible move.
	 *
	 * @param row
	 *            the row index of the square
	 * @param col
	 *            the column index of the square
	 * @param player
	 *            the player's possible moves
	 */
	public void southPossibleMoves(int row, int col, Player player) {
		String oppPiece = "";
		String yourPiece = "";
		if (player.white == true) {
			oppPiece = "O";
			yourPiece = "X";
		} else if (player.white == false) {
			oppPiece = "X";
			yourPiece = "O";
		}
		if (isInBounds(row - 1, col) && letterArray[row][col].equals(oppPiece)
				&& letterArray[row - 1][col].equals(yourPiece)) { // changed it
			while (row < 8) { // checks through every row
				if (letterArray[row][col] == oppPiece) { // finds if the opposing piece is there
					row++; // keeps on checking
				} else if (letterArray[row][col] == ".") { // finds an empty space
					letterArray[row][col] = "_"; // marks a possible move on the board
					row = 8; // stops checking
				} else {
					row = 8; // finds a piece on the same team, stops checking
				}
			}
		} else {
			return;
		}
	}

	/**
	 * Indicates whether or not the square to the right is a possible move.
	 *
	 * @param row
	 *            the row index of the square
	 * @param col
	 *            the column index of the square
	 * @param player
	 *            the player's possible moves
	 */
	public void eastPossibleMoves(int row, int col, Player player) {
		String oppPiece = "";
		String yourPiece = "";
		if (player.white == true) {
			oppPiece = "O";
			yourPiece = "X";
		} else if (player.white == false) {
			oppPiece = "X";
			yourPiece = "O";
		}
		if (isInBounds(row, col - 1) && letterArray[row][col].equals(oppPiece)
				&& letterArray[row][col - 1].equals(yourPiece)) { // changed it
			while (col < 8 && col >= 0) { // checks through every row // changed
				if (letterArray[row][col] == oppPiece) { // finds if the opposing piece is there
					col++; // keeps on checking
				} else if (letterArray[row][col] == ".") { // finds an empty space
					letterArray[row][col] = "_"; // marks a possible move on the board
					col = 8; // stops checking
				} else {
					col = 8; // finds a piece on the same team, stops checking
				}
			}
		} else {
			return;
		}
	}

	/**
	 * Indicates whether or not the square to the left is a possible move.
	 *
	 * @param row
	 *            the row index of the square
	 * @param col
	 *            the column index of the square
	 * @param player
	 *            the player's possible moves
	 */
	public void westPossibleMoves(int row, int col, Player player) {
		String oppPiece = "";
		String yourPiece = "";
		if (player.white == true) {
			oppPiece = "O";
			yourPiece = "X";
		} else if (player.white == false) {
			oppPiece = "X";
			yourPiece = "O";
		}
		if (isInBounds(row, col + 1) && letterArray[row][col].equals(oppPiece)
				&& letterArray[row][col + 1].equals(yourPiece)) { // changed it
			while (col < 8 && col >= 0) { // checks through every row //changed it
				if (letterArray[row][col] == oppPiece) { // finds if the opposing piece is there
					col--; // keeps on checking
				} else if (letterArray[row][col] == ".") { // finds an empty space
					letterArray[row][col] = "_"; // marks a possible move on the board
					col = 8; // stops checking
				} else {
					col = 8; // finds a piece on the same team, stops checking
				}
			}
		} else {
			return;
		}
	}

	/**
	 * Indicates whether or not the square to the northeast is a possible move.
	 *
	 * @param row
	 *            the row index of the square
	 * @param col
	 *            the column index of the square
	 * @param player
	 *            the player's possible moves
	 */
	public void northEastPossibleMoves(int row, int col, Player player) {
		String oppPiece = "";
		String yourPiece = "";
		if (player.white == true) {
			oppPiece = "O";
			yourPiece = "X";
		} else if (player.white == false) {
			oppPiece = "X";
			yourPiece = "O";
		}

		if (isInBounds(row + 1, col - 1) && letterArray[row][col].equals(oppPiece)
				&& letterArray[row + 1][col - 1].equals(yourPiece)) { // changed it
			while (col < 8 && row < 8 && row >= 0) { // checks through every row // changed it
				if (letterArray[row][col] == oppPiece) { // finds if the opposing piece is there
					col++; // keeps on checking
					row--;
				} else if (letterArray[row][col] == ".") { // finds an empty space
					letterArray[row][col] = "_"; // marks a possible move on the board
					col = 8; // stops checking
				} else {
					col = 8; // finds a piece on the same team, stops checking
				}
			}
		} else {
			return;
		}
	}

	/**
	 * Indicates whether or not the square to the northwest is a possible move.
	 *
	 * @param row
	 *            the row index of the square
	 * @param col
	 *            the column index of the square
	 * @param player
	 *            the player's possible moves
	 */
	public void northWestPossibleMoves(int row, int col, Player player) {
		String oppPiece = "";
		String yourPiece = "";
		if (player.white == true) {
			oppPiece = "O";
			yourPiece = "X";
		} else if (player.white == false) {
			oppPiece = "X";
			yourPiece = "O";
		}

		if (isInBounds(row + 1, col + 1) && letterArray[row][col].equals(oppPiece)
				&& letterArray[row + 1][col + 1].equals(yourPiece)) { // changed it
			while (col < 8 && row < 8 && col >= 0 && row >= 0) { // checks through every row // changed it
				if (letterArray[row][col] == oppPiece) { // finds if the opposing piece is there
					row--; // keeps on checking
					col--;
				} else if (letterArray[row][col] == ".") { // finds an empty space
					letterArray[row][col] = "_"; // marks a possible move on the board
					col = 8; // stops checking
				} else {
					col = 8; // finds a piece on the same team, stops checking
				}
			}
		} else {
			return;
		}
	}

	/**
	 * Indicates whether or not the square to the southeast is a possible move.
	 *
	 * @param row
	 *            the row index of the square
	 * @param col
	 *            the column index of the square
	 * @param player
	 *            the player's possible moves
	 */
	public void southEastPossibleMoves(int row, int col, Player player) {
		String oppPiece = "";
		String yourPiece = "";
		if (player.white == true) {
			oppPiece = "O";
			yourPiece = "X";
		} else if (player.white == false) {
			oppPiece = "X";
			yourPiece = "O";
		}

		if (isInBounds(row - 1, col - 1) && letterArray[row][col].equals(oppPiece)
				&& letterArray[row - 1][col - 1].equals(yourPiece)) { // changed it
			while (col < 8 && row < 8) { // checks through every row
				if (letterArray[row][col] == oppPiece) { // finds if the opposing piece is there
					col++; // keeps on checking
					row++;
				} else if (letterArray[row][col] == ".") { // finds an empty space
					letterArray[row][col] = "_"; // marks a possible move on the board
					col = 8; // stops checking
				} else {
					col = 8; // finds a piece on the same team, stops checking
				}
			}
		} else {
			return;
		}
	}

	/**
	 * Indicates whether or not the square to the southwest is a possible move.
	 *
	 * @param row
	 *            the row index of the square
	 * @param col
	 *            the column index of the square
	 * @param player
	 *            the player's possible moves
	 */
	public void southWestPossibleMoves(int row, int col, Player player) {
		String oppPiece = "";
		String yourPiece = "";
		if (player.white == true) {
			oppPiece = "O";
			yourPiece = "X";
		} else if (player.white == false) {
			oppPiece = "X";
			yourPiece = "O";
		}

		if (isInBounds(row - 1, col + 1) && letterArray[row][col].equals(oppPiece)
				&& letterArray[row - 1][col + 1].equals(yourPiece)) { // changed it
			while (col < 8 && col >= 0 && row < 8) { // checks through every row // changed it
				if (letterArray[row][col] == oppPiece) { // finds if the opposing piece is there
					row++; // keeps on checking
					col--;
				} else if (letterArray[row][col] == ".") { // finds an empty space
					letterArray[row][col] = "_"; // marks a possible move on the board
					col = 8; // stops checking
				} else {
					col = 8; // finds a piece on the same team, stops checking
				}
			}
		} else {
			return;
		}
	}

	/**
	 * Flips pieces. Use this method after making a move to flip the corresponding
	 * necessary pieces.
	 * 
	 * @param i
	 *            Row of piece
	 * @param j
	 *            Col of piece
	 */
	public void flipPieces(int i, int j) {
		// FOR DARK PIECES
		if (isInBounds(i, j) && letterArray[i][j].equals("X")) {
			// North
			if (isInBounds(i - 1, j) && letterArray[i - 1][j].equals("O")) {
				int m = i - 2;
				while (isInBounds(m, j) && letterArray[m][j].equals("O")) {
					m--;
				}
				if (isInBounds(m, j) && (letterArray[m][j].equals("X") || letterArray[m][j].equals("O"))) {

					while (isInBounds(m, j) && (letterArray[m][j].equals("X") || letterArray[m][j].equals("O"))) {
						setLightPiece(m, j);
						m++;
					}
					setLightPiece(i - 1, j);
				}
			}
			// Northeast
			if (isInBounds(i - 1, j + 1) && letterArray[i - 1][j + 1].equals("O")) {
				int m = i - 2;
				int n = j + 2;
				while (isInBounds(m, n) && letterArray[m][n].equals("O")) {
					m--;
					n++;
				}
				if (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
					while (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
						setLightPiece(m, n);
						m++;
						n--;
					}
					setLightPiece(i - 1, j + 1);
				}
			}
			// East
			if (isInBounds(i, j + 1) && letterArray[i][j + 1].equals("O")) {
				int n = j + 2;
				while (isInBounds(i, n) && letterArray[i][n].equals("O")) {
					n++;
				}
				if (isInBounds(i, n) && (letterArray[i][n].equals("X") || letterArray[i][n].equals("O"))) {
					while (isInBounds(i, n) && (letterArray[i][n].equals("X") || letterArray[i][n].equals("O"))) {
						setLightPiece(i, n);
						n--;
					}
					setLightPiece(i, j + 1);
				}
			}
			// Southeast
			if (isInBounds(i + 1, j + 1) && letterArray[i + 1][j + 1].equals("O")) {
				int m = i + 2;
				int n = j + 2;
				while (isInBounds(m, n) && letterArray[m][n].equals("O")) {
					m++;
					n++;
				}
				if (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
					while (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
						setLightPiece(m, n);
						m--;
						n--;
					}
					setLightPiece(i + 1, j + 1);
				}
			}
			// South
			if (isInBounds(i + 1, j) && letterArray[i + 1][j].equals("O")) {
				int m = i + 2;
				while (isInBounds(m, j) && letterArray[m][j].equals("O")) {
					m++;
				}
				if (isInBounds(m, j) && (letterArray[m][j].equals("X") || letterArray[m][j].equals("O"))) {

					while (isInBounds(m, j) && (letterArray[m][j].equals("X") || letterArray[m][j].equals("O"))) {
						setLightPiece(m, j);
						m--;
					}
					setLightPiece(i + 1, j);
				}
			}
			// Southwest
			if (isInBounds(i + 1, j - 1) && letterArray[i + 1][j - 1].equals("O")) {
				int m = i + 2;
				int n = j - 2;
				// updateGrid(" X", i + 1, j - 1);
				while (isInBounds(m, n) && letterArray[m][n].equals("O")) {
					m++;
					n--;
				}
				if (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
					while (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
						setLightPiece(m, n);
						m--;
						n++;
					}
					setLightPiece(i + 1, j - 1);
				}
			}
			// West
			if (isInBounds(i, j - 1) && letterArray[i][j - 1].equals("O")) {
				int n = j - 2;
				while (isInBounds(i, n) && letterArray[i][n].equals("O")) {
					n--;
				}
				if (isInBounds(i, n) && (letterArray[i][n].equals("X") || letterArray[i][n].equals("O"))) {

					while (isInBounds(i, n) && (letterArray[i][n].equals("X") || letterArray[i][n].equals("O"))) {
						setLightPiece(i, n);
						n++;
					}
					setLightPiece(i, j - 1);
				}
			}
			// Northeast
			if (isInBounds(i - 1, j - 1) && letterArray[i - 1][j - 1].equals("O")) {
				int m = i - 2;
				int n = j - 2;
				while (isInBounds(m, n) && letterArray[m][n].equals("O")) {
					m--;
					n--;
				}
				if (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
					while (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
						setLightPiece(m, n);
						m++;
						n++;
					}
					setLightPiece(i - 1, j - 1);
				}
			}
			setLightPiece(i, j);
		} else { // FOR LIGHT PIECES
			// North
			if (isInBounds(i - 1, j) && letterArray[i - 1][j].equals("X")) {
				int m = i - 2;
				while (isInBounds(m, j) && letterArray[m][j].equals("X")) {
					m--;
				}
				if (isInBounds(m, j) && (letterArray[m][j].equals("X") || letterArray[m][j].equals("O"))) {
					while (isInBounds(m, j) && (letterArray[m][j].equals("X") || letterArray[m][j].equals("O"))) {
						setDarkPiece(m, j);
						m++;
					}
					setDarkPiece(i - 1, j);
				}
			}
			// Northeast
			if (isInBounds(i - 1, j + 1) && letterArray[i - 1][j + 1].equals("X")) {
				int m = i - 2;
				int n = j + 2;
				while (isInBounds(m, n) && letterArray[m][n].equals("X")) {
					m--;
					n++;
				}
				if (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
					while (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
						setDarkPiece(m, n);
						m++;
						n--;
					}
					setDarkPiece(i - 1, j + 1);
				}
			}
			// East
			if (isInBounds(i, j + 1) && letterArray[i][j + 1].equals("X")) {
				int n = j + 2;
				while (isInBounds(i, n) && letterArray[i][n].equals("X")) {
					n++;
				}
				if (isInBounds(i, n) && (letterArray[i][n].equals("X") || letterArray[i][n].equals("O"))) {
					while (isInBounds(i, n) && (letterArray[i][n].equals("X") || letterArray[i][n].equals("O"))) {
						setDarkPiece(i, n);
						n--;
					}
					setDarkPiece(i, j + 1);
				}
			}
			// Southeast
			if (isInBounds(i + 1, j + 1) && letterArray[i + 1][j + 1].equals("X")) {
				int m = i + 2;
				int n = j + 2;
				while (isInBounds(m, n) && letterArray[m][n].equals("X")) {
					m++;
					n++;
				}
				if (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
					while (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
						setDarkPiece(m, n);
						m--;
						n--;
					}
					setDarkPiece(i + 1, j + 1);
				}
			}
			// South
			if (isInBounds(i + 1, j) && letterArray[i + 1][j].equals("X")) {
				int m = i + 2;
				while (isInBounds(m, j) && letterArray[m][j].equals("X")) {
					m++;
				}
				if (isInBounds(m, j) && (letterArray[m][j].equals("X") || letterArray[m][j].equals("O"))) {
					while (isInBounds(m, j) && (letterArray[m][j].equals("X") || letterArray[m][j].equals("O"))) {
						setDarkPiece(m, j);
						m--;
					}
					setDarkPiece(i + 1, j);
				}
			}
			// Southwest
			if (isInBounds(i + 1, j - 1) && letterArray[i + 1][j - 1].equals("X")) {
				int m = i + 2;
				int n = j - 2;
				while (isInBounds(m, n) && letterArray[m][n].equals("X")) {
					m++;
					n--;
				}
				if (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
					while (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
						setDarkPiece(m, n);
						m--;
						n++;
					}
					setDarkPiece(i + 1, j - 1);
				}
			}
			// West
			if (isInBounds(i, j - 1) && letterArray[i][j - 1].equals("X")) {
				int n = j - 2;
				while (isInBounds(i, n) && letterArray[i][n].equals("X")) {
					n--;
				}
				if (isInBounds(i, n) && (letterArray[i][n].equals("X") || letterArray[i][n].equals("O"))) {

					while (isInBounds(i, n) && (letterArray[i][n].equals("X") || letterArray[i][n].equals("O"))) {
						setDarkPiece(i, n);
						n++;
					}
					setDarkPiece(i, j - 1);
				}
			}
			// Northwest
			if (isInBounds(i - 1, j - 1) && letterArray[i - 1][j - 1].equals("X")) {
				int m = i - 2;
				int n = j - 2;
				while (isInBounds(m, n) && letterArray[m][n].equals("X")) {
					m--;
					n--;
				}
				if (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
					while (isInBounds(m, n) && (letterArray[m][n].equals("X") || letterArray[m][n].equals("O"))) {
						setDarkPiece(m, n);
						m++;
						n++;
					}
					setDarkPiece(i - 1, j - 1);
				}
			}
			setDarkPiece(i, j);
		}
	}

	/**
	 * Clears possible moves for the player. Should be used after his turn to clear
	 * the board for the next player.
	 * 
	 */
	public void clearPossibleMoves() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (letterArray[row][col].equals("_")) {
					letterArray[row][col] = ".";
				}
			}
		}
	}

	/**
	 * Indicates whether or not the square is in the game grid.
	 *
	 * @param row
	 *            the row index of the square
	 * @param col
	 *            the column index of the square
	 * @return true if the square is in the game grid; false otherwise
	 */
	public boolean isInBounds(int row, int col) {
		return (row < 8 && row >= 0 && col >= 0 && col < 8);
	}

	public static void main(String[] args) {
//		Player humanPlayer1 = new HumanPlayer();
//		Player humanPlayer2 = new HumanPlayer();
//		humanPlayer1.white = false;
//		humanPlayer2.white = true;
//
//		ReversiOld game = new ReversiOld(humanPlayer1, humanPlayer2);

	} // main
}
