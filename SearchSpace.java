/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Jing Chong
 */
public class SearchSpace {
    private TreeMap<Integer, Neuron> space;
    
    
    /**A constructor that create a tree map with
     * Neuron class as key and Attribute class as value.
     */
    public SearchSpace(){
        space = new TreeMap();
    }
    
    /**Add a new node to the search space.
     * @param id is the id of the newly added node
     * @param connectNum is the number of connected node to the newly added node
     * @param lifetime is the number of time a node can pass the message
     */
    public void addNode(int id, int connectNum, int lifetime){
        space.put(id, new Neuron(connectNum, lifetime));
    }
    
    /**Add a new connected node info to a specific node.
     * @param fromID is the id of a node in search space
     * @param toID is the id of a node connected to the fromID node
     * @param time is the time traveled from fromID node to toID node
     * @param distance is the distance traveled from fromID node to toID node
     */
    public void addSynapse(int fromID, int toID, int time, int distance){
        space.get(fromID).addSynapse(toID, time, distance);
    }
    
    /**Remove a specific node from search space.
     * @param id is the id of the node want to remove
     */
    public void removeNode(int id){
        space.remove(id);
        for(Map.Entry<Integer, Neuron> entry: space.entrySet())
            if(entry.getValue().containsSynapse(id)){
                entry.getValue().removeSynapse(id);
            }
    }
    
    /**Remove all the nodes in search space.
     */
    public void clear(){
        space.clear();
    }
    
    public int getSize(){
        return space.size();
    }
    
    /**Convert all the info in search space to string
     * @return a string contain all instances in this class
     */
    public String toString(){
        String info = "Search Space\n";
        for(Map.Entry<Integer, Neuron> entry: space.entrySet())
            info+="Node ID: "+entry.getKey()+"\t"+entry.getValue().toString()+"\n";
        return info;
    }
    
    
    public int nextNode(int id, int currentNode){
        return space.get(id).getNext(currentNode);
    }
    
    public boolean hasNext(int id, int currentNode){
        return space.get(id).hasNext(currentNode);
    }
    
    public Neuron get(int id){
        return space.get(id);
    }
    
    public TreeMap<Integer, Neuron> getTreeMap(){
        return this.space;
    }
}
