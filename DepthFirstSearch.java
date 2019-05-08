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
public class DepthFirstSearch {
    private SearchSpace space;
    private boolean ended;
    private int loopCount, currentTime, cycle;
    private ArrayList<Integer> goal;
    
    public DepthFirstSearch(SearchSpace space){
        this.space = space;
        ended = false;
        loopCount = 0;
        currentTime = 0;
        cycle = 0;
        goal = new ArrayList();
    }
    
    public void search(int start, int end){
        depthSearch(start, end, 0);
    }
    
    public void depthSearch(int start, int end, int connection){
        while(!ended){
            while(space.hasNext(start, connection)){
                if(start==end){
                    currentTime+=space.get(goal.get(goal.size()-1)).getTimeTo(start);
                    goal.add(start); System.out.println("Push "+start+" into stack");
                    System.out.println("Goal!");
                    ended = true;
                    break;
                }else{
                    if(!goal.isEmpty())
                            currentTime+=space.get(goal.get(goal.size()-1)).getTimeTo(start);
                    goal.add(start); System.out.println("Push "+start+" into stack");
                    space.get(start).setVisit(true);
                    if(space.get(space.nextNode(start, connection)).getVisit()){
                        connection = space.nextNode(start, connection);
                        goal.remove(goal.size()-1); System.out.println("Pop "+start+" from stack");
                        if(!goal.isEmpty())
                                currentTime-=space.get(goal.get(goal.size()-1)).getTimeTo(start);
                    }else{
                        start = space.nextNode(start, connection);
                        connection=0;
                    }
                }
            }
            if(ended)
                break;
            connection = goal.remove(goal.size()-1); System.out.println("Pop "+start+" from stack");
            if(!goal.isEmpty())
                    currentTime-=space.get(goal.get(goal.size()-1)).getTimeTo(connection);
            start = goal.remove(goal.size()-1); System.out.println("Pop "+start+" from stack");
            if(!goal.isEmpty())
                    currentTime-=space.get(goal.get(goal.size()-1)).getTimeTo(start);
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
