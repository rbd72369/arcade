package cs1302.arcade;

import java.util.Scanner;
/**
 * This class represents a Board.
 *
 * @author Timothy Xu <thx35333@uga.edu>
 */
public abstract class Board{
    protected int row, col = 0;
    protected String [][] letterArray; 
    //
    //  /**
    //   * Prints and modifies the game board.
    //   */
    //  public void printBoard(){
    //System.out.print("\n  ");
    //for (int i = 1; i <= 8; i++){// prints the col numbers
    //   System.out.print(i + " ");
    //    }
    //System.out.println("");
    //   for (int i = 1; i <= 8; i++){
    //   System.out.print(i + " ");// prints the row numbers
    //   for (int j = 1; j <= 8; j++){
    //   System.out.print(letterArray[i-1][j-1] + " ");
    //   }
    //   System.out.println("");
    //   }
    //  }
  
    /**
     * Sets a light piece down. Since the board starts at 1 and arrays start at 0, we correct
     * the number mismatch by subtracting the row and col we put the dark piece in by one.
     * 
     * @param row the row index of the square
     * @param col the column index of the square
     */
    public void setLightPiece(int row, int col){
	letterArray[row][col] = "X";
    }
  
    /**
     * Sets a dark piece down. Since the board starts at 1 and arrays start at 0, we correct
     * the number mismatch by subtracting the row and col we put the light piece in by one.
     * 
     * @param row the row index of the square
     * @param col the column index of the square
     */
    public void setDarkPiece(int row, int col){
	letterArray[row][col] = "O";
    }
  
    // vital abstract methods
    abstract boolean isInBounds(int row, int col);
}
