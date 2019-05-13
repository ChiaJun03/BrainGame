/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

/**Special needed by breadth & best first search for backtracking
 * 
 * @author Chia Jun
 */
public class Node {
    private int parent ;
    private int id ;
    private int heuristic; //heuristic value = time traveled
    
    /**Constructor for BreadthFirstSearch
     * @param parent of the node added
     * @param id of the node added
     */
    public Node(int parent, int id) {
        this.parent = parent;
        this.id = id;
    }
    
    /**Constructor for BestFirstSearch
     * @param parent of the node added
     * @param id of the node added
     * @param heuristic is the heuristic value (time traveled so far)
     */
    public Node(int parent, int id, int heuristic) {
        this.parent = parent;
        this.id = id;
        this.heuristic = heuristic;
    }
    
    /**
     * @return id of this node
     */
    public int getId(){
        return this.id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * @return id of parent of this node
     */
    public int getParent(){
        return this.parent;
    }
    
    /**
     * @param parent the id of parent to set
     */
    public void setParent(int parent){
        this.parent = parent ;
    }
    
    /**
     * @return heuristic value of this node
     */
    public int getHeuristic(){
        return this.heuristic;
    }
    
    /**
     * @param heuristic the heuristic value to set
     */
    public void setHeuristic(int heuristic){
        this.heuristic = heuristic;
    }
    
    /**Overwriting toString method
     * @return the node's id following by its heuristic value
     */
    public String toString(){
        return this.getId()+"("+this.getHeuristic()+")";
    }

    
}
