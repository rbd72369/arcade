package cs1302.arcade;

import java.util.Scanner;

/**
 * This class represents the base value of the player class.
 *
 * @author Timothy Xu <thx35333@uga.edu>
 */
public abstract class Player {
    // if false, its dark. if true, its white. this declaration will be implemented by HumanPlayer and Intelligent/Random ComputerPlayer
    boolean white; 
    int i, j;
    abstract void randomGenerateMove(String [][] letterArray);
}