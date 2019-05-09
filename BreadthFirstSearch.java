/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this goallate file, choose Tools | goallates
 * and open the goallate in the editor.
 */
package braingame;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Chia Jun
 */
public class BreadthFirstSearch {
    private SearchSpace space;
    private int currentTime;
    private Node front;
    private ArrayList<Node> open, close;
    private ArrayList<Integer> goal;
    
    public BreadthFirstSearch(SearchSpace space){
        this.space = space;
        open = new ArrayList();
        close = new ArrayList();
        goal = new ArrayList();
        currentTime = 0;
    }
    
    public void search(int start, int end){
        breadthFirstSearch(start, end, 0);
    }

    public void breadthFirstSearch(int start, int end, int connection) {
        System.out.println("Current Node : " + start);
        open.add(new Node(-1, start));
        System.out.println("Add "+start+" into open queue.");
        while (!open.isEmpty()) {
            front = open.remove(0);
            close.add(front);
            space.get(front.getId()).setVisit(true);
            System.out.println(front.getId()+" is removed from open queue and push into close stack.");
            connection = 0;
            if (front.getId() == end) {
                goal.add(0, end);
                System.out.println("Backtracking...");
                for(int i = 0 ; i< close.size() -1 ; i++){
                    if(front.getParent() == close.get(i).getId()){
                        System.out.println("ID: "+front.getId()+"\tParent: "+front.getParent()+"\tClose ID: "+close.get(i).getId());
                        goal.add(0, close.get(i).getId());
                        front = close.get(i);
                        if(i !=0 )
                            i = -1; // re-searching from index 0
                        else
                            break;
                    }
                }
                if (goal.get(0) != start){
                    goal.clear();
                }else{
                    for(int j = goal.size()-1  ; j > 0 ; j--){
                        currentTime += space.get (goal.get(j-1)).getTimeTo (goal.get(j));
                    }
                    System.out.println("Get goal ! "+end);
                }
                open.clear();
            }else{
                while (space.hasNext(front.getId(), connection)) {
                    int next = space.nextNode(front.getId(), connection);
                    if (space.get(next).getVisit() == false) {
                        System.out.println("Explore the node below "+front.getId()+" : "+next+" is found.");
                        open.add(new Node(front.getId(), next)); System.out.println(next+" is added into the open queue");
                        connection = space.nextNode(front.getId(),connection);
                    }else{
                        System.out.println("The Node is explored");
                        connection = space.nextNode(front.getId(),connection);
                    }
                }
            }
        }
    }
    
    public String toString(){
        String path = "";
        if(goal.isEmpty())
            path+="No path available";
        else{
            for(Integer ptr: goal)
                path+=ptr+" -> ";
            path+=" goal!\nTime used: "+currentTime+"s\n";
        }
        return path;
    }
}

/**A special node class to track parent for BFS
 * 
 * @author Chia Jun
 */
class Node {
    private int parent ;
    private int id ;

    public Node(int parent, int id) {
        this.parent = parent;
        this.id = id;
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
    
}
