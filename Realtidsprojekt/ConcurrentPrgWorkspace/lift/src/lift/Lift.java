package lift;

/**
 * 
 * @author stv10hjo
 *This class is a lift. Hello!
 */
public class Lift extends Thread {
//	private int numPass;
//	private int allowed;
//	private int currFloor;
//	private int nextFloor;
	private HerreKlassen monitor;
	
	public Lift(HerreKlassen monitor) {
		this.monitor = monitor;
		
//		allowed = 4;
//		numPass = 0;
//		currFloor = 0;
//		nextFloor = 1;
		
	}	
	
	
	public void run(){
		System.out.println("Smuts-lift helvetet is teh runnin'...");
//		int i = 0;
		while(true) {
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) { }
//			i++;
			monitor.liftGo();
			
			
			
		}		
	}

	
	

}
