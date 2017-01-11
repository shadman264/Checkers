/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers;

/**
 *
 * @author shadman264
 */
class Checker extends Game {

    /**
     * The actual game board -1 empty, 0 -> RED, 1 -> BLACK
     */
    public int[][] board;
    public static int killRow = -1;
    public static int killCol = -1;

    public static int kingRow = -1;
    public static int kingCol = -1;

    public int nowTurn = 0;
    public int depth = 2;
    public int alpha = -100000;
    public int beta = 100000;

    /**
     * First agent starts with O
     *
     * @param a
     * @param b
     */
    public Checker(Agent a, Agent b) {
        super(a, b);

        a.setRole(0); // The first argument/agent is always assigned RED (0)
        b.setRole(1); // The second argument/agent is always assigned BLACK (1)

        name = "CHECKERS";

        board = new int[8][8];
    }

    public Checker(Agent a, Agent b, int[][] ara) {
        super(a, b);

        a.setRole(0); // The first argument/agent is always assigned RED (0)
        b.setRole(1); // The second argument/agent is always assigned BLACK (1)

        name = "CHECKERS";

        board = new int[8][8];
        for (int i = 0; i < ara.length; i++) {
            for (int j = 0; j < ara.length; j++) {
                board[i][j] = ara[i][j];
            }
        }

    }

