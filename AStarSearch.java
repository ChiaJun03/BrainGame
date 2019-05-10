/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Jing Chong
 */
public class AStarSearch {
    private SearchSpace space;
    private Table table;
    private ArrayList<Integer> path;
    private int currentTime = 0, root;
    
    public AStarSearch(SearchSpace space){
        this.space = space;
        table = new Table(space);
        path = new ArrayList();
    }
    
    public void search(int start, int end){
        if(table.getMinTimefrom(start)[end-1]==0||table.getMinTimefrom(start)[end-1]==Integer.MAX_VALUE)
            System.out.println("No Path Availale");
        else{
            root = start;
            path.add(start);
            search(start, end, table.getMinTimefrom(start)[end-1]);
        }
    }
    
    public void search(int start, int end, int time){
        int cost;
        int connection=0;
        while(space.hasNext(start, connection)){
            int nextNode = space.nextNode(start, connection);
            cost = table.getMinTimefrom(nextNode)[end-1] + table.getMinTimefrom(root)[nextNode-1];
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
    }
    
}


class Table{
    private TreeMap<Integer,int[]> searchInfo;
    private SearchSpace space;
    private ArrayList<Integer> path;
    private int currentTime = 0;
    
    
    public Table(SearchSpace space){
        searchInfo = new TreeMap();
        this.space = space;
        createTable();
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
    
    
    public void evaluation(int start, int connection){
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
    
    public int[] getMinTimefrom(int id){
        return searchInfo.get(id);
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
