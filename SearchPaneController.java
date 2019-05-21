/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import javax.swing.JOptionPane;

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
    private Search search;
    
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
    @FXML
    private JFXButton tablebtn;
    @FXML
    private JFXCheckBox basic;
    @FXML
    private JFXCheckBox bfs;
    @FXML
    private JFXCheckBox dfs;
    @FXML
    private JFXCheckBox astar;
    @FXML
    private JFXCheckBox prune;
    @FXML
    private JFXCheckBox genetic;

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

    public void setSearchMethod(String searchMethod) {
        this.searchMethod = searchMethod;
        
        if(searchMethod.equalsIgnoreCase("basic search"))
            basic.setSelected(true);
        else if(searchMethod.equalsIgnoreCase("breadth first search"))
            bfs.setSelected(true);
        else if(searchMethod.equalsIgnoreCase("depth first search"))
            dfs.setSelected(true);
        else if(searchMethod.equalsIgnoreCase("prune search"))
            prune.setSelected(true);
        else if(searchMethod.equalsIgnoreCase("best first search"))
            astar.setSelected(true);
        else
            genetic.setSelected(true);
    }

    
    // need to figure out a way to delay the changes of scene
    public void displayPath(ArrayList<Integer> path){
        ArrayList<Integer> nodeList = new ArrayList<>();
        ArrayList<Synapse> edgeList = new ArrayList<>();
        for (int iterate1 = 0; iterate1 < path.size(); iterate1++) {
            nodeList.add(path.get(iterate1));
            System.out.println("Path list : " + path.get(iterate1));
            if (iterate1 > 0) {
                System.out.println(path.get(iterate1 - 1));
                Neuron temp = space.getTreeMap().get(path.get(iterate1 - 1));
                System.out.println(space);
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
        
        if(isOnlyOneSelected()){
            int start = Integer.parseInt(startnode.getText());
            int end = Integer.parseInt(endnode.getText());
            if(search!=null && search.getPath()!=null)
                space.deductLifeTimes(search.getPath());
        
            if(bfs.isSelected()){
                search = new BreadthFirstSearch(space);
            }
            else if(dfs.isSelected()){
                search = new DepthFirstSearch(space);
            }
            else if(astar.isSelected()){
                search = new BestFirstSearch(space);
            }
            else if(genetic.isSelected()){
                int populationSize = 20; // range between 10 to 100
                double mutationRate = 0.1; // range below 10%
                double crossoverRate = 0.8; // range higher than 70&
                int elitismCount = 5; // range 10% of the populationSize
                int tournamentSize = 10; // range 70% of the populationSize
                search = new GeneticAlgorithm(space.getTreeMap(),populationSize,mutationRate,crossoverRate, elitismCount,tournamentSize);
            }
            else if(basic.isSelected()){
                search = new BasicSearch(space);
            }
            else{
                search = new PruneSearch(space);
            }
            
            search.search(start, end);
        
            if(search.getPath()==null){
                displayPath(new ArrayList<Integer>());
                JOptionPane.showMessageDialog(null,"No Path Available.");
            }
            else{
                displayPath(search.getPath());
                JOptionPane.showMessageDialog(null, search);
            }
        
        }
        else{
            if(!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected())
                JOptionPane.showMessageDialog(null,"Please select a search method to proceed.", "Error", 0);
            else
                JOptionPane.showMessageDialog(null,"Please select only one search method at a time.");
        }
    }

    @FXML
    private void showTable(MouseEvent event) {
    }
    
    @FXML
    /**Change the pane of window according to the check box checked by user
     */
    public void run_search() throws IOException {
        
        
    }
    
    /**Check whether only one check box is selected
     * @return true if only one is checked else false
     */
    public boolean isOnlyOneSelected(){
        return (bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected())||
            (!bfs.isSelected()&&dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected())||
            (!bfs.isSelected()&&!dfs.isSelected()&&astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected())||
            (!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&genetic.isSelected()&&!basic.isSelected()&&!prune.isSelected())||
            (!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&basic.isSelected()&&!prune.isSelected())||
            (!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!genetic.isSelected()&&!basic.isSelected()&&prune.isSelected());
    }
}