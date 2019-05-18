/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeMap;

/**
 *
 * @author Yuan Qing
 */
public class Population {

    private TreeMap<Integer , Neuron> treemap;
    private Individual[] population;
    private double populationFitness = -1;

    /**
     *	Initializes blank population of individuals
     *	@param treemap the treemap of ID and Neuron
     *	@param populationSize The size of the population
     */
    public Population(TreeMap<Integer , Neuron> treemap , int populationSize) {
        // Initial a blank individual Array
        this.population = new Individual[populationSize];
        this.treemap = treemap;
    }

    /**
     *	Initializes population of individuals
     *	@param treemap the treemap of ID and Neuron
     *	@param populationSize The size of the population
     *	@param startID the start node of the search
     */
    public Population(TreeMap<Integer , Neuron> treemap , int populationSize, int startID ) {
        // Initial population Array
        this.population = new Individual[populationSize];
        this.treemap = treemap;

        // Loop over population size
        for (int individualCount = 0; individualCount < populationSize; individualCount++) {
                // Create individual
                Individual individual = new Individual(treemap, startID);
                // Add individual to population Array
                this.population[individualCount] = individual;
        }
    }

    /**
     * Get individuals array from the population
     * @return individual's Array
     */
    public Individual[] getIndividuals() {
        return this.population;
    }

    /**
     * Find fittest individual in the population
     * @param offset
     * @return individual Fittest individual at offset
     */
    public Individual getFittest(int offset) {
        // Order population by fitness
        Arrays.sort(this.population, new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                    if (o1.getFitness() > o2.getFitness()) {
                            return -1;
                    } else if (o1.getFitness() < o2.getFitness()) {
                            return 1;
                    }
                    return 0;
            }
        });

        // Return the fittest individual
        return this.population[offset];
    }

    public void arrange(){
        Arrays.sort(this.population, new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                    if (o1.getFitness() > o2.getFitness()) {
                            return -1;
                    } else if (o1.getFitness() < o2.getFitness()) {
                            return 1;
                    }
                    return 0;
            }
        });
    }

    /**
     * Set population's fitness
     * @param fitness The population's total fitness
     */
    public void setPopulationFitness(double fitness) {
        this.populationFitness = fitness;
    }

    /**
     * Get population's fitness
     * @return populationFitness The population's total fitness
     */
    public double getPopulationFitness() {
        return this.populationFitness;
    }

    /**
     * Get population's size
     * @return size The population's size
     */
    public int size() {
        return this.population.length;
    }

    /**
     * Set individual at offset
     * @param individual the individual object to be set
     * @param offset
     * @return individual object of the specific individual
     */
    public Individual setIndividual(int offset, Individual individual) {
        return population[offset] = individual;
    }

    /**
     * Get individual at offset
     * @param offset
     * @return individual object at the offset
     */
    public Individual getIndividual(int offset) {
        return population[offset];
    }

    /**
     * Shuffles the population in-place
     */
    public void shuffle() {
        Random rnd = new Random();
        for (int i = population.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Individual a = population[index];
            population[index] = population[i];
            population[i] = a;
        }
    }

    // need optimization
    public String toString(int ge){
        System.out.println("Generation: "+ge);
        String output ="";
        for(int iterate = 1 ; iterate <= population.length ; iterate++){
            System.out.println("Path: "+iterate);
            output+=population[iterate-1].toString();
        }
        return "";
    }

}