package net.akaishi_teacher.mhr.course;

public class StartTimerListener implements CountdownListener {

	protected Timer timer;
	
	public StartTimerListener(Timer timer) {
		this.timer = timer;
	}
	
	@Override
	public void start() {
	}

	@Override
	public void end() {
		timer.start();
	}

	@Override
	public void count(int now) {
	}

}
