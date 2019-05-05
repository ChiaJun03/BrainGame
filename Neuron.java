/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;

/**
 *
 * @author Jing Chong
 */
public class Neuron {
    private Integer lifetime;
    private Integer connectNum;
    private ArrayList<Synapse> synapseList;
    private ArrayList<Node> nodeList ;
    private boolean visited = false ;
    
    /**A constructor that create an object with 
     * specific lifetime and number of connected node.
     * @param connectNum
     * @param lifetime
     */
    public Neuron(Integer connectNum , Integer lifetime ){
        this.lifetime  = lifetime ;
        this.connectNum = connectNum;
        synapseList = new ArrayList ();
        nodeList = new ArrayList();
        visited = false;
    }

    /**Retrieve lifetime  of the neuron.
     * @return the lifetime 
     */
    public Integer getLifetime () {
        return lifetime ;
    }

    /**Set lifetime of the neuron.
     * @param lifetime the lifetime to set
     */
    public void setLifetime(Integer lifetime ) {
        this.lifetime = lifetime ;
    }

    /**Retrieve number of connected node to the neuron.
     * @return the connectNum
     */
    public Integer getConnectNum() {
        return connectNum;
    }

    /**Set number of connected node to the neuron.
     * @param connectNum the connectNum to set
     */
    public void setConnectNum(Integer connectNum) {
        this.connectNum = connectNum;
    }
    
    /**Convert all the info of this node to string
     * @return a string contain all instances in this class
     */
    public String toString(){
        String info = "";
        info += "Number of connected nodes: "+connectNum+"\tLifetime: "+lifetime+"\nConnection:\n";
        if(connectNum!=0)
            for(Synapse ptr: synapseList)
                info += ptr.toString();
        else
            info += "No Connection";
        return info;
    }
    
    public void addSynapse(int toID, int time, int distance){
        synapseList.add(new Synapse(toID, time, distance));
    }
    
    public void removeSynapse(int toID){
        if(containsSynapse(toID)){
            synapseList.remove(getIndexOf(toID));
            this.connectNum--;
        }
        else
            System.out.println("Synapse not found.");
    }
    
    public int getIndexOf(int id){
        for(Synapse ptr: synapseList)
            if(ptr.getID() == id)
                return synapseList.indexOf(ptr);
        return -1;
    }
    
    public boolean containsSynapse(int toID){
        return getIndexOf(toID)!=-1;
    }
    
    public boolean hasNext(int currentNode){
        return getIndexOf(currentNode)<synapseList.size()-1;
    }
    
    public int getNext(int currentNode){
        return synapseList.get(getIndexOf(currentNode)+1).getID();
    }
    
    public int getTimeTo(int toID){
        return synapseList.get(getIndexOf(toID)).getTime();
    }
    
    public int getDistanceTo(int toID){
        return synapseList.get(getIndexOf(toID)).getDistance();
    }
    
    ////////////////// Methods below is for BFS !! ////////////////
    
    public boolean checkVisit(){
        return visited ;
    }
    
    public void setVisited(){
        this.visited = true;
    }
    
    public void addBFSNode(int parent ,int toID ,int time){
       nodeList.add(new Node(parent , toID , time));
    }
    
    public int getIndexOfBFSNode(int id){
        for(Node ptr:nodeList){
            if(ptr.getId() == id)
                return nodeList.indexOf(ptr);
        }
        return -1;
    }
    
    public int getIndexOfBFSParent(int parent){
        for(Node ptr : nodeList){
            if(ptr.getParent()==parent)
                return nodeList.indexOf(ptr)+1;
        }
        return -1;
    }
    
    public boolean containsBFSNode(int id){
        return  getIndexOfBFSNode(id)!=-1;
    }
    
    
    public boolean containsBFSParent(int parent){
        return getIndexOfBFSParent(parent) != -1 ;
    }
    
    public int getParentOf(int id){
        return nodeList.get(getIndexOfBFSNode(id)).getParent();
    }
    
    public int getTime(int id){
        return nodeList.get(id).getTime();
    }
    
}