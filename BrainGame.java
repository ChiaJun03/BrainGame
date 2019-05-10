/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import java.util.Random;

/**
 *
 * @author Jing Chong
 */
public class BrainGame{
    
    public BrainGame(){
        
    }

    public static void main(String[] args) {
        Random rand = new Random();
        SearchSpace simulation = new SearchSpace();
        testCase(simulation);
        
        //System.out.println(simulation);
        
        //simulation.removeNode(2);
        
        //System.out.println(simulation.toString());
        
        //System.out.println(simulation.nextNode(1, 0));
        
        int start = rand.nextInt(20)+1;
        int end = rand.nextInt(20)+1;
        while(end==start)
            end = rand.nextInt(20)+1;
        System.out.println("Search path from node "+start+" to node "+end+":");
        
        
        /*
        DepthFirstSearch DFS = new DepthFirstSearch(simulation);
        DFS.search(start, end);
        System.out.println(DFS);
        
        
        
        BreadthFirstSearch BFS = new BreadthFirstSearch(simulation);
        BFS.search(start, end);
        System.out.println(BFS);
        */
        
        /*
        BasicSearch DFSS = new BasicSearch(simulation);
        DFSS.search(start, end);
        System.out.println(DFSS);
        
        
        
        PruneSearch pruning = new PruneSearch(simulation);
        pruning.search(start, end);
        System.out.println(pruning);
        
        
        
        AStarSearch search = new AStarSearch(simulation);
        System.out.println(search);
        //search.search(1, 2);
        */
        
        
        GeneticAlgorithm GA = new GeneticAlgorithm(simulation, 10, 0.3, 0.8, 5, 2);
        GA.search(start, end);
        
    }
    
    public static void testCase(SearchSpace simulation){
        Random rand = new Random();
        int id = 20;
        int num, toID;
        for(int i=1; i<=id; i++){
            simulation.addNode(i, num = rand.nextInt(9)+1, rand.nextInt(9)+1);
            for(int j=0; j<num; j++){
                toID = rand.nextInt(id)+1;
                while(simulation.get(i).containsSynapse(toID)||toID==i)
                    toID = rand.nextInt(id)+1;
                simulation.addSynapse(i, toID, rand.nextInt(19)+1, rand.nextInt(9)+1);
            }
        }
    }
    
    
}
