/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


/**
 * FXML Controller class
 *
 * @author Jasmoon
 */
public class RunSearchController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private Label header2;
    @FXML
    private JFXTextField noNeuron;
    @FXML
    private Label ID;
    @FXML
    private JFXTextField ConnectedNeuron;
    @FXML
    private JFXTextField Lifetime;
    @FXML
    private JFXRadioButton manualMode;
    @FXML
    private ToggleGroup modeSelection;
    @FXML
    private JFXRadioButton autoMode;
    @FXML
    private Label inputlbl;
    @FXML
    private JFXButton searchbtn;
    @FXML
    private VBox vbox1;
    @FXML
    private VBox vbox2;
    @FXML
    private JFXTextField connectedID;
    @FXML
    private JFXTextField time;
    @FXML
    private JFXTextField distance;
    @FXML
    private JFXButton sub1;
    @FXML
    private JFXButton sub2;
 
    int count=0;
    int count2=0;
    int a;
    int b;
    int c;
    private TableController control;
    private Graph<Integer , Synapse> graph;
    private SearchSpace space;
    @FXML
    private JFXButton tablebtn;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        space = new SearchSpace();
        //flashanime();
        bounceanime();
        // Initially 
        inputlbl.setVisible(false);
        ID.setVisible(false);
        ConnectedNeuron.setVisible(false);
        Lifetime.setVisible(false);
        vbox2.setVisible(false);
        sub1.setVisible(false);
        sub2.setVisible(false);
        searchbtn.setVisible(false);
        //noNeuron != 0
        ID.setText("ID "+1);
        
        //generate Graph
        graph = new DirectedSparseGraph<>();
    }
    
    /**To set the header of this pane according to the searching method selected in the menu pane
     * @param method selected by the user
     * @param position of the header to be displayed
     */
    public void setHeader(String method,Double position){
        header2.setText(method);
        header2.setTranslateX(position);
    }
    
    @FXML
    /**Change the pane to the menu's pane when user click back arrow
     */
    private void back_to_main() throws IOException {
        //temporarily no problem 
        //if got problem kindly refer 'Test' file and change how the FXML scene loads....
        AnchorPane content_area=FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));        
        pane.getChildren().removeAll();
        pane.getChildren().setAll(content_area);
    }
    
    @FXML
    /**Show the input slot based on mode selected by user
     */
    private void modeSelection(){
        if(autoMode.isSelected()){
            inputlbl.setVisible(false);
            ID.setVisible(false);
            ConnectedNeuron.setVisible(false);
            Lifetime.setVisible(false);
            vbox2.setVisible(false);
            sub1.setVisible(true);
            sub2.setVisible(false);
            // submit button "sub1" transition move up
            translateUPanime();
        }else{
            inputlbl.setVisible(true);
            ID.setVisible(true);
            ConnectedNeuron.setVisible(true);
            Lifetime.setVisible(true);
            sub1.setVisible(true);
            vbox2.setVisible(false);
            sub2.setVisible(false);
            // submit button "sub1" transition move down
            ReversetranslateUPanime();
        }
        fadeanime();
    }
    
    @FXML
    /**Proceed to the next step after user click the submit 1 button
     * if auto mode is selected, auto generate search space
     * if manual mode is selected, show text field for user input the neuron's info
     * output the search space in a table
     * a = number of neurons
     * b = neuron id
     * c = number of connections
     * d = lifetime
     * e = synapse's destination id
     * f = time
     * g = distance
     */
    private void submit1() {
        
        //check whether user's input is an integer and is a 0 or not
        //if yes, display error message
        if(!(isInt(noNeuron.getText().trim()))||Integer.parseInt(noNeuron.getText().trim())<=0){
            JOptionPane.showMessageDialog(null,"Please enter a valid number for Number of neurons", "Error", 0);
            noNeuron.clear();
            return; //exit
        }
        
        //for auto case
        if(autoMode.isSelected()){
            Random rand=new Random();
            int amount=Integer.parseInt(noNeuron.getText().trim());
            for(int i=1;i<=amount;i++){
                b=i;
                c=rand.nextInt(amount); //maximum coonected neuron is noNeuron-1
                //set maximum value to 1000??? for d,f,g
                int d=rand.nextInt(5)+1;
                space.addNode(b,c,d);
                control.addTableNode(b, c, d);
                
                //add vertex to the graph
                graph.addVertex(b);

                // e can't be equal to b && e can't be same as previous generated e
                ArrayList<Integer> list = new ArrayList<Integer>();
                for (int j = 1; j <= amount; j++) {
                   list.add(new Integer(j));
                }
                // remove self looping
                list.remove((Integer)b);
                //shuffle the destination id in list
                Collections.shuffle(list);
                //choose the first c number of ids in the list
                for(int j=1;j<=c;j++){
                    int e= list.get(j-1);
                    int f=rand.nextInt(10)+1;
                    int g=rand.nextInt(10)+1;
                    space.addSynapse(b,e,f,g);
                    control.addTableSynapse(b, e, f, g);
                    
                    //add edge to graph
                    graph.addEdge(space.get(b).getSynapseTo(e), b, e, EdgeType.DIRECTED);
                    
                }
                list.clear();
            }
            
            if(amount==1)
                JOptionPane.showMessageDialog(null,"1 neuron have been generated completely. Please click Search to proceed.");
            else
                JOptionPane.showMessageDialog(null,amount+" neurons have been generated completely. Please click Search to proceed.");
            searchbtn.setVisible(true);
            return;   
        }
        
        else{
            //counter for neuron id
            count++;
            a = Integer.parseInt(noNeuron.getText().trim());
            
            inputlbl.setVisible(true);
            ID.setVisible(true);
            vbox1.setVisible(true);
            sub1.setVisible(true);
            vbox2.setVisible(false);
            sub2.setVisible(false);
            
            b=count;
            c= Integer.parseInt(ConnectedNeuron.getText().trim());
            int d= Integer.parseInt(Lifetime.getText().trim());
            space.addNode(b,c,d);
            control.addTableNode(b, c, d);
            //add node to the graph
            graph.addVertex(b);
            
            if(c==0){
                if(count<a){
                    ConnectedNeuron.clear();
                    Lifetime.clear();
                    ID.setText("ID "+(count+1));
                }else{
                    vbox2.setVisible(false);
                    sub2.setVisible(false);

                    if(a==1)
                        JOptionPane.showMessageDialog(null,"You have entered 1 neuron. Please click Search to proceed.");
                    else
                        JOptionPane.showMessageDialog(null,"You have entered "+a+" neurons. Please click Search to proceed.");

                    searchbtn.setVisible(true);
                }
            }else{
                ID.setText("ID "+count+" --connecting--");
                inputlbl.setVisible(true);
                ID.setVisible(true);
                vbox1.setVisible(false);
                sub1.setVisible(false);
                vbox2.setVisible(true);
                sub2.setVisible(true); 
            }
        }
        
    }
    
    @FXML
    /**Proceed to next step after user input the connection
     * if it is the last connection, return to input neuron
     * else ask for the user to input connection again
     * if it is the last connection of last neuron, proceed to searching
     */
    private void submit2() {
        count2++;
        int e= Integer.parseInt(connectedID.getText().trim());
        int f= Integer.parseInt(time.getText().trim());
        int g= Integer.parseInt(distance.getText().trim());
        space.addSynapse(b,e,f,g);
        control.addTableSynapse(b, e, f, g);
        graph.addEdge(space.get(b).getSynapseTo(e), b, e, EdgeType.DIRECTED);
        
        //after user input the last connection
        if(count2 == c){
            if(count<a){
                 ID.setText("ID "+(count+1));
                inputlbl.setVisible(true);
                ID.setVisible(true);
                vbox1.setVisible(true);
                sub1.setVisible(true);
                vbox2.setVisible(false);
                sub2.setVisible(false);
            }else{
                 inputlbl.setVisible(false);
                ID.setVisible(false);
                vbox1.setVisible(false);
                sub1.setVisible(false);
                vbox2.setVisible(false);
                sub2.setVisible(false);

                if(a==1)
                    JOptionPane.showMessageDialog(null,"You have entered 1 neuron. Please click Search to proceed.");
                else
                    JOptionPane.showMessageDialog(null,"You have entered "+a+" neurons. Please click Search to proceed.");

                searchbtn.setVisible(true);
            }
            ConnectedNeuron.clear();
            Lifetime.clear();
            //reset count 2
            count2=0;
        }
        //rest textfield for e,f,g  
        connectedID.clear();
        time.clear();
        distance.clear();
    }

    @FXML
    /**Change to searching pane after user click search button
     */
    private void searchPath() {
        // reset count,count2
        count=0;
        count2=0;

        // reset every textfield for a,c,d,e,f,g
        noNeuron.clear();
        ConnectedNeuron.clear();
        Lifetime.clear();
        connectedID.clear();
        time.clear();
        distance.clear();
        

        try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("SearchPane.fxml"));
            GraphSetup gs = new GraphSetup();
            gs.setGraph(graph);
            AnchorPane root= (AnchorPane) loader.load();
            pane.getChildren().removeAll();
            pane.getChildren().setAll(root);
            SearchPaneController control=loader.getController();
            control.setSearchMethod(header2.getText());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    /**Show the search space in the form of table in a new window
     */
    @FXML
    public void showTable(){
        Stage tableStage = new Stage();
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("Table.fxml"));
            Parent root= (Parent) loader.load();
            control = loader.getController();
            tableStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            tableStage.setScene(scene);
            tableStage.show();

            BrainGame.draggable(root, tableStage);

        } catch (IOException ex) {
            Logger.getLogger(RunSearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //animation effect for text/object
    private void animation(){
        ScaleTransition transition = new ScaleTransition(Duration.seconds(3),header2);
        transition.setToX(-1.5);
        transition.setToY(-1.5);
        transition.setAutoReverse(true);
        transition.setCycleCount(ScaleTransition.INDEFINITE);
        transition.play();
    }
    
    private void flashanime(){
        FadeTransition transition = new FadeTransition(Duration.millis(500), header2);
        transition.setFromValue(0.0);
        transition.setToValue(1.0);
        transition.setAutoReverse(true);
        transition.setCycleCount(ScaleTransition.INDEFINITE);
        transition.play();
    }
    
    private void fadeanime(){
        FadeTransition transition = new FadeTransition(Duration.millis(1000),sub1);
        transition.setFromValue(0.0);
        transition.setToValue(1.0);
        transition.setAutoReverse(true);
        transition.play();
    }
    
    private void bounceanime(){
        TranslateTransition trans = new TranslateTransition(Duration.millis(300), header2);
        trans.setFromY(25);
        trans.setToY(15);
        trans.setCycleCount(4); //TranslateTransition.INDEFINITE
        trans.setAutoReverse(true);
        trans.setCycleCount(ScaleTransition.INDEFINITE);
        trans.play();  
    }
    
    private void translateUPanime(){
        TranslateTransition trans = new TranslateTransition(Duration.millis(500), sub1);  //path transition
        trans.setFromY(5);
        trans.setToY(-180);
        trans.play();  
    }
    
    private void ReversetranslateUPanime(){
        TranslateTransition trans = new TranslateTransition(Duration.millis(500), sub1);  //path transition
        trans.setFromY(-180);
        trans.setToY(5);
        trans.play();  
    }
    //animation effect for text /object
    
    /**A validator to check whether a user input is an integer
     * @param input of the user
     * @return true if it is an integer else false
     */
    public boolean isInt(String input){
        try{
          Integer.parseInt( input );
          return true;
        }catch( Exception e ){ 
          return false;
        }
    }
    
    
    @FXML
    private void checkModeSelected() {
        if(!(autoMode.isSelected()) && !(manualMode.isSelected())){
               JOptionPane.showMessageDialog(null,"Please select a mode to proceed.", "Error", 0);
        }
    }
    
}


/*
@FXML
private void submit1(MouseEvent event) {
        count=count+1;
        if (noNeuron.getText().isEmpty()){
            //Validate noNeuron...
            //Can upgrade to check input type
            JOptionPane.showMessageDialog(null,"Please input a number", "Error", 0);
        }
        a= Integer.parseInt(noNeuron.getText().trim());
        if(count<=a){
            b=count;
            c= Integer.parseInt(ConnectedNeuron.getText().trim());
            int d= Integer.parseInt(Lifetime.getText().trim());
            //space.addNode(b,c,d); 
            
            //next section -- connected synapse
            if(c==0){
                // if it is last node and have no connected node, will show a message asking user to click start for search
                if(count==a){
                    inputlbl.setVisible(false);
                    ID.setVisible(false);
                    ConnectedNeuron.setVisible(false);
                    Lifetime.setVisible(false);
                    sub1.setVisible(false);
                    vbox2.setVisible(false);
                    sub2.setVisible(false);
                    
                    if(a==1){
                        JOptionPane.showMessageDialog(null,"You have entered 1 neuron. Please click Search to proceed.");
                    }
                    else
                        JOptionPane.showMessageDialog(null,"You have entered "+a+" neurons. Please click Search to proceed.");      
                }
               ID.setText("ID "+(count+1));
               ConnectedNeuron.clear();
               Lifetime.clear();
               
               vbox1.setVisible(true);
               sub1.setVisible(true);
               
               vbox2.setVisible(false);
               sub2.setVisible(false);
            }
            else{
                    ID.setText("ID "+count+" --connecting--");
                    vbox1.setVisible(false);
                    sub1.setVisible(false);
                    ConnectedNeuron.clear();
                    Lifetime.clear();
                    
                    vbox2.setVisible(true);
                    sub2.setVisible(true);
                }
                
            }
        }
           
    @FXML
    private void submit2(MouseEvent event) {
        count2=count2+1;
        if(count2<=c){  //problem
           if (count2==c){ //problem
               int e= Integer.parseInt(connectedID.getText().trim());
               int f= Integer.parseInt(time.getText().trim());
               int g= Integer.parseInt(distance.getText().trim());
               //space.addSynapse(b,e,f,g);
               
               connectedID.clear();
               time.clear();
               distance.clear();
               
              if(count==a){
                  inputlbl.setVisible(false);
                  ID.setVisible(false);
                  ConnectedNeuron.setVisible(false);
                  Lifetime.setVisible(false);
                  sub1.setVisible(false);
                  vbox2.setVisible(false);
                  sub2.setVisible(false);
                    
                  if(a==1)
                    JOptionPane.showMessageDialog(null,"You have entered 1 neuron. Please click Search to proceed.");
                  else
                    JOptionPane.showMessageDialog(null,"You have entered "+a+" neurons. Please click Search to proceed.");    
              }
              else{
                ID.setText("ID "+(count+1));
                vbox2.setVisible(false);
                sub2.setVisible(false);
                vbox1.setVisible(true);
                sub1.setVisible(true);
                !!! //reset
                count2=0;
              }
                  
           }
           int e= Integer.parseInt(connectedID.getText().trim());
           int f= Integer.parseInt(time.getText().trim());
           int g= Integer.parseInt(distance.getText().trim());
           //space.addSynapse(b,e,f,g);
           
           connectedID.clear();
           time.clear();
           distance.clear();
        } 
    }
*/