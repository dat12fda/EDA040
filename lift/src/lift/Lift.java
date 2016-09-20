package lift;

/**
 * 
 * @author stv10hjo This class is a lift. Hello!
 */
public class Lift extends Thread {
	// private int numPass;
	// private int allowed;
	// private int currFloor;
	// private int nextFloor;
	private Monitor monitor;

	public Lift(Monitor monitor) {
		this.monitor = monitor;

		// allowed = 4;
		// numPass = 0;
		// currFloor = 0;
		// nextFloor = 1;

	}

	public void run() {
		System.out.println("Smuts-lift helvetet is teh runnin'...");
		while (true) {
			
			try {
				monitor.liftGo();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				monitor.decideNext();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int[] myInfo = monitor.myLoc();		
			monitor.lyftTitt.moveLift(myInfo[0], myInfo[1]);

		}

	}

}
