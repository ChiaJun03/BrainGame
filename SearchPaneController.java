/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Jing Chong
 */
public class SearchPaneController implements Initializable {

    private SearchSpace space;
    private GraphSetup graphObject;
    private String searchMethod;
    private SwingNode defaultGraphPane;
    private SwingNode changedGraphPane;
    @FXML
    private Button deleteButton;
    @FXML
    private AnchorPane searchPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        space = new SearchSpace(1);
        graphObject = new GraphSetup();
        defaultGraphPane = graphObject.setup();
        searchPane.getChildren().add(defaultGraphPane);
    }

    public void setSearchMehod(String searchMethod) {
        this.searchMethod = searchMethod;
    }

    @FXML
    private void deleteNode(MouseEvent event) {
        //GeneticAlgorithm GA = new GeneticAlgorithm(treeMap , 10 , 0.1 , 0.8 ,2 , 6);
        //displayPath(GA.search(1, 2));
        BreadthFirstSearch BFS = new BreadthFirstSearch(new SearchSpace(1));
        BFS.search(1, 2);
        System.out.println("Start Here");
        System.out.println(BFS.getPath());
        displayPath(BFS.getPath());
    }
    
    // need to figure out a way to delay the changes of scene
    public void displayPath(ArrayList<Integer> path){
        ArrayList<Integer> nodeList = new ArrayList<>();
        ArrayList<Synapse> edgeList = new ArrayList<>();
         for (int iterate1 = 0; iterate1 < path.size(); iterate1++) {
            nodeList.add(path.get(iterate1));
            System.out.println("Path list : " + path.get(iterate1));
            if (iterate1 > 0) {
                Neuron temp = space.getTreeMap().get(path.get(iterate1 - 1));
                System.out.println(temp);
                edgeList.add(temp.getSynapseTo(path.get(iterate1)));
            }
            showPath(nodeList,edgeList);
         }
    }
    
    public void showPath(ArrayList<Integer> nodeList , ArrayList<Synapse> edgeList) {
       
            searchPane.getChildren().remove(changedGraphPane);
            changedGraphPane = graphObject.changePath(nodeList, edgeList);
            searchPane.getChildren().add(changedGraphPane);
    }
}


