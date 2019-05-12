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
    private JFXCheckBox astar;  //astar have been changed to Best First Search, but fx:id is still astar     
    @FXML
    private ImageView closebtn;
    @FXML
    private JFXCheckBox basic;
    @FXML
    private JFXCheckBox prune;
    @FXML
    private JFXCheckBox genetic;
    @FXML
    private Label label;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void close_program(MouseEvent event) {
        System.exit(0);
        //Platform.exit();
    }
    
    
    public String searchType(){
        if(bfs.isSelected()){
            return "bfs";
        }
        return "Error";
    }
    
    @FXML
    void run_search(MouseEvent event) throws IOException {
        // Error message
            
        if((bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected())||(!bfs.isSelected()&&dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected())||(!bfs.isSelected()&&!dfs.isSelected()&&astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected())||(!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected())||(!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&basic.isSelected()&&!prune.isSelected())||(!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&prune.isSelected())){  
        try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("RunSearch.fxml"));
            AnchorPane root= (AnchorPane) loader.load();
            content_area.getChildren().removeAll();
            content_area.getChildren().setAll(root);  
            
            RunSearchController control=loader.getController();
            if(bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected()){
                control.setText("Breadth First Search",30.0);
            }
            else if (!bfs.isSelected()&&dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected()){
                control.setText("Depth First Search",45.0);
            }
            else if (!bfs.isSelected()&&!dfs.isSelected()&&astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected()){
                //astar have been changed to Best First Search, but fx:id is still astar     
                control.setText("Best First Search",50.0);
            }
            else if (!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected()){
                control.setText("Genetic Search",85.0);
            }
            else if (!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&basic.isSelected()&&!prune.isSelected()){
                control.setText("Basic Search",90.0);
            }
            else if (!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&prune.isSelected()){
                control.setText("Prune Search",90.0);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        }
        else{
            if(!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected()){
                JOptionPane.showMessageDialog(null,"Please select a search method to proceed.", "Error", 0);
            }
            else{
                JOptionPane.showMessageDialog(null,"Please select only one search method at a time.");
            } 
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
       
        
    }
