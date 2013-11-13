package net.akaishi_teacher.mhr.commands;

import java.util.ArrayList;
import java.util.Iterator;

import net.akaishi_teacher.mhr.course.CountdownListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Countdown {

	/**
	 * 残り秒数
	 */
	protected int time = -1;

	/**
	 * Listenerのリスト
	 */
	protected ArrayList<CountdownListener> listeners = new ArrayList<>();

	/**
	 * TaskID
	 */
	protected int taskId;

	public void addListener(CountdownListener listener) {
		listeners.add(listener);
	}

	/**
	 * カウントダウンをスタートします。
	 * @param time
	 * @param plugin
	 */
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

	/**
	 * タスクを終了します。
	 */
	public void endTask() {
		Bukkit.getScheduler().cancelTask(taskId);
	}

	/**
	 * カウントダウンが開始されているか判定します。
	 * @return カウントダウンが開始されている場合はtrue
	 */
	public boolean started() {
		if (time == -1) {
			return false;
		}
		return true;
	}

	/**
	 * カウントダウンが終了しているか判定します。
	 * @return カウントダウンが終了している場合はtrue
	 */
	public boolean ended() {
		if (time > 0) {
			return false;
		}
		return true;
	}

}
