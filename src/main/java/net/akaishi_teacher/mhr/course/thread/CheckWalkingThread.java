package net.akaishi_teacher.mhr.course.thread;

import java.util.ArrayList;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.status.HorseData;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Horse;

public final class CheckWalkingThread implements Runnable {

	/**
	 * チェックの間隔
	 */
	private int interval;

	/**
	 * 経過時間
	 */
	private long time;

	/**
	 * {@link MHRCore}クラスのインスタンス
	 */
	private MHRCore mhr;

	public CheckWalkingThread(MHRCore mhr, int interval) {
		this.mhr = mhr;
		this.interval = interval;
	}

	@Override
	public void run() {
		if (++time % interval == 0) {
			ArrayList<HorseData> datas = mhr.getStatus().getHorseDatas();
			for (HorseData data : datas) {
				Horse horse = data.horse;
				if (horse != null) {
					Location walkedBlockLocation = horse.getLocation().clone();
					walkedBlockLocation = walkedBlockLocation.add(0, -1, 0);
					Material type = walkedBlockLocation.getBlock().getType();
					if (type != Material.AIR) {
						data.walk(type);
					}
				}
			}
		}
	}

}
