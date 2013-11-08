package net.akaishi_teacher.mhr.course;

public class Timer {

	protected long time;

	private CountThread countThread;

	public boolean start() {
		if (countThread == null) {
			CountThread runnable = new CountThread();
			Thread thread = new Thread(runnable);
			thread.start();
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

	public long getTime() {
		return time;
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
