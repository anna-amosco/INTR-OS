/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package introos.mco2;

import static introos.mco2.TrainSystem.*;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Passenger implements Runnable{   
    private int id;
    private int startStation; //0-7 station id
    //private int destStation;
    //private int position;
    private static int totalPassengerCount; //track total number of spawned passengers    
    private final Station station;
    //private boolean onBoard;
    //private boolean waiting;
    private String status; //WAITING, ONBOARD

    public Passenger(int startStation,Station s) {
        totalPassengerCount++;
        this.id = totalPassengerCount;
        this.startStation = startStation;
        station = s;
        this.status = "WAITING";
    }
    
    /*
    public void board(){
        System.out.println("Passenger has boarded");
        //onTrain = 
    }
    
    public void unboard(){
        System.out.println("Passenger has unboarded");
    }
    */

    public static int getTotalPassengerCount() {
        return totalPassengerCount;
    }

    public static void setTotalPassengerCount(int totalPassengerCount) {
        Passenger.totalPassengerCount = totalPassengerCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStartStation() {
        return startStation;
    }

    public void setStartStation(int startStation) {
        this.startStation = startStation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }  
    
    public void updateDestination(){
        this.startStation--;
    }
    
    @Override
    public void run(){
        /*
        try {
            mayBoard.acquire();
        } catch (InterruptedException ex) {
            
        }
        
        board();        
        
        runReady.release();
        try {
            mayUnboard.acquire();
        } catch (InterruptedException ex) {
            
        }
        unboard();
        unloadDone.release();
        allPass.release();        
        */
        station.waiting_for_train(this);
    }
    
}
