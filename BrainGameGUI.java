/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingamegui;


import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


/**
 *
 * @author Jasmoon
 */
public class BrainGameGUI extends Application {
      //dragable/movable variable
      private double xOffset=0;
      private double yOffset = 0;
      
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        //change my program icon
        Image logo=new Image(getClass().getResourceAsStream("logo.png"));
        stage.getIcons().add(logo);
        //
        stage.setScene(scene);
        stage.show();
        
        //get pressed point -> Windows x,y
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        
        // make it move whenever user drag
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            } 
        });
        
        root.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                stage.setIconified(true);  
            } 
        });
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}  


/*
FXMLDocumentController control=new FXMLDocumentController();
        RunSearchController control2 =new RunSearchController();
        
        if(control.searchType().equals("bfs")){
           control2.setText("Breadth First Search");
        }
        
*/