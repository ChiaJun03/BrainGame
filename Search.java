/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import java.util.ArrayList;

/**
 *
 * @author Jing Chong   
 */
public interface Search {
    public abstract void search(int start, int end);
    
    public abstract void reset();
    
    public abstract void preSearch(int start);
    
    public abstract ArrayList<Integer> getPath();
    
    public abstract ArrayList<Integer> trackPath();
}
