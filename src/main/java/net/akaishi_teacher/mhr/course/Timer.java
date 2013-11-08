package net.akaishi_teacher.mhr.course;


public class Timer {

	protected int time = -1;

	private CountThread countThread;

	public Timer() {
	}

	public boolean start() {
		if (countThread == null) {
			CountThread runnable = new CountThread();
			Thread thread = new Thread(runnable);
			thread.start();
			countThread = runnable;
			return true;
		}
		return false;
	}

	public void end() {
		if (countThread != null) {
			countThread.stopped = true;
			countThread = null;
		}
	}

	public void reset() {
		time = -1;
	}

	public String formattedTime(String format) {
		int second = time / 20 % 60;
		int min = time / (20*60);
		return String.format(format, min, second);
	}
	
	public int getTime() {
		return time;
	}

	public boolean running() {
		if (time != -1)
			return true;
		return false;
	}

	class CountThread implements Runnable {
		boolean stopped;
		@Override
		public void run() {
			while (!stopped) {
				time++;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
