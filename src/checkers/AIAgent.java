package checkers;

import java.util.ArrayList;
import java.util.List;

/**
 * Example MiniMax agent extending Agent class. Here, for simplicity of
 * understanding min and max functions are written separately. One single
 * function can do the task.
 *
 * @author Azad
 *
 */
public class AIAgent extends Agent {

    private int first = 0;

    public AIAgent(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }
    public List<Checker> list = new ArrayList<Checker>();
    public List<CellValueTuple> list2 = new ArrayList<CellValueTuple>();
    public Checker bobj;

    public static int row = -1;
    public static int col = -1;
    public static int prevRow = -1;
    public static int prevCol = -1;

    @Override
    public void makeMove(Game g) {
        // TODO Auto-generated method stub

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Checker game = (Checker) g;
//        if (first == 0) {
//            bobj = new Board(cGame.board);
//            bobj.maxRole = role;
//            list.add(bobj);
//            first = 1;
//        }
        CellValueTuple finalCellObj = max(game);

        
        if (finalCellObj.col != -1) {
//            cGame.board[best.row][best.col] = best.roleVal;
            game.board[finalCellObj.row][finalCellObj.col] = game.board[finalCellObj.prevRow][finalCellObj.prevCol];
            game.board[finalCellObj.prevRow][finalCellObj.prevCol] = -1;
            if (finalCellObj.killRow != -1) {
                game.board[finalCellObj.killRow][finalCellObj.killCol] = -1;
            }
            if (finalCellObj.kingRow != -1) {
                if (game.nowTurn == 2) {
                    game.board[finalCellObj.kingRow][finalCellObj.kingCol] = 2;
                } else {
                    game.board[finalCellObj.kingRow][finalCellObj.kingCol] = 3;
                }
            }
            AIAgent.row = finalCellObj.row;
            AIAgent.col = finalCellObj.col;
            AIAgent.prevRow = finalCellObj.prevRow;
            AIAgent.prevCol = finalCellObj.prevCol;
            Checker.killRow = finalCellObj.killRow;
            Checker.killCol = finalCellObj.killCol;
            Checker.kingRow = finalCellObj.kingRow;
            Checker.kingCol = finalCellObj.kingCol;

            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!GOT THE BOARD!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            game.showGameState();
        }
    }

