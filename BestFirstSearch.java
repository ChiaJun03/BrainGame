/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import java.util.ArrayList;

/**
 *
 * @author Jing Chong
 */
public class BestFirstSearch {
    private SearchSpace space;
    private int currentTime;
    private Node front;
    private ArrayList<Node> open, close;
    private ArrayList<Integer> goal;
    
    public BestFirstSearch(SearchSpace space){
        this.space = space;
        open = new ArrayList();
        close = new ArrayList();
        goal = new ArrayList();
        currentTime = 0;
    }
    
    public void search(int start, int end){
        System.out.println("Search path from node "+start+" to node "+end+":");
        bestFirstSearch(start, end, 0);
    }
    
    public void bestFirstSearch(int start, int end, int connection){
        open.add(new Node(-1, start, 0));
        while (!open.isEmpty()) {
            System.out.println("Open: "+open);
            System.out.println("Close: "+close);
            front = open.remove(0);
            if(!nodeClosed(front.getId())){
                close.add(front);
                System.out.println("Exploring "+front.getId()+"...");
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
                            int heuristicValue = front.getHeuristic() + space.get(front.getId()).getTimeTo(next);
                            addPriority(front.getId(), next, heuristicValue);
                            connection = space.nextNode(front.getId(),connection);
                        }else{
                            connection = space.nextNode(front.getId(),connection);
                        }
                    }
                }
            }else
                System.out.println("Ignoring "+front.getId()+"...");
        }
    }
    
    public boolean nodeClosed(int id){
        for(Node ptr: close)
            if(ptr.getId() == id)
                return true;
        return false;
    }
    
    public void addPriority(int parent, int nextNode, int heuristic){
        if(open.size()==0)
            open.add(new Node(parent, nextNode, heuristic));
        else
            for(int i=0; i<open.size(); i++){
                if(open.get(i).getHeuristic() >= heuristic){
                    if(open.get(i).getHeuristic() == heuristic){
                        open.add(i+1, new Node(parent, nextNode, heuristic));
                        break;
                    }else{
                        open.add(i, new Node(parent, nextNode, heuristic));
                        break;
                    }
                }else if(i == open.size()-1){
                    open.add(new Node(parent, nextNode, heuristic));
                    break;
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