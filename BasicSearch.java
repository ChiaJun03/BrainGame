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
public class BasicSearch implements Search {
    private SearchSpace space;
    private int currentTime, currentDistance;
    private ArrayList<Integer> goal;
    private ArrayList<Integer> timeList, distanceList;
    private ArrayList<ArrayList<Integer>> pathList;
    
    public BasicSearch(SearchSpace space){
        this.space = space;
        currentTime = 0;
        currentDistance = 0;
        goal = new ArrayList();
        pathList = new ArrayList();
        timeList = new ArrayList();
        distanceList = new ArrayList();
    }
    
    public void search(int start, int end){
        reset();
        System.out.println("Search path from node "+start+" to node "+end+":");
        if(space.contains(start)&&space.contains(end))
            DFSbasedSearch(start, end, 0);
        else
            System.out.println("No path available");
    }
    
    public void DFSbasedSearch(int start, int end, int connection){
        int root = start;
        while(start!=root || space.hasNext(start, connection)){
            if(start == end){
                currentTime += space.get(goal.get(goal.size()-1)).getTimeTo(start);
                currentDistance += space.get(goal.get(goal.size()-1)).getDistanceTo(start);
                goal.add(start); System.out.println("Add "+start+" into path.");
                System.out.println(showPath(goal));
                pathList.add((ArrayList<Integer>) goal.clone());
                timeList.add(currentTime);
                distanceList.add(currentDistance);
                connection = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                System.out.println(showPath(goal));
                currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(connection);
                currentDistance -= space.get(goal.get(goal.size()-1)).getDistanceTo(start);
                start = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                System.out.println(showPath(goal));
                if(!goal.isEmpty()){
                    currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(start);
                    currentDistance -= space.get(goal.get(goal.size()-1)).getDistanceTo(start);
                }
            }else if(!space.hasNext(start, connection)){
                connection = start;
                start = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                System.out.println(showPath(goal));
                if(!goal.isEmpty()){
                    currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(start);
                    currentDistance -= space.get(goal.get(goal.size()-1)).getDistanceTo(start);
                }
            }else if(goal.contains(start)){
                connection = start;
                start = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                System.out.println(showPath(goal));
                currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(start);
                currentDistance -= space.get(goal.get(goal.size()-1)).getDistanceTo(start);
            }else{
                if(!goal.isEmpty()){
                    currentTime += space.get(goal.get(goal.size()-1)).getTimeTo(start);
                    currentDistance += space.get(goal.get(goal.size()-1)).getDistanceTo(start);
                }
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
                if(goal.indexOf(ptr)==goal.size()-1)
                    path+=ptr;
                else
                    path+=ptr+" - ";
        }
        return path;
    }
    
    public void reset(){
        currentTime = 0;
        currentDistance = 0;
        goal.clear();
        pathList.clear();
        timeList.clear();
        distanceList.clear();
    }
    
    public int getOptimized(){
        int minTime = Integer.MAX_VALUE;
        int minDistance = Integer.MAX_VALUE;
        int minIndex=-1;
        for(int i=0; i<timeList.size(); i++){
            if(timeList.get(i).compareTo(minTime)<0){
                minTime = timeList.get(i);
                minDistance = distanceList.get(i);
                minIndex = i;
            }else if(timeList.get(i).compareTo(minTime)==0){
                if(distanceList.get(i).compareTo(minDistance)<0)
                    minIndex = i;
            }
        }
        return minIndex;
    }
    
    public ArrayList<Integer> getPath(){
        if(getOptimized()==-1)
            return null;
        else
            return pathList.get(getOptimized());
    }
    
    public String toString(){
        int i = getOptimized();
        System.out.println("");
        System.out.println("All path:");
        for(int k=0; k<pathList.size(); k++)
            System.out.println(pathList.get(k)+"\tTime: "+timeList.get(k)+"\tDistance: "+distanceList.get(k));
        System.out.println("");
        String path = "";
        if(!pathList.isEmpty())
                path+=showPath(pathList.get(i))+"\nTime used: "+timeList.get(i)+"s"+"\nDistance used: "+distanceList.get(i)+"\n";
        else
            path+="No path available\n";
        return path;
    }
}
