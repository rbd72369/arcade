package cs1302.arcade;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *                                                                                                                  
 * This class runs the <code>Reversi.java</code> program.              
 *                                                                                                                     
 * @author Timothy Xu <thx35333@uga.edu>   Rory Donahue <rbd72369@uga.edu>                                                                      
 */
public class Reversi {
	int possMoves = 1;
	int whiteVal = 0;
	int blackVal = 0;
	Label whiteScore = new Label("White: " + whiteVal);
	Label blackScore = new Label("Black: " + blackVal);
	Label turn = new Label("It's White's turn!");
	GridPane board = new GridPane();
	Rectangle[] grid = new Rectangle[64];
	Label black = new Label("Black Move");
	Label white = new Label("White Move");

	ReversiOld r;
	String[][] boardA;
	Player humanDark = new HumanPlayer(); // BLACk
	Player humanWhite = new HumanPlayer(); // WHITE

	String[][] boardTracker = new String[8][8];
	Rectangle possibleMoves = new Rectangle(50, 50, Color.GREY);
	boolean gameWon = false;
	HBox score = new HBox(10);
	VBox group = new VBox();

	/**
	 * Constructor for Reversi.
	 */
	public Reversi() {
		Player humanDark = new HumanPlayer(); // BLACk
		Player humanWhite = new HumanPlayer(); // WHITE
		humanDark.white = false; // BLACK
		humanWhite.white = true; // WHITE
		ReversiOld r = new ReversiOld(humanDark, humanWhite);
		String[][] boardA = r.letterArray;

		this.humanDark = humanDark;
		this.humanWhite = humanWhite;
		this.r = r;
		this.boardA = boardA;
	}

