/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Jing Chong
 */
public class TableController implements Initializable {

    @FXML
    private TableColumn<TableNode, Integer> nodeID;
    @FXML
    private TableColumn<TableNode, Integer> numConnection;
    @FXML
    private TableColumn<TableNode, Integer> lifetime;
    @FXML
    private TableColumn<TableNode, Integer> source;
    @FXML
    private TableColumn<TableNode, Integer> destination;
    @FXML
    private TableColumn<TableNode, Integer> time;
    @FXML
    private TableColumn<TableNode, Integer> distance;
    @FXML
    private TableView<TableNode> neuronTab;
    @FXML
    private TableView<TableNode> synapseTab;

    private ObservableList<TableNode> nodeData = FXCollections.observableArrayList();
    private ObservableList<TableNode> synapseData = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nodeID.setCellValueFactory(new PropertyValueFactory<>("nodeID"));
        numConnection.setCellValueFactory(new PropertyValueFactory<>("numConnection"));
        lifetime.setCellValueFactory(new PropertyValueFactory<>("lifetime"));
        source.setCellValueFactory(new PropertyValueFactory<>("source"));
        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        distance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        
        neuronTab.setItems(nodeData);
        synapseTab.setItems(synapseData);
        
    }
    
    /**Add a neuron data into neuron table
     * @param nodeID
     * @param numConnection
     * @param lifetime 
     */
    public void addTableNode(Integer nodeID, Integer numConnection, Integer lifetime){
        nodeData.add(new TableNode (nodeID, numConnection, lifetime));
    }
    
    /**Add a synapse data into synapse table
     * @param source
     * @param destination
     * @param time
     * @param distance 
     */
    public void addTableSynapse(Integer source, Integer destination, Integer time, Integer distance){
        synapseData.add(new TableNode (source, destination, time, distance));
    }
    
    
    
}

