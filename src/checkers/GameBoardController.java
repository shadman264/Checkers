/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers;

import static checkers.MenuController.obj;
import com.sun.javaws.Main;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author shadman264
 */
public class GameBoardController implements Initializable {

    @FXML
    private ImageView b1;
    @FXML
    private ImageView r9;
    @FXML
    private ImageView r10;
    @FXML
    private ImageView r5;
    @FXML
    private ImageView r6;
    @FXML
    private ImageView r7;
    @FXML
    private ImageView r8;
    @FXML
    private ImageView r1;
    @FXML
    private ImageView r2;
    @FXML
    private ImageView r3;
    @FXML
    private ImageView r4;
    @FXML
    private ImageView b2;
    @FXML
    private ImageView b3;
    @FXML
    private ImageView b4;
    @FXML
    private ImageView b5;
    @FXML
    private ImageView b6;
    @FXML
    private ImageView b7;
    @FXML
    private ImageView b8;
    @FXML
    private ImageView b9;
    @FXML
    private ImageView b10;
    @FXML
    private ImageView b11;
    @FXML
    private ImageView b12;

    @FXML
    private ImageView board;

//    MY VAIRABLES
    private ImageView sourceID = null;
    private int newX = -1;
    private int newY = -1;

    public Game game = null;
    public Agent agent1 = null;
    public Agent agent2 = null;
    public Checker checkerGame = null;
    @FXML
    private ImageView r11;
    @FXML
    private ImageView r12;
    @FXML
    private Label turn_label;
    @FXML
    private ImageView r1k;
    @FXML
    private ImageView r2k;
    @FXML
    private ImageView r3k;
    @FXML
    private ImageView r4k;
    @FXML
    private ImageView r5k;
    @FXML
    private ImageView r6k;
    @FXML
    private ImageView r7k;
    @FXML
    private ImageView r8k;
    @FXML
    private ImageView r9k;
    @FXML
    private ImageView r10k;
    @FXML
    private ImageView r11k;
    @FXML
    private ImageView r12k;
    @FXML
    private ImageView b1k;
    @FXML
    private ImageView b2k;
    @FXML
    private ImageView b3k;
    @FXML
    private ImageView b4k;
    @FXML
    private ImageView b5k;
    @FXML
    private ImageView b6k;
    @FXML
    private ImageView b7k;
    @FXML
    private ImageView b8k;
    @FXML
    private ImageView b9k;
    @FXML
    private ImageView b10k;
    @FXML
    private ImageView b11k;
    @FXML
    private ImageView b12k;

//    public static GameBoardController megaObj = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

//    It selects piece
    @FXML
    private void movePiece(MouseEvent event) {
        if (sourceID != null) {
            sourceID.setOpacity(1);
            sourceID.setScaleX(1);
            sourceID.setScaleY(1);
        }

        sourceID = (ImageView) event.getSource();
        String sourceIDString = sourceID.toString().substring(sourceID.toString().indexOf('=') + 1, sourceID.toString().indexOf(','));
        if (sourceIDString.charAt(0) == 'r' && Game.turn == 0) {
            sourceID.setOpacity(0.8);
            sourceID.setScaleX(0.95);
            sourceID.setScaleY(0.95);
        } else if (sourceIDString.charAt(0) == 'b' && Game.turn == 1) {
            sourceID.setOpacity(0.8);
            sourceID.setScaleX(0.95);
            sourceID.setScaleY(0.95);
        } else if (sourceIDString.charAt(0) == 'r' && Game.turn == 1) {
            System.out.println("NOT YOUR TURN");
            sourceID = null;
        } else if (sourceIDString.charAt(0) == 'b' && Game.turn == 0) {
            System.out.println("NOT YOUR TURN");
            sourceID = null;
        }
//        System.out.println(sourceID);

//        MenuController.obj.game.showGameState();
    }

//    It moves piece
    @FXML
    public void desiredPosition(MouseEvent event) throws IOException, InterruptedException {
        if (event == null && Game.turn == 1) {

            this.desiredPosition2(AIAgent.row, AIAgent.col, AIAgent.prevRow, AIAgent.prevCol);
            AIAgent.row = -1;
            AIAgent.col = -1;
            AIAgent.prevRow = -1;
            AIAgent.prevCol = -1;
            return;
        }
        if (sourceID != null) {
//            MenuController.obj.agent2.makeMove(MenuController.obj.game);
            int x = (int) event.getX();
            int y = (int) event.getY();

            if (x < 27 && x > 451) {
                return;
            }
            if (y < 27 && y > 451) {
                return;
            }

//            System.out.println("GOT" + x +" " + y);
//            System.out.println("PREVIOUS" + (int)sourceID.getLayoutX()+" " + (int)sourceID.getLayoutY());
            x -= 27;
            int r = x % 53;
            x -= r;
            x += 19;

            y -= 27;
            r = y % 53;
            y -= r;
            y += 21;

//            System.out.println("NEW" + x +" " + y);
//            checkerGame = (Checker) game;
            int a, b, c, d;
            a = x - 19;
            a /= 53;

            b = y - 21;
            b /= 53;
            b = 7 - b;

            c = (int) sourceID.getLayoutX();
            c -= 19;
            c /= 53;

            d = (int) sourceID.getLayoutY();
            d -= 21;
            d /= 53;
            d = 7 - d;

            if (MenuController.obj.checkerGame.isValidCell(a, b,
                    c, d) == true) {
                if (Game.turn == 0) {
                    //FOR TESTING,REMOVE IT MUSTTTTTTTTTTTT
                    MenuController.obj.agent1.makeMove(MenuController.obj.game);

//                    return;
                } else {
                    MenuController.obj.agent2.makeMove(MenuController.obj.game);
                }

                sourceID.setLayoutX(x);
                sourceID.setLayoutY(y);
                sourceID.setOpacity(1);
                sourceID.setScaleX(1);
                sourceID.setScaleY(1);
                sourceID = null;
                MenuController.obj.game.showGameState();

                if (Checker.killRow != -1 && Checker.killCol != -1) {
//                    System.out.println("KR :" + Checker.killRow + " KC :" + Checker.killCol);
                    int killX = Checker.killCol;
                    killX *= 53;
                    killX += 19;

                    int killY = 7 - Checker.killRow;
                    killY *= 53;
                    killY += 21;

                    System.out.println("KR :" + killX + " KC :" + killY);

                    //REMOVE KILLED PIECE
                    //CHECK RED
                    if ((r1.getLayoutX() == killX && r1.getLayoutY() == killY)
                            || (r1k.getLayoutX() == killX && r1k.getLayoutY() == killY)) {
                        r1.setLayoutX(0);
                        r1.setLayoutY(0);
                        r1.setVisible(false);
                        r1k.setLayoutX(0);
                        r1k.setLayoutY(0);
                        r1k.setVisible(false);
                        System.out.println("Removed r1");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((r2.getLayoutX() == killX && r2.getLayoutY() == killY)
                            || (r2k.getLayoutX() == killX && r2k.getLayoutY() == killY)) {
                        r2.setLayoutX(0);
                        r2.setLayoutY(0);
                        r2.setVisible(false);
                        r2k.setLayoutX(0);
                        r2k.setLayoutY(0);
                        r2k.setVisible(false);
                        System.out.println("Removed r2");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((r3.getLayoutX() == killX && r3.getLayoutY() == killY)
                            || (r3k.getLayoutX() == killX && r3k.getLayoutY() == killY)) {
                        r3.setLayoutX(0);
                        r3.setLayoutY(0);
                        r3.setVisible(false);
                        r3k.setLayoutX(0);
                        r3k.setLayoutY(0);
                        r3k.setVisible(false);
                        System.out.println("Removed r3");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((r4.getLayoutX() == killX && r4.getLayoutY() == killY)
                            || (r4k.getLayoutX() == killX && r4k.getLayoutY() == killY)) {
                        r4.setLayoutX(0);
                        r4.setLayoutY(0);
                        r4.setVisible(false);
                        r4k.setLayoutX(0);
                        r4k.setLayoutY(0);
                        r4k.setVisible(false);
                        System.out.println("Removed r4");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((r5.getLayoutX() == killX && r5.getLayoutY() == killY)
                            || (r5k.getLayoutX() == killX && r5k.getLayoutY() == killY)) {
                        r5.setLayoutX(0);
                        r5.setLayoutY(0);
                        r5.setVisible(false);
                        r5k.setLayoutX(0);
                        r5k.setLayoutY(0);
                        r5k.setVisible(false);
                        System.out.println("Removed r5");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((r6.getLayoutX() == killX && r6.getLayoutY() == killY)
                            || (r6k.getLayoutX() == killX && r6k.getLayoutY() == killY)) {
                        r6.setLayoutX(0);
                        r6.setLayoutY(0);
                        r6.setVisible(false);
                        r6k.setLayoutX(0);
                        r6k.setLayoutY(0);
                        r6k.setVisible(false);
                        System.out.println("Removed r6");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((r7.getLayoutX() == killX && r7.getLayoutY() == killY)
                            || (r7k.getLayoutX() == killX && r7k.getLayoutY() == killY)) {
                        r7.setLayoutX(0);
                        r7.setLayoutY(0);
                        r7.setVisible(false);
                        r7k.setLayoutX(0);
                        r7k.setLayoutY(0);
                        r7k.setVisible(false);
                        System.out.println("Removed r7");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((r8.getLayoutX() == killX && r8.getLayoutY() == killY)
                            || (r8k.getLayoutX() == killX && r8k.getLayoutY() == killY)) {
                        r8.setLayoutX(0);
                        r8.setLayoutY(0);
                        r8.setVisible(false);
                        r8k.setLayoutX(0);
                        r8k.setLayoutY(0);
                        r8k.setVisible(false);
                        System.out.println("Removed r8");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((r9.getLayoutX() == killX && r9.getLayoutY() == killY)
                            || (r9k.getLayoutX() == killX && r9k.getLayoutY() == killY)) {
                        r9.setLayoutX(0);
                        r9.setLayoutY(0);
                        r9.setVisible(false);
                        r9k.setLayoutX(0);
                        r9k.setLayoutY(0);
                        r9k.setVisible(false);
                        System.out.println("Removed r9");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((r10.getLayoutX() == killX && r10.getLayoutY() == killY)
                            || (r10k.getLayoutX() == killX && r10k.getLayoutY() == killY)) {
                        r10.setLayoutX(0);
                        r10.setLayoutY(0);
                        r10.setVisible(false);
                        r10k.setLayoutX(0);
                        r10k.setLayoutY(0);
                        r10k.setVisible(false);
                        System.out.println("Removed r10");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((r11.getLayoutX() == killX && r11.getLayoutY() == killY)
                            || (r11k.getLayoutX() == killX && r11k.getLayoutY() == killY)) {
                        r11.setLayoutX(0);
                        r11.setLayoutY(0);
                        r11.setVisible(false);
                        r11k.setLayoutX(0);
                        r11k.setLayoutY(0);
                        r11k.setVisible(false);
                        System.out.println("Removed r11");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((r12.getLayoutX() == killX && r12.getLayoutY() == killY)
                            || (r12k.getLayoutX() == killX && r12k.getLayoutY() == killY)) {
                        r12.setLayoutX(0);
                        r12.setLayoutY(0);
                        r12.setVisible(false);
                        r12k.setLayoutX(0);
                        r12k.setLayoutY(0);
                        r12k.setVisible(false);
                        System.out.println("Removed r12");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } //CHECK BLACK
                    else if ((b1.getLayoutX() == killX && b1.getLayoutY() == killY)
                            || (b1k.getLayoutX() == killX && b1k.getLayoutY() == killY)) {
                        b1.setLayoutX(0);
                        b1.setLayoutY(0);
                        b1.setVisible(false);
                        b1k.setLayoutX(0);
                        b1k.setLayoutY(0);
                        b1k.setVisible(false);
                        System.out.println("Removed b1");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((b2.getLayoutX() == killX && b2.getLayoutY() == killY)
                            || (b2k.getLayoutX() == killX && b2k.getLayoutY() == killY)) {
                        b2.setLayoutX(0);
                        b2.setLayoutY(0);
                        b2.setVisible(false);
                        b2k.setLayoutX(0);
                        b2k.setLayoutY(0);
                        b2k.setVisible(false);
                        System.out.println("Removed b2");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((b3.getLayoutX() == killX && b3.getLayoutY() == killY)
                            || (b3k.getLayoutX() == killX && b3k.getLayoutY() == killY)) {
                        b3.setLayoutX(0);
                        b3.setLayoutY(0);
                        b3.setVisible(false);
                        b3k.setLayoutX(0);
                        b3k.setLayoutY(0);
                        b3k.setVisible(false);
                        System.out.println("Removed b3");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((b4.getLayoutX() == killX && b4.getLayoutY() == killY)
                            || (b4k.getLayoutX() == killX && b4k.getLayoutY() == killY)) {
                        b4.setLayoutX(0);
                        b4.setLayoutY(0);
                        b4.setVisible(false);
                        b4k.setLayoutX(0);
                        b4k.setLayoutY(0);
                        b4k.setVisible(false);
                        System.out.println("Removed b4");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((b5.getLayoutX() == killX && b5.getLayoutY() == killY)
                            || (b5k.getLayoutX() == killX && b5k.getLayoutY() == killY)) {
                        b5.setLayoutX(0);
                        b5.setLayoutY(0);
                        b5.setVisible(false);
                        b5k.setLayoutX(0);
                        b5k.setLayoutY(0);
                        b5k.setVisible(false);
                        System.out.println("Removed b5");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((b6.getLayoutX() == killX && b6.getLayoutY() == killY)
                            || (b6k.getLayoutX() == killX && b6k.getLayoutY() == killY)) {
                        b6.setLayoutX(0);
                        b6.setLayoutY(0);
                        b6.setVisible(false);
                        b6k.setLayoutX(0);
                        b6k.setLayoutY(0);
                        b6k.setVisible(false);
                        System.out.println("Removed b6");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((b7.getLayoutX() == killX && b7.getLayoutY() == killY)
                            || (b7k.getLayoutX() == killX && b7k.getLayoutY() == killY)) {
                        b7.setLayoutX(0);
                        b7.setLayoutY(0);
                        b7.setVisible(false);
                        b7k.setLayoutX(0);
                        b7k.setLayoutY(0);
                        b7k.setVisible(false);
                        System.out.println("Removed b7");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((b8.getLayoutX() == killX && b8.getLayoutY() == killY)
                            || (b8k.getLayoutX() == killX && b8k.getLayoutY() == killY)) {
                        b8.setLayoutX(0);
                        b8.setLayoutY(0);
                        b8.setVisible(false);
                        b8k.setLayoutX(0);
                        b8k.setLayoutY(0);
                        b8k.setVisible(false);
                        System.out.println("Removed b8");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((b9.getLayoutX() == killX && b9.getLayoutY() == killY)
                            || (b9k.getLayoutX() == killX && b9k.getLayoutY() == killY)) {
                        b9.setLayoutX(0);
                        b9.setLayoutY(0);
                        b9.setVisible(false);
                        b9k.setLayoutX(0);
                        b9k.setLayoutY(0);
                        b9k.setVisible(false);
                        System.out.println("Removed b9");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((b10.getLayoutX() == killX && b10.getLayoutY() == killY)
                            || (b10k.getLayoutX() == killX && b10k.getLayoutY() == killY)) {
                        b10.setLayoutX(0);
                        b10.setLayoutY(0);
                        b10.setVisible(false);
                        b10k.setLayoutX(0);
                        b10k.setLayoutY(0);
                        b10k.setVisible(false);
                        System.out.println("Removed b10");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((b11.getLayoutX() == killX && b11.getLayoutY() == killY)
                            || (b11k.getLayoutX() == killX && b11k.getLayoutY() == killY)) {
                        b11.setLayoutX(0);
                        b11.setLayoutY(0);
                        b11.setVisible(false);
                        b11k.setLayoutX(0);
                        b11k.setLayoutY(0);
                        b11k.setVisible(false);
                        System.out.println("Removed b11");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else if ((b12.getLayoutX() == killX && b12.getLayoutY() == killY)
                            || (b12k.getLayoutX() == killX && b12k.getLayoutY() == killY)) {
                        b12.setLayoutX(0);
                        b12.setLayoutY(0);
                        b12.setVisible(false);
                        b12k.setLayoutX(0);
                        b12k.setLayoutY(0);
                        b12k.setVisible(false);
                        System.out.println("Removed b12");
                        Checker.killRow = -1;
                        Checker.killCol = -1;
                    } else {
                        System.out.println("r1: " + r1.getLayoutX() + " " + r1.getLayoutY());
                        System.out.println("r2: " + r2.getLayoutX() + " " + r2.getLayoutY());
                        System.out.println("r3: " + r3.getLayoutX() + " " + r3.getLayoutY());
                        System.out.println("r4: " + r4.getLayoutX() + " " + r4.getLayoutY());
                        System.out.println("r5: " + r5.getLayoutX() + " " + r5.getLayoutY());
                        System.out.println("r6: " + r6.getLayoutX() + " " + r6.getLayoutY());
                        System.out.println("r7: " + r7.getLayoutX() + " " + r7.getLayoutY());
                        System.out.println("r8: " + r8.getLayoutX() + " " + r8.getLayoutY());
                        System.out.println("r9: " + r9.getLayoutX() + " " + r9.getLayoutY());
                        System.out.println("r10: " + r10.getLayoutX() + " " + r10.getLayoutY());
                        System.out.println("r11: " + r11.getLayoutX() + " " + r11.getLayoutY());
                        System.out.println("r12: " + r12.getLayoutX() + " " + r12.getLayoutY());

                        System.out.println("b1: " + b1.getLayoutX() + " " + b1.getLayoutY());
                        System.out.println("b2: " + b2.getLayoutX() + " " + b2.getLayoutY());
                        System.out.println("b3: " + b3.getLayoutX() + " " + b3.getLayoutY());
                        System.out.println("b4: " + b4.getLayoutX() + " " + b4.getLayoutY());
                        System.out.println("b5: " + b5.getLayoutX() + " " + b5.getLayoutY());
                        System.out.println("b6: " + b6.getLayoutX() + " " + b6.getLayoutY());
                        System.out.println("b7: " + b7.getLayoutX() + " " + b7.getLayoutY());
                        System.out.println("b8: " + b8.getLayoutX() + " " + b8.getLayoutY());
                        System.out.println("b9: " + b9.getLayoutX() + " " + b9.getLayoutY());
                        System.out.println("b10: " + b10.getLayoutX() + " " + b10.getLayoutY());
                        System.out.println("b11: " + b11.getLayoutX() + " " + b11.getLayoutY());
                        System.out.println("b12: " + b12.getLayoutX() + " " + b12.getLayoutY());

                    }

                }

                if (Checker.kingRow != -1 && Checker.kingCol != -1) {
                    int killX = Checker.kingCol;
                    killX *= 53;
                    killX += 19;

                    int killY = 7 - Checker.kingRow;
                    killY *= 53;
                    killY += 21;
                    //ASSIGN KING
                    //CHECK RED
                    if ((r1.getLayoutX() == killX && r1.getLayoutY() == killY)) {
                        System.out.println("r1 king");
                        if (r1.getLayoutX() != 0) {
                            r1k.setLayoutX(r1.getLayoutX());
                            r1k.setLayoutY(r1.getLayoutY());
                            r1k.setVisible(true);
                            r1.setVisible(false);
                            r1.setId("r1n");
                            r1k.setId("r1");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((r2.getLayoutX() == killX && r2.getLayoutY() == killY)) {
                        System.out.println("r2 king");
                        if (r2.getLayoutX() != 0) {
                            r2k.setLayoutX(r2.getLayoutX());
                            r2k.setLayoutY(r2.getLayoutY());
                            r2k.setVisible(true);
                            r2.setVisible(false);
                            r2.setId("r2n");
                            r2k.setId("r2");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((r3.getLayoutX() == killX && r3.getLayoutY() == killY)) {
                        System.out.println("r3 king");
                        if (r3.getLayoutX() != 0) {
                            r3k.setLayoutX(r3.getLayoutX());
                            r3k.setLayoutY(r3.getLayoutY());
                            r3k.setVisible(true);
                            r3.setVisible(false);
                            r3.setId("r3n");
                            r3k.setId("r3");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((r4.getLayoutX() == killX && r4.getLayoutY() == killY)) {
                        System.out.println("r4 king");
                        if (r4.getLayoutX() != 0) {
                            r4k.setLayoutX(r4.getLayoutX());
                            r4k.setLayoutY(r4.getLayoutY());
                            r4k.setVisible(true);
                            r4.setVisible(false);
                            r4.setId("r4n");
                            r4k.setId("r4");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((r5.getLayoutX() == killX && r5.getLayoutY() == killY)) {
                        System.out.println("r5 king");
                        if (r5.getLayoutX() != 0) {
                            r5k.setLayoutX(r5.getLayoutX());
                            r5k.setLayoutY(r5.getLayoutY());
                            r5k.setVisible(true);
                            r5.setVisible(false);
                            r5.setId("r5n");
                            r5k.setId("r5");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((r6.getLayoutX() == killX && r6.getLayoutY() == killY)) {
                        System.out.println("r6 king");
                        if (r6.getLayoutX() != 0) {
                            r6k.setLayoutX(r6.getLayoutX());
                            r6k.setLayoutY(r6.getLayoutY());
                            r6k.setVisible(true);
                            r6.setVisible(false);
                            r6.setId("r6n");
                            r6k.setId("r6");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((r7.getLayoutX() == killX && r7.getLayoutY() == killY)) {
                        System.out.println("r7 king");
                        if (r7.getLayoutX() != 0) {
                            r7k.setLayoutX(r7.getLayoutX());
                            r7k.setLayoutY(r7.getLayoutY());
                            r7k.setVisible(true);
                            r7.setVisible(false);
                            r7.setId("r7n");
                            r7k.setId("r7");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((r8.getLayoutX() == killX && r8.getLayoutY() == killY)) {
                        System.out.println("r8 king");
                        if (r8.getLayoutX() != 0) {
                            r8k.setLayoutX(r8.getLayoutX());
                            r8k.setLayoutY(r8.getLayoutY());
                            r8k.setVisible(true);
                            r8.setVisible(false);
                            r8.setId("r8n");
                            r8k.setId("r8");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((r9.getLayoutX() == killX && r9.getLayoutY() == killY)) {
                        System.out.println("r9 king");
                        if (r9.getLayoutX() != 0) {
                            r9k.setLayoutX(r9.getLayoutX());
                            r9k.setLayoutY(r9.getLayoutY());
                            r9k.setVisible(true);
                            r9.setVisible(false);
                            r9.setId("r9n");
                            r9k.setId("r9");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }

                    } else if ((r10.getLayoutX() == killX && r10.getLayoutY() == killY)) {
                        System.out.println("r10 king");
                        if (r10.getLayoutX() != 0) {
                            r10k.setLayoutX(r10.getLayoutX());
                            r10k.setLayoutY(r10.getLayoutY());
                            r10k.setVisible(true);
                            r10.setVisible(false);
                            r10.setId("r10n");
                            r10k.setId("r10");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((r11.getLayoutX() == killX && r11.getLayoutY() == killY)) {
                        System.out.println("r11 king");
                        if (r11.getLayoutX() != 0) {
                            r11k.setLayoutX(r11.getLayoutX());
                            r11k.setLayoutY(r11.getLayoutY());
                            r11k.setVisible(true);
                            r11.setVisible(false);
                            r11.setId("r11n");
                            r11k.setId("r11");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((r12.getLayoutX() == killX && r12.getLayoutY() == killY)) {
                        System.out.println("r12 king");
                        if (r12.getLayoutX() != 0) {
                            r12k.setLayoutX(r12.getLayoutX());
                            r12k.setLayoutY(r12.getLayoutY());
                            r12k.setVisible(true);
                            r12.setVisible(false);
                            r12.setId("r12n");
                            r12k.setId("r12");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } //CHECK BLACK
                    else if ((b1.getLayoutX() == killX && b1.getLayoutY() == killY)) {
                        System.out.println("b1 king");
                        if (b1.getLayoutX() != 0) {
                            b1k.setLayoutX(b1.getLayoutX());
                            b1k.setLayoutY(b1.getLayoutY());
                            b1k.setVisible(true);
                            b1.setVisible(false);
                            b1.setId("b1n");
                            b1k.setId("b1");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((b2.getLayoutX() == killX && b2.getLayoutY() == killY)) {
                        System.out.println("b2 king");
                        if (b2.getLayoutX() != 0) {
                            b2k.setLayoutX(b2.getLayoutX());
                            b2k.setLayoutY(b2.getLayoutY());
                            b2k.setVisible(true);
                            b2.setVisible(false);
                            b2.setId("b2n");
                            b2k.setId("b2");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((b3.getLayoutX() == killX && b3.getLayoutY() == killY)) {
                        System.out.println("b3 king");
                        if (b1.getLayoutX() != 0) {
                            b3k.setLayoutX(b3.getLayoutX());
                            b3k.setLayoutY(b3.getLayoutY());
                            b3k.setVisible(true);
                            b3.setVisible(false);
                            b3.setId("b3n");
                            b3k.setId("b3");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((b4.getLayoutX() == killX && b4.getLayoutY() == killY)) {
                        System.out.println("b4 king");
                        if (b4.getLayoutX() != 0) {
                            b4k.setLayoutX(b4.getLayoutX());
                            b4k.setLayoutY(b4.getLayoutY());
                            b4k.setVisible(true);
                            b4.setVisible(false);
                            b4.setId("b4n");
                            b4k.setId("b4");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((b5.getLayoutX() == killX && b5.getLayoutY() == killY)) {
                        System.out.println("b5 king");
                        if (b5.getLayoutX() != 0) {
                            b5k.setLayoutX(b5.getLayoutX());
                            b5k.setLayoutY(b5.getLayoutY());
                            b5k.setVisible(true);
                            b5.setVisible(false);
                            b5.setId("b5n");
                            b5k.setId("b5");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((b6.getLayoutX() == killX && b6.getLayoutY() == killY)) {
                        System.out.println("b6 king");
                        if (b6.getLayoutX() != 0) {
                            b6k.setLayoutX(b6.getLayoutX());
                            b6k.setLayoutY(b6.getLayoutY());
                            b6k.setVisible(true);
                            b6.setVisible(false);
                            b6.setId("b6n");
                            b6k.setId("b6");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((b7.getLayoutX() == killX && b7.getLayoutY() == killY)) {
                        System.out.println("b7 king");
                        if (b7.getLayoutX() != 0) {
                            b7k.setLayoutX(b7.getLayoutX());
                            b7k.setLayoutY(b7.getLayoutY());
                            b7k.setVisible(true);
                            b7.setVisible(false);
                            b7.setId("b7n");
                            b7k.setId("b7");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((b8.getLayoutX() == killX && b8.getLayoutY() == killY)) {
                        System.out.println("b8 king");
                        if (b8.getLayoutX() != 0) {
                            b8k.setLayoutX(b8.getLayoutX());
                            b8k.setLayoutY(b8.getLayoutY());
                            b8k.setVisible(true);
                            b8.setVisible(false);
                            b8.setId("b8n");
                            b8k.setId("b8");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((b9.getLayoutX() == killX && b9.getLayoutY() == killY)) {
                        System.out.println("b9 king");
                        if (b9.getLayoutX() != 0) {
                            b9k.setLayoutX(b9.getLayoutX());
                            b9k.setLayoutY(b9.getLayoutY());
                            b9k.setVisible(true);
                            b9.setVisible(false);
                            b9.setId("b9n");
                            b9k.setId("b9");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((b10.getLayoutX() == killX && b10.getLayoutY() == killY)) {
                        System.out.println("b10 king");
                        if (b10.getLayoutX() != 0) {
                            b10k.setLayoutX(b10.getLayoutX());
                            b10k.setLayoutY(b10.getLayoutY());
                            b10k.setVisible(true);
                            b10.setVisible(false);
                            b10.setId("b10n");
                            b10k.setId("b10");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((b11.getLayoutX() == killX && b11.getLayoutY() == killY)) {
                        System.out.println("b11 king");
                        if (b11.getLayoutX() != 0) {
                            b11k.setLayoutX(b11.getLayoutX());
                            b11k.setLayoutY(b11.getLayoutY());
                            b11k.setVisible(true);
                            b11.setVisible(false);
                            b11.setId("b11n");
                            b11k.setId("b11");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    } else if ((b12.getLayoutX() == killX && b12.getLayoutY() == killY)) {
                        System.out.println("b12 king");
                        if (b12.getLayoutX() != 0) {
                            b12k.setLayoutX(b12.getLayoutX());
                            b12k.setLayoutY(b12.getLayoutY());
                            b12k.setVisible(true);
                            b12.setVisible(false);
                            b12.setId("b12n");
                            b12k.setId("b12");
                            Checker.kingRow = -1;
                            Checker.kingCol = -1;
                        }
                    }
                }

                if (MenuController.obj.game.isFinished()) {
                    System.out.println("Winner IS " + MenuController.obj.game.winner.name);
//                    Stage stage = (Stage) board.getScene().getWindow();
//                    stage.close();
                    this.turn_label.setText(MenuController.obj.game.winner.name + " Wins!!");

                } else {
                    Game.turn = (Game.turn + 1) % 2;
                    MenuController.obj.checkerGame.nowTurn = (MenuController.obj.checkerGame.nowTurn + 1) % 2;
                    this.turn_label.setText("Player " + (Game.turn + 1) + "'s Turn");

                    obj.agent2.makeMove(obj.game);
                }
                this.desiredPosition(null);

            } else {
                System.out.println("WRONG MOVE!!!");
            }

        }

        
    }

    //FOR AI MOVE PIECE
    @FXML
    public void desiredPosition2(int row, int col, int prevRow, int prevCol) throws IOException, InterruptedException {
        //UNCOMMENT IT MUSTTTTTTTTTTTTTTTTT

//        MenuController.obj.agent2.makeMove(MenuController.obj.game);
        if (row < 0 && row > 7) {
            return;
        }
        if (col < 0 && col > 7) {
            return;
        }

//            System.out.println("GOT" + x +" " + y);
//            System.out.println("PREVIOUS" + (int)sourceID.getLayoutX()+" " + (int)sourceID.getLayoutY());
//        Thread.sleep(1000);
        col *= 53;
        col += 19;
        int x = col;

        prevCol *= 53;
        prevCol += 19;
        int prevX = prevCol;

        row = 7 - row;
        row *= 53;
        row += 21;
        int y = row;

        prevRow = 7 - prevRow;
        prevRow *= 53;
        prevRow += 21;
        int prevY = prevRow;

        System.out.println("GOT in desiredPosition2 : " + x + " " + y);

        //PLACE PIECE
//      //CHECK RED
        if ((r1.getLayoutX() == prevX && r1.getLayoutY() == prevY)
                || (r1k.getLayoutX() == prevX && r1k.getLayoutY() == prevY)) {

            if (r1.getLayoutX() != 0) {
                //king ekhno ashe nai
                r1.setLayoutX(x);
                r1.setLayoutY(y);
                r1.setOpacity(1);
                r1.setScaleX(1);
                r1.setScaleY(1);
            } else {
                //king ashche
                r1k.setLayoutX(x);
                r1k.setLayoutY(y);
                r1k.setOpacity(1);
                r1k.setScaleX(1);
                r1k.setScaleY(1);
            }
            System.out.println("Placed r1");

        } else if ((r2.getLayoutX() == prevX && r2.getLayoutY() == prevY)
                || (r2k.getLayoutX() == prevX && r2k.getLayoutY() == prevY)) {

            if (r2.getLayoutX() != 0) {
                //king ekhno ashe nai
                r2.setLayoutX(x);
                r2.setLayoutY(y);
                r2.setOpacity(1);
                r2.setScaleX(1);
                r2.setScaleY(1);
            } else {
                //king ashche
                r2k.setLayoutX(x);
                r2k.setLayoutY(y);
                r2k.setOpacity(1);
                r2k.setScaleX(1);
                r2k.setScaleY(1);
            }
            System.out.println("Placed r2");

        } else if ((r3.getLayoutX() == prevX && r3.getLayoutY() == prevY)
                || (r3k.getLayoutX() == prevX && r3k.getLayoutY() == prevY)) {

            if (r3.getLayoutX() != 0) {
                //king ekhno ashe nai
                r3.setLayoutX(x);
                r3.setLayoutY(y);
                r3.setOpacity(1);
                r3.setScaleX(1);
                r3.setScaleY(1);
            } else {
                //king ashche
                r3k.setLayoutX(x);
                r3k.setLayoutY(y);
                r3k.setOpacity(1);
                r3k.setScaleX(1);
                r3k.setScaleY(1);
            }
            System.out.println("Placed r3");

        } else if ((r4.getLayoutX() == prevX && r4.getLayoutY() == prevY)
                || (r4k.getLayoutX() == prevX && r4k.getLayoutY() == prevY)) {

            if (r4.getLayoutX() != 0) {
                //king ekhno ashe nai
                r4.setLayoutX(x);
                r4.setLayoutY(y);
                r4.setOpacity(1);
                r4.setScaleX(1);
                r4.setScaleY(1);
            } else {
                //king ashche
                r4k.setLayoutX(x);
                r4k.setLayoutY(y);
                r4k.setOpacity(1);
                r4k.setScaleX(1);
                r4k.setScaleY(1);
            }
            System.out.println("Placed r4");

        } else if ((r5.getLayoutX() == prevX && r5.getLayoutY() == prevY)
                || (r5k.getLayoutX() == prevX && r5k.getLayoutY() == prevY)) {

            if (r5.getLayoutX() != 0) {
                //king ekhno ashe nai
                r5.setLayoutX(x);
                r5.setLayoutY(y);
                r5.setOpacity(1);
                r5.setScaleX(1);
                r5.setScaleY(1);
            } else {
                //king ashche
                r5k.setLayoutX(x);
                r5k.setLayoutY(y);
                r5k.setOpacity(1);
                r5k.setScaleX(1);
                r5k.setScaleY(1);
            }
            System.out.println("Placed r5");

        } else if ((r6.getLayoutX() == prevX && r6.getLayoutY() == prevY)
                || (r6k.getLayoutX() == prevX && r6k.getLayoutY() == prevY)) {

            if (r6.getLayoutX() != 0) {
                //king ekhno ashe nai
                r6.setLayoutX(x);
                r6.setLayoutY(y);
                r6.setOpacity(1);
                r6.setScaleX(1);
                r6.setScaleY(1);
            } else {
                //king ashche
                r6k.setLayoutX(x);
                r6k.setLayoutY(y);
                r6k.setOpacity(1);
                r6k.setScaleX(1);
                r6k.setScaleY(1);
            }
            System.out.println("Placed r6");

        } else if ((r7.getLayoutX() == prevX && r7.getLayoutY() == prevY)
                || (r7k.getLayoutX() == prevX && r7k.getLayoutY() == prevY)) {

            if (r7.getLayoutX() != 0) {
                //king ekhno ashe nai
                r7.setLayoutX(x);
                r7.setLayoutY(y);
                r7.setOpacity(1);
                r7.setScaleX(1);
                r7.setScaleY(1);
            } else {
                //king ashche
                r7k.setLayoutX(x);
                r7k.setLayoutY(y);
                r7k.setOpacity(1);
                r7k.setScaleX(1);
                r7k.setScaleY(1);
            }
            System.out.println("Placed r7");

        } else if ((r8.getLayoutX() == prevX && r8.getLayoutY() == prevY)
                || (r8k.getLayoutX() == prevX && r8k.getLayoutY() == prevY)) {

            if (r8.getLayoutX() != 0) {
                //king ekhno ashe nai
                r8.setLayoutX(x);
                r8.setLayoutY(y);
                r8.setOpacity(1);
                r8.setScaleX(1);
                r8.setScaleY(1);
            } else {
                //king ashche
                r8k.setLayoutX(x);
                r8k.setLayoutY(y);
                r8k.setOpacity(1);
                r8k.setScaleX(1);
                r8k.setScaleY(1);
            }
            System.out.println("Placed r8");

        } else if ((r9.getLayoutX() == prevX && r9.getLayoutY() == prevY)
                || (r9k.getLayoutX() == prevX && r9k.getLayoutY() == prevY)) {

            if (r9.getLayoutX() != 0) {
                //king ekhno ashe nai
                r9.setLayoutX(x);
                r9.setLayoutY(y);
                r9.setOpacity(1);
                r9.setScaleX(1);
                r9.setScaleY(1);
            } else {
                //king ashche
                r9k.setLayoutX(x);
                r9k.setLayoutY(y);
                r9k.setOpacity(1);
                r9k.setScaleX(1);
                r9k.setScaleY(1);
            }
            System.out.println("Placed r9");

        } else if ((r10.getLayoutX() == prevX && r10.getLayoutY() == prevY)
                || (r10k.getLayoutX() == prevX && r10k.getLayoutY() == prevY)) {

            if (r10.getLayoutX() != 0) {
                //king ekhno ashe nai
                r10.setLayoutX(x);
                r10.setLayoutY(y);
                r10.setOpacity(1);
                r10.setScaleX(1);
                r10.setScaleY(1);
            } else {
                //king ashche
                r10k.setLayoutX(x);
                r10k.setLayoutY(y);
                r10k.setOpacity(1);
                r10k.setScaleX(1);
                r10k.setScaleY(1);
            }
            System.out.println("Placed r10");

        } else if ((r11.getLayoutX() == prevX && r11.getLayoutY() == prevY)
                || (r11k.getLayoutX() == prevX && r11k.getLayoutY() == prevY)) {

            if (r11.getLayoutX() != 0) {
                //king ekhno ashe nai
                r11.setLayoutX(x);
                r11.setLayoutY(y);
                r11.setOpacity(1);
                r11.setScaleX(1);
                r11.setScaleY(1);
            } else {
                //king ashche
                r11k.setLayoutX(x);
                r11k.setLayoutY(y);
                r11k.setOpacity(1);
                r11k.setScaleX(1);
                r11k.setScaleY(1);
            }
            System.out.println("Placed r11");

        } else if ((r12.getLayoutX() == prevX && r12.getLayoutY() == prevY)
                || (r12k.getLayoutX() == prevX && r12k.getLayoutY() == prevY)) {

            if (r12.getLayoutX() != 0) {
                //king ekhno ashe nai
                r12.setLayoutX(x);
                r12.setLayoutY(y);
                r12.setOpacity(1);
                r12.setScaleX(1);
                r12.setScaleY(1);
            } else {
                //king ashche
                r12k.setLayoutX(x);
                r12k.setLayoutY(y);
                r12k.setOpacity(1);
                r12k.setScaleX(1);
                r12k.setScaleY(1);
            }
            System.out.println("Placed r12");

        }//CHECK BLACK
        else if ((b1.getLayoutX() == prevX && b1.getLayoutY() == prevY)
                || (b1k.getLayoutX() == prevX && b1k.getLayoutY() == prevY)) {

            if (b1.getLayoutX() != 0) {
                //king ekhno ashe nai
                b1.setLayoutX(x);
                b1.setLayoutY(y);
                b1.setOpacity(1);
                b1.setScaleX(1);
                b1.setScaleY(1);
            } else {
                //king ashche
                b1k.setLayoutX(x);
                b1k.setLayoutY(y);
                b1k.setOpacity(1);
                b1k.setScaleX(1);
                b1k.setScaleY(1);
            }
            System.out.println("Placed b1");

        } else if ((b2.getLayoutX() == prevX && b2.getLayoutY() == prevY)
                || (b2k.getLayoutX() == prevX && b2k.getLayoutY() == prevY)) {

            if (b2.getLayoutX() != 0) {
                //king ekhno ashe nai
                b2.setLayoutX(x);
                b2.setLayoutY(y);
                b2.setOpacity(1);
                b2.setScaleX(1);
                b2.setScaleY(1);
            } else {
                //king ashche
                b2k.setLayoutX(x);
                b2k.setLayoutY(y);
                b2k.setOpacity(1);
                b2k.setScaleX(1);
                b2k.setScaleY(1);
            }
            System.out.println("Placed b2");

        } else if ((b3.getLayoutX() == prevX && b3.getLayoutY() == prevY)
                || (b3k.getLayoutX() == prevX && b3k.getLayoutY() == prevY)) {

            if (b3.getLayoutX() != 0) {
                //king ekhno ashe nai
                b3.setLayoutX(x);
                b3.setLayoutY(y);
                b3.setOpacity(1);
                b3.setScaleX(1);
                b3.setScaleY(1);
            } else {
                //king ashche
                b3k.setLayoutX(x);
                b3k.setLayoutY(y);
                b3k.setOpacity(1);
                b3k.setScaleX(1);
                b3k.setScaleY(1);
            }
            System.out.println("Placed b3");

        } else if ((b4.getLayoutX() == prevX && b4.getLayoutY() == prevY)
                || (b4k.getLayoutX() == prevX && b4k.getLayoutY() == prevY)) {

            if (b4.getLayoutX() != 0) {
                //king ekhno ashe nai
                b4.setLayoutX(x);
                b4.setLayoutY(y);
                b4.setOpacity(1);
                b4.setScaleX(1);
                b4.setScaleY(1);
            } else {
                //king ashche
                b4k.setLayoutX(x);
                b4k.setLayoutY(y);
                b4k.setOpacity(1);
                b4k.setScaleX(1);
                b4k.setScaleY(1);
            }
            System.out.println("Placed b4");

        } else if ((b5.getLayoutX() == prevX && b5.getLayoutY() == prevY)
                || (b5k.getLayoutX() == prevX && b5k.getLayoutY() == prevY)) {

            if (b5.getLayoutX() != 0) {
                //king ekhno ashe nai
                b5.setLayoutX(x);
                b5.setLayoutY(y);
                b5.setOpacity(1);
                b5.setScaleX(1);
                b5.setScaleY(1);
            } else {
                //king ashche
                b5k.setLayoutX(x);
                b5k.setLayoutY(y);
                b5k.setOpacity(1);
                b5k.setScaleX(1);
                b5k.setScaleY(1);
            }
            System.out.println("Placed b5");

        } else if ((b6.getLayoutX() == prevX && b6.getLayoutY() == prevY)
                || (b6k.getLayoutX() == prevX && b6k.getLayoutY() == prevY)) {

            if (b6.getLayoutX() != 0) {
                //king ekhno ashe nai
                b6.setLayoutX(x);
                b6.setLayoutY(y);
                b6.setOpacity(1);
                b6.setScaleX(1);
                b6.setScaleY(1);
            } else {
                //king ashche
                b6k.setLayoutX(x);
                b6k.setLayoutY(y);
                b6k.setOpacity(1);
                b6k.setScaleX(1);
                b6k.setScaleY(1);
            }
            System.out.println("Placed b6");

        } else if ((b7.getLayoutX() == prevX && b7.getLayoutY() == prevY)
                || (b7k.getLayoutX() == prevX && b7k.getLayoutY() == prevY)) {

            if (b7.getLayoutX() != 0) {
                //king ekhno ashe nai
                b7.setLayoutX(x);
                b7.setLayoutY(y);
                b7.setOpacity(1);
                b7.setScaleX(1);
                b7.setScaleY(1);
            } else {
                //king ashche
                b7k.setLayoutX(x);
                b7k.setLayoutY(y);
                b7k.setOpacity(1);
                b7k.setScaleX(1);
                b7k.setScaleY(1);
            }
            System.out.println("Placed b7");

        } else if ((b8.getLayoutX() == prevX && b8.getLayoutY() == prevY)
                || (b8k.getLayoutX() == prevX && b8k.getLayoutY() == prevY)) {

            if (b8.getLayoutX() != 0) {
                //king ekhno ashe nai
                b8.setLayoutX(x);
                b8.setLayoutY(y);
                b8.setOpacity(1);
                b8.setScaleX(1);
                b8.setScaleY(1);
            } else {
                //king ashche
                b8k.setLayoutX(x);
                b8k.setLayoutY(y);
                b8k.setOpacity(1);
                b8k.setScaleX(1);
                b8k.setScaleY(1);
            }
            System.out.println("Placed b8");

        } else if ((b9.getLayoutX() == prevX && b9.getLayoutY() == prevY)
                || (b9k.getLayoutX() == prevX && b9k.getLayoutY() == prevY)) {

            if (b9.getLayoutX() != 0) {
                //king ekhno ashe nai
                b9.setLayoutX(x);
                b9.setLayoutY(y);
                b9.setOpacity(1);
                b9.setScaleX(1);
                b9.setScaleY(1);
                System.out.println("DID IT NORMAL");
            } else {
                //king ashche
                b9k.setLayoutX(x);
                b9k.setLayoutY(y);
                b9k.setOpacity(1);
                b9k.setScaleX(1);
                b9k.setScaleY(1);
                System.out.println("DID IT KING");
            }
            System.out.println("Placed b9");

        } else if ((b10.getLayoutX() == prevX && b10.getLayoutY() == prevY)
                || (b10k.getLayoutX() == prevX && b10k.getLayoutY() == prevY)) {

            if (b10.getLayoutX() != 0) {
                //king ekhno ashe nai
                b10.setLayoutX(x);
                b10.setLayoutY(y);
                b10.setOpacity(1);
                b10.setScaleX(1);
                b10.setScaleY(1);
            } else {
                //king ashche
                b10k.setLayoutX(x);
                b10k.setLayoutY(y);
                b10k.setOpacity(1);
                b10k.setScaleX(1);
                b10k.setScaleY(1);
            }
            System.out.println("Placed b10");

        } else if ((b11.getLayoutX() == prevX && b11.getLayoutY() == prevY)
                || (b11k.getLayoutX() == prevX && b11k.getLayoutY() == prevY)) {

            if (b11.getLayoutX() != 0) {
                //king ekhno ashe nai
                b11.setLayoutX(x);
                b11.setLayoutY(y);
                b11.setOpacity(1);
                b11.setScaleX(1);
                b11.setScaleY(1);
            } else {
                //king ashche
                b11k.setLayoutX(x);
                b11k.setLayoutY(y);
                b11k.setOpacity(1);
                b11k.setScaleX(1);
                b11k.setScaleY(1);
            }
            System.out.println("Placed b11");

        } else if ((b12.getLayoutX() == prevX && b12.getLayoutY() == prevY)
                || (b12k.getLayoutX() == prevX && b12k.getLayoutY() == prevY)) {

            if (b12.getLayoutX() != 0) {
                //king ekhno ashe nai
                b12.setLayoutX(x);
                b12.setLayoutY(y);
                b12.setOpacity(1);
                b12.setScaleX(1);
                b12.setScaleY(1);
            } else {
                //king ashche
                b12k.setLayoutX(x);
                b12k.setLayoutY(y);
                b12k.setOpacity(1);
                b12k.setScaleX(1);
                b12k.setScaleY(1);
            }
            System.out.println("Placed b12");

        } else {
            System.out.println("r1: " + r1.getLayoutX() + " " + r1.getLayoutY());
            System.out.println("r2: " + r2.getLayoutX() + " " + r2.getLayoutY());
            System.out.println("r3: " + r3.getLayoutX() + " " + r3.getLayoutY());
            System.out.println("r4: " + r4.getLayoutX() + " " + r4.getLayoutY());
            System.out.println("r5: " + r5.getLayoutX() + " " + r5.getLayoutY());
            System.out.println("r6: " + r6.getLayoutX() + " " + r6.getLayoutY());
            System.out.println("r7: " + r7.getLayoutX() + " " + r7.getLayoutY());
            System.out.println("r8: " + r8.getLayoutX() + " " + r8.getLayoutY());
            System.out.println("r9: " + r9.getLayoutX() + " " + r9.getLayoutY());
            System.out.println("r10: " + r10.getLayoutX() + " " + r10.getLayoutY());
            System.out.println("r11: " + r11.getLayoutX() + " " + r11.getLayoutY());
            System.out.println("r12: " + r12.getLayoutX() + " " + r12.getLayoutY());

            System.out.println("b1: " + b1.getLayoutX() + " " + b1.getLayoutY());
            System.out.println("b2: " + b2.getLayoutX() + " " + b2.getLayoutY());
            System.out.println("b3: " + b3.getLayoutX() + " " + b3.getLayoutY());
            System.out.println("b4: " + b4.getLayoutX() + " " + b4.getLayoutY());
            System.out.println("b5: " + b5.getLayoutX() + " " + b5.getLayoutY());
            System.out.println("b6: " + b6.getLayoutX() + " " + b6.getLayoutY());
            System.out.println("b7: " + b7.getLayoutX() + " " + b7.getLayoutY());
            System.out.println("b8: " + b8.getLayoutX() + " " + b8.getLayoutY());
            System.out.println("b9: " + b9.getLayoutX() + " " + b9.getLayoutY());
            System.out.println("b10: " + b10.getLayoutX() + " " + b10.getLayoutY());
            System.out.println("b11: " + b11.getLayoutX() + " " + b11.getLayoutY());
            System.out.println("b12: " + b12.getLayoutX() + " " + b12.getLayoutY());

        }

        MenuController.obj.game.showGameState();

        
        //REMOVE PIECE
        if (Checker.killRow != -1 && Checker.killCol != -1) {
//                    System.out.println("KR :" + Checker.killRow + " KC :" + Checker.killCol);
            int killX = Checker.killCol;
            killX *= 53;
            killX += 19;

            int killY = 7 - Checker.killRow;
            killY *= 53;
            killY += 21;

            System.out.println("KR :" + killX + " KC :" + killY);

            //REMOVE KILLED PIECE
            //CHECK RED
            if ((r1.getLayoutX() == killX && r1.getLayoutY() == killY)
                    || (r1k.getLayoutX() == killX && r1k.getLayoutY() == killY)) {
                r1.setLayoutX(0);
                r1.setLayoutY(0);
                r1.setVisible(false);
                r1k.setLayoutX(0);
                r1k.setLayoutY(0);
                r1k.setVisible(false);
                System.out.println("Removed r1");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((r2.getLayoutX() == killX && r2.getLayoutY() == killY)
                    || (r2k.getLayoutX() == killX && r2k.getLayoutY() == killY)) {
                r2.setLayoutX(0);
                r2.setLayoutY(0);
                r2.setVisible(false);
                r2k.setLayoutX(0);
                r2k.setLayoutY(0);
                r2k.setVisible(false);
                System.out.println("Removed r2");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((r3.getLayoutX() == killX && r3.getLayoutY() == killY)
                    || (r3k.getLayoutX() == killX && r3k.getLayoutY() == killY)) {
                r3.setLayoutX(0);
                r3.setLayoutY(0);
                r3.setVisible(false);
                r3k.setLayoutX(0);
                r3k.setLayoutY(0);
                r3k.setVisible(false);
                System.out.println("Removed r3");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((r4.getLayoutX() == killX && r4.getLayoutY() == killY)
                    || (r4k.getLayoutX() == killX && r4k.getLayoutY() == killY)) {
                r4.setLayoutX(0);
                r4.setLayoutY(0);
                r4.setVisible(false);
                r4k.setLayoutX(0);
                r4k.setLayoutY(0);
                r4k.setVisible(false);
                System.out.println("Removed r4");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((r5.getLayoutX() == killX && r5.getLayoutY() == killY)
                    || (r5k.getLayoutX() == killX && r5k.getLayoutY() == killY)) {
                r5.setLayoutX(0);
                r5.setLayoutY(0);
                r5.setVisible(false);
                r5k.setLayoutX(0);
                r5k.setLayoutY(0);
                r5k.setVisible(false);
                System.out.println("Removed r5");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((r6.getLayoutX() == killX && r6.getLayoutY() == killY)
                    || (r6k.getLayoutX() == killX && r6k.getLayoutY() == killY)) {
                r6.setLayoutX(0);
                r6.setLayoutY(0);
                r6.setVisible(false);
                r6k.setLayoutX(0);
                r6k.setLayoutY(0);
                r6k.setVisible(false);
                System.out.println("Removed r6");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((r7.getLayoutX() == killX && r7.getLayoutY() == killY)
                    || (r7k.getLayoutX() == killX && r7k.getLayoutY() == killY)) {
                r7.setLayoutX(0);
                r7.setLayoutY(0);
                r7.setVisible(false);
                r7k.setLayoutX(0);
                r7k.setLayoutY(0);
                r7k.setVisible(false);
                System.out.println("Removed r7");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((r8.getLayoutX() == killX && r8.getLayoutY() == killY)
                    || (r8k.getLayoutX() == killX && r8k.getLayoutY() == killY)) {
                r8.setLayoutX(0);
                r8.setLayoutY(0);
                r8.setVisible(false);
                r8k.setLayoutX(0);
                r8k.setLayoutY(0);
                r8k.setVisible(false);
                System.out.println("Removed r8");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((r9.getLayoutX() == killX && r9.getLayoutY() == killY)
                    || (r9k.getLayoutX() == killX && r9k.getLayoutY() == killY)) {
                r9.setLayoutX(0);
                r9.setLayoutY(0);
                r9.setVisible(false);
                r9k.setLayoutX(0);
                r9k.setLayoutY(0);
                r9k.setVisible(false);
                System.out.println("Removed r9");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((r10.getLayoutX() == killX && r10.getLayoutY() == killY)
                    || (r10k.getLayoutX() == killX && r10k.getLayoutY() == killY)) {
                r10.setLayoutX(0);
                r10.setLayoutY(0);
                r10.setVisible(false);
                r10k.setLayoutX(0);
                r10k.setLayoutY(0);
                r10k.setVisible(false);
                System.out.println("Removed r10");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((r11.getLayoutX() == killX && r11.getLayoutY() == killY)
                    || (r11k.getLayoutX() == killX && r11k.getLayoutY() == killY)) {
                r11.setLayoutX(0);
                r11.setLayoutY(0);
                r11.setVisible(false);
                r11k.setLayoutX(0);
                r11k.setLayoutY(0);
                r11k.setVisible(false);
                System.out.println("Removed r11");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((r12.getLayoutX() == killX && r12.getLayoutY() == killY)
                    || (r12k.getLayoutX() == killX && r12k.getLayoutY() == killY)) {
                r12.setLayoutX(0);
                r12.setLayoutY(0);
                r12.setVisible(false);
                r12k.setLayoutX(0);
                r12k.setLayoutY(0);
                r12k.setVisible(false);
                System.out.println("Removed r12");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } //CHECK BLACK
            else if ((b1.getLayoutX() == killX && b1.getLayoutY() == killY)
                    || (b1k.getLayoutX() == killX && b1k.getLayoutY() == killY)) {
                b1.setLayoutX(0);
                b1.setLayoutY(0);
                b1.setVisible(false);
                b1k.setLayoutX(0);
                b1k.setLayoutY(0);
                b1k.setVisible(false);
                System.out.println("Removed b1");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((b2.getLayoutX() == killX && b2.getLayoutY() == killY)
                    || (b2k.getLayoutX() == killX && b2k.getLayoutY() == killY)) {
                b2.setLayoutX(0);
                b2.setLayoutY(0);
                b2.setVisible(false);
                b2k.setLayoutX(0);
                b2k.setLayoutY(0);
                b2k.setVisible(false);
                System.out.println("Removed b2");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((b3.getLayoutX() == killX && b3.getLayoutY() == killY)
                    || (b3k.getLayoutX() == killX && b3k.getLayoutY() == killY)) {
                b3.setLayoutX(0);
                b3.setLayoutY(0);
                b3.setVisible(false);
                b3k.setLayoutX(0);
                b3k.setLayoutY(0);
                b3k.setVisible(false);
                System.out.println("Removed b3");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((b4.getLayoutX() == killX && b4.getLayoutY() == killY)
                    || (b4k.getLayoutX() == killX && b4k.getLayoutY() == killY)) {
                b4.setLayoutX(0);
                b4.setLayoutY(0);
                b4.setVisible(false);
                b4k.setLayoutX(0);
                b4k.setLayoutY(0);
                b4k.setVisible(false);
                System.out.println("Removed b4");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((b5.getLayoutX() == killX && b5.getLayoutY() == killY)
                    || (b5k.getLayoutX() == killX && b5k.getLayoutY() == killY)) {
                b5.setLayoutX(0);
                b5.setLayoutY(0);
                b5.setVisible(false);
                b5k.setLayoutX(0);
                b5k.setLayoutY(0);
                b5k.setVisible(false);
                System.out.println("Removed b5");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((b6.getLayoutX() == killX && b6.getLayoutY() == killY)
                    || (b6k.getLayoutX() == killX && b6k.getLayoutY() == killY)) {
                b6.setLayoutX(0);
                b6.setLayoutY(0);
                b6.setVisible(false);
                b6k.setLayoutX(0);
                b6k.setLayoutY(0);
                b6k.setVisible(false);
                System.out.println("Removed b6");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((b7.getLayoutX() == killX && b7.getLayoutY() == killY)
                    || (b7k.getLayoutX() == killX && b7k.getLayoutY() == killY)) {
                b7.setLayoutX(0);
                b7.setLayoutY(0);
                b7.setVisible(false);
                b7k.setLayoutX(0);
                b7k.setLayoutY(0);
                b7k.setVisible(false);
                System.out.println("Removed b7");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((b8.getLayoutX() == killX && b8.getLayoutY() == killY)
                    || (b8k.getLayoutX() == killX && b8k.getLayoutY() == killY)) {
                b8.setLayoutX(0);
                b8.setLayoutY(0);
                b8.setVisible(false);
                b8k.setLayoutX(0);
                b8k.setLayoutY(0);
                b8k.setVisible(false);
                System.out.println("Removed b8");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((b9.getLayoutX() == killX && b9.getLayoutY() == killY)
                    || (b9k.getLayoutX() == killX && b9k.getLayoutY() == killY)) {
                b9.setLayoutX(0);
                b9.setLayoutY(0);
                b9.setVisible(false);
                b9k.setLayoutX(0);
                b9k.setLayoutY(0);
                b9k.setVisible(false);
                System.out.println("Removed b9");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((b10.getLayoutX() == killX && b10.getLayoutY() == killY)
                    || (b10k.getLayoutX() == killX && b10k.getLayoutY() == killY)) {
                b10.setLayoutX(0);
                b10.setLayoutY(0);
                b10.setVisible(false);
                b10k.setLayoutX(0);
                b10k.setLayoutY(0);
                b10k.setVisible(false);
                System.out.println("Removed b10");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((b11.getLayoutX() == killX && b11.getLayoutY() == killY)
                    || (b11k.getLayoutX() == killX && b11k.getLayoutY() == killY)) {
                b11.setLayoutX(0);
                b11.setLayoutY(0);
                b11.setVisible(false);
                b11k.setLayoutX(0);
                b11k.setLayoutY(0);
                b11k.setVisible(false);
                System.out.println("Removed b11");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else if ((b12.getLayoutX() == killX && b12.getLayoutY() == killY)
                    || (b12k.getLayoutX() == killX && b12k.getLayoutY() == killY)) {
                b12.setLayoutX(0);
                b12.setLayoutY(0);
                b12.setVisible(false);
                b12k.setLayoutX(0);
                b12k.setLayoutY(0);
                b12k.setVisible(false);
                System.out.println("Removed b12");
                Checker.killRow = -1;
                Checker.killCol = -1;
            } else {
                System.out.println("r1: " + r1.getLayoutX() + " " + r1.getLayoutY());
                System.out.println("r2: " + r2.getLayoutX() + " " + r2.getLayoutY());
                System.out.println("r3: " + r3.getLayoutX() + " " + r3.getLayoutY());
                System.out.println("r4: " + r4.getLayoutX() + " " + r4.getLayoutY());
                System.out.println("r5: " + r5.getLayoutX() + " " + r5.getLayoutY());
                System.out.println("r6: " + r6.getLayoutX() + " " + r6.getLayoutY());
                System.out.println("r7: " + r7.getLayoutX() + " " + r7.getLayoutY());
                System.out.println("r8: " + r8.getLayoutX() + " " + r8.getLayoutY());
                System.out.println("r9: " + r9.getLayoutX() + " " + r9.getLayoutY());
                System.out.println("r10: " + r10.getLayoutX() + " " + r10.getLayoutY());
                System.out.println("r11: " + r11.getLayoutX() + " " + r11.getLayoutY());
                System.out.println("r12: " + r12.getLayoutX() + " " + r12.getLayoutY());

                System.out.println("b1: " + b1.getLayoutX() + " " + b1.getLayoutY());
                System.out.println("b2: " + b2.getLayoutX() + " " + b2.getLayoutY());
                System.out.println("b3: " + b3.getLayoutX() + " " + b3.getLayoutY());
                System.out.println("b4: " + b4.getLayoutX() + " " + b4.getLayoutY());
                System.out.println("b5: " + b5.getLayoutX() + " " + b5.getLayoutY());
                System.out.println("b6: " + b6.getLayoutX() + " " + b6.getLayoutY());
                System.out.println("b7: " + b7.getLayoutX() + " " + b7.getLayoutY());
                System.out.println("b8: " + b8.getLayoutX() + " " + b8.getLayoutY());
                System.out.println("b9: " + b9.getLayoutX() + " " + b9.getLayoutY());
                System.out.println("b10: " + b10.getLayoutX() + " " + b10.getLayoutY());
                System.out.println("b11: " + b11.getLayoutX() + " " + b11.getLayoutY());
                System.out.println("b12: " + b12.getLayoutX() + " " + b12.getLayoutY());

            }

        }

        if (Checker.kingRow != -1 && Checker.kingCol != -1) {
            int killX = Checker.kingCol;
            killX *= 53;
            killX += 19;

            int killY = 7 - Checker.kingRow;
            killY *= 53;
            killY += 21;
            //ASSIGN KING
            //CHECK RED
            if ((r1.getLayoutX() == killX && r1.getLayoutY() == killY)) {
                System.out.println("r1 king");
                if (r1.getLayoutX() != 0) {
                    r1k.setLayoutX(r1.getLayoutX());
                    r1k.setLayoutY(r1.getLayoutY());
                    r1k.setVisible(true);
                    r1.setVisible(false);
                    r1.setId("r1n");
                    r1k.setId("r1");
                    r1.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((r2.getLayoutX() == killX && r2.getLayoutY() == killY)) {
                System.out.println("r2 king");
                if (r2.getLayoutX() != 0) {
                    r2k.setLayoutX(r2.getLayoutX());
                    r2k.setLayoutY(r2.getLayoutY());
                    r2k.setVisible(true);
                    r2.setVisible(false);
                    r2.setId("r2n");
                    r2k.setId("r2");
                    r2.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((r3.getLayoutX() == killX && r3.getLayoutY() == killY)) {
                System.out.println("r3 king");
                if (r3.getLayoutX() != 0) {
                    r3k.setLayoutX(r3.getLayoutX());
                    r3k.setLayoutY(r3.getLayoutY());
                    r3k.setVisible(true);
                    r3.setVisible(false);
                    r3.setId("r3n");
                    r3k.setId("r3");
                    r3.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((r4.getLayoutX() == killX && r4.getLayoutY() == killY)) {
                System.out.println("r4 king");
                if (r4.getLayoutX() != 0) {
                    r4k.setLayoutX(r4.getLayoutX());
                    r4k.setLayoutY(r4.getLayoutY());
                    r4k.setVisible(true);
                    r4.setVisible(false);
                    r4.setId("r4n");
                    r4k.setId("r4");
                    r4.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((r5.getLayoutX() == killX && r5.getLayoutY() == killY)) {
                System.out.println("r5 king");
                if (r5.getLayoutX() != 0) {
                    r5k.setLayoutX(r5.getLayoutX());
                    r5k.setLayoutY(r5.getLayoutY());
                    r5k.setVisible(true);
                    r5.setVisible(false);
                    r5.setId("r5n");
                    r5k.setId("r5");
                    r5.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((r6.getLayoutX() == killX && r6.getLayoutY() == killY)) {
                System.out.println("r6 king");
                if (r6.getLayoutX() != 0) {
                    r6k.setLayoutX(r6.getLayoutX());
                    r6k.setLayoutY(r6.getLayoutY());
                    r6k.setVisible(true);
                    r6.setVisible(false);
                    r6.setId("r6n");
                    r6k.setId("r6");
                    r6.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((r7.getLayoutX() == killX && r7.getLayoutY() == killY)) {
                System.out.println("r7 king");
                if (r7.getLayoutX() != 0) {
                    r7k.setLayoutX(r7.getLayoutX());
                    r7k.setLayoutY(r7.getLayoutY());
                    r7k.setVisible(true);
                    r7.setVisible(false);
                    r7.setId("r7n");
                    r7k.setId("r7");
                    r7.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((r8.getLayoutX() == killX && r8.getLayoutY() == killY)) {
                System.out.println("r8 king");
                if (r8.getLayoutX() != 0) {
                    r8k.setLayoutX(r8.getLayoutX());
                    r8k.setLayoutY(r8.getLayoutY());
                    r8k.setVisible(true);
                    r8.setVisible(false);
                    r8.setId("r8n");
                    r8k.setId("r8");
                    r8.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((r9.getLayoutX() == killX && r9.getLayoutY() == killY)) {
                System.out.println("r9 king");
                if (r9.getLayoutX() != 0) {
                    r9k.setLayoutX(r9.getLayoutX());
                    r9k.setLayoutY(r9.getLayoutY());
                    r9k.setVisible(true);
                    r9.setVisible(false);
                    r9.setId("r9n");
                    r9k.setId("r9");
                    r9.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }

            } else if ((r10.getLayoutX() == killX && r10.getLayoutY() == killY)) {
                System.out.println("r10 king");
                if (r10.getLayoutX() != 0) {
                    r10k.setLayoutX(r10.getLayoutX());
                    r10k.setLayoutY(r10.getLayoutY());
                    r10k.setVisible(true);
                    r10.setVisible(false);
                    r10.setId("r10n");
                    r10k.setId("r10");
                    r10.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((r11.getLayoutX() == killX && r11.getLayoutY() == killY)) {
                System.out.println("r11 king");
                if (r11.getLayoutX() != 0) {
                    r11k.setLayoutX(r11.getLayoutX());
                    r11k.setLayoutY(r11.getLayoutY());
                    r11k.setVisible(true);
                    r11.setVisible(false);
                    r11.setId("r11n");
                    r11k.setId("r11");
                    r11.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((r12.getLayoutX() == killX && r12.getLayoutY() == killY)) {
                System.out.println("r12 king");
                if (r12.getLayoutX() != 0) {
                    r12k.setLayoutX(r12.getLayoutX());
                    r12k.setLayoutY(r12.getLayoutY());
                    r12k.setVisible(true);
                    r12.setVisible(false);
                    r12.setId("r12n");
                    r12k.setId("r12");
                    r12.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } //CHECK BLACK
            else if ((b1.getLayoutX() == killX && b1.getLayoutY() == killY)) {
                System.out.println("b1 king");
                if (b1.getLayoutX() != 0) {
                    b1k.setLayoutX(b1.getLayoutX());
                    b1k.setLayoutY(b1.getLayoutY());
                    b1k.setVisible(true);
                    b1.setVisible(false);
                    b1.setId("b1n");
                    b1k.setId("b1");
                    b1.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((b2.getLayoutX() == killX && b2.getLayoutY() == killY)) {
                System.out.println("b2 king");
                if (b2.getLayoutX() != 0) {
                    b2k.setLayoutX(b2.getLayoutX());
                    b2k.setLayoutY(b2.getLayoutY());
                    b2k.setVisible(true);
                    b2.setVisible(false);
                    b2.setId("b2n");
                    b2k.setId("b2");
                    b2.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((b3.getLayoutX() == killX && b3.getLayoutY() == killY)) {
                System.out.println("b3 king");
                if (b1.getLayoutX() != 0) {
                    b3k.setLayoutX(b3.getLayoutX());
                    b3k.setLayoutY(b3.getLayoutY());
                    b3k.setVisible(true);
                    b3.setVisible(false);
                    b3.setId("b3n");
                    b3k.setId("b3");
                    b3.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((b4.getLayoutX() == killX && b4.getLayoutY() == killY)) {
                System.out.println("b4 king");
                if (b4.getLayoutX() != 0) {
                    b4k.setLayoutX(b4.getLayoutX());
                    b4k.setLayoutY(b4.getLayoutY());
                    b4k.setVisible(true);
                    b4.setVisible(false);
                    b4.setId("b4n");
                    b4k.setId("b4");
                    b4.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((b5.getLayoutX() == killX && b5.getLayoutY() == killY)) {
                System.out.println("b5 king");
                if (b5.getLayoutX() != 0) {
                    b5k.setLayoutX(b5.getLayoutX());
                    b5k.setLayoutY(b5.getLayoutY());
                    b5k.setVisible(true);
                    b5.setVisible(false);
                    b5.setId("b5n");
                    b5k.setId("b5");
                    b5.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((b6.getLayoutX() == killX && b6.getLayoutY() == killY)) {
                System.out.println("b6 king");
                if (b6.getLayoutX() != 0) {
                    b6k.setLayoutX(b6.getLayoutX());
                    b6k.setLayoutY(b6.getLayoutY());
                    b6k.setVisible(true);
                    b6.setVisible(false);
                    b6.setId("b6n");
                    b6k.setId("b6");
                    b6.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((b7.getLayoutX() == killX && b7.getLayoutY() == killY)) {
                System.out.println("b7 king");
                if (b7.getLayoutX() != 0) {
                    b7k.setLayoutX(b7.getLayoutX());
                    b7k.setLayoutY(b7.getLayoutY());
                    b7k.setVisible(true);
                    b7.setVisible(false);
                    b7.setId("b7n");
                    b7k.setId("b7");
                    b7.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((b8.getLayoutX() == killX && b8.getLayoutY() == killY)) {
                System.out.println("b8 king");
                if (b8.getLayoutX() != 0) {
                    b8k.setLayoutX(b8.getLayoutX());
                    b8k.setLayoutY(b8.getLayoutY());
                    b8k.setVisible(true);
                    b8.setVisible(false);
                    b8.setId("b8n");
                    b8k.setId("b8");
                    b8.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((b9.getLayoutX() == killX && b9.getLayoutY() == killY)) {
                System.out.println("b9 king");
                if (b9.getLayoutX() != 0) {
                    b9k.setLayoutX(b9.getLayoutX());
                    b9k.setLayoutY(b9.getLayoutY());
                    b9k.setVisible(true);
                    b9.setVisible(false);
                    b9.setId("b9n");
                    b9k.setId("b9");
                    b9.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((b10.getLayoutX() == killX && b10.getLayoutY() == killY)) {
                System.out.println("b10 king");
                if (b10.getLayoutX() != 0) {
                    b10k.setLayoutX(b10.getLayoutX());
                    b10k.setLayoutY(b10.getLayoutY());
                    b10k.setVisible(true);
                    b10.setVisible(false);
                    b10.setId("b10n");
                    b10k.setId("b10");
                    b10.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((b11.getLayoutX() == killX && b11.getLayoutY() == killY)) {
                System.out.println("b11 king");
                if (b11.getLayoutX() != 0) {
                    b11k.setLayoutX(b11.getLayoutX());
                    b11k.setLayoutY(b11.getLayoutY());
                    b11k.setVisible(true);
                    b11.setVisible(false);
                    b11.setId("b11n");
                    b11k.setId("b11");
                    b11.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            } else if ((b12.getLayoutX() == killX && b12.getLayoutY() == killY)) {
                System.out.println("b12 king");
                if (b12.getLayoutX() != 0) {
                    b12k.setLayoutX(b12.getLayoutX());
                    b12k.setLayoutY(b12.getLayoutY());
                    b12k.setVisible(true);
                    b12.setVisible(false);
                    b12.setId("b12n");
                    b12k.setId("b12");
                    b12.setLayoutX(0);
                    Checker.kingRow = -1;
                    Checker.kingCol = -1;
                }
            }
        }
        Game.turn = (Game.turn + 1) % 2;
        MenuController.obj.checkerGame.nowTurn = (MenuController.obj.checkerGame.nowTurn + 1) % 2;
        this.turn_label.setText("Player " + (Game.turn + 1) + "'s Turn");


    }

}
