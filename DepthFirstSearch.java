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
public class DepthFirstSearch implements Search{
    private SearchSpace space;
    private int currentTime, currentDistance;
    private ArrayList<Integer> goal;
    
    public DepthFirstSearch(SearchSpace space){
        this.space = space;
        currentTime = 0;
        currentDistance = 0;
        goal = new ArrayList();
    }
    
    public void search(int start, int end){
        reset();
        System.out.println("Search path from node "+start+" to node "+end+":");
        if(space.contains(start)&&space.contains(end))
            depthSearch(start, end, 0);
        else
            System.out.println("No path available");
    }
    
    public void depthSearch(int start, int end, int connection){
        int root = start;
        while(start!=root || space.hasNext(start, connection)){
            if(start == end){
                currentTime += space.get(goal.get(goal.size()-1)).getTimeTo(start);
                currentDistance += space.get(goal.get(goal.size()-1)).getDistanceTo(start);
                goal.add(start); System.out.println("Add "+start+" into path.");
                break;
            }else if(!space.hasNext(start, connection)){
                connection = start;
                start = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                if(!goal.isEmpty()){
                    currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(start);
                    currentDistance -= space.get(goal.get(goal.size()-1)).getDistanceTo(start);
                }
            }else if(goal.contains(start)){
                connection = start;
                start = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(start);
                currentDistance -= space.get(goal.get(goal.size()-1)).getDistanceTo(start);
            }else{
                if(!goal.isEmpty()){
                    currentTime += space.get(goal.get(goal.size()-1)).getTimeTo(start);
                    currentDistance += space.get(goal.get(goal.size()-1)).getDistanceTo(start);
                }
                goal.add(start); System.out.println("Add "+start+" into path.");
                start = space.nextNode(start, connection);
                connection = 0;
            }
        }
    }
    
    public void reset(){
        goal.clear();
        currentTime = 0;
        currentDistance = 0;
    }
    
    public String toString(){
        String path = "";
        if(goal.isEmpty())
            path+="No path available";
        else{
            for(Integer ptr: goal)
                path+=ptr+" - ";
            path+=" goal!\nTime used: "+currentTime+"s\nDistance travelled: "+currentDistance+"\n";
        }
        return path;
    }

    
    public ArrayList<Integer> getPath() {
        return goal;
    }
}
