/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

/**
 *
 * @author Chia Jun
 */
public class Node {
    private int parent ;
    private int id ;
    private int time ;

    public Node(int parent, int id, int time) {
        this.parent = parent;
        this.id = id;
        this.time = time;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setID(int id){
        this.id = id ;
    }
    
    public int getParent(){
        return this.parent;
    }
    
    public void setParent(int parent){
        this.parent = parent ;
    }
    
    public int getTime(){
        return this.time ;
    }
    
    public void setTime(int time){
        this.time = time ;
    }
    
    
}