    private CellValueTuple max(Checker game) {
        CellValueTuple maxCVT = new CellValueTuple();
        maxCVT.utility = -1000;

        int winner = game.checkForWin();

        //terminal check
        if (winner == role) {
            maxCVT.utility =  50; //this agent wins
            return maxCVT;
        } else if (winner != -1) {
            maxCVT.utility = -50; //opponent wins
            return maxCVT;
        } else if (game.isBoardFull()) {
            maxCVT.utility = 0; //draw
            return maxCVT;
        }
        if(game.depth<=0){
            System.out.println("RETURNED/////////////////");
            maxCVT.utility = game.heuristic(); //max depth
            return maxCVT;
        }

        list = new ArrayList<Checker>();
        list2 = new ArrayList<CellValueTuple>();
        list.add(game);
        list2.add(null);
        int totalNeigh = 0;
        Checker finalbobj = null;
        CellValueTuple finalCellObj = null;
        System.out.println("-------------------FROM MAX WE ARE PRINTING--------------------------------");
        while (list.isEmpty() != true) {
            
            bobj = list.get(0);
            System.out.println("****************DEPTH IS " + game.depth + "*******************");
            System.out.println("PARENT BOARD:");
            bobj.showGameState();

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (bobj.board[i][j] % 2 != bobj.nowTurn) {
                        continue;
                    }
                    int tempVal = bobj.board[i][j];
                    Checker bobj2;
                    CellValueTuple cellObj;
                    CellValueTuple cellObjRec;

                    //killing neighbors
                    if (bobj.isValidCell(j + 2, i + 2, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i + 1) + " & COL:" + (j + 1));
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;
                        bobj2.board[i + 2][j + 2] = tempVal;
//                            System.out.println("THIS IS IT " + game.board[i+1][j+1]);
                        bobj2.board[i][j] = -1;

                        int[] a;
                        a = bobj2.whatCanIKill(i + 2, j + 2, i, j, tempVal);
                        if (a.length != 0) {
                            //CAN KILL SOMETHING
                            bobj2.killIt(i + 2, j + 2, tempVal % 2, a[0], a[1]);
                            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&*************** KILLED AT : " + a[0] + " " + a[1]);
                        }
                        bobj2.makeKing(i + 2, j + 2, tempVal % 2);
//                        
                        cellObj = new CellValueTuple();
                        cellObj.row = i + 2;
                        cellObj.col = j + 2;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        cellObj.killRow = a[0];
                        cellObj.killCol = a[1];
                        if (bobj2.makeKing(i + 2, j + 2, tempVal % 2)) {
                            cellObj.kingRow = i + 2;
                            cellObj.kingCol = j + 2;
                        }
                        
                        bobj2.depth--;
                        cellObjRec = this.min(bobj2);
                        if (maxCVT.utility < cellObjRec.utility) {
                            cellObj.copy(cellObj, maxCVT);
                            maxCVT.utility = cellObjRec.utility;
                        }
                        if (maxCVT.utility >= bobj.beta) 
                            return maxCVT;
                        bobj.alpha = Math.max(bobj.alpha, maxCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }

                    if (bobj.isValidCell(j - 2, i + 2, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i + 1) + " & COL:" + (j + 1));
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;
                        bobj2.board[i + 2][j - 2] = tempVal;
//                            System.out.println("THIS IS IT " + game.board[i+1][j+1]);
                        bobj2.board[i][j] = -1;

                        int[] a;
                        a = bobj2.whatCanIKill(i + 2, j - 2, i, j, tempVal);
                        if (a.length != 0) {
                            //CAN KILL SOMETHING
                            bobj2.killIt(i + 2, j - 2, tempVal % 2, a[0], a[1]);
                            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&*************** KILLED AT : " + a[0] + " " + a[1]);
                        }
                        bobj2.makeKing(i + 2, j - 2, tempVal % 2);
//                        
                        cellObj = new CellValueTuple();
                        cellObj.row = i + 2;
                        cellObj.col = j - 2;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        cellObj.killRow = a[0];
                        cellObj.killCol = a[1];
                        if (bobj2.makeKing(i + 2, j - 2, tempVal % 2)) {
                            cellObj.kingRow = i + 2;
                            cellObj.kingCol = j - 2;
                        }
                        bobj2.depth--;
                        cellObjRec = this.min(bobj2);
                        if (maxCVT.utility < cellObjRec.utility) {
                            cellObj.copy(cellObj, maxCVT);
                            maxCVT.utility = cellObjRec.utility;
                        }
                        if (maxCVT.utility >= bobj.beta) 
                            return maxCVT;
                        bobj.alpha = Math.max(bobj.alpha, maxCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }

                    if (bobj.isValidCell(j - 2, i - 2, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i + 1) + " & COL:" + (j + 1));
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;
                        bobj2.board[i - 2][j - 2] = tempVal;
//                            System.out.println("THIS IS IT " + game.board[i+1][j+1]);
                        bobj2.board[i][j] = -1;

                        int[] a;
                        a = bobj2.whatCanIKill(i - 2, j - 2, i, j, tempVal);
                        if (a.length != 0) {
                            //CAN KILL SOMETHING
                            bobj2.killIt(i - 2, j - 2, tempVal % 2, a[0], a[1]);
                            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&*************** KILLED AT : " + a[0] + " " + a[1]);
                        }
                        bobj2.makeKing(i - 2, j - 2, tempVal % 2);
//                        
                        cellObj = new CellValueTuple();
                        cellObj.row = i - 2;
                        cellObj.col = j - 2;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        cellObj.killRow = a[0];
                        cellObj.killCol = a[1];
                        if (bobj2.makeKing(i - 2, j - 2, tempVal % 2)) {
                            cellObj.kingRow = i - 2;
                            cellObj.kingCol = j - 2;
                        }
                        bobj2.depth--;
                        cellObjRec = this.min(bobj2);
                        if (maxCVT.utility < cellObjRec.utility) {
                            cellObj.copy(cellObj, maxCVT);
                            maxCVT.utility = cellObjRec.utility;
                        }
                        if (maxCVT.utility >= bobj.beta) 
                            return maxCVT;
                        bobj.alpha = Math.max(bobj.alpha, maxCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }

                    if (bobj.isValidCell(j + 2, i - 2, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i + 1) + " & COL:" + (j + 1));
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;
                        bobj2.board[i - 2][j + 2] = tempVal;
//                            System.out.println("THIS IS IT " + game.board[i+1][j+1]);
                        bobj2.board[i][j] = -1;

                        int[] a;
                        a = bobj2.whatCanIKill(i - 2, j + 2, i, j, tempVal);
                        if (a.length != 0) {
                            //CAN KILL SOMETHING
                            bobj2.killIt(i - 2, j + 2, tempVal % 2, a[0], a[1]);
                            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&*************** KILLED AT : " + a[0] + " " + a[1]);
                        }
                        bobj2.makeKing(i - 2, j + 2, tempVal % 2);
//                        
                        cellObj = new CellValueTuple();
                        cellObj.row = i - 2;
                        cellObj.col = j + 2;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        cellObj.killRow = a[0];
                        cellObj.killCol = a[1];
                        if (bobj2.makeKing(i - 2, j + 2, tempVal % 2)) {
                            cellObj.kingRow = i - 2;
                            cellObj.kingCol = j + 2;
                        }
                        bobj2.depth--;
                        cellObjRec = this.min(bobj2);
                        if (maxCVT.utility < cellObjRec.utility) {
                            cellObj.copy(cellObj, maxCVT);
                            maxCVT.utility = cellObjRec.utility;
                        }
                        if (maxCVT.utility >= bobj.beta) 
                            return maxCVT;
                        bobj.alpha = Math.max(bobj.alpha, maxCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }

                    //niriho neighbors
                    if (bobj.isValidCell(j + 1, i + 1, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i + 1) + " & COL:" + (j + 1));
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;
                        bobj2.board[i + 1][j + 1] = tempVal;
//                            System.out.println("THIS IS IT " + game.board[i+1][j+1]);
                        bobj2.board[i][j] = -1;
                        bobj2.makeKing(i + 1, j + 1, tempVal % 2);
                        cellObj = new CellValueTuple();
                        cellObj.row = i + 1;
                        cellObj.col = j + 1;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        if (bobj2.makeKing(i + 1, j + 1, tempVal % 2)) {
                            cellObj.kingRow = i + 1;
                            cellObj.kingCol = j + 1;
                        }
                        bobj2.depth--;
                        cellObjRec = this.min(bobj2);
                        if (maxCVT.utility < cellObjRec.utility) {
                            cellObj.copy(cellObj, maxCVT);
                            maxCVT.utility = cellObjRec.utility;
                        }
                        if (maxCVT.utility >= bobj.beta) 
                            return maxCVT;
                        bobj.alpha = Math.max(bobj.alpha, maxCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }
                    if (bobj.isValidCell(j - 1, i + 1, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i + 1) + " & COL:" + (j - 1));
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;;
                        bobj2.board[i + 1][j - 1] = tempVal;
//                    System.out.println("THIS IS IT " + game.board[i + 1][j - 1]);
                        bobj2.board[i][j] = -1;
                        bobj2.makeKing(i + 1, j - 1, tempVal % 2);
                        cellObj = new CellValueTuple();
                        cellObj.row = i + 1;
                        cellObj.col = j - 1;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        if (bobj2.makeKing(i + 1, j - 1, tempVal % 2)) {
                            cellObj.kingRow = i + 1;
                            cellObj.kingCol = j - 1;
                        }
                        bobj2.depth--;
                        cellObjRec = this.min(bobj2);
                        if (maxCVT.utility < cellObjRec.utility) {
                            cellObj.copy(cellObj, maxCVT);
                            maxCVT.utility = cellObjRec.utility;
                        }
                        if (maxCVT.utility >= bobj.beta) 
                            return maxCVT;
                        bobj.alpha = Math.max(bobj.alpha, maxCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }
                    if (bobj.isValidCell(j + 1, i - 1, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i - 1) + " & COL:" + (j + 1));
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;
                        bobj2.board[i - 1][j + 1] = tempVal;
//                    System.out.println("THIS IS IT " + game.board[i - 1][j + 1]);
                        bobj2.board[i][j] = -1;
                        bobj2.makeKing(i - 1, j + 1, tempVal % 2);
                        cellObj = new CellValueTuple();
                        cellObj.row = i - 1;
                        cellObj.col = j + 1;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        if (bobj2.makeKing(i - 1, j + 1, tempVal % 2)) {
                            cellObj.kingRow = i - 1;
                            cellObj.kingCol = j + 1;
                        }
                        bobj2.depth--;
                        cellObjRec = this.min(bobj2);
                        if (maxCVT.utility < cellObjRec.utility) {
                            cellObj.copy(cellObj, maxCVT);
                            maxCVT.utility = cellObjRec.utility;
                        }
                        if (maxCVT.utility >= bobj.beta) 
                            return maxCVT;
                        bobj.alpha = Math.max(bobj.alpha, maxCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }
                    if (bobj.isValidCell(j - 1, i - 1, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i - 1) + " & COL:" + (j - 1));
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;
                        bobj2.board[i - 1][j - 1] = tempVal;
//                    System.out.println("THIS IS IT " + game.board[i - 1][j - 1]);
                        bobj2.board[i][j] = -1;
                        bobj2.makeKing(i - 1, j - 1, tempVal % 2);
                        cellObj = new CellValueTuple();
                        cellObj.row = i - 1;
                        cellObj.col = j - 1;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        if (bobj2.makeKing(i - 1, j - 1, tempVal % 2)) {
                            cellObj.kingRow = i - 1;
                            cellObj.kingCol = j - 1;
                        }
                        bobj2.depth--;
                        cellObjRec = this.min(bobj2);
                        if (maxCVT.utility < cellObjRec.utility) {
                            cellObj.copy(cellObj, maxCVT);
                            maxCVT.utility = cellObjRec.utility;
                        }
                        if (maxCVT.utility >= bobj.beta) 
                            return maxCVT;
                        bobj.alpha = Math.max(bobj.alpha, maxCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }

                }
            }

