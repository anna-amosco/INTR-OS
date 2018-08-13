/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package introos.mco2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Station {

    private int id;
    private Train currentTrain;
    private int curPassengers;  //num of passengers currently waiting in station    
    private ArrayList<Passenger> passengers;
    private ArrayList<Thread> station_robots;
    private String status; //if train is idle or occupied
    private boolean station_hasTrain; //if there is a train at the station currently
    
    private Semaphore mutex;
       
    //maybe i should just pass train from station to station
    public Station() {
        this.id = 0;   
        this.currentTrain = null;
        this.curPassengers = 0;
        this.station_hasTrain = false;
        this.passengers = new ArrayList();  
        this.mutex = new Semaphore(1);
        this.status = "IDLE";
        this.station_robots = new ArrayList();
    }

    public Station(int id) {
        this.id = id;   
        this.currentTrain = null;
        this.curPassengers = 0;
        this.station_hasTrain = false;
        this.passengers = new ArrayList();  
        this.mutex = new Semaphore(1);
        this.status = "IDLE";
        this.station_robots = new ArrayList();
    }
    
    public void acquire_mutex(){
        try{
            this.mutex.acquire();
        }catch(Exception e){
            
        }        
    }
    
    public void release_mutex(){
        this.mutex.release();
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }
    
    public void addPassenger(Passenger p){
        this.passengers.add(p);
    }
    
    public Passenger removeAndGetPassenger() {
            return passengers.remove(0);
    }    

    public int getId() {
        return id;
    }

    public Train getCurrentTrain() {
        return currentTrain;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCurrentTrain(Train currentTrain) {
        this.currentTrain = currentTrain;
    }

    public int getCurPassengers() {
        return curPassengers;
    }

    public void setCurPassengers(int curPassengers) {
        this.curPassengers = curPassengers;
    }
    
    /**
     * load train into station
     * @param train_freeSeats integer value of free seats in train currently at station
     */
    public synchronized void load_train(int train_freeSeats){
        this.acquire_mutex();
        System.out.println("train is now boarding passengers");
        if(curPassengers == 0){
            System.out.println("No passengers in station " + this.getId());
        }else if(train_freeSeats == 0){
            System.out.println("Train is full! Train is leaving station");
        }else{
            System.out.println("Passengers boarding");
            this.notifyAll();
        }
        
        try{
           //train declaring its arrival at station
           System.out.println("Arrived at ");
           Thread.sleep(3000);
        }catch(InterruptedException e){
            System.out.println(e);
        }
        
        this.release_mutex();        
    }
    
    /**
     * station waits for train
     * @param p passenger returns to waiting on station if there is no train yet
     */
    public synchronized void waiting_for_train(Passenger p){
        this.acquire_mutex();
        this.release_mutex();
        try{
            wait();
        }catch(Exception e){
            
        }
        
        this.passengers_boarded(p);
    }
    
    /**
     * generates passengers for station
     * @param numPassengers integer value how many passengers to be generated
     * @param destStation integer id of station passenger to be added to
     */
    public void generatePassengers(int numPassengers, int destStation){
        this.curPassengers += numPassengers;
        
        for(int i=0;i<numPassengers;i++){
            Passenger p = new Passenger(destStation,this);
            passengers.add(p);
            station_robots.add(new Thread(p));
            station_robots.get(station_robots.size()-1).start();
        }
        System.out.println("Station [" + this.id + "] now has " 
                + this.curPassengers+ " waiting");
    }
    
    /**
     * checks if all passengers are on board
     * @param p an incoming single passenger
     */
    public void passengers_boarded(Passenger p){        
        this.acquire_mutex();
        try{
            if(currentTrain.getOccupied_seats() == 0){
                this.release_mutex();
                waiting_for_train(p);
            }else{
                int passengers_onboard = this.currentTrain.getOccupied_seats();
                try{
                    currentTrain.get_Semaphore().acquire();
                }catch(Exception e){
                    System.out.println(e);
                }    
                this.curPassengers--;
                int currentPassengersCount = currentTrain.getOccupied_seats()+1;
                currentTrain.setOccupied_seats(passengers_onboard + 1);
                currentTrain.addPassenger(p);
                station_robots.remove(p);
                
                System.out.println("train[" + currentTrain.getId()+ "]:");
                System.out.println("Passenger " + p.getId()+ " has boarded the train");
                System.out.println("Available seats left: " + currentTrain.getAvailableSeats());
            }   System.out.println("Passengers onboard: " + currentTrain.getOccupied_seats());
            
        }catch(Exception e){
            System.out.println(e);
        }
        this.release_mutex();
    }
}