    @Override
    boolean isFinished() {
        if (checkForWin() != -1) {
            return true;
        } else if (isBoardFull()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    void initialize(boolean fromFile) {
        // TODO Auto-generated method stub

        //initialize with black
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = -1;
            }
        }

        //assign RED
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    board[i][j] = 0;
                } else if (i % 2 != 0 && j % 2 != 0) {
                    board[i][j] = 0;
                }
            }
        }

        //assign BLACK
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    board[i][j] = 1;
                } else if (i % 2 != 0 && j % 2 != 0) {
                    board[i][j] = 1;
                }
            }
        }

    }

    /**
     * Prints the current board (may be replaced/appended with by GUI)
     */
    @Override
    void showGameState() {
        System.out.println("-------------");

        for (int i = 7; i >= 0; i--) {
            System.out.print("| ");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == -1) {
                    System.out.print(" " + " | ");
                } else if (board[i][j] == 0) {
                    System.out.print("R | ");
                } else if (board[i][j] == 2) {
                    System.out.print("R* | ");
                } else if (board[i][j] == 1) {
                    System.out.print("B | ");
                } else if (board[i][j] == 3) {
                    System.out.print("B* | ");
                }
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    /**
     * Loop through all cells of the board and if one is found to be empty
     * (contains -1) then return false. Otherwise the board is full.
     */
    public boolean isBoardFull() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == -1) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns role of the winner, if no winner/ still game is going on -1.
     */
    public int checkForWin() {
        int redCount = 0;
        int blackCount = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 0 || board[i][j] == 2) {
                    redCount++;
                } else if (board[i][j] == 1 || board[i][j] == 3) {
                    blackCount++;
                }
            }
        }
        if (redCount == 0) {
            winner = agent[1];
            return 1;
        } else if (blackCount == 0) {
            winner = agent[0];
            return 1;
        } else {
            winner = null;
            return -1;
        }
    }

    @Override
    void updateMessage(String msg) {
        System.out.println(msg);
    }

    boolean isValidCell(int x, int y, int prevX, int prevY) {

        int col = x;
        int row = y;
        int prevCol = prevX;
        int prevRow = prevY;

        int role = board[prevRow][prevCol];

//        System.out.println("VALUE OF X & Y FROM isValidCell is :" + row + " " + col + " "
//                + prevRow + " " + prevCol + " role is:" + role);
        if (x < 0 || y < 0 || x > 7 || y > 7) {
            return false;
        }

        //CHECK IF IT IS EMPTY
        if (this.board[row][col] == -1) {
            //CHECK IF I CAN KILL
//            System.out.println(this.canIKill(row, col, prevRow, prevCol, role));
            if (this.canIKill(row, col, prevRow, prevCol, role) == true) {
                HumanAgent.col = col;
                HumanAgent.row = row;
                HumanAgent.prevCol = prevCol;
                HumanAgent.prevRow = prevRow;

                return true;
            }
            //CHECK IF KING
            if (role < 2) {
                //NOT KING
                //CHECK ROW
                if ((row - prevRow != 1 && role % 2 == 0) || (prevRow - row != 1 && role % 2 == 1)) {
                    return false;
                }
                //CHECK COLUMN
                if (Math.abs(col - prevCol) != 1) {
                    return false;
                }

            } else {
                //KING
                //CHECK ROW
                if (Math.abs(row - prevRow) != 1) {
                    return false;
                }
                //CHECK COLUMN
                if (Math.abs(col - prevCol) != 1 && Math.abs(col - prevCol) != 2) {
                    return false;
                }

            }

            HumanAgent.col = col;
            HumanAgent.row = row;
            HumanAgent.prevCol = prevCol;
            HumanAgent.prevRow = prevRow;

            return true;
        }

        return false;
    }

    boolean canIKill(int row, int col, int prevRow, int prevCol, int role) {
        if (role < 2) {
            //NOT KING
            if ((row - prevRow != 2 && role % 2 == 0) || (prevRow - row != 2 && role % 2 == 1)) {
                //System.out.println("1");
                return false;
            }
            if (Math.abs(col - prevCol) != 2) {
                //System.out.println("2");
                return false;
            }
            if (role % 2 == 0) {
                if (col - prevCol == 2 && board[prevRow + 1][prevCol + 1] != role
                        && board[prevRow + 1][prevCol + 1] != -1) {

//                    board[prevRow + 1][prevCol + 1] = -1;
                    Checker.killRow = prevRow + 1;
                    Checker.killCol = prevCol + 1;
                    //System.out.println("3");
                    return true;
                } else if (prevCol - col == 2 && board[prevRow + 1][prevCol - 1] != role && board[prevRow + 1][prevCol - 1] != -1) {
//                    board[prevRow + 1][prevCol - 1] = -1;
                    Checker.killRow = prevRow + 1;
                    Checker.killCol = prevCol - 1;
                    //System.out.println("4");
                    return true;
                } else {
                    //System.out.println("5");
                    return false;
                }
            } else if (role % 2 == 1) {
                if (col - prevCol == 2 && board[prevRow - 1][prevCol + 1] != role
                        && board[prevRow - 1][prevCol + 1] != -1) {
//                    board[prevRow - 1][prevCol + 1] = -1;
                    Checker.killRow = prevRow - 1;
                    Checker.killCol = prevCol + 1;
                    //System.out.println("3");
                    return true;
                } else if (prevCol - col == 2 && board[prevRow - 1][prevCol - 1] != role
                        && board[prevRow - 1][prevCol - 1] != -1) {
//                    board[prevRow - 1][prevCol - 1] = -1;
                    Checker.killRow = prevRow - 1;
                    Checker.killCol = prevCol - 1;
                    //System.out.println("4");
                    return true;
                } else {
                    //System.out.println("5");
                    return false;
                }
            }

        } else {
            //KING
            if (Math.abs(row - prevRow) != 2) {
                return false;
            }
            if (Math.abs(col - prevCol) != 2) {
                return false;
            }

            if (col - prevCol == 2 && prevRow != 7 && board[prevRow + 1][prevCol + 1] != role
                    && board[prevRow + 1][prevCol + 1] != -1) {
//                board[prevRow + 1][prevCol + 1] = -1;
                Checker.killRow = prevRow + 1;
                Checker.killCol = prevCol + 1;
                return true;
            } else if (col - prevCol == 2 && prevRow != 0 && board[prevRow - 1][prevCol + 1] != role
                    && board[prevRow - 1][prevCol + 1] != -1) {
//                board[prevRow - 1][prevCol + 1] = -1;
                Checker.killRow = prevRow - 1;
                Checker.killCol = prevCol + 1;
                return true;
            } else if (prevCol - col == 2 && prevRow != 7 && board[prevRow + 1][prevCol - 1] != role
                    && board[prevRow + 1][prevCol - 1] != -1) {
//                board[prevRow + 1][prevCol - 1] = -1;
                Checker.killRow = prevRow + 1;
                Checker.killCol = prevCol - 1;
                return true;
            } else if (prevCol - col == 2 && prevRow != 0 && board[prevRow - 1][prevCol - 1] != role
                    && board[prevRow - 1][prevCol - 1] != -1) {
//                board[prevRow - 1][prevCol - 1] = -1;
                Checker.killRow = prevRow - 1;
                Checker.killCol = prevCol - 1;
                return true;
            } else {
                return false;
            }
        }

        //System.out.println("OUT OF THE BOX");
        return false;
    }

    public int[] whatCanIKill(int row, int col, int prevRow, int prevCol, int role) {
        int[] a = new int[2];
        if (role < 2) {
            //NOT KING
            if ((row - prevRow != 2 && role % 2 == 0) || (prevRow - row != 2 && role % 2 == 1)) {
                //System.out.println("1");
                return a;
            }
            if (Math.abs(col - prevCol) != 2) {
                //System.out.println("2");
                return a;
            }
            if (role % 2 == 0) {
                if (col - prevCol == 2 && board[prevRow + 1][prevCol + 1] != role
                        && board[prevRow + 1][prevCol + 1] != -1) {

//                    board[prevRow + 1][prevCol + 1] = -1;
//                    Checker.killRow = prevRow + 1;
//                    Checker.killCol = prevCol + 1;
                    a[0] = prevRow + 1;
                    a[1] = prevCol + 1;
                    //System.out.println("3");
                    return a;
                } else if (prevCol - col == 2 && board[prevRow + 1][prevCol - 1] != role && board[prevRow + 1][prevCol - 1] != -1) {
//                    board[prevRow + 1][prevCol - 1] = -1;
//                    Checker.killRow = prevRow + 1;
//                    Checker.killCol = prevCol - 1;
                    a[0] = prevRow + 1;
                    a[1] = prevCol - 1;
                    //System.out.println("4");
                    return a;
                } else {
                    //System.out.println("5");
                    return a;
                }
            } else if (role % 2 == 1) {
                if (col - prevCol == 2 && board[prevRow - 1][prevCol + 1] != role
                        && board[prevRow - 1][prevCol + 1] != -1) {
//                    board[prevRow - 1][prevCol + 1] = -1;
//                    Checker.killRow = prevRow - 1;
//                    Checker.killCol = prevCol + 1;
                    a[0] = prevRow - 1;
                    a[1] = prevCol + 1;
                    //System.out.println("3");
                    return a;
                } else if (prevCol - col == 2 && board[prevRow - 1][prevCol - 1] != role
                        && board[prevRow - 1][prevCol - 1] != -1) {
//                    board[prevRow - 1][prevCol - 1] = -1;
//                    Checker.killRow = prevRow - 1;
//                    Checker.killCol = prevCol - 1;
                    //System.out.println("4");
                    a[0] = prevRow - 1;
                    a[1] = prevCol - 1;
                    return a;
                } else {
                    //System.out.println("5");
                    return a;
                }
            }

        } else {
            //KING
            if (Math.abs(row - prevRow) != 2) {
                return a;
            }
            if (Math.abs(col - prevCol) != 2) {
                return a;
            }

            if (col - prevCol == 2 && prevRow != 7 && board[prevRow + 1][prevCol + 1] != role
                    && board[prevRow + 1][prevCol + 1] != -1) {
//                board[prevRow + 1][prevCol + 1] = -1;
//                Checker.killRow = prevRow + 1;
//                Checker.killCol = prevCol + 1;
                a[0] = prevRow + 1;
                a[1] = prevCol + 1;
                return a;
            } else if (col - prevCol == 2 && prevRow != 0 && board[prevRow - 1][prevCol + 1] != role
                    && board[prevRow - 1][prevCol + 1] != -1) {
//                board[prevRow - 1][prevCol + 1] = -1;
//                Checker.killRow = prevRow - 1;
//                Checker.killCol = prevCol + 1;
                a[0] = prevRow - 1;
                a[1] = prevCol + 1;
                return a;
            } else if (prevCol - col == 2 && prevRow != 7 && board[prevRow + 1][prevCol - 1] != role
                    && board[prevRow + 1][prevCol - 1] != -1) {
//                board[prevRow + 1][prevCol - 1] = -1;
//                Checker.killRow = prevRow - 1;
//                Checker.killCol = prevCol + 1;
                a[0] = prevRow + 1;
                a[1] = prevCol - 1;
                return a;
            } else if (prevCol - col == 2 && prevRow != 0 && board[prevRow - 1][prevCol - 1] != role
                    && board[prevRow - 1][prevCol - 1] != -1) {
//                board[prevRow - 1][prevCol - 1] = -1;
//                Checker.killRow = prevRow - 1;
//                Checker.killCol = prevCol + 1;
                a[0] = prevRow - 1;
                a[1] = prevCol + 1;
                return a;
            } else {
                return a;
            }
        }

        //System.out.println("OUT OF THE BOX");
        return a;
    }

    public void killIt(int row, int col, int role, int killRow, int killCol) {
        System.out.println("TOLD TO KILL @@@@@@@@@@@@@@@@ " + killRow + " " + killCol);
        if (killRow < 0 || killRow > 7 || killCol > 7 || killCol < 0) {
            return;
        }
        board[killRow][killCol] = -1;

    }

    public boolean makeKing(int row, int col, int role) {
        if (row < 0 || row > 7 || col > 7 || col < 0) {
            return false;
        }
        if (role % 2 == 0 && row == 7) {

            board[row][col] = 2;
            return true;
        } else if (role % 2 == 1 && row == 0) {

            board[row][col] = 3;
            return true;
        } else {
            return false;
        }
    }

    public int heuristic() {
        int redCount = 0;
        int blackCount = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 0) {
                    redCount++;
                } else if (board[i][j] == 2) {
                    redCount += 2;
                } else if (board[i][j] == 1) {
                    blackCount++;
                } else if (board[i][j] == 3) {
                    blackCount += 2;
                }
            }
        }
        if (nowTurn == 1) {
            return redCount - blackCount;
        } else {
            return blackCount - redCount;
        }
    }

}
