package lift;

public class Monitor {
	LiftView lyftTitt;
	private volatile int here;
	private volatile int next;
	private int[] waitEntry;
	private int[] waitExit;
	private int load;
	private int totWaiting;
	private int direction;

	public Monitor(LiftView liftView) {
		lyftTitt = liftView;
		totWaiting = 0;
		direction = 1;
		load = 0;
		next = 0;
		waitEntry = new int[7];
		waitExit = new int[7];
		for (int i = 0; i < 7; i++) {
			waitEntry[i] = 0;
			waitExit[i] = 0;
		}
	}

	/**
	 * Passenger arrives and waits for the elevator
	 * 
	 * @param startLocation
	 */
	public synchronized void spawn(int startLocation) {
		waitEntry[startLocation]++;
		totWaiting++;
		drawLevel(startLocation);
		notifyAll();
	}

	/**
	 * Passenger enters the elevator
	 * 
	 * @param startLocation
	 * @param destination
	 * @throws InterruptedException
	 */
	public synchronized void enterLift(int startLocation, int destination)
			throws InterruptedException {

		while (here != startLocation || isFull() || here != next) {
			wait();
		}
		load++;
		totWaiting--;
		waitEntry[startLocation]--;
		waitExit[destination]++;
		drawLevel(here);
		draw(here);
		notifyAll();
	}

	/**
	 * Checks if the elevator is going the same direction as passengers
	 * destination
	 * 
	 * @param destination
	 * @param startLocation
	 * @return
	 */
	private boolean sameDirection(int destination, int startLocation) {
		int myNextCheck = destination - startLocation;
		if ((direction < 0) && (myNextCheck < 0)  || here != next) {
			return true;
		} else if ((direction > 0) && (myNextCheck > 0)) {
			return true;
		}
		return false;
	}

	/**
	 * Passenger leaves the elevator
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
		drawLevel(here);	
		draw(here);
		notifyAll();
	}

	private boolean isFull() {
		return (load == 4);
	}

	public synchronized void drawLevel(int floor) {
		lyftTitt.drawLevel(floor, waitEntry[floor]);
	}
	
	public synchronized void draw(int floor) {		
		lyftTitt.drawLift(here, load);
		if((waitEntry[here] > 0 && load < 4)) {
			System.out.println("Entry > 0 and Load < 4:     Here: " + here + ", Next: " + next + ", Load: " + load + ", Entry: " + waitEntry[here] + ", Exit: " + waitExit[here]);
		} 
		if((waitExit[here] > 0)){
			System.out.println("Exit > 0     Here: " + here + ", Next: " + next + ", Load: " + load + ", Entry: " + waitEntry[here] + ", Exit: " + waitExit[here]);
		}
	}
	
	
	
	public synchronized int[] myLoc() {
		int[] loc = {here, next};
		return loc;
	}
	

	public synchronized void liftGo() throws InterruptedException {
		while (load == 0 && totWaiting == 0) {
			wait();
		}		
		drawLevel(here);
		draw(here);
		//System.out.println(here);
		//System.out.println(next);

		here = next;
		if(here == 6) {
			direction = -1;
		} else if (here == 0) {
			direction = 1;
		}
		notifyAll();		
	}

	public synchronized void decideNext() throws InterruptedException {
		while ((waitExit[here] > 0) || (waitEntry[here] > 0 && load < 4)) {		
			wait();
		}		
		next = next + direction;
		
		System.out.println("Here: " + here);
		System.out.println("Next: " + next);
		notifyAll();
	}
}
