package test;
import mco2.Train;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import mco2.Passenger;
import mco2.Station;

public class threadTest extends JFrame {
	
	
	static Scanner sc = new Scanner(System.in);
	static Train[] railroad = new Train[33];
	static ArrayList<Train> trains = new ArrayList<Train>();
	static ArrayList<Station> stations = new ArrayList<Station>();
	static ArrayList<Integer> trainCapacities = new ArrayList<Integer>();
	
	public threadTest() {
		GridLayout test = new GridLayout(0,2);
		this.setSize(500,500);
		this.setLayout(test);
		JButton addATrain = new JButton("Add a train");
		JLabel totalSeats = new JLabel("How many seats? ");
		JTextField seats = new JTextField();
		seats.setSize(100, 200);
		addATrain.addActionListener(new ActionListener() {
			int trainCounter = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (trains.size() == 16)
					System.out.println("Max trains deployed");
				else {
					System.out.println("Pressed");
					int numSeats = Integer.parseInt(seats.getText());
					int freePosition = 0;
					
					for(int i = 0; i < railroad.length; i++) {
						if(railroad[i] == null)
							freePosition = i;
					}
					Train t = new Train(trainCounter + 1, freePosition, numSeats);
					trainCounter++;
					t.start();
					trains.add(t);
				}
			}
			
		});
		addATrain.setEnabled(true);
		addATrain.setVisible(true);
		this.add(addATrain);
		this.add(totalSeats);
		this.add(seats);
		this.setVisible(true);
	}
	
	public static String represent(Train[] railroad) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < railroad.length; i++){
			if (railroad[i] == null)
				sb.append("=");
			else
				sb.append(railroad[i].getId());
		}
		return sb.toString();
	}
	
	public static void main(String args[]) {
		
//		Runner runner = new Runner();
//		runner.start();
//		
//		Runner runner2 = new Runner();
//		runner2.start();
		
		int position = 0;
		for(int i = 0; i < 8; i++) {
			Station s = new Station(i, position);
			position += 4;
			stations.add(s);
		}
		
		
		
//		for(int i = 0; i < 16; i++) {
////			System.out.println("Input capacity for train " + (i + 1));
////			trainCapacities.add(sc.nextInt());
////			sc.nextLine();
//			trainCapacities.add(20);
//		}
		
		
		Thread runnable = new Thread(){
			int trainCounter = 0;
			@Override
			public synchronized void run(){
				int trainId = 0;
				int numSeats = 0;
				int freePosition = 0;
				
				int idCounter = 0;
				int randStart = 0;
				int randDestination = 0;
				int min = 0;
				int max = 7;
				while(true){
						//Passenger p = new Passenger(idCounter, randStart, randDestination);
						//stations.get(randStart).addWaitingPassenger(p);
						for(Train train : trains) {			
							for(Station station : stations) {
								//System.out.println(station.getPosition() + " " + train.getPosition());
								if (train.getPosition() == station.getPosition())
								{
									station.addTrainIntoStation(train);
									train.unloadTrain(station);
									if (station.getPassengers().size() != 0)
										train.loadTrain(station);				
								}
							}
						}
						//notify();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					//}
				}
			}
		};
		
		Thread runnable2 = new Thread(){
			@Override
			public synchronized void run(){
				int idCounter = 0;
				int randStart = 0;
				int randDestination = 0;
				int min = 0;
				int max = 7;			
				
				while(true){
					idCounter++;
					randStart = ThreadLocalRandom.current().nextInt(min, max + 1);
					randDestination = ThreadLocalRandom.current().nextInt(min, max + 1);
					while(randStart == randDestination) {
						randStart = ThreadLocalRandom.current().nextInt(min, max + 1);
						randDestination = ThreadLocalRandom.current().nextInt(min, max + 1);
					}
					
					Passenger p = new Passenger(idCounter, randStart, randDestination);
					stations.get(randStart).addWaitingPassenger(p);
					
					for(Train train : trains) {
						if (railroad[train.getPrevPosition()] != null && railroad[train.getPrevPosition()].getId() == train.getId())
							railroad[train.getPrevPosition()] = null;
						railroad[train.getPosition()] =  train;
					}
					System.out.println(represent(railroad));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					notify();
				}
			}
		};
		
//		Thread runnable2 = new Thread(){
//			@Override
//			public synchronized void run(){
//				while(true){
//					//wait();
//					if (railroad[train2.getPrevPosition()].getId() == train2.getId())
//						railroad[train2.getPrevPosition()] = null;
//					railroad[train2.getPosition()] =  train2;
//					System.out.println(represent(railroad));
//					notifyAll();
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//				}
//			}
//		};
//		for(Train t : trains) {
//			t.start();
//		}
		new threadTest();
		runnable.start();
		runnable2.start();
		//train2.start();
	}
}
