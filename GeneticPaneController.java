/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import static braingame.RunSearchController.tableStage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Jing Chong
 */
public class GeneticPaneController implements Initializable {
    private SearchSpace space;
    private GraphSetup graphObject;
    private SwingNode defaultGraphPane;
    private SwingNode changedGraphPane;
    private GeneticAlgorithm search;
    
    private Timeline tl;
    protected static Stage chartStage;
    private static XYChart.Series series;
    
    @FXML
    private JFXTextField startnode;
    @FXML
    private JFXTextField endnode;
    @FXML
    private ImageView backbtn;
    @FXML
    private ImageView closebtn;
    @FXML
    private JFXButton searchpath;
    @FXML
    private JFXButton tablebtn;
    @FXML
    private AnchorPane geneticPane;
    @FXML
    private JFXButton graphbtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        space = new SearchSpace(1);
        graphObject = new GraphSetup();
        defaultGraphPane = graphObject.setup();
        geneticPane.getChildren().add(defaultGraphPane);
        
        //create chart
        chartStage = new Stage();
        final NumberAxis xAxis = new NumberAxis(1, 20, 1);
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Generation");
        //creating the chart
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Performance");
        //defining a series
        series = new XYChart.Series();
        series.setName("Fitness");
        
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
        lineChart.setCreateSymbols(false);
       
        chartStage.setScene(scene);
        
        
        //tl.play();

        //BrainGame.draggable(scene, chartStage);

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
       
            geneticPane.getChildren().remove(changedGraphPane);
            changedGraphPane = graphObject.changePath(nodeList, edgeList);
            geneticPane.getChildren().add(changedGraphPane);
    }

    @FXML
    private void back_program(MouseEvent event) throws IOException {
        AnchorPane root=FXMLLoader.load(getClass().getResource("RunSearch.fxml "));       
        geneticPane.getChildren().removeAll();
        geneticPane.getChildren().setAll(root);
    }

    @FXML
    private void close_program(MouseEvent event) {
         System.exit(0);
    }

    @FXML
    private void searchpath() {
        
        series.getData().clear();
        if(search!=null && search.getPath()!=null)
            space.deductLifeTimes(search.getPath());
        
        tl = new Timeline();
        tl.getKeyFrames().add(
        new KeyFrame(Duration.millis(500), 
            new EventHandler<ActionEvent>() {
                int start = Integer.parseInt(startnode.getText());
                int end = Integer.parseInt(endnode.getText());
                int populationSize = 20; // range between 10 to 100
                double mutationRate = 0.1; // range below 10%
                double crossoverRate = 0.8; // range higher than 70&
                int elitismCount = 5; // range 10% of the populationSize
                int tournamentSize = 10; // range 70% of the populationSize
                GeneticAlgorithm search = new GeneticAlgorithm(space.getTreeMap(),populationSize,mutationRate,crossoverRate, elitismCount,tournamentSize);
                //System.out.println("1");
                Population population = search.initPopulation(start,end);
                //System.out.println("2");
                int generation = 1;
                int maxGenerations = 20;
                public void handle(ActionEvent actionEvent) {
                    if(search.isTerminationConditionMet(generation, maxGenerations) == false){
                        population = search.crossoverPopulation(population, end);
                        // Apply mutation
                        search.evalPopulation(population, end);
                        population = search.mutatePopulation(population, start, end);
                        // Evaluate population
                        search.evalPopulation(population, end);

                        population.arrange();
                        population.toString(generation);
                        GeneticPaneController.addData(generation, population.getPopulationFitness());
                        displayPath(population.getFittest(0).getPath());
                        generation++;
                        
                    }else{
                        search.setPath(population);
                        if(search.getPath()==null){
                            displayPath(new ArrayList<Integer>());
                            JOptionPane.showMessageDialog(null,"No Path Available.");
                        }
                        else{
                            displayPath(search.getPath());
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
        
        
        
       
    }

    @FXML
    private void showTable(MouseEvent event) {
        RunSearchController.tableStage.show();
    }    

    @FXML
    private void showGraph(MouseEvent event) {
        chartStage.show();
    }
    
    public static void addData(Integer generation, Double Fitness){
        series.getData().add(new XYChart.Data<>(generation, Fitness));
    }
    
}
