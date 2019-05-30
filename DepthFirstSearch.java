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
    private int currentTime, currentDistance, root, start, connection;
    private ArrayList<Integer> goal;
    private String console;
    
    public DepthFirstSearch(SearchSpace space){
        this.space = space;
        currentTime = 0;
        currentDistance = 0;
        goal = new ArrayList();
    }
    
    public void search(int start, int end){
        depthSearch(start, end, 0);
    }
    
    public void preSearch(int start){
        reset();
        root = start;
        this.start = start;
        connection=0;
    }
    
    public void depthSearch(int begin, int end, int connect){
        if(start!=root || space.hasNext(start, connection)){
            if(start == end){
                System.out.println(currentTime);
                currentTime += space.get(goal.get(goal.size()-1)).getTimeTo(start);
                currentDistance += space.get(goal.get(goal.size()-1)).getDistanceTo(start);
                goal.add(start); System.out.println("Add "+start+" into path.");
                console = toString()+" (Goal)";
                SearchPaneController.setIsEnd(true);
            }else if(!space.hasNext(start, connection)){
                console = toString()+" - "+start+" - (Leaf node)";
                connection = start;
                start = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                if(!goal.isEmpty()){
                    currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(start);
                    currentDistance -= space.get(goal.get(goal.size()-1)).getDistanceTo(start);
                }
            }else if(goal.contains(start)){
                console = toString()+" - "+start+" - (Loop)";
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
                console = toString()+" - ";
                start = space.nextNode(start, connection);
                connection = 0;
            }
        }else{
            SearchPaneController.setIsEnd(true);
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
                if(goal.indexOf(ptr)==goal.size()-1)
                    path+=ptr;
                else
                    path+=ptr+" - ";
        }
        return path;
    }

    
    public ArrayList<Integer> getPath() {
        return goal;
    }

    @Override
    public ArrayList<Integer> trackPath() {
        return goal;
    }

    @Override
    public String console() {
        return console;
    }
}
