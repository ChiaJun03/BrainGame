/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.Dimension;
import javafx.embed.swing.SwingNode;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author ASUS
 */
public class GraphSetup {
    
    public SwingNode setup(Graph graph){
        //layout the graph
        Layout<Integer,Integer> layout = new FRLayout<>(graph);
        layout.setSize(new Dimension(800,600));
       
        
        VisualizationViewer<Integer,Integer> vv = new VisualizationViewer<>(layout);
        vv.setPreferredSize(new Dimension(850,650));
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
    
    
}
