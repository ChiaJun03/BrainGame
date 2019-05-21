/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import static com.sun.jna.platform.win32.WinUser.GWL_STYLE;
import java.util.Random;
/**
 *
 * @author Jing Chong
 */
public class BrainGame extends Application{
    
    private static double xOffset=0;
    private static double yOffset = 0;
    
    
    public BrainGame(){
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        //change my program icon
        Image logo=new Image(getClass().getResourceAsStream("logo.png"));
        stage.getIcons().add(logo);
        stage.setScene(scene);
        stage.show();
        
        draggable(root, stage);
        
        minimize();
        
    }
    
    /**Enable draggable functionality of window
     * @param root the parent pane of the window
     * @param stage the window to be dragged
     */
    public static void draggable(Parent root, Stage stage){
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
    }
    
    /**Enable minimization functionality by implementing jna library
     */
    public void minimize(){
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
     


    /**Main method for console based simulation
     * @param args 
     */
    /*
    public static void main(String[] args) {
        
        Random rand = new Random();
        SearchSpace simulation = new SearchSpace();
        int [] point = new int [2];
        testCase(simulation);
        //randomPoint(point);
        
        
        
        simulation.addNode(1, 2, 2);
        simulation.addNode(2, 3, 2);
        simulation.addNode(3, 2, 2);
        simulation.addNode(4, 1, 2);
        
        simulation.addSynapse(1, 2, 1, 1);
        simulation.addSynapse(1, 3, 3, 1);
        simulation.addSynapse(2, 4, 1, 1);
        simulation.addSynapse(2, 1, 2, 1);
        simulation.addSynapse(2, 3, 2, 5);
        simulation.addSynapse(3, 1, 5, 5);
        simulation.addSynapse(3, 2, 7, 5);
        simulation.addSynapse(4, 2, 1, 5);
        
        //BasicSearch DFSS = new BasicSearch(simulation);
        
        DepthFirstSearch DFS = new DepthFirstSearch(simulation);
        
        //BreadthFirstSearch BFS = new BreadthFirstSearch(simulation);
        
        //PruneSearch pruning = new PruneSearch(simulation);
        
        
        BestFirstSearch bestSearch = new BestFirstSearch(simulation);
        bestSearch.search(point[0], point[1]);
        System.out.println(bestSearch);
        
        System.out.println(simulation);
        randomPoint(point);
        DFS.search(point[0], point[1]);
        System.out.println(DFS);
        System.out.println(simulation);
        randomPoint(point);
        DFS.search(point[0], point[1]);
        System.out.println(DFS);
        System.out.println(simulation);
        randomPoint(point);
        DFS.search(point[0], point[1]);
        System.out.println(DFS);
        System.out.println(simulation);
        randomPoint(point);
        DFS.search(point[0], point[1]);
        System.out.println(DFS);
        System.out.println(simulation);
        randomPoint(point);
        DFS.search(point[0], point[1]);
        System.out.println(DFS);
        
        System.out.println(simulation);
        randomPoint(point);
        BFS.search(point[0], point[1]);
        System.out.println(BFS);
        
        System.out.println(simulation);
        randomPoint(point);
        BFS.search(point[0], point[1]);
        System.out.println(BFS);
        
        System.out.println(simulation);
        randomPoint(point);
        BFS.search(point[0], point[1]);
        System.out.println(BFS);
        
        System.out.println(simulation);
        randomPoint(point);
        BFS.search(point[0], point[1]);
        System.out.println(BFS);
        
        //search.search(1, 4);
        
        //GeneticAlgorithm GA = new GeneticAlgorithm(simulation, 50, 0.3, 0.8, 5, 5);
        //GA.search(point[0], point[1]);
        
    }
    */

    /**Random generate a search space for the simulation
     * @param simulation an empty search space
     */
    /*
    public static void testCase(SearchSpace simulation){
        Random rand = new Random();
        int id = 10;
        int num, toID;
        for(int i=1; i<=id; i++){
            simulation.addNode(i, num = rand.nextInt(8)+1, rand.nextInt(9)+1);
            for(int j=0; j<num; j++){
                toID = rand.nextInt(id)+1;
                while(simulation.get(i).containsSynapse(toID)||toID==i)
                    toID = rand.nextInt(id)+1;
                simulation.addSynapse(i, toID, rand.nextInt(19)+1, rand.nextInt(9)+1);
            }
        }
    }
    */

    /**Generate random start and end point for search
     * @param point an int array to store the two points
     */
    /*
    public static void randomPoint(int [] point){
        Random rand = new Random();
        int start = rand.nextInt(9)+1;
        int end = rand.nextInt(9)+1;
        while(end==start)
            end = rand.nextInt(9)+1;
        point[0] = start;
        point[1] = end;
    }
    */
}
    /*
    FXMLDocumentController control=new FXMLDocumentController();
    RunSearchController control2 =new RunSearchController();

    if(control.searchType().equals("bfs")){
       control2.setText("Breadth First Search");
    } 
    */

    /* Minimize function but not effective
    root.setOnMouseExited(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            stage.setIconified(true);  
        } 
    });
    */