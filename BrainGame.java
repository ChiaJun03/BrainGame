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
        int [] point = new int [2];
        testCase(simulation);
        randomPoint(point);
        
        /*
        simulation.addNode(1, 1, 2);
        simulation.addNode(2, 3, 2);
        simulation.addNode(3, 2, 2);
        simulation.addNode(4, 1, 2);
        
        simulation.addSynapse(1, 2, 1, 1);
        simulation.addSynapse(2, 4, 1, 1);
        simulation.addSynapse(2, 1, 2, 1);
        simulation.addSynapse(2, 3, 2, 5);
        simulation.addSynapse(3, 1, 5, 5);
        simulation.addSynapse(3, 2, 7, 5);
        simulation.addSynapse(4, 2, 1, 5);
        */
        //System.out.println(simulation);
        
        //simulation.removeNode(2);
        
        //System.out.println(simulation.toString());
        
        //System.out.println(simulation.nextNode(1, 0));
        
        /*
        BasicSearch DFSS = new BasicSearch(simulation);
        DFSS.search(start, end);
        System.out.println(DFSS);
        */
        
        /*
        DepthFirstSearch DFS = new DepthFirstSearch(simulation);
        DFS.search(start, end);
        System.out.println(DFS);
        */
        
        /*
        BreadthFirstSearch BFS = new BreadthFirstSearch(simulation);
        BFS.search(point[0], point[1]);
        System.out.println(BFS);
        */
        
        /*
        PruneSearch pruning = new PruneSearch(simulation);
        pruning.search(start, end);
        System.out.println(pruning);
        */
        
        /*
        BestFirstSearch bestSearch = new BestFirstSearch(simulation);
        bestSearch.search(point[0], point[1]);
        System.out.println(bestSearch);
        */
        
        /*
        AStarSearch search = new AStarSearch(simulation);
        System.out.println(search.printTable());
        
        randomPoint(point);
        search.search(point[0], point[1]);
        randomPoint(point);
        search.search(point[0], point[1]);
        randomPoint(point);
        search.search(point[0], point[1]);
        randomPoint(point);
        search.search(point[0], point[1]);
        randomPoint(point);
        search.search(point[0], point[1]);
        
        search.search(1, 4);
        */
        
        
        GeneticAlgorithm GA = new GeneticAlgorithm(simulation, 50, 0.3, 0.8, 5, 5);
        GA.search(point[0], point[1]);
        
        
    }
    
    public static void testCase(SearchSpace simulation){
        Random rand = new Random();
        int id = 1000;
        int num, toID;
        for(int i=1; i<=id; i++){
            simulation.addNode(i, num = rand.nextInt(8)+1, rand.nextInt(9)+1);
            for(int j=0; j<num; j++){
                toID = rand.nextInt(id)+1;
                while(simulation.get(i).containsSynapse(toID)||toID==i)
                    toID = rand.nextInt(id)+1;
                simulation.addSynapse(i, toID, rand.nextInt(19)+1, rand.nextInt(9)+1);
            }
        }
    }
    
    public static void randomPoint(int [] point){
        Random rand = new Random();
        int start = rand.nextInt(999)+1;
        int end = rand.nextInt(999)+1;
        while(end==start)
            end = rand.nextInt(999)+1;
        point[0] = start;
        point[1] = end;
    }
    
    
    
}
