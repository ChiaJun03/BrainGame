/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Jing Chong
 */
public class BreadthFirstSearch {
    private LinkedList<Integer> open = new LinkedList();
    private LinkedList<Integer> close = new LinkedList();
    private LinkedList<Integer> temp = new LinkedList();
    private SearchSpace space;
    private int currentTime;
    private ArrayList<Integer> goal;
    private ArrayList<Integer> timeList;
    private ArrayList<ArrayList<Integer>> pathList;
    
    public BreadthFirstSearch(SearchSpace space){
        this.space = space;
        goal = new ArrayList();
        timeList = new ArrayList();
        pathList = new ArrayList();
        currentTime = 0;
    }
    
    public void search(int start, int end){
        breadthFirstSearch(start, end, 0);
    }

    public void breadthFirstSearch(int start, int end, int connection) {
        System.out.println("Current Node : " + start);
        open.add(start);
        System.out.println("Add "+start+" into open queue.");
        while (!open.isEmpty()) {
            int front = open.remove(0);
            close.add(front);
            space.get(front).setVisit(true);
            System.out.println(front+" is removed from open queue and push into close stack.");
            connection = 0;
            if (front == end) {
                int check = end ;
                temp.addFirst(end);
                for(int i = 0 ; i< close.size() -1 ; i++){
                    if(space.get(check).containsBFSParent(close.get(i))){
                        temp.addFirst(close.get(i));
                        check = close.get(i);
                        if(i !=0 )
                            i = -1; /// here is the prob
                        else
                            i=close.size();
                    }    
                }
                if(temp.get(0) != start){
                    System.out.println("The start node and end node is not connected !!!");
                }
                else{
                    System.out.println("here!!!!");
                    for(int j = temp.size()-1  ; j > 0 ; j--){
                    currentTime += space.get(temp.get(j-1)).getTimeTo(temp.get(j));
                }
                for(int k = 0 ; k < temp.size() ; k++){
                    goal.add(temp.get(k));
                }
                pathList.add((ArrayList<Integer>)goal.clone());
                goal.clear();
                timeList.add(currentTime);
                System.out.println("Get goal ! "+front);
                }
                open.clear();
            } else {
                while (space.hasNext(front, connection)) {
                    int next = space.nextNode(front, connection);
                    if (space.get(next).getVisit() == false) {
                        open.add(next);
                        System.out.println("Explore the node below "+front+" : "+next+" is found.");
                        System.out.println(next+" is added into the open queue");
                        connection = space.nextNode(front,connection);
                    }else{
                        System.out.println("The Node is explored");
                        connection = space.nextNode(front,connection);
                    }
                }
                
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
