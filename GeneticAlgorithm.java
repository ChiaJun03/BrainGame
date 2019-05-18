/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import java.util.Arrays;
import java.util.TreeMap;

/**
 *
 * @author Yuan Qing
 */
public class GeneticAlgorithm {

    private TreeMap<Integer, Neuron> treemap;
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;
    protected int tournamentSize;

    /**
     * Constructor of GeneticAlgorithm and initialize constants
     *
     * @param treeObject the SearchSpace object
     * @param populationSize
     * @param mutationRate
     * @param crossoverRate
     * @param elitismCount
     * @param tournamentSize
     */
    public GeneticAlgorithm(SearchSpace treeObject, int populationSize, double mutationRate, double crossoverRate, int elitismCount,
            int tournamentSize) {
        this.treemap = treeObject.getTreeMap();
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.tournamentSize = tournamentSize;
    }

    /**
     * Initialize population
     *
     * @param startID the start node of the search
     * @return population The initial population generated
     */
    public Population initPopulation(int startID) {
        // Initialize population
        Population population = new Population(this.treemap, this.populationSize, startID);
        return population;
    }

    /**
     * Check if population has met termination condition -- this termination
     * condition is a simple one; simply check if we've exceeded the allowed
     * number of generations.
     *
     * @param generationsCount Number of generations passed
     * @param maxGenerations Number of generations to terminate after
     * @return boolean True if termination condition met, otherwise, false
     */
    public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
        return (generationsCount > maxGenerations);
    }

   


    /**
     * Evaluate population -- basically run calcFitness on each individual.
     *
     * @param population the population to evaluate
     * @param endID the end node of the search
     */
    public void evalPopulation(Population population, int endID) {
        double populationFitness = 0;
        Individual[] individualArray =population.getIndividuals();
        // Loop over population evaluating individuals and summing population fitness
        for (int iterate=0 ; iterate< individualArray.length ; iterate++) {
            //System.out.println(endID);
            individualArray[iterate].checkGoal(endID);
            populationFitness += individualArray[iterate].getFitness();
        }

        double avgFitness = populationFitness / population.size();
        population.setPopulationFitness(avgFitness);
    }

    /**
     * Selects parent for crossover using tournament selection
     *
     * @param population the population to select
     * @return The individual selected as a parent
     */
    public Individual selectParent(Population population) {
        // Create tournament
        Population tournament = new Population(this.treemap, this.tournamentSize);

        // Add random individuals to the tournament
        population.shuffle();
        for (int i = 0; i < this.tournamentSize; i++) {
            Individual tournamentIndividual = population.getIndividual(i);
            tournament.setIndividual(i, tournamentIndividual);
        }

        // Return the best
        return tournament.getFittest(0);
    }

    /**
     * briefly explain crossover
     *
     * @param population
     * @param endID
     * @return The new population
     */
    public Population crossoverPopulation(Population population, int endID) {
        // Create new blank population
        Population newPopulation = new Population(this.treemap, population.size());

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            // Get parent1
            Individual parent1 = population.getFittest(populationIndex);
            parent1.checkGoal(endID);
            //if the individual does not reach goal get random next
            if (!parent1.getGoal()) {
                //no more path regenerate path
                if (treemap.get(parent1.getPath().get(parent1.getPath().size()-1)).getRandomNext(parent1.getHashSet()) == -1) {
                    int temp = parent1.getPath().get(0);
                    parent1.getPath().clear();
                    parent1.getPath().add(temp);
                    parent1.getHashSet().clear();
                    parent1.getHashSet().add(temp);
                    parent1.checkGoal(endID);
                }
                parent1.getPath().add(treemap.get(parent1.getPath().get(parent1.getPath().size()-1)).getRandomNext(parent1.getHashSet()));
                parent1.getHashSet().add(parent1.getPath().get(parent1.getPath().size()-1));
                parent1.calculateTime();
                parent1.calculateDistance();
                parent1.calculateFitness();
            }
            
            // Apply crossover to this individual?
            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
                // Find parent2 with tournament selection
                Individual parent2 = this.selectParent(population);
                Individual offspring = parent1;
                //to prevent crossover with individual that havent reach goal
                if (!(parent1.getGoal() == false && parent2.getGoal() == false)) {

                    int counter1 = parent1.getPath().size();
                    for (int i = 0; i < counter1; i++) {
                        if (parent2.getPath().contains(parent1.getPath().get(i))) {
                            int neuron = parent1.getPath().get(i);
                            int index1 = parent1.getPath().indexOf(neuron);
                            int index2 = parent2.getPath().indexOf(neuron);
                            if (parent1.getGoal() == true && parent2.getGoal() == true) {
                                if (parent1.getPath().size() - index1 >= parent2.getPath().size() - index2) {
                                    //parent2 behind
                                    //remove the neuron behind of the parent1
                                    while (offspring.getPath().get(offspring.getPath().size()-1) != neuron) {
                                        int remove = offspring.getPath().remove(offspring.getPath().size()-1);
                                        offspring.getHashSet().remove(remove);
                                    }
                                    // add the neuron behind of the parent2
                                    for (int iterate = index2 + 1; iterate < parent2.getPath().size(); iterate++) {
                                        offspring.getPath().add(parent2.getPath().get(iterate));
                                        offspring.getHashSet().add(offspring.getPath().get(offspring.getPath().size()-1));
                                    }
                                } else {
                                    //parent 2 infront
                                    //remove the neuron infront of the parent1
                                    while (offspring.getPath().get(0) != neuron) {
                                        int remove = offspring.getPath().remove(0);
                                        offspring.getHashSet().remove(remove);
                                    }
                                    // add the neuron infront of the parent 2
                                    for (int iterate = index2 - 1; iterate > -1; iterate--) {
                                        offspring.getPath().add(0,parent2.getPath().get(iterate));
                                        offspring.getHashSet().add(parent2.getPath().get(iterate));
                                    }

                                }

                            } else if (parent1.getGoal() == true && parent2.getGoal() == false) {
                                //parent1 behind
                                //remove the neuron infront of the parent1
                                while (offspring.getPath().get(0) != neuron) {
                                    int remove = offspring.getPath().remove(0);
                                    offspring.getHashSet().remove(remove);
                                }
                                // add the neuron infront of the parent 2
                                for (int iterate = index2 - 1; iterate > -1; iterate--) {
                                    offspring.getPath().add(0,parent2.getPath().get(iterate));
                                    offspring.getHashSet().add(parent2.getPath().get(iterate));
                                }
                            } else {
                                //parent2 behind
                                //remove the neuron behind of the parent1
                                while (offspring.getPath().get(offspring.getPath().size()-1) != neuron) {
                                    int remove = offspring.getPath().remove(offspring.getPath().size()-1);
                                    offspring.getHashSet().remove(remove);
                                }
                                // add the neuron behind of the parent2
                                for (int iterate = index2 + 1; iterate < parent2.getPath().size(); iterate++) {
                                    offspring.getPath().add(parent2.getPath().get(iterate));
                                    offspring.getHashSet().add(offspring.getPath().get(offspring.getPath().size()-1));
                                }
                            }
                            break;
                        }
                    }
                    offspring.checkGoal(endID);
                    offspring.calculateTime();
                    offspring.calculateDistance();
                    offspring.calculateFitness();
                }
                newPopulation.setIndividual(populationIndex, offspring);
                 
            }else {
                // Add individual to new population without applying crossover
                newPopulation.setIndividual(populationIndex, parent1);
            }

        }
        return newPopulation;
    }

    /**
     * Apply mutation to population briefly explain mutation process
     *
     * @param population The population to apply mutation to
     * @param startID the start node
     * @param endID the end node
     * @return The mutated population
     */
    public Population mutatePopulation(Population population, int startID, int endID) {
        // Initialize new blank population
        Population newPopulation = new Population(this.treemap, this.populationSize);
        // different rate for diffrent condition
        double changedRate;

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual individual = population.getFittest(populationIndex);

            // Skip mutation if this is an elite individual
            if (populationIndex >= this.elitismCount) {
                if (individual.getGoal()) {
                    changedRate = this.mutationRate;
                } else {
                    changedRate = this.mutationRate / 100;
                }
                // Does this gene need mutation?
                if (changedRate > Math.random()) {
                    // If mutataion occurs generate a new path
                    int length = population.getFittest(0).getPath().size();
                    individual = new Individual(treemap, startID, endID, length);
                    //individual.checkLoop(individual.getPath());
                    individual.calculateTime();
                    individual.calculateDistance();
                    individual.calculateFitness();
                }

            }
            // Add individual to population
            newPopulation.setIndividual(populationIndex, individual);
        }
        // Return mutated population
        return newPopulation;
    }

    public void search(int startID, int endID) {

        //Initialize population
        Population population = initPopulation(startID);
        evalPopulation(population, endID);
        int generation = 1;
        int maxGenerations = 1000;
        while (isTerminationConditionMet(generation, maxGenerations) == false) {
            // Print fittest individual from population

            // Apply crossover
            population = crossoverPopulation(population, endID);
            // Apply mutation
            evalPopulation(population, endID);
            population = mutatePopulation(population, startID, endID);
            // Evaluate population
            evalPopulation(population, endID);

            population.arrange();
            population.toString(generation);
            // Increment the current generation
            generation++;
        }

        Individual bestIndividual = population.getFittest(0);
        System.out.println("Stopped after " + maxGenerations + " generations.");
        System.out.println(bestIndividual);
    }
}
