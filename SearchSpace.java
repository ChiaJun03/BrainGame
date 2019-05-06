/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

/**
 *
 * @author Jing Chong
 */
public class SearchSpace {

    private TreeMap<Integer, Neuron> space;
    private TreeMap<Integer , Node> BFSspace;
    private Stack<Integer> goal = new Stack();
    private ArrayList<Stack> pathList = new ArrayList();
    private ArrayList<Integer> timeList = new ArrayList();

    /**
     * A constructor that create a tree map with Neuron class as key and
     * Attribute class as value.
     */
    public SearchSpace() {
        space = new TreeMap();
    }

    /**
     * Add a new node to the search space.
     *
     * @param id is the id of the newly added node
     * @param connectNum is the number of connected node to the newly added node
     * @param lifetime is the number of time a node can pass the message
     */
    public void addNode(int id, int connectNum, int lifetime) {
        space.put(id, new Neuron(connectNum, lifetime));
    }

    /**
     * Add a new connected node info to a specific node.
     *
     * @param fromID is the id of a node in search space
     * @param toID is the id of a node connected to the fromID node
     * @param time is the time traveled from fromID node to toID node
     * @param distance is the distance traveled from fromID node to toID node
     */
    public void addSynapse(int fromID, int toID, int time, int distance) {
        space.get(fromID).addSynapse(toID, time, distance);
        space.get(toID).addBFSNode(fromID, toID, time);
    }
    

    /**
     * Remove a specific node from search space.
     *
     * @param id is the id of the node want to remove
     */
    public void removeNode(int id) {
        space.remove(id);
        for (Map.Entry<Integer, Neuron> entry : space.entrySet()) {
            if (entry.getValue().containsSynapse(id)) {
                entry.getValue().removeSynapse(id);
            }
        }
    }

    /**
     * Remove all the nodes in search space.
     */
    public void clear() {
        space.clear();
    }
    
    public Neuron get(int ID){
        return space.get(ID);
    }

    /**
     * Convert all the info in search space to string
     *
     * @return a string contain all instances in this class
     */
    public String toString() {
        String info = "Search Space\n";
        for (Map.Entry<Integer, Neuron> entry : space.entrySet()) {
            info += "Node ID: " + entry.getKey() + "\t" + entry.getValue().toString() + "\n";
        }
        return info;
    }

    public int nextNode(int id, int currentNode) {
        return space.get(id).getNext(currentNode);
    }

    public boolean hasNext(int id, int currentNode) {
        return space.get(id).hasNext(currentNode);
    }
    
    public String checkNode(int id){
        String output = "";
        for (Map.Entry<Integer, Neuron> entry : space.entrySet())
            if(entry.getValue().containsSynapse(id))
                output+=true;
        return output;
    }

    private int minTime = Integer.MAX_VALUE;
    private int currentTime = 0;
    private int cycle = 0;

    public void search(int start, int end) {
        breadthFirstSearch(start, end, 0);
    }

    public void completeSearch(int start, int end, int connection) {
        System.out.println("Current connected node " + start + ": " + connection + "s");
        if (start == end) {
            currentTime += space.get(goal.peek()).getTimeTo(start);
            goal.push(start);
            System.out.println("Push " + start + " into stack");
            System.out.println("goal!");
            pathList.add((Stack) goal.clone());
            timeList.add(currentTime);
            goal.pop();
            currentTime -= space.get(goal.peek()).getTimeTo(start);
            System.out.println("Pop " + start + " from stack");
            cycle++;
        } else if (hasNext(start, connection)) {
            if (!goal.empty()) {
                currentTime += space.get(goal.peek()).getTimeTo(start);
            }
            goal.push(start);
            System.out.println("Push " + start + " into stack");
            System.out.println("Next Node: " + nextNode(start, connection));
            search(nextNode(start, connection), end);
            connection = nextNode(start, connection);
            System.out.println("last: " + connection + "s");
            goal.pop();
            if (!goal.empty()) {
                currentTime -= space.get(goal.peek()).getTimeTo(start);
            }
            System.out.println("Pop " + start + " from stack");
            completeSearch(start, end, connection);
            cycle++;
        }
    }

