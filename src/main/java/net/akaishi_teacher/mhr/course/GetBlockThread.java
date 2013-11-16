package net.akaishi_teacher.mhr.course;

import java.util.ArrayList;

import net.akaishi_teacher.mhr.MHRCore;
import net.akaishi_teacher.mhr.data.HorseData;

import org.bukkit.Location;
import org.bukkit.entity.Horse;

public final class GetBlockThread implements Runnable {

	/**
	 * チェックの間隔
	 */
	public int interval;

	/**
	 * 経過時間
	 */
	private long time;

	/**
	 * {@link MHRCore}クラスのインスタンス
	 */
	private MHRCore mhr;

	public GetBlockThread(MHRCore mhr, int interval) {
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
					mhr.getCourseFunc().walk(walkedBlockLocation.getBlock(), data);
				}
			}
		}
	}

}
