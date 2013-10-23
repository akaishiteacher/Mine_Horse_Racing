package net.akaishi_teacher.mhr;

import java.util.ArrayList;

public class MHRFullTimeHealRunnable implements Runnable {

	private MHR plugin;

	public MHRFullTimeHealRunnable(MHR plugin) {
		super();
		this.plugin = plugin;
	}



	@Override
	public void run() {
		try {
			ArrayList<HorseInfo> infoList = plugin.getHorsesControler().getHorseInfoList();
			for (HorseInfo info : infoList) {
				if (!info.getHorse().isDead()) {
					info.getHorse().setHealth(20.0D);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