    public void pruneSearch(int start, int end, int connection) {
        System.out.println("MinTime: " + minTime + "s");
        System.out.println("Current connected node " + start + ": " + currentTime + "s");
        if (start == end) {
            currentTime += space.get(goal.peek()).getTimeTo(start);
            if (currentTime <= minTime) {
                goal.push(start);
                System.out.println("Push " + start + " into stack");
                System.out.println("goal!");
                pathList.add((Stack) goal.clone());
                minTime = currentTime;
                timeList.add(minTime);
                goal.pop();
                currentTime -= space.get(goal.peek()).getTimeTo(start);
                System.out.println("Pop " + start + " from stack");
                cycle++;
            } else {
                currentTime -= space.get(goal.peek()).getTimeTo(start);
                System.out.println("///Prune!///");
            }
        } else if (hasNext(start, connection)) {
            if (!goal.empty()) {
                currentTime += space.get(goal.peek()).getTimeTo(start);
            }
            if (currentTime <= minTime) {
                goal.push(start);
                System.out.println("Push " + start + " into stack");
                System.out.println("Next Node: " + nextNode(start, connection));
                search(nextNode(start, connection), end);
                connection = nextNode(start, connection);
                System.out.println("Current Time: " + currentTime + "s");
                goal.pop();
                if (!goal.empty()) {
                    currentTime -= space.get(goal.peek()).getTimeTo(start);
                }
                System.out.println("Pop " + start + " from stack");
                pruneSearch(start, end, connection);
                cycle++;
            } else {
                currentTime -= space.get(goal.peek()).getTimeTo(start);
                System.out.println("///Prune!///");
            }
        }
    }

    
    
    
    
    
    
    
    
    /////Need to create a new Node class , with instance parent , Node's id and time (all int)
    
    public void breadthFirstSearch(int start, int end, int connection) {
        LinkedList<Integer> open = new LinkedList();
        LinkedList<Integer> close = new LinkedList();
        LinkedList<Integer> temp = new LinkedList();
        System.out.println("Current Node : " + start);
        open.add(start);
        System.out.println("Add "+start+" into open queue.");
        while (!open.isEmpty()) {
            int front = open.remove(0);
            cycle++;
            close.add(front);
            space.get(front).setVisited();
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
                    for(int j = temp.size()-1  ; j > 0 ; j--){
                    currentTime += space.get(temp.get(j-1)).getTimeTo(temp.get(j));
                }
                for(int k = 0 ; k < temp.size() ; k++){
                    goal.add(temp.get(k));
                }
                pathList.add((Stack)goal.clone());
                goal.clear();
                timeList.add(currentTime);
                System.out.println("Get goal ! "+front);
                }
                
                open.clear();
            } else {
                while (hasNext(front, connection)) {
                    int next = nextNode(front, connection);
                    if (space.get(next).checkVisit() == false) {
                        open.add(next);
                        System.out.println("Explore the node below "+front+" : "+next+" is found.");
                        System.out.println(next+" is added into the open queue");
                        connection = nextNode(front,connection);
                    }else{
                        System.out.println("The Node is explored");
                        break;
                    }
                }
                
            }

        }

    }

    public void solution() {
        int i = 0;
        System.out.println("");
        System.out.println("");
        System.out.println(pathList.size() + " path(s) found!");
        for (Stack ptr : pathList) {
            while (!ptr.empty()) {
                System.out.print(ptr.pop() + "<-");
            }
            System.out.println("\nTime used: " + timeList.get(i));
            i++;
            System.out.println("");
        }
        System.out.println("Cycle: " + cycle);

    }

}
