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
public class PruneSearch {
    private SearchSpace space;
    private int currentTime;
    private ArrayList<Integer> goal;
    private ArrayList<Integer> timeList;
    private ArrayList<ArrayList<Integer>> pathList;
    
    public PruneSearch(SearchSpace space){
        this.space = space;
        currentTime = 0;
        goal = new ArrayList();
        pathList = new ArrayList();
        timeList = new ArrayList();
    }
    
    public void search(int start, int end){
        pruneSearch(start, end, 0);
    }
    
    public void pruneSearch(int start, int end, int connection){
        int root = start;
        int minTime = Integer.MAX_VALUE;
        while(start!=root || space.hasNext(start, connection)){
            if(start == end){
                currentTime += space.get(goal.get(goal.size()-1)).getTimeTo(start);
                goal.add(start); System.out.println("Add "+start+" into path.");
                System.out.println(showPath(goal));
                if(currentTime<=minTime){
                    pathList.add((ArrayList<Integer>) goal.clone());
                    timeList.add(currentTime);
                    minTime = currentTime;
                }
                connection = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                System.out.println(showPath(goal));
                currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(connection);
                start = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                System.out.println(showPath(goal));
                if(!goal.isEmpty())
                    currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(start);
            }else if(!space.hasNext(start, connection)){
                connection = start;
                start = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                System.out.println(showPath(goal));
                if(!goal.isEmpty())
                    currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(start);
            }else if(goal.contains(start)){
                connection = start;
                start = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                System.out.println(showPath(goal));
                currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(start);
            }else if(currentTime > minTime){
                System.out.println("Prune!");
                connection = start;
                start = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                System.out.println(showPath(goal));
                if(!goal.isEmpty())
                    currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(start);
            }else{
                if(!goal.isEmpty())
                    currentTime += space.get(goal.get(goal.size()-1)).getTimeTo(start);
                goal.add(start); System.out.println("Add "+start+" into path.");
                System.out.println(showPath(goal));
                start = space.nextNode(start, connection);
                connection = 0;
            }
        }
    }
    
    public String showPath(ArrayList<Integer> goal){
        String path = "";
        if(goal.isEmpty())
            path+="No path available";
        else{
            for(Integer ptr: goal)
                path+=ptr+" -> ";
            path+=" goal!";
        }
        return path;
    }
    
    public String toString(){
        int i=0;
        System.out.println("");
        String path = "Total of "+pathList.size()+" path(s) is(are) found!\n";
        if(!pathList.isEmpty()){
            for(ArrayList<Integer> ptr: pathList){
                path+=showPath(ptr)+"\nTime used: "+timeList.get(i)+"s\n\n";
                i++;
            }
        }else
            path+="No path available\n";
        return path;
    }
}
