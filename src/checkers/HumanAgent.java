package checkers;

import java.util.Scanner;

/**
 * An example class implementing Agent class for human players. The
 * implementation is coupled with the actual game (here, TickTackToe) the agent
 * is playing.
 *
 * @author Azad
 *
 */
public class HumanAgent extends Agent {

    static Scanner in = new Scanner(System.in);

    public HumanAgent(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }
    
    public static int prevRow = -1;
    public static int prevCol = -1;
    public static int row = -1;
    public static int col = -1;

    @Override
    public void makeMove(Game game) {
        // TODO Auto-generated method stub
        Checker cGame = (Checker) game;
        
        cGame.board[prevRow][prevCol] = -1;
        if (role % 2 == 0 && row == 7) {
            Checker.kingRow = row;
            Checker.kingCol = col;
            cGame.board[row][col] = 2;
        } else if (role % 2 == 0 && row != 7) {
            cGame.board[row][col] = role;
        } else if (role % 2 == 1 && row == 0) {
            Checker.kingRow = row;
            Checker.kingCol = col;
            cGame.board[row][col] = 3;
        } else if (role % 2 == 1 && row != 0) {
            cGame.board[row][col] = role;
        }
        
        if(Checker.killCol!=-1 && Checker.killRow!=-1){
            cGame.board[Checker.killRow][Checker.killCol] = -1;
        }
    }

}
