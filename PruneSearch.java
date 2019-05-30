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
public class PruneSearch implements Search{
    private SearchSpace space;
    private int currentTime, currentDistance, minTime, root, start, connection;
    private ArrayList<Integer> goal;
    private ArrayList<Integer> timeList, distanceList;
    private ArrayList<ArrayList<Integer>> pathList;
    private String console;
    
    public PruneSearch(SearchSpace space){
        this.space = space;
        currentTime = 0;
        currentDistance = 0;
        goal = new ArrayList();
        pathList = new ArrayList();
        timeList = new ArrayList();
        distanceList = new ArrayList();
    }
    
    public void search(int start, int end){
        pruneSearch(start, end, 0);
    }
    
    public void preSearch(int start){
        reset();
        root = start;
        this.start = start;
        connection = 0;
        minTime = Integer.MAX_VALUE;
    }
    
    public void pruneSearch(int begin, int end, int connect){
        if(start!=root || space.hasNext(start, connection)){
            if(start == end){
                currentTime += space.get(goal.get(goal.size()-1)).getTimeTo(start);
                currentDistance += space.get(goal.get(goal.size()-1)).getDistanceTo(start);
                goal.add(start); System.out.println("Add "+start+" into path.");
                console = showPath(goal) + " (Goal "+pathList.size()+1+")";
                System.out.println(showPath(goal));
                if(currentTime<=minTime){
                    pathList.add((ArrayList<Integer>) goal.clone());
                    timeList.add(currentTime);
                    distanceList.add(currentDistance);
                    minTime = currentTime;
                }
                connection = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                System.out.println(showPath(goal));
                currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(connection);
                currentDistance -= space.get(goal.get(goal.size()-1)).getDistanceTo(connection);
                start = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                System.out.println(showPath(goal));
                if(!goal.isEmpty()){
                    currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(start);
                    currentDistance -= space.get(goal.get(goal.size()-1)).getDistanceTo(start);
                }
            }else if(!space.hasNext(start, connection)){
                console = showPath(goal)+" - "+start+" - (Leaf node)";
                connection = start;
                start = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                System.out.println(showPath(goal));
                if(!goal.isEmpty()){
                    currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(start);
                    currentDistance -= space.get(goal.get(goal.size()-1)).getDistanceTo(start);
                }
            }else if(goal.contains(start)){
                console = showPath(goal)+" - "+start+" - (Loop)";
                connection = start;
                start = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                System.out.println(showPath(goal));
                currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(start);
                currentDistance -= space.get(goal.get(goal.size()-1)).getDistanceTo(start);
            }else if(currentTime > minTime){
                System.out.println("Prune!");
                console = showPath(goal)+" - "+start+" - (Prune)";
                connection = start;
                start = goal.remove(goal.size()-1); System.out.println("Remove "+start+" from path.");
                System.out.println(showPath(goal));
                if(!goal.isEmpty()){
                    currentTime -= space.get(goal.get(goal.size()-1)).getTimeTo(start);
                    currentDistance -= space.get(goal.get(goal.size()-1)).getDistanceTo(start);
                }
            }else{
                if(!goal.isEmpty()){
                    currentTime += space.get(goal.get(goal.size()-1)).getTimeTo(start);
                    currentDistance += space.get(goal.get(goal.size()-1)).getDistanceTo(start);
                }
                goal.add(start); System.out.println("Add "+start+" into path.");
                console = showPath(goal)+" - ";
                System.out.println(showPath(goal));
                start = space.nextNode(start, connection);
                connection = 0;
            }
        }else{
            SearchPaneController.setIsEnd(true);
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
    
    public int getOptimized(){
        int minIndex = timeList.size()-1;
        for(int i=timeList.size()-2; i>=0; i--){
            if(timeList.get(i).compareTo(timeList.get(minIndex))==0)
                if(distanceList.get(i).compareTo(distanceList.get(minIndex))<0)
                    minIndex = i;
            else
                break;
        }
        return minIndex;
    }
    
    public ArrayList<Integer> getPath(){
        if(getOptimized()==-1)
            return null;
        else
            return pathList.get(getOptimized());
    }
    
    public void reset(){
        currentTime = 0;
        currentDistance = 0;
        goal.clear();
        pathList.clear();
        timeList.clear();
        distanceList.clear();
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

    @Override
    public ArrayList<Integer> trackPath() {
        return goal;
    }

    @Override
    public String console() {
        return console;
    }
}
