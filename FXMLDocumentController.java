/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingamegui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;



/**
 *
 * @author Jasmoon
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane content_area;
    @FXML
    private JFXButton runbtn;
    @FXML
    private JFXCheckBox bfs;
    @FXML
    private JFXCheckBox dfs;
    @FXML
    private JFXCheckBox astar;
    @FXML
    private ImageView closebtn;
    @FXML
    private JFXCheckBox basic;
    @FXML
    private JFXCheckBox prune;
    @FXML
    private JFXCheckBox genetic;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void close_program(MouseEvent event) {
        System.exit(0);
        //Platform.exit();
    }

    @FXML
    public void run_search(MouseEvent event) throws IOException {
       if(bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected()){
           AnchorPane pane=FXMLLoader.load(getClass().getResource("RunSearch.fxml"));
           content_area.getChildren().removeAll();
           content_area.getChildren().setAll(pane);  
       }
       
       else if (!bfs.isSelected()&&dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected()){
         // load dfs fxml
         
       }
       else if (!bfs.isSelected()&&!dfs.isSelected()&&astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected()){
         // load astar fxml
       }
       else if (!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected()){
         // load generic fxml
       }
       else if (!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&basic.isSelected()&&!prune.isSelected()){
         // load basic fxml
       }
       else if (!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&prune.isSelected()){
         // load prune fxml
       }
       else{ // Error message
          if(!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected()){
              JOptionPane.showMessageDialog(null,"Please select a search method to proceed.", "Error", 0);
          }
          else{
              JOptionPane.showMessageDialog(null,"Please select only one search method at a time.");
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
       
        
    }
    
    
}
