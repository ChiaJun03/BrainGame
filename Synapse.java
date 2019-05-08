/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

/**
 *
 * @author Jing Chong
 */
public class Synapse{
    private int toID;
    private Integer time;
    private Integer distance;
    private Neuron toNeuron;
    
    /**A constructor that create a synapse with specific toID, time and distance.
     * @param toID
     * @param time
     * @param distance
     */
    public Synapse (int toID, int time, int distance, Neuron toNeuron ){
        this.toID = toID;
        this.time = time;
        this.distance = distance;
        this.toNeuron = toNeuron;
    }
    
    public Integer getTime(){
        return this.time;
    }

    public Integer getDistance(){
        return this.distance;
    }

    public int getID(){
        return this.toID;
    }
    
    public String toString(){
        return "ID: "+toID+"\tTime: "+time+"\tDistance: "+distance+"\n";
    }
    
    public Neuron getNeuron(){
        return this.toNeuron;
    }

}
