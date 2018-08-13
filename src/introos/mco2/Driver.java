/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package introos.mco2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Driver {
  
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TrainSystem t = new TrainSystem();
        
        
        /*
        int seats = 3; //total seats per train
        int numPassenger = 5; //how many per station
        int numStations = 8; 
        int numTrains = 1; //total trains in system
        
        
        int randDest = 0;
        
        Train t = new Train(0,0,seats);
        t.start();
        
        //lets just say 5 passengers to each station ? every 3 secs?
        for(int j=0;j<8;j++){
            Station s = new Station(j);
            s.setUnloadingArea(addSemaphores(numTrains));
            s.setLoadingArea(addSemaphores(numTrains));
            for(int i=0;i<numPassenger;i++){
                do{
                    randDest = randNum(numStations-1,0);
                }while(randDest == j);
                
                Passenger p = new Passenger(i,j,randDest,s);
                //add passenger to station
                s.addPassenger(p);
                System.out.println("Passenger["+i+"]: src = "+j+" dest = " + randDest);
                p.start();
            }
        }                        
       */ 
        
    }
    
    /*
    public static List<Semaphore> addSemaphores(int numTrains){
        List<Semaphore> list = new ArrayList<Semaphore>();
        for(int i=0;i<numTrains;i++){
            list.add(new Semaphore(0));
        }        
        return list;        
    }
    */
    
       
}
