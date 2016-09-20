package todo;

import done.AbstractWashingMachine;

public class WashingProgram1 extends WashingProgram {
	private AbstractWashingMachine theMachine;
	private double theSpeed;
	private TemperatureController tc;
	private WaterController wc;
	private SpinController sc;
	

	protected WashingProgram1(AbstractWashingMachine mach, double speed,
			TemperatureController tempController,
			WaterController waterController, SpinController spinController) {
		super(mach, speed, tempController, waterController, spinController);
		// TODO Auto-generated constructor stub
		theMachine = mach;
		theSpeed = speed;
		tc = tempController;
		wc = waterController;
		sc = spinController;
	}

	@Override
	protected void wash() throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Starting program1");
		theMachine.setLock(true);
		wc.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL, 0.9));
		mailbox.doFetch();
		wc.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE, 0.9));
		tc.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_SET, 60.0));
		mailbox.doFetch();
		sc.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
		sleep((long)(500 * 1000 / mySpeed));   // CHANGE TO 30 MIN
		System.out.println("Done with main wash");
		sc.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
		tc.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE, 0.0));
		wc.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN, 0.2));
		mailbox.doFetch();
		wc.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE, 0.2));
		sleep(1000);
		//Rinse 5 times
		for (int i = 0; i < 5; i++) {
			System.out.println("Doing rinse #" + i);
			wc.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL, 0.9));
			mailbox.doFetch();
			wc.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE, 0.9));
			sc.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
			sleep((long)(500 * 1000 / mySpeed));
			sc.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
			wc.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN, 0.0));
			mailbox.doFetch();			
			wc.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE, 0.0));
		}
		
		sc.putEvent(new SpinEvent(this, SpinEvent.SPIN_FAST));
		sleep((long)(5 * 1 * 1000 / mySpeed));
		sc.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
		theMachine.setLock(false);		
	}

}