            list.remove(0);
            list2.remove(0);
//            
//            int maxUtilityIdx = -1;
//            if (list2.isEmpty() != true) {
//                maxUtilityIdx = maxHeuInList(list2);
//                System.out.println("IDX IS :::::::::::::::::::::::::: " + maxUtilityIdx);
//                finalCellObj = list2.remove(maxUtilityIdx);
////                System.out.println("THE GRAND FINAL###### Prev:" + finalCellObj.prevRow + " " + finalCellObj.prevCol + "\tNow:" + finalCellObj.row + " "+ finalCellObj.col);
//            }
//            if(maxUtilityIdx!=-1){
//                System.out.println("++++++++++++++++CHOOSING NO.\t " + (maxUtilityIdx+1) + " \tNEIGHBOR ");
//                System.out.println("MAX HEU IS \t" + finalCellObj.utility);
//                finalbobj = list.remove(maxUtilityIdx);
//            }
        }

//        game.board[finalCellObj.row][finalCellObj.col] = game.board[finalCellObj.prevRow][finalCellObj.prevCol];
//        game.board[finalCellObj.prevRow][finalCellObj.prevCol] = -1;
//        if (finalCellObj.killRow != -1) {
//            game.board[finalCellObj.killRow][finalCellObj.killCol] = -1;
//        }
//        if (finalCellObj.kingRow != -1) {
//            if (finalbobj.nowTurn == 2) {
//                game.board[finalCellObj.kingRow][finalCellObj.kingCol] = 2;
//            } else {
//                game.board[finalCellObj.kingRow][finalCellObj.kingCol] = 3;
//            }
//        }
//        AIAgent.row = finalCellObj.row;
//        AIAgent.col = finalCellObj.col;
//        AIAgent.prevRow = finalCellObj.prevRow;
//        AIAgent.prevCol = finalCellObj.prevCol;
//        Checker.killRow = finalCellObj.killRow;
//        Checker.killCol = finalCellObj.killCol;
//        Checker.kingRow = finalCellObj.kingRow;
//        Checker.kingCol = finalCellObj.kingCol;
//
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!GOT THE BOARD!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        game.showGameState();

        System.out.println("MAXCVT:" + maxCVT.row + " " + maxCVT.col);
        return maxCVT;

    }

    private CellValueTuple min(Checker game) {
        CellValueTuple minCVT = new CellValueTuple();
        minCVT.utility = 100;

        int winner = game.checkForWin();

        //terminal check
        if (winner == role) {
            minCVT.utility =  50; //this agent wins
            return minCVT;
        } else if (winner != -1) {
            minCVT.utility = -50; //opponent wins
            return minCVT;
        } else if (game.isBoardFull()) {
            minCVT.utility = 0; //draw
            return minCVT;
        }
        if(game.depth<=0){
            System.out.println("RETURNED FROM MIN****************************");
            minCVT.utility = game.heuristic(); //max depth
            return minCVT;
        }

        list = new ArrayList<Checker>();
        list2 = new ArrayList<CellValueTuple>();
        list.add(game);
        list2.add(null);
        
        int totalNeigh = 0;
        Checker finalbobj = null;
        CellValueTuple finalCellObj = null;
        System.out.println("-------------------FROM MIN WE ARE PRINTING--------------------------------");
        while (list.isEmpty() != true) {
            
            bobj = list.get(0);

            System.out.println("****************DEPTH IS " + game.depth  + "*******************");
            System.out.println("PARENT BOARD:");
            bobj.showGameState();

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (bobj.board[i][j] % 2 != bobj.nowTurn) {
                        continue;
                    }
                    int tempVal = bobj.board[i][j];
                    Checker bobj2;
                    CellValueTuple cellObj;
                    CellValueTuple cellObjRec;

                    //killing neighbors
                    if (bobj.isValidCell(j + 2, i + 2, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i + 1) + " & COL:" + (j + 1));
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;
                        bobj2.board[i + 2][j + 2] = tempVal;
//                            System.out.println("THIS IS IT " + game.board[i+1][j+1]);
                        bobj2.board[i][j] = -1;

                        int[] a;
                        a = bobj2.whatCanIKill(i + 2, j + 2, i, j, tempVal);
                        if (a.length != 0) {
                            //CAN KILL SOMETHING
                            bobj2.killIt(i + 2, j + 2, tempVal % 2, a[0], a[1]);
                            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&*************** KILLED AT : " + a[0] + " " + a[1]);
                        }
                        bobj2.makeKing(i + 2, j + 2, tempVal % 2);
                        cellObj = new CellValueTuple();
                        cellObj.row = i + 2;
                        cellObj.col = j + 2;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        cellObj.killRow = a[0];
                        cellObj.killCol = a[1];
                        if (bobj2.makeKing(i + 2, j + 2, tempVal % 2)) {
                            cellObj.kingRow = i + 2;
                            cellObj.kingCol = j + 2;
                        }
                        bobj2.depth--;
                        cellObjRec = this.max(bobj2);
                        if (minCVT.utility > cellObjRec.utility) {
                            cellObj.copy(cellObj, minCVT);
                            minCVT.utility = cellObjRec.utility;
                        }
                        if (minCVT.utility <= bobj.alpha) 
                            return minCVT;
                        bobj.beta = Math.min(bobj.beta, minCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }

                    if (bobj.isValidCell(j - 2, i + 2, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i + 1) + " & COL:" + (j + 1));
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;
                        bobj2.board[i + 2][j - 2] = tempVal;
//                            System.out.println("THIS IS IT " + game.board[i+1][j+1]);
                        bobj2.board[i][j] = -1;

                        int[] a;
                        a = bobj2.whatCanIKill(i + 2, j - 2, i, j, tempVal);
                        if (a.length != 0) {
                            //CAN KILL SOMETHING
                            bobj2.killIt(i + 2, j - 2, tempVal % 2, a[0], a[1]);
                            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&*************** KILLED AT : " + a[0] + " " + a[1]);
                        }
                        bobj2.makeKing(i + 2, j - 2, tempVal % 2);
//                        
                        cellObj = new CellValueTuple();
                        cellObj.row = i + 2;
                        cellObj.col = j - 2;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        cellObj.killRow = a[0];
                        cellObj.killCol = a[1];
                        if (bobj2.makeKing(i + 2, j - 2, tempVal % 2)) {
                            cellObj.kingRow = i + 2;
                            cellObj.kingCol = j - 2;
                        }
                        bobj2.depth--;
                        cellObjRec = this.max(bobj2);
                        if (minCVT.utility > cellObjRec.utility) {
                            cellObj.copy(cellObj, minCVT);
                            minCVT.utility = cellObjRec.utility;
                        }
                        if (minCVT.utility <= bobj.alpha) 
                            return minCVT;
                        bobj.beta = Math.min(bobj.beta, minCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }

                    if (bobj.isValidCell(j - 2, i - 2, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i + 1) + " & COL:" + (j + 1));
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;
                        bobj2.board[i - 2][j - 2] = tempVal;
//                            System.out.println("THIS IS IT " + game.board[i+1][j+1]);
                        bobj2.board[i][j] = -1;

                        int[] a;
                        a = bobj2.whatCanIKill(i - 2, j - 2, i, j, tempVal);
                        if (a.length != 0) {
                            //CAN KILL SOMETHING
                            bobj2.killIt(i - 2, j - 2, tempVal % 2, a[0], a[1]);
                            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&*************** KILLED AT : " + a[0] + " " + a[1]);
                        }
                        bobj2.makeKing(i - 2, j - 2, tempVal % 2);
//                        
                        cellObj = new CellValueTuple();
                        cellObj.row = i - 2;
                        cellObj.col = j - 2;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        cellObj.killRow = a[0];
                        cellObj.killCol = a[1];
                        if (bobj2.makeKing(i - 2, j - 2, tempVal % 2)) {
                            cellObj.kingRow = i - 2;
                            cellObj.kingCol = j - 2;
                        }
                        bobj2.depth--;
                        cellObjRec = this.max(bobj2);
                        if (minCVT.utility > cellObjRec.utility) {
                            cellObj.copy(cellObj, minCVT);
                            minCVT.utility = cellObjRec.utility;
                        }
                        if (minCVT.utility <= bobj.alpha) 
                            return minCVT;
                        bobj.beta = Math.min(bobj.beta, minCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }

                    if (bobj.isValidCell(j + 2, i - 2, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i + 1) + " & COL:" + (j + 1));
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;
                        bobj2.board[i - 2][j + 2] = tempVal;
//                            System.out.println("THIS IS IT " + game.board[i+1][j+1]);
                        bobj2.board[i][j] = -1;

                        int[] a;
                        a = bobj2.whatCanIKill(i - 2, j + 2, i, j, tempVal);
                        if (a.length != 0) {
                            //CAN KILL SOMETHING
                            bobj2.killIt(i - 2, j + 2, tempVal % 2, a[0], a[1]);
                            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&*************** KILLED AT : " + a[0] + " " + a[1]);
                        }
                        bobj2.makeKing(i - 2, j + 2, tempVal % 2);
//                        
                        cellObj = new CellValueTuple();
                        cellObj.row = i - 2;
                        cellObj.col = j + 2;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        cellObj.killRow = a[0];
                        cellObj.killCol = a[1];
                        if (bobj2.makeKing(i - 2, j + 2, tempVal % 2)) {
                            cellObj.kingRow = i - 2;
                            cellObj.kingCol = j + 2;
                        }
                        bobj2.depth--;
                        cellObjRec = this.max(bobj2);
                        if (minCVT.utility > cellObjRec.utility) {
                            cellObj.copy(cellObj, minCVT);
                            minCVT.utility = cellObjRec.utility;
                        }
                        if (minCVT.utility <= bobj.alpha) 
                            return minCVT;
                        bobj.beta = Math.min(bobj.beta, minCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }

                    //niriho neighbors
                    if (bobj.isValidCell(j + 1, i + 1, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i + 1) + " & COL:" + (j + 1));
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;
                        bobj2.board[i + 1][j + 1] = tempVal;
//                            System.out.println("THIS IS IT " + game.board[i+1][j+1]);
                        bobj2.board[i][j] = -1;
                        bobj2.makeKing(i + 1, j + 1, tempVal % 2);
                        cellObj = new CellValueTuple();
                        cellObj.row = i + 1;
                        cellObj.col = j + 1;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        if (bobj2.makeKing(i + 1, j + 1, tempVal % 2)) {
                            cellObj.kingRow = i + 1;
                            cellObj.kingCol = j + 1;
                        }
                        bobj2.depth--;
                        cellObjRec = this.max(bobj2);
                        if (minCVT.utility > cellObjRec.utility) {
                            cellObj.copy(cellObj, minCVT);
                            minCVT.utility = cellObjRec.utility;
                        }
                        if (minCVT.utility <= bobj.alpha) 
                            return minCVT;
                        bobj.beta = Math.min(bobj.beta, minCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }
                    if (bobj.isValidCell(j - 1, i + 1, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i + 1) + " & COL:" + (j - 1));
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;;
                        bobj2.board[i + 1][j - 1] = tempVal;
//                    System.out.println("THIS IS IT " + game.board[i + 1][j - 1]);
                        bobj2.board[i][j] = -1;
                        bobj2.makeKing(i + 1, j - 1, tempVal % 2);
                        cellObj = new CellValueTuple();
                        cellObj.row = i + 1;
                        cellObj.col = j - 1;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        if (bobj2.makeKing(i + 1, j - 1, tempVal % 2)) {
                            cellObj.kingRow = i + 1;
                            cellObj.kingCol = j - 1;
                        }
                        bobj2.depth--;
                        cellObjRec = this.max(bobj2);
                        if (minCVT.utility > cellObjRec.utility) {
                            cellObj.copy(cellObj, minCVT);
                            minCVT.utility = cellObjRec.utility;
                        }
                        if (minCVT.utility <= bobj.alpha) 
                            return minCVT;
                        bobj.beta = Math.min(bobj.beta, minCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }
                    if (bobj.isValidCell(j + 1, i - 1, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i - 1) + " & COL:" + (j + 1));
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;
                        bobj2.board[i - 1][j + 1] = tempVal;
//                    System.out.println("THIS IS IT " + game.board[i - 1][j + 1]);
                        bobj2.board[i][j] = -1;
                        bobj2.makeKing(i - 1, j + 1, tempVal % 2);
                        cellObj = new CellValueTuple();
                        cellObj.row = i - 1;
                        cellObj.col = j + 1;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        if (bobj2.makeKing(i - 1, j + 1, tempVal % 2)) {
                            cellObj.kingRow = i - 1;
                            cellObj.kingCol = j + 1;
                        }
                        bobj2.depth--;
                        cellObjRec = this.max(bobj2);
                        if (minCVT.utility > cellObjRec.utility) {
                            cellObj.copy(cellObj, minCVT);
                            minCVT.utility = cellObjRec.utility;
                        }
                        if (minCVT.utility <= bobj.alpha) 
                            return minCVT;
                        bobj.beta = Math.min(bobj.beta, minCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }
                    if (bobj.isValidCell(j - 1, i - 1, j, i) == true) {
//                        System.out.println("ROLE IS " + role);
//                        System.out.println("FOR ROW:" + (i + 1) + " & COL:" + (j + 1)
//                                + " TO ROW:" + (i - 1) + " & COL:" + (j - 1));
                        totalNeigh++;
                        bobj2 = new Checker(bobj.agent[0], bobj.agent[1], bobj.board);
                        bobj2.nowTurn = (bobj.nowTurn + 1) % 2;
                        bobj2.board[i - 1][j - 1] = tempVal;
//                    System.out.println("THIS IS IT " + game.board[i - 1][j - 1]);
                        bobj2.board[i][j] = -1;
                        bobj2.makeKing(i - 1, j - 1, tempVal % 2);
                        cellObj = new CellValueTuple();
                        cellObj.row = i - 1;
                        cellObj.col = j - 1;
                        cellObj.prevRow = i;
                        cellObj.prevCol = j;
                        if (bobj2.makeKing(i - 1, j - 1, tempVal % 2)) {
                            cellObj.kingRow = i - 1;
                            cellObj.kingCol = j - 1;
                        }
                        bobj2.depth--;
                        cellObjRec = this.max(bobj2);
                        if (minCVT.utility > cellObjRec.utility) {
                            cellObj.copy(cellObj, minCVT);
                            minCVT.utility = cellObjRec.utility;
                        }
                        if (minCVT.utility <= bobj.alpha) 
                            return minCVT;
                        bobj.beta = Math.min(bobj.beta, minCVT.utility);
//                        cellObj.utility = bobj2.heuristic();
                        list2.add(cellObj);
                        System.out.println("Neighbour # " + totalNeigh);
                        System.out.println("Prev:" + i + " " + j + "\tNow:" + cellObj.row + " " + cellObj.col);
                        System.out.println("HEURISTICS VALUE :::::\t" + cellObj.utility);
                        bobj2.showGameState();
                        list.add(bobj2);
                    }

                }
            }

            list.remove(0);
            list2.remove(0);
