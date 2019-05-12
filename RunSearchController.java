/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingamegui;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;


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

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
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
        
        //noNeuron != 0
        ID.setText("ID "+1);
    }
    

    public void setText(String a,Double b){
        header2.setText(a);
        header2.setTranslateX(b);
    }
    
    @FXML
    private void back_to_main(MouseEvent event) throws IOException {
        //temporarily no problem 
        //if got problem kindly refer 'Test' file and change how the FXML scene loads....
        AnchorPane content_area=FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));        
        pane.getChildren().removeAll();
        pane.getChildren().setAll(content_area);
        
    }
   

    @FXML
    private void auto(MouseEvent event) {
        if(autoMode.isSelected()){
            inputlbl.setVisible(false);
            ID.setVisible(false);
            ConnectedNeuron.setVisible(false);
            //ConnectedNeuron.setDisable(true);
            Lifetime.setVisible(false);
            vbox2.setVisible(false);
            sub1.setVisible(true);
            sub2.setVisible(false);
            // submit button "sub1" transition move up
            translateUPanime();
            fadeanime();
        } 
    }
    
    @FXML
    private void manual(MouseEvent event) {
        if (manualMode.isSelected()){
            inputlbl.setVisible(true);
            ID.setVisible(true);
            ConnectedNeuron.setVisible(true);
            Lifetime.setVisible(true);
            sub1.setVisible(true);
            vbox2.setVisible(false);
            sub2.setVisible(false);
            // submit button "sub1" transition move down
            ReversetranslateUPanime();
            fadeanime();
        }
    }
    
    @FXML
    private void submit1(MouseEvent event) {
        //check
        if(!(isInt(noNeuron.getText().trim()))||Integer.parseInt(noNeuron.getText().trim())<=0){
            JOptionPane.showMessageDialog(null,"Please enter a valid number for Number of neurons", "Error", 0);
            noNeuron.clear();
            return; //exit
        }
        
        if(autoMode.isSelected()){
            Random rand=new Random();
            int amount=Integer.parseInt(noNeuron.getText().trim()); // for message output
            for(int i=1;i<=amount;i++){
             b=i;
             c=rand.nextInt();
             int d=rand.nextInt();
             //space.addNode(b,c,d);
               for(int j=1;j<=c;j++){
                   int e=rand.nextInt();
                   int f=rand.nextInt();
                   int g=rand.nextInt();
                   //space.addSynapse(b,e,f,g);
               }  
            }
                if(amount==1){
                    JOptionPane.showMessageDialog(null,"1 neuron have been generated completely. Please click Search to proceed.");
                }
                else{
                    JOptionPane.showMessageDialog(null,amount+" neurons have been generated completely. Please click Search to proceed.");
                }
                return;   
        }
        
        count++;
        a= Integer.parseInt(noNeuron.getText().trim());
        if(count<a){
            inputlbl.setVisible(true);
            ID.setVisible(true);
            vbox1.setVisible(true);
            sub1.setVisible(true);
            vbox2.setVisible(false);
            sub2.setVisible(false);
            //input
            b=count;
            c= Integer.parseInt(ConnectedNeuron.getText().trim());
            int d= Integer.parseInt(Lifetime.getText().trim());
                    //test test
                        testMethod blabla= new testMethod();
                        blabla.addNode(b,c,d);
                    //test test
            //space.addNode(b,c,d);
            if(c==0){
              //Continue to next neuron input --no connecting neuron--
              ConnectedNeuron.clear();
              Lifetime.clear();
              ID.setText("ID "+(count+1));
            }
            else{ // proceed to next section -- connecting neuron --
                ID.setText("ID "+count+" --connecting--");
                inputlbl.setVisible(true);
                ID.setVisible(true);
                vbox1.setVisible(false);
                sub1.setVisible(false);
                vbox2.setVisible(true);
                sub2.setVisible(true); 
                
            }
        }
        else if (count==a){ // can be optimized lagi
            inputlbl.setVisible(false);
            ID.setVisible(false);
            vbox1.setVisible(false);
            sub1.setVisible(false);
         
            //input
            b=count;
            c= Integer.parseInt(ConnectedNeuron.getText().trim());
            int d= Integer.parseInt(Lifetime.getText().trim());
            //space.addNode(b,c,d);
            if(c==0){
                vbox2.setVisible(false);
                sub2.setVisible(false);
                
                if(a==1){
                    JOptionPane.showMessageDialog(null,"You have entered 1 neuron. Please click Search to proceed.");
                }
                else{
                    JOptionPane.showMessageDialog(null,"You have entered "+a+" neurons. Please click Search to proceed.");
                }
            }
            else{
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
    private void submit2(MouseEvent event) {
         count2++;
         if(count2<c && count<=a){
            int e= Integer.parseInt(connectedID.getText().trim());
            int f= Integer.parseInt(time.getText().trim());
            int g= Integer.parseInt(distance.getText().trim());
            //space.addSynapse(b,e,f,g);
         }
         
         else if (count2==c && count<a){ // proceed back to next neuron input
            int e= Integer.parseInt(connectedID.getText().trim());
            int f= Integer.parseInt(time.getText().trim());
            int g= Integer.parseInt(distance.getText().trim());
            //space.addSynapse(b,e,f,g);
            
            ID.setText("ID "+(count+1));
            inputlbl.setVisible(true);
            ID.setVisible(true);
            vbox1.setVisible(true);
            sub1.setVisible(true);
            vbox2.setVisible(false);
            sub2.setVisible(false);   
            
            ConnectedNeuron.clear();
            Lifetime.clear();
            //reset count 2
            count2=0;
         }
         
         else if (count2==c && count==a){ // -- close all-- proceed for searching
            int e= Integer.parseInt(connectedID.getText().trim());
            int f= Integer.parseInt(time.getText().trim());
            int g= Integer.parseInt(distance.getText().trim());
            //space.addSynapse(b,e,f,g);
            
            inputlbl.setVisible(false);
            ID.setVisible(false);
            vbox1.setVisible(false);
            sub1.setVisible(false);
            vbox2.setVisible(false);
            sub2.setVisible(false);
            
                if(a==1){
                    JOptionPane.showMessageDialog(null,"You have entered 1 neuron. Please click Search to proceed.");
                }
                else{
                    JOptionPane.showMessageDialog(null,"You have entered "+a+" neurons. Please click Search to proceed.");
                }
         
            //reset count 2
            count2=0;
         }
         
         //rest textfield for e,f,g  
         connectedID.clear();
         time.clear();
         distance.clear();
         
         
    }

    @FXML
    private void searchPath(MouseEvent event) {
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
     
     // Load next page
     // Get name of header and proceed to next-respective page of FXML
     if(header2.getText().equals("Breadth First Search")){
         try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("BreadthFirstSearch.fxml"));
            AnchorPane root= (AnchorPane) loader.load();
            pane.getChildren().removeAll();
            pane.getChildren().setAll(root);  
                //if need pass method over, then use
                //RunSearchController control=loader.getController();
            }catch (IOException e){
            e.printStackTrace();
            }
     }
     else if(header2.getText().equals("Depth First Search")){
        /*
         try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("DepthFirstSearch.fxml"));
            AnchorPane root= (AnchorPane) loader.load();
            pane.getChildren().removeAll();
            pane.getChildren().setAll(root);  
                //if need pass method over, then use
                //RunSearchController control=loader.getController();
            }catch (IOException e){
            e.printStackTrace();
            }
        */
     }
     else if(header2.getText().equals("Best First Search")){
         /*
         try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("BestFirstSearch.fxml"));  // its AnchorPane fx:id should be "astarpane"
            AnchorPane root= (AnchorPane) loader.load();
            pane.getChildren().removeAll();
            pane.getChildren().setAll(root);  
                //if need pass method over, then use
                //RunSearchController control=loader.getController();
            }catch (IOException e){
            e.printStackTrace();
            }
        */ 
     }
     else if(header2.getText().equals("Genetic Search")){
         /*
         try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("GeneticSearch.fxml"));
            AnchorPane root= (AnchorPane) loader.load();
            pane.getChildren().removeAll();
            pane.getChildren().setAll(root);  
                //if need pass method over, then use
                //RunSearchController control=loader.getController();
            }catch (IOException e){
            e.printStackTrace();
            }
        */  
     }
     else if(header2.getText().equals("Basic Search")){
         /*
         try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("BasicSearch.fxml"));
            AnchorPane root= (AnchorPane) loader.load();
            pane.getChildren().removeAll();
            pane.getChildren().setAll(root);  
                //if need pass method over, then use
                //RunSearchController control=loader.getController();
            }catch (IOException e){
            e.printStackTrace();
            }
        */ 
     }
     else if(header2.getText().equals("Prune Search")){
         /*
         try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("PruneSearch.fxml"));
            AnchorPane root= (AnchorPane) loader.load();
            pane.getChildren().removeAll();
            pane.getChildren().setAll(root);  
                //if need pass method over, then use
                //RunSearchController control=loader.getController();
            }catch (IOException e){
            e.printStackTrace();
            }
        */  
     }
    }
    
     //animation effect for text /object
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
    
    //validator
    public boolean isInt(String input ) { //Pass in string
        try { //Try to make the input into an integer
          Integer.parseInt( input );
          return true;
        }catch( Exception e ) { 
          return false;
        }
    }
        
    @FXML
    private void checkModeSelected(MouseEvent event) {
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
    
 
   
       