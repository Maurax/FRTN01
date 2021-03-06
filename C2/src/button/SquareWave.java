package button;
// Code skeleton for the SquareWave class in the Buttons exercise

public class SquareWave extends Thread {
	private Regul regul;
	private int sign = 1;

	private AmplitudeMonitor ampMon = new AmplitudeMonitor();

	// Internal AmplitudeMonitor class
	// Constructor not necessary
	private class AmplitudeMonitor {
		private double amp = 0.0;

		// Synchronized access methods. The amplitude should always be
		// non-negative.
		public synchronized double getAmp() {
			return amp;
		}

		public synchronized void setAmp(double amp) {
			if (amp > 0)
				this.amp = amp;
			else
				this.amp = 0;
		}
	}

	// Constructor
	public SquareWave(Regul regul, int priority) {
		this.regul = regul;
		setPriority(priority);
	}

	// Public methods to decrease and increase the amplitude by delta. Called
	// from Buttons
	// Should be synchronized on ampMon. Should also call the setRef method in
	// Regul
	public void incAmp(double delta) {
		ampMon.setAmp(ampMon.getAmp() + delta);
		setRef();
	}

	public void decAmp(double delta) {
		ampMon.setAmp(ampMon.getAmp() - delta);
		setRef();
	}

	private void setRef() {
		regul.setRef(sign * ampMon.getAmp());
	}

	// run method
	public void run() {
		final int period = 10000;
		try {
			while (!Thread.interrupted()) {
				sign *= -1;
				setRef();

				Thread.sleep(period / 2);

			}
		} catch (InterruptedException e)

		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Squarewave stopped.");
	}
}
