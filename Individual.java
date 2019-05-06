//package braingame;

import java.util.LinkedList;
import java.util.TreeMap;

public class Individual {

	private TreeMap<Integer,Neuron> treemap;
	private LinkedList<Integer> path;
	private int distance = 0 ;
	private int time;
	private double fitness = -1;
	private boolean goal;

	/**
	 * Initializes individual with specific path
	 *
	 * @param treemap the treemap of ID and Neuron
	 * @param path the indivodual's path
	 * @param endID the end node of the search

	public Individual(TreeMap<Integer , Neuron> treemap , LinkedList<Integer> path ,int endID) {
		// Create individual path
		this.path = path;
		this.treemap = treemap;
		checkGoal(endID);
		// initialise time ( to prevent arithmetic error)
		time = 100;

		calculateTime();
		calculateDistance();
	}

	/**
	 * Initializes individual that undergo mutation
	 *
	 * @param treemap the treemap of ID and Neuron
	 * @param startID the start node of the Search
	 * @param endID the end node of the Search
	 * @param length the length of the previous generation path
	 */
	public Individual(TreeMap<Integer , Neuron> treemap , int startID , int endID , int length ) {
		// Create individual path
		this.path = new LinkedList<>();
		this.treemap = treemap;;
		this.path.add(startID);
		for(int i = 1 ; i < length ; i++){
			path.add(treemap.get(path.get(i-1)).getRandomNext());
		}
		checkGoal(endID);
		// initialise time ( to prevent arithmetic error)
		time=100;

		calculateTime();
		calculateDistance();
	}

	/**
	 * Initializes random individual
	 * @param treemap the treemap of ID and Neuron
	 * @param startID the starting node
	 */
	public Individual(TreeMap<Integer , Neuron> treemap , int startID) {
		this.treemap = treemap;
		this.path = new LinkedList<>();
		this.path.add(startID);
		// initialise time ( to prevent arithmetic error)
		time=100;
		calculateTime();
		calculateDistance();
	}

   /**
	*	Check for looping occurance in the random generated path and remove it
	*	@param list the LinkedList path that to be check
	*/
    public void checkLoop(LinkedList<Integer> list){
		for(int iterate = 0 ; iterate < list.size();iterate++){
			if(list.indexOf(list.get(iterate))!=list.lastIndexOf(list.get(iterate))){
				int temp1 = list.indexOf(list.get(iterate));
				int temp2 = list.lastIndexOf(list.get(iterate));
				for(int i = temp1 ; i < temp2 ; i++){
					list.remove(temp1);
				}
				//regenerateBack(list,temp2-temp1);
			}
		}
    }

	/**
	 *	Retrieve total distance for this path
     *	@return total distance for this path
     */
    public int getDistance(){
        return distance;
    }

    /**
	 *	Retrieve total time for this path
     * 	@return total time for this path
     */
    public int getTime(){
        return time;
    }

	/**
	 * 	Gets individual's path list
	 * 	@return the path list
	 */
	public LinkedList<Integer> getPath() {
		return path;
	}

	/**
	 *	Set individual's fitness
	 *	@param fitness The individuals fitness
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	/**
	 * Gets individual's fitness
	 * @return The individual's fitness
	 */
	public double getFitness() {
		return fitness;
	}

	/**
	 *	Get individual's goal condition
	 *	@return the individual's goal boolean
	 */

	public boolean getGoal(){
		return goal;
	}

	/**
	 *	Check individual's goal condition and update import junit.framework.TestCase;
	 *	@param ID the end node of the search
	 */

	public void checkGoal(int ID){
		if(path.getLast() == ID){
			goal = true;
		}else{
			goal = false;
		}
	}

	// need optimization
	public String toString() {
		String output = "";
		for(int i = 0 ; i<path.size();i++){
			System.out.print(path.get(i)+" ");
		}
		System.out.println(getGoal()+" Fitness: "+getFitness() +" Time: "+getTime());
		System.out.println();
		return "";
	}

	/**
	 *	Calculate the total time for the path
	 */
	public void calculateTime(){
		if(path.size()>1){
			time=100;
			for(int i = 1 ; i < path.size() ; i++ ){
				time += this.treemap.get(path.get(i-1)).getTimeTo(path.get(i));
			}
		}
	}

	/**
	 *	Calculate the total distance for the path
	 */
	public void calculateDistance(){
		if(path.size()>1){
			distance=0;
			for(int i = 1 ; i < path.size() ; i++ ){
				distance += this.treemap.get(path.get(i-1)).getDistanceTo(path.get(i));
			}
		}
	}

}
