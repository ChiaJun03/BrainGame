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
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Jasmoon
 */
public class BrainGameGUI  {
    //dragable/movable variable
    /*
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        //change my program icon
        Image logo = new Image(getClass().getResourceAsStream("logo.png"));
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
      /*  long lhwnd = com.sun.glass.ui.Window.getWindows().get(0).getNativeWindow();
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
        //launch(args);

        Stack<Integer> test = new Stack();
        SearchSpace space = new SearchSpace();
        testCase(space);
        System.out.println(space);
        BestFirstSearch simulation = new BestFirstSearch(space);
        Scanner sc = new Scanner(System.in);
        while (true) {
            int input1 = sc.nextInt();
            int input2 = sc.nextInt();
            if (input1 == -1 && input2 == -1) {
                break;
            } else {
                simulation.search(input1, input2);
                System.out.println(simulation.toString());
            }
            System.out.println(space);
        }

    }

    public static void testCase(SearchSpace simulation) {
        Random rand = new Random();
        int id = 10;
        int num = rand.nextInt(10), toID;
        for (int i = 1; i <= id; i++) {
            simulation.addNode(i, rand.nextInt(9), rand.nextInt(9) + 1);
        }
        for (int i = 1; i <= id; i++) {
            for (int j = 0; j < simulation.get(i).getConnectNum(); j++) {
                toID = rand.nextInt(id - 1) + 1;
                while (simulation.get(i).containsSynapse(toID) || toID == i) {
                    toID = rand.nextInt(id - 1) + 1;
                }
                simulation.addSynapse(i, toID, rand.nextInt(10) + 1, rand.nextInt(9) + 1);
            }
        }
    }
}  


/*
FXMLDocumentController control=new FXMLDocumentController();
        RunSearchController control2 =new RunSearchController();
        
        if(control.searchType().equals("bfs")){
           control2.setText("Breadth First Search");
        }
        
*/
