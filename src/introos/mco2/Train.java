/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package introos.mco2;

import static introos.mco2.Driver.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Train implements Runnable{
    private int id;
    private int curPos;
    private int maxPassengers;
    private int occupied_seats; //num of passengers in train
    private Semaphore availableSeats; //maxPassengers - occupied_seats
    private String status; //IDLE, TRAVELLING, LOADING
    private String door_status; //OPEN, CLOSED
    private boolean train_isRunning;
    private final Station[] train_stations;
    private ArrayList<Passenger> passengers; 
    private ArrayList<Passenger> passenger_dropoffs;
    //the count of these passengers are the num of current passengers

    /**
    * @param id the integer value train id
    * @param maxPassengers total capacity of the train
    * @param train_stations array of Station
    * @param availableSeats semaphore giving permits of available seats
    */
    public Train(int id,int maxPassengers, Station[] train_stations, Semaphore availableSeats) {
        this.id = id;
        this.curPos = 0;
        //this.destPos = 0;
        this.maxPassengers = maxPassengers;
        this.availableSeats = availableSeats;
        
        this.status = "IDLE";
        this.door_status = "CLOSED";
        this.train_isRunning = true;
        
                this.train_stations = train_stations; 
        
        this.passengers = new ArrayList();
        this.passenger_dropoffs = new ArrayList();
        //this.train_stations = new Station[train_stations.length];       
    }
    
    /**
     * gets the number of occupied seats/ current number of passengers on board
     * @return integer
     */    
    public int getOccupied_seats() {
        return occupied_seats;
    }
    /**
     * sets number how many seats are occupied
     * @param occupied_seats an integer
     */
    public void setOccupied_seats(int occupied_seats) {
        this.occupied_seats = occupied_seats;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    /**
     * gets available permits for semaphore availableSeats
     * @return int value of available permits
     */
    public int getAvailableSeats() {
        return availableSeats.availablePermits();
    }

    public void setAvailableSeats(Semaphore availableSeats) {
        this.availableSeats = availableSeats;
    }
    /**
     * string of trains status 
     * @return IDLE, TRAVELLING, LOADING
     */
    public String getStatus() {
        return status;
    }

    /**
     * set the status of the train 
     * @param status if IDLE, TRAVELLING, LOADING
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * gets the current door status
     * @return string value CLOSED, OPEN
     */
    public String getDoor_status() {
        return door_status;
    }
    /**
     * sets string door status
     * @param door_status string OPEN, CLOSED
     */
    public void setDoor_status(String door_status) {
        this.door_status = door_status;
    }

    /**
     * gets boolean value if train is running or not
     * @return boolean value
     */
    public boolean isTrain_isRunning() {
        return train_isRunning;
    }

    /**
     * sets boolean value
     * is the train running?
     * @param train_isRunning boolean value
     */
    public void setTrain_isRunning(boolean train_isRunning) {
        this.train_isRunning = train_isRunning;
    }   
    
    /**
     * add a passenger to trains array list of passengers
     * @param p Passenger object being added to array list
     */
    public void addPassenger(Passenger p){
        passengers.add(p);
    }
    
    /**
     * gets the Semaphore availableSeats of the train
     * @return Semaphore 
     */
    public Semaphore get_Semaphore(){
        return availableSeats;
    }
    
    
    public void loadPassengers(){
        System.out.println("Loading Passengers");
        /*
        int passengersToGet = Math.min(
                        maxPassengers - passengers.size(),
                        station.getPassengers().size()); //how many passengers in station
        if (passengersToGet == 0)
                System.out.println("Train " + this.id +" is full");
        else
                System.out.println("Adding " + passengersToGet + " passengers");
        for (int i = 0; i < passengersToGet ; i++) {
                Passenger p = station.removeAndGetPassenger();
                passengers.add(p);
                System.out.println("Passenger added " + p.getId());
        
        }        
        */
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurPos() {
        return curPos;
    }

    public void setCurPos(int curPos) {
        this.curPos = curPos;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public ArrayList<Passenger> getPassenger_dropoffs() {
        return passenger_dropoffs;
    }

    public void setPassenger_dropoffs(ArrayList<Passenger> passenger_dropoffs) {
        this.passenger_dropoffs = passenger_dropoffs;
    }        
    
    /**
     * dropping off passengers, removing array list of passenger objects
     * passenger_dropoffs from array list passengers
     */
    public void unloadPassengers(){       
        System.out.println("Unloading Passengers");
        passenger_dropoffs.clear();
        for(Passenger p : passengers){
            if(true)
            {
                this.occupied_seats--;
                passenger_dropoffs.add(p);
            }else{
                //p.updateDest();
            }                    
        }
        passengers.removeAll(passenger_dropoffs);
        availableSeats.release(passenger_dropoffs.size());
    }
    
    public void runTrain(){
        System.out.println("Train running");
        System.out.println("current station: " + curPos);
        curPos += 1;
        
        if(curPos == 8)
            curPos = 0;
    }
    
    @Override
    public void run(){
        System.out.println("Train [" + this.id + "] is created and running");
        
        while (train_isRunning) {
            /*loadPassengers();
            mayBoard.release(maxPassengers);
            try { 
                runReady.acquire(maxPassengers);
            } catch (InterruptedException ex) {
                
            }
            runTrain();
            unloadPassengers();
            mayUnboard.release(maxPassengers);
            try {
                    unloadDone.acquire(maxPassengers);
            } catch (InterruptedException ex) {
                    
            }
            */
            
           train_stations[curPos].setCurrentTrain(this);
           this.unloadPassengers();
           train_stations[curPos].load_train(this.getAvailableSeats());
           try{
               Thread.sleep(3000);
               curPos = (getCurPos() + 1)%8;
           }catch(Exception e){
               System.out.println(e);
           }
        }        
         
        while(true)
            runTrain();
    }
}
