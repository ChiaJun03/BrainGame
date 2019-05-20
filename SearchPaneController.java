/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
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
    private AnchorPane searchPane;
    @FXML
    private JFXTextField startnode;
    @FXML
    private JFXTextField endnode;
    @FXML
    private ImageView backbtn;
    @FXML
    private JFXButton searchpath;

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

    private void deleteNode(MouseEvent event) {
        GeneticAlgorithm GA = new GeneticAlgorithm(space.getTreeMap() , 10 , 0.1 , 0.8 ,2 , 6);
        displayPath(GA.search(1, 2));
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

    @FXML
    private void back_program(MouseEvent event) throws IOException {
        AnchorPane root=FXMLLoader.load(getClass().getResource("RunSearch.fxml "));       
        searchPane.getChildren().removeAll();
        searchPane.getChildren().setAll(root);
    }

    @FXML
    private void close_program(MouseEvent event) {
         System.exit(0);
    }

    @FXML
    private void searchpath(MouseEvent event) {
        
    }
}


