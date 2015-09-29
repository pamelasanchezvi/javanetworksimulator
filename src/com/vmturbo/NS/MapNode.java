/**
 *
 */
package com.vmturbo.NS;

/**
 * @author pamelasanchez
 *
 */


public class MapNode implements Comparable<MapNode>{

    String name;
    double dist;
    double x;
    double y;
    boolean paint=true;

  //    ArrayList<adjacency> adjList = new ArrayList<adjacency>();

    public MapNode(String name, Double x, Double y){
        this.x= x;
        this.y = y;
        this.name = name;
        this.dist = Double.POSITIVE_INFINITY ; //or infinity??
    //      this.adjList.clear();


   }

   @Override
public int compareTo(MapNode o1){
        if(this.dist == o1.dist)
            return 0;
        else if((this.dist) > o1.dist)
            return 1;
        else
            return -1;
   }
}