package lift;

public class Liftare extends Thread {
	private int origin;
	private int dest;
	private HerreKlassen monitor;
	
	
	public Liftare(HerreKlassen monitor){
		setOriginAndDest();
		this.monitor = monitor;				
	}
	
	
	public void run(){
		try {
			sleep(1000*(int)(Math.random()*46.0));
		} catch (InterruptedException e1) {
			System.out.println("Kunde ej sova, mardrömmar...");
			e1.printStackTrace();
		}		
		monitor.spawn(origin);
		try {
			monitor.enterLift(origin, dest);
		} catch (InterruptedException e) {
			System.out.println("Smuts och kaos\n");
			e.printStackTrace();
		}
		try {
			monitor.exitLift(dest);
		} catch (InterruptedException e) {
			System.out.println("Smuts och kaos igen\n");
			e.printStackTrace();
		}
		run();
		
	}	
	
	
	/**
	 * sets origin and destination, random and not equal to each other.
	 */
	private void setOriginAndDest(){		
		origin = (int)(Math.random()*7.0);
		dest = (int)(Math.random()*7.0);
		while(dest == origin) dest = (int)(Math.random()*7.0);
				
	}

}
