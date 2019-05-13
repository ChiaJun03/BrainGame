/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashSet;

/**
 *
 * @author Yuan Qing
 */
public class Individual {

    private HashSet<Integer> nodeSet;
    private TreeMap<Integer, Neuron> treemap;
    private ArrayList<Integer> path;
    private int distance;
    private int time;
    private double fitness;
    private boolean goal;

    /**
     * Initializes individual that undergo mutation
     *
     * @param treemap the treemap of ID and Neuron
     * @param startID the start node of the Search
     * @param endID the end node of the Search
     * @param length the length of the previous generation path
     */
    public Individual(TreeMap<Integer, Neuron> treemap, int startID, int endID, int length) {
        // Create individual path
        this.path = new ArrayList<>();
        this.nodeSet = new HashSet<>();
        this.treemap = treemap;
        this.path.add(startID);
        nodeSet.add(startID);
        for (int i = 1; i < length; i++) {
            if (treemap.get(path.get(i - 1)).getRandomNext(nodeSet) == -1) {
                this.path.clear();
                nodeSet.clear();
                path.add(startID);
                nodeSet.add(startID);
                i = 1;
            }
            path.add(treemap.get(path.get(i - 1)).getRandomNext(nodeSet));
            nodeSet.add(path.get(path.size()-1));
        }
        checkGoal(endID);
        // initialise time ( to prevent arithmetic error)
        time = 100;

        calculateTime();
        calculateDistance();
        calculateFitness();
    }

    /**
     * Initializes random individual
     *
     * @param treemap the treemap of ID and Neuron
     * @param startID the starting node
     */
    public Individual(TreeMap<Integer, Neuron> treemap, int startID) {
        this.treemap = treemap;
        this.path = new ArrayList<>();
        this.nodeSet = new HashSet<>();
        this.path.add(startID);
        this.nodeSet.add(startID);
        // initialise time ( to prevent arithmetic error)
        time = 100;
        calculateTime();
        calculateDistance();
        calculateFitness();
    }

    /**
     * Retrieve total distance for this path
     *
     * @return total distance for this path
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Retrieve total time for this path
     *
     * @return total time for this path
     */
    public int getTime() {
        return time;
    }

    /**
     * Gets individual's path list
     *
     * @return the path list
     */
    public ArrayList<Integer> getPath() {
        return path;
    }

    public HashSet<Integer> getHashSet() {
        return nodeSet;
    }

    /**
     * Set individual's fitness
     *
     * @param fitness The individuals fitness
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * Gets individual's fitness
     *
     * @return The individual's fitness
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Get individual's goal condition
     *
     * @return the individual's goal boolean
     */
    public boolean getGoal() {
        return goal;
    }

    /**
     * Check individual's goal condition and update import
     * junit.framework.TestCase;
     *
     * @param ID the end node of the search
     */
    public boolean checkGoal(int ID) {
        if (path.get(path.size()-1) == ID) {
            goal = true;
        } else {
            goal = false;
        }
        return goal;
    }

    // need optimization
    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i) + " ");
        }
        System.out.println(getGoal() + " Fitness: " + getFitness() + " Time: " + getTime());
        System.out.println();
        return "";
    }

    /**
     * Calculate the total time for the path
     */
    public void calculateTime() {
        if (path.size() > 1) {
            time = 100;
            for (int i = 1; i < path.size(); i++) {
                time += this.treemap.get(path.get(i - 1)).getTimeTo(path.get(i));
            }
        }
    }

    /**
     * Calculate the total distance for the path
     */
    public void calculateDistance() {
        if (path.size() > 1) {
            distance = 0;
            for (int i = 1; i < path.size(); i++) {
                distance += this.treemap.get(path.get(i - 1)).getDistanceTo(path.get(i));
            }
        }
    }
    
    /**
     * calculate the individual fitness value
     */
    public void calculateFitness(){
        fitness = 1.0 / (getTime()*1.0);
        if (getGoal()) {
            fitness = fitness * 1000;
        } else {
            fitness = fitness * -1000;
        }
    }

}