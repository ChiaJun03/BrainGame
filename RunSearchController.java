/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingamegui;

import static java.awt.SystemColor.text;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Jasmoon
 */
public class RunSearchController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private Label header2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //flashanime();
        bounceanime();
    }    

    @FXML
    private void back_to_main(MouseEvent event) throws IOException {
        AnchorPane content_area=FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        pane.getChildren().removeAll();
        pane.getChildren().setAll(content_area);
    }
    //animation effect for text /object
    private void animation(){
        ScaleTransition transition = new ScaleTransition(Duration.seconds(3),header2);
        transition.setToX(-1.5);
        transition.setToY(-1.5);
        transition.setAutoReverse(true);
        transition.setCycleCount(ScaleTransition.INDEFINITE);
        transition.play();
    }
    
    private void flashanime(){
        FadeTransition transition = new FadeTransition(Duration.millis(500), header2);
        transition.setFromValue(0.0);
        transition.setToValue(1.0);
        transition.setAutoReverse(true);
        transition.setCycleCount(ScaleTransition.INDEFINITE);
        transition.play();
    }
    
    private void bounceanime(){
        TranslateTransition trans = new TranslateTransition(Duration.millis(300), header2);
        trans.setFromY(25);
        trans.setToY(15);
        trans.setCycleCount(4); //TranslateTransition.INDEFINITE
        trans.setAutoReverse(true);
        trans.play();  
    }
    //animation effect for text /object
   
}
