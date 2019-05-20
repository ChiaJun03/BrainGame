/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.AnchorPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author ASUS
 */
public class GraphSetup {
    private static Graph<Integer , Synapse> graph;
    private Layout<Integer,Synapse> layout;
    private VisualizationViewer<Integer,Synapse> vv;
    
    public void setLayout(){
        
        /*Transformer< Synapse , Integer> length_function = new Transformer<Synapse , Integer>(){
            @Override
            public Integer transform (Synapse synapse){
                return synapse.getTime();
            }
        };
        */
        //layout the graph
        layout = new FRLayout<>(graph);
        layout.setSize(new Dimension(700,500));
    }
    
    public void setGraph(Graph graph){
        this.graph = graph;
    }
    public SwingNode setup(){
        setLayout();

        vv = new VisualizationViewer<>(layout);
        vv.setPreferredSize(new Dimension(750,650));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);
        SwingNode swingNode = new SwingNode();
        SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                 JPanel panel = vv;
                 swingNode.setContent(panel);
             }
         });
        
        return swingNode;
    }    
    
    public void deleteNeuron(int ID){
        this.graph.removeVertex(ID);
    }
    
    public SwingNode changePath(ArrayList<Integer> nodeList , ArrayList<Synapse> edgeList){
        
        
        //change node colour 
        Transformer<Integer,Paint> vertexColor = new Transformer<Integer,Paint>() {
            @Override
            public Paint transform(Integer i) {
                if(nodeList.contains(i)) return Color.BLACK;
                return Color.RED;
            }
        };
        
        //change node size
        Transformer<Integer,Shape> vertexSize = new Transformer<Integer,Shape>(){
            @Override
            public Shape transform(Integer i){
                Ellipse2D circle = new Ellipse2D.Double(-15,-15, 30, 30);
                // in this case, the vertex is twice as large
                if(nodeList.contains(i)) 
                    return AffineTransform.getScaleInstance(2, 2).createTransformedShape(circle);
                else 
                    return circle;
            }
        };
        
        //change edge colour 
        Transformer<Synapse,Paint> edgeColor = new Transformer<Synapse , Paint>(){
            @Override
            public Paint transform(Synapse s){
                for(int iterate = 0 ; iterate < edgeList.size() ; iterate++){
                    if(edgeList.get(iterate).equals(s)){
                        return Color.cyan;
                    }
                }
                return new Color(1f,0f,0f,0f );
            }
        };
        
        //change edge stroke somehow cannot change thickness
        Transformer<Synapse , Stroke> edgeStroke = new Transformer<Synapse , Stroke>(){
            float dash[] = { 10.0f };
            @Override
            public Stroke transform(Synapse s){
                return new BasicStroke(3.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
            }
        };
        
        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
        vv.getRenderContext().setEdgeFillPaintTransformer(edgeColor);
        vv.getRenderContext().setEdgeArrowStrokeTransformer(edgeStroke);
        
        SwingNode swingNode = new SwingNode();
        SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                 JPanel panel = vv;
                 swingNode.setContent(panel);
             }
         });
        
        return swingNode;
        
    }
}
