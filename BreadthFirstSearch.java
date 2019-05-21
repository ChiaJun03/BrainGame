/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this goallate file, choose Tools | goallates
 * and open the goallate in the editor.
 */
package braingame;

import java.util.ArrayList;

/**
 *
 * @author Chia Jun
 */
public class BreadthFirstSearch implements Search {
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
        reset();
        System.out.println("Search path from node "+start+" to node "+end+":");
        if(space.contains(start)&&space.contains(end))
            breadthFirstSearch(start, end, 0);
        else
            System.out.println("No path available");
    }

    public void breadthFirstSearch(int start, int end, int connection) {
        System.out.println("Current Node : " + start);
        open.add(new Node(-1, start));
        System.out.println("Add "+start+" into open queue.");
        while (!open.isEmpty()) {
            System.out.println("Open: "+open);
            System.out.println("Close: "+close);
            front = open.remove(0);
            close.add(front);
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
                    if (!nodeClosed(next)) {
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
    
    public void reset(){
        open.clear();
        close.clear();
        goal.clear();
        currentTime = 0;
    }
    
    public boolean nodeClosed(int id){
        return close.contains(id);
    }
    
    public String toString(){
        String path = "";
        if(goal.isEmpty())
            path+="No path available";
        else{
            for(Integer ptr: goal)
                path+=ptr+" - ";
            path+=" goal!\nTime used: "+currentTime+"s\n";
        }
        return path;
    }
    
    public ArrayList<Integer> getPath(){
        return goal;
    }
}
