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
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
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
    private Timeline tl;
    private Search search;
    private static boolean isEnd;
    
    @FXML
    private AnchorPane searchPane;
    @FXML
    private JFXTextField startnode;
    @FXML
    private JFXTextField endnode;
    @FXML
    private ImageView closebtn;
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
    private ImageView backbtn;
    @FXML
    private Pane graphPane;
    @FXML
    private ImageView slideArrow;
    @FXML
    private ScrollPane consolePane;
    @FXML
    private Rectangle minimizeC;
    @FXML
    private TextArea console;
    @FXML
    private Circle animatebtn;
    @FXML
    private Rectangle animateback;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        space = new SearchSpace(1);
        graphObject = new GraphSetup();
        defaultGraphPane = graphObject.setup();
        graphPane.getChildren().add(defaultGraphPane);
    }
    
    // need to figure out a way to delay the changes of scene
    public void displayPath(ArrayList<Integer> path){
        ArrayList<Integer> nodeList = new ArrayList<>();
        ArrayList<Synapse> edgeList = new ArrayList<>();
        for (int iterate1 = 0; iterate1 < path.size(); iterate1++) {
            nodeList.add(path.get(iterate1));
            //System.out.println("Path list : " + path.get(iterate1));
            if (iterate1 > 0) {
                //System.out.println(path.get(iterate1 - 1));
                Neuron temp = space.getTreeMap().get(path.get(iterate1 - 1));
                //System.out.println(space);
                //System.out.println(temp);
                edgeList.add(temp.getSynapseTo(path.get(iterate1)));
            }
            showPath(nodeList,edgeList);
        }
    }
    
    public void showPath(ArrayList<Integer> nodeList , ArrayList<Synapse> edgeList) {
       
            graphPane.getChildren().remove(changedGraphPane);
            changedGraphPane = graphObject.changePath(nodeList, edgeList);
            graphPane.getChildren().add(changedGraphPane);
    }

    @FXML
    private void back_program(MouseEvent event) throws IOException {
        try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("RunSearch.fxml"));
            AnchorPane root= (AnchorPane) loader.load();
            searchPane.getChildren().removeAll();
            searchPane.getChildren().setAll(root);  

            RunSearchController control=loader.getController();
            control.setHeader("Searching Algorithm", 40.0);
            
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void close_program(MouseEvent event){
         System.exit(0);
    }

    @FXML
    private void searchpath(MouseEvent event){
        console.setText("Start\n");
        if(isOnlyOneSelected()){
            double fps;
            isEnd = false;
            selectAlgo();
            int start = Integer.parseInt(startnode.getText());
            int end = Integer.parseInt(endnode.getText());
            search.preSearch(start);
            searchPane.getChildren().remove(defaultGraphPane);
            if(space.contains(start)&&space.contains(end)){
                tl = new Timeline();
                if(animatebtn.getTranslateX()==0)
                    fps = 1;
                else
                    fps = 100;
                tl.getKeyFrames().add(
                new KeyFrame(Duration.millis(fps), 
                    new EventHandler<ActionEvent>(){
                        Search search = getSearch();
                        int start = Integer.parseInt(startnode.getText());
                        int end = Integer.parseInt(endnode.getText());
                        
                        public void handle(ActionEvent actionEvent){
                            search.search(start, end);
                            if(search.trackPath()!=null){
                                if(animatebtn.getTranslateX()==18)
                                    displayPath(search.trackPath());
                                console.appendText(search.console()+"\n");
                            }
                            if(isEnd){
                                if(search.getPath()==null){
                                    displayPath(new ArrayList<Integer>());
                                    console.appendText("No Path Available\nEnd\n");
                                    JOptionPane.showMessageDialog(null,"No Path Available.");
                                }
                                else{
                                    displayPath(search.getPath());
                                    console.appendText(search.getPath()+"\nEnd\n");
                                    JOptionPane.showMessageDialog(null, search);
                                }
                                tl.stop();
                            }
                        }
                     }
                ));
                tl.setCycleCount(Animation.INDEFINITE);
                tl.setAutoReverse(true);
                tl.play();
            }else{
                JOptionPane.showMessageDialog(null,"No Path Available.");
                console.appendText("No Path Available\n");
            }
        }else{
            if(!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!basic.isSelected()&&!prune.isSelected())
                JOptionPane.showMessageDialog(null,"Please select a search method to proceed.", "Error", 0);
            else
                JOptionPane.showMessageDialog(null,"Please select only one search method at a time.");
        }
    }

    @FXML
    private void showTable(MouseEvent event) {
        RunSearchController.tableStage.show();
    }
    
    /**Check whether only one check box is selected
     * @return true if only one is checked else false
     */
    public boolean isOnlyOneSelected(){
        return (bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!basic.isSelected()&&!prune.isSelected())||
            (!bfs.isSelected()&&dfs.isSelected()&&!astar.isSelected()&&!basic.isSelected()&&!prune.isSelected())||
            (!bfs.isSelected()&&!dfs.isSelected()&&astar.isSelected()&&!basic.isSelected()&&!prune.isSelected())||
            (!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!basic.isSelected()&&!prune.isSelected())||
            (!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&basic.isSelected()&&!prune.isSelected())||
            (!bfs.isSelected()&&!dfs.isSelected()&&!astar.isSelected()&&!basic.isSelected()&&prune.isSelected());
    }
    
    public void selectAlgo(){
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
        else if(basic.isSelected()){
            search = new BasicSearch(space);
        }
        else{
            search = new PruneSearch(space);
        }
        
    }
    
    public Search getSearch(){
        return this.search;
    }
    
    public static void setIsEnd(boolean cond){
        isEnd = cond;
    }
    
    @FXML
    public void slideConsoleIn(){
        TranslateTransition openConsole=new TranslateTransition(new Duration(350), consolePane);
        openConsole.setToX(consolePane.getWidth());
        openConsole.play();
        
    }
    
    @FXML
    public void slideConsoleOut(){
        TranslateTransition closeConsole=new TranslateTransition(new Duration(350), consolePane);
        closeConsole.setToX(-(consolePane.getWidth()));
        closeConsole.play();
    }
    
    public TextArea getConsole(){
        return console;
    }
    
    @FXML
    public void animateSwitch(){
        TranslateTransition slideOff = new TranslateTransition(new Duration(50), animatebtn);
        slideOff.setToX(0);
        
        TranslateTransition slideOn = new TranslateTransition(new Duration(50), animatebtn);
        slideOn.setToX(18);
        
        if(animatebtn.getTranslateX()!=0){
            slideOff.play();
            animateback.setFill(Color.WHITE);
        }else{
            slideOn.play();
            animateback.setFill(Color.GREENYELLOW);
        }
    }
    
}