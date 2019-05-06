/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package braingame;

import java.io.IOException;
import java.util.Stack;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Random;

/**
 *
 * @author Jing Chong
 */
public class BrainGame{
    //protected static final int WIDTH = 1000, HEIGHT = 850;

    public BrainGame(){

    }


    public static void main(String[] args) {
        //launch(args);
        Stack<Integer> test = new Stack<>();
        SearchSpace simulation = new SearchSpace();
        /*
        simulation.addNode(1, 4, 1);
        simulation.addNode(2, 1, 1);
        simulation.addNode(3, 1, 1);
        simulation.addNode(4, 1, 1);
        simulation.addNode(5, 1, 1);

        simulation.addSynapse(1, 2, 2, 5);
        simulation.addSynapse(1, 3, 4, 3);
        simulation.addSynapse(1, 4, 1, 1);
        simulation.addSynapse(1, 5, 3, 2);
        simulation.addSynapse(2, 4, 5, 1);
        simulation.addSynapse(3, 5, 2, 4);
        simulation.addSynapse(4, 3, 4, 4);
        simulation.addSynapse(5, 2, 4, 4);
        */

        testCase(simulation);
        System.out.println("Start Searching \n");
        searchGA(simulation,1,10);
    }
/*
        System.out.println(simulation.toString());

        //simulation.removeNode(2);

        //System.out.println(simulation.toString());

        //System.out.println(simulation.nextNode(1, 0));

        System.out.println("Search path from node 1 to node 5:");
        simulation.search(1, 2);
        simulation.solution();

    }


    /*
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene newScene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(newScene);
        primaryStage.setTitle("Brain Game");
        primaryStage.show();

    }
*/
    public static void testCase(SearchSpace simulation){
        Random random = new Random();
        int maxNode = 60;
        int num,toID;
        for(int iterate = 1; iterate <= maxNode ; iterate++){
            simulation.addNode(iterate,num=random.nextInt(maxNode-2)+1, random.nextInt(9)+1);
            for(int j =0 ; j < num ; j++){
                toID = random.nextInt(maxNode-1)+1;
                while(simulation.getTreeMap().get(iterate).containsSynapse(toID) || toID == iterate){
                    toID = random.nextInt(maxNode-1)+1;
                }
                simulation.addSynapse(iterate,toID,random.nextInt(19)+1 , random.nextInt(9)+1);
            }
        }
    }





    public static void searchGA(SearchSpace treeObject , int startID , int endID){
        int populationSize = 50;
        double crossoverRate = 0.8;
        double mutationRate = 0.5;
        int elitismCount = (int)(populationSize*0.05);
        int tournamentSize = (int)(populationSize*0.6) ;
        //Initialize GA (treemap,populationSize,mutationRate,crossoverRate,elitismCount,tournamentsize)
        GeneticAlgorithm ga = new GeneticAlgorithm(treeObject,populationSize,mutationRate,crossoverRate,elitismCount,tournamentSize);

        //Initialize population
        Population population = ga.initPopulation(startID);
        ga.evalPopulation(population,endID);
        int generation = 1;
        int maxGenerations = 100;
        while (ga.isTerminationConditionMet(generation, maxGenerations) == false) {
			// Print fittest individual from population

			// Apply crossover
            //System.out.println(generation);
			population = ga.crossoverPopulation(population,endID);
            //System.out.println("Generation 1 " +generation);
			// Apply mutation
			population = ga.mutatePopulation(population,startID,endID);
            //System.out.println("generation"+generation);
			// Evaluate population
			ga.evalPopulation(population , endID);

            population.arrange();
            //System.out.println("Generation : "+generation);
            population.toString(generation);
			// Increment the current generation

            generation++;
		}

        Individual bestIndividual = population.getFittest(0);
        System.out.println("Stopped after " + maxGenerations + " generations.");
        System.out.println(bestIndividual);
    }

}
