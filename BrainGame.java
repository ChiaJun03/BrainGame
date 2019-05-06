/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

/**
 *
 * @author Chia Jun
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.util.Random;
import java.util.Stack;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Jing Chong
 */
public class BrainGame{
    //protected static final int WIDTH = 1000, HEIGHT = 850;
    
    public BrainGame(){
        
    }

    public static void main(String[] args) {
        //launch(args);
        Stack<Integer> test = new Stack();
        SearchSpace simulation = new SearchSpace();
        /*
        simulation.addNode(1, 4, 1);
        simulation.addNode(2, 2, 1);
        simulation.addNode(3, 1, 1);
        simulation.addNode(4, 1, 1);
        simulation.addNode(5, 1, 1);
        simulation.addNode(6, 1, 1);
        simulation.addNode(7, 0, 1);
        
        
        simulation.addSynapse(1, 2, 2, 5);
        simulation.addSynapse(1, 3, 4, 3);
        simulation.addSynapse(1, 4, 1, 1);
        simulation.addSynapse(1, 5, 3, 2);
        simulation.addSynapse(2, 4, 5, 1);
        simulation.addSynapse(2, 6, 1, 1);
        simulation.addSynapse(3, 5, 2, 4);
        simulation.addSynapse(4, 3, 4, 4);
        simulation.addSynapse(5, 2, 4, 4);
        simulation.addSynapse(6, 7,1, 4);
        
        
        
        System.out.println(simulation.toString());
        simulation.search(1, 7);
        simulation.solution();
        
    }
    */
        //simulation.removeNode(2);
        
        //System.out.println(simulation.toString());
        
        //System.out.println(simulation.nextNode(1, 0));
        
        
        
        testCase(simulation);
        
        System.out.println(simulation);
        
        System.out.println(simulation.checkNode(25));
        System.out.println(simulation.checkNode(1025));

        
        simulation.removeNode(25);
        simulation.removeNode(1025);
        
        System.out.println(simulation);
        
        System.out.println(simulation.checkNode(25));
        System.out.println(simulation.checkNode(1025));
        
        
        //Random  rand = new Random();
        //int start=rand.nextInt(10000)+1 , end = rand.nextInt(10000)+1 ;
        //System.out.println("Search path from node "+start+ " to node "+end+" : ");
        //System.out.println(simulation.toString());
        //simulation.search(start , end);
        //simulation.solution();
        //System.out.println("Search path from node "+start+ " to node "+end+" done !");
        
        
        
    }
    
    public static void testCase(SearchSpace simulation){
        Random rand = new Random();
        int id = 5000 ;
        int num = rand.nextInt(10) , toID ;
        for(int i = 1 ; i<=id ; i++){
            simulation.addNode(i, rand.nextInt(10)+1  , rand.nextInt(9)+1);
        }
        for(int i = 1 ; i<=id ; i++){
            for(int j = 0 ; j < num ; j++){
                toID = rand.nextInt(id-1)+1 ;
                while(simulation.get(i).containsSynapse(toID) || toID==i)
                    toID = rand.nextInt(id-1)+1 ;
                simulation.addSynapse(i, toID, rand.nextInt(19)+1, rand.nextInt(9)+1);
            }
        }
    }
        

    /*
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene newScene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(newScene);
        primaryStage.setTitle("Brain Game");
        primaryStage.show();
        
    }
*/
    
    
}
