/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication1;

import java.lang.Math;

/**
 *
 * @author prestamour
 */
public class Point {

    int x;
    int y;
    
    public Point(){
        x=0;
        y=0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public double distance(Point p){
        return Math.sqrt(Math.pow(x-p.x,2)+Math.pow(y-p.y,2));
    }
    
    public String toString(){
        return "("+x+","+y+")";
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Point p = new Point();
        System.out.println(p);
        Point p2 = new Point(1,1);
        System.out.println(p2);
        double dist = p.distance(p2);
        System.out.println("The distance between them is: "+dist);
        
        if(dist > 2){
            System.out.println("Larger than 2");
        }else if(dist > 1){
            System.out.println("Larger than 1");
        }else{
            System.out.println("Less than 1");
        }
        
    }
    
}
