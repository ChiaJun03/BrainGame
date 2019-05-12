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
//Import JNA library
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import static com.sun.jna.platform.win32.WinUser.GWL_STYLE;


    


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
        
        /* Minimize function but not effective
        root.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                stage.setIconified(true);  
            } 
        });
        */
        
        //try minimize ---> using JNA library
        long lhwnd = com.sun.glass.ui.Window.getWindows().get(0).getNativeWindow();
        Pointer lpVoid = new Pointer(lhwnd);
        WinDef.HWND hwnd = new WinDef.HWND(lpVoid);
        final User32 user32 = User32.INSTANCE;
        int oldStyle = user32.GetWindowLong(hwnd, GWL_STYLE);
        System.out.println(Integer.toBinaryString(oldStyle));
        int newStyle = oldStyle | 0x00020000;//WS_MINIMIZEBOX
        System.out.println(Integer.toBinaryString(newStyle));
        user32.SetWindowLong(hwnd, GWL_STYLE, newStyle);
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