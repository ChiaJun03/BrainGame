/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

/**
 *
 * @author Jing Chong
 */
public class AStarSearch {
    private TreeMap<Integer,int[]> searchInfo;
    private SearchSpace space;
    private ArrayList<Integer> path;
    private int currentTime = 0;
    private int loopCount = 0;
    
    public AStarSearch(SearchSpace space){
        this.space = space;
        searchInfo = new TreeMap();
        path = new ArrayList();
        createTable();
        input(1, 0);
        path.clear();
    }
    
    public void createTable(){
        int MAX = Integer.MAX_VALUE;
        int [] values = new int [space.getSize()];
        for(int i=0; i<values.length; i++)
            values[i] = MAX;
            
        for(int i=1; i<=space.getSize(); i++){
            searchInfo.put(i, new int [space.getSize()]);
        }
        
        for(int i=1; i<=space.getSize(); i++){
            for(int j=0; j<searchInfo.get(i).length; j++){
                if(i == j+1)
                    searchInfo.get(i)[j] = 0;
                else
                    searchInfo.get(i)[j] = MAX;
            }
        }
    }
    
    public int getLast(){
        return path.get(path.size()-1);
    }
    
    public void removeLast(){
        path.remove(path.size()-1);
    }
    
    public int[] getTimeOf(int fromID){
        return searchInfo.get(fromID);
    }
    
    public int getIndexOf(int id){
        for(int i=0; i<path.size(); i++)
            if(path.get(i)==id)
                return i;
        return -1;
    }
    
    public boolean isHead(int id){
        return getIndexOf(id)==0;
    }
    
    public void input(int start, int connection){
        int root = start;
        int tempTime;
        while(start!=root || space.hasNext(start, connection)){
            if(!space.hasNext(start, connection)){
                connection = start;
                start = path.remove(path.size()-1); System.out.println("Remove "+start+" from path.");
                if(!path.isEmpty())
                    currentTime -= space.get(path.get(path.size()-1)).getTimeTo(start);
            }else if(path.contains(start)){
                currentTime += space.get(path.get(path.size()-1)).getTimeTo(start);
                tempTime = currentTime;
                for(int i=0; i<path.size()-1; i++){
                    if(tempTime < searchInfo.get(path.get(i))[start-1])
                        searchInfo.get(path.get(i))[start-1] = tempTime;
                    tempTime -= space.get(path.get(i)).getTimeTo(i+1);
                }
                currentTime -= space.get(path.get(path.size()-1)).getTimeTo(start);
                connection = start;
                start = path.remove(path.size()-1); System.out.println("Remove "+start+" from path.");
                currentTime -= space.get(path.get(path.size()-1)).getTimeTo(start);
            }else{
                if(!path.isEmpty())
                    currentTime += space.get(path.get(path.size()-1)).getTimeTo(start);
                path.add(start); System.out.println("Add "+start+" into path.");
                tempTime = currentTime;
                for(int i=0; i<path.size()-1; i++){
                    if(tempTime < searchInfo.get(path.get(i))[start-1])
                        searchInfo.get(path.get(i))[start-1] = tempTime;
                    tempTime -= space.get(path.get(i)).getTimeTo(i+1);
               }
                start = space.nextNode(start, connection);
                connection = 0;
            }
        }
    }
    
    private int root;
    private int cycle = 0;
    
    public void search(int start, int end){
        if(searchInfo.get(start)[end-1]==0||searchInfo.get(start)[end-1]==Integer.MAX_VALUE)
            System.out.println("No Path Availale");
        else{
            root = start;
            path.add(start);
            search(start, end, searchInfo.get(start)[end-1]);
        }
    }
    
    public void search(int start, int end, int time){
        cycle++;
        int cost;
        int connection=0;
        while(space.hasNext(start, connection)){
            int nextNode = space.nextNode(start, connection);
            cost = searchInfo.get(nextNode)[end-1] + searchInfo.get(root)[nextNode-1];
            if(cost == time){
                path.add(nextNode);
                break;
            }
            connection++;
        }
        if(path.get(path.size()-1)!=end)
            search(path.get(path.size()-1), end, time);
        else
            showPath(end);
    }
    
    public void showPath(int end){
        System.out.println("The shortest path from "+root+" to "+end);
        for(Integer ptr: path){
            System.out.print(ptr+" -> ");
        }
        System.out.println("");
        System.out.println("Cycle: "+cycle);
        cycle=0;
    }
    
    public String toString(){
        String table = "";
        for(int i=0; i<searchInfo.size(); i++)
            table += "\t"+(i+1);
        table += "\n";
        
        for(Map.Entry<Integer, int[]> entry: searchInfo.entrySet()){
            table += entry.getKey();
            for(int ptr: entry.getValue())
                table += "\t"+ptr;
            table += "\n";
        }
        
        return table;
    }
    
}
