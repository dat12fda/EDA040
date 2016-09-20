package lift;

import java.util.ArrayList;

//This class is the Lord of the classes.
public class HerreKlassen {


	private static ArrayList<Liftare> liftarna;
	private static Lift lift;
	private static int nbrMan;


	public static void main(String[] args) throws InterruptedException {
		LiftView lyftTitt = new LiftView();
		Monitor monitor = new Monitor(lyftTitt);
		liftarna = new ArrayList<Liftare>();
		lift = new Lift(monitor);
		nbrMan = 20;
		for (int i = 0; i < nbrMan; i++) {
			liftarna.add(new Liftare(monitor));
		}		
		lift.start();
		for (int i = 0; i < nbrMan; i++) {
			liftarna.get(i).start();
		}
		lift.join();
		for (int i = 0; i < nbrMan; i++) {
			liftarna.get(i).join();
		}
	}	
}
