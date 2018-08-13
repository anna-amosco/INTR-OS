/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package introos.mco2;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * class for train system functionality
 * @author author
 */
public class TrainSystem {
    private static int maxTrains = 16;
    private static int numStations = 8;
    static Thread[] train_threads = new Thread[maxTrains];
    public static Station[] trainStations = new Station[8];
    
    public static int numOfTrains = 0;
    static Semaphore availableSeats;

    public TrainSystem() {
        for(int i = 0; i< numStations; i++){
            Station station = new Station(i);
            trainStations[i] = station;
        }
    }
    
    /**
     * dispatches a new train into the train system
     * creates new train object, adds it to array of trains running 
     * in system train_threads
     * @param id integer value train id
     * @param capacity integer value of passenger capacity
     */
    public static void dispatchTrain(int id, int capacity){
        if(numOfTrains<16){
            availableSeats = new Semaphore(capacity, true);
        
            Train t = new Train(id, capacity,trainStations, availableSeats);
            train_threads[numOfTrains] = new Thread(t);
            train_threads[numOfTrains].start();
            numOfTrains++;
        }        
    }
    
    public static void generatePassengers(){
        int passengers_to_generate = 3;        
        for(int i=0;i<numStations;i++){
            int destStation = uniqueDest(i);
            trainStations[i].generatePassengers(passengers_to_generate,destStation);
        }
    }
    
    public static int uniqueDest(int given){
        int n;
        do{
            n = randNum(0,numStations-1);
        }while(given == n);
        return n;
    }
    
    public static int randNum(int max, int min){
        Random rand = new Random();
        int  n = rand.nextInt(max) + min;
        return n;
    }    
    
}
