/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 *
 * @author Jasmoon
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane content_area;
    @FXML
    private ImageView closebtn;
    @FXML
    private ImageView geneticSearch;
    @FXML
    private ImageView normalSearch;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    /**Enable user to close the program 
     * by pressing the close button
     */
    private void close_program() {
        System.exit(0);
        //Platform.exit();
    }
    
    @FXML
    /**Change the pane of window according to the check box checked by user
     */
    public void run_search(MouseEvent event) throws IOException {
        try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("RunSearch.fxml"));
            AnchorPane root= (AnchorPane) loader.load();
            content_area.getChildren().removeAll();
            content_area.getChildren().setAll(root);  

            RunSearchController control=loader.getController();

            if(event.getSource().equals(normalSearch)){
                control.setHeader("Searching Algorithm", 70.0);
            }
            else{
                control.setHeader("Genetic Algorithm",50.0);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    @FXML
    private void searchAEnt(MouseEvent event) {
          ScaleTransition st = new ScaleTransition(Duration.millis(500), normalSearch);
            st.setByX(0.07f);
            st.setByY(0.07f);
            st.setCycleCount(1);
            st.setAutoReverse(true);
            st.play();
    }
    
    @FXML
    private void searchAExit(MouseEvent event) {
        ScaleTransition st = new ScaleTransition(Duration.millis(500), normalSearch);
            st.setByX(-0.07f);
            st.setByY(-0.07f);
            st.setCycleCount(1);
            st.setAutoReverse(true);
            st.play();
    }
    
      @FXML
    private void searchBEnt(MouseEvent event) {
           ScaleTransition st = new ScaleTransition(Duration.millis(500), geneticSearch);
            st.setByX(0.07f);
            st.setByY(0.07f);
            st.setCycleCount(1);
            st.setAutoReverse(true);
            st.play();
    }

    @FXML
    private void searchBExit(MouseEvent event) {
          ScaleTransition st = new ScaleTransition(Duration.millis(500), geneticSearch);
            st.setByX(-0.07f);
            st.setByY(-0.07f);
            st.setCycleCount(1);
            st.setAutoReverse(true);
            st.play();
    }
    
}
       

    // try progress bar
    /*
            run_search.setOnSucceeded(e -> {
            primaryStage.setScene(new Scene(createMainScene.getValue()));
            primaryStage.show();
            loadingStage.hide();
            });
    */
    /*
     //OLD

          AnchorPane pane=FXMLLoader.load(getClass().getResource("RunSearch.fxml"));

        //
        label=new Label("Depth First Search");
        label.setLayoutX(187.0);
        label.setLayoutY(25.0);
        label.getStylesheets().add(RunSearchController.class.getResource("runsearch.css").toExternalForm());
        label.setId("header");
        //

        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(pane,label);  


    */
       
        
    