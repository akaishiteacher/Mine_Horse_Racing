package net.akaishi_teacher.mhr.course;

import java.util.ArrayList;

import net.akaishi_teacher.mhr.HorseData;
import net.akaishi_teacher.mhr.MHRCore;

import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class DontLetPlayerDoDismountListener implements Listener {

	private MHRCore mhr;

	public DontLetPlayerDoDismountListener(MHRCore mhr) {
		this.mhr = mhr;
	}

	@EventHandler
	public void horseNoDamageEvent(VehicleExitEvent event) {
		if (mhr.getCourseFunc().getManager().isCannotExitMode()) {
			if (event.getVehicle() instanceof Horse) {
				ArrayList<HorseData> datas = mhr.getStatus().getHorseDatas();
				for (HorseData data : datas) {
					if (data.horse.equals(event.getVehicle()) && !data.tpFlag) {
						event.setCancelled(true);
					}
				}
			}
		}
	}

}
