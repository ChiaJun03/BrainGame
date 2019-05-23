/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;

/**
 * FXML Controller class
 *
 * @author Jasmoon
 */
public class BreadthFirstSearchController implements Initializable {

    @FXML
    private AnchorPane bfspane;
    @FXML
    private ProgressBar progressbar;
    @FXML
    private ImageView backbtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // .setOnSucceded( e-> progressbar.setVisible(false));
    }    

    @FXML
    private void back_to_main(MouseEvent event) throws IOException {
        //temporarily no problem 
        //if got problem kindly refer 'Test' file and change how the FXML scene loads....
        AnchorPane content_area=FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));        
        bfspane.setBackground(Background.EMPTY); // remove bfspane orange background
        bfspane.getChildren().removeAll();
        bfspane.getChildren().setAll(content_area);
    }
    
}