//            int minUtilityIdx = -1;
//            if (list2.isEmpty() != true) {
//                minUtilityIdx = minHeuInList(list2);
//                System.out.println("IDX IS :::::::::::::::::::::::::: " + minUtilityIdx);
//                finalCellObj = list2.remove(minUtilityIdx);
////                System.out.println("THE GRAND FINAL###### Prev:" + finalCellObj.prevRow + " " + finalCellObj.prevCol + "\tNow:" + finalCellObj.row + " "+ finalCellObj.col);
//            }
//            if (minUtilityIdx != -1) {
//                System.out.println("++++++++++++++++CHOOSING NO.\t " + (minUtilityIdx + 1) + " \tNEIGHBOR ");
//                System.out.println("MAX HEU IS \t" + finalCellObj.utility);
//                finalbobj = list.remove(minUtilityIdx);
//            }

        }

//        game.board[finalCellObj.row][finalCellObj.col] = game.board[finalCellObj.prevRow][finalCellObj.prevCol];
//        game.board[finalCellObj.prevRow][finalCellObj.prevCol] = -1;
//        if (finalCellObj.killRow != -1) {
//            game.board[finalCellObj.killRow][finalCellObj.killCol] = -1;
//        }
//        if (finalCellObj.kingRow != -1) {
//            if (finalbobj.nowTurn == 2) {
//                game.board[finalCellObj.kingRow][finalCellObj.kingCol] = 2;
//            } else {
//                game.board[finalCellObj.kingRow][finalCellObj.kingCol] = 3;
//            }
//        }
//        AIAgent.row = finalCellObj.row;
//        AIAgent.col = finalCellObj.col;
//        AIAgent.prevRow = finalCellObj.prevRow;
//        AIAgent.prevCol = finalCellObj.prevCol;
//        Checker.killRow = finalCellObj.killRow;
//        Checker.killCol = finalCellObj.killCol;
//        Checker.kingRow = finalCellObj.kingRow;
//        Checker.kingCol = finalCellObj.kingCol;
//
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!GOT THE BOARD!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        game.showGameState();
        System.out.println("MINCVT:" + minCVT.row + " " + minCVT.col);
        return minCVT;

    }

    private int minRole() {
        if (role == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    class CellValueTuple {

        int row, col, utility, roleVal, prevRow, prevCol;
        int killRow, killCol, kingRow, kingCol;

        public CellValueTuple() {
            // TODO Auto-generated constructor stub
            row = -1;
            col = -1;
            prevRow = -1;
            prevCol = -1;
            killRow = -1;
            killCol = -1;
            kingRow = -1;
            kingCol = -1;
        }
        
        public void copy(CellValueTuple a,CellValueTuple b){
            b.col = a.col;
            b.killCol = a.killCol;
            b.killRow = a.killRow;
            b.kingCol = a.kingCol;
            b.kingRow = a.kingRow;
            b.prevCol = a.prevCol;
            b.prevRow = a.prevRow;
            b.roleVal = a.roleVal;
            b.row = a.row;
            b.utility = a.utility;
            return;
        }

    }

    public int maxHeuInList(List<CellValueTuple> l) {
        int max = -1000;
        int index = -1;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i) == null) {
                continue;
            }
            if (l.get(i).utility > max) {
                max = l.get(i).utility;
                index = i;
            }
        }
        return index;
    }

    public int minHeuInList(List<CellValueTuple> l) {
        int min = 1000;
        int index = -1;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i) == null) {
                continue;
            }
            if (l.get(i).utility < min) {
                min = l.get(i).utility;
                index = i;
            }
        }
        return index;
    }

}
