package net.akaishi_teacher.mhr.course;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Countdown {

	protected int time = -1;

	protected ArrayList<CountdownListener> listeners = new ArrayList<>();

	protected int taskId;

	public void addListener(CountdownListener listener) {
		listeners.add(listener);
	}

	public void start(int time, JavaPlugin plugin) {
		this.time = time;
		this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, new CountdownProcess(), 0, 20).getTaskId();
		for (Iterator<CountdownListener> iterator = listeners.iterator(); iterator.hasNext();) {
			CountdownListener type = (CountdownListener) iterator.next();
			type.start();
		}
	}

	class CountdownProcess implements Runnable {
		@Override
		public void run() {
			for (Iterator<CountdownListener> iterator = listeners.iterator(); iterator.hasNext();) {
				CountdownListener type = (CountdownListener) iterator.next();
				type.count(time);
			}
			if (time == 0) {
				time = -1;
				for (Iterator<CountdownListener> iterator = listeners.iterator(); iterator.hasNext();) {
					CountdownListener type = (CountdownListener) iterator.next();
					type.end();
				}
				endTask();
			}
			time--;
		}
	}

	public void endTask() {
		Bukkit.getScheduler().cancelTask(taskId);
	}

	public boolean started() {
		if (time == -1) {
			return false;
		}
		return true;
	}

	public boolean ended() {
		if (time > 0) {
			return false;
		}
		return true;
	}

}
