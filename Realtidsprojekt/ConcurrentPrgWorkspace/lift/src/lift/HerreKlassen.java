package lift;

import java.util.ArrayList;

//This class is the Lord of the classes. Heil! 
public class HerreKlassen {
	private LiftView lyftTitt;
	private static ArrayList<Liftare> liftarna;
	private static Lift lift;
	private int here;
	private int next;
	private int[] waitEntry;
	private int[] waitExit;
	private int load;
	private static int nbrMan;
	private int totWaiting;

	public HerreKlassen() {
		totWaiting = 0;
		nbrMan = 10;
		load = 0;
		next = 1;
		lyftTitt = new LiftView();
		liftarna = new ArrayList<Liftare>();
		lift = new Lift(this);
		for (int i = 0; i < nbrMan; i++) {
			liftarna.add(new Liftare(this));
		}
		waitEntry = new int[7];
		waitExit = new int[7];
		for (int i = 0; i < 7; i++) {
			waitEntry[i] = 0;
			waitExit[i] = 0;
		}

	}

	public static void main(String[] args) throws InterruptedException {
		new HerreKlassen();
		lift.start();
		for (int i = 0; i < nbrMan; i++) {
			liftarna.get(i).start();
		}
		lift.join();
		for (int i = 0; i < nbrMan; i++) {
			liftarna.get(i).join();
		}

	}

	/** Passenger arrives and waits for the elevator 
	 * 
	 * @param startLocation
	 */
	public synchronized void spawn(int startLocation) {
		waitEntry[startLocation]++;
		totWaiting++;
		draw(startLocation);
		notifyAll();
	}
	
	/** Passenger enters the elevator
	 * 
	 * @param startLocation
	 * @param destination
	 * @throws InterruptedException
	 */
	public synchronized void enterLift(int startLocation, int destination)
			throws InterruptedException {

		while (here != startLocation || isFull() || !sameDirection(destination, startLocation)) {
			wait();
		}
		load++;
		totWaiting--;
		waitEntry[startLocation]--;
		waitExit[destination]++;
		draw(startLocation);
		notifyAll();
	}

	private boolean sameDirection(int destination, int startLocation) {
		int nextDest = next - here;      						  
		int myNextCheck = destination - startLocation;				 	
		if((nextDest < 0 ) && (myNextCheck < 0)) {
			return true;
		} else if ((nextDest > 0) && (myNextCheck > 0))	{
			return true;
		}
		return false;
	}

	/** Passenger leaves the elevator
	 * 
	 * @param destination
	 * @throws InterruptedException
	 */
	public synchronized void exitLift(int destination)
			throws InterruptedException {
		while (here != destination) {
			wait();
		}
		load--;
		waitExit[destination]--;
		draw(destination);
		notifyAll();
	}

	private boolean isFull() {
		return (load == 4);
	}

	public synchronized void draw(int floor) {
		lyftTitt.drawLevel(floor, waitEntry[floor]);
		lyftTitt.drawLift(here, load);
	}

	
	public synchronized void liftGo() {
		if((load != 0) || (totWaiting != 0)) {
			draw(here);		
			lyftTitt.moveLift(here, next);		
			int temp = here;
			here = next;
			if (here == 6) { 
				next = 5; 
			} else if (here == 0) {
				next = 1;
			} else if (temp < next) {
				next++;
			} else {
				next--;
			}
			notifyAll();
		}
	}
}
