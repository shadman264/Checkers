/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shadman264
 */
public class MenuController implements Initializable {

    @FXML
    private Button h_h;
    @FXML
    private Button h_c;
    @FXML
    private Button c_c;

    public static GameBoardController obj;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void gameMode(MouseEvent event) throws IOException, InterruptedException {
        String sourceID = event.getSource().toString();
        sourceID = sourceID.substring(sourceID.indexOf('=') + 1, sourceID.indexOf(','));
        if (sourceID.equals("h_h") == true) {
            obj = new GameBoardController();
            obj.agent1 = new HumanAgent("Player1");
            obj.agent2 = new AIAgent("Player2");

            obj.game = new Checker(obj.agent1, obj.agent2);
            obj.game.initialize(false);
            
            obj.checkerGame = (Checker) obj.game;

            obj.game.showGameState();
            
            Parent root = FXMLLoader.load(getClass().getResource("GameBoard.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            
            
            
            

        } else if (sourceID.equals("c_c") == true) {

        } else if (sourceID.equals("h_c") == true) {

        }
    }

}