	/**
	 * Starts the game.
	 */
	public void game() {
		GridPane board = new GridPane();
		Rectangle[] grid = new Rectangle[64];
		int count = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				grid[count] = new Rectangle(50, 50, Color.GREEN);
				grid[count].setStroke(Color.BLACK);
				GridPane.setConstraints(grid[count], i, j);
				board.getChildren().addAll(grid[count]);
				count++;
			}
		}
		score.getChildren().add(whiteScore);
		score.getChildren().add(blackScore);
		score.getChildren().add(turn);
		group.getChildren().add(score);

		whitePiece(board, 3, 3);
		blackPiece(board, 4, 3);
		blackPiece(board, 3, 4);
		whitePiece(board, 4, 4);

		getWhiteMoves(board); // first move

		group.getChildren().addAll(board);
		Scene scene = new Scene(group, 408, 425, Color.GREEN);
		Stage stage = new Stage();
		stage.setTitle("Reversi");
		stage.setResizable(false);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();

	}// start

	/** 
	 * Sets the white piece at the specified row and column.
	 * @param board  The board
	 * @param row    Row of piece
	 * @param col	Col of piece
	 */
	public void whitePiece(GridPane board, int row, int col) {
		Circle white = new Circle(25, Color.WHITE);
		board.setConstraints(white, col, row);
		board.getChildren().addAll(white);
	}

	/**
	 * Sets the black piece at the specified row and col.
	 * @param board The board
	 * @param row	Row of piece
	 * @param col	Col of piece
	 */
	public void blackPiece(GridPane board, int row, int col) {
		Circle black = new Circle(25, Color.BLACK);
		board.setConstraints(black, col, row);
		board.getChildren().addAll(black);
	}

	/**
	 * Gets the node at the specified row/col.
	 * @param gridPane 	Gridpane that holds everything
	 * @param col		Col of desired location
	 * @param row		Row of desired location
	 * @return
	 */
	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
		for (Node node : gridPane.getChildren()) {
			if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Gets the rectangle at the specified row/col.
	 * @param gridPane 	Gridpane that holds everything
	 * @param col		Col of desired location
	 * @param row		Row of desired location
	 * @return
	 */
	private Rectangle getRectangleFromGridPane(GridPane gridPane, int col, int row) {
		return (Rectangle) getNodeFromGridPane(gridPane, col, row);
	}

	/**
	 * Checks the score and updates the values into the GUI.
	 */
	public void checkScore() {
		r.endGame();
		whiteVal = r.scoreX;
		blackVal = r.scoreO;
		whiteScore.setText("White: " + whiteVal);
		blackScore.setText("Black: " + blackVal);
		
		if (blackVal + whiteVal == 64) {
			Alert winner = new Alert(AlertType.INFORMATION);
			winner.setTitle("WINNER!");
			winner.setHeaderText(null);
			if (blackVal < whiteVal) {
				winner.setContentText("White Wins");
			}
			if (blackVal > whiteVal) {
				winner.setContentText("Black Wins");
			}
			if (blackVal != 0 && whiteVal != 0 && blackVal == whiteVal) {
				winner.setContentText("Tie game. You guys are bad.");
			}
			winner.showAndWait();
		}
		if (possMoves == 0) {
			Alert winner = new Alert(AlertType.INFORMATION);
			winner.setTitle("WINNER!");
			winner.setHeaderText(null);
			if (blackVal < whiteVal) {
				winner.setContentText("White Wins");
			}
			if (blackVal > whiteVal) {
				winner.setContentText("Black Wins");
			}
			if (blackVal != 0 && whiteVal != 0 && blackVal == whiteVal) {
				winner.setContentText("Tie game. You guys are bad.");
			}
			winner.showAndWait();
		}
	}

	/**
	 * Gets the possible white moves.
	 * @param board   Game board
	 */
	public void getWhiteMoves(GridPane board) {
		turn.setText("It's White's turn!");
		r.getPossibleMoves(humanWhite);
		canMakeAMove(board);
		checkScore();
		int count = 0;
		for (int rows = 0; rows < 8; rows++) {
			for (int cols = 0; cols < 8; cols++) {
				if (boardA[rows][cols].equals("_")) {
					getRectangleFromGridPane(board, cols, rows).setFill(Color.GREY);

					getRectangleFromGridPane(board, cols, rows).setOnMouseClicked(new EventHandler<MouseEvent>() {

						public void handle(MouseEvent me) {
							Rectangle rec = (Rectangle) me.getSource();
							int row = ((GridPane) rec.getParent()).getRowIndex(rec);
							int col = ((GridPane) rec.getParent()).getColumnIndex(rec);

							if (getRectangleFromGridPane(board, col, row).getFill().equals(Color.GREY)) {
								r.setLightPiece(row, col); // new
								r.flipPieces(row, col); // new
								
								r.clearPossibleMoves(); // ! new
								boardA = r.letterArray;
								move(board);
								reset(board);
								getBlackMoves(board);

							}
						}
					});
					count++;

				} // if
			} // for
		} // for
		if (count == 0) {
			getBlackMoves(board);
		}
	}

	/**
	 * Gets the possible black moves.
	 * @param board Game Board
	 */
	public void getBlackMoves(GridPane board) {
		turn.setText("It's Black's turn!");
		r.getPossibleMoves(humanDark);
		canMakeAMove(board);
		checkScore();
		int count = 0;
		for (int rows = 0; rows < 8; rows++) {
			for (int cols = 0; cols < 8; cols++) {
				if (boardA[rows][cols].equals("_")) {
					Rectangle rec = (Rectangle) getNodeFromGridPane(board, cols, rows);
					rec.setFill(Color.GREY);
					rec.setOnMouseClicked(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent me) {
							Rectangle rec = (Rectangle) me.getSource();
							int row = ((GridPane) rec.getParent()).getRowIndex(rec);
							int col = ((GridPane) rec.getParent()).getColumnIndex(rec);

							if (getRectangleFromGridPane(board, col, row).getFill().equals(Color.GREY)) {
								r.setDarkPiece(row, col); // new
								r.flipPieces(row, col); // new

								r.clearPossibleMoves(); // ! new
								boardA = r.letterArray;
								move(board);
								reset(board);
								getWhiteMoves(board);
							}

						}
					});
				} // if
				count++;

			} // for
		} // for
		if (count == 0) {
			getWhiteMoves(board);
		}

	}
	
	/**
	 * Checks whether the player can make a move
	 * @param board Game Board
	 */
	public void canMakeAMove(GridPane board) {
		possMoves = 0;
		for (int row = 0; row < 8; row++) { // if there is a possible move on the board, canMakeAMove will be true
			for (int col = 0; col < 8; col++) {
				if (boardA[row][col].equals("_")) {
					possMoves++;
				}
			}
		}
	}

	/**
	 * Resets the board/empties the board of pieces.
	 * @param board Game board
	 */
	public void reset(GridPane board) {
		for (int rows = 0; rows < 8; rows++) {
			for (int cols = 0; cols < 8; cols++) {
				getRectangleFromGridPane(board, cols, rows).setFill(Color.GREEN);
			}
		}
	}

	/**
	 * Moves the pieces from the boardA String to the GUI.
	 * @param board	Game Board
	 */
	public void move(GridPane board) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				// board.getChildren().removeAll(getNodeFromGridPane(board,col,row));
				if (boardA[row][col].equals("X")) {
					whitePiece(board, row, col);
				} else if (boardA[row][col].equals("O")) {
					blackPiece(board, row, col);
				} else {
					Rectangle r = new Rectangle(50, 50, Color.GREEN);
					r.setStroke(Color.BLACK);
					board.setConstraints(r, col, row);
				}
			}
		}
	}

}// Reversi
