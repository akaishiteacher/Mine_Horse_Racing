package net.akaishi_teacher.mhr.course;

public interface CountdownListener {

	public abstract void start();

	public abstract void end();
	
	public abstract void count(int now);
	
}
