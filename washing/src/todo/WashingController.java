package todo;

import done.*;

public class WashingController implements ButtonListener {	
	private AbstractWashingMachine theMachine;
	private double theSpeed;
	private SpinController sc;
	private TemperatureController tc;
	private WaterController wc;
	private WashingProgram wp;
	
    public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
    	// Create and start your controller threads here
    	this.theMachine = theMachine;
    	this.theSpeed = theSpeed;
    	sc = new SpinController(theMachine, theSpeed);
    	tc = new TemperatureController(theMachine, theSpeed);
    	wc = new WaterController(theMachine, theSpeed);   
    	sc.start();
    	tc.start();
    	wc.start();
    }

    public void processButton(int theButton) {
    	// Handle button presses (0, 1, 2, or 3). A button press
    	// corresponds to starting a new washing program. What should
    	// happen if there is already a running washing program?
    	switch(theButton) {
    	case 1:
    		if (wp == null || !wp.isAlive()) {
    			wp = new WashingProgram1(theMachine, theSpeed, tc, wc, sc);
    			wp.start();
    		}
    		break;
    	case 2:
    		if (wp == null || !wp.isAlive()) {
        		wp = new WashingProgram2(theMachine, theSpeed, tc, wc, sc);
        		wp.start();
    		}
    		break;
    	case 3:
    		if (wp == null || !wp.isAlive()) {
        		wp = new WashingProgram3(theMachine,
        			theSpeed, tc, wc, sc);
        			wp.start();
    		}
    		break;
    	case 0:
    		wp.interrupt();
    		break;
    	}
    }
}
